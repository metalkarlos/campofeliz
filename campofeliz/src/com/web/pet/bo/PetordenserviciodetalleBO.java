package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetordenserviciodetalleDAO;
import com.web.pet.pojo.annotations.Petordenserviciodetalle;
import com.web.pet.pojo.annotations.PetordenserviciodetalleId;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetordenserviciodetalleBO {
	
	public Petordenserviciodetalle getPetordenserviciodetalleById(PetordenserviciodetalleId petordenserviciodetalleId) throws Exception {
		Petordenserviciodetalle petordenserviciodetalle = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetordenserviciodetalleDAO petordenserviciodetalleDAO = new PetordenserviciodetalleDAO();
			
			petordenserviciodetalle = petordenserviciodetalleDAO.getPetordenserviciodetalleById(session, petordenserviciodetalleId);
		} catch(Exception he) {
			throw new Exception();
		} finally {
			session.close();
		}
		
		return petordenserviciodetalle;
	}
	
	public List<Petordenserviciodetalle> lisPetordenserviciodetalleByPage(int idordenservicio, int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Petordenserviciodetalle> lisPetordenserviciodetalle = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetordenserviciodetalleDAO petordenserviciodetalleDAO = new PetordenserviciodetalleDAO();
			
			lisPetordenserviciodetalle = petordenserviciodetalleDAO.lisPethistoriaclinicadetalleByPage(session, pageSize, pageNumber, args, idordenservicio);
		}catch(Exception e){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisPetordenserviciodetalle;
	}
	
	public boolean newPetordenserviciodetalle(Petordenserviciodetalle petordenserviciodetalle) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetordenserviciodetalleDAO petordenserviciodetalleDAO = new PetordenserviciodetalleDAO();
			
			int maxid = petordenserviciodetalleDAO.maxIdPetordenserviciodetalleByParent(session, petordenserviciodetalle.getId().getIdordenservicio())+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petordenserviciodetalle.getId().setIdordenserviciodetalle(maxid);
			petordenserviciodetalle.setFecharegistro(fecharegistro);
			petordenserviciodetalle.setIplog(usuarioBean.getIp());
			petordenserviciodetalle.getSetestado().setIdestado(1);
			petordenserviciodetalle.setSetusuario(usuarioBean.getSetUsuario());
	
			petordenserviciodetalleDAO.savePetordenserviciodetalle(session, petordenserviciodetalle);
			
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
	
	public boolean updatePetordenserviciodetalle(Petordenserviciodetalle petordenserviciodetalle) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetordenserviciodetalleDAO petordenserviciodetalleDAO = new PetordenserviciodetalleDAO();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petordenserviciodetalle.setFecharegistro(fecharegistro);
			petordenserviciodetalle.setIplog(usuarioBean.getIp());
			petordenserviciodetalle.setSetusuario(usuarioBean.getSetUsuario());
			petordenserviciodetalleDAO.updatePetordenserviciodetalle(session, petordenserviciodetalle);
			
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
	
	public boolean eliminarPetordenserviciodetalle(PetordenserviciodetalleId petordenserviciodetalleId) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetordenserviciodetalleDAO petordenserviciodetalleDAO = new PetordenserviciodetalleDAO();
			
			petordenserviciodetalleDAO.deletePetordenserviciodetalle(session, petordenserviciodetalleId);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
			session.getTransaction().rollback();
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return ok;
	}

}
