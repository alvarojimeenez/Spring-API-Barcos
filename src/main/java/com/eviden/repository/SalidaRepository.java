package com.eviden.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.model.Barco;
import com.eviden.model.Salida;

public interface SalidaRepository extends JpaRepository<Salida, Integer>{
	
	
	/**
	 * Metodo para buscar todas la salidas de un barco
	 * @param barco
	 * @return
	 */
	List<Salida> findByBarco(Barco barco);
	
	
	List<Salida> findByDestinoContaining(String destino);
}