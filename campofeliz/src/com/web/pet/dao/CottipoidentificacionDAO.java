package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Cottipoidentificacion;

public class CottipoidentificacionDAO {

	@SuppressWarnings("unchecked")
	public List<Cottipoidentificacion> lisCottipoidentificacion(Session session) throws Exception {
		List<Cottipoidentificacion> lisCottipoidentificacion = null;
		
		Criteria criteria = session.createCriteria(Cottipoidentificacion.class)
		.add( Restrictions.eq("setestado.idestado", 1))
		.addOrder( Order.asc("idtipoidentificacion"));
		
		lisCottipoidentificacion = (List<Cottipoidentificacion>)criteria.list();
		
		return lisCottipoidentificacion;
	}

}
