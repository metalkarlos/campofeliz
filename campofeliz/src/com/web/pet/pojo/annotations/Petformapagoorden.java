package com.web.pet.pojo.annotations;
// Generated 06/05/2017 20:55:18 by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Petformapagoorden generated by hbm2java
 */
@Entity
@Table(name = "petformapagoorden", catalog = "campofelizweb")
public class Petformapagoorden implements java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -120201181117092924L;
	private PetformapagoordenId id;
	private Cotformapago cotformapago;
	private Petordenservicio petordenservicio;
	private Setestado setestado;
	private Setusuario setusuario;
	private BigDecimal valor;
	private String observacion;
	private Date fecharegistro;
	private Date fechamodificacion;
	private String iplog;
	private String codigoUnico;

	public Petformapagoorden() {
	}

	public Petformapagoorden(PetformapagoordenId id, Cotformapago cotformapago, Petordenservicio petordenservicio,
			BigDecimal valor, Date fecharegistro) {
		this.id = id;
		this.cotformapago = cotformapago;
		this.petordenservicio = petordenservicio;
		this.valor = valor;
		this.fecharegistro = fecharegistro;
	}

	public Petformapagoorden(PetformapagoordenId id, Cotformapago cotformapago, Petordenservicio petordenservicio,
			Setestado setestado, Setusuario setusuario, BigDecimal valor, String observacion, Date fecharegistro,
			Date fechamodificacion, String iplog, String codigoUnico) {
		this.id = id;
		this.cotformapago = cotformapago;
		this.petordenservicio = petordenservicio;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.valor = valor;
		this.observacion = observacion;
		this.fecharegistro = fecharegistro;
		this.fechamodificacion = fechamodificacion;
		this.iplog = iplog;
		this.codigoUnico = codigoUnico;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "idordenservicio", column = @Column(name = "idordenservicio", nullable = false) ),
			@AttributeOverride(name = "idanio", column = @Column(name = "idanio", nullable = false) ),
			@AttributeOverride(name = "idformapagoorden", column = @Column(name = "idformapagoorden", nullable = false) ) })
	public PetformapagoordenId getId() {
		return this.id;
	}

	public void setId(PetformapagoordenId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idformapago", nullable = false)
	public Cotformapago getCotformapago() {
		return this.cotformapago;
	}

	public void setCotformapago(Cotformapago cotformapago) {
		this.cotformapago = cotformapago;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "idordenservicio", referencedColumnName = "idordenservicio", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "idanio", referencedColumnName = "idanio", nullable = false, insertable = false, updatable = false) })
	public Petordenservicio getPetordenservicio() {
		return this.petordenservicio;
	}

	public void setPetordenservicio(Petordenservicio petordenservicio) {
		this.petordenservicio = petordenservicio;
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

	@Column(name = "valor", nullable = false, precision = 5)
	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Column(name = "observacion", length = 300)
	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecharegistro", nullable = false, length = 19)
	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechamodificacion", length = 19)
	public Date getFechamodificacion() {
		return this.fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	@Column(name = "iplog", length = 20)
	public String getIplog() {
		return this.iplog;
	}

	public void setIplog(String iplog) {
		this.iplog = iplog;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Petformapagoorden petformapagoorden = (Petformapagoorden)super.clone();
		return petformapagoorden;
	}
	
	public Petformapagoorden clonar() throws Exception {
		return (Petformapagoorden)this.clone();
	}
	
	@Transient
	public String getCodigoUnico() {
		return codigoUnico;
	}

	public void setCodigoUnico(String codigoUnico) {
		this.codigoUnico = codigoUnico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cotformapago == null) ? 0 : cotformapago.getIdformapago());
		result = prime * result + ((fechamodificacion == null) ? 0 : fechamodificacion.hashCode());
		result = prime * result + ((fecharegistro == null) ? 0 : fecharegistro.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((iplog == null) ? 0 : iplog.hashCode());
		result = prime * result + ((observacion == null) ? 0 : observacion.hashCode());
		//result = prime * result + ((petordenservicio == null) ? 0 : petordenservicio.hashCode());
		result = prime * result + ((setestado == null) ? 0 : setestado.getIdestado());
		result = prime * result + ((setusuario == null) ? 0 : setusuario.getIdusuario());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		result = prime * result + ((codigoUnico == null) ? 0 : codigoUnico.hashCode());
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
		Petformapagoorden other = (Petformapagoorden) obj;
		if (cotformapago == null) {
			if (other.cotformapago != null)
				return false;
		} else if (cotformapago.getIdformapago() != other.cotformapago.getIdformapago())
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (iplog == null) {
			if (other.iplog != null)
				return false;
		} else if (!iplog.equals(other.iplog))
			return false;
		if (observacion == null) {
			if (other.observacion != null)
				return false;
		} else if (!observacion.equals(other.observacion))
			return false;
		/*if (petordenservicio == null) {
			if (other.petordenservicio != null)
				return false;
		} else if (!petordenservicio.equals(other.petordenservicio))
			return false;*/
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
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		if (codigoUnico == null) {
			if (other.codigoUnico != null)
				return false;
		} else if (!codigoUnico.equals(other.codigoUnico))
			return false;
		return true;
	}

}