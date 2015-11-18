package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.CotlugarDAO;
import com.web.pet.pojo.annotations.Cotlugar;
import com.web.pet.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CotlugarBO {
	
	public List<Cotlugar> lisCotlugar() throws Exception {
		List<Cotlugar> lisCotlugar = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotlugarDAO cotlugarDAO = new CotlugarDAO();
			
			lisCotlugar = cotlugarDAO.lisCotlugar(session);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisCotlugar;
	}
	
	public Cotlugar getCotlugarById(int id) throws Exception {
		Cotlugar cotlugar = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotlugarDAO cotlugarDAO = new CotlugarDAO();
			
			cotlugar = cotlugarDAO.getCotlugarById(session, id);
		} catch(Exception e){
			throw new Exception();
		} finally {
			session.close();
		}
		
		return cotlugar;
	}
	
	public List<Cotlugar> lisCotlugarByPage(int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Cotlugar> lisCotlugar = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotlugarDAO cotlugarDAO = new CotlugarDAO();
			
			lisCotlugar = cotlugarDAO.lisCotlugarByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisCotlugar;
	}
	
	public boolean ingresarCotlugar(Cotlugar cotlugar) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			CotlugarDAO cotlugarDAO = new CotlugarDAO();
			
			int maxid = cotlugarDAO.maxIdCotlugar(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotlugar.setIdlugar(maxid);
			cotlugar.setFecharegistro(fecharegistro);
			cotlugar.setIplog(usuarioBean.getIp());
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(1);
			cotlugar.setSetestado(setestado);
			
			cotlugar.setSetusuario(usuarioBean.getSetUsuario());
	
			cotlugarDAO.saveCotlugar(session, cotlugar);
			
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
	
	public boolean modificarCotlugar(Cotlugar cotlugar,Cotlugar cotlugarClon) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			if(!cotlugar.equals(cotlugarClon)){
				CotlugarDAO cotlugarDAO = new CotlugarDAO();
				Date fecharegistro = new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				
				cotlugar.setFecharegistro(fecharegistro);
				cotlugar.setIplog(usuarioBean.getIp());
				cotlugar.setSetusuario(usuarioBean.getSetUsuario());
				cotlugarDAO.updateCotlugar(session, cotlugar);
				
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

	public boolean eliminarCotlugar(Cotlugar cotlugar) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CotlugarDAO cotlugarDAO = new CotlugarDAO();
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			cotlugar.setSetestado(setestado);
			
			cotlugar.setFecharegistro(fecharegistro);
			cotlugar.setIplog(usuarioBean.getIp());
			cotlugar.setSetusuario(usuarioBean.getSetUsuario());
			cotlugarDAO.updateCotlugar(session, cotlugar);
			
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
