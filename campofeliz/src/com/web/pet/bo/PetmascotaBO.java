package com.web.pet.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetfotomascotaDAO;
import com.web.pet.dao.PetmascotaDAO;
import com.web.pet.dao.PetmascotacolorDAO;
import com.web.pet.pojo.annotations.Mascotas;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Petfotomascota;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetmascotaBO {

	public boolean updatePet(Petmascotahomenaje petmascotahomenaje, List<Petmascotacolor> lisPetmascotacolorOld, List<Petmascotacolor> lisPetmascotacolor) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetmascotacolorDAO petmascotacolorDAO = new PetmascotacolorDAO();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petmascotahomenaje.setFecharegistro(fecharegistro);
			petmascotahomenaje.setIplog(usuarioBean.getIp());
			petmascotahomenaje.setSetusuario(usuarioBean.getSetUsuario());
			petmascotaDAO.updatePetmascota(session, petmascotahomenaje);
			
			//ingresar colores nuevos
			boolean isnew = true;
			for(Petmascotacolor petmascotacolor : lisPetmascotacolor){
				isnew = true;
				for(Petmascotacolor petmascotacolorOld : lisPetmascotacolorOld){
					if(petmascotacolor.getCotcolor().getIdcolor() == petmascotacolorOld.getCotcolor().getIdcolor()){
						isnew = false;
						break;
					}
				}
				if(isnew){
					int maxid = petmascotacolorDAO.maxIdPetmascotacolor(session)+1;
					
					petmascotacolor.setIdmascotacolor(maxid);
					petmascotacolor.setFecharegistro(fecharegistro);
					petmascotacolor.setIplog(usuarioBean.getIp());
					petmascotacolor.getSetestado().setIdestado(1);
					petmascotacolor.setSetusuario(usuarioBean.getSetUsuario());
					
					petmascotacolorDAO.savePetmascotacolor(session, petmascotacolor);
				}
			}
			//inactivar colores removidos
			boolean isremoved = true;
			for(Petmascotacolor petmascotacolorOld : lisPetmascotacolorOld){
				isremoved = true;
				for(Petmascotacolor petmascotacolor : lisPetmascotacolor){
					if(petmascotacolorOld.getCotcolor().getIdcolor() == petmascotacolor.getCotcolor().getIdcolor()){
						isremoved = false;
						break;
					}
				}
				if(isremoved){
					petmascotacolorOld.setFecharegistro(fecharegistro);
					petmascotacolorOld.setIplog(usuarioBean.getIp());
					Setestado setestado = new Setestado();
					setestado.setIdestado(2);//inactivo
					petmascotacolorOld.setSetestado(setestado);
					petmascotacolorOld.setSetusuario(usuarioBean.getSetUsuario());
					
					petmascotacolorDAO.updatePetmascotacolor(session, petmascotacolorOld);
				}
			}
			
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
	
	public boolean newPet(Petmascotahomenaje petmascotahomenaje, List<Petmascotacolor> lisPetmascotacolor) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetmascotacolorDAO petmascotacolorDAO = new PetmascotacolorDAO();
			
			int maxid = petmascotaDAO.maxIdPetmascota(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petmascotahomenaje.setIdmascota(maxid);
			petmascotahomenaje.setFecharegistro(fecharegistro);
			petmascotahomenaje.setIplog(usuarioBean.getIp());
			petmascotahomenaje.getSetestado().setIdestado(1);
			petmascotahomenaje.setSetusuario(usuarioBean.getSetUsuario());
	
			petmascotaDAO.savePetmascota(session, petmascotahomenaje);
			
			//ingresar colores nuevos
			for(Petmascotacolor petmascotacolor : lisPetmascotacolor){
				int maxidPetmascotacolor = petmascotacolorDAO.maxIdPetmascotacolor(session)+1;
				
				//petmascotacolor.getPetmascota().setIdmascota(maxid);
				petmascotacolor.setPetmascotahomenaje(petmascotahomenaje);
				petmascotacolor.setIdmascotacolor(maxidPetmascotacolor);
				petmascotacolor.setFecharegistro(fecharegistro);
				petmascotacolor.setIplog(usuarioBean.getIp());
				petmascotacolor.getSetestado().setIdestado(1);
				petmascotacolor.getSetusuario().setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
				
				petmascotacolorDAO.savePetmascotacolor(session, petmascotacolor);
			}
			
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
	
	public Petmascotahomenaje getPetmascotaById(int idmascota) throws Exception {
		Petmascotahomenaje petmascotahomenaje = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			
			petmascotahomenaje = petmascotaDAO.getPetmascotaById(session, idmascota);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return petmascotahomenaje;
	}
	
	public List<Mascotas> lisMascotasByEspecieByPage(int especie, String nombre, int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Mascotas> lisMascotas = null;
		Session session = null;
		
		try{
			lisMascotas = new ArrayList<Mascotas>();
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			List<Petmascotahomenaje> lisPetmascotahomenaje = petmascotaDAO.lisPetmascotaByEspecieByPage(session, especie, nombre, pageSize, pageNumber, args);
			
			for(Petmascotahomenaje petmascotahomenaje : lisPetmascotahomenaje){
				Petfotomascota petfotomascota = petfotoDAO.getPetfotomascotaPerfilByIdmascota(session, petmascotahomenaje.getIdmascota());
				
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfotomascota(petfotomascota);
				mascotas.setPetmascotahomenaje(petmascotahomenaje);
				lisMascotas.add(mascotas);
			}
			
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisMascotas;
	}
	
	public List<Mascotas> lisMascotasByPage(String nombre, int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Mascotas> lisMascotas = null;
		Session session = null;
		
		try{
			lisMascotas = new ArrayList<Mascotas>();
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			List<Petmascotahomenaje> lisPetmascotahomenaje = petmascotaDAO.lisPetmascotaByPage(session, nombre, pageSize, pageNumber, args);
			
			for(Petmascotahomenaje petmascotahomenaje : lisPetmascotahomenaje){
				Petfotomascota petfotomascota = petfotoDAO.getPetfotomascotaPerfilByIdmascota(session, petmascotahomenaje.getIdmascota());

				Mascotas mascotas = new Mascotas();
				mascotas.setPetfotomascota(petfotomascota);
				mascotas.setPetmascotahomenaje(petmascotahomenaje);
				lisMascotas.add(mascotas);
			}
			
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisMascotas;
	}
	
	public List<Mascotas> lisMascotasBusquedaByPage(Petmascotahomenaje petmascotahomenaje, String[] caracteristicas, int pageSize, int pageNumber, int args[]) throws RuntimeException{
		List<Mascotas> lisMascotas = null;
		Session session = null;
		
		try {
			lisMascotas = new ArrayList<Mascotas>();
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			List<Petmascotahomenaje> lisPetmascotahomenaje = petmascotaDAO.lisPetmascotaBusquedaByPage(session, petmascotahomenaje, caracteristicas, pageSize, pageNumber, args);
			
			for(Petmascotahomenaje petmascotahomenaje1 : lisPetmascotahomenaje){
				Petfotomascota petfotomascota = petfotoDAO.getPetfotomascotaPerfilByIdmascota(session, petmascotahomenaje1.getIdmascota());
				
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfotomascota(petfotomascota);
				mascotas.setPetmascotahomenaje(petmascotahomenaje1);
				lisMascotas.add(mascotas);
			}
		} catch(Exception e){
			throw new RuntimeException();
		} finally {
			session.close();
		}
		
		
		return lisMascotas;
	}

	public void grabarMascotaBasico(Session session, Petmascotahomenaje petmascotahomenaje) throws Exception {
		
		PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
		
		int maxid = petmascotaDAO.maxIdPetmascota(session)+1;
		Date fecharegistro = new Date();
		UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
		
		petmascotahomenaje.setIdmascota(maxid);
		petmascotahomenaje.setFecharegistro(fecharegistro);
		petmascotahomenaje.setIplog(usuarioBean.getIp());
		petmascotahomenaje.getSetestado().setIdestado(1);
		petmascotahomenaje.setSetusuario(usuarioBean.getSetUsuario());

		petmascotaDAO.savePetmascota(session, petmascotahomenaje);
		
	}
}
