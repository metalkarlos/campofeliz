package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetordenservicioDAO;
import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetordenservicioBO {
	
	public Petordenservicio getPetordenservicioById(int idordenservicio) throws Exception{
		Petordenservicio petordenservicio = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
			
			petordenservicio = petordenservicioDAO.getPetordenservicioById(session, idordenservicio);
		}catch(Exception he) {
			throw new Exception();
		} finally {
			session.close();
		}
		
		return petordenservicio;
	}
	
	public List<Petordenservicio> lisPetordenservicioByPage(String[] nombres, int pageSize, int pageNumber, int args[]) throws RuntimeException{
		List<Petordenservicio> lisPetordenservicio = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
			
			lisPetordenservicio = petordenservicioDAO.lisPetordenservicioByPage(session, nombres, pageSize, pageNumber, args);
		}catch(Exception e){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisPetordenservicio;
	}
	
	public boolean newPetordenservicio(Petordenservicio petordenservicio) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
			
			int max = petordenservicioDAO.maxIdPetordenservicio(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petordenservicio.setIdordenservicio(max);
			petordenservicio.setFecharegistro(fecharegistro);
			petordenservicio.setIplog(usuarioBean.getIp());
			petordenservicio.getPetestado().setIdestado(1);
			petordenservicio.setSetusuario(usuarioBean.getSetUsuario());
			
			petordenservicioDAO.savePetordenservicio(session, petordenservicio);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception e){
			petordenservicio.setIdordenservicio(0);
			session.getTransaction().rollback();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean updatePetordenservicio(Petordenservicio petordenservicio) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petordenservicio.setFecharegistro(fecharegistro);
			petordenservicio.setIplog(usuarioBean.getIp());
			petordenservicio.setSetusuario(usuarioBean.getSetUsuario());
			petordenservicioDAO.updatePetordenservicio(session, petordenservicio);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception e){
			session.getTransaction().rollback();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return ok;
	}
}
