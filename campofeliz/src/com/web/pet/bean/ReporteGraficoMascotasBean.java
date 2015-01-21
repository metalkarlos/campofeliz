package com.web.pet.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.util.MessageUtil;
import com.web.util.Utilities;

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

	public void imprimir(){
		try {
			String nombreReporte = "";
			String rutaLogo = "logo_empresa.jpg";//TODO logo
			Map<String, Object> parametros = new HashMap<String, Object>();
			
			if(tipografico == 1){
			  nombreReporte = "GraficaMascotasxAnio";
			}
			else{
				if(tipografico == 2){
				  parametros.put("P_ANIO", anio);
				  nombreReporte = "GraficaMascotasxMes";
				}
			}
			
			parametros.put("P_LOGO", rutaLogo);
				
			new Utilities().imprimirJasperPdf(nombreReporte, parametros);
		}catch (Exception e) {
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

}
