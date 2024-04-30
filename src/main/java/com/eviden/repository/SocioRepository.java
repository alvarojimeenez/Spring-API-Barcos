package com.eviden.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eviden.model.Socio;

public interface SocioRepository extends JpaRepository<Socio, Integer> {
	
	
	/**
	 * Metodo para buscar los socios por su email
	 * @param email
	 * @return
	 */
	List<Socio> findByEmail(String email);
	
	List<Socio> findByPhone(String phone);
	
	List<Socio> findByDni(String dni);
	
	@Query("SELECT s from Socio s where s.dni in (select distinct sa.dniPatron from Salida sa)")
	List<Socio> findAllPatrones();

}
