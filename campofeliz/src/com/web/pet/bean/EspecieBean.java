package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.PetespecieBO;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class EspecieBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2533593253855721306L;
	private Petespecie petespecieItem;
	private Petespecie petespecieItemClon;
	private LazyDataModel<Petespecie> lisPetespecie;
	
	public EspecieBean() {
		petespecieItem = new Petespecie(0, new Setestado(), new Setusuario(), null, null, null, null,null);
		petespecieItemClon = new Petespecie(0, new Setestado(), new Setusuario(), null, null, null, null,null);
		consultarEspecies();
		
	}
	
	@SuppressWarnings("serial")
	private void consultarEspecies(){
		try
		{
			lisPetespecie = new LazyDataModel<Petespecie>() {
				public List<Petespecie> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
					List<Petespecie> data = new ArrayList<Petespecie>();
	
					PetespecieBO petespecieBO = new PetespecieBO();
					int args[] = {0};
					data = petespecieBO.lisPetespecieByPage(pageSize, first, args);
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
	
	public void guardar(){
		try{
			if(validarCampos()){
				if(!itemRepetido()){
					PetespecieBO petespecieBO = new PetespecieBO();
					boolean ok = false;
					
					if(petespecieItem.getIdespecie() > 0){
						ok = petespecieBO.modificarPetespecie(petespecieItem,petespecieItemClon);
						if(ok){
							new MessageUtil().showInfoMessage("Especie actualizada con exito!!", "");
						}else{
							new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
						}
					}else{
						ok = petespecieBO.ingresarPetespecie(petespecieItem);
						if(ok){
							new MessageUtil().showInfoMessage("Especie ingresada con exito!!", "");
						}else{
							new MessageUtil().showInfoMessage("No se ha podido ingresar el Color. Comunicar al Webmaster.", "");
						}
					}
				}else{
					new MessageUtil().showWarnMessage("Ya Existe! Descripción duplicada. Corrija e intente nuevamente.","");
				}
			}
			
			petespecieItem = new Petespecie(0, new Setestado(), new Setusuario(), null, null, null, null, null);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void eliminar(){
		try{
			PetespecieBO petespecieBO = new PetespecieBO();
			petespecieBO.eliminarPetespecie(petespecieItem);
			petespecieItem = new Petespecie(0, new Setestado(), new Setusuario(), null, null, null, null, null);
			new MessageUtil().showInfoMessage("Especie eliminada con exito!!", "");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(petespecieItem.getNombre() == null || petespecieItem.getNombre().trim().length() == 0){
			ok = false;
			new MessageUtil().showErrorMessage("El nombre de la especie es obligatorio.","");
		}
		
		return ok;
	}
	
	private boolean itemRepetido(){
		boolean repetido = false;
		return repetido;
	}

	public void newItem()
	{
		petespecieItem = new Petespecie(0, new Setestado(), new Setusuario(), null, null, null, null, null);
	}
	
	public String cancelar(){
		return "admin/home.jsf?faces-redirect=true&iditem=35";
	}
	
	public void clonar() {
		try{
			petespecieItemClon = petespecieItem.clonar();
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public Petespecie getPetespecieItem() {
		return petespecieItem;
	}

	public void setPetespecieItem(Petespecie petespecieItem) {
		this.petespecieItem = petespecieItem == null ? new Petespecie(0, new Setestado(), new Setusuario(), null, null, null, null,null) : petespecieItem;
	}

	public LazyDataModel<Petespecie> getLisPetespecie() {
		return lisPetespecie;
	}

	public void setLisPetespecie(LazyDataModel<Petespecie> lisPetespecie) {
		this.lisPetespecie = lisPetespecie;
	}

}
