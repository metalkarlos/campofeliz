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

import com.web.pet.bo.PetmascotaBO;
import com.web.pet.bo.PetrazaBO;
import com.web.pet.bo.PetespecieBO;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Mascotas;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class BuscarMascotaBean implements Serializable {
	private static final long serialVersionUID = -1838272166794983148L;
	private Petmascotahomenaje petmascotahomenaje;
	private String caracteristicas;
	private List<Petespecie> lisPetespecie;
	private List<Petraza> lisPetraza;
	private LazyDataModel<Mascotas> lisMascotas;
	private boolean renderGrid;//Para evitar consultar al inicio y sólo al presionar botón
	private int columnsGrid;
	private int rowsGrid;
	private String rutaImagenes;

	public BuscarMascotaBean() {
		petmascotahomenaje = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),0,new BigDecimal(0),null,false,false,null);
		llenarPettipo();
		llenarPetraza();
		setRenderGrid(false);
		setColumnsGrid(1);
		setRowsGrid(4);
		
		consultarMascotas();
		cargarRutaImagenes();
	}

	@SuppressWarnings("serial")
	private void consultarMascotas(){
		try
		{
			lisMascotas = new LazyDataModel<Mascotas>() {
				public List<Mascotas> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Mascotas> data = new ArrayList<Mascotas>();
					
					if(isRenderGrid())
					{
						PetmascotaBO petmascotaBO = new PetmascotaBO();
						int args[] = {0};
						String[] caracteristicas = null;
						if(BuscarMascotaBean.this.caracteristicas != null && BuscarMascotaBean.this.caracteristicas.trim().length() > 0){
							caracteristicas = BuscarMascotaBean.this.caracteristicas.split(" ");
						}
						
						data = petmascotaBO.lisMascotasBusquedaByPage(petmascotahomenaje, caracteristicas, (pageSize * columnsGrid), (first * columnsGrid), args);
						this.setRowCount(args[0]);
					}
					
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
	
	private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
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

	public LazyDataModel<Mascotas> getLisMascotas() {
		return lisMascotas;
	}

	public void setLisMascotas(LazyDataModel<Mascotas> lisMascotas) {
		this.lisMascotas = lisMascotas;
	}

	public boolean isRenderGrid() {
		return renderGrid;
	}

	public void setRenderGrid(boolean renderGrid) {
		this.renderGrid = renderGrid;
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

	private void llenarPettipo(){
		try{
			PetespecieBO pettipoBO = new PetespecieBO();
			setLisPetespecie(pettipoBO.lisPetespecie());
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", re.getMessage());
		}
	}
	
	private void llenarPetraza(){
		try{
			PetrazaBO petrazaBO = new PetrazaBO();
			setLisPetraza(petrazaBO.lisRazas());
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", re.getMessage());
		}
	}
	
	public void buscarMascotas(){
		setRenderGrid(true);
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

}
