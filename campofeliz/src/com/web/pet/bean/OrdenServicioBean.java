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

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.pet.bo.CotlugarBO;
import com.web.pet.bo.PetespecieBO;
import com.web.pet.bo.PetfotomascotaBO;
import com.web.pet.bo.PetmascotaBO;
import com.web.pet.bo.PetmascotacolorBO;
import com.web.pet.bo.PetordenservicioBO;
import com.web.pet.bo.PetordenserviciodetalleBO;
import com.web.pet.bo.PetrazaBO;
import com.web.pet.bo.PetservicioBO;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Petservicio;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cotlugar;
import com.web.pet.pojo.annotations.Cotpersona;
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
	private Petordenservicio petordenservicioClon;
	private Petmascotahomenaje petmascotahomenajeselected;
	private Mascotas mascotasselected;
	private Petordenserviciodetalle petordenserviciodetalleItem;
	private LazyDataModel<Petordenserviciodetalle> lisPetordenserviciodetalleModel;
	private List<Petordenserviciodetalle> lisPetordenserviciodetalle;
	private List<Cotlugar> lisCotlugar;
	private List<Petmascotacolor> lisPetmascotacolor;
	private List<Petservicio> lisPetservicio;
	private Petmascotahomenaje petmascotahomenajenuevo; 
	private Cotpersona cotpersonanuevo; 
	private List<Petespecie> lisPetespecie;
	private List<Petraza> lisRaza;
	private String linkReporte;

	public OrdenServicioBean() {
		petordenservicio = new Petordenservicio(0, new Petmascotahomenaje(), new Setestado(), new Cotlugar(), null, null, null, null, null, null, null);
		petordenservicioClon = new Petordenservicio(0, new Petmascotahomenaje(), new Setestado(), new Cotlugar(), null, null, null, null, null, null, null);
		mascotasselected = new Mascotas(new Petfotomascota(), new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),0,new BigDecimal(0),null,false,false,null)); 
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null);
		lisPetmascotacolor = new ArrayList<Petmascotacolor>();
		//petmascotahomenajenuevo = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,null,new Cotpersona(),null,1,new BigDecimal(0),null,false,false,null);
		petmascotahomenajenuevo = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);
		cotpersonanuevo = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
		lisPetordenserviciodetalle = new ArrayList<Petordenserviciodetalle>();
		llenarListaLugar();
		llenarListaServicio();
		inicializarEspecieMascota();
		llenarLisRaza();
		consultarDetalle();
	}
	
	@PostConstruct
	public void PostOrdenServicioBean(){
		FacesUtil facesUtil = new FacesUtil();
		
		try{
			Object par = facesUtil.getParametroUrl("idordenservicio");
			if(par != null){
				idordenservicio = Integer.parseInt(par.toString());
				
				petordenservicio = new PetordenservicioBO().getPetordenservicioById(idordenservicio);
				
				if(petordenservicio != null){
					petordenservicioClon = petordenservicio.clonar();
					
					if(petordenservicio.getCotlugar() == null){
						petordenservicio.setCotlugar(new Cotlugar());
						petordenservicioClon.setCotlugar(new Cotlugar());
					}
					Mascotas mascotas = new Mascotas();
					petmascotahomenajeselected = new PetmascotaBO().getPetmascotaById(petordenservicio.getPetmascotahomenaje().getIdmascota());
					Petfotomascota petfoto = new PetfotomascotaBO().getPetfotomascotaPerfilByIdmascota(petmascotahomenajeselected.getIdmascota());
					mascotas.setPetmascotahomenaje(petmascotahomenajeselected);
					mascotas.setPetfotomascota(petfoto);
					mascotasselected = mascotas;
					lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(petmascotahomenajeselected.getIdmascota());
					if(lisPetmascotacolor == null){
						lisPetmascotacolor = new ArrayList<Petmascotacolor>();
					}
				}else{
					petordenservicio = new Petordenservicio(0, new Petmascotahomenaje(), new Setestado(), new Cotlugar(), null, null, null, null, null, null, null);
				}
			}else{
				facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");
			}
		} catch(NumberFormatException ne){
			try{facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");}catch(Throwable e){}
		} catch(Exception e) {
			e.printStackTrace();
			try{facesUtil.redirect("../admin/home.jsf?faces-redirect=true&iditem=35");}catch(Throwable e2){}
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
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private void llenarListaServicio(){
		try{
			Petservicio petservicio = new Petservicio();
			petservicio.setIdservicio(0);
			petservicio.setNombre("Seleccione");
			petservicio.setSetestado(new Setestado());
			petservicio.setSetusuario(new Setusuario());
			
			lisPetservicio = new ArrayList<Petservicio>();
			lisPetservicio.add(petservicio);
			
			PetservicioBO petservicioBO = new PetservicioBO();
			List<Petservicio> lisTmp = petservicioBO.lisPetservicio(Parametro.EMPRESA_CAMPOFELIZ, Parametro.OFICINA_CAMPOFELIZ_LAROCA);
			if(lisTmp != null && lisTmp.size() > 0){
				lisPetservicio.addAll(lisTmp);
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private void inicializarEspecieMascota(){
		try
		{
			lisPetespecie = new PetespecieBO().lisPetespecie();
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private void llenarLisRaza(){
		try{
			Petraza petraza = new Petraza();
			petraza.setNombre("Seleccione");
			petraza.setSetestado(new Setestado());
			petraza.setSetusuario(new Setusuario());
			
			lisRaza = new ArrayList<Petraza>();
			lisRaza.add(petraza);
			
			PetrazaBO petrazaBO = new PetrazaBO();
			List<Petraza> lisTmp = petrazaBO.lisRazas();
			if(lisTmp != null & lisTmp.size() > 0){
				lisRaza.addAll(lisTmp);
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	@SuppressWarnings("serial")
	private void consultarDetalle(){
		try
		{
			lisPetordenserviciodetalleModel = new LazyDataModel<Petordenserviciodetalle>() {
				public List<Petordenserviciodetalle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					lisPetordenserviciodetalle = new ArrayList<Petordenserviciodetalle>();
					int args[] = {0};
					
					if(petordenservicio != null && petordenservicio.getIdordenservicio() > 0 ){
						PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
						lisPetordenserviciodetalle = petordenserviciodetalleBO.lisPetordenserviciodetalleByPage(petordenservicio.getIdordenservicio(), pageSize, first, args);
					}
					
					this.setRowCount(args[0]);
	
			        return lisPetordenserviciodetalle;
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

	public Petordenservicio getPetordenservicioClon() {
		return petordenservicioClon;
	}

	public void setPetordenservicioClon(Petordenservicio petordenservicioClon) {
		this.petordenservicioClon = petordenservicioClon;
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
		this.petordenserviciodetalleItem = petordenserviciodetalleItem == null ? new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null) : petordenserviciodetalleItem;
	}

	public LazyDataModel<Petordenserviciodetalle> getLisPetordenserviciodetalleModel() {
		return lisPetordenserviciodetalleModel;
	}

	public void setLisPetordenserviciodetalleModel(
			LazyDataModel<Petordenserviciodetalle> lisPetordenserviciodetalleModel) {
		this.lisPetordenserviciodetalleModel = lisPetordenserviciodetalleModel;
	}

	public List<Petordenserviciodetalle> getLisPetordenserviciodetalle() {
		return lisPetordenserviciodetalle;
	}

	public void setLisPetordenserviciodetalle(
			List<Petordenserviciodetalle> lisPetordenserviciodetalle) {
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

	public List<Petservicio> getLisPetservicio() {
		return lisPetservicio;
	}

	public void setLisPetservicio(List<Petservicio> lisPetservicio) {
		this.lisPetservicio = lisPetservicio;
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

	public List<Petraza> getLisRaza() {
		return lisRaza;
	}

	public void setLisRaza(List<Petraza> lisRaza) {
		this.lisRaza = lisRaza;
	}

	public String getLinkReporte() {
		return linkReporte;
	}

	public void setLinkReporte(String linkReporte) {
		this.linkReporte = linkReporte;
	}

	public List<Mascotas> buscarMascotas(String query) {
		List<Mascotas> lisMascotas = new ArrayList<Mascotas>();
		
		try{
			PetmascotaBO petmascotaBO = new PetmascotaBO();
			int args[] = {0};
			lisMascotas = petmascotaBO.lisMascotasByPage(query, 10, 0, args);
			
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
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
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void newItem(){
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null);
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
				
				petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null);
				
				if(ok){
					new MessageUtil().showInfoMessage("Servicio Agregado con exito!","");
				}
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

	private boolean validarCamposDetalle()
	{
		boolean ok = true;
		
		if(petordenserviciodetalleItem.getPetservicio() == null || petordenserviciodetalleItem.getPetservicio().getIdservicio() == 0){
			ok = false;
			new MessageUtil().showWarnMessage("Datos incompletos! El Servicio es obligatorio!","");
		}
		
		return ok;
	}
	
	public void guardar(){
		
		if(validarObligatorios()){
			try{
				boolean ok = false;
				
				PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
				petordenservicio.setPetmascotahomenaje(mascotasselected.getPetmascotahomenaje());
				
				if(petordenservicio.getCotlugar() == null ||  petordenservicio.getCotlugar().getIdlugar() == 0){
					petordenservicio.setCotlugar(null);
					petordenservicioClon.setCotlugar(null);
				}
				
				if(petordenservicio.getIdordenservicio() == 0){
					ok = petordenservicioBO.ingresarPetordenservicio(petordenservicio);
					if(ok){
						FacesUtil facesUtil = new FacesUtil();
						facesUtil.redirect("../admin/ordenservicio.jsf?faces-redirect=true&idordenservicio="+petordenservicio.getIdordenservicio()+"&iditem=40");
					}else{
						new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
					}
				}else{
					ok = petordenservicioBO.modificarPetordenservicio(petordenservicio, petordenservicioClon);
					if(ok){
						mostrarPaginaMensaje("Orden de Servicio actualizada con exito!!");
					}else{
						new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
					}
				}
			}catch(Exception re){
				re.printStackTrace();
				new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
			}
		}
		
	}
	
	private boolean validarObligatorios()
	{
		boolean ok = true;
		
		if(mascotasselected == null || mascotasselected.getPetmascotahomenaje() == null || mascotasselected.getPetmascotahomenaje().getIdmascota() == 0){
			new MessageUtil().showWarnMessage("Datos incompletos! Seleccione la Mascota!","");
			ok = false;
		}
		
		return ok;
	}
	
	private void mostrarPaginaMensaje(String mensaje) throws Exception {
		UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
		usuarioBean.setMensaje(mensaje);
		
		FacesUtil facesUtil = new FacesUtil();
		facesUtil.redirect("../admin/mensaje.jsf");	 
	}
	
	public void eliminar(){
		try{
			PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
			
			boolean ok = petordenservicioBO.eliminarPetordenservicio(petordenservicio,lisPetordenserviciodetalle);
			
			if(ok){
				mostrarPaginaMensaje("Orden de Servicio eliminada con exito!!");
			}else{
				new MessageUtil().showInfoMessage("No se ha podido eliminar Orden de Servicio.", "");
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void eliminarItem(){
		try{
			/*Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			petordenserviciodetalleItem.setSetestado(setestado);
			PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
			petordenserviciodetalleBO.updatePetordenserviciodetalle(petordenserviciodetalleItem);*/
			
			PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
			petordenserviciodetalleBO.eliminarPetordenserviciodetalle(petordenserviciodetalleItem.getId());
			
			petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null);
			
			new MessageUtil().showInfoMessage("Registro Eliminado con exito!","");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void imprimir2(){
		try {
			if(lisPetordenserviciodetalle != null && lisPetordenserviciodetalle.size() > 0){
				InputStream inputStream = null;
				String nombreReporte = "OrdenServicio";
	
				Map<String, Object> parametros = new HashMap<String, Object>();
				
				FileUtil fileUtil = new FileUtil();
				inputStream = fileUtil.getLogoEmpresaAsStream();
				if(inputStream != null){
					parametros.put("P_LOGO", inputStream);
				}
				
				parametros.put("P_IDORDENSERVICIO", petordenservicio.getIdordenservicio());
				
				new Utilities().imprimirJasperPdf(nombreReporte, parametros);
			}else{
				new MessageUtil().showWarnMessage("Debe ingresar los servicios. Corrija y reintente.","");
			}
		}catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void imprimir(){
		try {
			if(lisPetordenserviciodetalle != null && lisPetordenserviciodetalle.size() > 0){
				FacesUtil facesUtil = new FacesUtil();
				
				VisorBean visorBean = (VisorBean)facesUtil.getSessionBean("visorBean");
				visorBean.setNombreReporte("OrdenServicio");
				visorBean.getParametros().put("P_IDORDENSERVICIO", petordenservicio.getIdordenservicio());
				
				RequestContext.getCurrentInstance().execute("varDlgMostrarReporte.show()");
			}else{
				new MessageUtil().showWarnMessage("Debe ingresar los servicios. Corrija y reintente.","");
			}
		}catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void guardarMascotaResumido(){
		try{
			if(petmascotahomenajenuevo.getIdmascota() == 0){
				if(validarCamposResumido()){
					boolean ok = false;
					
					if(petmascotahomenajenuevo.getCottipoidentificacion() == null ||  petmascotahomenajenuevo.getCottipoidentificacion().getIdtipoidentificacion() == 0){
						petmascotahomenajenuevo.setCottipoidentificacion(null);
					}
					
					PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
					
					ok = petordenservicioBO.grabarMascotaBasico(petmascotahomenajenuevo, cotpersonanuevo);
					
					if(ok){
						new MessageUtil().showInfoMessage("Mascota ingresada con exito!","");
					}
				}
			}else{
				new MessageUtil().showWarnMessage("Mascota ya ha sido Ingresada!","");
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private boolean validarCamposResumido()
	{
		boolean ok = true;
		
		if(petmascotahomenajenuevo.getIdmascotaveterinaria() == null || petmascotahomenajenuevo.getIdmascotaveterinaria().trim().length() == 0){
			new MessageUtil().showWarnMessage("Datos incompletos! El código de la veterinaria es obligatorio!","");
			ok = false;
		}else{
			if(petmascotahomenajenuevo.getNombre() == null || petmascotahomenajenuevo.getNombre().trim().length() == 0){
				new MessageUtil().showWarnMessage("Datos incompletos! El nombre de la mascota es obligatorio!","");
				ok = false;
			}else{
				if(petmascotahomenajenuevo.getPetespecie().getIdespecie() == 0){
					new MessageUtil().showWarnMessage("Datos incompletos! La especie de la mascota es obligatorio!","");
					ok = false;
				}else{
					if(petmascotahomenajenuevo.getPetraza() == null || petmascotahomenajenuevo.getPetraza().getIdraza() == 0 ){
						new MessageUtil().showWarnMessage("Datos incompletos! La Raza es obligatoria!","");
						ok = false;
					}else{
						if(petmascotahomenajenuevo.getSexo() == 0){
							new MessageUtil().showWarnMessage("Datos incompletos! El sexo de la mascota es obligatorio!","");
							ok = false;
						}else{
							if(petmascotahomenajenuevo.getFechanacimiento() == null){
								new MessageUtil().showWarnMessage("Datos incompletos! Fecha de Nacimiento es obligatorio!","");
								ok = false;
							}else{
								if(petmascotahomenajenuevo.getFechafallecimiento() == null){
									new MessageUtil().showWarnMessage("Datos incompletos! Fecha de Fallecimiento es obligatorio!","");
									ok = false;
								}else{
									if(petmascotahomenajenuevo.getFechafallecimiento().before(petmascotahomenajenuevo.getFechanacimiento())){
										new MessageUtil().showWarnMessage("Atención! Fecha de Fallecimiento debe ser mayor a Fecha de Nacimiento!","");
										ok = false;
									}	
								}
							}
						}
					}
				}
			}
		}
		
		if(ok){
			if(cotpersonanuevo.getNombre1() == null || cotpersonanuevo.getNombre1().trim().length() == 0){
				new MessageUtil().showWarnMessage("Datos incompletos! El Primer Nombre del propietario es obligatorio!","");
				ok = false;
			}else{
				if(cotpersonanuevo.getApellido1() == null || cotpersonanuevo.getApellido1().trim().length() == 0){
					new MessageUtil().showWarnMessage("Datos incompletos! El Primer Apellido del propietario es obligatorio!","");
					ok = false;
				}else{
					if(cotpersonanuevo.getSexo() == 0){
						new MessageUtil().showWarnMessage("Datos incompletos! El sexo del propietario es obligatorio!","");
						ok = false;
					}else{
						if(cotpersonanuevo.getTelefono() == null || cotpersonanuevo.getTelefono().trim().length() == 0 ){
							new MessageUtil().showWarnMessage("Datos incompletos! El telefono del propietario es obligatorio!","");
							ok = false;
						}
					}
				}
			}
		}
		
		return ok;
	}
	
	public void limpiarEditorMascotaResumido(){
		petmascotahomenajenuevo = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);
		cotpersonanuevo = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null); 
	}

}
