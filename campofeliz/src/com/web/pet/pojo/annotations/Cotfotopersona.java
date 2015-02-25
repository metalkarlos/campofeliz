package com.web.pet.pojo.annotations;

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

@Entity
@Table(name = "cotfotopersona")
public class Cotfotopersona implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9184370414219295790L;
	private int idfoto;
	private Cotpersona cotpersona;
	private Setestado setestado;
	private Setusuario setusuario;
	private Date fecharegistro;
	private Integer mostrar;
	private String iplog;
	private byte[] objeto;
	private String ruta;
	private String nombrearchivo;
	private String descripcion;

	
	public Cotfotopersona() {
	}

	public Cotfotopersona(int idfoto, Cotpersona cotpersona, Date fecharegistro) {
		this.idfoto = idfoto;
		this.cotpersona = cotpersona;
		this.fecharegistro = fecharegistro;
	}

	public Cotfotopersona(int idfoto, Cotpersona cotpersona, Setestado setestado,
						  Setusuario setusuario, Date fecharegistro, Integer mostrar,
						  String iplog, byte[] objeto, String ruta, String nombrearchivo,
						  String descripcion) {
		this.idfoto = idfoto;
		this.cotpersona = cotpersona;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.fecharegistro = fecharegistro;
		this.mostrar = mostrar;
		this.iplog = iplog;
		this.objeto = objeto;
		this.ruta = ruta;
		this.nombrearchivo = nombrearchivo;
		this.descripcion = descripcion;
	}

	@Id
	@Column(name = "idfoto", unique = true, nullable = false)
	public int getIdfoto() {
		return this.idfoto;
	}

	public void setIdfoto(int idfoto) {
		this.idfoto = idfoto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idpersona", nullable = false)
	public Cotpersona getCotpersona() {
		return this.cotpersona;
	}

	public void setCotpersona(Cotpersona cotpersona) {
		this.cotpersona = cotpersona;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecharegistro", nullable = false, length = 29)
	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	@Column(name = "mostrar")
	public Integer getMostrar() {
		return this.mostrar;
	}

	public void setMostrar(Integer mostrar) {
		this.mostrar = mostrar;
	}

	@Column(name = "iplog", length = 20)
	public String getIplog() {
		return this.iplog;
	}

	public void setIplog(String iplog) {
		this.iplog = iplog;
	}

	@Column(name = "objeto")
	public byte[] getObjeto() {
		return this.objeto;
	}

	public void setObjeto(byte[] objeto) {
		this.objeto = objeto;
	}

	@Column(name = "ruta", length = 100)
	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Column(name = "nombrearchivo", length = 50)
	public String getNombrearchivo() {
		return this.nombrearchivo;
	}

	public void setNombrearchivo(String nombrearchivo) {
		this.nombrearchivo = nombrearchivo;
	}

	@Column(name = "descripcion", length = 200)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


}
