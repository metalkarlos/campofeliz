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
	private LazyDataModel<Petespecie> lisPetespecie;
	
	public EspecieBean() {
		petespecieItem = new Petespecie(0, new Setestado(), new Setusuario(), null, null, null, null,null);
		consultarEspecies();
		
	}
	
	@SuppressWarnings("serial")
	private void consultarEspecies(){
		try
		{
			lisPetespecie = new LazyDataModel<Petespecie>() {
				public List<Petespecie> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
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
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
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

	public void guardar(){
		try{
			if(validarCampos()){
				if(!itemRepetido()){
					PetespecieBO petespecieBO = new PetespecieBO();
					boolean ok = false;
					
					if(petespecieItem.getIdespecie() > 0){
						ok = petespecieBO.updatePetespecie(petespecieItem);
					}else{
						ok = petespecieBO.newPetespecie(petespecieItem);
					}
					
					petespecieItem = new Petespecie(0, new Setestado(), new Setusuario(), null, null, null, null, null);
					
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
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			petespecieItem.setSetestado(setestado);
			PetespecieBO petespecieBO = new PetespecieBO();
			petespecieBO.updatePetespecie(petespecieItem);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(petespecieItem.getNombre() == null || petespecieItem.getNombre().trim().length() == 0){
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
		petespecieItem = new Petespecie(0, new Setestado(), new Setusuario(), null, null, null, null, null);
	}
	
	public String cancelar(){
		return "admin/home.jsf?faces-redirect=true&iditem=35";
	}

}
