package com.eviden.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eviden.dto.SocioDto;
import com.eviden.exception.ExceptionCredentialNotValid;
import com.eviden.exception.ExceptionValueNotRight;
import com.eviden.model.Socio;
import com.eviden.repository.SocioRepository;

@Service
public class SocioService implements UserDetailsService{

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SocioRepository socioRepository;

	/**
	 * Servicio que nos devuelve la lista de todos los socios
	 * 
	 * @return
	 */
	public List<SocioDto> getSocios() {
		List<Socio> socios = socioRepository.findAll();
		return socios.stream().map(socio -> mapearDto(socio)).collect(Collectors.toList());
	}

	/**
	 * Servicio que nos devuelve un socio buscado por su id
	 * 
	 * @param id
	 * @return
	 */
	public SocioDto getSocioById(String id) {

		Integer idInt = 0;
		try {
			idInt = Integer.parseInt(id);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El id debe ser un valor entero");
		}

		Socio socio = socioRepository.findById(idInt).orElse(null);

		if (socio == null) {
			throw new ExceptionValueNotRight("No existe socio con ese id");
		}

		return mapearDto(socio);
	}

	/**
	 * Servicio que nos permite añadir un socio
	 * 
	 * @param socio
	 * @return
	 */
	public SocioDto addSocio(SocioDto socioDto) {
		validarCampos(socioDto);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(socioDto.getPassword());
		SocioDto socioDto2 = new SocioDto(socioDto.getDni(), socioDto.getName(), socioDto.getLastName(), socioDto.getAddress(), socioDto.getPhone(), socioDto.getEmail(), encodedPassword);
		Socio socio = mapearEntidad(socioDto2);
		socio.setRole("ROLE_USER");
		socioRepository.save(socio);
		socioDto.setIdSocio(socio.getIdSocio());
		socioDto.setRole(socio.getRole());
		return mapearDto(socio);
	}
	
	
	/**
	 * Servicio que permite editar un socio
	 * @param socioDto
	 * @param id
	 * @return
	 */
	public SocioDto editSocio(SocioDto socioDto, String id) {
		
		Integer idInt = 0;
		try {
			idInt = Integer.parseInt(id);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El id debe ser un valor entero");
		}
		
		Socio socio = socioRepository.findById(idInt).orElse(null);
		
		if (socio==null) {
			throw new ExceptionValueNotRight("No existe socio con ese id");
		}
		
		validarCampos(socioDto);
		
		socio.setDni(socioDto.getDni());
		socio.setName(socioDto.getName());
		socio.setLastName(socioDto.getLastName());
		socio.setEmail(socioDto.getEmail());
		socio.setAddress(socioDto.getAddress());
		socio.setPhone(socioDto.getPhone());
		
		
		socioRepository.save(socio);
				
		return mapearDto(socio);
	}

	/**
	 * Servicio que nos permite borrar un socio
	 * 
	 * @param socio
	 */
	public void deleteSocio(String id) {

		int idInt = 0;

		try {
			idInt = Integer.parseInt(id);
		} catch (Exception e) {
			throw new ExceptionValueNotRight("El id debe ser un valor entero");
		}

		Socio socio = socioRepository.findById(idInt).orElse(null);

		if (socio == null) {
			throw new ExceptionValueNotRight("No existe ese socio");
		}

		socioRepository.delete(socio);

	}

	/**
	 * Servicio que nos devuelve un socio buscado por su email
	 * 
	 * @param email
	 * @return
	 */
	public SocioDto getSocioByEmail(String email) {
		Socio socio = socioRepository.findByEmail(email).size() == 0 ? null : socioRepository.findByEmail(email).get(0) ;
		
		if (socio==null) {
			throw new ExceptionValueNotRight("No existe socio con ese email");
		}
		
		return mapearDto(socio);
	}
	
	
	/**
	 * Servicio que nos devuelve un socio buscado por su dni
	 * @param dni
	 * @return
	 */
	public SocioDto getSocioByDni(String dni) {

		Socio socio = socioRepository.findByDni(dni).get(0);

		if (socio == null) {
			throw new ExceptionValueNotRight("No existe socio con ese dni");
		}

		return mapearDto(socio);
	}
	
	
	/**
	 * Devuelve la lista de socios que son patrones
	 * @return
	 */
	public List<SocioDto> getSociosPatrones() {
		List<SocioDto> listSocioDto = new ArrayList<>();
		for (Socio s : socioRepository.findAllPatrones()) {
			listSocioDto.add(mapearDto(s));
		}
		return listSocioDto;
	}
	
	
	
	private void validarCampos(SocioDto socioDto) {
		
		if (socioDto.getName()==null) {
			throw new ExceptionValueNotRight("El nombre no puede ser nulo");
		}else if (socioDto.getName().isEmpty()) {
			throw new ExceptionValueNotRight("El nombre no puede estar vacío");
		}
		
		if (socioDto.getLastName()==null) {
			throw new ExceptionValueNotRight("Los apellidos no pueden ser nulos");
		}else if (socioDto.getLastName().isEmpty()) {
			throw new ExceptionValueNotRight("Los apellidos no pueden estar vacíos");
		}
				
		if (socioDto.getEmail()==null) {
			throw new ExceptionValueNotRight("El email no puede ser nulo");
		}else if (socioDto.getEmail().isEmpty()) {
			throw new ExceptionValueNotRight("El email no puede estar vacío");
		}else if (socioRepository.findByEmail(socioDto.getEmail()).size()!=0 && !socioRepository.findByEmail(socioDto.getEmail()).get(0).getEmail().equals(socioDto.getEmail())) {
			throw new ExceptionValueNotRight("El email ya existe");
		}
		
		if (socioDto.getAddress()==null) {
			throw new ExceptionValueNotRight("La dirección no puede ser nula");
		}else if (socioDto.getAddress().isEmpty()) {
			throw new ExceptionValueNotRight("La dirección no puede estar vacío");
		}
		
		if (socioDto.getPhone()==null) {
			throw new ExceptionValueNotRight("El teléfono no puede ser nulo");
		}else if (socioDto.getPhone().isEmpty()) {
			throw new ExceptionValueNotRight("El teléfono no puede estar vacío");
		}else if (!socioDto.getPhone().matches("\\d{9}")) {
			throw new ExceptionValueNotRight("El teléfono debe tener 9 cifras");
		}else if (socioRepository.findByPhone(socioDto.getPhone()).size()!=0 && !socioRepository.findByPhone(socioDto.getPhone()).get(0).getPhone().equals(socioDto.getPhone())) {
			throw new ExceptionValueNotRight("El teléfono ya existe");
		}
	}
	
	
	/**
	 * Metodo que nos comprueba si con el dni que nos estamos logueando existe en la base de datos
	 */
	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
		List<Socio> result = socioRepository.findByDni(dni);
		
		if (result!=null && result.size()==1) {
			return result.get(0);
		}else {
			throw new ExceptionCredentialNotValid("Usuario no encontrado con dni: "+ dni);
		}
	}
	

	/**
	 * Convierte entidad a DTO
	 * 
	 * @param socio
	 * @return
	 */
	private SocioDto mapearDto(Socio socio) {
		return modelMapper.map(socio, SocioDto.class);
	}

	/**
	 * Convierte DTO a entidad
	 * 
	 * @param socioDto
	 * @return
	 */
	private Socio mapearEntidad(SocioDto socioDto) {
		return modelMapper.map(socioDto, Socio.class);
	}


}
