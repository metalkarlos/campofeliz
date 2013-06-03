package com.web.pet.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import com.web.pet.global.Parametro;
import com.web.util.MessageUtil;
import com.web.util.Utilities;

@ManagedBean
@Named
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
	
	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}
	
	public void imprimir(){
		try {
			if (validarCampos()) {
				String nombreReporte = "MascotasEstadiaxVencer";
				String rutaLogo = Parametro.RUTA_IMAGENES_MISCELLANEOUS+"logo_empresa.jpg";
				Map<String, Object> parametros = new HashMap<String, Object>();
				
				parametros.put("P_LOGO", rutaLogo);
				
				if(anio >=0 ){
				  parametros.put("P_ANIOS", anio);
				}
				new Utilities().imprimirJasperPdf(nombreReporte, parametros);
			}
		}catch (Exception e) {
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	private boolean validarCampos(){
		boolean ok = true;
		if(anio < 0){
			new MessageUtil().showFatalMessage("Datos incompletos!", "Debe ingresar los años a vencer!");
			ok = false;
		}
		return ok;
	}
}
