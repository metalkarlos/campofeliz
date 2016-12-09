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
 * Setperfil generated by hbm2java
 */
@Entity
@Table(name = "setperfil")
public class Setperfil implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1164279330735203578L;
	private int idperfil;
	private Setestado setestado;
	private Setusuario setusuario;
	private String nombre;
	private String descripcion;
	private Date fecharegistro;
	private String iplog;
//	private Set<Setusuario> setusuarios = new HashSet<Setusuario>(0);

	public Setperfil() {
	}

	public Setperfil(int idperfil, Date fecharegistro) {
		this.idperfil = idperfil;
		this.fecharegistro = fecharegistro;
	}

	public Setperfil(int idperfil, Setestado setestado, Setusuario setusuario,
			String nombre, String descripcion, Date fecharegistro,
			String iplog/*, Set<Setusuario> setusuarios*/) {
		this.idperfil = idperfil;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
//		this.setusuarios = setusuarios;
	}

	@Id
	@Column(name = "idperfil", unique = true, nullable = false)
	public int getIdperfil() {
		return this.idperfil;
	}

	public void setIdperfil(int idperfil) {
		this.idperfil = idperfil;
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

	@Column(name = "nombre", length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 200)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result
				+ ((fecharegistro == null) ? 0 : fecharegistro.hashCode());
		result = prime * result + idperfil;
		result = prime * result + ((iplog == null) ? 0 : iplog.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((setestado == null) ? 0 : setestado.hashCode());
		result = prime * result
				+ ((setusuario == null) ? 0 : setusuario.hashCode());
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
		Setperfil other = (Setperfil) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fecharegistro == null) {
			if (other.fecharegistro != null)
				return false;
		} else if (!fecharegistro.equals(other.fecharegistro))
			return false;
		if (idperfil != other.idperfil)
			return false;
		if (iplog == null) {
			if (other.iplog != null)
				return false;
		} else if (!iplog.equals(other.iplog))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (setestado == null) {
			if (other.setestado != null)
				return false;
		} else if (!setestado.equals(other.setestado))
			return false;
		if (setusuario == null) {
			if (other.setusuario != null)
				return false;
		} else if (!setusuario.equals(other.setusuario))
			return false;
		return true;
	}
	
	
/*
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "setperfil", targetEntity = Setusuario.class)
	public Set<Setusuario> getSetusuarios() {
		return this.setusuarios;
	}

	public void setSetusuarios(Set<Setusuario> setusuarios) {
		this.setusuarios = setusuarios;
	}
*/
}
