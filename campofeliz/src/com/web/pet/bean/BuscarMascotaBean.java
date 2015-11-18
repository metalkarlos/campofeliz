package com.web.pet.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CotpersonaBO;
import com.web.pet.bo.PetmascotaBO;
import com.web.pet.bo.PetrazaBO;
import com.web.pet.bo.PetespecieBO;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
//import com.web.pet.pojo.annotations.Mascotas;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class BuscarMascotaBean implements Serializable {
	private static final long serialVersionUID = -1838272166794983148L;
	private Petmascotahomenaje petmascotahomenaje;
	private String caracteristicas;
	private List<Petespecie> lisPetespecie;
	private List<Petraza> lisPetraza;
	//private LazyDataModel<Mascotas> lisMascotas;
	private LazyDataModel<Petmascotahomenaje> lisPetmascotahomenaje;
	private int columnsGrid;
	private int rowsGrid;

	public BuscarMascotaBean() {
		petmascotahomenaje = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),0,new BigDecimal(0),null,false,false,null);
		llenarPettipo();
		llenarPetraza();
		setColumnsGrid(1);
		setRowsGrid(4);
		
		consultarMascotas();
	}

	@SuppressWarnings("serial")
	private void consultarMascotas(){
		try
		{
			lisPetmascotahomenaje = new LazyDataModel<Petmascotahomenaje>() {
				public List<Petmascotahomenaje> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Petmascotahomenaje> data = new ArrayList<Petmascotahomenaje>();
					
					PetmascotaBO petmascotaBO = new PetmascotaBO();
					int args[] = {0};
					String[] caracteristicas = null;
					if(BuscarMascotaBean.this.caracteristicas != null && BuscarMascotaBean.this.caracteristicas.trim().length() > 0){
						caracteristicas = BuscarMascotaBean.this.caracteristicas.split(" ");
					}
					
					data = petmascotaBO.lisPetmascotahomenajeBusquedaByPage(petmascotahomenaje, caracteristicas, (pageSize * columnsGrid), (first * columnsGrid), args);
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
	
	private void llenarPettipo(){
		try{
			PetespecieBO pettipoBO = new PetespecieBO();
			setLisPetespecie(pettipoBO.lisPetespecie());
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage(re.getMessage(),"");
		}
	}
	
	private void llenarPetraza(){
		try{
			PetrazaBO petrazaBO = new PetrazaBO();
			setLisPetraza(petrazaBO.lisRazas(0));
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage(re.getMessage(),"");
		}
	}
	
	public List<Cotpersona> buscarPropietarios(String query) {
		List<Cotpersona> lisPropietarios = new ArrayList<Cotpersona>();
		
		List<Cotpersona> lisCotpersona = new ArrayList<Cotpersona>();
		CotpersonaBO cotpersonaBO = new CotpersonaBO();
		int args[] = {0};
		String[] nombres = null;
		if(query != null && query.trim().length() > 0){
			nombres = query.split(" ");
		}
		lisCotpersona = cotpersonaBO.lisCotpersonaByPage(nombres, 10, 0, args);
		
		if(lisCotpersona != null && lisCotpersona.size() > 0){
			for(Cotpersona cotpersona : lisCotpersona){
				lisPropietarios.add(cotpersona);
			}
		}
		
		return lisPropietarios;
	}
	
	public Petmascotahomenaje getPetmascotahomenaje() {
		return petmascotahomenaje;
	}

	public void setPetmascotahomenaje(Petmascotahomenaje petmascotahomenaje) {
		this.petmascotahomenaje = petmascotahomenaje;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public List<Petespecie> getLisPetespecie() {
		return lisPetespecie;
	}

	public void setLisPetespecie(List<Petespecie> lisPetespecie) {
		this.lisPetespecie = lisPetespecie;
	}

	public List<Petraza> getLisPetraza() {
		return lisPetraza;
	}

	public void setLisPetraza(List<Petraza> lisPetraza) {
		this.lisPetraza = lisPetraza;
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

	public LazyDataModel<Petmascotahomenaje> getLisPetmascotahomenaje() {
		return lisPetmascotahomenaje;
	}

	public void setLisPetmascotahomenaje(
			LazyDataModel<Petmascotahomenaje> lisPetmascotahomenaje) {
		this.lisPetmascotahomenaje = lisPetmascotahomenaje;
	}

}
