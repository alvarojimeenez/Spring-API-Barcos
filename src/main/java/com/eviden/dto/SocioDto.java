package com.eviden.dto;

import java.util.List;

import com.eviden.model.Barco;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

public class SocioDto {

	private Integer idSocio;
	
	private String dni;
	
	private String name;
	
	private String lastName;
	
	private String password;
	
	private String address;
	
	private String role;
	
	private String phone;
	
	private String email;
	
	private List<BarcoDto> listBarcos;

	public SocioDto() {
		super();
	}
	
	public SocioDto(Integer idSocio, String dni, String name, String lastName, String address, String phone, String email) {
		super();
		this.idSocio = idSocio;
		this.dni = dni;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}



	public SocioDto(String dni, String name, String lastName, String address, String phone, String email, String password) {
		this.dni = dni;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.password = password;
	}



	public Integer getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<BarcoDto> getListBarcos() {
		return listBarcos;
	}

	public void setListBarcos(List<BarcoDto> listBarcos) {
		this.listBarcos = listBarcos;
	}
	
	
}
