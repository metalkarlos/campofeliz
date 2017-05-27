package com.web.pet.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.web.pet.bo.CotformapagoBO;
import com.web.pet.bo.CotlugarBO;
import com.web.pet.bo.CotpersonaBO;
import com.web.pet.bo.CottipolugarBO;
import com.web.pet.bo.PetespecieBO;
import com.web.pet.bo.PetformapagoordenBO;
import com.web.pet.bo.PetmascotaBO;
import com.web.pet.bo.PetmascotacolorBO;
import com.web.pet.bo.PetordenservicioBO;
import com.web.pet.bo.PetordenserviciodetalleBO;
import com.web.pet.bo.PetrazaBO;
import com.web.pet.bo.PetservicioBO;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Cottipolugar;
import com.web.pet.pojo.annotations.Petservicio;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cotlugar;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Petformapagoorden;
import com.web.pet.pojo.annotations.PetformapagoordenId;
import com.web.pet.pojo.annotations.Cotoficina;
import com.web.pet.pojo.annotations.Cotempresa;
import com.web.pet.pojo.annotations.Cotestadopago;
import com.web.pet.pojo.annotations.Cotformapago;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.pet.pojo.annotations.PetordenservicioId;
import com.web.pet.pojo.annotations.Petordenserviciodetalle;
import com.web.pet.pojo.annotations.PetordenserviciodetalleId;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;
import com.web.util.Utilities;

@ManagedBean
@ViewScoped
public class OrdenServicioBean implements Serializable {
	
	//Orden de servicio
	private static final long serialVersionUID = -6374223940126173373L;
	private int idordenservicio;
	private int idanio;
	private Petordenservicio petordenservicio;
	private Petordenservicio petordenservicioClon;
	private Cottipolugar cottipolugar;
	private List<Cottipolugar> lisCottipolugar;
	private List<Cotlugar> lisCotlugar;
	private List<Petmascotacolor> lisPetmascotacolor;
	private Petmascotahomenaje petmascotahomenajeSeleccionar;

	//Editor de Mascotas
	private Petmascotahomenaje petmascotahomenajeEditorSeleccionar;
	private Petmascotahomenaje petmascotahomenajeEditor;
	private Petmascotahomenaje petmascotahomenajeEditorClon;
	private Cotpersona cotpersonaEditorSeleccionar;
	private Cotpersona cotpersonaEditor;
	private Cotpersona cotpersonaEditorClon;
	private Cotpersona cotpersonabusqueda;
	private List<Petespecie> lisPetespecie;
	private List<Petraza> lisRaza;
		
	//Seccion de Servicios
	private List<Petordenserviciodetalle> lisPetordenserviciodetalle;
	private List<Petordenserviciodetalle> lisPetordenserviciodetalleClon;
	private Petordenserviciodetalle petordenserviciodetalleSeleccionado;
	private Petordenserviciodetalle petordenserviciodetalleItem;
	private List<Petservicio> lisPetservicio;
	
	//Seccion de Formas de Pago
	private List<Petformapagoorden> lisPetformapagoorden;
	private List<Petformapagoorden> lisPetformapagoordenClon;
	private Petformapagoorden petformapagoordenSeleccionado;
	private Petformapagoorden petformapagoordenItem;
	private List<Cotformapago> lisCotformapago;
	
	public OrdenServicioBean() {
		//Orden de servicio
		petordenservicio = new Petordenservicio(new PetordenservicioId(0, 0), new Petmascotahomenaje(), new Setestado(), new Cotlugar(), null, new Date(), null, null, null, null, new Date(), new Date() , new Cotestadopago(), new BigDecimal(0), new BigDecimal(0));
		petordenservicioClon = new Petordenservicio(new PetordenservicioId(0, 0), new Petmascotahomenaje(), new Setestado(), new Cotlugar(), null, null, null, null, null, null, null, null, new Cotestadopago(), new BigDecimal(0), new BigDecimal(0));
		cottipolugar = new Cottipolugar(0, null, null, null, new Setestado(), new Setusuario());
		lisPetmascotacolor = new ArrayList<Petmascotacolor>();
		petmascotahomenajeSeleccionar = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);
		llenarListaTipoLugar();
		
		//Editor de Mascotas
		petmascotahomenajeEditorSeleccionar = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);
		petmascotahomenajeEditor = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);
		petmascotahomenajeEditorClon = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);
		cotpersonaEditorSeleccionar = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
		cotpersonaEditor = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
		cotpersonaEditorClon = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
		cotpersonabusqueda = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
		inicializarEspecieMascota();
		
		//Seccion de Servicios
		lisPetordenserviciodetalle = new ArrayList<Petordenserviciodetalle>();
		lisPetordenserviciodetalleClon = new ArrayList<Petordenserviciodetalle>();
		petordenserviciodetalleSeleccionado = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null, null, 0, new BigDecimal(0));
		petordenserviciodetalleSeleccionado.setPetservicio(new Petservicio(0, new Setestado(), null, new Setusuario(), null, null, new Cotoficina(), new Cotempresa(), null, null, null, false, null, null, 0, new BigDecimal(0)));
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null, null, 0, new BigDecimal(0));
		petordenserviciodetalleItem.setPetservicio(new Petservicio(0, new Setestado(), null, new Setusuario(), null, null, new Cotoficina(), new Cotempresa(), null, null, null, false, null, null, 0, new BigDecimal(0)));
		//llenarListaServicio();
		
		//Seccion de Formas de Pago
		lisPetformapagoorden = new ArrayList<Petformapagoorden>();
		lisPetformapagoordenClon = new ArrayList<Petformapagoorden>();
		petformapagoordenSeleccionado = new Petformapagoorden(new PetformapagoordenId(), new Cotformapago(), new Petordenservicio(), new Setestado(), new Setusuario(), new BigDecimal(0), null, null, null, null, null);
		petformapagoordenItem = new Petformapagoorden(new PetformapagoordenId(), new Cotformapago(), new Petordenservicio(), new Setestado(), new Setusuario(), new BigDecimal(0), null, null, null, null, null);
		lisCotformapago = new ArrayList<Cotformapago>();
	}
	
	@PostConstruct
	public void PostOrdenServicioBean(){
		FacesUtil facesUtil = new FacesUtil();
		
		try{
			Object par = facesUtil.getParametroUrl("idordenservicio");
			Object par2 = facesUtil.getParametroUrl("idanio");
			
			if(par != null && par2 != null){
				idordenservicio = Integer.parseInt(par.toString());
				idanio = Integer.parseInt(par2.toString());
				
				if(idordenservicio > 0 && idanio > 0){
					PetordenservicioId petordenservicioId = new PetordenservicioId();
					petordenservicioId.setIdordenservicio(idordenservicio);
					petordenservicioId.setIdanio(idanio);
					
					PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
					petordenservicio = petordenservicioBO.getPetordenservicioById(petordenservicioId);
					
					if(petordenservicio != null){
						petordenservicioClon = petordenservicio.clonar();
						
						if(petordenservicio != null && petordenservicio.getId() != null && petordenservicio.getId().getIdordenservicio() > 0 ){
							PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
							lisPetordenserviciodetalle = petordenserviciodetalleBO.lisPetordenserviciodetalle(petordenservicioId);
							
							if(lisPetordenserviciodetalle != null && lisPetordenserviciodetalle.size() > 0){
								lisPetordenserviciodetalleClon = new ArrayList<Petordenserviciodetalle>(lisPetordenserviciodetalle);
							}
							
							PetformapagoordenBO petformapagoordenBO = new PetformapagoordenBO();
							lisPetformapagoorden = petformapagoordenBO.lisPetformapagoorden(petordenservicioId);
							
							if(lisPetformapagoorden != null && lisPetformapagoorden.size() > 0){
								lisPetformapagoordenClon = new ArrayList<Petformapagoorden>(lisPetformapagoorden);
							}
							
							if(petordenservicio.getCotlugar() != null && petordenservicio.getCotlugar().getIdlugar() > 0){
								cottipolugar.setIdtipolugar(petordenservicio.getCotlugar().getCottipolugar().getIdtipolugar());
								llenarListaLugar();
							}
							
							if(petordenservicio.getCotestadopago() == null){
								petordenservicio.setCotestadopago(new Cotestadopago(0, null, null));
								petordenservicioClon.setCotestadopago(new Cotestadopago(0, null, null));
							}
						}
						
						lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(petordenservicio.getPetmascotahomenaje().getIdmascota());
						if(lisPetmascotacolor == null){
							lisPetmascotacolor = new ArrayList<Petmascotacolor>();
						}
					}else{
						petordenservicio = new Petordenservicio(new PetordenservicioId(0, 0), new Petmascotahomenaje(), new Setestado(), new Cotlugar(), null, new Date(), null, null, null, null, new Date(), new Date(), new Cotestadopago(), new BigDecimal(0), new BigDecimal(0));
					}
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
	
	private void llenarListaServicio(){
		try{
			Petservicio petservicio = new Petservicio();
			petservicio.setIdservicio(0);
			petservicio.setNombre("Seleccione");
			petservicio.setSetestado(new Setestado());
			petservicio.setSetusuario(new Setusuario());
			petservicio.setCotempresa(new Cotempresa());
			petservicio.setCotoficina(new Cotoficina());
			
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
	
	private void llenarListaFormasPago(){
		try{
			Cotformapago cotformapago = new Cotformapago();
			cotformapago.setIdformapago(0);
			cotformapago.setNombre("Seleccione");
			cotformapago.setSetestado(new Setestado());
			cotformapago.setSetusuario(new Setusuario());
			
			lisCotformapago = new ArrayList<Cotformapago>();
			lisCotformapago.add(cotformapago);
			
			CotformapagoBO cotformapagoBO = new CotformapagoBO();
			List<Cotformapago> lisTmp = cotformapagoBO.lisCotformapago();
			if(lisTmp != null && lisTmp.size() > 0){
				lisCotformapago.addAll(lisTmp);
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
	
	private void llenarListaTipoLugar(){
		try
		{
			CottipolugarBO cottipolugarBO = new CottipolugarBO();
			lisCottipolugar = cottipolugarBO.lisCottipolugar();
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void llenarListaLugar(){
		try{
			lisCotlugar = new ArrayList<Cotlugar>();
			
			CotlugarBO cotlugarBO = new CotlugarBO();
			List<Cotlugar> lisTmp = cotlugarBO.lisCotlugarByTipoLugar(this.cottipolugar.getIdtipolugar());
			if(lisTmp != null & lisTmp.size() > 0){
				lisCotlugar.addAll(lisTmp);
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void llenarLisRaza(){
		try{
			lisRaza = new ArrayList<Petraza>();
			
			PetrazaBO petrazaBO = new PetrazaBO();
			List<Petraza> lisTmp = petrazaBO.lisRazas(petmascotahomenajeEditor.getPetespecie().getIdespecie());
			if(lisTmp != null & lisTmp.size() > 0){
				lisRaza.addAll(lisTmp);
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public List<Petmascotahomenaje> buscarMascotas(String query) {
		List<Petmascotahomenaje> lisPetmascotahomenaje = new ArrayList<Petmascotahomenaje>();
		
		try{
			PetmascotaBO petmascotaBO = new PetmascotaBO();
			int args[] = {0};
			lisPetmascotahomenaje = petmascotaBO.lisPetmascotahomenajeByPage(query, 20, 0, args);
			
			for(Petmascotahomenaje petmascotahomenaje : lisPetmascotahomenaje){
				if(petmascotahomenaje.getPetraza() == null){
					petmascotahomenaje.setPetraza(new Petraza());
				}
			}
			
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
			
		return lisPetmascotahomenaje;
	}
	
	public List<Cotpersona> buscarPropietarios(String query) {
		List<Cotpersona> lisPropietarios = new ArrayList<Cotpersona>();
		
		if(query != null && query.trim().length() > 0){
			String[] nombres = null;
			nombres = query.split(" ");
			
			CotpersonaBO cotpersonaBO = new CotpersonaBO();
			int args[] = {0};
			List<Cotpersona> lisCotpersona = cotpersonaBO.lisCotpersonaByPage(nombres, 20, 0, args);
			
			if(lisCotpersona != null && lisCotpersona.size() > 0){
				for(Cotpersona cotpersona : lisCotpersona){
					lisPropietarios.add(cotpersona);
				}
			}
		}
		
		return lisPropietarios;
	}
	
	public void seleccionarPropietarioEditor() {
		try{
			cotpersonaEditor = cotpersonaEditorSeleccionar.clonar();
			cotpersonaEditorClon = cotpersonaEditorSeleccionar.clonar();
			cotpersonaEditorSeleccionar = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void seleccionarMascota(){
		try{
			petordenservicio.setPetmascotahomenaje(petmascotahomenajeSeleccionar.clonar());
			petmascotahomenajeSeleccionar = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);
			lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(petordenservicio.getPetmascotahomenaje().getIdmascota());
			if(lisPetmascotacolor == null){
				lisPetmascotacolor = new ArrayList<Petmascotacolor>();
			}
		}
		catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void seleccionarMascotaEditor() {
		try{
			if(petmascotahomenajeEditorSeleccionar != null){
				petmascotahomenajeEditor = petmascotahomenajeEditorSeleccionar.clonar();
				petmascotahomenajeEditorSeleccionar = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);

				if(petmascotahomenajeEditor.getCotpersona() != null && petmascotahomenajeEditor.getCotpersona().getIdpersona() > 0){
					cotpersonaEditor = petmascotahomenajeEditor.getCotpersona().clonar();
					cotpersonaEditorClon = petmascotahomenajeEditor.getCotpersona().clonar();
				}else{
					cotpersonaEditor = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
					cotpersonaEditorClon = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
				}
				if(petmascotahomenajeEditor.getPetraza() == null){
					petmascotahomenajeEditor.setPetraza(new Petraza());
				}
				if(petmascotahomenajeEditor.getPetespecie().getIdespecie() > 0){
					llenarLisRaza();
				}
				petmascotahomenajeEditorClon = petmascotahomenajeEditor.clonar();
			}
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void seleccionarDetalleServicios(){
		try{
			petordenserviciodetalleItem = petordenserviciodetalleSeleccionado.clonar();
			petordenserviciodetalleSeleccionado = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null, null, 0, new BigDecimal(0));
			petordenserviciodetalleSeleccionado.setPetservicio(new Petservicio(0, new Setestado(), null, new Setusuario(), null, null, new Cotoficina(), new Cotempresa(), null, null, null, false, null, null, 0, new BigDecimal(0)));
			llenarListaServicio();
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void seleccionarDetallePagos(){
		try{
			petformapagoordenItem = petformapagoordenSeleccionado.clonar();
			petformapagoordenSeleccionado = new Petformapagoorden(new PetformapagoordenId(), new Cotformapago(), new Petordenservicio(), new Setestado(), new Setusuario(), new BigDecimal(0), null, null, null, null, null);
			llenarListaFormasPago();
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void seleccionarComboServicio(){
		try{
			if(petordenserviciodetalleItem.getPetservicio() != null && petordenserviciodetalleItem.getPetservicio().getIdservicio() > 0){
				petordenserviciodetalleItem.setPrecio(petordenserviciodetalleItem.getPetservicio().getPrecio());
			}
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

	public void nuevoServicio(){
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(new PetordenservicioId(0,0), null, null, null), null, null, null, 0, new BigDecimal(0));
		llenarListaServicio();
	}
	
	public void nuevaFormaPago(){
		petformapagoordenItem = new Petformapagoorden(new PetformapagoordenId(), new Cotformapago(), new Petordenservicio(), new Setestado(), new Setusuario(), new BigDecimal(0), null, null, null, null, null);
		llenarListaFormasPago();
	}
	
	public void grabarServicio(){
		try{
			if(validarServicios()){
				int index = -1;
				for(Petordenserviciodetalle petordenserviciodetalle : lisPetordenserviciodetalle){
					if(petordenserviciodetalle.getPetservicio().getIdservicio() == petordenserviciodetalleItem.getPetservicio().getIdservicio()){
						index = lisPetordenserviciodetalle.indexOf(petordenserviciodetalle);
						break;
					}
				}
				
				//modificar
				if(index > -1){
					lisPetordenserviciodetalle.set(index, petordenserviciodetalleItem.clonar());
					petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(new PetordenservicioId(0,0), null, null, null), null, null, null, 0, new BigDecimal(0));
					lisPetservicio = new ArrayList<Petservicio>();
					calcularTotalServicios();
					
					RequestContext.getCurrentInstance().execute("varDialogEditorServicio.hide()");
				}else{
					//nuevo
					if(petordenserviciodetalleItem.getPetservicio().getIdservicio() > 0){
						lisPetordenserviciodetalle.add(petordenserviciodetalleItem.clonar());
						petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(new PetordenservicioId(0,0), null, null, null), null, null, null, 0, new BigDecimal(0));
						lisPetservicio = new ArrayList<Petservicio>();
						calcularTotalServicios();
						
						RequestContext.getCurrentInstance().execute("varDialogEditorServicio.hide()");
					}
				}
				
				//modificar
				/*if(petordenserviciodetalleItem.getId().getIdordenserviciodetalle() > 0){
					int index = -1;
					for(Petordenserviciodetalle petordenserviciodetalle : lisPetordenserviciodetalle){
						if(petordenserviciodetalle.getId().equals(petordenserviciodetalleItem.getId())){
							index = lisPetordenserviciodetalle.indexOf(petordenserviciodetalle);
							break;
						}
					}
					
					if(index > -1){
						lisPetordenserviciodetalle.set(index, petordenserviciodetalleItem.clonar());
						petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(new PetordenservicioId(0,0), null, null, null), null, null, null, 0, new BigDecimal(0));
						lisPetservicio = new ArrayList<Petservicio>();
						calcularTotalServicios();
						
						RequestContext.getCurrentInstance().execute("varDialogEditorServicio.hide()");
					}
				}else{
					//nuevo
					if(petordenserviciodetalleItem.getPetservicio().getIdservicio() > 0){
						lisPetordenserviciodetalle.add(petordenserviciodetalleItem.clonar());
						petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(new PetordenservicioId(0,0), null, null, null), null, null, null, 0, new BigDecimal(0));
						lisPetservicio = new ArrayList<Petservicio>();
						calcularTotalServicios();
						
						RequestContext.getCurrentInstance().execute("varDialogEditorServicio.hide()");
					}
				}*/
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void quitarServicio(){
		if(petordenserviciodetalleItem.getPetservicio().getIdservicio() > 0){
			int index = -1;
			for(Petordenserviciodetalle petordenserviciodetalle : lisPetordenserviciodetalle){
				if(petordenserviciodetalle.getPetservicio().getIdservicio() == petordenserviciodetalleItem.getPetservicio().getIdservicio()){
					index = lisPetordenserviciodetalle.indexOf(petordenserviciodetalle);
					break;
				}
			}
			if(index > -1){
				lisPetordenserviciodetalle.remove(index);
				calcularTotalServicios();
			}
		}
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(new PetordenservicioId(0,0), null, null, null), null, null, null, 0, new BigDecimal(0));
		lisPetservicio = new ArrayList<Petservicio>();
	}
	
	private void calcularTotalServicios(){
		BigDecimal preciototal = new BigDecimal(0);
		for(Petordenserviciodetalle petordenserviciodetalle : lisPetordenserviciodetalle){
			preciototal = preciototal.add(petordenserviciodetalle.getPrecio());
		}
		petordenservicio.setPreciototal(preciototal);
	}
	
	public void grabarPago(){
		try{
			if(validarPagos()){
				/*int index = -1;
				for(Petformapagoorden petformapagoorden : lisPetformapagoorden){
					if(petformapagoorden.getCotformapago().getIdformapago() == petformapagoordenItem.getCotformapago().getIdformapago()){
						index = lisPetformapagoorden.indexOf(petformapagoorden);
						break;
					}
				}
				
				//modificar
				if(index > -1){
					lisPetformapagoorden.set(index, petformapagoordenItem.clonar());
					petformapagoordenItem = new Petformapagoorden(new PetformapagoordenId(), new Cotformapago(), new Petordenservicio(), new Setestado(), new Setusuario(), new BigDecimal(0), null, null, null, null);
					lisCotformapago = new ArrayList<Cotformapago>();
					calcularTotalPagos();
					
					RequestContext.getCurrentInstance().execute("varDialogEditorFormaPago.hide()");
				}else{
					//nuevo
					if(petformapagoordenItem.getCotformapago().getIdformapago() > 0){
						lisPetformapagoorden.add(petformapagoordenItem.clonar());
						petformapagoordenItem = new Petformapagoorden(new PetformapagoordenId(), new Cotformapago(), new Petordenservicio(), new Setestado(), new Setusuario(), new BigDecimal(0), null, null, null, null);
						lisCotformapago = new ArrayList<Cotformapago>();
						calcularTotalPagos();
						
						RequestContext.getCurrentInstance().execute("varDialogEditorFormaPago.hide()");
					}
				}*/
				//modificar
				if(petformapagoordenItem.getId().getIdformapagoorden() > 0 || petformapagoordenItem.getCodigoUnico() != null){
					int index = -1;
					for(Petformapagoorden petformapagoorden : lisPetformapagoorden){
						if( (petformapagoordenItem.getId().getIdformapagoorden() > 0 && petformapagoorden.getId().equals(petformapagoordenItem.getId())) || 
							(petformapagoordenItem.getCodigoUnico() != null && petformapagoordenItem.getCodigoUnico().equals(petformapagoorden.getCodigoUnico())) ){
							index = lisPetformapagoorden.indexOf(petformapagoorden);
							break;
						}
					}
					
					if(index > -1){
						lisPetformapagoorden.set(index, petformapagoordenItem.clonar());
						petformapagoordenItem = new Petformapagoorden(new PetformapagoordenId(), new Cotformapago(), new Petordenservicio(), new Setestado(), new Setusuario(), new BigDecimal(0), null, null, null, null, null);
						lisCotformapago = new ArrayList<Cotformapago>();
						calcularTotalPagos();
						
						RequestContext.getCurrentInstance().execute("varDialogEditorFormaPago.hide()");
					}
				}else{
					//nuevo
					if(petformapagoordenItem.getCotformapago().getIdformapago() > 0){
						if(petformapagoordenItem.getCodigoUnico() == null || petformapagoordenItem.getCodigoUnico().length() == 0){
							Utilities utilities = new Utilities();
							String codigoUnico = utilities.generarAleatorio();
							petformapagoordenItem.setCodigoUnico(codigoUnico);
						}
						lisPetformapagoorden.add(petformapagoordenItem.clonar());
						petformapagoordenItem = new Petformapagoorden(new PetformapagoordenId(), new Cotformapago(), new Petordenservicio(), new Setestado(), new Setusuario(), new BigDecimal(0), null, null, null, null, null);
						lisCotformapago = new ArrayList<Cotformapago>();
						calcularTotalPagos();
						
						RequestContext.getCurrentInstance().execute("varDialogEditorFormaPago.hide()");
					}
				}
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private boolean validarPagos()
	{
		boolean ok = true;
		
		if(petformapagoordenItem.getCotformapago() == null || petformapagoordenItem.getCotformapago().getIdformapago() == 0){
			ok = false;
			new MessageUtil().showWarnMessage("Seleccione una Forma de Pago","");
		}else{
			for(Petformapagoorden petformapagoorden : lisPetformapagoorden){
				/*if(petformapagoorden.getCotformapago().getIdformapago() == petformapagoordenItem.getCotformapago().getIdformapago()){
					if(petformapagoorden.equals(petformapagoordenItem)){
						ok = false;
						new MessageUtil().showWarnMessage("Ningún cambio que agregar","");
						break;
					}
				}*/
				
				if( (petformapagoordenItem.getId().getIdformapagoorden() > 0 && petformapagoorden.getId().equals(petformapagoordenItem.getId())) || 
					(petformapagoordenItem.getCodigoUnico() != null && petformapagoordenItem.getCodigoUnico().equals(petformapagoorden.getCodigoUnico())) ){
					if(petformapagoorden.equals(petformapagoordenItem)){
						ok = false;
						new MessageUtil().showWarnMessage("Ningún cambio que agregar","");
						break;
					}
				}
			}
			
				
			
			//Pregunto si valor es mayor a cero
			if(ok && petformapagoordenItem.getValor().compareTo(new BigDecimal(0)) <= 0){
				ok = false;
				new MessageUtil().showWarnMessage("Debe especificar un valor","");
			}
		}

		return ok;
	}
	
	public void quitarPago(){
		if(petformapagoordenItem.getCotformapago().getIdformapago() > 0){
			int index = -1;
			for(Petformapagoorden petformapagoorden : lisPetformapagoorden){
				if(petformapagoorden.getCotformapago().getIdformapago() == petformapagoordenItem.getCotformapago().getIdformapago()){
					index = lisPetformapagoorden.indexOf(petformapagoorden);
					break;
				}
			}
			if(index > -1){
				lisPetformapagoorden.remove(index);
			}
		}
		petformapagoordenItem = new Petformapagoorden(new PetformapagoordenId(), new Cotformapago(), new Petordenservicio(), new Setestado(), new Setusuario(), new BigDecimal(0), null, null, null, null, null);
		lisCotformapago = new ArrayList<Cotformapago>();
	}
	
	private void calcularTotalPagos(){
		BigDecimal pagototal = new BigDecimal(0);
		for(Petformapagoorden petformapagoorden : lisPetformapagoorden){
			pagototal = pagototal.add(petformapagoorden.getValor());
		}
		petordenservicio.setPagototal(pagototal);
	}
	
	public void limpiarVentanaServicio(){
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(new PetordenservicioId(0,0), null, null, null), null, null, null, 0, new BigDecimal(0));
		lisPetservicio = new ArrayList<Petservicio>();
	}
	
	public void limpiarVentanaPagos(){
		petformapagoordenItem = new Petformapagoorden(new PetformapagoordenId(), new Cotformapago(), new Petordenservicio(), new Setestado(), new Setusuario(), new BigDecimal(0), null, null, null, null, null);
		lisCotformapago = new ArrayList<Cotformapago>();
	}
	
	private boolean validarServicios()
	{
		boolean ok = true;
		
		if(petordenserviciodetalleItem.getPetservicio() == null || petordenserviciodetalleItem.getPetservicio().getIdservicio() == 0){
			ok = false;
			new MessageUtil().showWarnMessage("Seleccione un Servicio","");
		}else{
			for(Petordenserviciodetalle petordenserviciodetalle : lisPetordenserviciodetalle){
				if(petordenserviciodetalle.getPetservicio().getIdservicio() == petordenserviciodetalleItem.getPetservicio().getIdservicio()){
					if(!petordenserviciodetalle.getId().equals(petordenserviciodetalleItem.getId())){
						ok = false;
						new MessageUtil().showWarnMessage("Servicio repetido","");
						break;
					}else{
						if(petordenserviciodetalle.equals(petordenserviciodetalleItem)){
							ok = false;
							new MessageUtil().showWarnMessage("Ningún cambio que agregar","");
							break;
						}
					}
				}
			}
			
			//Pregunto si precio es mayor a cero
			/*if(ok && petordenserviciodetalleItem.getPrecio().compareTo(new BigDecimal(0)) <= 0){
				ok = false;
				new MessageUtil().showWarnMessage("Debe especificar un precio","");
			}*/
		}

		return ok;
	}
	
	public void guardar(){
		
		if(validarObligatorios()){
			try{
				boolean ok = false;
				
				PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
				
				if(petordenservicio.getCotlugar() == null ||  petordenservicio.getCotlugar().getIdlugar() == 0){
					petordenservicio.setCotlugar(null);
					petordenservicioClon.setCotlugar(null);
				}
				
				/*if(petordenservicio.getCotestadopago() == null ||  petordenservicio.getCotestadopago().getIdestadopago() == 0){
					petordenservicio.setCotestadopago(null);
					petordenservicioClon.setCotestadopago(null);
				}*/
				
				Cotestadopago cotestadopago = new Cotestadopago();
				if(petordenservicio.getPagototal().compareTo(petordenservicio.getPreciototal()) >= 0){
					cotestadopago.setIdestadopago(Parametro.ESTADO_PAGO_CANCELADO);
				}else{
					cotestadopago.setIdestadopago(Parametro.ESTADO_PAGO_PORCANCELAR);
				}
				petordenservicio.setCotestadopago(cotestadopago);
				
				if(petordenservicio.getId().getIdordenservicio() == 0){
					ok = petordenservicioBO.ingresarPetordenservicio(petordenservicio, lisPetordenserviciodetalle, lisPetformapagoorden);
					if(ok){
						Utilities utilities = new Utilities();
						utilities.mostrarPaginaMensaje("Orden de Servicio ingresada con exito!!");
					}else{
						new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
					}
				}else{
					Utilities utilities = new Utilities();
					ok = petordenservicioBO.modificarPetordenservicio(petordenservicio, petordenservicioClon, lisPetordenserviciodetalle, lisPetordenserviciodetalleClon, lisPetformapagoorden, lisPetformapagoordenClon);
					if(ok){
						utilities.mostrarPaginaMensaje("Orden de Servicio actualizada con exito!!");
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
		
		if(petordenservicio.getFechaemision() == null){
			new MessageUtil().showWarnMessage("Especifique la Fecha de Emisión.","");
			ok = false;
		}else{
			if(petordenservicio.getFechaentierro() == null){
				new MessageUtil().showWarnMessage("Especifique la Fecha de Entierro/Cremación.","");
				ok = false;
			}else{ 
				if(petordenservicio.getFechaentierro() != null && petordenservicio.getPetmascotahomenaje().getFechafallecimiento() != null && (petordenservicio.getFechaentierro().before(petordenservicio.getPetmascotahomenaje().getFechafallecimiento()) )){
					new MessageUtil().showWarnMessage("Fecha de Entierro/Cremación debe ser mayor a Fecha de Fallecimiento de la mascota.","");
					ok = false;
				}else{
					if(petordenservicio.getPetmascotahomenaje() == null || petordenservicio.getPetmascotahomenaje().getIdmascota() == 0){
						new MessageUtil().showWarnMessage("Escriba el Nombre de la Mascota.","");
						ok = false;
					}else{
						if(lisPetordenserviciodetalle == null || lisPetordenserviciodetalle.size() == 0){
							new MessageUtil().showWarnMessage("Debe elegir al menos un servicio.","");
							ok = false;
						}
					}
				}
			}
		}
		
		return ok;
	}
	
	public void eliminar(){
		try{
			PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
			Utilities utilities = new Utilities();
			
			boolean ok = petordenservicioBO.eliminarPetordenservicio(petordenservicio,lisPetordenserviciodetalle,lisPetformapagoorden);
			
			if(ok){
				utilities.mostrarPaginaMensaje("Orden de Servicio eliminada con exito!!");
			}else{
				new MessageUtil().showInfoMessage("No se ha podido eliminar Orden de Servicio.", "");
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void imprimir(){
		try {
			if(lisPetordenserviciodetalle != null && lisPetordenserviciodetalle.size() > 0){
				FacesUtil facesUtil = new FacesUtil();
				
				VisorBean visorBean = (VisorBean)facesUtil.getSessionBean("visorBean");
				visorBean.setNombreReporte("OrdenServicio");
				visorBean.getParametros().put("P_IDORDENSERVICIO", petordenservicio.getId().getIdordenservicio());
				visorBean.getParametros().put("P_IDANIO", petordenservicio.getId().getIdanio());
				
				RequestContext.getCurrentInstance().execute("window.open('visor_reportes.jsf')");
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
			if(validarCamposResumido()){
				boolean ok = false;
				
				if(petmascotahomenajeEditor.getCottipoidentificacion() == null ||  petmascotahomenajeEditor.getCottipoidentificacion().getIdtipoidentificacion() == 0){
					petmascotahomenajeEditor.setCottipoidentificacion(null);
					petmascotahomenajeEditorClon.setCottipoidentificacion(null);
				}
				
				PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
				
				ok = petordenservicioBO.grabarMascotaBasico(petmascotahomenajeEditor, petmascotahomenajeEditorClon, cotpersonaEditor, cotpersonaEditorClon);
				
				if(ok){
					PetmascotaBO petmascotaBO = new PetmascotaBO();
					petordenservicio.setPetmascotahomenaje(petmascotaBO.getPetmascotaById(petmascotahomenajeEditor.getIdmascota()));
					limpiarEditorMascotaResumido();
					
					RequestContext.getCurrentInstance().execute("vardialogeditormascota.hide()");
				}else{
					new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
				}
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private boolean validarCamposResumido()
	{
		boolean ok = true;
		
		if(petmascotahomenajeEditor.getNombre() == null || petmascotahomenajeEditor.getNombre().trim().length() == 0){
			new MessageUtil().showWarnMessage("Datos incompletos! El nombre de la mascota es obligatorio!","");
			ok = false;
		}else{
			if(petmascotahomenajeEditor.getPetespecie().getIdespecie() == 0){
				new MessageUtil().showWarnMessage("Datos incompletos! La especie de la mascota es obligatorio!","");
				ok = false;
			}else{
				if(petmascotahomenajeEditor.getPetraza() == null || petmascotahomenajeEditor.getPetraza().getIdraza() == 0 ){
					new MessageUtil().showWarnMessage("Datos incompletos! La Raza es obligatoria!","");
					ok = false;
				}else{
					if(petmascotahomenajeEditor.getSexo() == 0){
						new MessageUtil().showWarnMessage("Datos incompletos! El sexo de la mascota es obligatorio!","");
						ok = false;
					}else{
						if(petmascotahomenajeEditor.getFechafallecimiento() == null){
							new MessageUtil().showWarnMessage("Datos incompletos! Fecha de Fallecimiento es obligatorio!","");
							ok = false;
						}else{
							if(petmascotahomenajeEditor.getFechanacimiento() != null && petmascotahomenajeEditor.getFechafallecimiento().before(petmascotahomenajeEditor.getFechanacimiento())){
								new MessageUtil().showWarnMessage("Atención! Fecha de Fallecimiento debe ser mayor a Fecha de Nacimiento!","");
								ok = false;
							}else{
								if(cotpersonaEditor.getNombre1() == null || cotpersonaEditor.getNombre1().trim().length() == 0){
									new MessageUtil().showWarnMessage("Datos incompletos! El Primer Nombre es obligatorio!","");
									ok = false;
								}else{
									if(cotpersonaEditor.getApellido1() == null || cotpersonaEditor.getApellido1().trim().length() == 0){
										new MessageUtil().showWarnMessage("Datos incompletos! El Primer Apellido es obligatorio!","");
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
			if(cotpersonaEditor.getNombre1() == null || cotpersonaEditor.getNombre1().trim().length() == 0){
				new MessageUtil().showWarnMessage("Datos incompletos! El Primer Nombre del propietario es obligatorio!","");
				ok = false;
			}else{
				if(cotpersonaEditor.getApellido1() == null || cotpersonaEditor.getApellido1().trim().length() == 0){
					new MessageUtil().showWarnMessage("Datos incompletos! El Primer Apellido del propietario es obligatorio!","");
					ok = false;
				}else{
					if(cotpersonaEditor.getSexo() == 0){
						new MessageUtil().showWarnMessage("Datos incompletos! El sexo del propietario es obligatorio!","");
						ok = false;
					}
				}
			}
		}
		
		return ok;
	}
	
	public void limpiarEditorMascotaResumido(){
		petmascotahomenajeEditor = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);
		petmascotahomenajeEditorClon = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);
		cotpersonaEditor = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
		cotpersonaEditorClon = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
		cotpersonabusqueda = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
	}
	
	public int getIdordenservicio() {
		return idordenservicio;
	}

	public void setIdordenservicio(int idordenservicio) {
		this.idordenservicio = idordenservicio;
	}

	public int getIdanio() {
		return idanio;
	}

	public void setIdanio(int idanio) {
		this.idanio = idanio;
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

	public Petordenserviciodetalle getPetordenserviciodetalleItem() {
		return petordenserviciodetalleItem;
	}

	public void setPetordenserviciodetalleItem(
			Petordenserviciodetalle petordenserviciodetalleItem) {
		this.petordenserviciodetalleItem = petordenserviciodetalleItem;
		//this.petordenserviciodetalleItem = petordenserviciodetalleItem == null ? new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null, null, 0, new BigDecimal(0)) : petordenserviciodetalleItem;
		//petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(new PetordenservicioId(0,0), null, null, null), null, null, null, 0, new BigDecimal(0));
	}

	public List<Petordenserviciodetalle> getLisPetordenserviciodetalle() {
		return lisPetordenserviciodetalle;
	}

	public void setLisPetordenserviciodetalle(
			List<Petordenserviciodetalle> lisPetordenserviciodetalle) {
		this.lisPetordenserviciodetalle = lisPetordenserviciodetalle;
	}

	public List<Petordenserviciodetalle> getLisPetordenserviciodetalleClon() {
		return lisPetordenserviciodetalleClon;
	}

	public void setLisPetordenserviciodetalleClon(
			List<Petordenserviciodetalle> lisPetordenserviciodetalleClon) {
		this.lisPetordenserviciodetalleClon = lisPetordenserviciodetalleClon;
	}

	public List<Cottipolugar> getLisCottipolugar() {
		return lisCottipolugar;
	}

	public void setLisCottipolugar(List<Cottipolugar> lisCottipolugar) {
		this.lisCottipolugar = lisCottipolugar;
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

	public Petmascotahomenaje getPetmascotahomenajeEditor() {
		return petmascotahomenajeEditor;
	}

	public void setPetmascotahomenajeEditor(
			Petmascotahomenaje petmascotahomenajeEditor) {
		this.petmascotahomenajeEditor = petmascotahomenajeEditor;
	}

	public Petmascotahomenaje getPetmascotahomenajeEditorClon() {
		return petmascotahomenajeEditorClon;
	}

	public void setPetmascotahomenajeEditorClon(
			Petmascotahomenaje petmascotahomenajeEditorClon) {
		this.petmascotahomenajeEditorClon = petmascotahomenajeEditorClon;
	}

	public List<Petespecie> getLisPetespecie() {
		return lisPetespecie;
	}

	public void setLisPetespecie(List<Petespecie> lisPetespecie) {
		this.lisPetespecie = lisPetespecie;
	}

	public Cotpersona getCotpersonaEditor() {
		return cotpersonaEditor;
	}

	public void setCotpersonaEditor(Cotpersona cotpersonaEditor) {
		this.cotpersonaEditor = cotpersonaEditor;
	}

	public Cotpersona getCotpersonaEditorClon() {
		return cotpersonaEditorClon;
	}

	public void setCotpersonaEditorClon(Cotpersona cotpersonaEditorClon) {
		this.cotpersonaEditorClon = cotpersonaEditorClon;
	}

	public List<Petraza> getLisRaza() {
		return lisRaza;
	}

	public void setLisRaza(List<Petraza> lisRaza) {
		this.lisRaza = lisRaza;
	}

	public Cottipolugar getCottipolugar() {
		return cottipolugar;
	}

	public void setCottipolugar(Cottipolugar cottipolugar) {
		this.cottipolugar = cottipolugar;
	}

	public Cotpersona getCotpersonabusqueda() {
		return cotpersonabusqueda;
	}

	public void setCotpersonabusqueda(Cotpersona cotpersonabusqueda) {
		this.cotpersonabusqueda = cotpersonabusqueda;
	}

	public Petordenserviciodetalle getPetordenserviciodetalleSeleccionado() {
		return petordenserviciodetalleSeleccionado;
	}

	public void setPetordenserviciodetalleSeleccionado(Petordenserviciodetalle petordenserviciodetalleSeleccionado) {
		this.petordenserviciodetalleSeleccionado = petordenserviciodetalleSeleccionado;
	}

	public List<Petformapagoorden> getLisPetformapagoorden() {
		return lisPetformapagoorden;
	}

	public void setLisPetformapagoorden(List<Petformapagoorden> lisPetformapagoorden) {
		this.lisPetformapagoorden = lisPetformapagoorden;
	}

	public List<Petformapagoorden> getLisPetformapagoordenClon() {
		return lisPetformapagoordenClon;
	}

	public void setLisPetformapagoordenClon(List<Petformapagoorden> lisPetformapagoordenClon) {
		this.lisPetformapagoordenClon = lisPetformapagoordenClon;
	}

	public Petformapagoorden getPetformapagoordenSeleccionado() {
		return petformapagoordenSeleccionado;
	}

	public void setPetformapagoordenSeleccionado(Petformapagoorden petformapagoordenSeleccionado) {
		this.petformapagoordenSeleccionado = petformapagoordenSeleccionado;
	}

	public Petformapagoorden getPetformapagoordenItem() {
		return petformapagoordenItem;
	}

	public void setPetformapagoordenItem(Petformapagoorden petformapagoordenItem) {
		this.petformapagoordenItem = petformapagoordenItem;
	}

	public List<Cotformapago> getLisCotformapago() {
		return lisCotformapago;
	}

	public void setLisCotformapago(List<Cotformapago> lisCotformapago) {
		this.lisCotformapago = lisCotformapago;
	}

	public Petmascotahomenaje getPetmascotahomenajeSeleccionar() {
		return petmascotahomenajeSeleccionar;
	}

	public void setPetmascotahomenajeSeleccionar(Petmascotahomenaje petmascotahomenajeSeleccionar) {
		this.petmascotahomenajeSeleccionar = petmascotahomenajeSeleccionar;
	}

	public Petmascotahomenaje getPetmascotahomenajeEditorSeleccionar() {
		return petmascotahomenajeEditorSeleccionar;
	}

	public void setPetmascotahomenajeEditorSeleccionar(Petmascotahomenaje petmascotahomenajeEditorSeleccionar) {
		this.petmascotahomenajeEditorSeleccionar = petmascotahomenajeEditorSeleccionar;
	}

	public Cotpersona getCotpersonaEditorSeleccionar() {
		return cotpersonaEditorSeleccionar;
	}

	public void setCotpersonaEditorSeleccionar(Cotpersona cotpersonaEditorSeleccionar) {
		this.cotpersonaEditorSeleccionar = cotpersonaEditorSeleccionar;
	}

}
