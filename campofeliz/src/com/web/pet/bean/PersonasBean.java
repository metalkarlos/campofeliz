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
import com.web.pet.pojo.annotations.Cotestado;
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
	
	public PersonasBean() {
		cotpersonaSelected = new Cotpersona(0, new Cottipoidentificacion(), new Cotestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		//setColumnsGrid(4);
		//setRowsGrid(4);
		consultarPersonas();
	}
	
	@SuppressWarnings("serial")
	private void consultarPersonas(){
		try{
			lisCotpersona = new LazyDataModel<Cotpersona>() {
				@Override
				public List<Cotpersona> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Cotpersona> lisCotpersona = new ArrayList<Cotpersona>();
					CotpersonaBO cotpersonaBO = new CotpersonaBO();
					int args[] = {0};
					String[] nombres = null;
					if(nombre != null && nombre.trim().length() > 0){
						nombres = nombre.split(" ");
					}
					lisCotpersona = cotpersonaBO.lisCotpersonaByPage(nombres, pageSize, first, args);
					this.setRowCount(args[0]);
					
					return lisCotpersona;
				}
			};
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
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
		this.cotpersonaSelected = cotpersonaSelected == null ? new Cotpersona(0, new Cottipoidentificacion(), new Cotestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null) : cotpersonaSelected;
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
			facesUtil.redirect("../pages/persona.jsf?faces-redirect=true&idpersona="+cotpersonaSelected.getIdpersona()+"&iditem=36");
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
}
