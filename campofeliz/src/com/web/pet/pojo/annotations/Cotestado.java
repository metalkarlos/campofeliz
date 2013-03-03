package com.web.pet.pojo.annotations;

// Generated 26/07/2012 03:22:37 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Cotestado generated by hbm2java
 */
@Entity
@Table(name = "cotestado", schema = "comun")
public class Cotestado implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7896543052851307361L;
	private int idestado;
	private Setusuario setusuario;
	private String nombre;
	private String descripcion;
	private Date fecharegistro;
	private String iplog;
//	private Set<Cottiposervicio> cottiposervicios = new HashSet<Cottiposervicio>(0);
//	private Set<Cotservicio> cotservicios = new HashSet<Cotservicio>(0);
//	private Set<Cotevento> coteventos = new HashSet<Cotevento>(0);
//	private Set<Cotpersona> cotpersonas = new HashSet<Cotpersona>(0);
//	private Set<Cotgrupodocumento> cotgrupodocumentos = new HashSet<Cotgrupodocumento>(0);
//	private Set<Cotcolor> cotcolors = new HashSet<Cotcolor>(0);
//	private Set<Cottipovacuna> cottipovacunas = new HashSet<Cottipovacuna>(0);
//	private Set<Cottipoidentificacion> cottipoidentificacions = new HashSet<Cottipoidentificacion>(0);
//	private Set<Cotdocumento> cotdocumentos = new HashSet<Cotdocumento>(0);
//	private Set<Cottipodocumento> cottipodocumentos = new HashSet<Cottipodocumento>(0);
//	private Set<Cotperiodotiempo> cotperiodotiempos = new HashSet<Cotperiodotiempo>(0);
//	private Set<Cotlugar> cotlugars = new HashSet<Cotlugar>(0);

	public Cotestado() {
	}

	public Cotestado(int idestado, String nombre, Date fecharegistro) {
		this.idestado = idestado;
		this.nombre = nombre;
		this.fecharegistro = fecharegistro;
	}

	public Cotestado(int idestado, Setusuario setusuario, String nombre,
			String descripcion, Date fecharegistro, String iplog/*, Set<Cottiposervicio> cottiposervicios, Set<Cotservicio> cotservicios,
			Set<Cotevento> coteventos, Set<Cotlugar> cotlugars, Set<Cotpersona> cotpersonas, Set<Cotgrupodocumento> cotgrupodocumentos,
			Set<Cotcolor> cotcolors, Set<Cottipovacuna> cottipovacunas, Set<Cottipoidentificacion> cottipoidentificacions,
			Set<Cotdocumento> cotdocumentos, Set<Cottipodocumento> cottipodocumentos, Set<Cotperiodotiempo> cotperiodotiempos*/) {
		this.idestado = idestado;
		this.setusuario = setusuario;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
//		this.cottiposervicios = cottiposervicios;
//		this.cotservicios = cotservicios;
//		this.coteventos = coteventos;
//		this.cotlugars = cotlugars;
//		this.cotpersonas = cotpersonas;
//		this.cotgrupodocumentos = cotgrupodocumentos;
//		this.cotcolors = cotcolors;
//		this.cottipovacunas = cottipovacunas;
//		this.cottipoidentificacions = cottipoidentificacions;
//		this.cotdocumentos = cotdocumentos;
//		this.cottipodocumentos = cottipodocumentos;
//		this.cotperiodotiempos = cotperiodotiempos;
	}

	@Id
	@Column(name = "idestado", unique = true, nullable = false)
	public int getIdestado() {
		return this.idestado;
	}

	public void setIdestado(int idestado) {
		this.idestado = idestado;
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
/*	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cottiposervicio.class)
	public Set<Cottiposervicio> getCottiposervicios() {
		return this.cottiposervicios;
	}

	public void setCottiposervicios(Set<Cottiposervicio> cottiposervicios) {
		this.cottiposervicios = cottiposervicios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cotservicio.class)
	public Set<Cotservicio> getCotservicios() {
		return this.cotservicios;
	}

	public void setCotservicios(Set<Cotservicio> cotservicios) {
		this.cotservicios = cotservicios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cotevento.class)
	public Set<Cotevento> getCoteventos() {
		return this.coteventos;
	}

	public void setCoteventos(Set<Cotevento> coteventos) {
		this.coteventos = coteventos;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Cotlugar.class)
	public Set<Cotlugar> getCotlugars() {
		return this.cotlugars;
	}

	public void setCotlugars(Set<Cotlugar> cotlugars) {
		this.cotlugars = cotlugars;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cotpersona.class)
	public Set<Cotpersona> getCotpersonas() {
		return this.cotpersonas;
	}

	public void setCotpersonas(Set<Cotpersona> cotpersonas) {
		this.cotpersonas = cotpersonas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cotgrupodocumento.class)
	public Set<Cotgrupodocumento> getCotgrupodocumentos() {
		return this.cotgrupodocumentos;
	}

	public void setCotgrupodocumentos(Set<Cotgrupodocumento> cotgrupodocumentos) {
		this.cotgrupodocumentos = cotgrupodocumentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cotcolor.class)
	public Set<Cotcolor> getCotcolors() {
		return this.cotcolors;
	}

	public void setCotcolors(Set<Cotcolor> cotcolors) {
		this.cotcolors = cotcolors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cottipovacuna.class)
	public Set<Cottipovacuna> getCottipovacunas() {
		return this.cottipovacunas;
	}

	public void setCottipovacunas(Set<Cottipovacuna> cottipovacunas) {
		this.cottipovacunas = cottipovacunas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cottipoidentificacion.class)
	public Set<Cottipoidentificacion> getCottipoidentificacions() {
		return this.cottipoidentificacions;
	}

	public void setCottipoidentificacions(Set<Cottipoidentificacion> cottipoidentificacions) {
		this.cottipoidentificacions = cottipoidentificacions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cotdocumento.class)
	public Set<Cotdocumento> getCotdocumentos() {
		return this.cotdocumentos;
	}

	public void setCotdocumentos(Set<Cotdocumento> cotdocumentos) {
		this.cotdocumentos = cotdocumentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cottipodocumento.class)
	public Set<Cottipodocumento> getCottipodocumentos() {
		return this.cottipodocumentos;
	}

	public void setCottipodocumentos(Set<Cottipodocumento> cottipodocumentos) {
		this.cottipodocumentos = cottipodocumentos;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cotestado", targetEntity = Cotperiodotiempo.class)
	public Set<Cotperiodotiempo> getCotperiodotiempos() {
		return this.cotperiodotiempos;
	}

	public void setCotperiodotiempos(Set<Cotperiodotiempo> cotperiodotiempos) {
		this.cotperiodotiempos = cotperiodotiempos;
	}
*/
}