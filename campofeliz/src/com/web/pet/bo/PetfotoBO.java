package com.web.pet.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.daointerface.PetfotoDAOInterface;
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
            throw new RuntimeException("Problemas al cargar la interfaz PetfotoDAOInterface");
        }
	}

	public List<Petfoto> lisPetfotoByPetId(int idmascota) throws Exception {
		List<Petfoto> lisPetfoto = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			lisPetfoto = petfotoDAOInterface.lisPetfotoByPetId(session, idmascota);
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
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			int maxid = petfotoDAOInterface.maxIdPetfoto(session)+1;
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
			petfotoDAOInterface.savePetfoto(session, petfoto);
			
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
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petfoto.setFecharegistro(fecharegistro);
			petfoto.setIplog(usuarioBean.getIp());
			petfoto.setSetusuario(usuarioBean.getSetUsuario());
			petfotoDAOInterface.updatePetfoto(session, petfoto);
			
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
			lispetfoto = petfotoDAOInterface.lisPetfotoPerfil(session, tipo);
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
			petfoto = petfotoDAOInterface.getPetfotoPerfilByPetId(session, idmascota);
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
			
			petfotoDAOInterface.resetPetfotoPerfilByPetId(session, idmascota);
			
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
			
			//Primero se quita la imágen que está actualmente como perfil
			petfotoDAOInterface.resetPetfotoPerfilByPetId(session, petfoto.getPetmascota().getIdmascota());
			
			//Luego se pone la imágen seleccionada como foto del perfil
			petfotoDAOInterface.setPetfotoPerfil(session, petfoto.getIdfoto());
			
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
			
			petfotoDAOInterface.deletePetfoto(session, idfoto);
			
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
