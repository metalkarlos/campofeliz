package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CottipolugarBO;
import com.web.pet.pojo.annotations.Cottipolugar;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class TipoLugarBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3128539431760855510L;
	private Cottipolugar cottipolugarItem;
	private Cottipolugar cottipolugarItemClon;
	private LazyDataModel<Cottipolugar> lisCottipolugar;
	
	public TipoLugarBean() {
		cottipolugarItem = new Cottipolugar(0, null, null, null, new Setestado(), new Setusuario());
		cottipolugarItemClon = new Cottipolugar(0, null, null, null, new Setestado(), new Setusuario());
		consultarTiposDeLugar();
	}
	
	@SuppressWarnings("serial")
	private void consultarTiposDeLugar(){
		try
		{
			lisCottipolugar = new LazyDataModel<Cottipolugar>() {
				public List<Cottipolugar> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
					List<Cottipolugar> data = new ArrayList<Cottipolugar>();
	
					CottipolugarBO cottipolugarBO = new CottipolugarBO();
					int args[] = {0};
					data = cottipolugarBO.lisCottipolugarByPage(pageSize, first, args);
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
				public Object getRowKey(Cottipolugar object) {
					return object != null ? object.getIdtipolugar() : null;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public Cottipolugar getRowData(String rowKey) {
					List<Cottipolugar> cottipolugars = (List<Cottipolugar>) getWrappedData();
				    int idtipolugar = Integer.valueOf(rowKey);

				    for (Cottipolugar cottipolugar : cottipolugars) {
				        if (cottipolugar.getIdtipolugar() == idtipolugar) {
				            return cottipolugar;
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
	
	public void newItem()
	{
		cottipolugarItem = new Cottipolugar(0, null, null, null, new Setestado(), new Setusuario());
	}
	
	public void clonar() {
		try{
			cottipolugarItemClon = cottipolugarItem.clonar();
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void guardar(){
		try{
			if(validarCampos()){
				if(!isItemRepetido()){
					CottipolugarBO cottipolugarBO = new CottipolugarBO();
					boolean ok = false;
					
					if(cottipolugarItem.getIdtipolugar() > 0){
						ok = cottipolugarBO.modificarCottipolugar(cottipolugarItem, cottipolugarItemClon);
						if(ok){
							new MessageUtil().showInfoMessage("Tipo de Lugar actualizado con exito!!", "");
						}else{
							new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
						}
					}else{
						ok = cottipolugarBO.ingresarCottipolugar(cottipolugarItem);
						if(ok){
							new MessageUtil().showInfoMessage("Tipo de Lugar ingresado con exito!!", "");
						}else{
							new MessageUtil().showInfoMessage("No se ha podido ingresar el Tipo de Lugar. Comunicar al Webmaster.", "");
						}
					}
				}else{
					new MessageUtil().showWarnMessage("Tipo de Lugar ya Existe. Corrija e intente nuevamente.","");
				}
			}
			
			cottipolugarItem = new Cottipolugar(0, null, null, null, new Setestado(), new Setusuario());
			cottipolugarItemClon = new Cottipolugar(0, null, null, null, new Setestado(), new Setusuario());
			
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(cottipolugarItem.getNombre() == null || cottipolugarItem.getNombre().trim().length() == 0){
			ok = false;
			new MessageUtil().showErrorMessage("El nombre del tipo de lugar es obligatorio.","");
		}
		
		return ok;
	}
	
	private boolean isItemRepetido(){
		boolean repetido = false;
		
		
		
		return repetido;
	}
	
	public void eliminar(){
		try{
			CottipolugarBO cottipolugarBO = new CottipolugarBO();
			cottipolugarBO.eliminarCottipolugar(cottipolugarItem);
			cottipolugarItem = new Cottipolugar(0, null, null, null, new Setestado(), new Setusuario());
			cottipolugarItemClon = new Cottipolugar(0, null, null, null, new Setestado(), new Setusuario());
			new MessageUtil().showInfoMessage("Tipo de Lugar eliminado con exito!!", "");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

	public Cottipolugar getCottipolugarItem() {
		return cottipolugarItem;
	}

	public void setCottipolugarItem(Cottipolugar cottipolugarItem) {
		this.cottipolugarItem = cottipolugarItem == null ? new Cottipolugar(0, null, null, null, new Setestado(), new Setusuario()) : cottipolugarItem;
	}

	public LazyDataModel<Cottipolugar> getLisCottipolugar() {
		return lisCottipolugar;
	}

	public void setLisCottipolugar(LazyDataModel<Cottipolugar> lisCottipolugar) {
		this.lisCottipolugar = lisCottipolugar;
	}
	
	

}
