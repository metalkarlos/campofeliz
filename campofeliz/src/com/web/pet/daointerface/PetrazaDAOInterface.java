package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Petraza;

public interface PetrazaDAOInterface {
	public int maxIdPetraza(Session session) throws Exception;
	public void savePetraza(Session session, Petraza petraza) throws Exception;
	public void updatePetraza(Session session, Petraza petraza) throws Exception;
	public List<Petraza> lisPetraza(Session session) throws Exception;
	public List<Petraza> lisPetrazaByPage(Session session, int pageSize, int pageNumber, int args[]) throws Exception;
	public Petraza getPetrazaById(Session session, int id) throws Exception;
}
