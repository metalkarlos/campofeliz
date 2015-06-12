package com.web.pet.pojo.annotations;

// Generated 26/07/2012 03:22:37 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Cottipoidentificacion generated by hbm2java
 */
@Entity
@Table(name = "cottipoidentificacion")
public class Cottipoidentificacion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4274430955674030976L;
	private int idtipoidentificacion;
	private Setestado setestado;
	private Setusuario setusuario;
	private String nombre;
	private String descripcion;
	private Date fecharegistro;
	private String iplog;
//	private Set<Cotpersona> cotpersonas = new HashSet<Cotpersona>(0);
//	private Set<Petmascota> petmascotas = new HashSet<Petmascota>(0);

	public Cottipoidentificacion() {
	}

	public Cottipoidentificacion(int idtipoidentificacion, String nombre,
			Date fecharegistro) {
		this.idtipoidentificacion = idtipoidentificacion;
		this.nombre = nombre;
		this.fecharegistro = fecharegistro;
	}

	public Cottipoidentificacion(int idtipoidentificacion, Setestado setestado,
			Setusuario setusuario, String nombre, String descripcion,
			Date fecharegistro, String iplog/*, Set<Cotpersona> cotpersonas, Set<Petmascota> petmascotas*/) {
		this.idtipoidentificacion = idtipoidentificacion;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
//		this.cotpersonas = cotpersonas;
//		this.petmascotas = petmascotas;
	}

	@Id
	@Column(name = "idtipoidentificacion", unique = true, nullable = false)
	public int getIdtipoidentificacion() {
		return this.idtipoidentificacion;
	}

	public void setIdtipoidentificacion(int idtipoidentificacion) {
		this.idtipoidentificacion = idtipoidentificacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idestado")
	public Setestado getSetestado() {
		return this.setestado;
	}

	public void setSetestado(Setestado setestado) {
		this.setestado = setestado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	public Setusuario getSetusuario() {
		return this.setusuario;
	}

	public void setSetusuario(Setusuario setusuario) {
		this.setusuario = setusuario;
	}

	@Column(name = "nombre", nullable = false, length = 50)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecharegistro", nullable = false, length = 29)
	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	@Column(name = "iplog", length = 20)
	public String getIplog() {
		return this.iplog;
	}

	public void setIplog(String iplog) {
		this.iplog = iplog;
	}
/*
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cottipoidentificacion", targetEntity = Cotpersona.class)
	public Set<Cotpersona> getCotpersonas() {
		return this.cotpersonas;
	}

	public void setCotpersonas(Set<Cotpersona> cotpersonas) {
		this.cotpersonas = cotpersonas;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cottipoidentificacion", targetEntity = Petmascota.class)
	public Set<Petmascota> getPetmascotas() {
		return this.petmascotas;
	}

	public void setPetmascotas(Set<Petmascota> petmascotas) {
		this.petmascotas = petmascotas;
	}
*/
}
