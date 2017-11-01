package com.web.pet.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetfotomascotaDAO;
import com.web.pet.pojo.annotations.Petfotomascota;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;
import com.web.util.FileUtil;

public class PetfotomascotaBO {

	public List<Petfotomascota> lisPetfotomascotaByIdmascota(int idmascota) throws Exception {
		List<Petfotomascota> lisPetfoto = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			lisPetfoto = petfotoDAO.lisPetfotomascotaByIdmascota(session, idmascota);
		}catch(Exception re){
			throw new Exception(re);
		}finally{
			session.close();
		}
		
		return lisPetfoto;
	}
	
	public boolean newPetfotomascota(Petfotomascota petfotomascota) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetfotomascotaDAO petfotomascotaDAO = new PetfotomascotaDAO();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			int maxid = petfotomascotaDAO.maxIdPetfotomascota(session)+1;
			petfotomascota.setIdfotomascota(maxid);
			int secuencia = petfotomascotaDAO.getCantFotosPorMascota(session, petfotomascota.getPetmascotahomenaje().getIdmascota());
			
			//foto en disco
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			Calendar fecha = Calendar.getInstance();
			
			String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
			String rutaMascota =  fileUtil.getPropertyValue("repositorio-mascota") + "/" + fecha.get(Calendar.YEAR);
			String nombrearchivo = fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + "-" + petfotomascota.getPetmascotahomenaje().getPetespecie().getIdespecie()+"-"+petfotomascota.getPetmascotahomenaje().getIdmascota()+"-"+secuencia+"."+fileUtil.getFileExtention(petfotomascota.getNombrearchivo()).toLowerCase();
			
			String rutaCompleta = rutaImagenes + "/" + rutaMascota;
			
			//asignar ruta y nombre de archivo en objeto
			petfotomascota.setNombrearchivo(nombrearchivo);
			petfotomascota.setRuta("/" + rutaMascota + "/" + nombrearchivo);
			
			//Auditoria
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
			petfotomascotaDAO.savePetfotomascota(session, petfotomascota);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception re){
			session.getTransaction().rollback();
			throw new Exception(re); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean updatePetfotomascota(Petfotomascota petfotomascota) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petfotomascota.setFecharegistro(fecharegistro);
			petfotomascota.setIplog(usuarioBean.getIp());
			petfotomascota.setSetusuario(usuarioBean.getSetUsuario());
			petfotoDAO.updatePetfotomascota(session, petfotomascota);
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception he){
			session.getTransaction().rollback();
			throw new Exception(he);
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public Petfotomascota getPetfotomascotaPerfilByIdmascota(int idmascota) throws Exception{
		Petfotomascota petfotomascota = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			petfotomascota = petfotoDAO.getPetfotomascotaPerfilByIdmascota(session, idmascota);
		} catch(Exception he){
			throw new Exception(he); 
		}finally{
			session.close();
		}
		
		return petfotomascota;
	}
	
	public boolean resetPetfotomascotaPerfilByIdmascota(int idmascota) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			petfotoDAO.resetPetfotomascotaPerfilByIdmascota(session, idmascota);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
			session.getTransaction().rollback();
			throw new RuntimeException(he); 
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
			
			PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			//Primero se quita la imágen que está actualmente como perfil
			petfotoDAO.resetPetfotomascotaPerfilByIdmascota(session, petfotomascota.getPetmascotahomenaje().getIdmascota());
			
			//Luego se pone la imágen seleccionada como foto del perfil
			petfotoDAO.setPetfotomascotaPerfil(session, petfotomascota.getIdfotomascota());
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
			session.getTransaction().rollback();
			throw new Exception(he); 
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
			
			PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			petfotoDAO.deletePetfoto(session, idfoto);
			
			session.getTransaction().commit();
			ok = true;
		} catch(Exception he){
			session.getTransaction().rollback();
			throw new Exception(he); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
}
