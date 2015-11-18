package com.web.pet.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.web.util.FacesUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class ReporteGraficoMascotasBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2356343430340449219L;
	private int tipografico;
	private int anio;
	
	public ReporteGraficoMascotasBean() {
		tipografico = 1;
		anio = Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public void imprimir(){
		try {
			FacesUtil facesUtil = new FacesUtil();
			
			VisorBean visorBean = (VisorBean)facesUtil.getSessionBean("visorBean");
			
			if(tipografico == 1){
				visorBean.setNombreReporte("GraficaMascotasxAnio");
			}
			else{
				if(tipografico == 2){
				  visorBean.getParametros().put("P_ANIO", anio);
				  visorBean.setNombreReporte("GraficaMascotasxMes");
				}
			}

			RequestContext.getCurrentInstance().execute("varDlgMostrarReporte.show()");
		}catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public int getTipografico() {
		return tipografico;
	}

	public void setTipografico(int tipografico) {
		this.tipografico = tipografico;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

}
