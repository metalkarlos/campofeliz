package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.DateSelectEvent;

import com.web.util.FileUtil;
import com.web.pet.bo.CoteventoBO;
import com.web.pet.pojo.annotations.Cotevento;
import com.web.util.MessageUtil;

@ManagedBean
@RequestScoped
public class HomeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8481913464233517487L;
	private Date date = new Date();
	private String strSpecialDates;
	private String strUrlAgenda;
	
	public HomeBean() {
		try {
			FileUtil fileUtil = new FileUtil();
			strUrlAgenda = fileUtil.getPropertyValue("agenda");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!", null);
		}
		
		mostrarEventosAgendados();
	}
	
	private void mostrarEventosAgendados(){
		Calendar primeraFechaMes = Calendar.getInstance();
		primeraFechaMes.set(primeraFechaMes.get(Calendar.YEAR),primeraFechaMes.get(Calendar.MONTH),1,0,0,0);
		
		Calendar ultimaFechaMes = Calendar.getInstance();
		ultimaFechaMes.set(ultimaFechaMes.get(Calendar.YEAR),ultimaFechaMes.get(Calendar.MONTH),ultimaFechaMes.getMaximum(Calendar.DAY_OF_MONTH),0,0,0);
		
		//consulta eventos agendados en el mes
		CoteventoBO coteventoBO = new CoteventoBO();
		List<Cotevento> lisCotevento = coteventoBO.lisCotevento(primeraFechaMes.getTime(), ultimaFechaMes.getTime());
		
		//crea lista de días agendados en el mes
		List<Integer> lisDiasAgendados = new ArrayList<Integer>();
		for(Cotevento evento : lisCotevento){
			Calendar calFechaDesde = Calendar.getInstance();
			calFechaDesde.setTime(evento.getFechadesde());
			
			Calendar calFechaHasta = Calendar.getInstance();
			calFechaHasta.setTime(evento.getFechahasta());
			
			while(calFechaDesde.compareTo(calFechaHasta) <= 0){
				lisDiasAgendados.add(calFechaDesde.get(Calendar.DAY_OF_MONTH));
				calFechaDesde.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		
		//eliminar días duplicados
		Set<Integer> set = new HashSet<Integer>(lisDiasAgendados);

		//pasa días a una cadena separada por comas
		Iterator<Integer> itrDates = set.iterator();
		strSpecialDates = "";
		while(itrDates.hasNext()){
			int intDate = itrDates.next();
			strSpecialDates += intDate;
			if(itrDates.hasNext()){
				strSpecialDates += ",";
			}
		}
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public String getStrSpecialDates() {
		return strSpecialDates;
	}

	public void setStrSpecialDates(String strSpecialDates) {
		this.strSpecialDates = strSpecialDates;
	}

	public String getStrUrlAgenda() {
		return strUrlAgenda;
	}

	public void setStrUrlAgenda(String strUrlAgenda) {
		this.strUrlAgenda = strUrlAgenda;
	}

	public void handleDateSelect(DateSelectEvent event) {  
		new MessageUtil().showInfoMessage("Hola! Estoy en construcción!","");  
    }

}
