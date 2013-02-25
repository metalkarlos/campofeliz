package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CottiposervicioBO;
import com.web.pet.pojo.annotations.Cotestado;
import com.web.pet.pojo.annotations.Cottiposervicio;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@Named
@ViewScoped
public class TipoServicioBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5923602529190991250L;
	private Cottiposervicio cottiposervicioItem;
	private LazyDataModel<Cottiposervicio> lisCottiposervicio;
	
	public TipoServicioBean() {
		cottiposervicioItem = new Cottiposervicio(0, new Cotestado(), new Setusuario(), null, null, null, null);
		consultarTipoServicio();
		
	}
	
	@SuppressWarnings("serial")
	private void consultarTipoServicio(){
		try
		{
			lisCottiposervicio = new LazyDataModel<Cottiposervicio>() {
				public List<Cottiposervicio> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Cottiposervicio> data = new ArrayList<Cottiposervicio>();
	
					CottiposervicioBO cottiposervicioBO = new CottiposervicioBO();
					int args[] = {0};
					data = cottiposervicioBO.lisCottiposervicioByPage(pageSize, first, args);
					this.setRowCount(args[0]);
	
			        return data;
				}
			};
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public Cottiposervicio getCottiposervicioItem() {
		return cottiposervicioItem;
	}

	public void setCottiposervicioItem(Cottiposervicio cottiposervicioItem) {
		this.cottiposervicioItem = cottiposervicioItem == null ? new Cottiposervicio(0, new Cotestado(), new Setusuario(), null, null, null, null) : cottiposervicioItem;
	}

	public LazyDataModel<Cottiposervicio> getLisCottiposervicio() {
		return lisCottiposervicio;
	}

	public void setLisCottiposervicio(LazyDataModel<Cottiposervicio> lisCottiposervicio) {
		this.lisCottiposervicio = lisCottiposervicio;
	}

	public void guardar(){
		try{
			if(validarCampos()){
				if(!itemRepetido()){
					CottiposervicioBO cottiposervicioBO = new CottiposervicioBO();
					boolean ok = false;
					
					if(cottiposervicioItem.getIdtiposervicio() > 0){
						try{
							ok = cottiposervicioBO.updateCottiposervicio(cottiposervicioItem);
						}catch(Exception re){
							new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
						}
					}else{
						try{
							ok = cottiposervicioBO.newCottiposervicio(cottiposervicioItem);
						}catch(Exception re){
							new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
						}
					}
					
					cottiposervicioItem = new Cottiposervicio(0, new Cotestado(), new Setusuario(), null, null, null, null);
					
					if(ok){
						new MessageUtil().showInfoMessage("Exito!", "Registro completo!");
					}
				}else{
					new MessageUtil().showErrorMessage("Ya Existe!", "Descripción duplicada. Corrija e intente nuevamente.");
				}
			}else{
				new MessageUtil().showErrorMessage("Error!", "Datos incompletos!");
			}
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void eliminar(){
		try{
			Cotestado cotestado = new Cotestado();
			cotestado.setIdestado(2);//inactivo
			cottiposervicioItem.setCotestado(cotestado);
			CottiposervicioBO cottiposervicioBO = new CottiposervicioBO();
			cottiposervicioBO.updateCottiposervicio(cottiposervicioItem);
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(cottiposervicioItem.getNombre() == null || cottiposervicioItem.getNombre().trim().length() == 0){
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
		cottiposervicioItem = new Cottiposervicio(0, new Cotestado(), new Setusuario(), null, null, null, null);
	}
	
	public String cancelar(){
		return "home.jsf?faces-redirect=true&iditem=35";
	}

}
