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
import com.web.pet.pojo.annotations.Cotestado;
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
	private LazyDataModel<Cotlugar> lisCotlugar;
	
	public LugarBean() {
		cotlugarItem =  new Cotlugar(0, new Cotestado(), new Setusuario(), null, null, null, null);
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
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public Cotlugar getCotlugarItem() {
		return cotlugarItem;
	}

	public void setCotlugarItem(Cotlugar cotlugarItem) {
		this.cotlugarItem = cotlugarItem == null ? new Cotlugar(0, new Cotestado(), new Setusuario(), null, null, null, null) : cotlugarItem;
	}

	public LazyDataModel<Cotlugar> getLisCotlugar() {
		return lisCotlugar;
	}

	public void setLisCotlugar(LazyDataModel<Cotlugar> lisCotlugar) {
		this.lisCotlugar = lisCotlugar;
	}

	public void guardar(){
		try{
			if(validarCampos()){
				if(!itemRepetido()){
					CotlugarBO cotlugarBO = new CotlugarBO();
					boolean ok = false;
					
					if(cotlugarItem.getIdlugar() > 0){
						ok = cotlugarBO.updateCotlugar(cotlugarItem);
					}else{
						ok = cotlugarBO.newCotlugar(cotlugarItem);
					}
					
					cotlugarItem = new Cotlugar(0, new Cotestado(), new Setusuario(), null, null, null, null);
					
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
			cotlugarItem.setCotestado(cotestado);
			CotlugarBO cotlugarBO = new CotlugarBO();
			cotlugarBO.updateCotlugar(cotlugarItem);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(cotlugarItem.getNombre() == null || cotlugarItem.getNombre().trim().length() == 0){
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
		cotlugarItem = new Cotlugar(0, new Cotestado(), new Setusuario(), null, null, null, null);
	}
	
	public String cancelar(){
		return "admin/home.jsf?faces-redirect=true&iditem=35";
	}

}
