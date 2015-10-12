package com.web.pet.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.util.FileUtil;
import com.web.util.MessageUtil;
import com.web.util.Utilities;

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

	public void imprimir(){
		InputStream inputStream = null;
		
		try {
			if (validarCampos()) {
				String nombreReporte = "MascotasxTiempo";
				Map<String, Object> parametros = new HashMap<String, Object>();
				
				FileUtil fileUtil = new FileUtil();
				inputStream = fileUtil.getLogoEmpresaAsStream();
				if(inputStream != null){
					parametros.put("P_LOGO", inputStream);
				}
					  
				if(fechadesde !=null){
					parametros.put("P_FECHA_DESDE", fechadesde);
				}else{
					parametros.put("P_FECHA_DESDE", null);
				}
  
				if(fechahasta != null){
					parametros.put("P_FECHA_HASTA", fechahasta);  
				}else{
					parametros.put("P_FECHA_HASTA", null);
				}
	
				new Utilities().imprimirJasperPdf(nombreReporte, parametros);
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
	
}
