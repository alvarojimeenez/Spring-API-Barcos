package com.eviden.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.dto.BarcoDto;
import com.eviden.service.BarcoService;
import com.eviden.service.SalidaService;
import com.eviden.service.SocioService;

@RestController
public class BarcoController {
	
	@Autowired
	private BarcoService barcoService;
	
	@Autowired
	private SocioService socioService;
	
	@Autowired
	private SalidaService salidaService;
	
	
	/**
	 * Controlador que carga la lista de barcos
	 * @param model
	 * @return
	 */
	@GetMapping("barcos")
	public List<BarcoDto> getBarcos() {
		List<BarcoDto> listBarcos = barcoService.getBarcos();

		return listBarcos;
	}
	
	/**
	 * Devuelve un barco buscado por su id
	 * @param id
	 * @return
	 */
	@GetMapping("barco/{id}")
	public ResponseEntity<?> getBarco(@PathVariable String id) {
		BarcoDto barcoDto = barcoService.getBarcoById(id);

		if (barcoDto == null) {

			return ResponseEntity.badRequest().build();
		} else {

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(barcoDto);
		}
	}
	
	
	/**
	 * Controlador que permite añadir un nuevo barco
	 * @param barcoDto
	 * @return
	 */
	@PostMapping("barco")
	public ResponseEntity<?> addBarco(@RequestBody BarcoDto barcoDto) {
		BarcoDto add = barcoService.addBarco(barcoDto);
		
		if (add == null) {
			return ResponseEntity.badRequest().build();
		}else {
			
			return ResponseEntity.status(HttpStatus.CREATED).body(add);
		}
		
	}
	

	
	/**
	 * Controlador que permite editar un barco a través de su id
	 * @param id
	 * @param barcoDto
	 * @return
	 */
	@PutMapping("barco/{id}")
	public ResponseEntity<?> editBarco(@PathVariable String id, @RequestBody BarcoDto barcoDto) {
		BarcoDto edit = barcoService.editBarco(barcoDto, id);
		
		if (edit==null) {
			return ResponseEntity.badRequest().build();
		}else {
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(edit);
		}
		
	}
	
	
	/**
	 * Controlador que permite borrar un barco
	 * @param id
	 * @return
	 */
	@DeleteMapping("barco/{id}")
	public ResponseEntity<Map<String, String>> deleteBarco(@PathVariable String id) {
		barcoService.deleteBarco(id);
		Map<String, String> messageDeleted = new HashMap<>();
		messageDeleted.put("message", "Barco borrado");
		
		return new ResponseEntity<>(messageDeleted, HttpStatus.OK);
				
	}
	
	/**
	 * Devuelve un barco buscado por su matricula
	 * @param matricula
	 * @return
	 */
	@GetMapping("barcoByMatricula/{matricula}")
	public ResponseEntity<?> getBarcoByMatricula(@PathVariable String matricula) {
		BarcoDto barcoDto = barcoService.getBarcoByMatricula(matricula);

		if (barcoDto == null) {

			return ResponseEntity.badRequest().build();
		} else {

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(barcoDto);
		}
	}
	
	/**
	 * Devuelve un barco buscado por su numero de amarre
	 * @param numAmarre
	 * @return
	 */
	@GetMapping("barcoByNumAmarre/{numAmarre}")
	public ResponseEntity<?> getBarcoByNumAmarre(@PathVariable Integer numAmarre) {
		BarcoDto barcoDto = barcoService.getBarcoByNumAmarre(numAmarre);

		if (barcoDto == null) {

			return ResponseEntity.badRequest().build();
		} else {

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(barcoDto);
		}
	}
	
	
}
