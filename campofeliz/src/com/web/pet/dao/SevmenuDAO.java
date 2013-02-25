package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.web.pet.daointerface.SevmenuDAOInterface;
import com.web.pet.pojo.annotations.Sevmenu;

public class SevmenuDAO implements SevmenuDAOInterface {

	@SuppressWarnings("unchecked")
	public List<Sevmenu> lisSevmenu(Session session) throws Exception{
		List<Sevmenu> arraydatos = null;
		
		Criteria criteria = session.createCriteria(Sevmenu.class);
		arraydatos = (List<Sevmenu>)criteria.list();
		
		return arraydatos;
	}

}
