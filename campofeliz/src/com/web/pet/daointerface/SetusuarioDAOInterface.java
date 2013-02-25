package com.web.pet.daointerface;

import org.hibernate.Session;

import com.web.pet.pojo.annotations.Setusuario;

public interface SetusuarioDAOInterface {
	public Setusuario getByUserPasswd(Session session, String nombre, String clave) throws Exception;
}
