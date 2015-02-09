package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.PetrazaBO;
import com.web.pet.pojo.annotations.Petestado;
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
	private LazyDataModel<Petraza> lisPetraza;
	
	public RazaBean() {
		petrazaItem = new Petraza(0, new Petestado(), new Setusuario(), null, null, null, null, null, null);
		consultarRazas();
		
	}
	
	@SuppressWarnings("serial")
	private void consultarRazas(){
		try
		{
			lisPetraza = new LazyDataModel<Petraza>() {
				public List<Petraza> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Petraza> data = new ArrayList<Petraza>();
	
					PetrazaBO petrazaBO = new PetrazaBO();
					int args[] = {0};
					data = petrazaBO.lisPetrazaByPage(pageSize, first, args);
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
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public Petraza getPetrazaItem() {
		return petrazaItem;
	}

	public void setPetrazaItem(Petraza petrazaItem) {
		this.petrazaItem = petrazaItem == null ? new Petraza(0, new Petestado(), new Setusuario(), null, null, null, null, null, null) : petrazaItem;
	}

	public LazyDataModel<Petraza> getLisPetraza() {
		return lisPetraza;
	}

	public void setLisPetraza(LazyDataModel<Petraza> lisPetraza) {
		this.lisPetraza = lisPetraza;
	}

	public void guardar(){
		try{
			if(validarCampos()){
				if(!itemRepetido()){
					PetrazaBO petrazaBO = new PetrazaBO();
					boolean ok = false;
					
					if(petrazaItem.getIdraza() > 0){
						ok = petrazaBO.updatePetraza(petrazaItem);
					}else{
						ok = petrazaBO.newPetraza(petrazaItem);
					}
					
					petrazaItem = new Petraza(0, new Petestado(), new Setusuario(), null, null, null, null, null, null);
					
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
			Petestado petestado = new Petestado();
			petestado.setIdestado(2);//inactivo
			petrazaItem.setPetestado(petestado);
			PetrazaBO petrazaBO = new PetrazaBO();
			petrazaBO.updatePetraza(petrazaItem);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(petrazaItem.getNombre() == null || petrazaItem.getNombre().trim().length() == 0){
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
		petrazaItem = new Petraza(0, new Petestado(), new Setusuario(), null, null, null, null, null, null);
	}
	
	public String cancelar(){
		return "admin/home.jsf?faces-redirect=true&iditem=35";
	}

}
