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
 * Cotevento generated by hbm2java
 */
@Entity
@Table(name = "cotevento", schema = "comun")
public class Cotevento implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3404931533921926058L;
	private int idevento;
	private Cotestado cotestado;
	private Setusuario setusuario;
	private String titulo;
	private String descripcion;
	private Date fechadesde;
	private Date fechahasta;
	private Date fecharegistro;
	private String iplog;

	public Cotevento() {
	}

	public Cotevento(int idevento, String titulo, Date fechadesde,
			Date fechahasta, Date fecharegistro) {
		this.idevento = idevento;
		this.titulo = titulo;
		this.fechadesde = fechadesde;
		this.fechahasta = fechahasta;
		this.fecharegistro = fecharegistro;
	}

	public Cotevento(int idevento, Cotestado cotestado, Setusuario setusuario,
			String titulo, String descripcion, Date fechadesde,
			Date fechahasta, Date fecharegistro, String iplog) {
		this.idevento = idevento;
		this.cotestado = cotestado;
		this.setusuario = setusuario;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechadesde = fechadesde;
		this.fechahasta = fechahasta;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
	}

	@Id
	@Column(name = "idevento", unique = true, nullable = false)
	public int getIdevento() {
		return this.idevento;
	}

	public void setIdevento(int idevento) {
		this.idevento = idevento;
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

	@Column(name = "titulo", nullable = false, length = 100)
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name = "descripcion", length = 300)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechadesde", nullable = false, length = 29)
	public Date getFechadesde() {
		return this.fechadesde;
	}

	public void setFechadesde(Date fechadesde) {
		this.fechadesde = fechadesde;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechahasta", nullable = false, length = 29)
	public Date getFechahasta() {
		return this.fechahasta;
	}

	public void setFechahasta(Date fechahasta) {
		this.fechahasta = fechahasta;
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

}
