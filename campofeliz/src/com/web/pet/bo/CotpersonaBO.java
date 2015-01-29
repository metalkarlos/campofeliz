package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;


import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.CotpersonaDAO;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CotpersonaBO {
	
	public Cotpersona getCotpersonaById(int idpersona) throws Exception{
		Cotpersona cotpersona = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			cotpersona = cotpersonaDAO.getCotpersonaById(session, idpersona);
		}catch(Exception e){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return cotpersona;
	}
	
	public List<Cotpersona> lisCotpersonaByPage(String[] nombres, int pageSize, int pageNumber, int args[]) throws RuntimeException{
		List<Cotpersona> lisCotpersona = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			lisCotpersona = cotpersonaDAO.lisCotpersonaByPage(session, nombres, pageSize, pageNumber, args);
		}catch(Exception e){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisCotpersona;
	}
	
	public List<Cotpersona> lisCotpersonaPetmascotaByPage(String[] nombres, int pageSize, int pageNumber, int args[]) throws RuntimeException{
		List<Cotpersona> lisCotpersona = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			lisCotpersona = cotpersonaDAO.lisCotpersonaPetmascotaByPage(session, nombres, pageSize, pageNumber, args);
		}catch(Exception e){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisCotpersona;
	}
	
	public List<Cotpersona> lisCotpersonaBusqueda(Cotpersona cotpersona) throws Exception{
		List<Cotpersona> lisCotpersona = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			lisCotpersona = cotpersonaDAO.lisCotpersonaBusqueda(session, cotpersona);
		}catch(Exception e){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisCotpersona;
	}
	
	public List<Cotpersona> lisCotpersonaBusquedaByPage(Cotpersona cotpersona, int pageSize, int pageNumber, int args[]) throws Exception{
		List<Cotpersona> lisCotpersona = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			lisCotpersona = cotpersonaDAO.lisCotpersonaBusquedaByPage(session, cotpersona, pageSize, pageNumber, args);
		}catch(Exception e){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisCotpersona;
	}
	
	public boolean newCotpersona(Cotpersona cotpersona) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			int max = cotpersonaDAO.maxIdCotpersona(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotpersona.setIdpersona(max);
			cotpersona.setFecharegistro(fecharegistro);
			cotpersona.setIplog(usuarioBean.getIp());
			cotpersona.getCotestado().setIdestado(1);
			cotpersona.setSetusuario(usuarioBean.getSetUsuario());
			
			cotpersona.setObjeto(null);
			cotpersonaDAO.saveCotpersona(session, cotpersona);
			
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
	
	public boolean updateCotpersona(Cotpersona cotpersona) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotpersona.setFecharegistro(fecharegistro);
			cotpersona.setIplog(usuarioBean.getIp());
			cotpersona.setSetusuario(usuarioBean.getSetUsuario());
			
			cotpersona.setObjeto(null);
			cotpersonaDAO.updateCotpersona(session, cotpersona);
			
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
