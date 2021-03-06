package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CottiposervicioBO;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cottiposervicio;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class TipoServicioBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5923602529190991250L;
	private Cottiposervicio cottiposervicioItem;
	private LazyDataModel<Cottiposervicio> lisCottiposervicio;
	
	public TipoServicioBean() {
		cottiposervicioItem = new Cottiposervicio(0, new Setestado(), new Setusuario(), null, null, null, null);
		consultarTipoServicio();
		
	}
	
	@SuppressWarnings("serial")
	private void consultarTipoServicio(){
		try
		{
			lisCottiposervicio = new LazyDataModel<Cottiposervicio>() {
				public List<Cottiposervicio> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
					List<Cottiposervicio> data = new ArrayList<Cottiposervicio>();
	
					CottiposervicioBO cottiposervicioBO = new CottiposervicioBO();
					int args[] = {0};
					data = cottiposervicioBO.lisCottiposervicioByPage(pageSize, first, args);
					this.setRowCount(args[0]);
	
			        return data;
				}
				
				@Override
               public void setRowIndex(int rowIndex) {
                   /*
                    * The following is in ancestor (LazyDataModel):
                    * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
                    */
                   if (rowIndex == -1 || getPageSize() == 0) {
                       super.setRowIndex(-1);
                   }
                   else {
                       super.setRowIndex(rowIndex % getPageSize());
                   }      
               }
				
				@Override
				public Object getRowKey(Cottiposervicio object) {
					return object != null ? object.getIdtiposervicio() : null;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public Cottiposervicio getRowData(String rowKey) {
					List<Cottiposervicio> cottiposervicios = (List<Cottiposervicio>) getWrappedData();
				    int idtiposervicio = Integer.valueOf(rowKey);

				    for (Cottiposervicio cottiposervicio : cottiposervicios) {
				        if (cottiposervicio.getIdtiposervicio() == idtiposervicio) {
				            return cottiposervicio;
				        }
				    }

				    return null;
				}
			};
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public Cottiposervicio getCottiposervicioItem() {
		return cottiposervicioItem;
	}

	public void setCottiposervicioItem(Cottiposervicio cottiposervicioItem) {
		this.cottiposervicioItem = cottiposervicioItem == null ? new Cottiposervicio(0, new Setestado(), new Setusuario(), null, null, null, null) : cottiposervicioItem;
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
						ok = cottiposervicioBO.updateCottiposervicio(cottiposervicioItem);
					}else{
						ok = cottiposervicioBO.newCottiposervicio(cottiposervicioItem);
					}
					
					cottiposervicioItem = new Cottiposervicio(0, new Setestado(), new Setusuario(), null, null, null, null);
					
					if(ok){
						new MessageUtil().showInfoMessage("Exito! Registro completo!","");
					}
				}else{
					new MessageUtil().showWarnMessage("Ya Existe! Descripción duplicada. Corrija e intente nuevamente.","");
				}
			}else{
				new MessageUtil().showWarnMessage("Datos incompletos! Datos incompletos!","");
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void eliminar(){
		try{
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			cottiposervicioItem.setSetestado(setestado);
			CottiposervicioBO cottiposervicioBO = new CottiposervicioBO();
			cottiposervicioBO.updateCottiposervicio(cottiposervicioItem);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
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
		cottiposervicioItem = new Cottiposervicio(0, new Setestado(), new Setusuario(), null, null, null, null);
	}
	
	public String cancelar(){
		return "admin/home.jsf?faces-redirect=true&iditem=35";
	}

}
