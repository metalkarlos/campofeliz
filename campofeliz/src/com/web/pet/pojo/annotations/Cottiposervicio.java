package com.web.pet.pojo.annotations;

// Generated 26/01/2013 09:01:06 AM by Hibernate Tools 3.4.0.CR1

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
 * Cottiposervicio generated by hbm2java
 */
@Entity
@Table(name = "cottiposervicio", schema = "comun")
public class Cottiposervicio implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3733571613143797261L;
	private int idtiposervicio;
	private Cotestado cotestado;
	private Setusuario setusuario;
	private String nombre;
	private String descripcion;
	private Date fecharegistro;
	private String iplog;
//	private Set<Cotservicio> cotservicios = new HashSet<Cotservicio>(0);

	public Cottiposervicio() {
	}

	public Cottiposervicio(int idtiposervicio, String nombre, Date fecharegistro) {
		this.idtiposervicio = idtiposervicio;
		this.nombre = nombre;
		this.fecharegistro = fecharegistro;
	}

	public Cottiposervicio(int idtiposervicio, Cotestado cotestado,
			Setusuario setusuario, String nombre, String descripcion,
			Date fecharegistro, String iplog/*, Set<Cotservicio> cotservicios*/) {
		this.idtiposervicio = idtiposervicio;
		this.cotestado = cotestado;
		this.setusuario = setusuario;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		//this.cotservicios = cotservicios;
	}

	@Id
	@Column(name = "idtiposervicio", unique = true, nullable = false)
	public int getIdtiposervicio() {
		return this.idtiposervicio;
	}

	public void setIdtiposervicio(int idtiposervicio) {
		this.idtiposervicio = idtiposervicio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idestado")
	public Cotestado getCotestado() {
		return this.cotestado;
	}

	public void setCotestado(Cotestado cotestado) {
		this.cotestado = cotestado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	public Setusuario getSetusuario() {
		return this.setusuario;
	}

	public void setSetusuario(Setusuario setusuario) {
		this.setusuario = setusuario;
	}

	@Column(name = "nombre", nullable = false, length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 200)
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cottiposervicio", targetEntity = Cotservicio.class)
	public Set<Cotservicio> getCotservicios() {
		return this.cotservicios;
	}

	public void setCotservicios(Set<Cotservicio> cotservicios) {
		this.cotservicios = cotservicios;
	}
*/
}
