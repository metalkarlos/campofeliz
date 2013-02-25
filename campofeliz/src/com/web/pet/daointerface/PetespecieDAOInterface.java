package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Petespecie;

public interface PetespecieDAOInterface {
	public int maxIdPetespecie(Session session) throws Exception;
	public void savePetespecie(Session session, Petespecie petespecie) throws Exception;
	public void updatePetespecie(Session session, Petespecie petespecie) throws Exception;
	public List<Petespecie> lisPetespecie(Session session) throws Exception;
	public List<Petespecie> lisPetespecieByPage(Session session, int pageSize, int pageNumber, int args[]) throws Exception;
	public Petespecie getPetespecieById(Session session, int id) throws Exception;
}
