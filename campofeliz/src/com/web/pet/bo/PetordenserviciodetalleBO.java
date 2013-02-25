package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.PetordenserviciodetalleDAOInterface;
import com.web.pet.pojo.annotations.Petordenserviciodetalle;
import com.web.pet.pojo.annotations.PetordenserviciodetalleId;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetordenserviciodetalleBO {
	private PetordenserviciodetalleDAOInterface petordenserviciodetalleDAOInterface;
	
	public PetordenserviciodetalleBO() {
		try{
			petordenserviciodetalleDAOInterface = (PetordenserviciodetalleDAOInterface) PetordenserviciodetalleBO.class.getClassLoader().loadClass("com.web.pet.dao.PetordenserviciodetalleDAO").newInstance();
        }catch(Exception ex){
        	ex.printStackTrace();
            throw new RuntimeException("Problemas al cargar la interfaz PetordenserviciodetalleDAOInterface");
        }
	}
	
	public Petordenserviciodetalle getPetordenserviciodetalleById(PetordenserviciodetalleId petordenserviciodetalleId) throws Exception {
		Petordenserviciodetalle petordenserviciodetalle = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			petordenserviciodetalle = petordenserviciodetalleDAOInterface.getPetordenserviciodetalleById(session, petordenserviciodetalleId);
		} catch(Exception he) {
			he.printStackTrace();
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
			lisPetordenserviciodetalle = petordenserviciodetalleDAOInterface.lisPethistoriaclinicadetalleByPage(session, pageSize, pageNumber, args, idordenservicio);
		}catch(Exception e){
			e.printStackTrace();
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
			
			int maxid = petordenserviciodetalleDAOInterface.maxIdPetordenserviciodetalleByParent(session, petordenserviciodetalle.getId().getIdordenservicio())+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petordenserviciodetalle.getId().setIdordenserviciodetalle(maxid);
			petordenserviciodetalle.setFecharegistro(fecharegistro);
			petordenserviciodetalle.setIplog(usuarioBean.getIp());
			petordenserviciodetalle.getPetestado().setIdestado(1);
			petordenserviciodetalle.setSetusuario(usuarioBean.getSetUsuario());
	
			petordenserviciodetalleDAOInterface.savePetordenserviciodetalle(session, petordenserviciodetalle);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception e){
			e.printStackTrace();
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
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petordenserviciodetalle.setFecharegistro(fecharegistro);
			petordenserviciodetalle.setIplog(usuarioBean.getIp());
			petordenserviciodetalle.setSetusuario(usuarioBean.getSetUsuario());
			petordenserviciodetalleDAOInterface.updatePetordenserviciodetalle(session, petordenserviciodetalle);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception e){
			e.printStackTrace();
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
			
			petordenserviciodetalleDAOInterface.deletePetordenserviciodetalle(session, petordenserviciodetalleId);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
			he.printStackTrace();
			session.getTransaction().rollback();
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return ok;
	}

}
