package com.web.pet.pojo.annotations;

import java.math.BigDecimal;

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
 * Petordenservicio generated by hbm2java
 */
@Entity
@Table(name = "petordenservicio")
public class Petordenservicio implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 4645223024729017391L;
	private PetordenservicioId id;
	private Petmascotahomenaje petmascotahomenaje;
	private Setestado setestado;
	private Cotlugar cotlugar;
	private Setusuario setusuario;
	private Date fechaentierro;
	private String dedicatoria;
	private String observacion;
	private Date fecharegistro;
	private String iplog;
	private Date fechaemision;
	private Date fechamodificacion;
	private Cotestadopago cotestadopago;
	private BigDecimal preciototal;
	private BigDecimal pagototal;

	public Petordenservicio() {
	}

	public Petordenservicio(PetordenservicioId id,
			Petmascotahomenaje petmascotahomenaje, Date fecharegistro,
			Date fechaemision) {
		this.id = id;
		this.petmascotahomenaje = petmascotahomenaje;
		this.fecharegistro = fecharegistro;
		this.fechaemision = fechaemision;
	}

	public Petordenservicio(PetordenservicioId id, Petmascotahomenaje petmascotahomenaje,
			Setestado setestado, Cotlugar cotlugar, Setusuario setusuario,
			Date fechaentierro, String dedicatoria, String observacion,
			Date fecharegistro, String iplog, Date fechaemision, Date fechamodificacion, 
			Cotestadopago cotestadopago, BigDecimal preciototal, BigDecimal pagototal) {
		this.id = id;
		this.petmascotahomenaje = petmascotahomenaje;
		this.setestado = setestado;
		this.cotlugar = cotlugar;
		this.setusuario = setusuario;
		this.fechaentierro = fechaentierro;
		this.dedicatoria = dedicatoria;
		this.observacion = observacion;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		this.fechaemision = fechaemision;
		this.fechamodificacion = fechamodificacion;
		this.cotestadopago = cotestadopago;
		this.preciototal = preciototal;
		this.pagototal = pagototal;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idordenservicio", column = @Column(name = "idordenservicio", nullable = false)),
			@AttributeOverride(name = "idanio", column = @Column(name = "idanio", nullable = false)) })
	public PetordenservicioId getId() {
		return this.id;
	}

	public void setId(PetordenservicioId id) {
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
	@JoinColumn(name = "idestado")
	public Setestado getSetestado() {
		return this.setestado;
	}

	public void setSetestado(Setestado setestado) {
		this.setestado = setestado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idlugar")
	public Cotlugar getCotlugar() {
		return this.cotlugar;
	}

	public void setCotlugar(Cotlugar cotlugar) {
		this.cotlugar = cotlugar;
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
	@Column(name = "fechaentierro", length = 19)
	public Date getFechaentierro() {
		return this.fechaentierro;
	}

	public void setFechaentierro(Date fechaentierro) {
		this.fechaentierro = fechaentierro;
	}

	@Column(name = "dedicatoria", length = 1000)
	public String getDedicatoria() {
		return this.dedicatoria;
	}

	public void setDedicatoria(String dedicatoria) {
		this.dedicatoria = dedicatoria;
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

	@Column(name = "iplog", length = 20)
	public String getIplog() {
		return this.iplog;
	}

	public void setIplog(String iplog) {
		this.iplog = iplog;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaemision", nullable = false, length = 19)
	public Date getFechaemision() {
		return this.fechaemision;
	}

	public void setFechaemision(Date fechaemision) {
		this.fechaemision = fechaemision;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "petordenservicio", targetEntity = Petordenserviciodetalle.class, cascade = CascadeType.ALL)
	public Set<Petordenserviciodetalle> getPetordenserviciodetalles() {
		return this.petordenserviciodetalles;
	}

	public void setPetordenserviciodetalles(Set<Petordenserviciodetalle> petordenserviciodetalles) {
		this.petordenserviciodetalles = petordenserviciodetalles;
	}*/
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechamodificacion",  length = 19)
	public Date getFechamodificacion() {
		return this.fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idestadopago")
	public Cotestadopago getCotestadopago() {
		return this.cotestadopago;
	}

	public void setCotestadopago(Cotestadopago cotestadopago) {
		this.cotestadopago = cotestadopago;
	}
	
	@Column(name = "preciototal", precision = 5)
	public BigDecimal getPreciototal() {
		return this.preciototal;
	}

	public void setPreciototal(BigDecimal preciototal) {
		this.preciototal = preciototal;
	}
	
	@Column(name = "pagototal", precision = 5)
	public BigDecimal getPagototal() {
		return pagototal;
	}

	public void setPagototal(BigDecimal pagototal) {
		this.pagototal = pagototal;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Petordenservicio petordenservicio = (Petordenservicio)super.clone();
		
		if(petordenservicio.getCotlugar() != null && petordenservicio.getCotlugar().getIdlugar() > 0){
			petordenservicio.setCotlugar((Cotlugar) petordenservicio.getCotlugar().clone());
		}
		
		return petordenservicio;
	}
	
	public Petordenservicio clonar() throws Exception {
		return (Petordenservicio)this.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cotlugar == null) ? 0 : cotlugar.getIdlugar());
		result = prime * result + ((dedicatoria == null) ? 0 : dedicatoria.hashCode());
		result = prime * result + ((fechaemision == null) ? 0 : fechaemision.hashCode());
		result = prime * result + ((fechaentierro == null) ? 0 : fechaentierro.hashCode());
		result = prime * result + ((fecharegistro == null) ? 0 : fecharegistro.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((iplog == null) ? 0 : iplog.hashCode());
		result = prime * result + ((observacion == null) ? 0 : observacion.hashCode());
		result = prime * result + ((petmascotahomenaje == null) ? 0 : petmascotahomenaje.getIdmascota());
		result = prime * result + ((setestado == null) ? 0 : setestado.getIdestado());
		result = prime * result + ((setusuario == null) ? 0 : setusuario.getIdusuario());
		result = prime * result + ((fechamodificacion == null) ? 0 : fechamodificacion.hashCode());
		result = prime * result + ((cotestadopago == null) ? 0 : cotestadopago.hashCode());
		result = prime * result + ((preciototal == null) ? 0 : preciototal.hashCode());
		result = prime * result + ((pagototal == null) ? 0 : pagototal.hashCode());
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
		Petordenservicio other = (Petordenservicio) obj;
		if (cotlugar == null) {
			if (other.cotlugar != null)
				return false;
		} else if (cotlugar.getIdlugar() != other.cotlugar.getIdlugar())
			return false;
		if (dedicatoria == null) {
			if (other.dedicatoria != null)
				return false;
		} else if (!dedicatoria.equals(other.dedicatoria))
			return false;
		if (fechaemision == null) {
			if (other.fechaemision != null)
				return false;
		} else if (!fechaemision.equals(other.fechaemision))
			return false;
		if (fechaentierro == null) {
			if (other.fechaentierro != null)
				return false;
		} else if (!fechaentierro.equals(other.fechaentierro))
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
		if (petmascotahomenaje == null) {
			if (other.petmascotahomenaje != null)
				return false;
		} else if (petmascotahomenaje.getIdmascota() != other.petmascotahomenaje.getIdmascota())
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
		if (fechamodificacion == null) {
			if (other.fechamodificacion != null)
				return false;
		} else if (!fechamodificacion.equals(other.fechamodificacion))
			return false;
		if (cotestadopago == null) {
			if (other.cotestadopago != null)
				return false;
		} else if (cotestadopago.getIdestadopago() != other.cotestadopago.getIdestadopago())
			return false;
		if (preciototal == null) {
			if (other.preciototal != null)
				return false;
		} else if (!preciototal.equals(other.preciototal))
			return false;
		if (pagototal == null) {
			if (other.pagototal != null)
				return false;
		} else if (!pagototal.equals(other.pagototal))
			return false;
		return true;
	}

}
