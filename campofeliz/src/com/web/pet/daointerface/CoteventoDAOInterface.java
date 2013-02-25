package com.web.pet.daointerface;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Cotevento;

public interface CoteventoDAOInterface {
	public int maxIdCotevento(Session session) throws Exception;
	public List<Cotevento> lisCotevento(Session session) throws Exception;
	public Cotevento getCoteventoById(Session session, int id) throws Exception;
	public void saveCotevento(Session session, Cotevento cotevento) throws Exception;
	public void updateCotevento(Session session, Cotevento cotevento) throws Exception;
	public void deleteCotevento(Session session, int id) throws Exception;
	public List<Cotevento> lisCotevento(Session session, Date fechadesde, Date fechahasta) throws Exception;
}
