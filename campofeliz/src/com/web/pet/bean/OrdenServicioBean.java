package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

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
import com.web.pet.global.Parametro;
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
import com.web.util.MessageUtil;
import com.web.util.Utilities;

@ManagedBean
@Named
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

	public OrdenServicioBean() {
		petordenservicio = new Petordenservicio(0, new Petmascota(), new Petestado(), new Cotlugar(), null, null, null, null, null, null, null);
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Petestado(), new Setusuario(), new Cotservicio(), new Petordenservicio(), null, null);
		lisPetmascotacolor = new ArrayList<Petmascotacolor>();
		llenarListaLugar();
		llenarListaServicio();
		consultarDetalle();
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
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
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
					try{
						ok = petordenserviciodetalleBO.updatePetordenserviciodetalle(petordenserviciodetalleItem);
					}catch(RuntimeException re){
						new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
					}
				}else{
					try{
						PetordenserviciodetalleId petordenserviciodetalleId = new PetordenserviciodetalleId();
						petordenserviciodetalleId.setIdordenservicio(petordenservicio.getIdordenservicio());
						petordenserviciodetalleItem.setId(petordenserviciodetalleId);
						ok = petordenserviciodetalleBO.newPetordenserviciodetalle(petordenserviciodetalleItem);
					}catch(RuntimeException re){
						new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
					}
				}
				
				petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Petestado(), new Setusuario(), new Cotservicio(), new Petordenservicio(), null, null);
				
				if(ok){
					new MessageUtil().showInfoMessage("Exito!", "Registro completo!");
				}
			}
		}catch(Exception re){
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
		try{
			PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
			
			if(petordenservicio.getIdordenservicio() > 0){
				try{
					petordenservicioBO.updatePetordenservicio(petordenservicio);
					new MessageUtil().showInfoMessage("Exito!", "Datos actualizados!");
				}catch(RuntimeException re){
					new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
				}
			}else{
				try{
					petordenservicio.setPetmascota(mascotasselected.getPetmascota());
					petordenservicioBO.newPetordenservicio(petordenservicio);
					new MessageUtil().showInfoMessage("Exito!", "Orden de Servicio creada!");
				}catch(RuntimeException re){
					new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
				}
			}
			
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void eliminar(){
		try{
			Petestado petestado = new Petestado();
			petestado.setIdestado(2);//inactivo
			petordenservicio.setPetestado(petestado);
			PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
			petordenservicioBO.updatePetordenservicio(petordenservicio);
			
			FacesUtil facesUtil = new FacesUtil();
			facesUtil.redirect("ordenesservicio.jsf?iditem=40");
		}catch(Exception re){
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
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void imprimir(){
		try {
			String nombreReporte = "Rep_Solicitd_Mourtoria";

			String rutaLogo = Parametro.RUTA_IMAGENES_MISCELLANEOUS+"logo_empresa.jpg";
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("P_LOGO", rutaLogo);
			parametros.put("P_IDORDENSERVICIO", petordenservicio.getIdordenservicio());
			
			new Utilities().imprimirJasperPdf(nombreReporte, parametros);
		}catch (Exception e) {
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
}
