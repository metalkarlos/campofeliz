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
import com.web.pet.pojo.annotations.Setestado;
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
	private Cotcolor cotcolorItemClon;
	private LazyDataModel<Cotcolor> lisCotcolor;
	
	public ColorBean() {
		cotcolorItem = new Cotcolor(0, new Setestado(), new Setusuario(), null, null, null, null, null);
		cotcolorItemClon = new Cotcolor(0, new Setestado(), new Setusuario(), null, null, null, null, null);
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
					CotcolorBO cotcolorBO = new CotcolorBO();
					boolean ok = false;
					
					if(cotcolorItem.getIdcolor() > 0){
						ok = cotcolorBO.modificarCotcolor(cotcolorItem,cotcolorItemClon);
						if(ok){
							new MessageUtil().showInfoMessage("Color actualizado con exito!!", "");
						}else{
							new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
						}
					}else{
						ok = cotcolorBO.ingresarCotcolor(cotcolorItem);
						if(ok){
							new MessageUtil().showInfoMessage("Color ingresado con exito!!", "");
						}else{
							new MessageUtil().showInfoMessage("No se ha podido ingresar el Color. Comunicar al Webmaster.", "");
						}
					}
				}else{
					new MessageUtil().showWarnMessage("Descripcion Ya Existe!. Corrija e intente nuevamente.","");
				}
			}
			
			cotcolorItem = new Cotcolor(0, new Setestado(), new Setusuario(), null, null, null, null, null);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void eliminar(){
		try{
			CotcolorBO cotcolorBO = new CotcolorBO();
			cotcolorBO.eliminarCotcolor(cotcolorItem);
			cotcolorItem = new Cotcolor(0, new Setestado(), new Setusuario(), null, null, null, null, null);
			new MessageUtil().showInfoMessage("Color eliminado con exito!!", "");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(cotcolorItem.getNombre() == null || cotcolorItem.getNombre().trim().length() == 0){
			ok = false;
			new MessageUtil().showErrorMessage("El nombre del color es obligatorio.","");
		}else{
			if(cotcolorItem.getHex() == null || cotcolorItem.getHex().trim().length() == 0){
				ok = false;
				new MessageUtil().showErrorMessage("El color es obligatorio.","");
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
		cotcolorItem = new Cotcolor(0, new Setestado(), new Setusuario(), null, null, null, null, null);
	}
	
	public String cancelar(){
		return "admin/home.jsf?faces-redirect=true&iditem=35";
	}
	
	public void clonar() {
		try{
			cotcolorItemClon = cotcolorItem.clonar();
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public Cotcolor getCotcolorItem() {
		return cotcolorItem;
	}

	public void setCotcolorItem(Cotcolor cotcolorItem) {
		this.cotcolorItem = cotcolorItem == null ? new Cotcolor(0, new Setestado(), new Setusuario(), null, null, null, null, null) : cotcolorItem;
	}

	public LazyDataModel<Cotcolor> getLisCotcolor() {
		return lisCotcolor;
	}

	public void setLisCotcolor(LazyDataModel<Cotcolor> lisCotcolor) {
		this.lisCotcolor = lisCotcolor;
	}

}
