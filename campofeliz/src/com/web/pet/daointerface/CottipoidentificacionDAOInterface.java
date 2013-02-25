package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Cottipoidentificacion;

public interface CottipoidentificacionDAOInterface {
	public List<Cottipoidentificacion> lisCottipoidentificacion(Session session) throws Exception;
}
