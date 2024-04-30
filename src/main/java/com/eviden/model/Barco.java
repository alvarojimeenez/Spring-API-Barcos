package com.eviden.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Barcos")
public class Barco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_barco")
	private Integer idBarco;
	
	private String matricula;
	
	
	@Column(name = "nombre_barco")
	private String nombreBarco;
	
	
	@Column(name = "num_amarre")
	private Integer numAmarre;
	
	
	private Double cuota;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "id_socio")
	private Socio socio;
	
	
	@OneToMany(mappedBy = "barco", cascade = CascadeType.ALL)
	private List<Salida> listaSalidas;

	public Barco() {
		super();
	}

	public Barco(Integer idBarco, String matricula, String nombreBarco, Integer numAmarre, Double cuota) {
		super();
		this.idBarco = idBarco;
		this.matricula = matricula;
		this.nombreBarco = nombreBarco;
		this.numAmarre = numAmarre;
		this.cuota = cuota;
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

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public List<Salida> getListaSalidas() {
		return listaSalidas;
	}

	public void setListaSalidas(List<Salida> listaSalidas) {
		this.listaSalidas = listaSalidas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idBarco);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Barco other = (Barco) obj;
		return Objects.equals(idBarco, other.idBarco);
	}
	
	
	
	
}
