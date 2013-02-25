package com.web.pet.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.PetmascotacolorDAOInterface;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetmascotacolorBO {
	private PetmascotacolorDAOInterface petmascotacolorDAOInterface;

	public PetmascotacolorBO() throws Exception {
		try{
			petmascotacolorDAOInterface = (PetmascotacolorDAOInterface)PetmascotacolorBO.class.getClassLoader().loadClass("com.web.pet.dao.PetmascotacolorDAO").newInstance();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Problemas al cargar la interfaz PetmascotacolorDAOInterface");
		}
	}
	
	public List<Petmascotacolor> lisPetmascotacolor(int idmascota) throws Exception{
		List<Petmascotacolor> lisPetmascotacolor = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			lisPetmascotacolor = petmascotacolorDAOInterface.lisPetmascotacolor(session, idmascota);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception();
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
			
			int maxid = petmascotacolorDAOInterface.maxIdPetmascotacolor(session);
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petmascotacolor.setIdmascotacolor(maxid);
			petmascotacolor.setFecharegistro(fecharegistro);
			petmascotacolor.setIplog(usuarioBean.getIp());
			petmascotacolor.getPetestado().setIdestado(1);
			petmascotacolor.setSetusuario(usuarioBean.getSetUsuario());
			
			petmascotacolorDAOInterface.savePetmascotacolor(session, petmascotacolor);
			
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
