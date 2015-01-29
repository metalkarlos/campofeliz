package com.web.pet.bo;

import org.hibernate.Session;

import com.web.pet.dao.SetusuarioDAO;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.HibernateUtil;

public class SetusuarioBO {
	
	public Setusuario getByUserPasswd(String nombre, String clave ) throws Exception {
		Setusuario setusuario = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			SetusuarioDAO setusuarioDAO = new SetusuarioDAO();
			
			setusuario = setusuarioDAO.getByUserPasswd(session, nombre, clave);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return setusuario;
	}
	
}
