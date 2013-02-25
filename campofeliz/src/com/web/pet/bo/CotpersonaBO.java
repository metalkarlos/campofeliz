package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;


import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.CotpersonaDAOInterface;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.HibernateUtil;

public class CotpersonaBO {
	
	private CotpersonaDAOInterface cotpersonaDAOInterface;
	
	public CotpersonaBO() {
		try{
			cotpersonaDAOInterface = (CotpersonaDAOInterface) CotpersonaBO.class.getClassLoader().loadClass("com.web.pet.dao.CotpersonaDAO").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Problemas al cargar la interfaz CotpersonaDAOInterface");
		}
	}
	
	public Cotpersona getCotpersonaById(int idpersona) throws Exception{
		Cotpersona cotpersona = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			cotpersona = cotpersonaDAOInterface.getCotpersonaById(session, idpersona);
		}catch(Exception e){
			e.printStackTrace();
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
			lisCotpersona = cotpersonaDAOInterface.lisCotpersonaByPage(session, nombres, pageSize, pageNumber, args);
			
			for(Cotpersona cotpersona : lisCotpersona){
				FileUtil fileUtil = new FileUtil();
				String realpath = new FacesUtil().getRealPath("");
				if(cotpersona.getRuta() == null || !fileUtil.existFile(realpath+cotpersona.getRuta()))
				{
					cotpersona.setRuta(Parametro.BLANK_IMAGE_PATH);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
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
			lisCotpersona = cotpersonaDAOInterface.lisCotpersonaPetmascotaByPage(session, nombres, pageSize, pageNumber, args);
			
			for(Cotpersona cotpersona : lisCotpersona){
				FileUtil fileUtil = new FileUtil();
				String realpath = new FacesUtil().getRealPath("");
				if(cotpersona.getRuta() == null || !fileUtil.existFile(realpath+cotpersona.getRuta()))
				{
					cotpersona.setRuta(Parametro.BLANK_IMAGE_PATH);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
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
			lisCotpersona = cotpersonaDAOInterface.lisCotpersonaBusqueda(session, cotpersona);
			
			for(Cotpersona persona : lisCotpersona){
				FileUtil fileUtil = new FileUtil();
				String realpath = new FacesUtil().getRealPath("");
				if(persona.getRuta() == null || !fileUtil.existFile(realpath+persona.getRuta()))
				{
					persona.setRuta(Parametro.BLANK_IMAGE_PATH);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
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
			lisCotpersona = cotpersonaDAOInterface.lisCotpersonaBusquedaByPage(session, cotpersona, pageSize, pageNumber, args);
			
			for(Cotpersona persona : lisCotpersona){
				FileUtil fileUtil = new FileUtil();
				String realpath = new FacesUtil().getRealPath("");
				if(persona.getRuta() == null || !fileUtil.existFile(realpath+persona.getRuta()))
				{
					persona.setRuta(Parametro.BLANK_IMAGE_PATH);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
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
			
			int max = cotpersonaDAOInterface.maxIdCotpersona(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotpersona.setIdpersona(max);
			cotpersona.setFecharegistro(fecharegistro);
			cotpersona.setIplog(usuarioBean.getIp());
			cotpersona.getCotestado().setIdestado(1);
			cotpersona.setSetusuario(usuarioBean.getSetUsuario());
			
			cotpersona.setObjeto(null);
			cotpersonaDAOInterface.saveCotpersona(session, cotpersona);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception e){
			e.printStackTrace();
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
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotpersona.setFecharegistro(fecharegistro);
			cotpersona.setIplog(usuarioBean.getIp());
			cotpersona.setSetusuario(usuarioBean.getSetUsuario());
			
			cotpersona.setObjeto(null);
			cotpersonaDAOInterface.updateCotpersona(session, cotpersona);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return ok;
	}

}
