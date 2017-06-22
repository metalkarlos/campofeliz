package com.web.pet.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.LazyScheduleModel;

import com.web.pet.bo.CoteventoBO;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cotevento;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class AgendaBean implements Serializable{

	private static final long serialVersionUID = -1996637487952329953L;
    private ScheduleModel lazyEventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private String theme;
    private TimeZone timezone = TimeZone.getDefault();
    private List<Cotevento> lisCotevento;
    private Cotevento cotevento;

	public AgendaBean() {
		cotevento = new Cotevento();
		consultarEventos();
    }
	
	@SuppressWarnings("serial")
	private void consultarEventos(){
		try
		{
			lazyEventModel = new LazyScheduleModel() {
				public void loadEvents(Date start, Date end) {
	                clear();
	                CoteventoBO coteventoBO = new CoteventoBO();
	                lisCotevento = coteventoBO.lisCotevento(start, end);
	                for(Cotevento evento : lisCotevento){
	                	long diff = (evento.getFechahasta().getTime()-evento.getFechadesde().getTime());
	                	boolean isAllDay = (diff == 86399000);
	                	DefaultScheduleEvent defaultScheduleEvent = new DefaultScheduleEvent(evento.getTitulo(), evento.getFechadesde(), evento.getFechahasta(), isAllDay);
	                	defaultScheduleEvent.setData(evento);
	                	addEvent(defaultScheduleEvent);
	                }
	            }
	    	};
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
    
    public void addEvent(ActionEvent actionEvent) {
        grabarEvento();
    }
    
    private void grabarEvento(){
    	try{
    		CoteventoBO coteventoBO = new CoteventoBO();
    		
    		cotevento.setTitulo(event.getTitle());
    		cotevento.setFechadesde(event.getStartDate());
    		cotevento.setFechahasta(event.getEndDate());
    		
    		if(cotevento.getIdevento() == 0){
                lazyEventModel.addEvent(event);
                coteventoBO.newCotevento(cotevento, event.isAllDay());
            }else{
                lazyEventModel.updateEvent(event);
                coteventoBO.updateEvento(cotevento);
            }
    		
    		event = new DefaultScheduleEvent();
    		cotevento = new Cotevento(0,new Setestado(),new Setusuario(),null,null,null,null,null,null);
    		
    		new MessageUtil().showInfoMessage("Registro completo!","");
    	}catch(Exception e){
    		e.printStackTrace();
    		new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
    	}
    }
    
    public void eliminarEvento(ActionEvent actionEvent){
    	if(event.getId() != null){
    		try {
	            lazyEventModel.updateEvent(event);
	            //Cotevento cotevento = (Cotevento)event.getData();
	            CoteventoBO coteventoBO = new CoteventoBO();
	            coteventoBO.deleteCotevento(cotevento.getIdevento());
	            new MessageUtil().showInfoMessage("Registro eliminado!","");
    		} catch(Exception e){
    			e.printStackTrace();
    			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
    		}
        }
    }
    
    public void onEventSelect(SelectEvent selectEvent) {
    	event = (ScheduleEvent)selectEvent.getObject();
    	cotevento = event.getData() == null ? new Cotevento() :  (Cotevento) event.getData();
    }
	
	public void onDateSelect(SelectEvent selectEvent) {
		Date date = (Date) selectEvent.getObject();
		event = new DefaultScheduleEvent("Evento", date, date);
		cotevento = event.getData() == null ? new Cotevento() :  (Cotevento) event.getData();
	}
	
	public void onEventMove(ScheduleEntryMoveEvent moveEvent) {
		try  {
			event = moveEvent.getScheduleEvent();
	    	cotevento = event.getData() == null ? new Cotevento() :  (Cotevento) event.getData();
	    	
	    	cotevento.setTitulo(event.getTitle());
			cotevento.setFechadesde(event.getStartDate());
			cotevento.setFechahasta(event.getEndDate());
			
			CoteventoBO coteventoBO = new CoteventoBO();
			coteventoBO.updateEvento(cotevento);
	    	
			new MessageUtil().showInfoMessage("Evento modificado!","");
		}catch(Exception e){
    		e.printStackTrace();
    		new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
    	}
	}
	
	public void onEventResize(ScheduleEntryResizeEvent event) {
		new MessageUtil().showInfoMessage("Evento ha sido modificado! Selecciona el evento y guarda tus cambios!","");
	}
	
	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
    }
	
	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}
	
	public ScheduleEvent getEvent() {
		return event;
	}
	
	public Cotevento getCotevento() {
		return cotevento;
	}

	public void setCotevento(Cotevento cotevento) {
		this.cotevento = cotevento;
	}

	public String getTheme() {
		return theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}
	
	public TimeZone getTimezone() {
		return timezone;
	}
    
}
