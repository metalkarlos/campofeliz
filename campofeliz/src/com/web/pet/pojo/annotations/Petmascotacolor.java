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
 * Petmascotacolor generated by hbm2java
 */
@Entity
@Table(name = "petmascotacolor")
public class Petmascotacolor implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7967921817126534849L;
	private int idmascotacolor;
	private Petmascotahomenaje petmascotahomenaje;
	private Setestado setestado;
	private Cotcolor cotcolor;
	private Setusuario setusuario;
	private Date fecharegistro;
	private String iplog;
	private Date fechamodificacion;

	public Petmascotacolor() {
	}

	public Petmascotacolor(int idmascotacolor, Petmascotahomenaje petmascotahomenaje,
			Cotcolor cotcolor, Date fecharegistro) {
		this.idmascotacolor = idmascotacolor;
		this.petmascotahomenaje = petmascotahomenaje;
		this.cotcolor = cotcolor;
		this.fecharegistro = fecharegistro;
	}

	public Petmascotacolor(int idmascotacolor, Petmascotahomenaje petmascotahomenaje,
			Setestado setestado, Cotcolor cotcolor, Setusuario setusuario,
			Date fecharegistro, String iplog,Date fechamodificacion) {
		this.idmascotacolor = idmascotacolor;
		this.petmascotahomenaje = petmascotahomenaje;
		this.setestado = setestado;
		this.cotcolor = cotcolor;
		this.setusuario = setusuario;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		this.fechamodificacion = fechamodificacion;
	}

	@Id
	@Column(name = "idmascotacolor", unique = true, nullable = false)
	public int getIdmascotacolor() {
		return this.idmascotacolor;
	}

	public void setIdmascotacolor(int idmascotacolor) {
		this.idmascotacolor = idmascotacolor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmascota", nullable = false)
	public Petmascotahomenaje getPetmascotahomenaje() {
		return this.petmascotahomenaje;
	}

	public void setPetmascotahomenaje(Petmascotahomenaje petmascotahomenaje) {
		this.petmascotahomenaje = petmascotahomenaje;
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
	@JoinColumn(name = "idcolor", nullable = false)
	public Cotcolor getCotcolor() {
		return this.cotcolor;
	}

	public void setCotcolor(Cotcolor cotcolor) {
		this.cotcolor = cotcolor;
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

	@Column(name = "iplog", length = 20)
	public String getIplog() {
		return this.iplog;
	}

	public void setIplog(String iplog) {
		this.iplog = iplog;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechamodificacion", length = 29)
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

}
