package com.web.pet.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.PetordenservicioBO;
import com.web.pet.pojo.annotations.Cotestadopago;
import com.web.pet.pojo.annotations.Cotlugar;
import com.web.pet.pojo.annotations.PetordenservicioId;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class OrdenesServicioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3702266479784470178L;
	private LazyDataModel<Petordenservicio> lisPetordenservicio;
	private Petordenservicio petordenservicioSelected;
	private String nombre;
	private String textoBusqueda;
	
	public OrdenesServicioBean() {
		petordenservicioSelected = new Petordenservicio(new PetordenservicioId(), new Petmascotahomenaje(), new Setestado(), new Cotlugar(), new Setusuario(), null, null, null, null, null, null, null, new Cotestadopago(), new BigDecimal(0), new BigDecimal(0));
		
		nombre = "buscar por nombre de mascota";
		textoBusqueda="buscar por nombre de mascota";
		
		consultarOrdenes();
	}
	
	@SuppressWarnings("serial")
	private void consultarOrdenes(){
		try{
			lisPetordenservicio = new LazyDataModel<Petordenservicio>() {
				@Override
				public List<Petordenservicio> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
					List<Petordenservicio> lisPetordenservicio = new ArrayList<Petordenservicio>();
					PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
					int args[] = {0};
					
					try{
						String nombreParam = nombre.equals(textoBusqueda)?null:nombre;
						String[] nombres = null;
						if(nombreParam != null && nombreParam.trim().length() > 0){
							nombres = nombreParam.split(" ");
						}
						lisPetordenservicio = petordenservicioBO.lisPetordenservicioByPage(nombres, pageSize, first, args);
						if(lisPetordenservicio == null){
							lisPetordenservicio = new ArrayList<Petordenservicio>();
						}
					}catch(Exception re){
						re.printStackTrace();
						new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
					}
					
					this.setRowCount(args[0]);
					
					return lisPetordenservicio;
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

	public LazyDataModel<Petordenservicio> getLisPetordenservicio() {
		return lisPetordenservicio;
	}

	public void setLisPetordenservicio(LazyDataModel<Petordenservicio> lisPetordenservicio) {
		this.lisPetordenservicio = lisPetordenservicio;
	}

	public Petordenservicio getPetordenservicioSelected() {
		return petordenservicioSelected;
	}

	public void setPetordenservicioSelected(Petordenservicio petordenservicioSelected) {
		this.petordenservicioSelected = petordenservicioSelected;
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
			facesUtil.redirect("../admin/ordenservicio.jsf?faces-redirect=true&idordenservicio="+petordenservicioSelected.getId().getIdordenservicio()+"&idanio="+petordenservicioSelected.getId().getIdanio()+"&iditem=40");
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
