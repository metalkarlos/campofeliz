package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CotlugarBO;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cotlugar;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class LugarBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2914517347984298492L;
	private Cotlugar cotlugarItem;
	private Cotlugar cotlugarItemClon;
	private LazyDataModel<Cotlugar> lisCotlugar;
	
	public LugarBean() {
		cotlugarItem =  new Cotlugar(0, new Setestado(), new Setusuario(), null, null, null, null);
		cotlugarItemClon =  new Cotlugar(0, new Setestado(), new Setusuario(), null, null, null, null);
		consultarLugar();
	}
	
	@SuppressWarnings("serial")
	private void consultarLugar(){
		try
		{
			setLisCotlugar(new LazyDataModel<Cotlugar>() {
				public List<Cotlugar> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Cotlugar> data = new ArrayList<Cotlugar>();
	
					CotlugarBO cotlugarBO = new CotlugarBO();
					int args[] = {0};
					data = cotlugarBO.lisCotlugarByPage(pageSize, first, args);
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
			});
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void guardar(){
		try{
			if(validarCampos()){
				if(!itemRepetido()){
					CotlugarBO cotlugarBO = new CotlugarBO();
					boolean ok = false;
					
					if(cotlugarItem.getIdlugar() > 0){
						ok = cotlugarBO.modificarCotlugar(cotlugarItem,cotlugarItemClon);
						if(ok){
							new MessageUtil().showInfoMessage("Lugar actualizado con exito!!", "");
						}else{
							new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
						}
					}else{
						ok = cotlugarBO.ingresarCotlugar(cotlugarItem);
						if(ok){
							new MessageUtil().showInfoMessage("Lugar ingresado con exito!!", "");
						}else{
							new MessageUtil().showInfoMessage("No se ha podido ingresar el Color. Comunicar al Webmaster.", "");
						}
					}
				}else{
					new MessageUtil().showWarnMessage("Ya Existe! Descripción duplicada. Corrija e intente nuevamente.","");
				}
			}
			
			cotlugarItem = new Cotlugar(0, new Setestado(), new Setusuario(), null, null, null, null);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void eliminar(){
		try{
			CotlugarBO cotlugarBO = new CotlugarBO();
			cotlugarBO.eliminarCotlugar(cotlugarItem);
			cotlugarItem = new Cotlugar(0, new Setestado(), new Setusuario(), null, null, null, null);
			new MessageUtil().showInfoMessage("Lugar eliminado con exito!!", "");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(cotlugarItem.getNombre() == null || cotlugarItem.getNombre().trim().length() == 0){
			ok = false;
			new MessageUtil().showErrorMessage("El nombre del lugar es obligatorio.","");
		}
		
		return ok;
	}
	
	private boolean itemRepetido(){
		boolean repetido = false;
		return repetido;
	}

	public void newItem()
	{
		cotlugarItem = new Cotlugar(0, new Setestado(), new Setusuario(), null, null, null, null);
	}
	
	public String cancelar(){
		return "admin/home.jsf?faces-redirect=true&iditem=35";
	}
	
	public void clonar() {
		try{
			cotlugarItemClon = cotlugarItem.clonar();
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public Cotlugar getCotlugarItem() {
		return cotlugarItem;
	}

	public void setCotlugarItem(Cotlugar cotlugarItem) {
		this.cotlugarItem = cotlugarItem == null ? new Cotlugar(0, new Setestado(), new Setusuario(), null, null, null, null) : cotlugarItem;
	}

	public LazyDataModel<Cotlugar> getLisCotlugar() {
		return lisCotlugar;
	}

	public void setLisCotlugar(LazyDataModel<Cotlugar> lisCotlugar) {
		this.lisCotlugar = lisCotlugar;
	}

}
