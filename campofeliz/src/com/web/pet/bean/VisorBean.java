package com.web.pet.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.web.util.FileUtil;
import com.web.util.MessageUtil;
import com.web.util.Utilities;

@ManagedBean
@SessionScoped
public class VisorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5784236366075587652L;
	private Map<String, Object> parametros;
	private String nombreReporte;
	
	public VisorBean() {
		parametros = new HashMap<String, Object>();
	}
	
	public void imprimir(){
		InputStream inputStream = null;
		
		try {
			if(nombreReporte != null){
				FileUtil fileUtil = new FileUtil();
				inputStream = fileUtil.getLogoEmpresaAsStream();
				if(inputStream != null){
					parametros.put("P_LOGO", inputStream);
				}
				
				new Utilities().imprimirJasperPdf(nombreReporte, parametros);
				
				parametros = new HashMap<String, Object>();
				nombreReporte = null;
			}
		}catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}finally {
			if(inputStream != null){
				try{
					inputStream.close();
				}catch(Exception e){
					
				}
			}
		}
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public String getNombreReporte() {
		return nombreReporte;
	}

	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}

}
