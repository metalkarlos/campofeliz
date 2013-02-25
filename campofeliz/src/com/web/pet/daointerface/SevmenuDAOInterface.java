package com.web.pet.daointerface;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Sevmenu;

public interface SevmenuDAOInterface {
	public List<Sevmenu> lisSevmenu(Session session) throws Exception;
}
