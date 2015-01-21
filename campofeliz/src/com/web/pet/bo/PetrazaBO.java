package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.PetrazaDAOInterface;
import com.web.pet.pojo.annotations.Petraza;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetrazaBO {
	
	private PetrazaDAOInterface petrazaDAOInterface;
	
	public PetrazaBO() throws RuntimeException {
		try{
			petrazaDAOInterface = (PetrazaDAOInterface) PetrazaBO.class.getClassLoader().loadClass("com.web.pet.dao.PetrazaDAO").newInstance();
        }catch(Exception ex){
            throw new RuntimeException("Problemas al cargar la interfaz PetrazaDAOInterface");
        }
	}
	
	public List<Petraza> lisRazas() throws Exception {
		List<Petraza> lisPetraza = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			lisPetraza = petrazaDAOInterface.lisPetraza(session);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisPetraza;
	}
	
	public Petraza getPetrazaById(int id) throws Exception {
		Petraza petraza = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			petraza = petrazaDAOInterface.getPetrazaById(session, id);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return petraza;
	}
	
	public List<Petraza> lisPetrazaByPage(int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Petraza> lisPetraza = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			lisPetraza = petrazaDAOInterface.lisPetrazaByPage(session, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisPetraza;
	}
	
	public boolean newPetraza(Petraza petraza) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			int maxid = petrazaDAOInterface.maxIdPetraza(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petraza.setIdraza(maxid);
			petraza.setFecharegistro(fecharegistro);
			petraza.setIplog(usuarioBean.getIp());
			petraza.getPetestado().setIdestado(1);
			petraza.setSetusuario(usuarioBean.getSetUsuario());
	
			petrazaDAOInterface.savePetraza(session, petraza);
			
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
	
	public boolean updatePetraza(Petraza petraza) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petraza.setFecharegistro(fecharegistro);
			petraza.setIplog(usuarioBean.getIp());
			petraza.setSetusuario(usuarioBean.getSetUsuario());
			petrazaDAOInterface.updatePetraza(session, petraza);
			
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
