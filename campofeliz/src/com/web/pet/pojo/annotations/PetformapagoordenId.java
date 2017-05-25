package com.web.pet.pojo.annotations;
// Generated 06/05/2017 20:55:18 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PetformapagoordenId generated by hbm2java
 */
@Embeddable
public class PetformapagoordenId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7708326837281480710L;
	private int idordenservicio;
	private int idanio;
	private int idformapagoorden;

	public PetformapagoordenId() {
	}

	public PetformapagoordenId(int idordenservicio, int idanio, int idformapagoorden) {
		this.idordenservicio = idordenservicio;
		this.idanio = idanio;
		this.idformapagoorden = idformapagoorden;
	}

	@Column(name = "idordenservicio", nullable = false)
	public int getIdordenservicio() {
		return this.idordenservicio;
	}

	public void setIdordenservicio(int idordenservicio) {
		this.idordenservicio = idordenservicio;
	}

	@Column(name = "idanio", nullable = false)
	public int getIdanio() {
		return this.idanio;
	}

	public void setIdanio(int idanio) {
		this.idanio = idanio;
	}

	@Column(name = "idformapagoorden", nullable = false)
	public int getIdformapagoorden() {
		return this.idformapagoorden;
	}

	public void setIdformapagoorden(int idformapagoorden) {
		this.idformapagoorden = idformapagoorden;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PetformapagoordenId))
			return false;
		PetformapagoordenId castOther = (PetformapagoordenId) other;

		return (this.getIdordenservicio() == castOther.getIdordenservicio())
				&& (this.getIdanio() == castOther.getIdanio())
				&& (this.getIdformapagoorden() == castOther.getIdformapagoorden());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdordenservicio();
		result = 37 * result + this.getIdanio();
		result = 37 * result + this.getIdformapagoorden();
		return result;
	}

}
