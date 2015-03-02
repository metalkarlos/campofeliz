package com.web.pet.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetfotoDAO;
import com.web.pet.pojo.annotations.Petfotomascota;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;
import com.web.util.FileUtil;

public class PetfotoBO {

	public List<Petfotomascota> lisPetfotoByPetId(int idmascota) throws Exception {
		List<Petfotomascota> lisPetfoto = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			lisPetfoto = petfotoDAO.lisPetfotoByPetId(session, idmascota);
		}catch(Exception re){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisPetfoto;
	}
	
	public boolean newPetfoto(Petfotomascota petfotomascota) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			int maxid = petfotoDAO.maxIdPetfoto(session)+1;
			Date fecharegistro = new Date();
			String fileExtention = new FileUtil().getFileExtention(petfotomascota.getNombrearchivo());
			String nombrearchivo = petfotomascota.getPetmascotahomenaje().getPetespecie().getIdespecie()+"-"+petfotomascota.getPetmascotahomenaje().getIdmascota()+"-"+maxid+"."+fileExtention.toLowerCase();
			
			petfotomascota.setIdfotomascota(maxid);
			
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			Calendar fecha = Calendar.getInstance();
			
			String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
			String rutaMascota =  fileUtil.getPropertyValue("repositorio-mascota") + fecha.get(Calendar.YEAR);
			String rutaCompleta = rutaImagenes + rutaMascota;
			
			//asignar ruta y nombre de archivo en objeto
			petfotomascota.setNombrearchivo(nombrearchivo);
			petfotomascota.setRuta(rutaMascota+"/"+nombrearchivo);
			petfotomascota.setFecharegistro(fecharegistro);
			petfotomascota.setIplog(usuarioBean.getIp());
			petfotomascota.getSetestado().setIdestado(1);
			petfotomascota.setSetusuario(usuarioBean.getSetUsuario());
	
			if(fileUtil.createDir(rutaCompleta)){
				//crear foto en disco
				String rutaArchivo = rutaCompleta + "/" + nombrearchivo;
				fileUtil.createFile(rutaArchivo,petfotomascota.getObjeto());
			}
			
			petfotomascota.setObjeto(null);
			petfotoDAO.savePetfoto(session, petfotomascota);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception re){
			session.getTransaction().rollback();
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean updatePetfoto(Petfotomascota petfotomascota) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petfotomascota.setFecharegistro(fecharegistro);
			petfotomascota.setIplog(usuarioBean.getIp());
			petfotomascota.setSetusuario(usuarioBean.getSetUsuario());
			petfotoDAO.updatePetfoto(session, petfotomascota);
			
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
	
	public Petfotomascota getPetfotoPerfilByPetId(int idmascota) throws Exception{
		Petfotomascota petfotomascota = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			petfotomascota = petfotoDAO.getPetfotoPerfilByPetId(session, idmascota);
		} catch(Exception he){
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return petfotomascota;
	}
	
	public boolean resetPetfotoPerfilByPetId(int idmascota) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			petfotoDAO.resetPetfotoPerfilByPetId(session, idmascota);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
			session.getTransaction().rollback();
			throw new RuntimeException(); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean ponerFotoPerfil(Petfotomascota petfotomascota) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			//Primero se quita la imágen que está actualmente como perfil
			petfotoDAO.resetPetfotoPerfilByPetId(session, petfotomascota.getPetmascotahomenaje().getIdmascota());
			
			//Luego se pone la imágen seleccionada como foto del perfil
			petfotoDAO.setPetfotoPerfil(session, petfotomascota.getIdfotomascota());
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
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
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			petfotoDAO.deletePetfoto(session, idfoto);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
			session.getTransaction().rollback();
			throw new Exception(); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
}
