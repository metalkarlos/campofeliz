package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.CotcolorDAOInterface;
import com.web.pet.pojo.annotations.Cotcolor;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class CotcolorBO {
	
	private CotcolorDAOInterface cotcolorDAOInterface;
	
	public CotcolorBO() throws RuntimeException {
		try{
			cotcolorDAOInterface = (CotcolorDAOInterface) CotcolorBO.class.getClassLoader().loadClass("com.web.pet.dao.CotcolorDAO").newInstance();
        }catch(Exception ex){
            throw new RuntimeException("Problemas al cargar la interfaz CotcolorDAOInterface");
        }
	}

	public List<Cotcolor> lisCotcolor() throws Exception {
		List<Cotcolor> lisCotcolor = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			lisCotcolor = cotcolorDAOInterface.lisCotcolor(session);
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
			cotcolor = cotcolorDAOInterface.getCotcolorById(session, id);
		} catch(Exception he) {
			throw new Exception();
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
			lisCotcolor = cotcolorDAOInterface.lisCotcolorByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisCotcolor;
	}
	
	public boolean newCotcolor(Cotcolor cotcolor) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			int maxid = cotcolorDAOInterface.maxIdCotcolor(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotcolor.setIdcolor(maxid);
			cotcolor.setFecharegistro(fecharegistro);
			cotcolor.setIplog(usuarioBean.getIp());
			cotcolor.getCotestado().setIdestado(1);
			cotcolor.setSetusuario(usuarioBean.getSetUsuario());
	
			cotcolorDAOInterface.saveCotcolor(session, cotcolor);
			
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
	
	public boolean updateCotcolor(Cotcolor cotcolor) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			cotcolor.setFecharegistro(fecharegistro);
			cotcolor.setIplog(usuarioBean.getIp());
			cotcolor.setSetusuario(usuarioBean.getSetUsuario());
			cotcolorDAOInterface.updateCotcolor(session, cotcolor);
			
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
