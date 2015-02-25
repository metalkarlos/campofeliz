package com.web.pet.pojo.annotations;


public class Mascotas implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5961378667932487034L;
	Petfotomascota petfotomascota;
	Petmascotahomenaje petmascotahomenaje;
	
	public Mascotas() {
	}

	public Mascotas(Petfotomascota petfoto, Petmascotahomenaje petmascotahomenaje) {
		this.petfotomascota = petfoto;
		this.petmascotahomenaje = petmascotahomenaje;
	}
	
	public Petfotomascota getPetfotomascota() {
		return petfotomascota;
	}
	public void setPetfotomascota(Petfotomascota petfotomascota) {
		this.petfotomascota = petfotomascota;
	}
	public Petmascotahomenaje getPetmascotahomenaje() {
		return petmascotahomenaje;
	}
	public void setPetmascotahomenaje(Petmascotahomenaje petmascotahomenaje) {
		this.petmascotahomenaje = petmascotahomenaje;
	}

}
