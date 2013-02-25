package com.web.pet.bo;

import org.hibernate.Session;

import com.web.pet.daointerface.SetusuarioDAOInterface;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.HibernateUtil;

public class SetusuarioBO {
	
	private Session session = null;
	private SetusuarioDAOInterface setusuarioDAOInterface;
	
	public SetusuarioBO() throws RuntimeException {
		try{
			setusuarioDAOInterface = (SetusuarioDAOInterface) SetusuarioBO.class.getClassLoader().loadClass("com.web.pet.dao.SetusuarioDAO").newInstance();
        }catch(Exception ex){
        	ex.printStackTrace();
            throw new RuntimeException("Problemas al cargar la interfaz SetusuarioDAOInterface");
        }
	}

	public Setusuario getByUserPasswd(String nombre, String clave ) throws Exception {
		Setusuario setusuario = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			setusuario = setusuarioDAOInterface.getByUserPasswd(session, nombre, clave);
		}catch(Exception he){
			he.printStackTrace();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return setusuario;
	}
	
}
