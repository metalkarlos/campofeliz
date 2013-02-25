package com.web.pet.pojo.annotations;

// Generated 26/07/2012 03:22:37 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Setestado generated by hbm2java
 */
@Entity
@Table(name = "setestado", schema = "seguridad")
public class Setestado implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4377027854631494747L;
	private int idestado;
	private Setusuario setusuario;
	private String nombre;
	private String descripcion;
	private Date fecharegistro;
	private String iplog;
//	private Set<Setusuario> setusuarios = new HashSet<Setusuario>(0);
//	private Set<Setmenu> setmenus = new HashSet<Setmenu>(0);
//	private Set<Setperfil> setperfils = new HashSet<Setperfil>(0);

	public Setestado() {
	}

	public Setestado(int idestado, Date fecharegistro) {
		this.idestado = idestado;
		this.fecharegistro = fecharegistro;
	}

	public Setestado(int idestado, Setusuario setusuario, String nombre,
			String descripcion, Date fecharegistro, String iplog/*,
			Set<Setusuario> setusuarios, Set<Setmenu> setmenus, Set<Setperfil> setperfils*/) {
		this.idestado = idestado;
		this.setusuario = setusuario;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
//		this.setusuarios = setusuarios;
//		this.setmenus = setmenus;
//		this.setperfils = setperfils;
	}

	@Id
	@Column(name = "idestado", unique = true, nullable = false)
	public int getIdestado() {
		return this.idestado;
	}

	public void setIdestado(int idestado) {
		this.idestado = idestado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	public Setusuario getSetusuario() {
		return this.setusuario;
	}

	public void setSetusuario(Setusuario setusuario) {
		this.setusuario = setusuario;
	}

	@Column(name = "nombre", length = 20)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 100)
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
/*
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "setestado", targetEntity = Setusuario.class)
	public Set<Setusuario> getSetusuarios() {
		return this.setusuarios;
	}

	public void setSetusuarios(Set<Setusuario> setusuarios) {
		this.setusuarios = setusuarios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "setestado", targetEntity = Setmenu.class)
	public Set<Setmenu> getSetmenus() {
		return this.setmenus;
	}

	public void setSetmenus(Set<Setmenu> setmenus) {
		this.setmenus = setmenus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "setestado", targetEntity = Setperfil.class)
	public Set<Setperfil> getSetperfils() {
		return this.setperfils;
	}

	public void setSetperfils(Set<Setperfil> setperfils) {
		this.setperfils = setperfils;
	}
*/
}
