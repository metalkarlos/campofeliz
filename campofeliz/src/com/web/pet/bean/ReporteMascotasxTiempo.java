package com.web.pet.bean;

import java.io.Serializable;
import java.util.Date;
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
public class ReporteMascotasxTiempo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6411248694906500853L;
	private int tipolistado;
	private int anio;
	private int mes;
	private int tipografica;
	private Date fechadesde;
	private Date fechahasta;
	
	
	
	public int getTipolistado() {
		return tipolistado;
	}




	public void setTipolistado(int tipolistado) {
		this.tipolistado = tipolistado;
	}




	public int getAnio() {
		return anio;
	}




	public void setAnio(int anio) {
		this.anio = anio;
	}




	public int getMes() {
		return mes;
	}




	public void setMes(int mes) {
		this.mes = mes;
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




	public int getTipografica() {
		return tipografica;
	}




	public void setTipografica(int tipografica) {
		this.tipografica = tipografica;
	}




	public  ReporteMascotasxTiempo(){
		tipolistado=0;
		tipografica =0;
		anio=0;
		mes=0;
		fechadesde = null;
		fechahasta = null;
	}
	
	
	public void imprimir(){
		try {
			if (validarCampos()) {
				String nombreReporte = new String("");
				String rutaLogo = Parametro.RUTA_IMAGENES_MISCELLANEOUS+"logo_empresa.jpg";
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("P_LOGO", rutaLogo);
				
				if(tipolistado == 1){
				  
				  nombreReporte = "MascotasxTiempo";
				  parametros.put("P_ANIO", anio);
				  parametros.put("P_TIEMPO", "Año"); 
				  
				  if(mes >0){
					  parametros.put("P_MES", mes);  
					  parametros.put("P_TIEMPO", "Mes");
				  }else{
					  parametros.put("P_MES", null);
				  }
				  if(fechadesde !=null){
					  parametros.put("P_FECHA_DESDE", fechadesde);
					  parametros.put("P_TIEMPO", "Rangos de Fechas");
				  }else{
					  parametros.put("P_FECHA_DESDE", null);
				  }
				  if(fechahasta  !=null){
					  parametros.put("P_FECHA_HASTA", fechahasta);  
				  }else{
					  parametros.put("P_FECHA_HASTA", null);
				  }
				  
				}else if(tipolistado == 2 && tipografica ==1){
				  nombreReporte = "GraficaMascotasxAnio";
				  
				}else if(tipolistado == 2 && tipografica ==2){
					  parametros.put("P_ANIO", anio);
					  nombreReporte = "GraficaMascotasxMes";
				}
					
				
				
				new Utilities().imprimirJasperPdf(nombreReporte, parametros);
			}
		}catch (Exception e) {
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	
private boolean validarCampos(){
	boolean ok = true;
	
	if(tipolistado ==0){
		new MessageUtil().showFatalMessage("Datos incompletos!", "Debe seleccionar el tipo de Reporte!");
		ok = false;
	}else{
		if((tipolistado ==1) && (anio ==0 && mes ==0 && fechadesde == null && fechahasta == null)){
			new MessageUtil().showFatalMessage("Datos incompletos!", "Para Tipo de Reporte: Listado, debe de ingresar por lo menos el Año!");
			ok = false;
		}else{
			if((fechadesde != null && fechahasta != null)&&(fechahasta.before(fechadesde))){
				new MessageUtil().showFatalMessage("Datos incompletos!", "Fecha Desde debe estar antes de Fecha Hasta!");
				ok = false;
			}else{
				if(tipolistado ==2 && tipografica ==0){
					new MessageUtil().showFatalMessage("Datos incompletos!", "Para Tipo de Reporte: Gráfica, debe seleccionar Año o Mes!");
					ok = false;
				}else{
					if(tipolistado ==2 && tipografica ==2 && anio ==0){
						new MessageUtil().showFatalMessage("Datos incompletos!", "Para Tipo de Reporte: Gráfica, en filtro mes debe ingresar el  Año!");
						ok = false;
					}	
				}
			}
		}
	}
	
	
	return ok;
	
}
	
}
