package com.web.pet.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.web.pet.bo.CotcolorBO;
import com.web.pet.bo.CotpersonaBO;
import com.web.pet.bo.CottipoidentificacionBO;
import com.web.pet.bo.PetespecieBO;
import com.web.pet.bo.PetfotomascotaBO;
import com.web.pet.bo.PetmascotaBO;
import com.web.pet.bo.PetmascotacolorBO;
import com.web.pet.bo.PetrazaBO;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Cotcolor;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Petfotomascota;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;
import com.web.util.Utilities;

@ManagedBean
@ViewScoped
public class MascotaBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5992617393517396941L;
	private Petmascotahomenaje petmascotahomenaje;
	private Petmascotahomenaje petmascotahomenajeClon;
	private int idmascota;
	//private int especie;
	private List<Cottipoidentificacion> lisCottipoidentificacion;
	private List<Petraza> lisRaza;
	private List<Cotcolor> lisColor;
	private List<Petmascotacolor> lisPetmascotacolorOld;
	private List<Petmascotacolor> lisPetmascotacolor;
	private List<Petespecie> lisPetespecie;
	//private static StreamedContent foto;
	private int idcolorselected;
	private Cotcolor cotcolorselected;
	//private Cotpersona cotpersonaselected;
	//private Cottipoidentificacion cottipoidentificacionselected;
	//private Petfotomascota petfotomascota;
	private List<Petfotomascota> lisPetfotomascota;
	private List<Petfotomascota> lisPetfotomascotaClon;
	private Petfotomascota petfotomascotaSeleccionado;
	private StreamedContent streamedContent;
	private UploadedFile uploadedFile;
	private String descripcionFoto;
	private boolean fotoSubida;
	private long maxfilesize;
	private Cotpersona cotpersonaSeleccionar;

	public MascotaBean() {
		petmascotahomenaje = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),0,new BigDecimal(0),null,false,false,null);
		petmascotahomenajeClon = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),0,new BigDecimal(0),null,false,false,null);
		//cottipoidentificacionselected = new Cottipoidentificacion();
		lisPetmascotacolorOld = new ArrayList<Petmascotacolor>();
		//cotpersonaselected = new Cotpersona(); 
		descripcionFoto = "";
		fotoSubida = false;
		lisPetmascotacolor = new ArrayList<Petmascotacolor>();
		lisPetfotomascota = new ArrayList<Petfotomascota>();
		lisPetfotomascotaClon = new ArrayList<Petfotomascota>();
		petfotomascotaSeleccionado = new Petfotomascota();
		maxfilesize = Parametro.TAMAÑO_IMAGEN;
		cotpersonaSeleccionar = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);

		llenarLisTipoidentificacion();
		llenarLisColor();
		llenarEspecieMascota();
		
		//lisColor = ColorConverter.lisColorDB;//Programación ubicada en ColorConverter
	}
	
	@PostConstruct
	public void PostMascotaBean() {
		FacesUtil facesUtil = new FacesUtil();

		try{
			//Object par1 = facesUtil.getParametroUrl("especie");
			Object par2 = facesUtil.getParametroUrl("idmascota");
			
			if(/*par1 != null && */par2 != null){
			
				//especie  = Integer.parseInt(par1.toString());
				idmascota = Integer.parseInt(par2.toString());
				
				if(idmascota > 0){
					PetmascotaBO petmascotaBO = new PetmascotaBO();
					petmascotahomenaje = petmascotaBO.getPetmascotaById(idmascota);
					
					if(petmascotahomenaje != null){
						
						petmascotahomenajeClon = petmascotahomenaje.clonar();
						
						if(petmascotahomenaje.getCottipoidentificacion() == null){
							petmascotahomenaje.setCottipoidentificacion(new Cottipoidentificacion(0,null,null));
						}
						
						if(petmascotahomenajeClon.getCottipoidentificacion() == null){
							petmascotahomenajeClon.setCottipoidentificacion(new Cottipoidentificacion(0,null,null));
						}
						
						PetfotomascotaBO petfotomascotaBO = new PetfotomascotaBO();
						lisPetfotomascota = petfotomascotaBO.lisPetfotomascotaByIdmascota(idmascota);
						if( lisPetfotomascota != null && lisPetfotomascota.size() > 0){
							for(Petfotomascota petfotomascota : lisPetfotomascota){
								lisPetfotomascotaClon.add(petfotomascota.clonar());
							}
						}
						
						//cotpersonaselected = petmascotahomenaje.getCotpersona();
						/*if(petmascotahomenaje.getCottipoidentificacion() != null){
							cottipoidentificacionselected = petmascotahomenaje.getCottipoidentificacion();
						}*/
						lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(idmascota);
						if(lisPetmascotacolor == null){
							lisPetmascotacolor = new ArrayList<Petmascotacolor>();
						}
						lisPetmascotacolorOld = new ArrayList<Petmascotacolor>(lisPetmascotacolor);
						if(petmascotahomenaje.getCotpersona() == null){
							petmascotahomenaje.setCotpersona(new Cotpersona());
						}
						if(petmascotahomenaje.getCottipoidentificacion() == null){
							petmascotahomenaje.setCottipoidentificacion(new Cottipoidentificacion());
						}
						if(petmascotahomenaje.getPetraza() == null){
							petmascotahomenaje.setPetraza(new Petraza());
						}
						if(petmascotahomenaje.getPetespecie() == null){
							petmascotahomenaje.setPetespecie(new Petespecie());
						}
					}else{
						//petmascotahomenaje = new Petmascotahomenaje(0, null, new Petraza(), new Cotpersona());
						petmascotahomenaje = new Petmascotahomenaje(0, new Setestado(), new Setusuario(), new Petespecie(), null, null, null, null, null, null, null, null, null, null, null, null, new Petraza(), new Cotpersona(), new Cottipoidentificacion(), 1, new BigDecimal(0), null, false, false, null);
					}
					
					//petmascotahomenaje.getPetespecie().setIdespecie(this.especie);
					if(petmascotahomenaje.getPetespecie().getIdespecie() > 0){
						llenarLisRaza();
					}
					
					//foto = new PrimeUtil().getByteDefaultStreamedContent(petfoto.getFoto());
					/*else{
						try {
							String imagePath = new FacesUtil().getRealPath("")+File.separator+"resources"+File.separator+"images"+File.separator+"miscellaneous"+File.separator+"blank.jpg";
							File file = new File(imagePath);
							InputStream inputStream = new FileInputStream(file);
							foto = new DefaultStreamedContent(inputStream, "image/jpeg", "null_image");
							uploaded = false;
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}*/
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
	
	private void llenarEspecieMascota(){
		try
		{
			lisPetespecie = new PetespecieBO().lisPetespecie();
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private void llenarLisTipoidentificacion(){
		try{
			/*Cottipoidentificacion cottipoidentificacion = new Cottipoidentificacion();
			cottipoidentificacion.setIdtipoidentificacion(0);
			cottipoidentificacion.setNombre("Seleccione");
			cottipoidentificacion.setSetestado(new Setestado());
			cottipoidentificacion.setSetusuario(new Setusuario());*/
		
			lisCottipoidentificacion = new ArrayList<Cottipoidentificacion>();
			//lisCottipoidentificacion.add(cottipoidentificacion);
			
			CottipoidentificacionBO cottipoidentificacionBO = new CottipoidentificacionBO();
			List<Cottipoidentificacion> lisTmp = cottipoidentificacionBO.lisCottipoidentificacion();
			if(lisTmp != null && lisTmp.size() > 0){
				lisCottipoidentificacion.addAll(lisTmp);
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void llenarLisRaza(){
		try{
			lisRaza = new ArrayList<Petraza>();
			
			PetrazaBO petrazaBO = new PetrazaBO();
			List<Petraza> lisTmp = petrazaBO.lisRazas(petmascotahomenaje.getPetespecie().getIdespecie());//this.especie);
			if(lisTmp != null & lisTmp.size() > 0){
				lisRaza.addAll(lisTmp);
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	private void llenarLisColor(){
		try{
			/*Cotcolor cotcolor = new Cotcolor();
			cotcolor.setNombre("Seleccione");
			cotcolor.setSetestado(new Setestado());
			cotcolor.setSetusuario(new Setusuario());*/
			
			lisColor = new ArrayList<Cotcolor>();
			//lisColor.add(cotcolor);
			
			CotcolorBO cotcolorBO = new CotcolorBO();
			List<Cotcolor> lisTmp = cotcolorBO.lisCotcolor();
			if(lisTmp != null && lisTmp.size() > 0){
				lisColor.addAll(lisTmp);
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void verificarCedula(){
		try{
			Cotpersona cotpersona = new Cotpersona(0, new Cottipoidentificacion(), new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
			cotpersona.setNumeroidentificacion(petmascotahomenaje.getCotpersona().getNumeroidentificacion());
			
			List<Cotpersona> lisCotpersona = new ArrayList<Cotpersona>();
			CotpersonaBO cotpersonaBO = new CotpersonaBO();
			lisCotpersona = cotpersonaBO.lisCotpersonaBusqueda(cotpersona);
			
			if(lisCotpersona != null && lisCotpersona.size() > 0){
				petmascotahomenaje.setCotpersona(lisCotpersona.get(0));
				new MessageUtil().showInfoMessage("Correcto! Se ha validado correctamente!","");
			}else{
				petmascotahomenaje.setCotpersona(new Cotpersona());
				new MessageUtil().showFatalMessage("Cédula no Existe! Cédula no existe, verifique e intente nuevamente!","");
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public List<Cotpersona> buscarPropietarios(String query) {
		List<Cotpersona> lisPropietarios = new ArrayList<Cotpersona>();
		
		if(query != null && query.trim().length() > 0){
			CotpersonaBO cotpersonaBO = new CotpersonaBO();
			String[] nombres = query.split(" ");
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
	
	public void seleccionarPropietario() {
		try{
			petmascotahomenaje.setCotpersona(cotpersonaSeleccionar.clonar());
			cotpersonaSeleccionar = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
		}catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try{
			uploadedFile = event.getFile();
			streamedContent = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType());
			
			FacesUtil facesUtil = new FacesUtil();
			UsuarioBean usuarioBean = (UsuarioBean)facesUtil.getSessionBean("usuarioBean");
			usuarioBean.setStreamedContent(streamedContent);
			facesUtil.setSessionBean("usuarioBean", usuarioBean);
			fotoSubida = true;
			
			new MessageUtil().showInfoMessage("Presione Grabar para guardar los cambios.","");
		}catch(Exception x){
			x.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void borrarFotoSubida(){
		streamedContent = null;
		uploadedFile = null;
		descripcionFoto = null;
		fotoSubida = false;
	}
	
	public void grabar(){
		try{
			if(validarCampos()){
				PetmascotaBO petmascotaBO = new PetmascotaBO();
				Petfotomascota petfotomascota = new Petfotomascota();
				boolean ok = false;
				
				//petmascotahomenaje.setCotpersona(cotpersonaselected);
				
				/*if(cottipoidentificacionselected != null && cottipoidentificacionselected.getIdtipoidentificacion() > 0){
					petmascotahomenaje.setCottipoidentificacion(cottipoidentificacionselected);
				}else{
					petmascotahomenaje.setCottipoidentificacion(null);
				}*/
				
				if(petmascotahomenaje.getCottipoidentificacion() != null && petmascotahomenaje.getCottipoidentificacion().getIdtipoidentificacion() == 0){
					petmascotahomenaje.setCottipoidentificacion(null);
				}
				
				if(fotoSubida && descripcionFoto != null && descripcionFoto.trim().length() > 0){
					petfotomascota.setDescripcion(descripcionFoto);
				}
				
				Utilities utilities = new Utilities();
				
				if(idmascota > 0){
					ok = petmascotaBO.modificarMascota(petmascotahomenaje,petmascotahomenajeClon,lisPetfotomascota,lisPetfotomascotaClon,lisPetmascotacolor,lisPetmascotacolorOld,petfotomascota, uploadedFile, null);
					lisPetmascotacolorOld = new ArrayList<Petmascotacolor>(lisPetmascotacolor);
					if(ok){
						utilities.mostrarPaginaMensaje("Mascota actualizada con exito!!");
					}else{
						new MessageUtil().showInfoMessage("No existen cambios que guardar.", "");
					}
				}else{
					//if(especie > 0){
						//petmascota.getPetespecie().setIdespecie(especie);
						ok = petmascotaBO.ingresarMascota(petmascotahomenaje, lisPetmascotacolor, petfotomascota, uploadedFile, null);
						uploadedFile = null;
						if(ok){
							utilities.mostrarPaginaMensaje("Mascota ingresada con exito!!");
						}else{
							new MessageUtil().showInfoMessage("No se ha podido ingresar la Mascota. Comunicar al Webmaster.", "");
						}
						//idmascota = petmascotahomenaje.getIdmascota();
						//lisPetmascotacolorOld = new ArrayList<Petmascotacolor>(lisPetmascotacolor);
						/*FacesUtil facesUtil = new FacesUtil();
						facesUtil.redirect("mascota.jsf?faces-redirect=true&idmascota="+idmascota+"&tipo="+especie+"&iditem=2");*/
					//}else{
						//new MessageUtil().showFatalMessage("Tipo de mascota no definido, verifique e intente nuevamente!", "Tipo de mascota no definido, verifique e intente nuevamente!");
					//}
				}
				
				/*if(ok){
					new MessageUtil().showInfoMessage("Exito! Registro completo!","");
				}*/
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
		
		//return "";//"mascota.jsf?faces-redirect=true&idmascota="+this.idmascota+"&tipo="+this.tipo;
	}

	public void eliminar(){
		try{
			PetmascotaBO petmascotaBO = new PetmascotaBO();
			Utilities utilities = new Utilities();
			
			if(petmascotahomenaje.getCottipoidentificacion() != null && petmascotahomenaje.getCottipoidentificacion().getIdtipoidentificacion() == 0){
				petmascotahomenaje.setCottipoidentificacion(null);
			}
			
			if(petmascotahomenaje.getPetespecie() != null && petmascotahomenaje.getPetespecie().getIdespecie() == 0){
				petmascotahomenaje.setPetespecie(null);
			}
			
			if(petmascotahomenaje.getPetraza() != null && petmascotahomenaje.getPetraza().getIdraza() == 0){
				petmascotahomenaje.setPetraza(null);
			}
			
			if(petmascotahomenaje.getCotpersona() != null && petmascotahomenaje.getCotpersona().getIdpersona() == 0){
				petmascotahomenaje.setCotpersona(null);
			}
			
			boolean ok = petmascotaBO.eliminar(petmascotahomenaje,lisPetfotomascota,lisPetmascotacolor);
			if(ok){
				utilities.mostrarPaginaMensaje("Mascota eliminada con exito!");
			}else{
				utilities.mostrarPaginaMensaje("No se ha podido eliminar la Mascota. Comunicar al Webmaster.");
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void cancelar(){
		/*MenuBean menuBean = (MenuBean)new FacesUtil().getSessionBean("menuBean");
		FacesUtil facesUtil = new FacesUtil();
		facesUtil.redirect("mascotas.jsf?faces-redirect=true&tipo="+this.especie+"&iditem="+menuBean.getActiveIdItem());*/
	}
	
	
	private boolean validarCampos()
	{
		boolean ok = true;
		
		if(petmascotahomenaje.getNombre() == null || petmascotahomenaje.getNombre().trim().length() == 0){
			new MessageUtil().showWarnMessage("El Nombre de la Mascota es obligatorio.","");
			ok = false;
		}else{
			/*if(petmascota.getCaracteristicas() == null || petmascota.getCaracteristicas().trim().length() == 0){
				ok = false;
			}*/
			if(petmascotahomenaje.getCotpersona() == null || petmascotahomenaje.getCotpersona().getIdpersona() == 0 ){
				new MessageUtil().showWarnMessage("El Nombre del Propietario es obligatorio.","");
				ok = false;
			}else{
				if(petmascotahomenaje.getPetespecie() == null || petmascotahomenaje.getPetespecie().getIdespecie() == 0 ){
					new MessageUtil().showWarnMessage("La Especie es obligatoria.","");
					ok = false;
				}else{
					if(petmascotahomenaje.getPetraza() == null || petmascotahomenaje.getPetraza().getIdraza() == 0 ){
						new MessageUtil().showWarnMessage("La Raza es obligatoria.","");
						ok = false;
					}else{
						if(petmascotahomenaje.getCottipoidentificacion() != null && petmascotahomenaje.getCottipoidentificacion().getIdtipoidentificacion() > 0 && (petmascotahomenaje.getNumeroidentificacion() == null || petmascotahomenaje.getNumeroidentificacion().trim().length() == 0)){
							new MessageUtil().showWarnMessage("Si selecciona el Tipo de Identificación tambien debe ingresar el Número de Identificación.","");
							ok = false;
						}else{
							if(petmascotahomenaje.getNumeroidentificacion() != null && petmascotahomenaje.getNumeroidentificacion().trim().length() > 0 && (petmascotahomenaje.getCottipoidentificacion() == null || petmascotahomenaje.getCottipoidentificacion().getIdtipoidentificacion() == 0)){
								new MessageUtil().showWarnMessage("Si ingresa el Número de Identificación también debe seleccionar el Tipo de Identificación.","");
								ok = false;
							}
							else{ 
								if(petmascotahomenaje.getFechafallecimiento() == null){
									new MessageUtil().showWarnMessage("Datos incompletos! Fecha de Fallecimiento es obligatorio!","");
									ok = false;
								}else{
									if(petmascotahomenaje.getFechanacimiento() != null && (petmascotahomenaje.getFechafallecimiento().before(petmascotahomenaje.getFechanacimiento()) )){
										new MessageUtil().showWarnMessage("Fecha de Fallecimiento debe ser mayor a Fecha de Nacimiento.","");
										ok = false;
									}	
								}
							}	
							
						}
					}
				}
			}
		}
		
		return ok;
	}
	
	public void quitarColor(){
		try{
			int i=-1;
			for(i=0; i<lisPetmascotacolor.size(); i++){
				if(lisPetmascotacolor.get(i).getCotcolor().getIdcolor() == idcolorselected){
					break;
				}
			}
			if(i >= 0){
				lisPetmascotacolor.remove(i);
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void agregarColor(){
		try{
			if(cotcolorselected != null && cotcolorselected.getIdcolor() > 0){
				Petmascotacolor petmascotacolor = new Petmascotacolor(0, new Petmascotahomenaje(), new Setestado(), new Cotcolor(), new Setusuario(), null, null,null);
				petmascotacolor.getPetmascotahomenaje().setIdmascota(idmascota);
				petmascotacolor.setCotcolor(cotcolorselected);
				lisPetmascotacolor.add(petmascotacolor);
				cotcolorselected = new Cotcolor();
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void ponerFotoPrincipal(){
		petmascotahomenaje.setRutafoto(petfotomascotaSeleccionado.getRuta());
		petfotomascotaSeleccionado = new Petfotomascota();
		new MessageUtil().showInfoMessage("Presione Grabar para guardar los cambios.","");
	}
	
	public void quitarFotoGaleria(){
		if(petfotomascotaSeleccionado.getRuta().equalsIgnoreCase(petmascotahomenaje.getRutafoto())){
			new MessageUtil().showInfoMessage("La foto a eliminar es la foto principal de ésta mascota. Seleccione otra foto como principal para poderla eliminar.","");
		}else{
			lisPetfotomascota.remove(petfotomascotaSeleccionado);
			petfotomascotaSeleccionado = new Petfotomascota();
		}
	}
	
	public void setPetmascotahomenaje(Petmascotahomenaje petmascotahomenaje) {
		this.petmascotahomenaje = petmascotahomenaje;
	}

	public Petmascotahomenaje getPetmascotahomenaje() {
		return petmascotahomenaje;
	}

	public int getIdmascota() {
		return idmascota;
	}

	public void setIdmascota(int idmascota) {
		this.idmascota = idmascota;
	}
	
	/*public void setEspecie(int especie) {
		this.especie = especie;
	}

	public int getEspecie() {
		return especie;
	}*/

	public List<Cotcolor> getLisColor() {
		return lisColor;
	}

	public void setLisColor(List<Cotcolor> lisColor) {
		this.lisColor = lisColor;
	}

	public List<Cottipoidentificacion> getLisCottipoidentificacion() {
		return lisCottipoidentificacion;
	}

	public void setLisCottipoidentificacion(List<Cottipoidentificacion> lisCottipoidentificacion) {
		this.lisCottipoidentificacion = lisCottipoidentificacion;
	}

	public List<Petraza> getLisRaza() {
		return lisRaza;
	}

	public void setLisRaza(List<Petraza> lisRaza) {
		this.lisRaza = lisRaza;
	}

	/*public StreamedContent getFoto() {
		return foto;
	}*/

	public List<Petmascotacolor> getLisPetmascotacolor() {
		return lisPetmascotacolor;
	}

	public void setLisPetmascotacolor(List<Petmascotacolor> lisPetmascotacolor) {
		this.lisPetmascotacolor = lisPetmascotacolor;
	}

	public int getIdcolorselected() {
		return idcolorselected;
	}

	public void setIdcolorselected(int idcolorselected) {
		this.idcolorselected = idcolorselected;
	}

	public Cotcolor getCotcolorselected() {
		return cotcolorselected;
	}

	public void setCotcolorselected(Cotcolor cotcolorselected) {
		this.cotcolorselected = cotcolorselected;
	}

	/*public Cotpersona getCotpersonaselected() {
		return cotpersonaselected;
	}*/

	/*public void setCotpersonaselected(Cotpersona cotpersonaselected) {
		this.cotpersonaselected = cotpersonaselected;
	}*/

	/*public Cottipoidentificacion getCottipoidentificacionselected() {
		return cottipoidentificacionselected;
	}

	public void setCottipoidentificacionselected(
			Cottipoidentificacion cottipoidentificacionselected) {
		this.cottipoidentificacionselected = cottipoidentificacionselected;
	}*/

	/*public Petfotomascota getPetfotomascota() {
		return petfotomascota;
	}

	public void setPetfotomascota(Petfotomascota petfotomascota) {
		this.petfotomascota = petfotomascota;
	}*/

	public List<Petfotomascota> getLisPetfotomascota() {
		return lisPetfotomascota;
	}

	public void setLisPetfotomascota(List<Petfotomascota> lisPetfotomascota) {
		this.lisPetfotomascota = lisPetfotomascota;
	}

	public Petfotomascota getPetfotomascotaSeleccionado() {
		return petfotomascotaSeleccionado;
	}

	public void setPetfotomascotaSeleccionado(
			Petfotomascota petfotomascotaSeleccionado) {
		this.petfotomascotaSeleccionado = petfotomascotaSeleccionado;
	}
	
	public List<Petespecie> getLisPetespecie() {
		return lisPetespecie;
	}

	public void setLisPetespecie(List<Petespecie> lisPetespecie) {
		this.lisPetespecie = lisPetespecie;
	}

	public String getDescripcionFoto() {
		return descripcionFoto;
	}

	public void setDescripcionFoto(String descripcionFoto) {
		this.descripcionFoto = descripcionFoto;
	}

	public boolean isFotoSubida() {
		return fotoSubida;
	}

	public void setFotoSubida(boolean fotoSubida) {
		this.fotoSubida = fotoSubida;
	}

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	public long getMaxfilesize() {
		return maxfilesize;
	}

	public Cotpersona getCotpersonaSeleccionar() {
		return cotpersonaSeleccionar;
	}

	public void setCotpersonaSeleccionar(Cotpersona cotpersonaSeleccionar) {
		this.cotpersonaSeleccionar = cotpersonaSeleccionar;
	}

}
