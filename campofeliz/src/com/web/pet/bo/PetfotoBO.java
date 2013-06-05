package com.web.pet.bo;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.PetfotoDAOInterface;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Petfoto;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;
import com.web.util.FileUtil;

public class PetfotoBO {
	
	private PetfotoDAOInterface petfotoDAOInterface;
	
	public PetfotoBO() throws RuntimeException {
		try{
			petfotoDAOInterface = (PetfotoDAOInterface) PetfotoBO.class.getClassLoader().loadClass("com.web.pet.dao.PetfotoDAO").newInstance();
        }catch(Exception e){
        	e.printStackTrace();
            throw new RuntimeException("Problemas al cargar la interfaz PetfotoDAOInterface");
        }
	}

	public List<Petfoto> lisPetfotoByPetId(int idmascota) throws Exception {
		List<Petfoto> lisPetfoto = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			lisPetfoto = petfotoDAOInterface.lisPetfotoByPetId(session, idmascota);
			FileUtil fileUtil = new FileUtil();
			Properties petsoftProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
			String resources_server_url = petsoftProperties.getProperty("resources_server_url");
			String resources_server_war = petsoftProperties.getProperty("resources_server_war");
			
			for(Petfoto petfoto : lisPetfoto){
				String mascotaPath = Parametro.DEPLOYMENTS_PATH+Parametro.FILE_SEPARATOR+resources_server_war+petfoto.getRuta();
				if(fileUtil.existFile(mascotaPath)){
					petfoto.setRuta(resources_server_url + petfoto.getRuta());
				}else{
					petfoto.setRuta(Parametro.BLANK_IMAGE_PATH);
				}
			}
		}catch(Exception re){
			re.printStackTrace();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisPetfoto;
	}
	
	public boolean newPetfoto(Petfoto petfoto) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			int maxid = petfotoDAOInterface.maxIdPetfoto(session)+1;
			Date fecharegistro = new Date();
			String fileExtention = new FileUtil().getFileExtention(petfoto.getNombrearchivo());
			String nombrearchivo = petfoto.getPetmascota().getPetespecie().getIdespecie()+"-"+petfoto.getPetmascota().getIdmascota()+"-"+maxid+"."+fileExtention.toLowerCase();
			
			petfoto.setIdfoto(maxid);
			petfoto.setNombrearchivo(nombrearchivo);
			petfoto.setRuta(Parametro.MASCOTAS_PATH + petfoto.getPetmascota().getIdmascota() + Parametro.FILE_SEPARATOR + nombrearchivo);
			petfoto.setFecharegistro(fecharegistro);
			petfoto.setIplog(usuarioBean.getIp());
			petfoto.getPetestado().setIdestado(1);
			petfoto.setSetusuario(usuarioBean.getSetUsuario());
	
			createImageOnDisc(petfoto);
			petfoto.setObjeto(null);
			petfotoDAOInterface.savePetfoto(session, petfoto);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception re){
			re.printStackTrace();
			session.getTransaction().rollback();
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	private void createImageOnDisc(Petfoto petfoto) throws Exception {
		FileUtil fileUtil = new FileUtil();
		Properties petsoftProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
		String resources_server_war = petsoftProperties.getProperty("resources_server_war");
		
		String mascotaPath = Parametro.DEPLOYMENTS_PATH+Parametro.FILE_SEPARATOR+resources_server_war+Parametro.MASCOTAS_PATH+petfoto.getPetmascota().getIdmascota();
		
		if(fileUtil.createDir(mascotaPath)){
			fileUtil.createFile(mascotaPath+Parametro.FILE_SEPARATOR+petfoto.getNombrearchivo(),petfoto.getObjeto());
		}
	}
	
	public boolean updatePetfoto(Petfoto petfoto) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petfoto.setFecharegistro(fecharegistro);
			petfoto.setIplog(usuarioBean.getIp());
			petfoto.setSetusuario(usuarioBean.getSetUsuario());
			petfotoDAOInterface.updatePetfoto(session, petfoto);
			
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
	
	public List<Petfoto> lisPetfotoPerfil(int tipo) throws Exception {
		List<Petfoto> lispetfoto = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			lispetfoto = petfotoDAOInterface.lisPetfotoPerfil(session, tipo);
		}catch(Exception re){
			re.printStackTrace();
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return lispetfoto;
	}
	
	public Petfoto getPetfotoPerfilByPetId(int idmascota) throws Exception{
		Petfoto petfoto = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			petfoto = petfotoDAOInterface.getPetfotoPerfilByPetId(session, idmascota);
			FileUtil fileUtil = new FileUtil();
			Properties petsoftProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
			String resources_server_url = petsoftProperties.getProperty("resources_server_url");
			String resources_server_war = petsoftProperties.getProperty("resources_server_war");
			
			if(petfoto != null){
				String mascotaPath = Parametro.DEPLOYMENTS_PATH+Parametro.FILE_SEPARATOR+resources_server_war+petfoto.getRuta();
				if(fileUtil.existFile(mascotaPath)){
					petfoto.setRuta(resources_server_url + petfoto.getRuta());
				}else{
					petfoto.setRuta(Parametro.BLANK_IMAGE_PATH);
				}
			}else{
				petfoto = new Petfoto();
				petfoto.setRuta(Parametro.BLANK_IMAGE_PATH);
			}
		} catch(Exception he){
			he.printStackTrace();
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return petfoto;
	}
	
	public boolean resetPetfotoPerfilByPetId(int idmascota) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			petfotoDAOInterface.resetPetfotoPerfilByPetId(session, idmascota);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
			he.printStackTrace();
			session.getTransaction().rollback();
			throw new RuntimeException(); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean ponerFotoPerfil(Petfoto petfoto) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			//Primero se quita la imágen que está actualmente como perfil
			petfotoDAOInterface.resetPetfotoPerfilByPetId(session, petfoto.getPetmascota().getIdmascota());
			
			//Luego se pone la imágen seleccionada como foto del perfil
			petfotoDAOInterface.setPetfotoPerfil(session, petfoto.getIdfoto());
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
			he.printStackTrace();
			session.getTransaction().rollback();
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean eliminarFotoAlbum(int idfoto) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			petfotoDAOInterface.deletePetfoto(session, idfoto);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
			he.printStackTrace();
			session.getTransaction().rollback();
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
}
