package com.web.pet.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetfotoDAO;
import com.web.pet.pojo.annotations.Petfoto;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;
import com.web.util.FileUtil;

public class PetfotoBO {

	public List<Petfoto> lisPetfotoByPetId(int idmascota) throws Exception {
		List<Petfoto> lisPetfoto = null;
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
	
	public boolean newPetfoto(Petfoto petfoto) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			int maxid = petfotoDAO.maxIdPetfoto(session)+1;
			Date fecharegistro = new Date();
			String fileExtention = new FileUtil().getFileExtention(petfoto.getNombrearchivo());
			String nombrearchivo = petfoto.getPetmascota().getPetespecie().getIdespecie()+"-"+petfoto.getPetmascota().getIdmascota()+"-"+maxid+"."+fileExtention.toLowerCase();
			
			petfoto.setIdfoto(maxid);
			
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			Calendar fecha = Calendar.getInstance();
			
			String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
			String rutaMascota =  "/mascotas/" + fecha.get(Calendar.YEAR);
			String rutaCompleta = rutaImagenes + rutaMascota;
			
			//asignar ruta y nombre de archivo en objeto
			petfoto.setNombrearchivo(nombrearchivo);
			petfoto.setRuta(rutaMascota+"/"+nombrearchivo);
			petfoto.setFecharegistro(fecharegistro);
			petfoto.setIplog(usuarioBean.getIp());
			petfoto.getPetestado().setIdestado(1);
			petfoto.setSetusuario(usuarioBean.getSetUsuario());
	
			if(fileUtil.createDir(rutaCompleta)){
				//crear foto en disco
				String rutaArchivo = rutaCompleta + "/" + nombrearchivo;
				fileUtil.createFile(rutaArchivo,petfoto.getObjeto());
			}
			
			petfoto.setObjeto(null);
			petfotoDAO.savePetfoto(session, petfoto);
			
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
	
	public boolean updatePetfoto(Petfoto petfoto) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petfoto.setFecharegistro(fecharegistro);
			petfoto.setIplog(usuarioBean.getIp());
			petfoto.setSetusuario(usuarioBean.getSetUsuario());
			petfotoDAO.updatePetfoto(session, petfoto);
			
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
	
	public List<Petfoto> lisPetfotoPerfil(int tipo) throws Exception {
		List<Petfoto> lispetfoto = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			lispetfoto = petfotoDAO.lisPetfotoPerfil(session, tipo);
		}catch(Exception re){
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
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			petfoto = petfotoDAO.getPetfotoPerfilByPetId(session, idmascota);
		} catch(Exception he){
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
	
	public boolean ponerFotoPerfil(Petfoto petfoto) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetfotoDAO petfotoDAO = new PetfotoDAO();
			
			//Primero se quita la im�gen que est� actualmente como perfil
			petfotoDAO.resetPetfotoPerfilByPetId(session, petfoto.getPetmascota().getIdmascota());
			
			//Luego se pone la im�gen seleccionada como foto del perfil
			petfotoDAO.setPetfotoPerfil(session, petfoto.getIdfoto());
			
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
