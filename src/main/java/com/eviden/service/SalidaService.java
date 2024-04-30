package com.eviden.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.eviden.dto.SalidaDto;
import com.eviden.dto.SocioDto;
import com.eviden.exception.ExceptionValueNotRight;
import com.eviden.model.Barco;
import com.eviden.model.Salida;
import com.eviden.model.Socio;
import com.eviden.repository.BarcoRepository;
import com.eviden.repository.SalidaRepository;

@Service
public class SalidaService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SalidaRepository salidaRepository;

	@Autowired
	private BarcoRepository barcoRepository;
	
	
	/**
	 * Devuelve todas las salidas
	 * @return
	 */
	public List<SalidaDto> listSalidas() {
		return salidaRepository.findAll().stream().map(salida -> mapearDto(salida)).collect(Collectors.toList());
	}

	/**
	 * Metodo que devuelve la lista de salidas de un barco
	 * 
	 * @param barco
	 * @return
	 */
	public List<SalidaDto> listSalidasByBarco(Barco barco) {
		return salidaRepository.findByBarco(barco).stream().map(salida -> mapearDto(salida)).collect(Collectors.toList());
	}

	/**
	 * Metodo que busca una salida por su id
	 * 
	 * @param id
	 * @return
	 */
	public SalidaDto getSalidaById(String id) {
		int idInt = 0;

		try {
			idInt = Integer.parseInt(id);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El id debe ser un valor entero");
		}

		Salida salida = salidaRepository.findById(idInt).orElse(null);

		if (salida == null) {
			throw new ExceptionValueNotRight("No existe salida con ese id");
		}

		return mapearDto(salida);
	}

	/**
	 * Metodo para añadir una salida
	 * @param salidaDto
	 * @return
	 */
	public SalidaDto addSalida(SalidaDto salidaDto) {
		
		SalidaDto s = new SalidaDto(salidaDto.getHoraSalida(), salidaDto.getDestino(), salidaDto.getDniPatron(), salidaDto.getIdBarco());
		Salida salida = mapearEntidad(s);
		salidaRepository.save(salida);
		salidaDto.setIdSalida(salida.getIdSalida());
		return mapearDto(salida);
	}
	
	/**
	 * Metodo para editar una salida
	 * @param salidaDto
	 * @param id
	 * @return
	 */
	
	public SalidaDto editSalida(SalidaDto salidaDto, String id) {
		Integer idInt = 0;
		try {
			idInt = Integer.parseInt(id);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El id debe ser un valor entero");
		}
		
		
		Salida salida = salidaRepository.findById(idInt).orElse(null);
		Barco barco = barcoRepository.findById(salidaDto.getIdBarco()).orElse(null);

		if (barco == null) {
			throw new ExceptionValueNotRight("No existe ese barco");
		}else if (salida == null) {
			throw new ExceptionValueNotRight("No existe esa salida");
		}

		salida.setHoraSalida(salidaDto.getHoraSalida());
		salida.setDestino(salidaDto.getDestino());
		salida.setDniPatron(salidaDto.getDniPatron());
		salida.setBarco(barco);

		salidaRepository.save(salida);
		
		salidaDto.setIdSalida(salida.getIdSalida());

		return mapearDto(salida);
	}

	/**
	 * Metodo para borrar una salida
	 * 
	 * @param salida
	 */
	public void deleteSalida(String id) {
		int idInt = 0;

		try {
			idInt = Integer.parseInt(id);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El id debe ser un valor entero");
		}
		
		Salida salida = salidaRepository.findById(idInt).orElse(null);
		
		if (salida == null) {
			throw new ExceptionValueNotRight("No existe esa salida");
		}
		

		salidaRepository.delete(salida);
		
	}

	/**
	 * Metodo para paginar la lista de salidas Si el order es true que me ordene
	 * ascendentemente por el campo que indiquemos Si el order es false que me
	 * ordene descendentemente por el campo que indiquemos
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param sortField
	 * @param order
	 * @return devuelve la lista de salidas paginada
	 */
	public Page<SalidaDto> getSalidas(String numPage, int pageSize, String sortField, boolean order) {
		Pageable pageable = null;
		int numPageInt = 0;
		
		try {
			numPageInt = Integer.parseInt(numPage);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El número de la página debe ser un valor entero");
		}
		if (numPageInt <=0) {
			throw new ExceptionValueNotRight("El número de la página debe ser un valor positivo");
		} 
		if (pageSize <=0) {
			throw new ExceptionValueNotRight("El tamaño de la página debe ser un valor positivo");
		}
		if(order == false) {
			pageable = PageRequest.of(numPageInt -1, pageSize,Sort.by(sortField).descending());
		}else {
			pageable = PageRequest.of(numPageInt -1, pageSize,Sort.by(sortField).ascending());
		}
		
		try {
			Page<Salida> temp =  salidaRepository.findAll(pageable);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El campo por el que el ordenar no existe");
		}
		
		Page<Salida> temp = salidaRepository.findAll(pageable);
		Page<SalidaDto> result = temp.map(salida -> new SalidaDto(salida.getIdSalida(), salida.getHoraSalida(), salida.getDestino(), salida.getDniPatron(), salida.getBarco().getIdBarco()));
		
		return result;
	}
	
	
	/**
	 * Método que te devuelve una lista de salidas que contenga ese destino
	 * @param destino
	 * @return
	 */
	public List<SalidaDto> getSalidaByDestino(String destino) {

		List<Salida> salidas = salidaRepository.findByDestinoContaining(destino);

		return salidas.stream().map(salida -> mapearDto(salida)).collect(Collectors.toList());
	}

	
	

	/**
	 * Convierte entidad a DTO
	 * 
	 * @param salida
	 * @return
	 */
	private SalidaDto mapearDto(Salida salida) {
		return modelMapper.map(salida, SalidaDto.class);
	}

	/**
	 * Convierte DTO a entidad
	 * 
	 * @param salidaDto
	 * @return
	 */
	private Salida mapearEntidad(SalidaDto salidaDto) {
		return modelMapper.map(salidaDto, Salida.class);
	}

}
