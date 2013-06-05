package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Petfoto;

public interface PetfotoDAOInterface {
	public int maxIdPetfoto(Session session) throws Exception;
	public List<Petfoto> lisPetfotoByPetId(Session session, int idmascota) throws Exception;
	public Petfoto getPetfotoById(Session session, int id) throws Exception;
	public Petfoto getPetfotoPerfilByPetId(Session session, int idmascota) throws Exception;
	public void savePetfoto(Session session, Petfoto petfoto) throws Exception;
	public void updatePetfoto(Session session, Petfoto petfoto) throws Exception;
	public void deletePetfoto(Session session, int idfoto) throws Exception;
	public void resetPetfotoPerfilByPetId(Session session, int idmascota) throws Exception;
	public void setPetfotoPerfil(Session session, int idfoto) throws Exception;
	public List<Petfoto> lisPetfotoPerfil(Session session, int tipo) throws Exception;
}
