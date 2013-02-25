package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Petordenservicio;

public interface PetordenservicioDAOInterface {
	public int maxIdPetordenservicio(Session session) throws Exception;
	public Petordenservicio getPetordenservicioById(Session session, int idordenservicio) throws Exception;
	public List<Petordenservicio> lisPetordenservicioByPage(Session session, String[] nombres, int pageSize, int pageNumber, int args[]) throws Exception;
	public void savePetordenservicio(Session session, Petordenservicio petordenservicio) throws Exception;
	public void updatePetordenservicio(Session session, Petordenservicio petordenservicio) throws Exception;
}
