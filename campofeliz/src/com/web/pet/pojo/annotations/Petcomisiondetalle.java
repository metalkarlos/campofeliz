package com.web.pet.pojo.annotations;

// Generated 29/01/2013 08:42:08 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
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
 * Petcomisiondetalle generated by hbm2java
 */
@Entity
@Table(name = "petcomisiondetalle")
public class Petcomisiondetalle implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 744845729626416132L;
	private PetcomisiondetalleId id;
	private Petmascotahomenaje petmascotahomenaje;
	private Petcomision petcomision;
	private Petservicio petservicio;
	private BigDecimal valorservicio;
	private BigDecimal porcentajecomision;
	private BigDecimal valorcomision;
	private Date fecharegistro;
	private String iplog;
	private Integer idusuario;
	private Integer idestado;

	public Petcomisiondetalle() {
	}

	public Petcomisiondetalle(PetcomisiondetalleId id, Petmascotahomenaje petmascotahomenaje,
			Petcomision petcomision, BigDecimal valorservicio,
			BigDecimal porcentajecomision, BigDecimal valorcomision,
			Date fecharegistro) {
		this.id = id;
		this.petmascotahomenaje = petmascotahomenaje;
		this.petcomision = petcomision;
		this.valorservicio = valorservicio;
		this.porcentajecomision = porcentajecomision;
		this.valorcomision = valorcomision;
		this.fecharegistro = fecharegistro;
	}

	public Petcomisiondetalle(PetcomisiondetalleId id, Petmascotahomenaje petmascotahomenaje,
			Petcomision petcomision, Petservicio petservicio,
			BigDecimal valorservicio, BigDecimal porcentajecomision,
			BigDecimal valorcomision, Date fecharegistro, String iplog,
			Integer idusuario, Integer idestado) {
		this.id = id;
		this.petmascotahomenaje = petmascotahomenaje;
		this.petcomision = petcomision;
		this.petservicio = petservicio;
		this.valorservicio = valorservicio;
		this.porcentajecomision = porcentajecomision;
		this.valorcomision = valorcomision;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		this.idusuario = idusuario;
		this.idestado = idestado;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idcomision", column = @Column(name = "idcomision", nullable = false)),
			@AttributeOverride(name = "idcomisiondetalle", column = @Column(name = "idcomisiondetalle", nullable = false)) })
	public PetcomisiondetalleId getId() {
		return this.id;
	}

	public void setId(PetcomisiondetalleId id) {
		this.id = id;
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
	@JoinColumn(name = "idcomision", nullable = false, insertable = false, updatable = false)
	public Petcomision getPetcomision() {
		return this.petcomision;
	}

	public void setPetcomision(Petcomision petcomision) {
		this.petcomision = petcomision;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idservicio", nullable = false)
	public Petservicio getPetservicio() {
		return this.petservicio;
	}

	public void setPetservicio(Petservicio petservicio) {
		this.petservicio = petservicio;
	}

	@Column(name = "valorservicio", nullable = false, precision = 5)
	public BigDecimal getValorservicio() {
		return this.valorservicio;
	}

	public void setValorservicio(BigDecimal valorservicio) {
		this.valorservicio = valorservicio;
	}

	@Column(name = "porcentajecomision", nullable = false, precision = 5)
	public BigDecimal getPorcentajecomision() {
		return this.porcentajecomision;
	}

	public void setPorcentajecomision(BigDecimal porcentajecomision) {
		this.porcentajecomision = porcentajecomision;
	}

	@Column(name = "valorcomision", nullable = false, precision = 5)
	public BigDecimal getValorcomision() {
		return this.valorcomision;
	}

	public void setValorcomision(BigDecimal valorcomision) {
		this.valorcomision = valorcomision;
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

	@Column(name = "idusuario")
	public Integer getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	@Column(name = "idestado")
	public Integer getIdestado() {
		return this.idestado;
	}

	public void setIdestado(Integer idestado) {
		this.idestado = idestado;
	}

}
