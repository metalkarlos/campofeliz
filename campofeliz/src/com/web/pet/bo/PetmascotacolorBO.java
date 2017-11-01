package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetmascotacolorDAO;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetmascotacolorBO {

	public List<Petmascotacolor> lisPetmascotacolor(int idmascota) throws Exception{
		List<Petmascotacolor> lisPetmascotacolor = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotacolorDAO petmascotacolorDAO = new PetmascotacolorDAO();
			
			lisPetmascotacolor = petmascotacolorDAO.lisPetmascotacolor(session, idmascota);
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return lisPetmascotacolor;
	}
	
	public boolean newPetmascotacolor(Petmascotacolor petmascotacolor) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetmascotacolorDAO petmascotacolorDAO = new PetmascotacolorDAO();
			
			int maxid = petmascotacolorDAO.maxIdPetmascotacolor(session);
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petmascotacolor.setIdmascotacolor(maxid);
			petmascotacolor.setFecharegistro(fecharegistro);
			petmascotacolor.setIplog(usuarioBean.getIp());
			petmascotacolor.getSetestado().setIdestado(1);
			petmascotacolor.setSetusuario(usuarioBean.getSetUsuario());
			
			petmascotacolorDAO.savePetmascotacolor(session, petmascotacolor);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception e){
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return ok;
	}
}
