package com.web.pet.pojo.annotations;

// Generated 29/01/2013 08:42:08 PM by Hibernate Tools 3.4.0.CR1

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
 * Cotlugar generated by hbm2java
 */
@Entity
@Table(name = "cotlugar")
public class Cotlugar implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2575663126792873637L;
	private int idlugar;
	private Cotestado cotestado;
	private Setusuario setusuario;
	private String nombre;
	private String descripcion;
	private Date fecharegistro;
	private String iplog;
	//private Set<Petordenservicio> petordenservicios = new HashSet<Petordenservicio>(0);

	public Cotlugar() {
	}

	public Cotlugar(int idlugar, String nombre, Date fecharegistro) {
		this.idlugar = idlugar;
		this.nombre = nombre;
		this.fecharegistro = fecharegistro;
	}

	public Cotlugar(int idlugar, Cotestado cotestado, Setusuario setusuario,
			String nombre, String descripcion, Date fecharegistro,
			String iplog/*, Set<Petordenservicio> petordenservicios*/) {
		this.idlugar = idlugar;
		this.cotestado = cotestado;
		this.setusuario = setusuario;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		//this.petordenservicios = petordenservicios;
	}

	@Id
	@Column(name = "idlugar", unique = true, nullable = false)
	public int getIdlugar() {
		return this.idlugar;
	}

	public void setIdlugar(int idlugar) {
		this.idlugar = idlugar;
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

	@Column(name = "nombre", nullable = false, length = 20)
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

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotlugar", targetEntity = Petordenservicio.class)
	public Set<Petordenservicio> getPetordenservicios() {
		return this.petordenservicios;
	}

	public void setPetordenservicios(Set<Petordenservicio> petordenservicios) {
		this.petordenservicios = petordenservicios;
	}*/

}
