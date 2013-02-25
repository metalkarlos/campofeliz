package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Cottiposervicio;

public interface CottiposervicioDAOInterface {
	public int maxIdCottiposervicio(Session session) throws Exception;
	public void saveCottiposervicio(Session session, Cottiposervicio cottiposervicio) throws Exception;
	public void updateCottiposervicio(Session session, Cottiposervicio cottiposervicio) throws Exception;
	public List<Cottiposervicio> lisCottiposervicioByPage(Session session, int pageSize, int pageNumber, int args[]) throws Exception;
	public Cottiposervicio getCottiposervicioById(Session session, int id) throws Exception;
}
