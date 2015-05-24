package com.web.pet.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CotlugarBO;
import com.web.pet.bo.CotservicioBO;
import com.web.pet.bo.PetespecieBO;
import com.web.pet.bo.PetfotoBO;
import com.web.pet.bo.PetmascotaBO;
import com.web.pet.bo.PetmascotacolorBO;
import com.web.pet.bo.PetordenservicioBO;
import com.web.pet.bo.PetordenserviciodetalleBO;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cotlugar;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cotservicio;
import com.web.pet.pojo.annotations.Mascotas;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Petfotomascota;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.pet.pojo.annotations.Petordenserviciodetalle;
import com.web.pet.pojo.annotations.PetordenserviciodetalleId;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;
import com.web.util.Utilities;

@ManagedBean
@ViewScoped
public class OrdenServicioBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6374223940126173373L;
	private int idordenservicio;
	private Petordenservicio petordenservicio;
	private Petmascotahomenaje petmascotahomenajeselected;
	private Mascotas mascotasselected;
	private Petordenserviciodetalle petordenserviciodetalleItem;
	private LazyDataModel<Petordenserviciodetalle> lisPetordenserviciodetalle;
	private List<Cotlugar> lisCotlugar;
	private List<Petmascotacolor> lisPetmascotacolor;
	private List<Cotservicio> lisCotservicio;
	private Petmascotahomenaje petmascotahomenajenuevo; 
	private Cotpersona cotpersonanuevo; 
	private List<Petespecie> lisPetespecie;

	public OrdenServicioBean() {
		petordenservicio = new Petordenservicio(0, new Petmascotahomenaje(), new Setestado(), new Cotlugar(), null, null, null, null, null, null, null);
		mascotasselected = new Mascotas(new Petfotomascota(), new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),0,new BigDecimal(0),null,false,false,null)); 
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Setestado(), new Setusuario(), new Cotservicio(), new Petordenservicio(), null, null);
		lisPetmascotacolor = new ArrayList<Petmascotacolor>();
		petmascotahomenajenuevo = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,null,new Cotpersona(),null,1,new BigDecimal(0),null,false,false,null);
		cotpersonanuevo = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0);
		llenarListaLugar();
		llenarListaServicio();
		inicializarEspecieMascota();
		consultarDetalle();
	}
	
	@PostConstruct
	public void PostOrdenServicioBean(){
		FacesUtil facesUtil = new FacesUtil();
		idordenservicio = Integer.parseInt(facesUtil.getParametroUrl("idordenservicio") != null ? facesUtil
						.getParametroUrl("idordenservicio").toString() : "0");
		
		if(idordenservicio > 0){
			try{
				petordenservicio = new PetordenservicioBO().getPetordenservicioById(idordenservicio);
				if(petordenservicio.getCotlugar() == null){
					petordenservicio.setCotlugar(new Cotlugar());
				}
				Mascotas mascotas = new Mascotas();
				petmascotahomenajeselected = new PetmascotaBO().getPetmascotaById(petordenservicio.getPetmascotahomenaje().getIdmascota());
				Petfotomascota petfoto = new PetfotoBO().getPetfotoPerfilByPetId(petmascotahomenajeselected.getIdmascota());
				mascotas.setPetmascotahomenaje(petmascotahomenajeselected);
				mascotas.setPetfotomascota(petfoto);
				mascotasselected = mascotas;
				lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(petmascotahomenajeselected.getIdmascota());
				if(lisPetmascotacolor == null){
					lisPetmascotacolor = new ArrayList<Petmascotacolor>();
				}
			}catch(Exception re){
				re.printStackTrace();
				new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
	}
	
	private void llenarListaLugar(){
		try{
			lisCotlugar = new ArrayList<Cotlugar>();
			
			CotlugarBO cotlugarBO = new CotlugarBO();
			List<Cotlugar> lisTmp = cotlugarBO.lisCotlugar();
			if(lisTmp != null && lisTmp.size() > 0){
				lisCotlugar.addAll(lisTmp);
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	private void llenarListaServicio(){
		try{
			Cotservicio cotservicio = new Cotservicio();
			cotservicio.setIdservicio(0);
			cotservicio.setNombre("Seleccione");
			cotservicio.setSetestado(new Setestado());
			cotservicio.setSetusuario(new Setusuario());
			
			lisCotservicio = new ArrayList<Cotservicio>();
			lisCotservicio.add(cotservicio);
			
			CotservicioBO cotservicioBO = new CotservicioBO();
			List<Cotservicio> lisTmp = cotservicioBO.lisCotservicio();
			if(lisTmp != null && lisTmp.size() > 0){
				lisCotservicio.addAll(lisTmp);
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	@SuppressWarnings("serial")
	private void consultarDetalle(){
		try
		{
			setLisPetordenserviciodetalle(new LazyDataModel<Petordenserviciodetalle>() {
				public List<Petordenserviciodetalle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Petordenserviciodetalle> data = new ArrayList<Petordenserviciodetalle>();
	
					PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
					int args[] = {0};
					data = petordenserviciodetalleBO.lisPetordenserviciodetalleByPage(petordenservicio.getIdordenservicio(), pageSize, first, args);
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
			});
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	private void inicializarEspecieMascota(){
		try
		{
			lisPetespecie = new PetespecieBO().lisPetespecie();
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public Petmascotahomenaje getPetmascotahomenajeselected() {
		return petmascotahomenajeselected;
	}

	public void setPetmascotahomenajeselected(Petmascotahomenaje petmascotahomenajeselected) {
		this.petmascotahomenajeselected = petmascotahomenajeselected;
	}

	public int getIdordenservicio() {
		return idordenservicio;
	}

	public void setIdordenservicio(int idordenservicio) {
		this.idordenservicio = idordenservicio;
	}

	public Petordenservicio getPetordenservicio() {
		return petordenservicio;
	}

	public void setPetordenservicio(Petordenservicio petordenservicio) {
		this.petordenservicio = petordenservicio;
	}

	public Mascotas getMascotasselected() {
		return mascotasselected;
	}

	public void setMascotasselected(Mascotas mascotasselected) {
		this.mascotasselected = mascotasselected;
	}

	public Petordenserviciodetalle getPetordenserviciodetalleItem() {
		return petordenserviciodetalleItem;
	}

	public void setPetordenserviciodetalleItem(
			Petordenserviciodetalle petordenserviciodetalleItem) {
		this.petordenserviciodetalleItem = petordenserviciodetalleItem == null ? new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Setestado(), new Setusuario(), new Cotservicio(), new Petordenservicio(), null, null) : petordenserviciodetalleItem;
	}

	public LazyDataModel<Petordenserviciodetalle> getLisPetordenserviciodetalle() {
		return lisPetordenserviciodetalle;
	}

	public void setLisPetordenserviciodetalle(
			LazyDataModel<Petordenserviciodetalle> lisPetordenserviciodetalle) {
		this.lisPetordenserviciodetalle = lisPetordenserviciodetalle;
	}

	public List<Cotlugar> getLisCotlugar() {
		return lisCotlugar;
	}

	public void setLisCotlugar(List<Cotlugar> lisCotlugar) {
		this.lisCotlugar = lisCotlugar;
	}

	public List<Petmascotacolor> getLisPetmascotacolor() {
		return lisPetmascotacolor;
	}

	public void setLisPetmascotacolor(List<Petmascotacolor> lisPetmascotacolor) {
		this.lisPetmascotacolor = lisPetmascotacolor;
	}

	public List<Cotservicio> getLisCotservicio() {
		return lisCotservicio;
	}

	public void setLisCotservicio(List<Cotservicio> lisCotservicio) {
		this.lisCotservicio = lisCotservicio;
	}

	public Petmascotahomenaje getPetmascotahomenajenuevo() {
		return petmascotahomenajenuevo;
	}

	public void setPetmascotahomenajenuevo(
			Petmascotahomenaje petmascotahomenajenuevo) {
		this.petmascotahomenajenuevo = petmascotahomenajenuevo;
	}

	public List<Petespecie> getLisPetespecie() {
		return lisPetespecie;
	}

	public void setLisPetespecie(List<Petespecie> lisPetespecie) {
		this.lisPetespecie = lisPetespecie;
	}

	public Cotpersona getCotpersonanuevo() {
		return cotpersonanuevo;
	}

	public void setCotpersonanuevo(Cotpersona cotpersonanuevo) {
		this.cotpersonanuevo = cotpersonanuevo;
	}

	public List<Mascotas> buscarMascotas(String query) {
		List<Mascotas> lisMascotas = new ArrayList<Mascotas>();
		
		try{
			PetmascotaBO petmascotaBO = new PetmascotaBO();
			int args[] = {0};
			lisMascotas = petmascotaBO.lisMascotasByPage(query, 10, 0, args);
			
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
			
		return lisMascotas;
	}
	
	public void itemSelectMascota(SelectEvent event){
		try{
			Mascotas mascotas = (Mascotas) event.getObject();
			lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(mascotas.getPetmascotahomenaje().getIdmascota());
			if(lisPetmascotacolor == null){
				lisPetmascotacolor = new ArrayList<Petmascotacolor>();
			}
		}
		catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void newItem(){
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Setestado(), new Setusuario(), new Cotservicio(), new Petordenservicio(), null, null);
	}
	
	public void guardarItem(){
		try{
			if(validarCamposDetalle()){
				PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
				boolean ok = false;
				
				if(petordenserviciodetalleItem.getId().getIdordenserviciodetalle() > 0){
					ok = petordenserviciodetalleBO.updatePetordenserviciodetalle(petordenserviciodetalleItem);
				}else{
					PetordenserviciodetalleId petordenserviciodetalleId = new PetordenserviciodetalleId();
					petordenserviciodetalleId.setIdordenservicio(petordenservicio.getIdordenservicio());
					petordenserviciodetalleItem.setId(petordenserviciodetalleId);
					ok = petordenserviciodetalleBO.newPetordenserviciodetalle(petordenserviciodetalleItem);
				}
				
				petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Setestado(), new Setusuario(), new Cotservicio(), new Petordenservicio(), null, null);
				
				if(ok){
					new MessageUtil().showInfoMessage("Exito!", "Registro completo!");
				}
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	private boolean validarCamposDetalle()
	{
		boolean ok = true;
		
		if(petordenserviciodetalleItem.getCotservicio() == null || petordenserviciodetalleItem.getCotservicio().getIdservicio() == 0){
			ok = false;
			new MessageUtil().showWarnMessage("Datos incompletos!", "El Servicio es obligatorio!");
		}
		
		return ok;
	}
	
	public void guardar(){
		
		if(validarObligatorios()){
			try{
				PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
				petordenservicio.setPetmascotahomenaje(mascotasselected.getPetmascotahomenaje());
				
				if(petordenservicio.getCotlugar() == null ||  petordenservicio.getCotlugar().getIdlugar() == 0){
					petordenservicio.setCotlugar(null);
				}
				
				if(petordenservicio.getIdordenservicio() > 0){
					petordenservicioBO.updatePetordenservicio(petordenservicio);
					new MessageUtil().showInfoMessage("Exito!", "Datos actualizados!");
				}else{
					petordenservicioBO.newPetordenservicio(petordenservicio);
					FacesUtil facesUtil = new FacesUtil();
					facesUtil.redirect("../admin/ordenservicio.jsf?faces-redirect=true&idordenservicio="+petordenservicio.getIdordenservicio()+"&iditem=40");
				}
			}catch(Exception re){
				re.printStackTrace();
				new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		
	}
	
	private boolean validarObligatorios()
	{
		boolean ok = true;
		
		if(mascotasselected == null || mascotasselected.getPetmascotahomenaje() == null || mascotasselected.getPetmascotahomenaje().getIdmascota() == 0){
			new MessageUtil().showWarnMessage("Datos incompletos!", "Seleccione la Mascota!");
			ok = false;
		}
		
		return ok;
	}
	
	public void eliminar(){
		try{
			PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			petordenservicio.setSetestado(setestado);
			
			if(petordenservicio.getCotlugar() == null ||  petordenservicio.getCotlugar().getIdlugar() == 0){
				petordenservicio.setCotlugar(null);
			}
			
			petordenservicioBO.updatePetordenservicio(petordenservicio);
			
			FacesUtil facesUtil = new FacesUtil();
			facesUtil.redirect("../admin/ordenesservicio.jsf?iditem=40");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void eliminarItem(){
		try{
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			petordenserviciodetalleItem.setSetestado(setestado);
			PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
			petordenserviciodetalleBO.updatePetordenserviciodetalle(petordenserviciodetalleItem);
			
			/*PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
			petordenserviciodetalleBO.eliminarPetordenserviciodetalle(petordenserviciodetalleItem.getId());*/
			
			new MessageUtil().showInfoMessage("Exito!", "Registro Eliminado!");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void imprimir(){
		InputStream inputStream = null;
		
		try {
			String nombreReporte = "OrdenServicio";

			Map<String, Object> parametros = new HashMap<String, Object>();
			
			FileUtil fileUtil = new FileUtil();
			inputStream = fileUtil.getLogoEmpresaAsStream();
			if(inputStream != null){
				parametros.put("P_LOGO", inputStream);
			}
			
			parametros.put("P_IDORDENSERVICIO", petordenservicio.getIdordenservicio());
			
			new Utilities().imprimirJasperPdf(nombreReporte, parametros);
		}catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void guardarMascotaResumido(){
		try{
			if(validarCamposResumido()){
				boolean ok = false;
				PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
				
				ok = petordenservicioBO.grabarMascotaBasico(petmascotahomenajenuevo, cotpersonanuevo);
				
				if(ok){
					new MessageUtil().showInfoMessage("Exito!", "Mascota Ingresada!");
					petmascotahomenajenuevo = new Petmascotahomenaje(0, new Setestado(), new Setusuario(), new Petespecie(), null, null, null, null, null, null, null, null, null, null, null, null, new Petraza(), new Cotpersona(), new Cottipoidentificacion(), null, null, null, false, false, null);
				}
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	private boolean validarCamposResumido()
	{
		boolean ok = true;
		
		if(petmascotahomenajenuevo.getIdmascotaveterinaria() == null || petmascotahomenajenuevo.getIdmascotaveterinaria().trim().length() == 0){
			new MessageUtil().showWarnMessage("Datos incompletos!", "El código de la mascota es obligatorio!");
			ok = false;
		}else{
			if(petmascotahomenajenuevo.getNombre() == null || petmascotahomenajenuevo.getNombre().trim().length() == 0){
				new MessageUtil().showWarnMessage("Datos incompletos!", "El nombre de la mascota es obligatorio!");
				ok = false;
			}else{
				if(petmascotahomenajenuevo.getPetespecie().getIdespecie() == 0){
					new MessageUtil().showWarnMessage("Datos incompletos!", "La especie de la mascota es obligatorio!");
					ok = false;
				}else{
					if(petmascotahomenajenuevo.getSexo() == 0){
						new MessageUtil().showWarnMessage("Datos incompletos!", "El sexo de la mascota es obligatorio!");
						ok = false;
					}
				}
			}
		}
		
		if(ok){
			if(cotpersonanuevo.getNombre1() == null || cotpersonanuevo.getNombre1().trim().length() == 0){
				new MessageUtil().showWarnMessage("Datos incompletos!", "El Primer Nombre del propietario es obligatorio!");
				ok = false;
			}else{
				if(cotpersonanuevo.getApellido1() == null || cotpersonanuevo.getApellido1().trim().length() == 0){
					new MessageUtil().showWarnMessage("Datos incompletos!", "El Primer Apellido del propietario es obligatorio!");
					ok = false;
				}else{
					if(cotpersonanuevo.getSexo() == 0){
						new MessageUtil().showWarnMessage("Datos incompletos!", "El sexo del propietario es obligatorio!");
						ok = false;
					}else{
						if(cotpersonanuevo.getTelefono() == null || cotpersonanuevo.getTelefono().trim().length() == 0 ){
							new MessageUtil().showWarnMessage("Datos incompletos!", "El telefono del propietario es obligatorio!");
							ok = false;
						}
					}
				}
			}
		}
		
		return ok;
	}

}
