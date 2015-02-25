package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Sevmenu;

public class SevmenuDAO {

	@SuppressWarnings("unchecked")
	public List<Sevmenu> lisSetmenuPadres(Session session) throws Exception{
		List<Sevmenu> lisSetmenu = null;
		
		Criteria criteria = session.createCriteria(Sevmenu.class)
				.add(Restrictions.eq("nivel", 0))
				.add(Restrictions.eq("setestado.idestado", 1))
				.addOrder(Order.asc("orden"));
		lisSetmenu = (List<Sevmenu>)criteria.list();
		
		return lisSetmenu;
	}
	
	@SuppressWarnings("unchecked")
	public List<Sevmenu> lisSetmenuHijos(Session session, int idmenupadre) throws Exception{
		List<Sevmenu> lisSetmenu = null;
		
		Criteria criteria = session.createCriteria(Sevmenu.class)
				.add(Restrictions.eq("idmenupadre", idmenupadre))
				.add(Restrictions.eq("setestado.idestado", 1))
				.addOrder(Order.asc("orden"));
		lisSetmenu = (List<Sevmenu>)criteria.list();
		
		return lisSetmenu;
	}
	
	@SuppressWarnings("unchecked")
	public List<Sevmenu> lisSetmenu(Session session) throws Exception{
		List<Sevmenu> arraydatos = null;
		
		Criteria criteria = session.createCriteria(Sevmenu.class)
				.add(Restrictions.eq("setestado.idestado", 1))
				.addOrder(Order.asc("orden"));
		arraydatos = (List<Sevmenu>)criteria.list();
		
		return arraydatos;
	}

}
