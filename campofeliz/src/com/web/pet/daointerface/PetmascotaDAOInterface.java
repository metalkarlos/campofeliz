package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Petmascota;

public interface PetmascotaDAOInterface {
	public int maxIdPetmascota(Session session) throws Exception;
	public List<Petmascota> lisPetmascota(Session session, int especie) throws Exception;
	public List<Petmascota> lisPetmascotaByEspecieByPage(Session session, int especie, String nombre, int pageSize, int pageNumber, int args[]) throws Exception;
	public List<Petmascota> lisPetmascotaByPage(Session session, String nombre, int pageSize, int pageNumber, int args[]) throws Exception;
	public List<Petmascota> lisPetmascotaBusqueda(Session session, Petmascota petmascota, String[] caracteristicas) throws Exception;
	public List<Petmascota> lisPetmascotaBusquedaByPage(Session session, Petmascota petmascota, String[] caracteristicas, int pageSize, int pageNumber, int args[]) throws Exception;
	public Petmascota getPetmascotaById(Session session, int id) throws Exception;
	public void savePetmascota(Session session, Petmascota petmascota) throws Exception;
	public void updatePetmascota(Session session, Petmascota petmascota) throws Exception;
}
