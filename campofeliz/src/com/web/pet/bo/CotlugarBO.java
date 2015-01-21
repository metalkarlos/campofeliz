package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.CotlugarDAOInterface;
import com.web.pet.pojo.annotations.Cotlugar;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CotlugarBO {
	
	private CotlugarDAOInterface cotlugarDAOInterface;

	public CotlugarBO() throws RuntimeException {
		try {
			cotlugarDAOInterface = (CotlugarDAOInterface) CotlugarBO.class.getClassLoader().loadClass("com.web.pet.dao.CotlugarDAO").newInstance();
		} catch(Exception e) {
			throw new RuntimeException("Problemas al cargar la interfaz CotlugarDAOInterface");
		}
	}
	
	public List<Cotlugar> lisCotlugar() throws Exception {
		List<Cotlugar> lisCotlugar = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			lisCotlugar = cotlugarDAOInterface.lisCotlugar(session);
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
			cotlugar = cotlugarDAOInterface.getCotlugarById(session, id);
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
			lisCotlugar = cotlugarDAOInterface.lisCotlugarByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisCotlugar;
	}
	
	public boolean newCotlugar(Cotlugar cotlugar) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			int maxid = cotlugarDAOInterface.maxIdCotlugar(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotlugar.setIdlugar(maxid);
			cotlugar.setFecharegistro(fecharegistro);
			cotlugar.setIplog(usuarioBean.getIp());
			cotlugar.getCotestado().setIdestado(1);
			cotlugar.setSetusuario(usuarioBean.getSetUsuario());
	
			cotlugarDAOInterface.saveCotlugar(session, cotlugar);
			
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
	
	public boolean updateCotlugar(Cotlugar cotlugar) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotlugar.setFecharegistro(fecharegistro);
			cotlugar.setIplog(usuarioBean.getIp());
			cotlugar.setSetusuario(usuarioBean.getSetUsuario());
			cotlugarDAOInterface.updateCotlugar(session, cotlugar);
			
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
