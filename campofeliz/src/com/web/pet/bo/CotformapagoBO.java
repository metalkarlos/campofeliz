package com.web.pet.bo;

import java.util.List;

import org.hibernate.Session;

import com.web.pet.dao.CotformapagoDAO;
import com.web.pet.pojo.annotations.Cotformapago;
import com.web.util.HibernateUtil;

public class CotformapagoBO {
	
	public Cotformapago getCotformapagoById(int id) throws Exception{
		Cotformapago cotformapago = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotformapagoDAO cotformapagoDAO = new CotformapagoDAO();
			
			cotformapago = cotformapagoDAO.getCotformapagoById(session, id);
		} catch(Exception he) {
			throw new Exception();
		} finally {
			session.close();
		}
		
		return cotformapago;
	}
	
	public List<Cotformapago> lisCotformapago() throws Exception {
		List<Cotformapago> lisCotformapago = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotformapagoDAO cotformapagoDAO = new CotformapagoDAO();
			
			lisCotformapago = cotformapagoDAO.lisCotformapago(session);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisCotformapago;
	}
	
	/*public List<Cotcolor> lisCotcolorByPage(int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Cotcolor> lisCotcolor = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotcolorDAO cotcolorDAO = new CotcolorDAO();
			
			lisCotcolor = cotcolorDAO.lisCotcolorByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException();
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
			throw new Exception(); 
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
			throw new Exception();
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
			throw new Exception();
		}finally{
			session.close();
		}
		
		return ok;
	}*/
}
