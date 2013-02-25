package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Petordenserviciodetalle;
import com.web.pet.pojo.annotations.PetordenserviciodetalleId;


public interface PetordenserviciodetalleDAOInterface {
	public int maxIdPetordenserviciodetalleByParent(Session session, int idordenservicio) throws Exception;
	public Petordenserviciodetalle getPetordenserviciodetalleById(Session session, PetordenserviciodetalleId petordenserviciodetalleId) throws Exception;
	public List<Petordenserviciodetalle> lisPethistoriaclinicadetalleByPage(Session session, int pageSize, int pageNumber, int args[], int idordenservicio) throws Exception;
	public void savePetordenserviciodetalle(Session session, Petordenserviciodetalle petordenserviciodetalle) throws Exception;
	public void updatePetordenserviciodetalle(Session session, Petordenserviciodetalle petordenserviciodetalle) throws Exception;
	public void deletePetordenserviciodetalle(Session session, PetordenserviciodetalleId petordenserviciodetalleId) throws Exception;
}
