package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.CotcolorDAO;
import com.web.pet.pojo.annotations.Cotcolor;
import com.web.pet.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CotcolorBO {
	
	public List<Cotcolor> lisCotcolor() throws Exception {
		List<Cotcolor> lisCotcolor = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotcolorDAO cotcolorDAO = new CotcolorDAO();
			
			lisCotcolor = cotcolorDAO.lisCotcolor(session);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisCotcolor;
	}
	
	public Cotcolor getCotcolorById(int id) throws Exception{
		Cotcolor cotcolor = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotcolorDAO cotcolorDAO = new CotcolorDAO();
			
			cotcolor = cotcolorDAO.getCotcolorById(session, id);
		} catch(Exception he) {
			throw new Exception(he);
		} finally {
			session.close();
		}
		
		return cotcolor;
	}
	
	public List<Cotcolor> lisCotcolorByPage(int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Cotcolor> lisCotcolor = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotcolorDAO cotcolorDAO = new CotcolorDAO();
			
			lisCotcolor = cotcolorDAO.lisCotcolorByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException(he);
		}finally{
			session.close();
		}
		
		return lisCotcolor;
	}
	
	public boolean ingresarCotcolor(Cotcolor cotcolor) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CotcolorDAO cotcolorDAO = new CotcolorDAO();
			
			int maxid = cotcolorDAO.maxIdCotcolor(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotcolor.setIdcolor(maxid);
			cotcolor.setFecharegistro(fecharegistro);
			cotcolor.setIplog(usuarioBean.getIp());
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(1);
			cotcolor.setSetestado(setestado);
			
			cotcolor.setSetusuario(usuarioBean.getSetUsuario());
	
			cotcolorDAO.saveCotcolor(session, cotcolor);
			
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
	
	public boolean modificarCotcolor(Cotcolor cotcolor,Cotcolor cotcolorClon) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			if(!cotcolor.equals(cotcolorClon)){
				CotcolorDAO cotcolorDAO = new CotcolorDAO();
				Date fecharegistro = new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				
				cotcolor.setFecharegistro(fecharegistro);
				cotcolor.setIplog(usuarioBean.getIp());
				cotcolor.setSetusuario(usuarioBean.getSetUsuario());
				cotcolorDAO.updateCotcolor(session, cotcolor);
				
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
	
	public boolean eliminarCotcolor(Cotcolor cotcolor) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CotcolorDAO cotcolorDAO = new CotcolorDAO();
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			cotcolor.setSetestado(setestado);
			
			cotcolor.setFecharegistro(fecharegistro);
			cotcolor.setIplog(usuarioBean.getIp());
			cotcolor.setSetusuario(usuarioBean.getSetUsuario());
			cotcolorDAO.updateCotcolor(session, cotcolor);
			
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
