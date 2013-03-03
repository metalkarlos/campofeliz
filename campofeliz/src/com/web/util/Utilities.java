package com.web.util;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.hibernate.Session;

import com.web.pet.global.Parametro;

public class Utilities {
	
	public void imprimirJasperPdf(String nombreReporte, Map<String, Object> parametros) throws Exception {
		
		try{
			String rutaReporte = Parametro.RUTA_REPORTES+nombreReporte+".jasper";
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Connection connection = session.connection();
	        
			JasperPrint jasperPrint = JasperFillManager.fillReport(rutaReporte, parametros, connection);//new JREmptyDataSource());
			
			//crea en disco
			JasperExportManager.exportReportToPdfFile(jasperPrint, Parametro.RUTA_REPORTES+nombreReporte+".pdf");
			
			//muestra en browser
			FacesContext.getCurrentInstance().getExternalContext().responseReset(); 
			FacesContext.getCurrentInstance().getExternalContext().setResponseContentType(FacesContext.getCurrentInstance().getExternalContext().getMimeType(rutaReporte)); 
			//FacesContext.getCurrentInstance().getExternalContext().setResponseContentLength(contentLength); 
			FacesContext.getCurrentInstance().getExternalContext().setResponseHeader("Content-disposition", "attachment; filename=\""+nombreReporte+".pdf\"");//inline 
	        OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	        FacesContext.getCurrentInstance().responseComplete();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception();
		}
	}
	
}
