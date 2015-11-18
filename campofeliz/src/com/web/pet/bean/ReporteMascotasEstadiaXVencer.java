package com.web.pet.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.web.util.FacesUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class ReporteMascotasEstadiaXVencer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8606123902697883482L;
	private int anio;
	
	public  ReporteMascotasEstadiaXVencer(){
		anio = 0;
	}
	
	public void imprimir(){
		try {
			if (validarCampos()) {
				FacesUtil facesUtil = new FacesUtil();
				
				VisorBean visorBean = (VisorBean)facesUtil.getSessionBean("visorBean");
				visorBean.setNombreReporte("MascotasEstadiaxVencer");
				
				if(anio >= 0){
					visorBean.getParametros().put("P_ANIOS", anio);
				}
  
				RequestContext.getCurrentInstance().execute("varDlgMostrarReporte.show()");
			}
		}catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private boolean validarCampos(){
		boolean ok = true;
		if(anio < 0){
			new MessageUtil().showFatalMessage("Datos incompletos! Debe ingresar los años a vencer!","");
			ok = false;
		}
		return ok;
	}
	
	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}
}
