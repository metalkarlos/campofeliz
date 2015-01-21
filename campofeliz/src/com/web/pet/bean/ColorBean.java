package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CotcolorBO;
import com.web.pet.pojo.annotations.Cotcolor;
import com.web.pet.pojo.annotations.Cotestado;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class ColorBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4533900181456343345L;
	private Cotcolor cotcolorItem;
	private LazyDataModel<Cotcolor> lisCotcolor;
	
	public ColorBean() {
		cotcolorItem = new Cotcolor(0, new Cotestado(), new Setusuario(), null, null, null, null, null);
		consultarColores();
		
	}
	
	@SuppressWarnings("serial")
	private void consultarColores(){
		try
		{
			lisCotcolor = new LazyDataModel<Cotcolor>() {
				public List<Cotcolor> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Cotcolor> data = new ArrayList<Cotcolor>();
	
					CotcolorBO cotcolorBO = new CotcolorBO();
					int args[] = {0};
					data = cotcolorBO.lisCotcolorByPage(pageSize, first, args);
					this.setRowCount(args[0]);
	
			        return data;
				}
			};
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public Cotcolor getCotcolorItem() {
		return cotcolorItem;
	}

	public void setCotcolorItem(Cotcolor cotcolorItem) {
		this.cotcolorItem = cotcolorItem == null ? new Cotcolor(0, new Cotestado(), new Setusuario(), null, null, null, null, null) : cotcolorItem;
	}

	public LazyDataModel<Cotcolor> getLisCotcolor() {
		return lisCotcolor;
	}

	public void setLisCotcolor(LazyDataModel<Cotcolor> lisCotcolor) {
		this.lisCotcolor = lisCotcolor;
	}

	public void guardar(){
		try{
			if(validarCampos()){
				if(!itemRepetido()){
					CotcolorBO cotcolorBO = new CotcolorBO();
					boolean ok = false;
					
					if(cotcolorItem.getIdcolor() > 0){
						try{
							ok = cotcolorBO.updateCotcolor(cotcolorItem);
						}catch(Exception re){
							new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
						}
					}else{
						try{
							ok = cotcolorBO.newCotcolor(cotcolorItem);
						}catch(Exception re){
							new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
						}
					}
					
					cotcolorItem = new Cotcolor(0, new Cotestado(), new Setusuario(), null, null, null, null, null);
					
					if(ok){
						new MessageUtil().showInfoMessage("Exito!", "Registro completo!");
					}
				}else{
					new MessageUtil().showErrorMessage("Ya Existe!", "Descripción duplicada. Corrija e intente nuevamente.");
				}
			}else{
				new MessageUtil().showErrorMessage("Error!", "Datos incompletos!");
			}
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void eliminar(){
		try{
			Cotestado cotestado = new Cotestado();
			cotestado.setIdestado(2);//inactivo
			cotcolorItem.setCotestado(cotestado);
			CotcolorBO cotcolorBO = new CotcolorBO();
			cotcolorBO.updateCotcolor(cotcolorItem);
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(cotcolorItem.getNombre() == null || cotcolorItem.getNombre().trim().length() == 0){
			ok = false;
		}else{
			if(cotcolorItem.getHex() == null || cotcolorItem.getHex().trim().length() == 0){
				ok = false;
			}
		}
		
		return ok;
	}
	
	private boolean itemRepetido(){
		boolean repetido = false;
		
		/*Iterator lisCotcolorTmp = lisCotcolor.iterator();
		while(lisCotcolorTmp.hasNext()){
			Cotcolor cotColor = (Cotcolor)lisCotcolorTmp.next();
			if(cotColor.getIdcolor() != cotcolorItem.getIdcolor() && 
			(cotColor.getNombre().toLowerCase().equals(cotcolorItem.getNombre().toLowerCase()) || cotColor.getHex().toLowerCase().equals(cotcolorItem.getHex().toLowerCase()))){
				repetido = true;
				break;
			}
		}
		int max = lisCotcolor.getRowCount();
		for(int i = 0; i < max; i++){
			Cotcolor cotColor = (Cotcolor)lisCotcolor.getRowData(rowKey)getRowData(rowKey)next();
			if(cotColor.getIdcolor() != cotcolorItem.getIdcolor() && 
			(cotColor.getNombre().toLowerCase().equals(cotcolorItem.getNombre().toLowerCase()) || cotColor.getHex().toLowerCase().equals(cotcolorItem.getHex().toLowerCase()))){
				repetido = true;
				break;
			}
		}*/
		
		return repetido;
	}

	public void newItem()
	{
		cotcolorItem = new Cotcolor(0, new Cotestado(), new Setusuario(), null, null, null, null, null);
	}
	
	public String cancelar(){
		return "home.jsf?faces-redirect=true&iditem=35";
	}

}
