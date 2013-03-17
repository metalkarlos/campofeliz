package com.web.pet.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.PetfotoDAOInterface;
import com.web.pet.daointerface.PetmascotaDAOInterface;
import com.web.pet.daointerface.PetmascotacolorDAOInterface;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Mascotas;
import com.web.pet.pojo.annotations.Petestado;
import com.web.pet.pojo.annotations.Petfoto;
import com.web.pet.pojo.annotations.Petmascota;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.HibernateUtil;

public class PetmascotaBO {

	private PetmascotaDAOInterface petmascotaDAOInterface;
	private PetfotoDAOInterface petfotoDAOInterface;
	private PetmascotacolorDAOInterface petmascotacolorDAOInterface;
	
	public PetmascotaBO() throws RuntimeException {
		
		try {
			petmascotaDAOInterface = (PetmascotaDAOInterface) PetmascotaBO.class.getClassLoader().loadClass("com.web.pet.dao.PetmascotaDAO").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Problemas al cargar la interfaz PetmascotaDAOInterface");
		} 
        
        try{
        	petfotoDAOInterface = (PetfotoDAOInterface) PetmascotaBO.class.getClassLoader().loadClass("com.web.pet.dao.PetfotoDAO").newInstance();
        }catch(Exception e){
        	e.printStackTrace();
			throw new RuntimeException();
        }
        
        try{
        	petmascotacolorDAOInterface = (PetmascotacolorDAOInterface) PetmascotaBO.class.getClassLoader().loadClass("com.web.pet.dao.PetmascotacolorDAO").newInstance();
        }catch(Exception e){
        	e.printStackTrace();
			throw new RuntimeException();
        }
	}
	
	public List<Petmascota> lisPetMascotaTipo(int tipo) throws Exception {
		List<Petmascota> dataset = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			dataset = petmascotaDAOInterface.lisPetmascota(session, tipo);
		}catch(Exception he){
			he.printStackTrace();
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
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petmascota.setFecharegistro(fecharegistro);
			petmascota.setIplog(usuarioBean.getIp());
			petmascota.setSetusuario(usuarioBean.getSetUsuario());
			petmascotaDAOInterface.updatePetmascota(session, petmascota);
			
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
					int maxid = petmascotacolorDAOInterface.maxIdPetmascotacolor(session)+1;
					
					petmascotacolor.setIdmascotacolor(maxid);
					petmascotacolor.setFecharegistro(fecharegistro);
					petmascotacolor.setIplog(usuarioBean.getIp());
					petmascotacolor.getPetestado().setIdestado(1);
					petmascotacolor.setSetusuario(usuarioBean.getSetUsuario());
					
					petmascotacolorDAOInterface.savePetmascotacolor(session, petmascotacolor);
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
					
					petmascotacolorDAOInterface.updatePetmascotacolor(session, petmascotacolorOld);
				}
			}
			
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
	
	public boolean newPet(Petmascota petmascota, List<Petmascotacolor> lisPetmascotacolor) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			int maxid = petmascotaDAOInterface.maxIdPetmascota(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petmascota.setIdmascota(maxid);
			petmascota.setFecharegistro(fecharegistro);
			petmascota.setIplog(usuarioBean.getIp());
			petmascota.getPetestado().setIdestado(1);
			petmascota.setSetusuario(usuarioBean.getSetUsuario());
	
			petmascotaDAOInterface.savePetmascota(session, petmascota);
			
			//ingresar colores nuevos
			for(Petmascotacolor petmascotacolor : lisPetmascotacolor){
				int maxidPetmascotacolor = petmascotacolorDAOInterface.maxIdPetmascotacolor(session)+1;
				
				//petmascotacolor.getPetmascota().setIdmascota(maxid);
				petmascotacolor.setPetmascota(petmascota);
				petmascotacolor.setIdmascotacolor(maxidPetmascotacolor);
				petmascotacolor.setFecharegistro(fecharegistro);
				petmascotacolor.setIplog(usuarioBean.getIp());
				petmascotacolor.getPetestado().setIdestado(1);
				petmascotacolor.getSetusuario().setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
				
				petmascotacolorDAOInterface.savePetmascotacolor(session, petmascotacolor);
			}
			
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
	
	public Petmascota getPetmascotaById(int idmascota) throws Exception {
		Petmascota petmascota = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			petmascota = petmascotaDAOInterface.getPetmascotaById(session, idmascota);
		}catch(Exception he){
			he.printStackTrace();
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
			List<Petmascota> lisPetmascota = petmascotaDAOInterface.lisPetmascota(session, tipo);
			FileUtil fileUtil = new FileUtil();
			Properties systemProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
			String resources_server_url = systemProperties.getProperty("resources_server_url");
			String resources_server_war = systemProperties.getProperty("resources_server_war");
			
			for(Petmascota petmascota : lisPetmascota){
				Petfoto petfoto = petfotoDAOInterface.getPetfotoPerfilByPetId(session, petmascota.getIdmascota());
				
				if(petfoto != null){
					String mascotaPath = Parametro.DEPLOYMENTS_PATH+Parametro.FILE_SEPARATOR+resources_server_war+petfoto.getRuta();
					if(fileUtil.existFile(mascotaPath)){
						petfoto.setRuta(resources_server_url + petfoto.getRuta());
					}else{
						petfoto.setRuta(resources_server_url + Parametro.BLANK_IMAGE_PATH);
					}
				}else{
					petfoto = new Petfoto();
					petfoto.setRuta(resources_server_url + Parametro.BLANK_IMAGE_PATH);
				}
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfoto(petfoto);
				mascotas.setPetmascota(petmascota);
				lisMascotas.add(mascotas);
			}
			
		}catch(Exception he){
			he.printStackTrace();
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
			List<Petmascota> lisPetmascota = petmascotaDAOInterface.lisPetmascotaByEspecieByPage(session, especie, nombre, pageSize, pageNumber, args);
			FileUtil fileUtil = new FileUtil();
			Properties petsoftProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
			String resources_server_url = petsoftProperties.getProperty("resources_server_url");
			String resources_server_war = petsoftProperties.getProperty("resources_server_war");
			
			for(Petmascota petmascota : lisPetmascota){
				Petfoto petfoto = petfotoDAOInterface.getPetfotoPerfilByPetId(session, petmascota.getIdmascota());
				
				if(petfoto != null){
					String mascotaPath = Parametro.DEPLOYMENTS_PATH+Parametro.FILE_SEPARATOR+resources_server_war+petfoto.getRuta();
					if(fileUtil.existFile(mascotaPath)){
						petfoto.setRuta(resources_server_url + petfoto.getRuta());
					}else{
						petfoto.setRuta(resources_server_url + Parametro.BLANK_IMAGE_PATH);
					}
				}else{
					petfoto = new Petfoto();
					petfoto.setRuta(resources_server_url + Parametro.BLANK_IMAGE_PATH);
				}
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfoto(petfoto);
				mascotas.setPetmascota(petmascota);
				lisMascotas.add(mascotas);
			}
			
		}catch(Exception he){
			he.printStackTrace();
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
			List<Petmascota> lisPetmascota = petmascotaDAOInterface.lisPetmascotaByPage(session, nombre, pageSize, pageNumber, args);
			FileUtil fileUtil = new FileUtil();
			Properties petsoftProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
			String resources_server_url = petsoftProperties.getProperty("resources_server_url");
			String resources_server_war = petsoftProperties.getProperty("resources_server_war");
			
			for(Petmascota petmascota : lisPetmascota){
				Petfoto petfoto = petfotoDAOInterface.getPetfotoPerfilByPetId(session, petmascota.getIdmascota());

				if(petfoto != null){
					String mascotaPath = Parametro.DEPLOYMENTS_PATH+Parametro.FILE_SEPARATOR+resources_server_war+petfoto.getRuta();
					
					if(fileUtil.existFile(mascotaPath)){
						petfoto.setRuta(resources_server_url + petfoto.getRuta());
					}
					else{
						petfoto.setRuta(resources_server_url + Parametro.BLANK_IMAGE_PATH);
					}
				}else{
					petfoto = new Petfoto();
					petfoto.setRuta(resources_server_url + Parametro.BLANK_IMAGE_PATH);
				}
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfoto(petfoto);
				mascotas.setPetmascota(petmascota);
				lisMascotas.add(mascotas);
			}
			
		}catch(Exception he){
			he.printStackTrace();
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
			List<Petmascota> lisPetmascota = petmascotaDAOInterface.lisPetmascotaBusqueda(session, petmascota, caracteristicas);
			FileUtil fileUtil = new FileUtil();
			Properties petsoftProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
			String resources_server_url = petsoftProperties.getProperty("resources_server_url");
			String resources_server_war = petsoftProperties.getProperty("resources_server_war");
			
			for(Petmascota petmascota1 : lisPetmascota){
				Petfoto petfoto = petfotoDAOInterface.getPetfotoPerfilByPetId(session, petmascota1.getIdmascota());
				
				if(petfoto != null){
					String mascotaPath = Parametro.DEPLOYMENTS_PATH+Parametro.FILE_SEPARATOR+resources_server_war+petfoto.getRuta();
					if(fileUtil.existFile(mascotaPath)){
						petfoto.setRuta(resources_server_url + petfoto.getRuta());
					}else{
						petfoto.setRuta(resources_server_url + Parametro.BLANK_IMAGE_PATH);
					}
				}else{
					petfoto = new Petfoto();
					petfoto.setRuta(resources_server_url + Parametro.BLANK_IMAGE_PATH);
				}
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfoto(petfoto);
				mascotas.setPetmascota(petmascota1);
				lisMascotas.add(mascotas);
			}
		} catch(Exception e){
			e.printStackTrace();
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
			List<Petmascota> lisPetmascota = petmascotaDAOInterface.lisPetmascotaBusquedaByPage(session, petmascota, caracteristicas, pageSize, pageNumber, args);
			FileUtil fileUtil = new FileUtil();
			Properties petsoftProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
			String resources_server_url = petsoftProperties.getProperty("resources_server_url");
			String resources_server_war = petsoftProperties.getProperty("resources_server_war");
			
			for(Petmascota petmascota1 : lisPetmascota){
				Petfoto petfoto = petfotoDAOInterface.getPetfotoPerfilByPetId(session, petmascota1.getIdmascota());
				
				if(petfoto != null){
					String mascotaPath = Parametro.DEPLOYMENTS_PATH+Parametro.FILE_SEPARATOR+resources_server_war+petfoto.getRuta();
					if(fileUtil.existFile(mascotaPath)){
						petfoto.setRuta(resources_server_url + petfoto.getRuta());
					}
					else{
						petfoto.setRuta(resources_server_url + Parametro.BLANK_IMAGE_PATH);
					}
				}else{
					petfoto = new Petfoto();
					petfoto.setRuta(resources_server_url + Parametro.BLANK_IMAGE_PATH);
				}
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfoto(petfoto);
				mascotas.setPetmascota(petmascota1);
				lisMascotas.add(mascotas);
			}
		} catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			session.close();
		}
		
		
		return lisMascotas;
	}

}
