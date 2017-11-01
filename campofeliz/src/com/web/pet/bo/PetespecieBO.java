package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetespecieDAO;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetespecieBO {
	
	public List<Petespecie> lisPetespecie() throws Exception {
		List<Petespecie> lisPetespecie = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetespecieDAO petespecieDAO = new PetespecieDAO();
			
			lisPetespecie = petespecieDAO.lisPetespecie(session);
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
		
		return lisPetespecie;
	}
	
	public Petespecie getPetespecieById(int id) throws Exception {
		Petespecie petespecie = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetespecieDAO petespecieDAO = new PetespecieDAO();
			
			petespecie = petespecieDAO.getPetespecieById(session, id);
		} catch(Exception e){
			throw new Exception(e);
		} finally {
			session.close();
		}
		
		return petespecie;
	}
	
	public List<Petespecie> lisPetespecieByPage(int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Petespecie> lisPetespecie = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetespecieDAO petespecieDAO = new PetespecieDAO();
			
			lisPetespecie = petespecieDAO.lisPetespecieByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException(he);
		}finally{
			session.close();
		}
		
		return lisPetespecie;
	}
	
	public boolean ingresarPetespecie(Petespecie petespecie) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetespecieDAO petespecieDAO = new PetespecieDAO();
			
			int maxid = petespecieDAO.maxIdPetespecie(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petespecie.setIdespecie(maxid);
			petespecie.setFecharegistro(fecharegistro);
			petespecie.setIplog(usuarioBean.getIp());
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(1);
			petespecie.setSetestado(setestado);
			
			petespecie.setSetusuario(usuarioBean.getSetUsuario());
	
			petespecieDAO.savePetespecie(session, petespecie);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception he){
			session.getTransaction().rollback();
			throw new Exception(he); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean modificarPetespecie(Petespecie petespecie,Petespecie petespecieClon) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			if(!petespecie.equals(petespecieClon)){
				PetespecieDAO petespecieDAO = new PetespecieDAO();
				Date fecharegistro = new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				
				petespecie.setFecharegistro(fecharegistro);
				petespecie.setIplog(usuarioBean.getIp());
				petespecie.setSetusuario(usuarioBean.getSetUsuario());
				petespecieDAO.updatePetespecie(session, petespecie);
				
				ok = true;
			}
			
			if(ok){
				session.getTransaction().commit();
			}
		}catch(Exception he){
			session.getTransaction().rollback();
			throw new Exception(he);
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean eliminarPetespecie(Petespecie petespecie) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetespecieDAO petespecieDAO = new PetespecieDAO();
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			petespecie.setSetestado(setestado);
			
			petespecie.setFecharegistro(fecharegistro);
			petespecie.setIplog(usuarioBean.getIp());
			petespecie.setSetusuario(usuarioBean.getSetUsuario());
			petespecieDAO.updatePetespecie(session, petespecie);
				
			session.getTransaction().commit();
			ok = true;
		}catch(Exception he){
			session.getTransaction().rollback();
			throw new Exception(he);
		}finally{
			session.close();
		}
		
		return ok;
	}

}
