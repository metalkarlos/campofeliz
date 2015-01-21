package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.PetordenservicioDAOInterface;
import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetordenservicioBO {
	private PetordenservicioDAOInterface petordenservicioDAOInterface;
	
	public PetordenservicioBO() {
		try{
			petordenservicioDAOInterface = (PetordenservicioDAOInterface)PetordenservicioBO.class.getClassLoader().loadClass("com.web.pet.dao.PetordenservicioDAO").newInstance();
		}catch(Exception ex){
            throw new RuntimeException("Problemas al cargar la interfaz PetordenservicioDAOInterface");
        }
	}
	
	public Petordenservicio getPetordenservicioById(int idordenservicio) throws Exception{
		Petordenservicio petordenservicio = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			petordenservicio = petordenservicioDAOInterface.getPetordenservicioById(session, idordenservicio);
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
			lisPetordenservicio = petordenservicioDAOInterface.lisPetordenservicioByPage(session, nombres, pageSize, pageNumber, args);
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
			
			int max = petordenservicioDAOInterface.maxIdPetordenservicio(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petordenservicio.setIdordenservicio(max);
			petordenservicio.setFecharegistro(fecharegistro);
			petordenservicio.setIplog(usuarioBean.getIp());
			petordenservicio.getPetestado().setIdestado(1);
			petordenservicio.setSetusuario(usuarioBean.getSetUsuario());
			
			petordenservicioDAOInterface.savePetordenservicio(session, petordenservicio);
			
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
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petordenservicio.setFecharegistro(fecharegistro);
			petordenservicio.setIplog(usuarioBean.getIp());
			petordenservicio.setSetusuario(usuarioBean.getSetUsuario());
			petordenservicioDAOInterface.updatePetordenservicio(session, petordenservicio);
			
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
