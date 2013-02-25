package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.CottiposervicioDAOInterface;
import com.web.pet.pojo.annotations.Cottiposervicio;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CottiposervicioBO {
	
	private CottiposervicioDAOInterface cottiposervicioDAOInterface;

	public CottiposervicioBO() throws RuntimeException {
		try {
			cottiposervicioDAOInterface = (CottiposervicioDAOInterface) CottiposervicioBO.class.getClassLoader().loadClass("com.web.pet.dao.CottiposervicioDAO").newInstance();
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Problemas al cargar la interfaz CottiposervicioDAOInterface");
		}
	}
	
	public Cottiposervicio getCottiposervicioById(int id) throws Exception {
		Cottiposervicio cottiposervicio = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			cottiposervicio = cottiposervicioDAOInterface.getCottiposervicioById(session, id);
		} catch(Exception e){
			e.printStackTrace();
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
			lisCottiposervicio = cottiposervicioDAOInterface.lisCottiposervicioByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			he.printStackTrace();
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
			
			int maxid = cottiposervicioDAOInterface.maxIdCottiposervicio(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cottiposervicio.setIdtiposervicio(maxid);
			cottiposervicio.setFecharegistro(fecharegistro);
			cottiposervicio.setIplog(usuarioBean.getIp());
			cottiposervicio.getCotestado().setIdestado(1);
			cottiposervicio.setSetusuario(usuarioBean.getSetUsuario());
	
			cottiposervicioDAOInterface.saveCottiposervicio(session, cottiposervicio);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception he){
			he.printStackTrace();
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
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cottiposervicio.setFecharegistro(fecharegistro);
			cottiposervicio.setIplog(usuarioBean.getIp());
			cottiposervicio.setSetusuario(usuarioBean.getSetUsuario());
			cottiposervicioDAOInterface.updateCottiposervicio(session, cottiposervicio);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception he){
			he.printStackTrace();
			session.getTransaction().rollback();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return ok;
	}

}
