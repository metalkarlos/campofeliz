package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Cotlugar;

public interface CotlugarDAOInterface {
	public int maxIdCotlugar(Session session) throws Exception;
	public void saveCotlugar(Session session, Cotlugar cotlugar) throws Exception;
	public void updateCotlugar(Session session, Cotlugar cotlugar) throws Exception;
	public List<Cotlugar> lisCotlugar(Session session) throws Exception;
	public List<Cotlugar> lisCotlugarByPage(Session session, int pageSize, int pageNumber, int args[]) throws Exception;
	public Cotlugar getCotlugarById(Session session, int id) throws Exception;
}
