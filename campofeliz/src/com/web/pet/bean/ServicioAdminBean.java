package com.web.pet.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import com.web.pet.bo.CotoficinaBO;
import com.web.pet.bo.PetservicioBO;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Cotempresa;
import com.web.pet.pojo.annotations.Cotoficina;
import com.web.pet.pojo.annotations.Petfotoservicio;
import com.web.pet.pojo.annotations.Petservicio;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;
import com.web.util.Utilities;

@ManagedBean
@ViewScoped
public class ServicioAdminBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4748535305651371565L;
	private int idservicio;
	private int idempresa;
	private Petservicio petservicio;
	private Petservicio petservicioClon;
	private List<Petfotoservicio> lisPetfotoservicio;
	private List<Petfotoservicio> lisPetfotoservicioClon;
	private List<Cotoficina> lisCotoficina;
	private Petfotoservicio petfotoservicioSeleccionado;
	private boolean fotoSubida;
	private long maxfilesize;
	
	public ServicioAdminBean() {
		petservicio = new Petservicio(0, new Setestado(), null, new Setusuario(), null, null, new Cotoficina(), new Cotempresa(), null, null, null, false, new Date(), null, 0, new BigDecimal(0));
		petservicioClon = new Petservicio(0, new Setestado(), null, new Setusuario(), null, null, new Cotoficina(), new Cotempresa(), null, null, null, false, new Date(), null, 0, new BigDecimal(0));
		lisPetfotoservicio = new ArrayList<Petfotoservicio>();
		lisPetfotoservicioClon = new ArrayList<Petfotoservicio>();
		lisCotoficina = new ArrayList<Cotoficina>();
		petfotoservicioSeleccionado = new Petfotoservicio();
		fotoSubida = false;
		maxfilesize = Parametro.TAMA�O_IMAGEN;
	}
	
	@PostConstruct
	public void PostServicioAdminBean() {
		FacesUtil facesUtil = new FacesUtil();
		
		try{
			Object par = facesUtil.getParametroUrl("idservicio");
			Object par2 = facesUtil.getParametroUrl("idempresa");
			if(par != null && par2 != null){
				idservicio = Integer.parseInt(par.toString());
				idempresa = Integer.parseInt(par2.toString());
				
				CotoficinaBO cotoficinaBO = new CotoficinaBO();
				lisCotoficina = cotoficinaBO.lisCotoficinaByIdempresa(idempresa);
				
				if(idservicio > 0){
					consultaServicio();
				}else{
					PetservicioBO petservicioBO = new PetservicioBO();
					int orden = petservicioBO.getMaxOrden();
					petservicio.setOrden(orden + 1);
					petservicio.setCotempresa(new Cotempresa(idempresa, null));
				}
			}else{
				facesUtil.redirect("../pages/home.jsf");
			}
		} catch(NumberFormatException ne){
			try{facesUtil.redirect("../pages/home.jsf");}catch(Exception e){}
		} catch(Exception e) {
			e.printStackTrace();
			try{facesUtil.redirect("../pages/home.jsf");}catch(Exception e2){}
		}
	}
	
	private void consultaServicio(){
		if(this.idservicio > 0){
			try {
				PetservicioBO petservicioBO = new PetservicioBO();
				petservicio = petservicioBO.getPetservicioConObjetosById(idservicio,idempresa);
				
				if(petservicio != null && petservicio.getIdservicio() > 0){
					petservicioClon = petservicio.clonar();
					
					if(petservicio.getCotempresa() != null && petservicio.getCotempresa().getIdempresa() > 0){
						CotoficinaBO cotoficinaBO = new CotoficinaBO();
						lisCotoficina = cotoficinaBO.lisCotoficinaByIdempresa(petservicio.getCotempresa().getIdempresa());
					}
					
					if(petservicio.getPetfotoservicios() != null && petservicio.getPetfotoservicios().size() > 0){
						//ordenar por fecharegistro
						Petfotoservicio[] arr = new Petfotoservicio[petservicio.getPetfotoservicios().size()];
						arr = petservicio.getPetfotoservicios().toArray(arr);
						Arrays.sort(arr, Petfotoservicio.FecharegistroComparator);
						lisPetfotoservicio = new ArrayList<Petfotoservicio>(Arrays.asList(arr));
						
						for(Petfotoservicio petfotoservicio : lisPetfotoservicio){
							lisPetfotoservicioClon.add(petfotoservicio.clonar());
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
			}
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try{
			Petfotoservicio petfotoservicio = new Petfotoservicio();
			petfotoservicio.setImagen(event.getFile().getContents());
			petfotoservicio.setNombrearchivo(event.getFile().getFileName().toLowerCase());
			lisPetfotoservicio.add(petfotoservicio);
			fotoSubida = true;
			
			new MessageUtil().showInfoMessage("Presione Grabar para guardar los cambios.","");
		}catch(Exception x){
			x.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void ponerFotoPrincipal(){
		petservicio.setRutafoto(petfotoservicioSeleccionado.getRuta());
		petfotoservicioSeleccionado = new Petfotoservicio();
		new MessageUtil().showInfoMessage("Presione Grabar para guardar los cambios.","");
	}
	
	public void quitarFotoGaleria(){
		if(petfotoservicioSeleccionado.getRuta().equalsIgnoreCase(petservicio.getRutafoto())){
			new MessageUtil().showInfoMessage("La foto a eliminar es la foto principal de �ste servicio. Seleccione otra foto como principal para poderla eliminar.","");
		}else{
			lisPetfotoservicio.remove(petfotoservicioSeleccionado);
			petfotoservicioSeleccionado = new Petfotoservicio();
		}
	}
	
	public void grabar(){
		try{
			boolean ok = false;
			
			PetservicioBO petservicioBO = new PetservicioBO();
			Utilities utilities = new Utilities();
			
			if(idservicio == 0){
				ok = petservicioBO.ingresar(petservicio, lisPetfotoservicio);
				if(ok){
					utilities.mostrarPaginaMensaje("Servicio creado con exito!!");
				}else{
					new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
				}
			}else{
				ok = petservicioBO.modificar(petservicio, petservicioClon, lisPetfotoservicio, lisPetfotoservicioClon);
				if(ok){
					utilities.mostrarPaginaMensaje("Servicio modificado con exito!!");
				}else{
					new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void eliminar(){
		try{
			PetservicioBO petservicioBO = new PetservicioBO();
			Utilities utilities = new Utilities();
			
			boolean ok = petservicioBO.eliminar(petservicio);
			if(ok){
				utilities.mostrarPaginaMensaje("Servicio eliminado con exito!!");
			}else{
				utilities.mostrarPaginaMensaje("No se ha podido eliminar el Servicio. Comunicar al Webmaster.");
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public Petservicio getPetservicio() {
		return petservicio;
	}

	public void setPetservicio(Petservicio petservicio) {
		this.petservicio = petservicio;
	}

	public int getIdservicio() {
		return idservicio;
	}

	public void setIdservicio(int idservicio) {
		this.idservicio = idservicio;
	}

	public List<Petfotoservicio> getLisPetfotoservicio() {
		return lisPetfotoservicio;
	}

	public void setLisPetfotoservicio(List<Petfotoservicio> lisPetfotoservicio) {
		this.lisPetfotoservicio = lisPetfotoservicio;
	}

	public Petfotoservicio getPetfotoservicioSeleccionado() {
		return petfotoservicioSeleccionado;
	}

	public void setPetfotoservicioSeleccionado(
			Petfotoservicio petfotoservicioSeleccionado) {
		this.petfotoservicioSeleccionado = petfotoservicioSeleccionado;
	}

	public boolean isFotoSubida() {
		return fotoSubida;
	}

	public void setFotoSubida(boolean fotoSubida) {
		this.fotoSubida = fotoSubida;
	}

	public long getMaxfilesize() {
		return maxfilesize;
	}

	public List<Cotoficina> getLisCotoficina() {
		return lisCotoficina;
	}

	public void setLisCotoficina(List<Cotoficina> lisCotoficina) {
		this.lisCotoficina = lisCotoficina;
	}

	public int getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(int idempresa) {
		this.idempresa = idempresa;
	}
}
