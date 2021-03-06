package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CotpersonaBO;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class PersonasBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8994385598420000349L;
	private LazyDataModel<Cotpersona> lisCotpersona;
	/*private int columnsGrid;
	private int rowsGrid;*/
	private Cotpersona cotpersonaSelected;
	private String nombre;
	private String textoBusqueda;
	
	public PersonasBean() {
		cotpersonaSelected = new Cotpersona(0, new Cottipoidentificacion(), new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		
		nombre = "buscar por nombre de persona";
		textoBusqueda="buscar por nombre de persona";
		
		consultarPersonas();
	}
	
	@SuppressWarnings("serial")
	private void consultarPersonas(){
		try{
			lisCotpersona = new LazyDataModel<Cotpersona>() {
				@Override
				public List<Cotpersona> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
					List<Cotpersona> lisCotpersona = new ArrayList<Cotpersona>();
					CotpersonaBO cotpersonaBO = new CotpersonaBO();
					int args[] = {0};
					
					String nombreParam = nombre.equals(textoBusqueda)?null:nombre;
					String[] nombres = null;
					if(nombreParam != null && nombreParam.trim().length() > 0){
						nombres = nombreParam.split(" ");
					}
					lisCotpersona = cotpersonaBO.lisCotpersonaByPage(nombres, pageSize, first, args);
					this.setRowCount(args[0]);
					
					return lisCotpersona;
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
				public Object getRowKey(Cotpersona object) {
					return object != null ? object.getIdpersona() : null;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public Cotpersona getRowData(String rowKey) {
					List<Cotpersona> cotpersonas = (List<Cotpersona>) getWrappedData();
				    int idpersona = Integer.valueOf(rowKey);

				    for (Cotpersona cotpersona : cotpersonas) {
				        if (cotpersona.getIdpersona() == idpersona) {
				            return cotpersona;
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
	
	
	public LazyDataModel<Cotpersona> getLisCotpersona() {
		return lisCotpersona;
	}
	public void setLisCotpersona(LazyDataModel<Cotpersona> lisCotpersona) {
		this.lisCotpersona = lisCotpersona;
	}

	/*public int getColumnsGrid() {
		return columnsGrid;
	}

	public void setColumnsGrid(int columnsGrid) {
		this.columnsGrid = columnsGrid;
	}

	public int getRowsGrid() {
		return rowsGrid * columnsGrid;
	}

	public void setRowsGrid(int rowsGrid) {
		this.rowsGrid = rowsGrid;
	}*/

	public Cotpersona getCotpersonaSelected() {
		return cotpersonaSelected;
	}

	public void setCotpersonaSelected(Cotpersona cotpersonaSelected) {
		this.cotpersonaSelected = cotpersonaSelected == null ? new Cotpersona(0, new Cottipoidentificacion(), new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null) : cotpersonaSelected;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void onRowSelect(SelectEvent event){
		try{
			FacesUtil facesUtil = new FacesUtil();
			facesUtil.redirect("../admin/persona.jsf?faces-redirect=true&idpersona="+cotpersonaSelected.getIdpersona()+"&iditem=36");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

	public String getTextoBusqueda() {
		return textoBusqueda;
	}

	public void setTextoBusqueda(String textoBusqueda) {
		this.textoBusqueda = textoBusqueda;
	}
}
