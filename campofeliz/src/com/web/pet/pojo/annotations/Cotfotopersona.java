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
import javax.persistence.Transient;

@Entity
@Table(name = "cotfotopersona")
public class Cotfotopersona implements java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5699866582026667408L;
	private int idfotopersona;
	private Cotpersona cotpersona;
	private Setestado setestado;
	private Setusuario setusuario;
	private Date fecharegistro;
	private Date fechamodificacion;
	private Integer mostrar;
	private String iplog;
	private String ruta;
	private String nombrearchivo;
	private String descripcion;
	private byte[] objeto;

	
	public Cotfotopersona() {
	}

	public Cotfotopersona(int idfotopersona, Cotpersona cotpersona, Date fecharegistro) {
		this.idfotopersona = idfotopersona;
		this.cotpersona = cotpersona;
		this.fecharegistro = fecharegistro;
	}

	public Cotfotopersona(int idfotopersona, Cotpersona cotpersona, Setestado setestado,
						  Setusuario setusuario, Date fecharegistro, Date fechamodificacion, Integer mostrar,
						  String iplog, String ruta, String nombrearchivo,
						  String descripcion) {
		this.idfotopersona = idfotopersona;
		this.cotpersona = cotpersona;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.mostrar = mostrar;
		this.iplog = iplog;
		this.ruta = ruta;
		this.nombrearchivo = nombrearchivo;
		this.descripcion = descripcion;
	}

	@Id
	@Column(name = "idfotopersona", unique = true, nullable = false)
	public int getIdfotopersona() {
		return this.idfotopersona;
	}

	public void setIdfotopersona(int idfotopersona) {
		this.idfotopersona = idfotopersona;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechamodificacion",  length = 29)
	public Date getFechamodificacion() {
		return this.fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
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

	@Transient
	public byte[] getObjeto() {
		return this.objeto;
	}

	public void setObjeto(byte[] objeto) {
		this.objeto = objeto;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Cotfotopersona cotfotopersona = (Cotfotopersona)super.clone();
		return cotfotopersona;
	}
	
	public Cotfotopersona clonar() throws Exception {
		return (Cotfotopersona)this.clone();
	}

}
