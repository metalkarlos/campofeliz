package com.web.pet.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.primefaces.model.UploadedFile;


import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.CotfotopersonaDAO;
import com.web.pet.dao.CotpersonaDAO;
import com.web.pet.pojo.annotations.Cotfotopersona;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.HibernateUtil;

public class CotpersonaBO {
	
	public Cotpersona getCotpersonaById(int idpersona) throws Exception{
		Cotpersona cotpersona = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			cotpersona = cotpersonaDAO.getCotpersonaById(session, idpersona);
		}catch(Exception e){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return cotpersona;
	}
	
	public Cotpersona getCotpersonaConObjetosById(int idpersona) throws Exception {
		Cotpersona cotpersona = null;
		Session session = null;
		
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
            cotpersona = cotpersonaDAO.getCotpersonaConObjetosById(session, idpersona);
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
        finally{
            session.close();
        }
		
		return cotpersona;
	}
	
	public List<Cotpersona> lisCotpersonaByPage(String[] nombres, int pageSize, int pageNumber, int args[]) throws RuntimeException{
		List<Cotpersona> lisCotpersona = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			lisCotpersona = cotpersonaDAO.lisCotpersonaByPage(session, nombres, pageSize, pageNumber, args);
		}catch(Exception e){
			throw new RuntimeException();
		}finally{
			session.close();
		}
		
		return lisCotpersona;
	}
	
	public List<Cotpersona> lisCotpersonaBusqueda(Cotpersona cotpersona) throws Exception{
		List<Cotpersona> lisCotpersona = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			lisCotpersona = cotpersonaDAO.lisCotpersonaBusqueda(session, cotpersona);
		}catch(Exception e){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisCotpersona;
	}
	
	public List<Cotpersona> lisCotpersonaBusquedaByPage(Cotpersona cotpersona, int pageSize, int pageNumber, int args[]) throws Exception{
		List<Cotpersona> lisCotpersona = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			lisCotpersona = cotpersonaDAO.lisCotpersonaBusquedaByPage(session, cotpersona, pageSize, pageNumber, args);
		}catch(Exception e){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisCotpersona;
	}
	
	public boolean newCotpersona(Cotpersona cotpersona, Cotfotopersona cotfotopersona, UploadedFile uploadedFile) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			
			int max = cotpersonaDAO.maxIdCotpersona(session)+1;
			cotpersona.setIdpersona(max);
			Setestado setestadoCotpersona = new Setestado();
			setestadoCotpersona.setIdestado(1);
			cotpersona.setSetestado(setestadoCotpersona);
			
			//auditoria
			Date fecharegistro = new Date();
			cotpersona.setFecharegistro(fecharegistro);
			cotpersona.setIplog(usuarioBean.getIp());
			cotpersona.setSetusuario(usuarioBean.getSetUsuario());
			
			//ingresar persona
			cotpersonaDAO.saveCotpersona(session, cotpersona);
			
			//Si subio foto se crea en disco y en base
			if(uploadedFile != null){
				creaFotoDiscoBD(cotpersona, cotfotopersona, uploadedFile, session);
				//se setea la ruta de la foto tambien en petnoticia.rutafoto
				cotpersona.setRuta(cotfotopersona.getRuta());
				//update
				cotpersonaDAO.updateCotpersona(session, cotpersona);
			}
			
			session.getTransaction().commit();
			
			ok = true;
		}catch(Exception e){
			session.getTransaction().rollback();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	private void creaFotoDiscoBD(Cotpersona cotpersona, Cotfotopersona cotfotopersona, UploadedFile uploadedFile, Session session) throws Exception {
		UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
		CotfotopersonaDAO cotfotopersonaDAO = new CotfotopersonaDAO();
		
		int maxIdfotonoticia = cotfotopersonaDAO.maxIdCotfotopersona(session)+1;
		int cantFotosPorPersona = cotfotopersonaDAO.getCantFotosPorPersona(session, cotpersona.getIdpersona())+1;
		
		//foto en disco
		FileUtil fileUtil = new FileUtil();
		FacesUtil facesUtil = new FacesUtil();
		Calendar fecha = Calendar.getInstance();
		
		String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
		String rutaPersonas =  fileUtil.getPropertyValue("repositorio-personas") + fecha.get(Calendar.YEAR);
		String nombreArchivo = fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + "-" + cotpersona.getIdpersona() + "-" + cantFotosPorPersona + "." + fileUtil.getFileExtention(uploadedFile.getFileName()).toLowerCase();
		
		String rutaCompleta = rutaImagenes + rutaPersonas;
		
		if(fileUtil.createDir(rutaCompleta)){
			//crear foto en disco
			String rutaArchivo = rutaCompleta + "/" + nombreArchivo;
			fileUtil.createFile(rutaArchivo,uploadedFile.getContents());
		}
		
		//foto en BD
		cotfotopersona.setIdfotopersona(maxIdfotonoticia);
		cotfotopersona.setCotpersona(cotpersona);
		String rutaBD = rutaPersonas + "/" + nombreArchivo;
		cotfotopersona.setRuta(rutaBD);
		cotfotopersona.setNombrearchivo(nombreArchivo);
		cotfotopersona.setMostrar(1);//campo sin uso ya que tabla principal posee ruta de foto de perfil
		Setestado setestadoCotfotopersona = new Setestado();
		setestadoCotfotopersona.setIdestado(1);
		cotfotopersona.setSetestado(setestadoCotfotopersona);
		
		//auditoria
		Date fecharegistro = new Date();
		cotfotopersona.setFecharegistro(fecharegistro);
		cotfotopersona.setIplog(usuarioBean.getIp());
		cotfotopersona.setSetusuario(usuarioBean.getSetUsuario());
		
		//ingresar foto en BD
		cotfotopersonaDAO.saveCotfotopersona(session, cotfotopersona);
	}
	
	public boolean updateCotpersona(Cotpersona cotpersona, Cotpersona cotpersonaClon, List<Cotfotopersona> lisCotfotopersona, List<Cotfotopersona> lisCotfotopersonaClon, Cotfotopersona cotfotopersona, UploadedFile uploadedFile) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			CotfotopersonaDAO cotfotopersonaDAO = new CotfotopersonaDAO();
			Date fecharegistro = new Date();
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			
			//Se graba la persona si han habido cambios
			if(!cotpersona.equals(cotpersonaClon)){
				//auditoria
				cotpersona.setFechamodificacion(fecharegistro);
				cotpersona.setIplog(usuarioBean.getIp());
				cotpersona.setSetusuario(usuarioBean.getSetUsuario());
		
				//actualizar
				cotpersonaDAO.updateCotpersona(session, cotpersona);
				ok = true;
			}
			
			//Se evalua si han habido cambios en la lista de las fotos
			for(Cotfotopersona cotfotopersonaClon : lisCotfotopersonaClon){
				boolean encuentra = false;
				for(Cotfotopersona cotfotopersonaItem : lisCotfotopersona){
					if(cotfotopersonaClon.getIdfotopersona() == cotfotopersonaItem.getIdfotopersona()){
						//si encuentra
						encuentra = true;
						
						if(!cotfotopersonaClon.equals(cotfotopersonaItem)){
							//si han habido cambios se actualiza
							
							//auditoria
							fecharegistro = new Date();
							cotfotopersonaItem.setFechamodificacion(fecharegistro);
							cotfotopersonaItem.setIplog(usuarioBean.getIp());
							cotfotopersonaItem.setSetusuario(usuarioBean.getSetUsuario());
							
							//actualizar
							cotfotopersonaDAO.updateCotfotopersona(session, cotfotopersonaItem);
							ok = true;
						}
					}
				}
				if(!encuentra){
					//no encuentra
					//inhabilitar
					Setestado setestado = new Setestado();
					setestado.setIdestado(2);
					cotfotopersonaClon.setSetestado(setestado);
					
					//auditoria
					fecharegistro = new Date();
					cotfotopersonaClon.setFechamodificacion(fecharegistro);
					cotfotopersonaClon.setIplog(usuarioBean.getIp());
					cotfotopersonaClon.setSetusuario(usuarioBean.getSetUsuario());
					
					//actualizar
					cotfotopersonaDAO.updateCotfotopersona(session, cotfotopersonaClon);
					
					//eliminar foto del disco
					String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
					
					String rutaArchivo = rutaImagenes + cotfotopersonaClon.getRuta();
					
					fileUtil.deleteFile(rutaArchivo);
					ok = true;
				}
			}
			
			//Si subio foto se crea en disco y en base
			if(uploadedFile != null){
				creaFotoDiscoBD(cotpersona, cotfotopersona, uploadedFile, session);
				ok = true;
			}
			
			if(ok){
				session.getTransaction().commit();
			}
		}catch(Exception e){
			session.getTransaction().rollback();
			throw new Exception();
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean eliminar(Cotpersona cotpersona) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
			CotfotopersonaDAO cotfotopersonaDAO = new CotfotopersonaDAO();
			
			//se eliminan fisicamente las fotos asociadas a la persona
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
			
			for(Cotfotopersona tmp : cotpersona.getCotfotopersonas()){
				String rutaArchivo = rutaImagenes + tmp.getRuta();
				fileUtil.deleteFile(rutaArchivo);
				
				//se inactivan todas las fotos asociadas a la persona
				Setestado setestado = new Setestado();
				setestado.setIdestado(2);
				tmp.setSetestado(setestado);
				
				//auditoria
				Date fecharegistro = new Date();
				tmp.setFechamodificacion(fecharegistro);
				tmp.setIplog(usuarioBean.getIp());
				tmp.setSetusuario(usuarioBean.getSetUsuario());
				
				//actualizar
				cotfotopersonaDAO.updateCotfotopersona(session, tmp);
			}			 
			
			//se inactiva el registro
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);
			cotpersona.setSetestado(setestado);
			
			//auditoria
			Date fecharegistro = new Date();
			cotpersona.setFechamodificacion(fecharegistro);
			cotpersona.setIplog(usuarioBean.getIp());
			cotpersona.setSetusuario(usuarioBean.getSetUsuario());
			
			cotpersonaDAO.updateCotpersona(session, cotpersona);
			
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

	public void grabarPersonaBasico(Session session, Cotpersona cotpersona) throws Exception{
			
		CotpersonaDAO cotpersonaDAO = new CotpersonaDAO();
		
		int max = cotpersonaDAO.maxIdCotpersona(session)+1;
		Date fecharegistro = new Date();
		UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
		
		cotpersona.setIdpersona(max);
		cotpersona.setFecharegistro(fecharegistro);
		cotpersona.setIplog(usuarioBean.getIp());
		cotpersona.getSetestado().setIdestado(1);
		cotpersona.setSetusuario(usuarioBean.getSetUsuario());
		
		cotpersona.setObjeto(null);
		cotpersonaDAO.saveCotpersona(session, cotpersona);
			
	}
}
