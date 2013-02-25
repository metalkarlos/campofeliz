package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Cotservicio;

public interface CotservicioDAOInterface {
	public int maxIdCotservicio(Session session) throws Exception;
	public void saveCotservicio(Session session, Cotservicio cotservicio) throws Exception;
	public void updateCotservicio(Session session, Cotservicio cotservicio) throws Exception;
	public List<Cotservicio> lisCotservicio(Session session) throws Exception;
	public List<Cotservicio> lisCotservicioByPage(Session session, int pageSize, int pageNumber, int args[]) throws Exception;
	public Cotservicio getCotservicioById(Session session, int id) throws Exception;
}
