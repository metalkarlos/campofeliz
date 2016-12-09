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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cotpersona == null) ? 0 : cotpersona.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime
				* result
				+ ((fechamodificacion == null) ? 0 : fechamodificacion
						.hashCode());
		result = prime * result
				+ ((fecharegistro == null) ? 0 : fecharegistro.hashCode());
		result = prime * result + idfotopersona;
		result = prime * result + ((iplog == null) ? 0 : iplog.hashCode());
		result = prime * result + ((mostrar == null) ? 0 : mostrar.hashCode());
		result = prime * result
				+ ((nombrearchivo == null) ? 0 : nombrearchivo.hashCode());
		result = prime * result + ((ruta == null) ? 0 : ruta.hashCode());
		result = prime * result
				+ ((setestado == null) ? 0 : setestado.getIdestado());
		result = prime * result
				+ ((setusuario == null) ? 0 : setusuario.getIdusuario());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cotfotopersona other = (Cotfotopersona) obj;
		if (cotpersona == null) {
			if (other.cotpersona != null)
				return false;
		} else if (cotpersona.getIdpersona() != other.cotpersona.getIdpersona())
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechamodificacion == null) {
			if (other.fechamodificacion != null)
				return false;
		} else if (!fechamodificacion.equals(other.fechamodificacion))
			return false;
		if (fecharegistro == null) {
			if (other.fecharegistro != null)
				return false;
		} else if (!fecharegistro.equals(other.fecharegistro))
			return false;
		if (idfotopersona != other.idfotopersona)
			return false;
		if (iplog == null) {
			if (other.iplog != null)
				return false;
		} else if (!iplog.equals(other.iplog))
			return false;
		if (mostrar == null) {
			if (other.mostrar != null)
				return false;
		} else if (!mostrar.equals(other.mostrar))
			return false;
		if (nombrearchivo == null) {
			if (other.nombrearchivo != null)
				return false;
		} else if (!nombrearchivo.equals(other.nombrearchivo))
			return false;
		if (ruta == null) {
			if (other.ruta != null)
				return false;
		} else if (!ruta.equals(other.ruta))
			return false;
		if (setestado == null) {
			if (other.setestado != null)
				return false;
		} else if (setestado.getIdestado() != other.setestado.getIdestado())
			return false;
		if (setusuario == null) {
			if (other.setusuario != null)
				return false;
		} else if (setusuario.getIdusuario() != other.setusuario.getIdusuario())
			return false;
		return true;
	}
	
	

}
