package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Cotcolor;

public interface CotcolorDAOInterface {
	public int maxIdCotcolor(Session session) throws Exception;
	public void saveCotcolor(Session session, Cotcolor cotcolor) throws Exception;
	public void updateCotcolor(Session session, Cotcolor cotcolor) throws Exception;
	public List<Cotcolor> lisCotcolor(Session session) throws Exception;
	public List<Cotcolor> lisCotcolorByPage(Session session, int pageSize, int pageNumber, int args[]) throws Exception;
	public Cotcolor getCotcolorById(Session session, int id) throws Exception;
}
