package com.web.pet.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.CoteventoDAO;
import com.web.pet.pojo.annotations.Cotevento;
import com.web.pet.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CoteventoBO {
	
	public boolean newCotevento(Cotevento cotevento, boolean isAllDay) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			CoteventoDAO coteventoDAO = new CoteventoDAO();
			
			int idEvento = coteventoDAO.maxIdCotevento(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotevento.setIdevento(idEvento);
			cotevento.setFecharegistro(fecharegistro);
			Setestado setestado = new Setestado();
			setestado.setIdestado(1);
			cotevento.setSetestado(setestado);
			cotevento.setIplog(usuarioBean.getIp());
			cotevento.setSetusuario(usuarioBean.getSetUsuario());
			
    		if(isAllDay){
    			Calendar endDate = Calendar.getInstance();
    			endDate.setTime(cotevento.getFechadesde());
    			endDate.set(Calendar.DATE, endDate.get(Calendar.DATE)+1);
    			endDate.set(Calendar.SECOND, endDate.get(Calendar.SECOND)-1);
    			
    			Date fechaHasta = endDate.getTime();
    			cotevento.setFechahasta(fechaHasta);
    		}
			
			coteventoDAO.saveCotevento(session, cotevento);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception e){
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
			
			CoteventoDAO coteventoDAO = new CoteventoDAO();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotevento.setFecharegistro(fecharegistro);
			cotevento.setIplog(usuarioBean.getIp());
			cotevento.setSetusuario(usuarioBean.getSetUsuario());
			coteventoDAO.updateCotevento(session, cotevento);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception e){
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
			
			CoteventoDAO coteventoDAO = new CoteventoDAO();
			
			coteventoDAO.deleteCotevento(session, id);
			session.getTransaction().commit();
			ok = true;
		} catch(Exception e){
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
			
			CoteventoDAO coteventoDAO = new CoteventoDAO();
			
			lisCotevento = coteventoDAO.lisCotevento(session, fechadesde, fechahasta);
		} catch(Exception e){
			throw new RuntimeException();
		} finally {
			session.close();
		}
		
		return lisCotevento;
	}

}
