package com.web.pet.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.web.pet.bo.CotlugarBO;
import com.web.pet.bo.CottipolugarBO;
import com.web.pet.bo.PetespecieBO;
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
import com.web.pet.pojo.annotations.Cotoficina;
import com.web.pet.pojo.annotations.Cotempresa;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.pet.pojo.annotations.PetordenservicioId;
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
	private int idanio;
	private Petordenservicio petordenservicio;
	private Petordenservicio petordenservicioClon;
	private Petordenserviciodetalle petordenserviciodetalleItem;
	private LazyDataModel<Petordenserviciodetalle> lisPetordenserviciodetalleModel;
	private List<Petordenserviciodetalle> lisPetordenserviciodetalle;
	private List<Petordenserviciodetalle> lisPetordenserviciodetalleClon;
	private List<Cottipolugar> lisCottipolugar;
	private List<Cotlugar> lisCotlugar;
	private List<Petmascotacolor> lisPetmascotacolor;
	private List<Petservicio> lisPetservicio;
	private Petmascotahomenaje petmascotahomenajenuevo; 
	private Cotpersona cotpersonanuevo; 
	private List<Petespecie> lisPetespecie;
	private List<Petraza> lisRaza;
	private String linkReporte;
	private Cottipolugar cottipolugar;

	public OrdenServicioBean() {
		petordenservicio = new Petordenservicio(new PetordenservicioId(0, 0), new Petmascotahomenaje(), new Setestado(), new Cotlugar(), null, null, null, null, null, null, null, new Date() , null);
		petordenservicioClon = new Petordenservicio(new PetordenservicioId(0, 0), new Petmascotahomenaje(), new Setestado(), new Cotlugar(), null, null, null, null, null, null, null, null, null);
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null, null);
		petordenserviciodetalleItem.setPetservicio(new Petservicio(0, new Setestado(), null, new Setusuario(), null, null, new Cotoficina(), new Cotempresa(), null, null, null, false, null, null, 0));
		lisPetmascotacolor = new ArrayList<Petmascotacolor>();
		petmascotahomenajenuevo = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),1,new BigDecimal(0),null,false,false,null);
		cotpersonanuevo = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
		lisPetordenserviciodetalle = new ArrayList<Petordenserviciodetalle>();
		lisPetordenserviciodetalleClon = new ArrayList<Petordenserviciodetalle>();
		cottipolugar = new Cottipolugar(0, null, null, null, new Setestado(), new Setusuario());
		
		llenarListaTipoLugar();
		//llenarListaLugar();
		llenarListaServicio();
		inicializarEspecieMascota();
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
							lisPetordenserviciodetalle = petordenserviciodetalleBO.lisPetordenserviciodetalle(petordenservicio.getId());
							
							if(lisPetordenserviciodetalle != null && lisPetordenserviciodetalle.size() > 0){
								lisPetordenserviciodetalleClon = new ArrayList<Petordenserviciodetalle>(lisPetordenserviciodetalle);
							}
							
							if(petordenservicio.getCotlugar() != null && petordenservicio.getCotlugar().getIdlugar() > 0){
								cottipolugar.setIdtipolugar(petordenservicio.getCotlugar().getCottipolugar().getIdtipolugar());
								llenarListaLugar();
							}
						}
						
						/*if(petordenservicio.getCotlugar() == null){
							petordenservicio.setCotlugar(new Cotlugar());
							//petordenservicioClon.setCotlugar(new Cotlugar());
						}*/

						lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(petordenservicio.getPetmascotahomenaje().getIdmascota());
						if(lisPetmascotacolor == null){
							lisPetmascotacolor = new ArrayList<Petmascotacolor>();
						}
					}else{
						petordenservicio = new Petordenservicio(new PetordenservicioId(0, 0), new Petmascotahomenaje(), new Setestado(), new Cotlugar(), null, null, null, null, null, null, null, new Date(), null);
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
	
	/*private void llenarListaLugar(){
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
	}*/
	
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
			/*Petraza petraza = new Petraza();
			petraza.setNombre("Seleccione");
			petraza.setSetestado(new Setestado());
			petraza.setSetusuario(new Setusuario());*/
			
			lisRaza = new ArrayList<Petraza>();
			//lisRaza.add(petraza);
			
			PetrazaBO petrazaBO = new PetrazaBO();
			List<Petraza> lisTmp = petrazaBO.lisRazas(petmascotahomenajenuevo.getPetespecie().getIdespecie());
			if(lisTmp != null & lisTmp.size() > 0){
				lisRaza.addAll(lisTmp);
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	/*@SuppressWarnings("serial")
	private void consultarDetalle(){
		try
		{
			lisPetordenserviciodetalleModel = new LazyDataModel<Petordenserviciodetalle>() {
				public List<Petordenserviciodetalle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					lisPetordenserviciodetalle = new ArrayList<Petordenserviciodetalle>();
					int args[] = {0};
					
					if(petordenservicio != null && petordenservicio.getId() != null && petordenservicio.getId().getIdordenservicio() > 0 ){
						PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
						lisPetordenserviciodetalle = petordenserviciodetalleBO.lisPetordenserviciodetalleByPage(petordenservicio.getId(), pageSize, first, args);
					}
					
					this.setRowCount(args[0]);
	
			        return lisPetordenserviciodetalle;
				}
				
				@Override
               public void setRowIndex(int rowIndex) {
                   
                    * The following is in ancestor (LazyDataModel):
                    * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
                    
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
	}*/
	
	/*private void consultarServicios(){
		try{
			if(petordenservicio != null && petordenservicio.getId() != null && petordenservicio.getId().getIdordenservicio() > 0 ){
				PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
				lisPetordenserviciodetalle = petordenserviciodetalleBO.lisPetordenserviciodetalle(petordenservicio.getId());
				
				if(lisPetordenserviciodetalle != null && lisPetordenserviciodetalle.size() > 0){
					lisPetordenserviciodetalleClon = new ArrayList<Petordenserviciodetalle>(lisPetordenserviciodetalle);
				}
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}*/
	
	/*public List<Mascotas> buscarMascotas(String query) {
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
	}*/
	
	public List<Petmascotahomenaje> buscarMascotas(String query) {
		List<Petmascotahomenaje> lisPetmascotahomenaje = new ArrayList<Petmascotahomenaje>();
		
		try{
			PetmascotaBO petmascotaBO = new PetmascotaBO();
			int args[] = {0};
			lisPetmascotahomenaje = petmascotaBO.lisPetmascotahomenajeByPage(query, 10, 0, args);
			
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
			
		return lisPetmascotahomenaje;
	}
	
	public void itemSelectMascota(SelectEvent event){
		try{
			Petmascotahomenaje petmascotahomenaje = (Petmascotahomenaje) event.getObject();
			lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(petmascotahomenaje.getIdmascota());
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
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null, null);
	}
	
	/*public void guardarItem(){
		try{
			if(validarCamposDetalle()){
				PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
				boolean ok = false;
				
				if(petordenserviciodetalleItem.getId().getIdordenserviciodetalle() > 0){
					ok = petordenserviciodetalleBO.updatePetordenserviciodetalle(petordenserviciodetalleItem);
				}else{
					PetordenserviciodetalleId petordenserviciodetalleId = new PetordenserviciodetalleId();
					petordenserviciodetalleId.setIdordenservicio(petordenservicio.getId().getIdordenservicio());
					petordenserviciodetalleId.setIdanio(petordenservicio.getId().getIdanio());
					petordenserviciodetalleItem.setId(petordenserviciodetalleId);
					ok = petordenserviciodetalleBO.newPetordenserviciodetalle(petordenserviciodetalleItem);
				}
				
				petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null);
				
				if(ok){
					new MessageUtil().showInfoMessage("Servicio Agregado con exito!","");
				}
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}*/
	
	public void agregarServicio(){
		try{
			if(validarServicios()){
				if(petordenserviciodetalleItem.getPetservicio().getIdservicio() > 0){
					lisPetordenserviciodetalle.add(petordenserviciodetalleItem.clonar());
				}
			}
			petordenserviciodetalleItem.setPetservicio(new Petservicio(0, new Setestado(),null, new Setusuario(), null, null, new Cotoficina(), new Cotempresa(), null, null, null, false, null, null, 0));
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

	/*private boolean validarCamposDetalle()
	{
		boolean ok = true;
		
		if(petordenserviciodetalleItem.getPetservicio() == null || petordenserviciodetalleItem.getPetservicio().getIdservicio() == 0){
			ok = false;
			new MessageUtil().showWarnMessage("Datos incompletos! El Servicio es obligatorio!","");
		}
		
		return ok;
	}*/
	
	private boolean validarServicios()
	{
		boolean ok = true;
		
		for(Petordenserviciodetalle petordenserviciodetalle : lisPetordenserviciodetalle){
			if(petordenserviciodetalle.getPetservicio().equals(petordenserviciodetalleItem.getPetservicio())){
				ok = false;
				new MessageUtil().showWarnMessage("Servicio repetido","");
				break;
			}
		}

		return ok;
	}
	
	public void guardar(){
		
		if(validarObligatorios()){
			try{
				boolean ok = false;
				
				PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
				//petordenservicio.setPetmascotahomenaje(mascotasselected.getPetmascotahomenaje());
				
				if(petordenservicio.getCotlugar() == null ||  petordenservicio.getCotlugar().getIdlugar() == 0){
					petordenservicio.setCotlugar(null);
					petordenservicioClon.setCotlugar(null);
				}
				
				if(petordenservicio.getId().getIdordenservicio() == 0){
					ok = petordenservicioBO.ingresarPetordenservicio(petordenservicio, lisPetordenserviciodetalle);
					if(ok){
						//FacesUtil facesUtil = new FacesUtil();
						//facesUtil.redirect("../admin/ordenservicio.jsf?faces-redirect=true&idordenservicio="+petordenservicio.getId().getIdordenservicio()+"&idanio="+petordenservicio.getId().getIdanio()+"&iditem=40");
						Utilities utilities = new Utilities();
						utilities.mostrarPaginaMensaje("Orden de Servicio ingresada con exito!!");
					}else{
						new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
					}
				}else{
					Utilities utilities = new Utilities();
					ok = petordenservicioBO.modificarPetordenservicio(petordenservicio, petordenservicioClon, lisPetordenserviciodetalle, lisPetordenserviciodetalleClon);
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
		
		return ok;
	}
	
	public void eliminar(){
		try{
			PetordenservicioBO petordenservicioBO = new PetordenservicioBO();
			Utilities utilities = new Utilities();
			
			boolean ok = petordenservicioBO.eliminarPetordenservicio(petordenservicio,lisPetordenserviciodetalle);
			
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
	
	/*public void eliminarItem(){
		try{

			PetordenserviciodetalleBO petordenserviciodetalleBO = new PetordenserviciodetalleBO();
			petordenserviciodetalleBO.eliminarPetordenserviciodetalle(petordenserviciodetalleItem.getId());
			
			petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null);
			
			new MessageUtil().showInfoMessage("Registro Eliminado con exito!","");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}*/
	
	public void quitarServicio(){
		if(petordenserviciodetalleItem.getPetservicio().getIdservicio() > 0){
			lisPetordenserviciodetalle.remove(petordenserviciodetalleItem);
		}
		petordenserviciodetalleItem = new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null, null);
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
				
				parametros.put("P_IDORDENSERVICIO", petordenservicio.getId().getIdordenservicio());
				parametros.put("P_IDANIO", petordenservicio.getId().getIdanio());
				
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
				visorBean.getParametros().put("P_IDORDENSERVICIO", petordenservicio.getId().getIdordenservicio());
				visorBean.getParametros().put("P_IDANIO", petordenservicio.getId().getIdanio());
				
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
		this.petordenserviciodetalleItem = petordenserviciodetalleItem == null ? new Petordenserviciodetalle(new PetordenserviciodetalleId(0,0,0), new Setestado(), new Setusuario(), new Petservicio(), new Petordenservicio(), null, null, null) : petordenserviciodetalleItem;
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

	public Cottipolugar getCottipolugar() {
		return cottipolugar;
	}

	public void setCottipolugar(Cottipolugar cottipolugar) {
		this.cottipolugar = cottipolugar;
	}

}
