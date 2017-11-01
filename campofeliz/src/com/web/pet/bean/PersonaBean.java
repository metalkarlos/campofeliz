package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;

import com.web.pet.bo.CotfotopersonaBO;
import com.web.pet.bo.CotpersonaBO;
import com.web.pet.bo.CottipoidentificacionBO;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cotfotopersona;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;
import com.web.util.Utilities;

@ManagedBean
@ViewScoped
public class PersonaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9199679241560273036L;
	private int idpersona;
	private Cotpersona cotpersona;
	private Cotfotopersona cotfotopersonaSelected;
	private Cottipoidentificacion cottipoidentificacionselected;
	private Cotpersona cotpersonaClon;
	private List<Cottipoidentificacion> lisCottipoidentificacion;
	private List<Cotfotopersona> lisCotfotopersona;
	private List<Cotfotopersona> lisCotfotopersonaClon;	
	private boolean fotoSubida;
	private long maxfilesize;
	
	public PersonaBean() {
		cotpersona = new Cotpersona(0, new Cottipoidentificacion(0,null,null), new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		cotpersonaClon = new Cotpersona(0, new Cottipoidentificacion(0,null,null), new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		lisCotfotopersona = new ArrayList<Cotfotopersona>();
		lisCotfotopersonaClon = new ArrayList<Cotfotopersona>();
		cottipoidentificacionselected = new Cottipoidentificacion(0,null,null);
		cotfotopersonaSelected = new Cotfotopersona();
		fotoSubida = false;
		maxfilesize = Parametro.TAMAÑO_IMAGEN;
		llenarLisTipoidentificacion();
	}
	
	@PostConstruct
	public void PostPersonaBean() {
		FacesUtil facesUtil = new FacesUtil();
		
		try{
			Object par = facesUtil.getParametroUrl("idpersona");
			if(par != null){
				idpersona = Integer.parseInt(par.toString());
				
				if(idpersona > 0){
					CotpersonaBO cotpersonaBO = new CotpersonaBO();
					cotpersona = cotpersonaBO.getCotpersonaConObjetosById(idpersona);
					
					if(cotpersona != null){
						
						cotpersonaClon = cotpersona.clonar();
						
						if(cotpersona.getCottipoidentificacion() == null){
							cotpersona.setCottipoidentificacion(new Cottipoidentificacion(0,null,null));
						}
						
						if(cotpersonaClon.getCottipoidentificacion() == null){
							cotpersonaClon.setCottipoidentificacion(new Cottipoidentificacion(0,null,null));
						}
						
						CotfotopersonaBO cotfotopersonaBO = new CotfotopersonaBO();
						lisCotfotopersona = cotfotopersonaBO.lisPetfotopersonaByIdpersona(idpersona);
						if(lisCotfotopersona != null && lisCotfotopersona.size() > 0){
							for(Cotfotopersona cotfotopersona : lisCotfotopersona){
								lisCotfotopersonaClon.add(cotfotopersona.clonar());
							}
						}
					}else{
						cotpersona = new Cotpersona(0, new Cottipoidentificacion(0,null,null), new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
					}
				}
			}else{
				facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");
			}
		} catch(NumberFormatException ne){
			try{facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");}catch(Throwable e){}
		} catch(Exception e) {
			e.printStackTrace();
			try{facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");}catch(Throwable e2){}
		}
	}
	
	private void llenarLisTipoidentificacion(){
		try{
		
			lisCottipoidentificacion = new ArrayList<Cottipoidentificacion>();
			
			CottipoidentificacionBO cottipoidentificacionBO = new CottipoidentificacionBO();
			List<Cottipoidentificacion> lisTmp = cottipoidentificacionBO.lisCottipoidentificacion();
			if(lisTmp != null && lisTmp.size() > 0){
				lisCottipoidentificacion.addAll(lisTmp);
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void ponerFotoPrincipal(ActionEvent actionEvent)
	{
		cotpersona.setRuta(cotfotopersonaSelected.getRuta());
		cotfotopersonaSelected = new Cotfotopersona();
		new MessageUtil().showInfoMessage("Presione Grabar para guardar los cambios.","");
	}
	
	public void quitarFotoGaleria(){
		if(cotfotopersonaSelected.getRuta().equalsIgnoreCase(cotpersona.getRuta())){
			new MessageUtil().showInfoMessage("La foto a eliminar es la foto principal de ésta persona. Seleccione otra foto como principal para poderla eliminar.","");
		}else{
			lisCotfotopersona.remove(cotfotopersonaSelected);
			cotfotopersonaSelected = new Cotfotopersona();
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try{
			Cotfotopersona cotfotopersona = new Cotfotopersona();
			cotfotopersona.setObjeto(event.getFile().getContents());
			cotfotopersona.setNombrearchivo(event.getFile().getFileName().toLowerCase());
			lisCotfotopersona.add(cotfotopersona);
			fotoSubida = true;
			
			new MessageUtil().showInfoMessage("Presione Grabar para guardar los cambios.","");
		}catch(Exception x){
			x.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

	public void grabar(){
		try{
			if(validarCampos()){
				CotpersonaBO cotpersonaBO = new CotpersonaBO();
				boolean ok = false;
				
				if(cotpersona.getCottipoidentificacion() != null && cotpersona.getCottipoidentificacion().getIdtipoidentificacion() == 0){
					cotpersona.setCottipoidentificacion(null);
				}
				
				Utilities utilities = new Utilities();
				
				if(idpersona > 0){
					ok = cotpersonaBO.modificarCotpersona(cotpersona, cotpersonaClon, lisCotfotopersona, lisCotfotopersonaClon, null);
					if(ok){
						utilities.mostrarPaginaMensaje("Persona actualizada con exito!!");
					}else{
						new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
					}
				}else{
					ok = cotpersonaBO.ingresarCotpersona(cotpersona, lisCotfotopersona, null);
					if(ok){
						utilities.mostrarPaginaMensaje("Persona ingresada con exito!!");
					}else{
						new MessageUtil().showInfoMessage("No se ha podido ingresar la Persona. Comunicar al Webmaster.", "");
					}
				}
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public String cancelar(){
		return null;
	}
	
	private boolean validarCampos(){
		boolean ok = true;
		
		if(cotpersona.getApellido1() == null || cotpersona.getApellido1().trim().length() == 0){
			new MessageUtil().showWarnMessage("Datos incompletos! El Primer Apellido es obligatorio!","");
			ok = false;
		}else{
			if(cotpersona.getNombre1() == null || cotpersona.getNombre1().trim().length() == 0){
				new MessageUtil().showWarnMessage("Datos incompletos! El Primer Nombre es obligatorio!","");
				ok = false;
			}else{
				if(cotpersona.getCottipoidentificacion() != null && cotpersona.getCottipoidentificacion().getIdtipoidentificacion() > 1 && (cotpersona.getNumeroidentificacion() == null || cotpersona.getNumeroidentificacion().trim().length() == 0)){
					new MessageUtil().showWarnMessage("Datos incompletos! Si selecciona el Tipo de Identificación tambien debe ingresar el Número de Identificación!","");
					ok = false;
				}else{
					if(cotpersona.getNumeroidentificacion() != null && cotpersona.getNumeroidentificacion().trim().length() > 0 && (cotpersona.getCottipoidentificacion() == null || cotpersona.getCottipoidentificacion().getIdtipoidentificacion() == 0)){
						new MessageUtil().showWarnMessage("Datos incompletos! Si ingresa El Número de Identificación tambien debe seleccionar el Tipo de Identificación!","");
						ok = false;
					}
				}
			}
		}
		
		return ok;
	}
	
	public void eliminar(){
		try{
			CotpersonaBO cotpersonaBO = new CotpersonaBO();
			Utilities utilities = new Utilities();
			
			if(cotpersona.getCottipoidentificacion() != null && cotpersona.getCottipoidentificacion().getIdtipoidentificacion() == 0){
				cotpersona.setCottipoidentificacion(null);
			}
			
			boolean ok = cotpersonaBO.eliminar(cotpersona,lisCotfotopersona);
			if(ok){
				utilities.mostrarPaginaMensaje("Persona eliminada con exito!");
			}else{
				utilities.mostrarPaginaMensaje("No se ha podido eliminar la Persona. Comunicar al Webmaster.");
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public Cotpersona getCotpersona() {
		return cotpersona;
	}
	public void setCotpersona(Cotpersona cotpersona) {
		this.cotpersona = cotpersona;
	}

	public int getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}

	public List<Cottipoidentificacion> getLisCottipoidentificacion() {
		return lisCottipoidentificacion;
	}

	public void setLisCottipoidentificacion(List<Cottipoidentificacion> lisCottipoidentificacion) {
		this.lisCottipoidentificacion = lisCottipoidentificacion;
	}
	
	public Cottipoidentificacion getCottipoidentificacionselected() {
		return cottipoidentificacionselected;
	}

	public void setCottipoidentificacionselected(
			Cottipoidentificacion cottipoidentificacionselected) {
		this.cottipoidentificacionselected = cottipoidentificacionselected;
	}

	public Cotfotopersona getCotfotopersonaSelected() {
		return cotfotopersonaSelected;
	}

	public void setCotfotopersonaSelected(Cotfotopersona cotfotopersonaSelected) {
		this.cotfotopersonaSelected = cotfotopersonaSelected;
	}

	public List<Cotfotopersona> getLisCotfotopersona() {
		return lisCotfotopersona;
	}

	public void setLisCotfotopersona(List<Cotfotopersona> lisCotfotopersona) {
		this.lisCotfotopersona = lisCotfotopersona;
	}
	
	public boolean isFotoSubida() {
		return fotoSubida;
	}

	public void setFotoSubida(boolean fotoSubida) {
		this.fotoSubida = fotoSubida;
	}
	
	public long getMaxfilesize() {
		return maxfilesize;
	}

	public void setMaxfilesize(long maxfilesize) {
		this.maxfilesize = maxfilesize;
	}
	
}
