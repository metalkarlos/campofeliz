package com.web.pet.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.pet.bo.SetpeticionclaveBO;
import com.web.pet.bo.SetusuarioBO;
import com.web.pet.pojo.annotations.Setpeticionclave;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;
import com.web.util.Utilities;

@ManagedBean
@ViewScoped
public class CambiarClaveBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8457542780198016959L;
	private String claveNueva;
	private String claveNuevaRep;
	private UUID uid;
	private Setpeticionclave setpeticionclave;
	
	public CambiarClaveBean() {
		claveNueva = "";
		claveNuevaRep = "";
	}
	
	@PostConstruct
	public void initCambiarClaveBean() {
		FacesUtil facesUtil = new FacesUtil();
		
		try{
			Object par = facesUtil.getParametroUrl("uid");
			
			if(par != null){
				uid = UUID.fromString(par.toString());
				
				SetpeticionclaveBO setpeticionclaveBO = new SetpeticionclaveBO();
				setpeticionclave = setpeticionclaveBO.getSetpeticionclaveByUid(uid);
				
				if(setpeticionclave == null || setpeticionclave.getIdpeticionclave() == 0){
					facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");
				}
			}else{
				facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");
			}
		}catch(Exception e){
			e.printStackTrace();
			try{
				facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");
			}catch(Throwable th){}
		}
	}
	
	public void cambiarClave(){
		if(validacionOk()){
			try{
				boolean ok = false;
				
				Utilities utilities = new Utilities();
				String cifrado = utilities.cifrar(claveNueva);
				
				SetusuarioBO setusuarioBO = new SetusuarioBO();
				ok = setusuarioBO.cambiarClave(setpeticionclave.getUsuario(), cifrado);
				
				if(ok){
					FacesUtil facesUtil = new FacesUtil();
					facesUtil.redirect("../pages/login.jsf");
				}else{
					new MessageUtil().showInfoMessage("No se ha podido cambiar la clave", "");
				}
			}catch(Exception e){
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
			}
		}
	}
	
	private boolean validacionOk(){
		boolean ok = false;
		
		if(claveNueva.compareTo(claveNuevaRep) == 0){
			ok = true;
		}else{
			new MessageUtil().showWarnMessage("Claves no son iguales. Corrija e intente nuevamente", "");
		}
		
		return ok;
	}
	
	public String getClaveNueva() {
		return claveNueva;
	}

	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}

	public String getClaveNuevaRep() {
		return claveNuevaRep;
	}

	public void setClaveNuevaRep(String claveNuevaRep) {
		this.claveNuevaRep = claveNuevaRep;
	}

}
