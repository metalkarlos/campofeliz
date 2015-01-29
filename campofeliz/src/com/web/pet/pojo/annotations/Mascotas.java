package com.web.pet.pojo.annotations;


public class Mascotas implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5961378667932487034L;
	Petfoto petfoto;
	Petmascota petmascota;
	
	public Petfoto getPetfoto() {
		return petfoto;
	}
	public void setPetfoto(Petfoto petfoto) {
		this.petfoto = petfoto;
	}
	public Petmascota getPetmascota() {
		return petmascota;
	}
	public void setPetmascota(Petmascota petmascota) {
		this.petmascota = petmascota;
	}

}
