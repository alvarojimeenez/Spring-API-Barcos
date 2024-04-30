package com.eviden.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eviden.dto.BarcoDto;
import com.eviden.dto.SalidaDto;
import com.eviden.exception.ExceptionPageNotFound;
import com.eviden.model.Barco;
import com.eviden.model.Salida;
import com.eviden.service.BarcoService;
import com.eviden.service.SalidaService;

@RestController
public class SalidaController {

	/**
	 * Inyección de dependencias
	 */
	
	@Autowired
	private SalidaService salidaService;

	@Autowired
	private BarcoService barcoService;

	/**
	 * Paginado de la lista de salidas con campo a ordenar, orden ascendente y
	 * descendente y tamaño de la página
	 * 
	 * @param model
	 * @param pagNum
	 * @param pageSize
	 * @param sortField
	 * @param order
	 * @return
	 */
	@GetMapping("listSalidas")
	public ResponseEntity<Page<SalidaDto>> getSalidas(@RequestParam("pageNumber") Optional<String> pagNum,
			@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("sortField") Optional<String> sortField,
			@RequestParam("order") Optional<Boolean> order) {

		Page<SalidaDto> listSalidas = salidaService.getSalidas(pagNum.orElse("1"), pageSize.orElse(9),
				sortField.orElse("idSalida"), order.orElse(true));

		if (listSalidas == null) {
			return ResponseEntity.notFound().build();

		} else if (listSalidas.getContent().size() == 0) {
			throw new ExceptionPageNotFound("Página no encontrada");

		} else {
			return ResponseEntity.ok(listSalidas);
		}

	}
	
	/**
	 * Nos devuelve todas las salidas
	 * @return
	 */
	@GetMapping("salidas")
	public List<SalidaDto> getAllSalidas() {
		return salidaService.listSalidas();
	}

	/**
	 * Devolverá una salida buscada por su id
	 * @param id
	 * @return
	 */
	@GetMapping("salida/{id}")
	public ResponseEntity<?> getSalida(@PathVariable String id) {
		SalidaDto salidaDto = salidaService.getSalidaById(id);

		if (salidaDto == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(salidaDto);
		}
	}

	/**
	 * Permitirá añadir una salida nueva
	 * @param salidaDto
	 * @return
	 */
	@PostMapping("salida")
	public ResponseEntity<?> addSalida(@RequestBody SalidaDto salidaDto) {
		SalidaDto add = salidaService.addSalida(salidaDto);

		if (add == null) {
			return ResponseEntity.badRequest().build();

		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(salidaDto);

		}
	}

	/**
	 * Permitirá editar una salida a través de su id
	 * @param salidaDto
	 * @param id
	 * @return
	 */
	@PutMapping("salida/{id}")
	public ResponseEntity<?> editSalida(@RequestBody SalidaDto salidaDto, @PathVariable String id) {
		SalidaDto edit = salidaService.editSalida(salidaDto, id);

		if (edit == null) {

			return ResponseEntity.badRequest().build();
		} else {

			return ResponseEntity.status(HttpStatus.CREATED).body(salidaDto);
		}
	}

	/**
	 * Controlador que borra una salida de la base de datos
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("salida/{id}")
	public ResponseEntity<Map<String, String>> deleteSalida(@PathVariable String id) {
		salidaService.deleteSalida(id);
		Map<String, String> messageDeleted = new HashMap<>();
		messageDeleted.put("message", "Salida eliminada");
		return new ResponseEntity<>(messageDeleted,HttpStatus.OK);
	}

	@GetMapping("salidas/barco/{id}")
	public List<SalidaDto> listSalidasBarco(@PathVariable String id) {

		BarcoDto barco = barcoService.getBarcoById(id);
		Barco b = new Barco(barco.getIdBarco(), barco.getMatricula(), barco.getNombreBarco(), barco.getNumAmarre(), barco.getCuota());


		return salidaService.listSalidasByBarco(b);

		
	}
	
	@GetMapping("salidas/{destino}")
	public List<SalidaDto> getSalidasByDestino(@PathVariable String destino) {
		List<SalidaDto> salidaDto = salidaService.getSalidaByDestino(destino);

		return salidaDto;
	}

}
