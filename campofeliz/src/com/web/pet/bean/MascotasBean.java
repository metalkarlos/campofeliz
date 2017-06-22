package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.PetespecieBO;
import com.web.pet.bo.PetmascotaBO;
//import com.web.pet.pojo.annotations.Mascotas;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class MascotasBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7416931331487760552L;
	private int especie;
	//private LazyDataModel<Mascotas> lisMascotas;
	private LazyDataModel<Petmascotahomenaje> lisPetmascotahomenaje;
	private int columnsGrid;
	private int rowsGrid;
	private List<Petespecie> lisPetespecie;
	private String nombre;
	private String textoBusqueda;

	public MascotasBean() {
		setColumnsGrid(4);
		setRowsGrid(4);
		
		nombre = "buscar por nombre de mascota";
		textoBusqueda="buscar por nombre de mascota";
		
		/*llenarListaEspecieMascota();
		
		if(lisPetespecie != null && lisPetespecie.size() > 0){
			especie = lisPetespecie.get(0).getIdespecie();
		}
		
		consultarMascotas();*/
	}
	
	@PostConstruct
	public void PostMascotasBean(){
		
		llenarListaEspecieMascota();
		
		if(lisPetespecie != null && lisPetespecie.size() > 0){
			especie = lisPetespecie.get(0).getIdespecie();
		}
		
		consultarMascotas();
		
		/*FacesUtil facesUtil = new FacesUtil();
		
		try{
			Object par = facesUtil.getParametroUrl("especie");
			if(par != null){
				especie = Integer.parseInt(par.toString());
			}else{
				facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");
			}
		} catch(NumberFormatException ne){
			try{facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");}catch(Throwable e){}
		} catch(Exception e) {
			e.printStackTrace();
			try{facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");}catch(Throwable e2){}
		}*/
	}
	
	private void llenarListaEspecieMascota(){
		try
		{
			lisPetespecie = new PetespecieBO().lisPetespecie();
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	@SuppressWarnings("serial")
	private void consultarMascotas(){
		try
		{
			lisPetmascotahomenaje = new LazyDataModel<Petmascotahomenaje>() {
				public List<Petmascotahomenaje> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
					List<Petmascotahomenaje> data = new ArrayList<Petmascotahomenaje>();
					PetmascotaBO petmascotaBO = new PetmascotaBO();
					int args[] = {0};
					
					String nombreParam = nombre.equals(textoBusqueda)?null:nombre;
					if(especie > 0 || nombreParam != null){
						data = petmascotaBO.lisPetmascotahomenajeEspecieByPage(especie, nombreParam, pageSize, first, args);
					}

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
			};
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void setEspecie(int especie) {
		this.especie = especie;
	}

	public int getEspecie() {
		return especie;
	}

	/*public LazyDataModel<Mascotas> getLisMascotas() {
		return lisMascotas;
	}

	public void setLisMascotas(LazyDataModel<Mascotas> lisMascotas) {
		this.lisMascotas = lisMascotas;
	}*/

	public int getColumnsGrid() {
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
	}

	public List<Petespecie> getLisPetespecie() {
		return lisPetespecie;
	}

	public void setLisPetespecie(List<Petespecie> lisPetespecie) {
		this.lisPetespecie = lisPetespecie;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTextoBusqueda() {
		return textoBusqueda;
	}

	public void setTextoBusqueda(String textoBusqueda) {
		this.textoBusqueda = textoBusqueda;
	}

	public LazyDataModel<Petmascotahomenaje> getLisPetmascotahomenaje() {
		return lisPetmascotahomenaje;
	}

	public void setLisPetmascotahomenaje(
			LazyDataModel<Petmascotahomenaje> lisPetmascotahomenaje) {
		this.lisPetmascotahomenaje = lisPetmascotahomenaje;
	}

}
