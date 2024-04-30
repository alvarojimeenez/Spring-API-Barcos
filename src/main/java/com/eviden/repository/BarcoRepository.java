package com.eviden.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eviden.model.Barco;
import com.eviden.model.Socio;


public interface BarcoRepository extends JpaRepository<Barco, Integer>{

	
	/**
	 * Metodo para buscar barcos por su matricula
	 * @param matricula
	 * @return
	 */
	List<Barco> findByMatricula(String matricula);
	
	List<Barco> findBySocio(Socio socio);
	
	List<Barco> findByNumAmarre(Integer numAmarre);
}
