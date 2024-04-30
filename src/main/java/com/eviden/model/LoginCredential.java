package com.eviden.model;

import java.util.Objects;

public class LoginCredential {

	private String dni;
	
	private String password;

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginCredential other = (LoginCredential) obj;
		return Objects.equals(dni, other.dni);
	}
	
	
}
