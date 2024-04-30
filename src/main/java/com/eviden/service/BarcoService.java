package com.eviden.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eviden.dto.BarcoDto;
import com.eviden.exception.ExceptionValueNotRight;
import com.eviden.model.Barco;
import com.eviden.repository.BarcoRepository;
import com.eviden.repository.SocioRepository;

@Service
public class BarcoService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BarcoRepository barcoRepository;
	
	@Autowired
	private SocioRepository socioRepository;
	
	
	/**
	 * Servicio que nos devuelta una lista de todos los barcos
	 * @return
	 */
	public List<BarcoDto> getBarcos() {
		return barcoRepository.findAll().stream().map(barco -> mapearDto(barco)).collect(Collectors.toList());
	}
	
	
	/**
	 * Servicio que nos devuelve un barco buscado por su id
	 * @param id
	 * @return
	 */
	public BarcoDto getBarcoById(String id) {
		
		int idInt = 0;

		try {
			idInt = Integer.parseInt(id);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El id debe ser un valor entero");
		}
		
		Barco barco = barcoRepository.findById(idInt).orElse(null);
		
		if (barco==null) {
			throw new ExceptionValueNotRight("No existe barco con ese id");
		}
		
		
		return mapearDto(barco);
	}
	
	
	/**
	 * Servicio que nos permite añadir un barco nuevo
	 * @param barco
	 * @return
	 */
	public BarcoDto addBarco(BarcoDto barcoDto) {
		validarCampos(barcoDto);
		BarcoDto barcoDto2 = new BarcoDto(barcoDto.getMatricula(), barcoDto.getNombreBarco(), barcoDto.getNumAmarre(), barcoDto.getCuota(), barcoDto.getIdSocio());
		Barco barco = mapearEntidad(barcoDto2);
		barcoRepository.save(barco);
		return mapearDto(barco);
	}
	
	
	/**
	 * Servicio que nos permite editar un barco
	 * @param barcoDto
	 * @param id
	 * @return
	 */
	public BarcoDto editBarco(BarcoDto barcoDto, String id) {
		
		Integer idInt = 0;
		try {
			idInt = Integer.parseInt(id);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El id debe ser un valor entero");
		}
		
		Barco barco = barcoRepository.findById(idInt).orElse(null);
		
		if (barco==null) {
			throw new ExceptionValueNotRight("No existe barco con ese id");
		}
		
		validarCampos(barcoDto);
		
		barco.setMatricula(barcoDto.getMatricula());
		barco.setNombreBarco(barcoDto.getNombreBarco());
		barco.setNumAmarre(barcoDto.getNumAmarre());
		barco.setCuota(barcoDto.getCuota());
		barco.setSocio(socioRepository.findById(barcoDto.getIdSocio()).orElse(null));
		
		
		barcoRepository.save(barco);
				
		return mapearDto(barco);
	}
	
	
	/**
	 * Servicio que nos permite borrar un barco
	 * @param barco
	 */
	public void deleteBarco(String id) {
		int idInt = 0;

		try {
			idInt = Integer.parseInt(id);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El id debe ser un valor entero");
		}

		Barco barco = barcoRepository.findById(idInt).orElse(null);

		if (barco == null) {
			throw new ExceptionValueNotRight("No existe ese barco");
		}
		
		barcoRepository.delete(barco);
	}
	
	
	/**
	 * Servicio que nos devuelve un barco buscado por su matricula
	 * @param matricula
	 * @return
	 */
	public BarcoDto getBarcoByMatricula(String matricula) {
		Barco barco = barcoRepository.findByMatricula(matricula).size() == 0 ? null : barcoRepository.findByMatricula(matricula).get(0) ;
		
		if (barco==null) {
			throw new ExceptionValueNotRight("No existe barco con esa matrícula");
		}
		
		return mapearDto(barco);
	}
	
	/**
	 * Servicio que nos devuelve un barco buscado por su numero de amarre
	 * @param numAmarre
	 * @return
	 */
	public BarcoDto getBarcoByNumAmarre(Integer numAmarre) {
		Barco barco = barcoRepository.findByNumAmarre(numAmarre).size() == 0 ? null : barcoRepository.findByNumAmarre(numAmarre).get(0) ;
		
		if (barco==null) {
			throw new ExceptionValueNotRight("No existe barco con ese numero del amarre");
		}
		
		return mapearDto(barco);
	}
	
	
	private void validarCampos(BarcoDto barcoDto) {
		
		if (barcoDto.getNombreBarco()==null) {
			throw new ExceptionValueNotRight("El nombre no puede ser nulo");
		}else if (barcoDto.getNombreBarco().isEmpty()) {
			throw new ExceptionValueNotRight("El nombre no puede estar vacío");
		}
		
		if (barcoDto.getNumAmarre()==null) {
			throw new ExceptionValueNotRight("El numero del amarre no puede ser nulo");
		}else if (barcoDto.getNumAmarre()<=0) {
			throw new ExceptionValueNotRight("El numero del amarre debe ser mayor que cero");
		}else if (barcoRepository.findByNumAmarre(barcoDto.getNumAmarre()).size()!=0 && !barcoRepository.findByNumAmarre(barcoDto.getNumAmarre()).get(0).getNumAmarre().equals(barcoDto.getNumAmarre())) {
			throw new ExceptionValueNotRight("El numero del amarre ya existe");
		}
	
				
		if (barcoDto.getMatricula()==null) {
			throw new ExceptionValueNotRight("La matrícula no puede ser nula");
		}else if (barcoDto.getMatricula().isEmpty()) {
			throw new ExceptionValueNotRight("La matrícula no puede estar vacía");
		}else if (barcoRepository.findByMatricula(barcoDto.getMatricula()).size()!=0 && !barcoRepository.findByMatricula(barcoDto.getMatricula()).get(0).getMatricula().equals(barcoDto.getMatricula())) {
			throw new ExceptionValueNotRight("La matrícula ya existe");
		}
		
		if (barcoDto.getCuota()==null) {
			throw new ExceptionValueNotRight("La cuota no puede ser nula");
		}else if (barcoDto.getCuota()<=0) {
			throw new ExceptionValueNotRight("La cuota debe ser mayor que cero");
		}
	}
	
	
	/**
	 * Convierte entidad a DTO
	 * 
	 * @param barco
	 * @return
	 */
	private BarcoDto mapearDto(Barco barco) {
		return modelMapper.map(barco, BarcoDto.class);
	}

	/**
	 * Convierte DTO a entidad
	 * 
	 * @param barcoDto
	 * @return
	 */
	private Barco mapearEntidad(BarcoDto barcoDto) {
		return modelMapper.map(barcoDto, Barco.class);
	}
	
}
