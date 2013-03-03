package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.CoteventoDAOInterface;
import com.web.pet.pojo.annotations.Cotevento;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CoteventoBO {
	CoteventoDAOInterface coteventoDAOInterface;
	
	public CoteventoBO() throws RuntimeException {
		try {
			coteventoDAOInterface = (CoteventoDAOInterface) CoteventoBO.class.getClassLoader().loadClass("com.web.pet.dao.CoteventoDAO").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Problemas al cargar la interfaz CoteventoDAOInterface");
		}
	}
	
	public boolean newCotevento(Cotevento cotevento) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			int max = coteventoDAOInterface.maxIdCotevento(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotevento.setIdevento(max);
			cotevento.setFecharegistro(fecharegistro);
			cotevento.getCotestado().setIdestado(1);
			cotevento.setIplog(usuarioBean.getIp());
			cotevento.setSetusuario(usuarioBean.getSetUsuario());
			
			coteventoDAOInterface.saveCotevento(session, cotevento);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new Exception();
		} finally {
			session.close();
		}
		
		return ok;
	}
	
	public boolean updateEvento(Cotevento cotevento) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotevento.setFecharegistro(fecharegistro);
			cotevento.setIplog(usuarioBean.getIp());
			cotevento.setSetusuario(usuarioBean.getSetUsuario());
			coteventoDAOInterface.updateCotevento(session, cotevento);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new Exception();
		} finally {
			session.close();
		}
		
		return ok;
	}
	
	public boolean deleteCotevento(int id) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			coteventoDAOInterface.deleteCotevento(session, id);
			session.getTransaction().commit();
			ok = true;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new Exception();
		} finally {
			session.close();
		}
		
		return ok;
	}
	
	public List<Cotevento> lisCotevento(Date fechadesde, Date fechahasta) throws RuntimeException{
		List<Cotevento> lisCotevento = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			lisCotevento = coteventoDAOInterface.lisCotevento(session, fechadesde, fechahasta);
		} catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			session.close();
		}
		
		return lisCotevento;
	}

}