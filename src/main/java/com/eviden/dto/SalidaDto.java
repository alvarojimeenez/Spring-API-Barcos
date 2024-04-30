package com.eviden.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class SalidaDto {

private Integer idSalida;
	
	@JsonFormat(shape= Shape.STRING, pattern ="yyyy-MM-dd HH:mm", timezone = "Europe/Madrid")
	private Date horaSalida;
	
	private String destino;
	
	private String dniPatron;
	
	private Integer idBarco;

	public SalidaDto() {
		super();
	}

	public SalidaDto(Date horaSalida, String destino, String dniPatron, Integer idBarco) {
		this.horaSalida = horaSalida;
		this.destino = destino;
		this.dniPatron = dniPatron;
		this.idBarco = idBarco;
	}

	


	public SalidaDto(Integer idSalida, Date horaSalida, String destino, String dniPatron, Integer idBarco) {
		this.idSalida = idSalida;
		this.horaSalida = horaSalida;
		this.destino = destino;
		this.dniPatron = dniPatron;
		this.idBarco = idBarco;
	}

	public Integer getIdSalida() {
		return idSalida;
	}

	public void setIdSalida(Integer idSalida) {
		this.idSalida = idSalida;
	}

	public Date getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getDniPatron() {
		return dniPatron;
	}

	public void setDniPatron(String dniPatron) {
		this.dniPatron = dniPatron;
	}

	public Integer getIdBarco() {
		return idBarco;
	}

	public void setIdBarco(Integer idBarco) {
		this.idBarco = idBarco;
	}
	
}
