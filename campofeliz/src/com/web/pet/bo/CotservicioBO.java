package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.CotservicioDAO;
import com.web.pet.pojo.annotations.Cotservicio;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CotservicioBO {
	
	public List<Cotservicio> lisCotservicio() throws Exception {
		List<Cotservicio> lisCotservicio = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotservicioDAO cotservicioDAO = new CotservicioDAO();
			
			lisCotservicio = cotservicioDAO.lisCotservicio(session);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisCotservicio;
	}
	
	public Cotservicio getCotservicioById(int id) throws Exception {
		Cotservicio cotservicio = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotservicioDAO cotservicioDAO = new CotservicioDAO();
			
			cotservicio = cotservicioDAO.getCotservicioById(session, id);
		} catch(Exception e){
			throw new Exception();
		} finally {
			session.close();
		}
		
		return cotservicio;
	}
	
	public List<Cotservicio> lisCotservicioByPage(int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Cotservicio> lisCotservicio = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotservicioDAO cotservicioDAO = new CotservicioDAO();
			
			lisCotservicio = cotservicioDAO.lisCotservicioByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisCotservicio;
	}
	
	public boolean newCotservicio(Cotservicio cotservicio) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CotservicioDAO cotservicioDAO = new CotservicioDAO();
			
			int maxid = cotservicioDAO.maxIdCotservicio(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotservicio.setIdservicio(maxid);
			cotservicio.setFecharegistro(fecharegistro);
			cotservicio.setIplog(usuarioBean.getIp());
			cotservicio.getCotestado().setIdestado(1);
			cotservicio.setSetusuario(usuarioBean.getSetUsuario());
	
			cotservicioDAO.saveCotservicio(session, cotservicio);
			
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
	
	public boolean updateCotservicio(Cotservicio cotservicio) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CotservicioDAO cotservicioDAO = new CotservicioDAO();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotservicio.setFecharegistro(fecharegistro);
			cotservicio.setIplog(usuarioBean.getIp());
			cotservicio.setSetusuario(usuarioBean.getSetUsuario());
			cotservicioDAO.updateCotservicio(session, cotservicio);
			
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
