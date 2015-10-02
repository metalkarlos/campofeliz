package com.web.pet.pojo.annotations;

// Generated 29/01/2013 08:42:08 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Petordenserviciodetalle generated by hbm2java
 */
@Entity
@Table(name = "petordenserviciodetalle")
public class Petordenserviciodetalle implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1517731967653899671L;
	private PetordenserviciodetalleId id;
	private Setestado setestado;
	private Setusuario setusuario;
	private Petservicio petservicio;
	private Petordenservicio petordenservicio;
	private Date fecharegistro;
	private String iplog;

	public Petordenserviciodetalle() {
	}

	public Petordenserviciodetalle(PetordenserviciodetalleId id,
			Petordenservicio petordenservicio, Date fecharegistro) {
		this.id = id;
		this.petordenservicio = petordenservicio;
		this.fecharegistro = fecharegistro;
	}

	public Petordenserviciodetalle(PetordenserviciodetalleId id,
			Setestado setestado, Setusuario setusuario,
			Petservicio petservicio, Petordenservicio petordenservicio,
			Date fecharegistro, String iplog) {
		this.id = id;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.petservicio = petservicio;
		this.petordenservicio = petordenservicio;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idordenservicio", column = @Column(name = "idordenservicio", nullable = false)),
			@AttributeOverride(name = "idordenserviciodetalle", column = @Column(name = "idordenserviciodetalle", nullable = false)) })
	public PetordenserviciodetalleId getId() {
		return this.id;
	}

	public void setId(PetordenserviciodetalleId id) {
		this.id = id;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idservicio", nullable = false)
	public Petservicio getPetservicio() {
		return this.petservicio;
	}

	public void setPetservicio(Petservicio petservicio) {
		this.petservicio = petservicio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idordenservicio", nullable = false, insertable = false, updatable = false)
	public Petordenservicio getPetordenservicio() {
		return this.petordenservicio;
	}

	public void setPetordenservicio(Petordenservicio petordenservicio) {
		this.petordenservicio = petordenservicio;
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
