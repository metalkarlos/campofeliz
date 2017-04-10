package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetrazaDAO;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetrazaBO {
	
	public List<Petraza> lisRazas(int idespecie) throws Exception {
		List<Petraza> lisPetraza = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetrazaDAO petrazaDAO = new PetrazaDAO();
			
			lisPetraza = petrazaDAO.lisPetraza(session, idespecie);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisPetraza;
	}
	
	public Petraza getPetrazaById(int id) throws Exception {
		Petraza petraza = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetrazaDAO petrazaDAO = new PetrazaDAO();
			
			petraza = petrazaDAO.getPetrazaById(session, id);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return petraza;
	}
	
	public List<Petraza> lisPetrazaByPage(int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Petraza> lisPetraza = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetrazaDAO petrazaDAO = new PetrazaDAO();
			
			lisPetraza = petrazaDAO.lisPetrazaByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisPetraza;
	}
	
	public List<Petraza> lisPetrazaPorEspeciePagineo(int idespecie, String nombre, int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Petraza> lisPetraza = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetrazaDAO petrazaDAO = new PetrazaDAO();
			
			lisPetraza = petrazaDAO.lisPetrazaPorEspeciePagineo(session, idespecie, nombre, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisPetraza;
	}
	
	public boolean ingresarPetraza(Petraza petraza) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetrazaDAO petrazaDAO = new PetrazaDAO();
			
			int maxid = petrazaDAO.maxIdPetraza(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petraza.setIdraza(maxid);
			petraza.setFecharegistro(fecharegistro);
			petraza.setIplog(usuarioBean.getIp());
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(1);
			
			petraza.setSetestado(setestado);
			petraza.setSetusuario(usuarioBean.getSetUsuario());
	
			petrazaDAO.savePetraza(session, petraza);
			
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
	
	public boolean modificarPetraza(Petraza petraza,Petraza petrazaClon) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			if(!petraza.equals(petrazaClon)){
				PetrazaDAO petrazaDAO = new PetrazaDAO();
				Date fecharegistro = new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				
				petraza.setFecharegistro(fecharegistro);
				petraza.setIplog(usuarioBean.getIp());
				petraza.setSetusuario(usuarioBean.getSetUsuario());
				petrazaDAO.updatePetraza(session, petraza);
				
				ok = true;
			}
			
			if(ok){
				session.getTransaction().commit();
			}
		}catch(Exception he){
			session.getTransaction().rollback();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean eliminarPetraza(Petraza petraza) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetrazaDAO petrazaDAO = new PetrazaDAO();
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			petraza.setSetestado(setestado);
			
			petraza.setFecharegistro(fecharegistro);
			petraza.setIplog(usuarioBean.getIp());
			petraza.setSetusuario(usuarioBean.getSetUsuario());
			petrazaDAO.updatePetraza(session, petraza);
			
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
