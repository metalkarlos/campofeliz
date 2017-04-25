package com.web.pet.bo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.primefaces.model.UploadedFile;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetfotomascotaDAO;
import com.web.pet.dao.PetmascotaDAO;
import com.web.pet.dao.PetmascotacolorDAO;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Petfotomascota;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.HibernateUtil;

public class PetmascotaBO {

	public boolean modificarMascota(Petmascotahomenaje petmascotahomenaje,Petmascotahomenaje petmascotahomenajeClon,List<Petfotomascota> lisPetfotomascota,List<Petfotomascota> lisPetfotomascotaClon,List<Petmascotacolor> lisPetmascotacolor, List<Petmascotacolor> lisPetmascotacolorOld, Petfotomascota petfotomascota, UploadedFile uploadedFile, Session sessionExterna) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			if(sessionExterna == null){
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
			}else{
				session = sessionExterna;
			}
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetfotomascotaDAO PetfotomascotaDAO = new PetfotomascotaDAO();
			PetmascotacolorDAO petmascotacolorDAO = new PetmascotacolorDAO();
			FacesUtil facesUtil = new FacesUtil();
			FileUtil fileUtil = new FileUtil();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			//Se evalua si han habido cambios en la lista de las fotos
			for(Petfotomascota petfotomascotaClon : lisPetfotomascotaClon){
				boolean encuentra = false;
				for(Petfotomascota petfotomascotaItem : lisPetfotomascota){
					if(petfotomascotaClon.getIdfotomascota() == petfotomascotaItem.getIdfotomascota()){
						//si encuentra
						encuentra = true;
						
						if(!petfotomascotaClon.equals(petfotomascotaItem)){
							//si han habido cambios se actualiza
							
							//auditoria
							fecharegistro = new Date();
							petfotomascotaItem.setFechamodificacion(fecharegistro);
							petfotomascotaItem.setIplog(usuarioBean.getIp());
							petfotomascotaItem.setSetusuario(usuarioBean.getSetUsuario());
							
							//actualizar
							PetfotomascotaDAO.updatePetfotomascota(session, petfotomascotaItem);
							ok = true;
						}
					}
				}
				if(!encuentra){
					//no encuentra
					//inhabilitar
					Setestado setestado = new Setestado();
					setestado.setIdestado(2);
					petfotomascotaClon.setSetestado(setestado);
					
					//auditoria
					fecharegistro = new Date();
					petfotomascotaClon.setFechamodificacion(fecharegistro);
					petfotomascotaClon.setIplog(usuarioBean.getIp());
					petfotomascotaClon.setSetusuario(usuarioBean.getSetUsuario());
					
					//actualizar
					PetfotomascotaDAO.updatePetfotomascota(session, petfotomascotaClon);
					
					//eliminar foto del disco
					String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
					
					String rutaArchivo = rutaImagenes + petfotomascotaClon.getRuta();
					
					fileUtil.deleteFile(rutaArchivo);
					ok = true;
				}
			}
			
			
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
					ok = true;
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
					ok = true;
				}
			}
			
			//Si subio foto se crea en disco y en base
			if(uploadedFile != null){
				creaFotoDiscoBD(petmascotahomenaje, petfotomascota, uploadedFile, session);
				//si no tiene imagen principal se setea
				if(petmascotahomenaje.getRutafoto() == null || petmascotahomenaje.getRutafoto().trim().length() == 0){
					petmascotahomenaje.setRutafoto(petfotomascota.getRuta());
				}
				ok = true;
			}
			
			//Se graba la mascota si han habido cambios
			if(!petmascotahomenaje.equals(petmascotahomenajeClon)){
				//auditoria
				petmascotahomenaje.setFecharegistro(fecharegistro);
				petmascotahomenaje.setIplog(usuarioBean.getIp());
				petmascotahomenaje.setSetusuario(usuarioBean.getSetUsuario());
				
				//actualizar
				petmascotaDAO.updatePetmascota(session, petmascotahomenaje);
				ok = true;
			}
			
			if(ok && sessionExterna == null){
				session.getTransaction().commit();
			}
		}catch(Exception he){
			ok = false;
			if(sessionExterna == null){
				session.getTransaction().rollback();
			}
			throw new Exception();
		}finally{
			if(sessionExterna == null){
				session.close();
			}
		}
		
		return ok;
	}
	
	private void creaFotoDiscoBD(Petmascotahomenaje petmascotahomenaje, Petfotomascota petfotomascota, UploadedFile uploadedFile, Session session) throws Exception {
		UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
		PetfotomascotaDAO petfotomascotaDAO = new PetfotomascotaDAO();
		
		int maxidfotomascota = petfotomascotaDAO.maxIdPetfotomascota(session)+1;
		int cantFotosPorMascota = petfotomascotaDAO.getCantFotosPorMascota(session, petmascotahomenaje.getIdmascota())+1;
		
		//foto en disco
		FileUtil fileUtil = new FileUtil();
		FacesUtil facesUtil = new FacesUtil();
		Calendar fecha = Calendar.getInstance();
		
		String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
		String rutaMascotas =  fileUtil.getPropertyValue("repositorio-mascota") + "/" + fecha.get(Calendar.YEAR);
		String nombreArchivo = fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + "-" + petmascotahomenaje.getIdmascota() + "-" + cantFotosPorMascota + "." + fileUtil.getFileExtention(uploadedFile.getFileName()).toLowerCase();
		
		String rutaCompleta = rutaImagenes + "/" + rutaMascotas;
		
		if(fileUtil.createDir(rutaCompleta)){
			//crear foto en disco
			String rutaArchivo = rutaCompleta + "/" + nombreArchivo;
			fileUtil.createFile(rutaArchivo,uploadedFile.getContents());
		}
		
		//foto en BD
		petfotomascota.setIdfotomascota(maxidfotomascota);
		petfotomascota.setPetmascotahomenaje(petmascotahomenaje);
		String rutaBD = "/" + rutaMascotas + "/" + nombreArchivo;
		petfotomascota.setRuta(rutaBD);
		petfotomascota.setNombrearchivo(nombreArchivo);
		petfotomascota.setMostrar(1);//campo sin uso ya que tabla principal posee ruta de foto de perfil
		Setestado setestadoPetfotomascota = new Setestado();
		setestadoPetfotomascota.setIdestado(1);
		petfotomascota.setSetestado(setestadoPetfotomascota);
		
		//auditoria
		Date fecharegistro = new Date();
		petfotomascota.setFecharegistro(fecharegistro);
		petfotomascota.setIplog(usuarioBean.getIp());
		petfotomascota.setSetusuario(usuarioBean.getSetUsuario());
		
		//ingresar foto en BD
		petfotomascotaDAO.savePetfotomascota(session, petfotomascota);
	}
	
	public boolean ingresarMascota(Petmascotahomenaje petmascotahomenaje, List<Petmascotacolor> lisPetmascotacolor, Petfotomascota petfotomascota, UploadedFile uploadedFile, Session sessionExterna) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			if(sessionExterna == null){
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
			}else{
				session = sessionExterna;
			}
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetmascotacolorDAO petmascotacolorDAO = new PetmascotacolorDAO();
			
			//mascota
			int maxid = petmascotaDAO.maxIdPetmascota(session)+1;
			petmascotahomenaje.setIdmascota(maxid);
			Setestado setestadoPetmascota = new Setestado();
			setestadoPetmascota.setIdestado(1);
			petmascotahomenaje.setSetestado(setestadoPetmascota);
			
			//auditoria
			Date fecharegistro = new Date();
			petmascotahomenaje.setFecharegistro(fecharegistro);
			petmascotahomenaje.setIplog(usuarioBean.getIp());
			petmascotahomenaje.setSetusuario(usuarioBean.getSetUsuario());
	
			//ingresar mascota
			petmascotaDAO.savePetmascota(session, petmascotahomenaje);
			
			//ingresar colores nuevos
			for(Petmascotacolor petmascotacolor : lisPetmascotacolor){
				int maxidPetmascotacolor = petmascotacolorDAO.maxIdPetmascotacolor(session)+1;
				
				//petmascotacolor.getPetmascota().setIdmascota(maxid);
				petmascotacolor.setPetmascotahomenaje(petmascotahomenaje);
				petmascotacolor.setIdmascotacolor(maxidPetmascotacolor);
				fecharegistro = new Date();
				petmascotacolor.setFecharegistro(fecharegistro);
				petmascotacolor.setIplog(usuarioBean.getIp());
				Setestado setestadocolor = new Setestado();
				setestadocolor.setIdestado(1);
				petmascotacolor.setSetestado(setestadocolor);
				petmascotacolor.setSetusuario(usuarioBean.getSetUsuario());
				
				petmascotacolorDAO.savePetmascotacolor(session, petmascotacolor);
			}
			
			//Si subio foto se crea en disco y en base
			if(uploadedFile != null){
				creaFotoDiscoBD(petmascotahomenaje, petfotomascota, uploadedFile, session);
				//se setea la ruta de la foto tambien en petnoticia.rutafoto
				petmascotahomenaje.setRutafoto(petfotomascota.getRuta());
				//update
				petmascotaDAO.updatePetmascota(session, petmascotahomenaje);
			}
			
			if(sessionExterna == null){
				session.getTransaction().commit();
			}
			
			ok = true;
		}catch(Exception he){
			if(sessionExterna == null){
				session.getTransaction().rollback();
			}
			throw new Exception(); 
		}finally{
			if(sessionExterna == null){
				session.close();
			}
		}
		
		return ok;
	}
	
	public boolean eliminar(Petmascotahomenaje petmascotahomenaje,List<Petfotomascota> lisPetfotomascota,List<Petmascotacolor> lisPetmascotacolor) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			PetfotomascotaDAO petfotomascotaDAO = new PetfotomascotaDAO();
			PetmascotacolorDAO petmascotacolorDAO = new PetmascotacolorDAO();
			
			//se eliminan fisicamente las fotos asociadas a la mascota
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
			
			for(Petfotomascota tmp : lisPetfotomascota){
				//se inactivan todas las fotos asociadas a la mascota
				Setestado setestadofoto = new Setestado();
				setestadofoto.setIdestado(2);
				tmp.setSetestado(setestadofoto);
				
				//auditoria
				Date fecharegistro = new Date();
				tmp.setFechamodificacion(fecharegistro);
				tmp.setIplog(usuarioBean.getIp());
				tmp.setSetusuario(usuarioBean.getSetUsuario());
				
				//actualizar
				petfotomascotaDAO.updatePetfotomascota(session, tmp);
				
				//se elimina el archivo de imagen
				String rutaArchivo = rutaImagenes + tmp.getRuta();
				fileUtil.deleteFile(rutaArchivo);
			}
			
			for(Petmascotacolor tmp : lisPetmascotacolor){
				//se inactivan todos los colores asociados a la mascota
				Setestado setestadocolor = new Setestado();
				setestadocolor.setIdestado(2);
				tmp.setSetestado(setestadocolor);
				
				//auditoria
				Date fecharegistro = new Date();
				tmp.setFechamodificacion(fecharegistro);
				tmp.setIplog(usuarioBean.getIp());
				tmp.setSetusuario(usuarioBean.getSetUsuario());
				
				//actualizar
				petmascotacolorDAO.updatePetmascotacolor(session, tmp);
			}
			
			//se inactiva el registro
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);
			petmascotahomenaje.setSetestado(setestado);
			
			//auditoria
			Date fecharegistro = new Date();
			petmascotahomenaje.setFechamodificacion(fecharegistro);
			petmascotahomenaje.setIplog(usuarioBean.getIp());
			petmascotahomenaje.setSetusuario(usuarioBean.getSetUsuario());
			
			petmascotaDAO.updatePetmascota(session, petmascotahomenaje);
			
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
	
	public List<Petmascotahomenaje> lisPetmascotahomenajeEspecieByPage(int especie, String nombre, int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Petmascotahomenaje> lisPetmascotahomenaje = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			
			lisPetmascotahomenaje = petmascotaDAO.lisPetmascotaByEspecieByPage(session, especie, nombre, pageSize, pageNumber, args);
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisPetmascotahomenaje;
	}
	
	public List<Petmascotahomenaje> lisPetmascotahomenajeByPage(String nombre, int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Petmascotahomenaje> lisPetmascotahomenaje = null;
		Session session = null;
		
		try{
			lisPetmascotahomenaje = new ArrayList<Petmascotahomenaje>();
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			//PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			lisPetmascotahomenaje = petmascotaDAO.lisPetmascotaByPage(session, nombre, pageSize, pageNumber, args);
			
			/*for(Petmascotahomenaje petmascotahomenaje : lisPetmascotahomenaje){
				Petfotomascota petfotomascota = petfotoDAO.getPetfotomascotaPerfilByIdmascota(session, petmascotahomenaje.getIdmascota());

				Mascotas mascotas = new Mascotas();
				mascotas.setPetfotomascota(petfotomascota);
				mascotas.setPetmascotahomenaje(petmascotahomenaje);
				lisMascotas.add(mascotas);
			}*/
			
		}catch(Exception he){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisPetmascotahomenaje;
	}
	
	public List<Petmascotahomenaje> lisPetmascotahomenajeBusquedaByPage(Petmascotahomenaje petmascotahomenaje, String[] caracteristicas, int pageSize, int pageNumber, int args[]) throws RuntimeException{
		List<Petmascotahomenaje> lisPetmascotahomenaje = null;
		Session session = null;
		
		try {
			lisPetmascotahomenaje = new ArrayList<Petmascotahomenaje>();
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetmascotaDAO petmascotaDAO = new PetmascotaDAO();
			//PetfotomascotaDAO petfotoDAO = new PetfotomascotaDAO();
			
			lisPetmascotahomenaje = petmascotaDAO.lisPetmascotaBusquedaByPage(session, petmascotahomenaje, caracteristicas, pageSize, pageNumber, args);
			
			/*for(Petmascotahomenaje petmascotahomenaje1 : lisPetmascotahomenaje){
				Petfotomascota petfotomascota = petfotoDAO.getPetfotomascotaPerfilByIdmascota(session, petmascotahomenaje1.getIdmascota());
				
				Mascotas mascotas = new Mascotas();
				mascotas.setPetfotomascota(petfotomascota);
				mascotas.setPetmascotahomenaje(petmascotahomenaje1);
				lisMascotas.add(mascotas);
			}*/
		} catch(Exception e){
			throw new RuntimeException();
		} finally {
			session.close();
		}
		
		
		return lisPetmascotahomenaje;
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
