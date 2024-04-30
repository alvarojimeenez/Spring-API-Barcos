package com.eviden.dto;

import java.util.List;

import com.eviden.model.Salida;
import com.eviden.model.Socio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class BarcoDto {

	private Integer idBarco;
	
	private String matricula;
	
	private String nombreBarco;
	
	private Integer numAmarre;
	
	private Double cuota;
	
	private Integer idSocio;
	
	private List<Salida> listaSalidas;

	public BarcoDto() {
		super();
	}

	public BarcoDto(String matricula, String nombreBarco, Integer numAmarre, Double cuota, Integer idSocio) {
		this.matricula = matricula;
		this.nombreBarco = nombreBarco;
		this.numAmarre = numAmarre;
		this.cuota = cuota;
		this.idSocio = idSocio;
	}

	public Integer getIdBarco() {
		return idBarco;
	}

	public void setIdBarco(Integer idBarco) {
		this.idBarco = idBarco;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombreBarco() {
		return nombreBarco;
	}

	public void setNombreBarco(String nombreBarco) {
		this.nombreBarco = nombreBarco;
	}

	public Integer getNumAmarre() {
		return numAmarre;
	}

	public void setNumAmarre(Integer numAmarre) {
		this.numAmarre = numAmarre;
	}

	public Double getCuota() {
		return cuota;
	}

	public void setCuota(Double cuota) {
		this.cuota = cuota;
	}

	public Integer getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}

	public List<Salida> getListaSalidas() {
		return listaSalidas;
	}

	public void setListaSalidas(List<Salida> listaSalidas) {
		this.listaSalidas = listaSalidas;
	}
	
}
