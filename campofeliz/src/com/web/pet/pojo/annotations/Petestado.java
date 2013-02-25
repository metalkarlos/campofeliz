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
 * Petestado generated by hbm2java
 */
@Entity
@Table(name = "petestado", schema = "pets")
public class Petestado implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1150725854739261293L;
	private int idestado;
	private Setusuario setusuario;
	private String nombre;
	private String descripcion;
	private Date fecharegistro;
	private String iplog;
//	private Set<Petmascota> petmascotas = new HashSet<Petmascota>(0);
//	private Set<Petcomision> petcomisions = new HashSet<Petcomision>(0);
//	private Set<Petordenserviciodetalle> petordenserviciodetalles = new HashSet<Petordenserviciodetalle>(0);
//	private Set<Pettipocomision> pettipocomisions = new HashSet<Pettipocomision>(0);
//	private Set<Petordenservicio> petordenservicios = new HashSet<Petordenservicio>(0);
//	private Set<Pettablacobro> pettablacobros = new HashSet<Pettablacobro>(0);
//	private Set<Petvacunamascota> petvacunamascotas = new HashSet<Petvacunamascota>(0);
//	private Set<Petespecie> petespecies = new HashSet<Petespecie>(0);
//	private Set<Petmascotacolor> petmascotacolors = new HashSet<Petmascotacolor>(0);
//	private Set<Petraza> petrazas = new HashSet<Petraza>(0);
//	private Set<Petmascota> petmascotas = new HashSet<Petmascota>(0);
//	private Set<Pethistoriaclinicadetalle> pethistoriaclinicadetalles = new HashSet<Pethistoriaclinicadetalle>(0);
//	private Set<Petfoto> petfotos = new HashSet<Petfoto>(0);
//	private Set<Pethistoriaclinica> pethistoriaclinicas = new HashSet<Pethistoriaclinica>(0);
//	private Set<Pettipoexamen> pettipoexamens = new HashSet<Pettipoexamen>(0);
//	private Set<Petexamen> petexamens = new HashSet<Petexamen>(0);
//	private Set<Petplantillavacuna> petplantillavacunas = new HashSet<Petplantillavacuna>(0);

	public Petestado() {
	}

	public Petestado(int idestado, String nombre, Date fecharegistro) {
		this.idestado = idestado;
		this.nombre = nombre;
		this.fecharegistro = fecharegistro;
	}

	public Petestado(int idestado, Setusuario setusuario, String nombre,
			String descripcion, Date fecharegistro, String iplog/*,
			Set<Petmascota> petmascotas, Set<Petcomision> petcomisions, 
			Set<Petordenserviciodetalle> petordenserviciodetalles, 
			Set<Pettipocomision> pettipocomisions, Set<Petordenservicio> petordenservicios, Set<Pettablacobro> pettablacobros,
			Set<Petvacunamascota> petvacunamascotas, Set<Petespecie> petespecies, Set<Petmascotacolor> petmascotacolors,
			Set<Petraza> petrazas, Set<Petmascota> petmascotas, Set<Pethistoriaclinicadetalle> pethistoriaclinicadetalles,
			Set<Petfoto> petfotos, Set<Pethistoriaclinica> pethistoriaclinicas, Set<Pettipoexamen> pettipoexamens, Set<Petexamen> petexamens, Set<Petplantillavacuna> petplantillavacunas*/) {
		this.idestado = idestado;
		this.setusuario = setusuario;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
//		this.petmascotas = petmascotas;
//		this.petcomisions = petcomisions; 
//		this.petordenserviciodetalles = petordenserviciodetalles; 
//		this.pettipocomisions = pettipocomisions; 
//		this.petordenservicios = petordenservicios; 
//		this.pettablacobros = pettablacobros;
//		this.petvacunamascotas = petvacunamascotas;
//		this.petespecies = petespecies;
//		this.petmascotacolors = petmascotacolors;
//		this.petrazas = petrazas;
//		this.petmascotas = petmascotas;
//		this.pethistoriaclinicadetalles = pethistoriaclinicadetalles;
//		this.petfotos = petfotos;
//		this.pethistoriaclinicas = pethistoriaclinicas;
//		this.pettipoexamens = pettipoexamens;
//		this.petexamens = petexamens;
//		this.petplantillavacunas = petplantillavacunas;
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

	@Column(name = "nombre", nullable = false, length = 20)
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petmascota.class)
	public Set<Petmascota> getPetmascotas() {
		return this.petmascotas;
	}

	public void setPetmascotas(Set<Petmascota> petmascotas) {
		this.petmascotas = petmascotas;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petcomision.class)
	public Set<Petcomision> getPetcomisions() {
		return this.petcomisions;
	}

	public void setPetcomisions(Set<Petcomision> petcomisions) {
		this.petcomisions = petcomisions;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petordenserviciodetalle.class)
	public Set<Petordenserviciodetalle> getPetordenserviciodetalles() {
		return this.petordenserviciodetalles;
	}

	public void setPetordenserviciodetalles(Set<Petordenserviciodetalle> petordenserviciodetalles) {
		this.petordenserviciodetalles = petordenserviciodetalles;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Pettipocomision.class)
	public Set<Pettipocomision> getPettipocomisions() {
		return this.pettipocomisions;
	}

	public void setPettipocomisions(Set<Pettipocomision> pettipocomisions) {
		this.pettipocomisions = pettipocomisions;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petordenservicio.class)
	public Set<Petordenservicio> getPetordenservicios() {
		return this.petordenservicios;
	}

	public void setPetordenservicios(Set<Petordenservicio> petordenservicios) {
		this.petordenservicios = petordenservicios;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Pettablacobro.class)
	public Set<Pettablacobro> getPettablacobros() {
		return this.pettablacobros;
	}

	public void setPettablacobros(Set<Pettablacobro> pettablacobros) {
		this.pettablacobros = pettablacobros;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petvacunamascota.class)
	public Set<Petvacunamascota> getPetvacunamascotas() {
		return this.petvacunamascotas;
	}

	public void setPetvacunamascotas(Set<Petvacunamascota> petvacunamascotas) {
		this.petvacunamascotas = petvacunamascotas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petespecie.class)
	public Set<Petespecie> getPetespecies() {
		return this.petespecies;
	}

	public void setPetespecies(Set<Petespecie> petespecies) {
		this.petespecies = petespecies;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petmascotacolor.class)
	public Set<Petmascotacolor> getPetmascotacolors() {
		return this.petmascotacolors;
	}

	public void setPetmascotacolors(Set<Petmascotacolor> petmascotacolors) {
		this.petmascotacolors = petmascotacolors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petraza.class)
	public Set<Petraza> getPetrazas() {
		return this.petrazas;
	}

	public void setPetrazas(Set<Petraza> petrazas) {
		this.petrazas = petrazas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petmascota.class)
	public Set<Petmascota> getPetmascotas() {
		return this.petmascotas;
	}

	public void setPetmascotas(Set<Petmascota> petmascotas) {
		this.petmascotas = petmascotas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Pethistoriaclinicadetalle.class)
	public Set<Pethistoriaclinicadetalle> getPethistoriaclinicadetalles() {
		return this.pethistoriaclinicadetalles;
	}

	public void setPethistoriaclinicadetalles(Set<Pethistoriaclinicadetalle> pethistoriaclinicadetalles) {
		this.pethistoriaclinicadetalles = pethistoriaclinicadetalles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petfoto.class)
	public Set<Petfoto> getPetfotos() {
		return this.petfotos;
	}

	public void setPetfotos(Set<Petfoto> petfotos) {
		this.petfotos = petfotos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Pethistoriaclinica.class)
	public Set<Pethistoriaclinica> getPethistoriaclinicas() {
		return this.pethistoriaclinicas;
	}

	public void setPethistoriaclinicas(Set<Pethistoriaclinica> pethistoriaclinicas) {
		this.pethistoriaclinicas = pethistoriaclinicas;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Pettipoexamen.class)
	public Set<Pettipoexamen> getPettipoexamens() {
		return this.pettipoexamens;
	}

	public void setPettipoexamens(Set<Pettipoexamen> pettipoexamens) {
		this.pettipoexamens = pettipoexamens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petexamen.class)
	public Set<Petexamen> getPetexamens() {
		return this.petexamens;
	}

	public void setPetexamens(Set<Petexamen> petexamens) {
		this.petexamens = petexamens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "petestado", targetEntity = Petplantillavacuna.class)
	public Set<Petplantillavacuna> getPetplantillavacunas() {
		return this.petplantillavacunas;
	}

	public void setPetplantillavacunas(Set<Petplantillavacuna> petplantillavacunas) {
		this.petplantillavacunas = petplantillavacunas;
	}
*/
}
