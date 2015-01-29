package com.web.pet.bo;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.dao.CottipoidentificacionDAO;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.util.HibernateUtil;

public class CottipoidentificacionBO {
	
	public List<Cottipoidentificacion> lisCottipoidentificacion() throws Exception{
		List<Cottipoidentificacion> lisCottipoidentificacion = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CottipoidentificacionDAO cottipoidentificacionDAO = new CottipoidentificacionDAO();
			
			lisCottipoidentificacion = cottipoidentificacionDAO.lisCottipoidentificacion(session);
		}catch(Exception e){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisCottipoidentificacion;
	}

}
