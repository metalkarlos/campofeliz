package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.CottiposervicioDAO;
import com.web.pet.pojo.annotations.Cottiposervicio;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CottiposervicioBO {

	public Cottiposervicio getCottiposervicioById(int id) throws Exception {
		Cottiposervicio cottiposervicio = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			CottiposervicioDAO cottiposervicioDAO = new CottiposervicioDAO();
			
			cottiposervicio = cottiposervicioDAO.getCottiposervicioById(session, id);
		} catch(Exception e){
			throw new Exception();
		} finally {
			session.close();
		}
		
		return cottiposervicio;
	}
	
	public List<Cottiposervicio> lisCottiposervicioByPage(int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Cottiposervicio> lisCottiposervicio = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CottiposervicioDAO cottiposervicioDAO = new CottiposervicioDAO();
			
			lisCottiposervicio = cottiposervicioDAO.lisCottiposervicioByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisCottiposervicio;
	}
	
	public boolean newCottiposervicio(Cottiposervicio cottiposervicio) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CottiposervicioDAO cottiposervicioDAO = new CottiposervicioDAO();
			
			int maxid = cottiposervicioDAO.maxIdCottiposervicio(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cottiposervicio.setIdtiposervicio(maxid);
			cottiposervicio.setFecharegistro(fecharegistro);
			cottiposervicio.setIplog(usuarioBean.getIp());
			cottiposervicio.getSetestado().setIdestado(1);
			cottiposervicio.setSetusuario(usuarioBean.getSetUsuario());
	
			cottiposervicioDAO.saveCottiposervicio(session, cottiposervicio);
			
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
	
	public boolean updateCottiposervicio(Cottiposervicio cottiposervicio) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CottiposervicioDAO cottiposervicioDAO = new CottiposervicioDAO();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cottiposervicio.setFecharegistro(fecharegistro);
			cottiposervicio.setIplog(usuarioBean.getIp());
			cottiposervicio.setSetusuario(usuarioBean.getSetUsuario());
			cottiposervicioDAO.updateCottiposervicio(session, cottiposervicio);
			
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
