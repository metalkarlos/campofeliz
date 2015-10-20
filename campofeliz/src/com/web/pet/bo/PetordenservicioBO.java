package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetordenservicioDAO;
import com.web.pet.dao.PetordenserviciodetalleDAO;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.pet.pojo.annotations.Petordenserviciodetalle;
import com.web.pet.pojo.annotations.Setestado;
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
			throw new RuntimeException(e);
		}finally{
			session.close();
		}
		
		return lisPetordenservicio;
	}
	
	public boolean ingresarPetordenservicio(Petordenservicio petordenservicio) throws Exception{
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
			petordenservicio.getSetestado().setIdestado(1);
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
	
	public boolean modificarPetordenservicio(Petordenservicio petordenservicio, Petordenservicio petordenservicioClon) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			//Se graba el servicio si han habido cambios
			if(!petordenservicio.equals(petordenservicioClon)){
				petordenservicio.setFecharegistro(fecharegistro);
				petordenservicio.setIplog(usuarioBean.getIp());
				petordenservicio.setSetusuario(usuarioBean.getSetUsuario());
				petordenservicioDAO.updatePetordenservicio(session, petordenservicio);
				ok = true;
			}
			
			if(ok){
				session.getTransaction().commit();
			}
		}catch(Exception e){
			ok = false;
			session.getTransaction().rollback();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean eliminarPetordenservicio(Petordenservicio petordenservicio, List<Petordenserviciodetalle> lisPetordenserviciodetalle) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
			PetordenserviciodetalleDAO petordenserviciodetalleDAO = new PetordenserviciodetalleDAO();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			petordenservicio.setSetestado(setestado);
			
			petordenservicio.setFecharegistro(fecharegistro);
			petordenservicio.setIplog(usuarioBean.getIp());
			petordenservicio.setSetusuario(usuarioBean.getSetUsuario());
			petordenservicioDAO.updatePetordenservicio(session, petordenservicio);
			
			//se eliminan detalles en caso de existir
			for(Petordenserviciodetalle tmp : lisPetordenserviciodetalle){
				//petordenserviciodetalleDAO.deletePetordenserviciodetalle(session, tmp.getId());
				Setestado setestadoservicios = new Setestado();
				setestadoservicios.setIdestado(2);//inactivo
				tmp.setSetestado(setestadoservicios);
				
				fecharegistro = new Date();
				tmp.setFecharegistro(fecharegistro);
				tmp.setIplog(usuarioBean.getIp());
				tmp.setSetusuario(usuarioBean.getSetUsuario());
				
				petordenserviciodetalleDAO.updatePetordenserviciodetalle(session, tmp);
			}
			
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

	public boolean grabarMascotaBasico(Petmascotahomenaje petmascotahomenaje, Cotpersona cotpersona) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CotpersonaBO cotpersonaBO = new CotpersonaBO();
			cotpersonaBO.grabarPersonaBasico(session, cotpersona);
			
			PetmascotaBO petmascotaBO = new PetmascotaBO();
			petmascotahomenaje.setCotpersona(cotpersona);
			petmascotaBO.grabarMascotaBasico(session, petmascotahomenaje);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception he){
			session.getTransaction().rollback();
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return ok;
	}
}
