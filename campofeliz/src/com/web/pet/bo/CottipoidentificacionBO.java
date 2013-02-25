package com.web.pet.bo;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.daointerface.CottipoidentificacionDAOInterface;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.util.HibernateUtil;

public class CottipoidentificacionBO {
	
	private CottipoidentificacionDAOInterface cottipoidentificacionDAOInterface;
	
	public CottipoidentificacionBO() throws Exception {
		try{
			cottipoidentificacionDAOInterface = (CottipoidentificacionDAOInterface)CottipoidentificacionBO.class.getClassLoader().loadClass("com.web.pet.dao.CottipoidentificacionDAO").newInstance();
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("Problemas al cargar la interfaz CottipoidentificacionDAOInterface");
		}
	}
	
	public List<Cottipoidentificacion> lisCottipoidentificacion() throws Exception{
		List<Cottipoidentificacion> lisCottipoidentificacion = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			lisCottipoidentificacion = cottipoidentificacionDAOInterface.lisCottipoidentificacion(session);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisCottipoidentificacion;
	}

}
