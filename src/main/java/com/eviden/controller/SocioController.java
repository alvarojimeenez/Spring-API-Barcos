package com.eviden.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.dto.SocioDto;
import com.eviden.dto.TokenDto;
import com.eviden.exception.ExceptionCredentialNotValid;
import com.eviden.model.LoginCredential;
import com.eviden.model.Socio;
import com.eviden.service.SocioService;
import com.eviden.utility.TokenUtils;

@RestController
public class SocioController {

	@Autowired
	private SocioService socioService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Controlador que devuelve una lista de socios
	 * @return
	 */
	@GetMapping("/socios")
	public List<SocioDto> getSocios() {
		List<SocioDto> listSocios = socioService.getSocios();

		return listSocios;
	}

	/**
	 * Controlador que devuelve un socio por su id
	 * @param id
	 * @return
	 */
	@GetMapping("socio/{id}")
	public ResponseEntity<?> getSocio(@PathVariable String id) {
		SocioDto socioDto = socioService.getSocioById(id);

		if (socioDto == null) {

			return ResponseEntity.badRequest().build();
		} else {

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(socioDto);
		}
	}

	/**
	 * Permitirá añadir un nuevo socio
	 * @param socioDto
	 * @return
	 */
	@PostMapping("socio")
	public ResponseEntity<?> addSocio(@RequestBody SocioDto socioDto) {
		SocioDto add = socioService.addSocio(socioDto);
		
		if (add == null) {
			return ResponseEntity.badRequest().build();
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(socioDto);
		}
	}
	
	/**
	 * Permitirá editar un socio a través de su id
	 * @param socioDto
	 * @param id
	 * @return
	 */
	@PutMapping("socio/{id}")
	public ResponseEntity<?> editSocio(@RequestBody SocioDto socioDto, @PathVariable String id) {
		SocioDto edit = socioService.editSocio(socioDto, id);
		
		if (edit == null) {
			return ResponseEntity.badRequest().build();
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(edit);
		}
	}

	/**
	 * Controlador que permite borrar un socio
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("socio/{id}")
	public ResponseEntity<Map<String, String>> deleteSocio(@PathVariable String id) {
		socioService.deleteSocio(id);
		Map<String, String> messageDeleted = new HashMap<>();
		messageDeleted.put("message", "Socio eliminado");
		return new ResponseEntity<>(messageDeleted, HttpStatus.OK);
	}
	
	
	/**
	 * Devuelve un socio por su email
	 * @param email
	 * @return
	 */
	@GetMapping("socioByEmail/{email}")
	public ResponseEntity<?> getSocioByEmail(@PathVariable String email) {
		SocioDto socioDto = socioService.getSocioByEmail(email);

		if (socioDto == null) {

			return ResponseEntity.badRequest().build();
		} else {

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(socioDto);
		}
	}
	
	
	/**
	 * Devuelve un socio por su dni
	 * @param dni
	 * @return
	 */
	@GetMapping("socioByDni/{dni}")
	public ResponseEntity<?> getSocioByDni(@PathVariable String dni) {
		SocioDto socioDto = socioService.getSocioByDni(dni);

		if (socioDto == null) {

			return ResponseEntity.badRequest().build();
		} else {

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(socioDto);
		}
	}
	
	/**
	 * Devuelve los socios que son patrones
	 * @return
	 */
	@GetMapping("/socios/patrones")
	public List<SocioDto> getSociosPatrones() {
		List<SocioDto> listSociosPatrones = socioService.getSociosPatrones();

		return listSociosPatrones;
	}
	
	/**
	 * Endpoint para iniciar sesión. Nos devolverá el token de autenticación
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginCredential loginRequest) {

		Authentication authentication;
		//Si el dni y el password que le paso son los adecuados me 
		// devuele un autentication. Si no lo encuentra, lanza una exception
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getDni() , loginRequest.getPassword()));
	
		} catch (Exception e) {
			throw new ExceptionCredentialNotValid(e.getMessage());
		}
		
		Socio socio = (Socio)authentication.getPrincipal();
		String jwt = TokenUtils.generateToken(loginRequest.getDni(), socio.getEmail(), socio.getName(), socio.getRole(), socio.getIdSocio());
		
		TokenDto dtoToken = new TokenDto(jwt);
		
		return ResponseEntity.ok(dtoToken);
	}
	
}
