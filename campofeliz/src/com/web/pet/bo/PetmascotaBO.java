package com.web.pet.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetfotoDAO;
import com.web.pet.dao.PetmascotaDAO;
import com.web.pet.dao.PetmascotacolorDAO;
import com.web.pet.pojo.annotations.Mascotas;
import com.web.pet.pojo.annotations.Petestado;
import com.web.pet.pojo.annotations.Petfoto;
import com.web.pet.pojo.annotations.Petmascota;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetmascotaBO {

	public List<Petmascota> lisPetMascotaTipo(int tipo) throws Exception {
		List<Petmascota> dataset = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			
			dataset = petmascotaDAO.lisPetmascota(session, tipo);
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return dataset;
	}
	
	/*public Petmascota getById(int id) throws Exception {
		Petmascota petmascota = new Petmascota();
		Session session = null;

		try{
			session = HibernateUtil.getSessionFactory().openSession();
			petmascota = petmascotaDAOInterface.getPetmascotaById(session, id);
		}catch(Exception he){
			he.printStackTrace();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return petmascota;
	}*/
	
	public boolean updatePet(Petmascota petmascota, List<Petmascotacolor> lisPetmascotacolorOld, List<Petmascotacolor> lisPetmascotacolor) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetmascotacolorDAO petmascotacolorDAO = new PetmascotacolorDAO();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petmascota.setFecharegistro(fecharegistro);
			petmascota.setIplog(usuarioBean.getIp());
			petmascota.setSetusuario(usuarioBean.getSetUsuario());
			petmascotaDAO.updatePetmascota(session, petmascota);
			
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
					petmascotacolor.getPetestado().setIdestado(1);
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
					Petestado petestado = new Petestado();
					petestado.setIdestado(2);//inactivo
					petmascotacolorOld.setPetestado(petestado);
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
	
	public boolean newPet(Petmascota petmascota, List<Petmascotacolor> lisPetmascotacolor) throws Exception {
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
			
			petmascota.setIdmascota(maxid);
			petmascota.setFecharegistro(fecharegistro);
			petmascota.setIplog(usuarioBean.getIp());
			petmascota.getPetestado().setIdestado(1);
			petmascota.setSetusuario(usuarioBean.getSetUsuario());
	
			petmascotaDAO.savePetmascota(session, petmascota);
			
			//ingresar colores nuevos
			for(Petmascotacolor petmascotacolor : lisPetmascotacolor){
				int maxidPetmascotacolor = petmascotacolorDAO.maxIdPetmascotacolor(session)+1;
				
				//petmascotacolor.getPetmascota().setIdmascota(maxid);
				petmascotacolor.setPetmascota(petmascota);
				petmascotacolor.setIdmascotacolor(maxidPetmascotacolor);
				petmascotacolor.setFecharegistro(fecharegistro);
				petmascotacolor.setIplog(usuarioBean.getIp());
				petmascotacolor.getPetestado().setIdestado(1);
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
	
	public Petmascota getPetmascotaById(int idmascota) throws Exception {
		Petmascota petmascota = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			
			petmascota = petmascotaDAO.getPetmascotaById(session, idmascota);
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return petmascota;
	}
	
	public List<Mascotas> lisMascotas(int tipo) throws Exception {
		List<Mascotas> lisMascotas = null;
		Session session = null;
		
		try{
			lisMascotas = new ArrayList<Mascotas>();
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			List<Petmascota> lisPetmascota = petmascotaDAO.lisPetmascota(session, tipo);
			
			for(Petmascota petmascota : lisPetmascota){
				Petfoto petfoto = petfotoDAO.getPetfotoPerfilByPetId(session, petmascota.getIdmascota());
				
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfoto(petfoto);
				mascotas.setPetmascota(petmascota);
				lisMascotas.add(mascotas);
			}
			
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisMascotas;
	}
	
	public List<Mascotas> lisMascotasByEspecieByPage(int especie, String nombre, int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Mascotas> lisMascotas = null;
		Session session = null;
		
		try{
			lisMascotas = new ArrayList<Mascotas>();
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			List<Petmascota> lisPetmascota = petmascotaDAO.lisPetmascotaByEspecieByPage(session, especie, nombre, pageSize, pageNumber, args);
			
			for(Petmascota petmascota : lisPetmascota){
				Petfoto petfoto = petfotoDAO.getPetfotoPerfilByPetId(session, petmascota.getIdmascota());
				
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfoto(petfoto);
				mascotas.setPetmascota(petmascota);
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
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			List<Petmascota> lisPetmascota = petmascotaDAO.lisPetmascotaByPage(session, nombre, pageSize, pageNumber, args);
			
			for(Petmascota petmascota : lisPetmascota){
				Petfoto petfoto = petfotoDAO.getPetfotoPerfilByPetId(session, petmascota.getIdmascota());

				Mascotas mascotas = new Mascotas();
				mascotas.setPetfoto(petfoto);
				mascotas.setPetmascota(petmascota);
				lisMascotas.add(mascotas);
			}
			
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisMascotas;
	}
	
	public List<Mascotas> lisMascotasBusqueda(Petmascota petmascota, String[] caracteristicas) throws Exception{
		List<Mascotas> lisMascotas = null;
		Session session = null;
		
		try {
			lisMascotas = new ArrayList<Mascotas>();
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			List<Petmascota> lisPetmascota = petmascotaDAO.lisPetmascotaBusqueda(session, petmascota, caracteristicas);
			
			for(Petmascota petmascota1 : lisPetmascota){
				Petfoto petfoto = petfotoDAO.getPetfotoPerfilByPetId(session, petmascota1.getIdmascota());
				
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfoto(petfoto);
				mascotas.setPetmascota(petmascota1);
				lisMascotas.add(mascotas);
			}
		} catch(Exception e){
			throw new Exception();
		} finally {
			session.close();
		}
		
		
		return lisMascotas;
	}
	
	public List<Mascotas> lisMascotasBusquedaByPage(Petmascota petmascota, String[] caracteristicas, int pageSize, int pageNumber, int args[]) throws RuntimeException{
		List<Mascotas> lisMascotas = null;
		Session session = null;
		
		try {
			lisMascotas = new ArrayList<Mascotas>();
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			List<Petmascota> lisPetmascota = petmascotaDAO.lisPetmascotaBusquedaByPage(session, petmascota, caracteristicas, pageSize, pageNumber, args);
			
			for(Petmascota petmascota1 : lisPetmascota){
				Petfoto petfoto = petfotoDAO.getPetfotoPerfilByPetId(session, petmascota1.getIdmascota());
				
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfoto(petfoto);
				mascotas.setPetmascota(petmascota1);
				lisMascotas.add(mascotas);
			}
		} catch(Exception e){
			throw new RuntimeException();
		} finally {
			session.close();
		}
		
		
		return lisMascotas;
	}

}
