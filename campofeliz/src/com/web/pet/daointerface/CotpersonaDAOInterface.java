package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Cotpersona;

public interface CotpersonaDAOInterface {
	public int maxIdCotpersona(Session session) throws Exception;
	public Cotpersona getCotpersonaById(Session session, int idpersona) throws Exception;
	public List<Cotpersona> lisCotpersonaByPage(Session session, String[] nombres, int pageSize, int pageNumber, int args[]) throws Exception;
	public List<Cotpersona> lisCotpersonaPetmascotaByPage(Session session, String[] nombres, int pageSize, int pageNumber, int args[]) throws Exception;
	public List<Cotpersona> lisCotpersonaBusqueda(Session session, Cotpersona cotpersona) throws Exception;
	public List<Cotpersona> lisCotpersonaBusquedaByPage(Session session, Cotpersona cotpersona, int pageSize, int pageNumber, int args[]) throws Exception;
	public void saveCotpersona(Session session, Cotpersona cotpersona) throws Exception;
	public void updateCotpersona(Session session, Cotpersona cotpersona) throws Exception;
}
