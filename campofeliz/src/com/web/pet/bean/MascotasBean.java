package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.PetmascotaBO;
import com.web.pet.bo.PetespecieBO;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Mascotas;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.util.MessageUtil;

@ManagedBean
@Named
@ViewScoped
public class MascotasBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7416931331487760552L;
	private int especie;
	private String tipoNombre;
	private String fileSeparator;
	private String blankImage;
	private String mascotasPath;
	private LazyDataModel<Mascotas> lisMascotas;
	private int columnsGrid;
	private int rowsGrid;
	private List<Petespecie> lisPetespecie;
	private String nombre;
	private String deploymentsPath = Parametro.DEPLOYMENTS_PATH;

	public MascotasBean() {
		setFileSeparator(Parametro.FILE_SEPARATOR);
		setBlankImage(Parametro.BLANK_IMAGE_PATH);
		setMascotasPath(Parametro.MASCOTAS_PATH);
		setColumnsGrid(4);
		setRowsGrid(4);
		
		inicializarEspecieMascota();
		
		if(especie <= 0 && lisPetespecie != null && lisPetespecie.size() > 0){
			especie = lisPetespecie.get(0).getIdespecie();
		}
		
		consultarMascotas();
	}
	
	private void inicializarEspecieMascota(){
		try
		{
			lisPetespecie = new PetespecieBO().lisPetespecie();
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	@SuppressWarnings("serial")
	private void consultarMascotas(){
		try
		{
			lisMascotas = new LazyDataModel<Mascotas>() {
				public List<Mascotas> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Mascotas> data = new ArrayList<Mascotas>();
					PetmascotaBO petmascotaBO = new PetmascotaBO();
					int args[] = {0};
					
					if(especie > 0){
						data = petmascotaBO.lisMascotasByEspecieByPage(especie, nombre, pageSize, first, args);
					}else{
						new MessageUtil().showFatalMessage("Oops!", "Tipo de mascota no definido, verifique e intente nuevamente!");
					}
					this.setRowCount(args[0]);
	
			        return data;
				}
			};
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void setEspecie(int especie) {
		this.especie = especie;
	}

	public int getEspecie() {
		return especie;
	}

	public String getTipoNombre() {
		switch(especie) {
		case 1:
			tipoNombre = "Perros";
			break;
		case 2:
			tipoNombre = "Gatos";
			break;
		default :
			tipoNombre = "Mascotas";	
		}
		return tipoNombre;
	}

	public String getFileSeparator() {
		return fileSeparator;
	}

	public void setFileSeparator(String fileSeparator) {
		this.fileSeparator = fileSeparator;
	}

	public String getBlankImage() {
		return blankImage;
	}

	public void setBlankImage(String blankImage) {
		this.blankImage = blankImage;
	}

	public String getMascotasPath() {
		return mascotasPath;
	}

	public void setMascotasPath(String mascotasPath) {
		this.mascotasPath = mascotasPath;
	}

	public LazyDataModel<Mascotas> getLisMascotas() {
		return lisMascotas;
	}

	public void setLisMascotas(LazyDataModel<Mascotas> lisMascotas) {
		this.lisMascotas = lisMascotas;
	}

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

	public String getDeploymentsPath() {
		return deploymentsPath;
	}

}
