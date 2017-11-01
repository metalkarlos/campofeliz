package com.web.pet.bo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.bean.UsuarioBean;
import com.web.pet.dao.PetformapagoordenDAO;
import com.web.pet.dao.PetordenservicioDAO;
import com.web.pet.dao.PetordenserviciodetalleDAO;
import com.web.pet.pojo.annotations.Cotfotopersona;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Petformapagoorden;
import com.web.pet.pojo.annotations.PetformapagoordenId;
import com.web.pet.pojo.annotations.Petfotomascota;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.pet.pojo.annotations.PetordenservicioId;
import com.web.pet.pojo.annotations.Petordenserviciodetalle;
import com.web.pet.pojo.annotations.PetordenserviciodetalleId;
import com.web.pet.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetordenservicioBO {
	
	public Petordenservicio getPetordenservicioById(PetordenservicioId petordenservicioId) throws Exception{
		Petordenservicio petordenservicio = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
			
			petordenservicio = petordenservicioDAO.getPetordenservicioById(session, petordenservicioId);
		}catch(Exception he) {
			throw new Exception(he);
		} finally {
			session.close();
		}
		
		return petordenservicio;
	}
	
	public List<Petordenservicio> lisPetordenservicioByPage(String[] nombres, int pageSize, int pageNumber, int args[]) throws RuntimeException{
		List<Petordenservicio> lisPetordenservicio = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
			
			lisPetordenservicio = petordenservicioDAO.lisPetordenservicioByPage(session, nombres, pageSize, pageNumber, args);
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			session.close();
		}
		
		return lisPetordenservicio;
	}
	
	public boolean ingresarPetordenservicio(Petordenservicio petordenservicio, List<Petordenserviciodetalle> lisPetordenserviciodetalle, List<Petformapagoorden> lisPetformapagoorden) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
			PetordenserviciodetalleDAO petordenserviciodetalleDAO = new PetordenserviciodetalleDAO();
			PetformapagoordenDAO petformapagoordenDAO = new PetformapagoordenDAO();
			
			//grabar cabecera
			int idanio = Calendar.getInstance().get(Calendar.YEAR);
			int idordenservicio = petordenservicioDAO.maxIdPetordenservicio(session, idanio) + 1;
			PetordenservicioId petordenservicioId = new PetordenservicioId();
			petordenservicioId.setIdordenservicio(idordenservicio);
			petordenservicioId.setIdanio(idanio);
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petordenservicio.setId(petordenservicioId);
			petordenservicio.setFecharegistro(fecharegistro);
			petordenservicio.setIplog(usuarioBean.getIp());
			petordenservicio.getSetestado().setIdestado(1);
			petordenservicio.setSetusuario(usuarioBean.getSetUsuario());
			
			petordenservicioDAO.savePetordenservicio(session, petordenservicio);
			
			//grabar detalle servicios
			for(Petordenserviciodetalle petordenserviciodetalle : lisPetordenserviciodetalle){

				int idordenserviciodetalle = petordenserviciodetalleDAO.maxIdPetordenserviciodetalleByParent(session, petordenservicioId) + 1;
				
				petordenserviciodetalle.setPetordenservicio(petordenservicio);
				
				PetordenserviciodetalleId petordenserviciodetalleId = new PetordenserviciodetalleId();
				petordenserviciodetalleId.setIdanio(petordenservicioId.getIdanio());
				petordenserviciodetalleId.setIdordenservicio(petordenservicioId.getIdordenservicio());
				petordenserviciodetalleId.setIdordenserviciodetalle(idordenserviciodetalle);
				
				petordenserviciodetalle.setId(petordenserviciodetalleId);
				
				//auditoria
				fecharegistro = new Date();
				petordenserviciodetalle.setFecharegistro(fecharegistro);
				petordenserviciodetalle.setIplog(usuarioBean.getIp());
				petordenserviciodetalle.setSetusuario(usuarioBean.getSetUsuario());
				Setestado setestado = new Setestado(); 
				setestado.setIdestado(1);
				petordenserviciodetalle.setSetestado(setestado);
				
				petordenserviciodetalleDAO.savePetordenserviciodetalle(session, petordenserviciodetalle);
			}
			
			//grabar detalle pagos
			for(Petformapagoorden petformapagoorden : lisPetformapagoorden){

				int idformapagoorden = petformapagoordenDAO.maxIdByParent(session, petordenservicioId) + 1;
				
				petformapagoorden.setPetordenservicio(petordenservicio);
				
				PetformapagoordenId petformapagoordenId = new PetformapagoordenId();
				petformapagoordenId.setIdanio(petordenservicioId.getIdanio());
				petformapagoordenId.setIdordenservicio(petordenservicioId.getIdordenservicio());
				petformapagoordenId.setIdformapagoorden(idformapagoorden);
				
				petformapagoorden.setId(petformapagoordenId);
				
				//auditoria
				fecharegistro = new Date();
				petformapagoorden.setFecharegistro(fecharegistro);
				petformapagoorden.setIplog(usuarioBean.getIp());
				petformapagoorden.setSetusuario(usuarioBean.getSetUsuario());
				Setestado setestado = new Setestado(); 
				setestado.setIdestado(1);
				petformapagoorden.setSetestado(setestado);
				
				petformapagoordenDAO.grabarPetformapagoorden(session, petformapagoorden);
			}
			
			session.getTransaction().commit();
			ok = true;
		}catch(Exception e){
			petordenservicio.setId(null);
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean modificarPetordenservicio(Petordenservicio petordenservicio, Petordenservicio petordenservicioClon, List<Petordenserviciodetalle> lisPetordenserviciodetalle, List<Petordenserviciodetalle> lisPetordenserviciodetalleClon, List<Petformapagoorden> lisPetformapagoorden, List<Petformapagoorden> lisPetformapagoordenClon) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
			PetordenserviciodetalleDAO petordenserviciodetalleDAO = new PetordenserviciodetalleDAO();
			PetformapagoordenDAO petformapagoordenDAO = new PetformapagoordenDAO();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			//Se graba el servicio si han habido cambios
			if(!petordenservicio.equals(petordenservicioClon)){
				petordenservicio.setFechamodificacion(fecharegistro);
				petordenservicio.setIplog(usuarioBean.getIp());
				petordenservicio.setSetusuario(usuarioBean.getSetUsuario());
				petordenservicioDAO.updatePetordenservicio(session, petordenservicio);
				ok = true;
			}
			
			//Se evalua si han habido cambios en la lista de servicios
			for(Petordenserviciodetalle petordenserviciodetalleClon : lisPetordenserviciodetalleClon){
				boolean encuentra = false;
				for(Petordenserviciodetalle petordenserviciodetalleItem : lisPetordenserviciodetalle){
					if(petordenserviciodetalleClon.getId().equals(petordenserviciodetalleItem.getId())){
						//si encuentra
						encuentra = true;
						
						if(!petordenserviciodetalleClon.equals(petordenserviciodetalleItem)){
							//si han habido cambios se actualiza
							
							//auditoria
							fecharegistro = new Date();
							petordenserviciodetalleItem.setFechamodificacion(fecharegistro);
							petordenserviciodetalleItem.setIplog(usuarioBean.getIp());
							petordenserviciodetalleItem.setSetusuario(usuarioBean.getSetUsuario());
							
							//actualizar
							petordenserviciodetalleDAO.updatePetordenserviciodetalle(session, petordenserviciodetalleItem);
							ok = true;
						}
						
						break;
					}
				}
				if(!encuentra){
					//no encuentra
					//inhabilitar
					Setestado setestado = new Setestado();
					setestado.setIdestado(2);
					petordenserviciodetalleClon.setSetestado(setestado);
					
					//auditoria
					fecharegistro = new Date();
					petordenserviciodetalleClon.setFechamodificacion(fecharegistro);
					petordenserviciodetalleClon.setIplog(usuarioBean.getIp());
					petordenserviciodetalleClon.setSetusuario(usuarioBean.getSetUsuario());
					
					//actualizar
					petordenserviciodetalleDAO.updatePetordenserviciodetalle(session, petordenserviciodetalleClon);
					
					ok = true;
				}
			}
			
			//Se evalua si han subido nuevos servicios
			for(Petordenserviciodetalle petordenserviciodetalleItem : lisPetordenserviciodetalle){
				boolean encuentra = false;
				for(Petordenserviciodetalle petordenserviciodetalleClon : lisPetordenserviciodetalleClon){
					if(petordenserviciodetalleItem.getId().equals(petordenserviciodetalleClon.getId())){
						//si encuentra
						encuentra = true; 
						break;
					}
				}
				//no encuentra en lista clonada
				if(!encuentra){
					int idordenserviciodetalle = petordenserviciodetalleDAO.maxIdPetordenserviciodetalleByParent(session, petordenservicio.getId()) + 1;
					
					petordenserviciodetalleItem.setPetordenservicio(petordenservicio);
					
					PetordenserviciodetalleId petordenserviciodetalleId = new PetordenserviciodetalleId();
					petordenserviciodetalleId.setIdanio(petordenservicio.getId().getIdanio());
					petordenserviciodetalleId.setIdordenservicio(petordenservicio.getId().getIdordenservicio());
					petordenserviciodetalleId.setIdordenserviciodetalle(idordenserviciodetalle);
					
					petordenserviciodetalleItem.setId(petordenserviciodetalleId);
					
					//auditoria
					fecharegistro = new Date();
					petordenserviciodetalleItem.setFecharegistro(fecharegistro);
					petordenserviciodetalleItem.setIplog(usuarioBean.getIp());
					petordenserviciodetalleItem.setSetusuario(usuarioBean.getSetUsuario());
					Setestado setestado = new Setestado(); 
					setestado.setIdestado(1);
					petordenserviciodetalleItem.setSetestado(setestado);
					
					petordenserviciodetalleDAO.savePetordenserviciodetalle(session, petordenserviciodetalleItem);
					ok = true;
				}
			}
			
			//Se evalua si han habido cambios en la lista de pagos
			for(Petformapagoorden petformapagoordenClon : lisPetformapagoordenClon){
				boolean encuentra = false;
				for(Petformapagoorden petformapagoordenItem : lisPetformapagoorden){
					if(petformapagoordenClon.getId().equals(petformapagoordenItem.getId())){
						//si encuentra
						encuentra = true;
						
						if(!petformapagoordenClon.equals(petformapagoordenItem)){
							//si han habido cambios se actualiza
							
							//auditoria
							fecharegistro = new Date();
							petformapagoordenItem.setFechamodificacion(fecharegistro);
							petformapagoordenItem.setIplog(usuarioBean.getIp());
							petformapagoordenItem.setSetusuario(usuarioBean.getSetUsuario());
							
							//actualizar
							petformapagoordenDAO.actualizarPetformapagoorden(session, petformapagoordenItem);
							ok = true;
						}
						
						break;
					}
				}
				if(!encuentra){
					//no encuentra
					//inhabilitar
					Setestado setestado = new Setestado();
					setestado.setIdestado(2);
					petformapagoordenClon.setSetestado(setestado);
					
					//auditoria
					fecharegistro = new Date();
					petformapagoordenClon.setFechamodificacion(fecharegistro);
					petformapagoordenClon.setIplog(usuarioBean.getIp());
					petformapagoordenClon.setSetusuario(usuarioBean.getSetUsuario());
					
					//actualizar
					petformapagoordenDAO.actualizarPetformapagoorden(session, petformapagoordenClon);
					
					ok = true;
				}
			}
			
			//Se evalua si han subido nuevos pagos
			for(Petformapagoorden petformapagoordenItem : lisPetformapagoorden){
				boolean encuentra = false;
				for(Petformapagoorden petformapagoordenClon : lisPetformapagoordenClon){
					if(petformapagoordenItem.getId().equals(petformapagoordenClon.getId())){
						//si encuentra
						encuentra = true; 
						break;
					}
				}
				//no encuentra en lista clonada
				if(!encuentra){
					int idformapagoorden = petformapagoordenDAO.maxIdByParent(session, petordenservicio.getId()) + 1;
					
					petformapagoordenItem.setPetordenservicio(petordenservicio);
					
					PetformapagoordenId petformapagoordenId = new PetformapagoordenId();
					petformapagoordenId.setIdanio(petordenservicio.getId().getIdanio());
					petformapagoordenId.setIdordenservicio(petordenservicio.getId().getIdordenservicio());
					petformapagoordenId.setIdformapagoorden(idformapagoorden);
					
					petformapagoordenItem.setId(petformapagoordenId);
					
					//auditoria
					fecharegistro = new Date();
					petformapagoordenItem.setFecharegistro(fecharegistro);
					petformapagoordenItem.setIplog(usuarioBean.getIp());
					petformapagoordenItem.setSetusuario(usuarioBean.getSetUsuario());
					Setestado setestado = new Setestado(); 
					setestado.setIdestado(1);
					petformapagoordenItem.setSetestado(setestado);
					
					petformapagoordenDAO.grabarPetformapagoorden(session, petformapagoordenItem);
					ok = true;
				}
			}
			
			if(ok){
				session.getTransaction().commit();
			}
		}catch(Exception e){
			ok = false;
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean eliminarPetordenservicio(Petordenservicio petordenservicio, List<Petordenserviciodetalle> lisPetordenserviciodetalle, List<Petformapagoorden> lisPetformapagoorden) throws Exception{
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			PetordenservicioDAO petordenservicioDAO = new PetordenservicioDAO();
			PetordenserviciodetalleDAO petordenserviciodetalleDAO = new PetordenserviciodetalleDAO();
			PetformapagoordenDAO petformapagoordenDAO = new PetformapagoordenDAO();
		
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			petordenservicio.setSetestado(setestado);
			
			petordenservicio.setFechamodificacion(fecharegistro);
			petordenservicio.setIplog(usuarioBean.getIp());
			petordenservicio.setSetusuario(usuarioBean.getSetUsuario());
			petordenservicioDAO.updatePetordenservicio(session, petordenservicio);
			
			//se eliminan los servicios en caso de existir
			for(Petordenserviciodetalle tmp : lisPetordenserviciodetalle){
				//petordenserviciodetalleDAO.deletePetordenserviciodetalle(session, tmp.getId());
				Setestado setestadoservicios = new Setestado();
				setestadoservicios.setIdestado(2);//inactivo
				tmp.setSetestado(setestadoservicios);
				
				fecharegistro = new Date();
				tmp.setFechamodificacion(fecharegistro);
				tmp.setIplog(usuarioBean.getIp());
				tmp.setSetusuario(usuarioBean.getSetUsuario());
				
				petordenserviciodetalleDAO.updatePetordenserviciodetalle(session, tmp);
			}
			
			//se eliminan los pagos en caso de existir
			for(Petformapagoorden petformapagoorden : lisPetformapagoorden){
				Setestado setestadoPago = new Setestado();
				setestadoPago.setIdestado(2);//inactivo
				petformapagoorden.setSetestado(setestadoPago);
				
				fecharegistro = new Date();
				petformapagoorden.setFechamodificacion(fecharegistro);
				petformapagoorden.setIplog(usuarioBean.getIp());
				petformapagoorden.setSetusuario(usuarioBean.getSetUsuario());
				
				petformapagoordenDAO.actualizarPetformapagoorden(session, petformapagoorden);
			}
			
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

	public boolean grabarMascotaBasico(Petmascotahomenaje petmascotahomenaje, Petmascotahomenaje petmascotahomenajeClon, Cotpersona cotpersona, Cotpersona cotpersonaClon) throws Exception {
		boolean okPersona = false;
		boolean okMascota = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			CotpersonaBO cotpersonaBO = new CotpersonaBO();
			
			if(cotpersona.getIdpersona() == 0){
				//cotpersonaBO.grabarPersonaBasico(session, cotpersona);
				okPersona = cotpersonaBO.ingresarCotpersona(cotpersona, null, session);
			}else{
				okPersona = cotpersonaBO.modificarCotpersona(cotpersona, cotpersonaClon, new ArrayList<Cotfotopersona>(), new ArrayList<Cotfotopersona>(), session);
			}
			
			PetmascotaBO petmascotaBO = new PetmascotaBO();
			petmascotahomenaje.setCotpersona(cotpersona);
			
			if(petmascotahomenaje.getIdmascota() == 0){
				//petmascotaBO.grabarMascotaBasico(session, petmascotahomenaje);
				okMascota = petmascotaBO.ingresarMascota(petmascotahomenaje, null, new ArrayList<Petmascotacolor>(), session);
			}else{
				okMascota = petmascotaBO.modificarMascota(petmascotahomenaje, petmascotahomenajeClon, new ArrayList<Petfotomascota>(), new ArrayList<Petfotomascota>(), new ArrayList<Petmascotacolor>(), new ArrayList<Petmascotacolor>(), session);
			}
			
			if(okPersona || okMascota){
				session.getTransaction().commit();
			}
		}catch(Exception he){
			okPersona = false;
			okMascota = false;
			session.getTransaction().rollback();
			throw new Exception(he); 
		}finally{
			session.close();
		}
		
		return (okPersona || okMascota);
	}
}
