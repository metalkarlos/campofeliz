package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Petmascotacolor;

public interface PetmascotacolorDAOInterface {
	public int maxIdPetmascotacolor(Session session) throws Exception;
	public List<Petmascotacolor> lisPetmascotacolor(Session session, int idmascota) throws Exception;
	public void savePetmascotacolor(Session session, Petmascotacolor petmascotacolor) throws Exception;
	public void updatePetmascotacolor(Session session, Petmascotacolor petmascotacolor) throws Exception;
}
