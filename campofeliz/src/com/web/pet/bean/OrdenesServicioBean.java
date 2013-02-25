package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.PetordenservicioBO;
import com.web.pet.pojo.annotations.Cotlugar;
import com.web.pet.pojo.annotations.Petestado;
import com.web.pet.pojo.annotations.Petmascota;
import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;

@ManagedBean
@Named
@ViewScoped
public class OrdenesServicioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3702266479784470178L;
	private LazyDataModel<Petordenservicio> lisPetordenservicio;
	private Petordenservicio petordenservicioSelected;
	private String nombre;
	
	public OrdenesServicioBean() {
		petordenservicioSelected = new Petordenservicio(0, new Petmascota(), new Petestado(), new Cotlugar(), new Setusuario(), null, null, null, null, null, null);
		consultarOrdenes();
	}
	
	@SuppressWarnings("serial")
	private void consultarOrdenes(){
		try{
			lisPetordenservicio = new LazyDataModel<Petordenservicio>() {
				@Override
				public List<Petordenservicio> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Petordenservicio> lisPetordenservicio = new ArrayList<Petordenservicio>();
					PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
					int args[] = {0};
					String[] nombres = null;
					if(nombre != null && nombre.trim().length() > 0){
						nombres = nombre.split(" ");
					}
					lisPetordenservicio = petordenservicioBO.lisPetordenservicioByPage(nombres, pageSize, first, args);
					this.setRowCount(args[0]);
					
					return lisPetordenservicio;
				}
			};
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
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
		FacesUtil facesUtil = new FacesUtil();
		facesUtil.redirect("ordenservicio.jsf?faces-redirect=true&idordenservicio="+petordenservicioSelected.getIdordenservicio()+"&iditem=40");
	}

}
