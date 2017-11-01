package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.CottipolugarDAO;
import com.web.pet.pojo.annotations.Cottipolugar;
import com.web.pet.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CottipolugarBO {
	
	public List<Cottipolugar> lisCottipolugar() throws Exception {
		List<Cottipolugar> lisCottipolugar = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			CottipolugarDAO cottipolugarDAO = new CottipolugarDAO();
			
			lisCottipolugar = cottipolugarDAO.lisCottipolugar(session);
		} catch(Exception e) {
			throw new Exception(e);
		}finally {
			session.close();
		}
		
		return lisCottipolugar;
	}
	
	public List<Cottipolugar> lisCottipolugarByPage(int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Cottipolugar> lisCottipolugar = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CottipolugarDAO cottipolugarDAO = new CottipolugarDAO();
			
			lisCottipolugar = cottipolugarDAO.lisCottipolugarByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException(he);
		}finally{
			session.close();
		}
		
		return lisCottipolugar;
	}
	
	public boolean ingresarCottipolugar(Cottipolugar cottipolugar) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CottipolugarDAO cottipolugarDAO = new CottipolugarDAO();
			
			int idtipolugar = cottipolugarDAO.maxIdCottipolugar(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cottipolugar.setIdtipolugar(idtipolugar);
			cottipolugar.setFecharegistro(fecharegistro);
			cottipolugar.setIplog(usuarioBean.getIp());
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(1);
			cottipolugar.setSetestado(setestado);
			
			cottipolugar.setSetusuario(usuarioBean.getSetUsuario());
	
			cottipolugarDAO.ingresarCottipolugar(session, cottipolugar);
			
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
	
	public boolean modificarCottipolugar(Cottipolugar cottipolugar,Cottipolugar cottipolugarClon) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			if(!cottipolugar.equals(cottipolugarClon)){
				CottipolugarDAO cottipolugarDAO = new CottipolugarDAO();
				Date fecharegistro = new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				
				cottipolugar.setFecharegistro(fecharegistro);
				cottipolugar.setIplog(usuarioBean.getIp());
				cottipolugar.setSetusuario(usuarioBean.getSetUsuario());
				cottipolugarDAO.modificarCottipolugar(session, cottipolugar);
				
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
	
	public boolean eliminarCottipolugar(Cottipolugar cottipolugar) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CottipolugarDAO cottipolugarDAO = new CottipolugarDAO();
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			cottipolugar.setSetestado(setestado);
			
			cottipolugar.setFecharegistro(fecharegistro);
			cottipolugar.setIplog(usuarioBean.getIp());
			cottipolugar.setSetusuario(usuarioBean.getSetUsuario());
			cottipolugarDAO.modificarCottipolugar(session, cottipolugar);
				
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
