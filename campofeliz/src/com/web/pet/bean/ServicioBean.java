package com.web.pet.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CotservicioBO;
import com.web.pet.pojo.annotations.Cotestado;
import com.web.pet.pojo.annotations.Cotservicio;
import com.web.pet.pojo.annotations.Cottiposervicio;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class ServicioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 613490246931868149L;
	private Cotservicio cotservicioItem;
	private LazyDataModel<Cotservicio> lisCotservicio;
	
	public ServicioBean() {
		cotservicioItem = new Cotservicio(0, new Cotestado(), new Setusuario(), new Cottiposervicio(), null, null, null, null, new BigDecimal(0));
		consultarServicio();
	}
	
	@SuppressWarnings("serial")
	private void consultarServicio(){
		try
		{
			setLisCotservicio(new LazyDataModel<Cotservicio>() {
				public List<Cotservicio> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Cotservicio> data = new ArrayList<Cotservicio>();
	
					CotservicioBO cotservicioBO = new CotservicioBO();
					int args[] = {0};
					data = cotservicioBO.lisCotservicioByPage(pageSize, first, args);
					this.setRowCount(args[0]);
	
			        return data;
				}
			});
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public Cotservicio getCotservicioItem() {
		return cotservicioItem;
	}

	public void setCotservicioItem(Cotservicio cotservicioItem) {
		this.cotservicioItem = cotservicioItem == null ? new Cotservicio(0, new Cotestado(), new Setusuario(), new Cottiposervicio(), null, null, null, null, new BigDecimal(0)) : cotservicioItem;
	}

	public LazyDataModel<Cotservicio> getLisCotservicio() {
		return lisCotservicio;
	}

	public void setLisCotservicio(LazyDataModel<Cotservicio> lisCotservicio) {
		this.lisCotservicio = lisCotservicio;
	}

	public void guardar(){
		try{
			if(validarCampos()){
				if(!itemRepetido()){
					CotservicioBO cotservicioBO = new CotservicioBO();
					boolean ok = false;
					
					if(cotservicioItem.getIdservicio() > 0){
						ok = cotservicioBO.updateCotservicio(cotservicioItem);
					}else{
						Cottiposervicio cottiposervicio = new Cottiposervicio();
						cottiposervicio.setIdtiposervicio(1);//servicio mortuorio
						cotservicioItem.setCottiposervicio(cottiposervicio);
						ok = cotservicioBO.newCotservicio(cotservicioItem);
					}
					
					cotservicioItem = new Cotservicio(0, new Cotestado(), new Setusuario(), new Cottiposervicio(), null, null, null, null, new BigDecimal(0));
					
					if(ok){
						new MessageUtil().showInfoMessage("Exito!", "Registro completo!");
					}
				}else{
					new MessageUtil().showWarnMessage("Ya Existe!", "Descripción duplicada. Corrija e intente nuevamente.");
				}
			}else{
				new MessageUtil().showWarnMessage("Datos incompletos!", "Datos incompletos!");
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void eliminar(){
		try{
			Cotestado cotestado = new Cotestado();
			cotestado.setIdestado(2);//inactivo
			cotservicioItem.setCotestado(cotestado);
			CotservicioBO cotservicioBO = new CotservicioBO();
			cotservicioBO.updateCotservicio(cotservicioItem);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(cotservicioItem.getNombre() == null || cotservicioItem.getNombre().trim().length() == 0){
			ok = false;
		}
		
		return ok;
	}
	
	private boolean itemRepetido(){
		boolean repetido = false;
		return repetido;
	}

	public void newItem()
	{
		cotservicioItem = new Cotservicio(0, new Cotestado(), new Setusuario(), new Cottiposervicio(), null, null, null, null, new BigDecimal(0));
	}
	
	public String cancelar(){
		return "home.jsf?faces-redirect=true&iditem=35";
	}

}
