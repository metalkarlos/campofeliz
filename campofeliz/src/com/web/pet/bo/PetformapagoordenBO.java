package com.web.pet.bo;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.dao.PetformapagoordenDAO;
import com.web.pet.pojo.annotations.Petformapagoorden;
import com.web.pet.pojo.annotations.PetordenservicioId;
import com.web.util.HibernateUtil;

public class PetformapagoordenBO {
	
	public List<Petformapagoorden> lisPetformapagoorden(PetordenservicioId petordenservicioId) throws Exception {
		List<Petformapagoorden> lisPetformapagoorden = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetformapagoordenDAO petformapagoordenDAO = new PetformapagoordenDAO();
			
			lisPetformapagoorden = petformapagoordenDAO.lisPetformapagoorden(session, petordenservicioId);
		}catch(Exception e){
			throw new Exception(e.getMessage(), e.getCause());
		}finally{
			session.close();
		}
		
		return lisPetformapagoorden;
	}
	
}
