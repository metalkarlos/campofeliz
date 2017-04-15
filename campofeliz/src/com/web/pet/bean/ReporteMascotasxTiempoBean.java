package com.web.pet.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.web.util.FacesUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class ReporteMascotasxTiempoBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6553301468560185338L;
	private Date fechadesde;
	private Date fechahasta;
	
	public  ReporteMascotasxTiempoBean(){
		fechadesde = new Date();
		fechahasta = new Date();
	}
	
	public void imprimir(){
		try {
			if (validarCampos()) {
				FacesUtil facesUtil = new FacesUtil();
				
				VisorBean visorBean = (VisorBean)facesUtil.getSessionBean("visorBean");
				visorBean.setNombreReporte("MascotasxTiempo");
				
				if(fechadesde !=null){
					visorBean.getParametros().put("P_FECHA_DESDE", fechadesde);
				}else{
					visorBean.getParametros().put("P_FECHA_DESDE", null);
				}
  
				if(fechahasta != null){
					visorBean.getParametros().put("P_FECHA_HASTA", fechahasta);  
				}else{
					visorBean.getParametros().put("P_FECHA_HASTA", null);
				}
				
				RequestContext.getCurrentInstance().execute("window.open('visor_reportes.jsf')");
			}
		}catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private boolean validarCampos(){
		boolean ok = true;
		
		if((fechadesde != null && fechahasta != null)&&(fechahasta.before(fechadesde))){
			new MessageUtil().showFatalMessage("Datos incompletos! Fecha Desde debe estar antes de Fecha Hasta!","");
			ok = false;
		}
		
		return ok;
	}
	
	public Date getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(Date fechadesde) {
		this.fechadesde = fechadesde;
	}

	public Date getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(Date fechahasta) {
		this.fechahasta = fechahasta;
	}
}
