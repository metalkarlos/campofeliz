package com.web.pet.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CotlugarBO;
import com.web.pet.bo.CotpersonaBO;
import com.web.pet.bo.CotservicioBO;
import com.web.pet.bo.PetfotoBO;
import com.web.pet.bo.PetmascotaBO;
import com.web.pet.bo.PetmascotacolorBO;
import com.web.pet.bo.PetordenservicioBO;
import com.web.pet.bo.PetordenserviciodetalleBO;
import com.web.pet.pojo.annotations.Cotestado;
import com.web.pet.pojo.annotations.Cotlugar;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cotservicio;
import com.web.pet.pojo.annotations.Mascotas;
import com.web.pet.pojo.annotations.Petestado;
import com.web.pet.pojo.annotations.Petfoto;
import com.web.pet.pojo.annotations.Petmascota;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.pet.pojo.annotations.Petordenserviciodetalle;
import com.web.pet.pojo.annotations.PetordenserviciodetalleId;
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
	private Petmascota petmascotaselected;
	private Mascotas mascotasselected;
	private Petordenserviciodetalle petordenserviciodetalleItem;
	private LazyDataModel<Petordenserviciodetalle> lisPetordenserviciodetalle;
	private List<Cotlugar> lisCotlugar;
	private List<Petmascotacolor> lisPetmascotacolor;
	private List<Cotservicio> lisCotservicio;
	private String rutaImagenes;

	public OrdenServicioBean() {
		petordenservicio = new Petordenservicio(0, new Petmascota(), new Petestado(), new Cotlugar(), null, null, null, null, null, null, null);
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Petestado(), new Setusuario(), new Cotservicio(), new Petordenservicio(), null, null);
		lisPetmascotacolor = new ArrayList<Petmascotacolor>();
		llenarListaLugar();
		llenarListaServicio();
		consultarDetalle();
		cargarRutaImagenes();
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
			cotservicio.setCotestado(new Cotestado());
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
			});
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
	
	public Petmascota getPetmascotaselected() {
		return petmascotaselected;
	}

	public void setPetmascotaselected(Petmascota petmascotaselected) {
		this.petmascotaselected = petmascotaselected;
	}

	public int getIdordenservicio() {
		return idordenservicio;
	}

	public void setIdordenservicio(int idordenservicio) {
		this.idordenservicio = idordenservicio;
		if(idordenservicio > 0){
			try{
				petordenservicio = new PetordenservicioBO().getPetordenservicioById(idordenservicio);
				if(petordenservicio.getCotlugar() == null){
					petordenservicio.setCotlugar(new Cotlugar());
				}
				Mascotas mascotas = new Mascotas();
				petmascotaselected = new PetmascotaBO().getPetmascotaById(petordenservicio.getPetmascota().getIdmascota());
				Petfoto petfoto = new PetfotoBO().getPetfotoPerfilByPetId(petmascotaselected.getIdmascota());
				mascotas.setPetmascota(petmascotaselected);
				mascotas.setPetfoto(petfoto);
				mascotasselected = mascotas;
				lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(petmascotaselected.getIdmascota());
				if(lisPetmascotacolor == null){
					lisPetmascotacolor = new ArrayList<Petmascotacolor>();
				}
			}catch(Exception re){
				re.printStackTrace();
				new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
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
		this.petordenserviciodetalleItem = petordenserviciodetalleItem == null ? new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Petestado(), new Setusuario(), new Cotservicio(), new Petordenservicio(), null, null) : petordenserviciodetalleItem;
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

	public List<Cotpersona> buscarPropietarios(String query) {
		List<Cotpersona> lisPropietarios = new ArrayList<Cotpersona>();
		
		List<Cotpersona> lisCotpersona = new ArrayList<Cotpersona>();
		CotpersonaBO cotpersonaBO = new CotpersonaBO();
		int args[] = {0};
		String[] nombres = null;
		if(query != null && query.trim().length() > 0){
			nombres = query.split(" ");
		}
		lisCotpersona = cotpersonaBO.lisCotpersonaPetmascotaByPage(nombres, 10, 0, args);
		
		if(lisCotpersona != null && lisCotpersona.size() > 0){
			for(Cotpersona cotpersona : lisCotpersona){
				lisPropietarios.add(cotpersona);
			}
		}
		
		return lisPropietarios;
	}
	
	public List<Mascotas> buscarMascotas(String query) {
		List<Mascotas> lisMascotas = new ArrayList<Mascotas>();
		PetmascotaBO petmascotaBO = new PetmascotaBO();
		int args[] = {0};
		lisMascotas = petmascotaBO.lisMascotasByPage(query, 10, 0, args);
		
		return lisMascotas;
	}
	
	public void itemSelectMascota(SelectEvent event){
		try{
			Mascotas mascotas = (Mascotas) event.getObject();
			lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(mascotas.getPetmascota().getIdmascota());
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
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Petestado(), new Setusuario(), new Cotservicio(), new Petordenservicio(), null, null);
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
				
				petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Petestado(), new Setusuario(), new Cotservicio(), new Petordenservicio(), null, null);
				
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
			new MessageUtil().showErrorMessage("Datos incompletos!", "El Servicio es obligatorio!");
		}
		
		return ok;
	}
	
	public void guardar(){
		
		if(validarObligatorios()){
			try{
				PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
				petordenservicio.setPetmascota(mascotasselected.getPetmascota());
				
				if(petordenservicio.getIdordenservicio() > 0){
					petordenservicioBO.updatePetordenservicio(petordenservicio);
					new MessageUtil().showInfoMessage("Exito!", "Datos actualizados!");
				}else{
					petordenservicioBO.newPetordenservicio(petordenservicio);
					new MessageUtil().showInfoMessage("Exito!", "Orden de Servicio creada!");
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
		
		if(mascotasselected == null || mascotasselected.getPetmascota() == null || mascotasselected.getPetmascota().getIdmascota() == 0){
			new MessageUtil().showErrorMessage("Datos incompletos!", "La Mascota es obligatoria!");
			ok = false;
		}
		
		return ok;
	}
	
	public void eliminar(){
		try{
			Petestado petestado = new Petestado();
			petestado.setIdestado(2);//inactivo
			petordenservicio.setPetestado(petestado);
			PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
			petordenservicioBO.updatePetordenservicio(petordenservicio);
			
			FacesUtil facesUtil = new FacesUtil();
			facesUtil.redirect("../pages/ordenesservicio.jsf?iditem=40");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void eliminarItem(){
		try{
			Petestado petestado = new Petestado();
			petestado.setIdestado(2);//inactivo
			petordenserviciodetalleItem.setPetestado(petestado);
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

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}
}
