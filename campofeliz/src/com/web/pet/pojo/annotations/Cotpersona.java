package com.web.pet.pojo.annotations;

// Generated 26/07/2012 03:22:37 PM by Hibernate Tools 3.4.0.CR1

import java.util.Arrays;
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
 * Cotpersona generated by hbm2java
 */
@Entity
@Table(name = "cotpersona")
public class Cotpersona implements java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2069293345548166597L;
	private int idpersona;
	private Cottipoidentificacion cottipoidentificacion;
	private Setestado setestado;
	private Setusuario setusuario;
	private String apellido1;
	private String apellido2;
	private String nombre1;
	private String nombre2;
	private String alias;
	private Date fecharegistro;
	private Date fechamodificacion;
	private String iplog;
	private String numeroidentificacion;
	private Date fechanacimiento;
	private String telefono;
	private String direccion;
	private String email;
	private String ruta;
	private byte[] objeto;
	private Integer sexo;
	private String nombre;

	public Cotpersona() {
	}

	public Cotpersona(int idpersona, Date fecharegistro) {
		this.idpersona = idpersona;
		this.fecharegistro = fecharegistro;
	}

	public Cotpersona(int idpersona,
			Cottipoidentificacion cottipoidentificacion, Setestado setestado,
			Setusuario setusuario, String apellido1, String apellido2,
			String nombre1, String nombre2, String alias, Date fecharegistro,
			String iplog, String numeroidentificacion, Date fechanacimiento,
			String telefono, String direccion, String email, String ruta, byte[] objeto, Integer sexo,
			Date fechamodificacion, String nombre) {
		this.idpersona = idpersona;
		this.cottipoidentificacion = cottipoidentificacion;
		this.setestado = setestado;
		this.setusuario = setusuario;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nombre1 = nombre1;
		this.nombre2 = nombre2;
		this.alias = alias;
		this.fecharegistro = fecharegistro;
		this.iplog = iplog;
		this.numeroidentificacion = numeroidentificacion;
		this.fechanacimiento = fechanacimiento;
		this.telefono = telefono;
		this.direccion = direccion;
		this.email = email;
		this.ruta = ruta;
		this.objeto = objeto;
		this.sexo = sexo;
		this.fechamodificacion = fechamodificacion;
		this.nombre = nombre;
	}

	@Id
	@Column(name = "idpersona", unique = true, nullable = false)
	public int getIdpersona() {
		return this.idpersona;
	}

	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipoidentificacion")
	public Cottipoidentificacion getCottipoidentificacion() {
		return this.cottipoidentificacion;
	}

	public void setCottipoidentificacion(
			Cottipoidentificacion cottipoidentificacion) {
		this.cottipoidentificacion = cottipoidentificacion;
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

	@Column(name = "apellido1", length = 30)
	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	@Column(name = "apellido2", length = 30)
	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	@Column(name = "nombre1", length = 30)
	public String getNombre1() {
		return this.nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	@Column(name = "nombre2", length = 30)
	public String getNombre2() {
		return this.nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	@Column(name = "alias", length = 30)
	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	@Column(name = "numeroidentificacion", length = 15)
	public String getNumeroidentificacion() {
		return this.numeroidentificacion;
	}

	public void setNumeroidentificacion(String numeroidentificacion) {
		this.numeroidentificacion = numeroidentificacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechanacimiento", length = 19)
	public Date getFechanacimiento() {
		return this.fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	@Column(name = "telefono", length = 50)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "direccion", length = 250)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ruta", length = 100)
	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Column(name = "objeto")
	public byte[] getObjeto() {
		return this.objeto;
	}

	public void setObjeto(byte[] objeto) {
		this.objeto = objeto;
	}

	@Column(name = "sexo")
	public Integer getSexo() {
		return this.sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}

	@Column(name = "nombre", length = 150)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Cotpersona cotpersona = (Cotpersona)super.clone();
		
		if(cotpersona.getCottipoidentificacion() != null && cotpersona.getCottipoidentificacion().getIdtipoidentificacion() > 0){
			cotpersona.setCottipoidentificacion((Cottipoidentificacion) cotpersona.getCottipoidentificacion().clone());
		}
		
		return cotpersona;
	}
	
	public Cotpersona clonar() throws Exception {
		return (Cotpersona)this.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((apellido1 == null) ? 0 : apellido1.hashCode());
		result = prime * result + ((apellido2 == null) ? 0 : apellido2.hashCode());
		result = prime * result + ((cottipoidentificacion == null) ? 0 : cottipoidentificacion.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fechamodificacion == null) ? 0 : fechamodificacion.hashCode());
		result = prime * result + ((fechanacimiento == null) ? 0 : fechanacimiento.hashCode());
		result = prime * result + ((fecharegistro == null) ? 0 : fecharegistro.hashCode());
		result = prime * result + idpersona;
		result = prime * result + ((iplog == null) ? 0 : iplog.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombre1 == null) ? 0 : nombre1.hashCode());
		result = prime * result + ((nombre2 == null) ? 0 : nombre2.hashCode());
		result = prime * result + ((numeroidentificacion == null) ? 0 : numeroidentificacion.hashCode());
		result = prime * result + Arrays.hashCode(objeto);
		result = prime * result + ((ruta == null) ? 0 : ruta.hashCode());
		result = prime * result + ((setestado == null) ? 0 : setestado.getIdestado());
		result = prime * result + ((setusuario == null) ? 0 : setusuario.getIdusuario());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
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
		Cotpersona other = (Cotpersona) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (apellido1 == null) {
			if (other.apellido1 != null)
				return false;
		} else if (!apellido1.equals(other.apellido1))
			return false;
		if (apellido2 == null) {
			if (other.apellido2 != null)
				return false;
		} else if (!apellido2.equals(other.apellido2))
			return false;
		if (cottipoidentificacion == null) {
			if (other.cottipoidentificacion != null)
				return false;
		} else if (!cottipoidentificacion.equals(other.cottipoidentificacion))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fechamodificacion == null) {
			if (other.fechamodificacion != null)
				return false;
		} else if (!fechamodificacion.equals(other.fechamodificacion))
			return false;
		if (fechanacimiento == null) {
			if (other.fechanacimiento != null)
				return false;
		} else if (!fechanacimiento.equals(other.fechanacimiento))
			return false;
		if (fecharegistro == null) {
			if (other.fecharegistro != null)
				return false;
		} else if (!fecharegistro.equals(other.fecharegistro))
			return false;
		if (idpersona != other.idpersona)
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
		if (nombre1 == null) {
			if (other.nombre1 != null)
				return false;
		} else if (!nombre1.equals(other.nombre1))
			return false;
		if (nombre2 == null) {
			if (other.nombre2 != null)
				return false;
		} else if (!nombre2.equals(other.nombre2))
			return false;
		if (numeroidentificacion == null) {
			if (other.numeroidentificacion != null)
				return false;
		} else if (!numeroidentificacion.equals(other.numeroidentificacion))
			return false;
		if (!Arrays.equals(objeto, other.objeto))
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
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

}
