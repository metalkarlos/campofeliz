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
import com.web.pet.bo.PetrazaBO;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class RazaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2391829863647678000L;
	private Petraza petrazaItem;
	private Petraza petrazaItemClon;
	private List<Petespecie> lisPetespecie;
	private LazyDataModel<Petraza> lisPetraza;
	private int idespecie;
	private String nombre;
	private String textoBusqueda;
	
	public RazaBean() {
		petrazaItem = new Petraza(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, new Petespecie());
		petrazaItemClon = new Petraza(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, new Petespecie());
		
		nombre = "buscar por nombre de especie";
		textoBusqueda = "buscar por nombre de especie";
	}
	
	@PostConstruct
	public void PostMascotasBean(){
		
		llenarEspecieMascota();
		
		if(lisPetespecie != null && lisPetespecie.size() > 0){
			idespecie = lisPetespecie.get(0).getIdespecie();
		}
		
		consultarRazas();
	}
	
	@SuppressWarnings("serial")
	private void consultarRazas(){
		try
		{
			lisPetraza = new LazyDataModel<Petraza>() {
				public List<Petraza> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
					List<Petraza> data = new ArrayList<Petraza>();
	
					PetrazaBO petrazaBO = new PetrazaBO();
					int args[] = {0};
					
					String nombreParam = nombre.equals(textoBusqueda)?null:nombre;
					if(idespecie > 0 || nombreParam != null){
						data = petrazaBO.lisPetrazaPorEspeciePagineo(idespecie, nombreParam, pageSize, first, args);
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
				
				@Override
				public Object getRowKey(Petraza object) {
					return object != null ? object.getIdraza() : null;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public Petraza getRowData(String rowKey) {
					List<Petraza> petrazas = (List<Petraza>) getWrappedData();
				    int idraza = Integer.valueOf(rowKey);

				    for (Petraza petraza : petrazas) {
				        if (petraza.getIdraza() == idraza) {
				            return petraza;
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
	
	private void llenarEspecieMascota(){
		try
		{
			lisPetespecie = new PetespecieBO().lisPetespecie();
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void guardar(){
		try{
			if(validarCampos()){
				if(!itemRepetido()){
					PetrazaBO petrazaBO = new PetrazaBO();
					boolean ok = false;
					
					if(petrazaItem.getIdraza() > 0){
						ok = petrazaBO.modificarPetraza(petrazaItem,petrazaItemClon);
						if(ok){
							new MessageUtil().showInfoMessage("Raza actualizada con exito!!", "");
						}else{
							new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
						}
					}else{
						ok = petrazaBO.ingresarPetraza(petrazaItem);
						if(ok){
							new MessageUtil().showInfoMessage("Raza ingresada con exito!!", "");
						}else{
							new MessageUtil().showInfoMessage("No se ha podido ingresar la Raza. Comunicar al Webmaster.", "");
						}
					}
				}else{
					new MessageUtil().showWarnMessage("Ya Existe! Descripción duplicada. Corrija e intente nuevamente.","");
				}
			}
			
			petrazaItem = new Petraza(0, new Setestado(), new Setusuario(), null, null, null, null, null, null,new Petespecie());
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void eliminar(){
		try{
			PetrazaBO petrazaBO = new PetrazaBO();
			petrazaBO.eliminarPetraza(petrazaItem);
			petrazaItem = new Petraza(0, new Setestado(), new Setusuario(), null, null, null, null, null, null,new Petespecie());
			new MessageUtil().showInfoMessage("Raza eliminada con exito!!", "");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(petrazaItem.getPetespecie() == null || petrazaItem.getPetespecie().getIdespecie() == 0 ){
			ok = false;
			new MessageUtil().showErrorMessage("La Especie es obligatoria.","");
		}else{
			if(petrazaItem.getNombre() == null || petrazaItem.getNombre().trim().length() == 0){
				ok = false;
				new MessageUtil().showErrorMessage("El nombre de la raza es obligatorio.","");
			}
		}
		
		return ok;
	}
	
	private boolean itemRepetido(){
		boolean repetido = false;
		return repetido;
	}

	public void newItem()
	{
		petrazaItem = new Petraza(0, new Setestado(), new Setusuario(), null, null, null, null, null, null,new Petespecie());
	}
	
	public String cancelar(){
		return "admin/home.jsf?faces-redirect=true&iditem=35";
	}
	
	public void clonar() {
		try{
			petrazaItemClon = petrazaItem.clonar();
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public Petraza getPetrazaItem() {
		return petrazaItem;
	}

	public void setPetrazaItem(Petraza petrazaItem) {
		this.petrazaItem = petrazaItem == null ? new Petraza(0, new Setestado(), new Setusuario(), null, null, null, null, null, null,new Petespecie()) : petrazaItem;
	}

	public LazyDataModel<Petraza> getLisPetraza() {
		return lisPetraza;
	}

	public void setLisPetraza(LazyDataModel<Petraza> lisPetraza) {
		this.lisPetraza = lisPetraza;
	}

	public List<Petespecie> getLisPetespecie() {
		return lisPetespecie;
	}

	public void setLisPetespecie(List<Petespecie> lisPetespecie) {
		this.lisPetespecie = lisPetespecie;
	}

	public int getIdespecie() {
		return idespecie;
	}

	public void setIdespecie(int idespecie) {
		this.idespecie = idespecie;
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

}
