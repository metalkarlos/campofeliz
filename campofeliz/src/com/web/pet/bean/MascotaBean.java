package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import com.web.pet.bo.CotcolorBO;
import com.web.pet.bo.CotpersonaBO;
import com.web.pet.bo.CottipoidentificacionBO;
import com.web.pet.bo.PetfotoBO;
import com.web.pet.bo.PetmascotaBO;
import com.web.pet.bo.PetmascotacolorBO;
import com.web.pet.bo.PetrazaBO;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Cotcolor;
import com.web.pet.pojo.annotations.Cotestado;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Petestado;
import com.web.pet.pojo.annotations.Petfoto;
import com.web.pet.pojo.annotations.Petmascota;
import com.web.pet.pojo.annotations.Petmascotacolor;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@Named
@ViewScoped
public class MascotaBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5992617393517396941L;
	private Petmascota petmascota;
	private int idmascota;
	private int especie;
	private List<Cottipoidentificacion> lisCottipoidentificacion;
	private List<Petraza> lisRaza;
	private List<Cotcolor> lisColor;
	private List<Petmascotacolor> lisPetmascotacolorOld;
	private List<Petmascotacolor> lisPetmascotacolor;
	//private static StreamedContent foto;
	private String mascotasPath;
	private String blankImage;
	private int idcolorselected;
	private Cotcolor cotcolorselected;
	private Cotpersona cotpersonaselected;
	private Cottipoidentificacion cottipoidentificacionselected;

	public MascotaBean() {
		petmascota = new Petmascota(0, new Petestado(), null, new Petraza(), new Setusuario(), new Petespecie(), new Cotpersona(), null, null, null, null, null, null, null, null, false, false, null);
		cottipoidentificacionselected = new Cottipoidentificacion();
		lisPetmascotacolorOld = new ArrayList<Petmascotacolor>();

		llenarLisTipoidentificacion();
		llenarLisRaza();
		llenarLisColor();
		lisPetmascotacolor = new ArrayList<Petmascotacolor>();
		//lisColor = ColorConverter.lisColorDB;//Programaci�n ubicada en ColorConverter
		setBlankImage(Parametro.BLANK_IMAGE_PATH);
	}
	
	private void llenarLisTipoidentificacion(){
		try{
			Cottipoidentificacion cottipoidentificacion = new Cottipoidentificacion();
			cottipoidentificacion.setIdtipoidentificacion(0);
			cottipoidentificacion.setNombre("Seleccione");
			cottipoidentificacion.setCotestado(new Cotestado());
			cottipoidentificacion.setSetusuario(new Setusuario());
		
			lisCottipoidentificacion = new ArrayList<Cottipoidentificacion>();
			lisCottipoidentificacion.add(cottipoidentificacion);
			
			CottipoidentificacionBO cottipoidentificacionBO = new CottipoidentificacionBO();
			List<Cottipoidentificacion> lisTmp = cottipoidentificacionBO.lisCottipoidentificacion();
			if(lisTmp != null && lisTmp.size() > 0){
				lisCottipoidentificacion.addAll(lisTmp);
			}
		}catch(Exception e){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	private void llenarLisRaza(){
		try{
			Petraza petraza = new Petraza();
			petraza.setNombre("Seleccione");
			petraza.setPetestado(new Petestado());
			petraza.setSetusuario(new Setusuario());
			
			lisRaza = new ArrayList<Petraza>();
			lisRaza.add(petraza);
			
			PetrazaBO petrazaBO = new PetrazaBO();
			List<Petraza> lisTmp = petrazaBO.lisRazas();
			if(lisTmp != null & lisTmp.size() > 0){
				lisRaza.addAll(lisTmp);
			}
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	private void llenarLisColor(){
		try{
			Cotcolor cotcolor = new Cotcolor();
			cotcolor.setNombre("Seleccione");
			cotcolor.setCotestado(new Cotestado());
			cotcolor.setSetusuario(new Setusuario());
			
			lisColor = new ArrayList<Cotcolor>();
			lisColor.add(cotcolor);
			
			CotcolorBO cotcolorBO = new CotcolorBO();
			List<Cotcolor> lisTmp = cotcolorBO.lisCotcolor();
			if(lisTmp != null && lisTmp.size() > 0){
				lisColor.addAll(lisTmp);
			}
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void setPetmascota(Petmascota petmascota) {
		this.petmascota = petmascota;
	}

	public Petmascota getPetmascota() {
		return petmascota;
	}

	public int getIdmascota() {
		return idmascota;
	}

	public void setIdmascota(int idmascota) {
		this.idmascota = idmascota;
		if(idmascota>0){
			try{
				petmascota = new PetmascotaBO().getPetmascotaById(idmascota);
				cotpersonaselected = petmascota.getCotpersona();
				if(petmascota != null && petmascota.getCottipoidentificacion() != null && petmascota.getCottipoidentificacion().getIdtipoidentificacion() > 0){
					cottipoidentificacionselected = petmascota.getCottipoidentificacion();
				}
				Petfoto petfoto = new PetfotoBO().getPetfotoPerfilByPetId(idmascota);
				lisPetmascotacolor = new PetmascotacolorBO().lisPetmascotacolor(idmascota);
				if(lisPetmascotacolor == null){
					lisPetmascotacolor = new ArrayList<Petmascotacolor>();
				}
				lisPetmascotacolorOld = new ArrayList<Petmascotacolor>(lisPetmascotacolor);
				if(petfoto!=null){
					FileUtil fileUtil = new FileUtil();
					Properties petsoftProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
					String resources_server_url = petsoftProperties.getProperty("resources_server_url");
					
					setMascotasPath(resources_server_url+petfoto.getRuta());
					//setMascotasPath(Parametro.MASCOTAS_PATH+idmascota+Parametro.FILE_SEPARATOR+petfoto.getName());
					//mascotasPath = petfoto.getPath()+idmascota+"/"+petfoto.getName();
					//foto = new PrimeUtil().getByteDefaultStreamedContent(petfoto.getFoto());
				}
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
			}catch(Exception re){
				new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
	}
	
	public void verificarCedula(){
		try{
			Cotpersona cotpersona = new Cotpersona(0, new Cottipoidentificacion(), new Cotestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0);
			cotpersona.setNumeroidentificacion(petmascota.getCotpersona().getNumeroidentificacion());
			
			List<Cotpersona> lisCotpersona = new ArrayList<Cotpersona>();
			CotpersonaBO cotpersonaBO = new CotpersonaBO();
			lisCotpersona = cotpersonaBO.lisCotpersonaBusqueda(cotpersona);
			
			if(lisCotpersona != null && lisCotpersona.size() > 0){
				petmascota.setCotpersona(lisCotpersona.get(0));
				new MessageUtil().showInfoMessage("Correcto!", "Se ha validado correctamente!");
			}else{
				petmascota.setCotpersona(new Cotpersona());
				new MessageUtil().showFatalMessage("C�dula no Existe!", "C�dula no existe, verifique e intente nuevamente!");
			}
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public void setEspecie(int especie) {
		this.especie = especie;
	}

	public int getEspecie() {
		return especie;
	}

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

	public String getBlankImage() {
		return blankImage;
	}

	public void setBlankImage(String blankImage) {
		this.blankImage = blankImage;
	}

	public String getMascotasPath() {
		return mascotasPath;
	}

	public void setMascotasPath(String mascotasPath) {
		this.mascotasPath = mascotasPath;
	}

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

	public Cotpersona getCotpersonaselected() {
		return cotpersonaselected;
	}

	public void setCotpersonaselected(Cotpersona cotpersonaselected) {
		this.cotpersonaselected = cotpersonaselected;
	}

	public Cottipoidentificacion getCottipoidentificacionselected() {
		return cottipoidentificacionselected;
	}

	public void setCottipoidentificacionselected(
			Cottipoidentificacion cottipoidentificacionselected) {
		this.cottipoidentificacionselected = cottipoidentificacionselected;
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
	
	public void grabar(){
		try{
			
			petmascota.setCotpersona(cotpersonaselected);
			
			if(validarCampos()){
				PetmascotaBO petmascotaBO = new PetmascotaBO();
				boolean ok = false;
				
				if(cottipoidentificacionselected != null && cottipoidentificacionselected.getIdtipoidentificacion() > 0){
					petmascota.setCottipoidentificacion(cottipoidentificacionselected);
				}else{
					petmascota.setCottipoidentificacion(null);
				}
				
				if(idmascota > 0){
					try{
						ok = petmascotaBO.updatePet(petmascota, lisPetmascotacolorOld, lisPetmascotacolor);
						lisPetmascotacolorOld = new ArrayList<Petmascotacolor>(lisPetmascotacolor);
					}catch(RuntimeException re){
						new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
					}
				}else{
					if(especie > 0){
						//petmascota.getPetespecie().setIdespecie(especie);
						try{
							ok = petmascotaBO.newPet(petmascota, lisPetmascotacolor);
							idmascota = petmascota.getIdmascota();
							lisPetmascotacolorOld = new ArrayList<Petmascotacolor>(lisPetmascotacolor);
							/*FacesUtil facesUtil = new FacesUtil();
							facesUtil.redirect("mascota.jsf?faces-redirect=true&idmascota="+idmascota+"&tipo="+especie+"&iditem=2");*/
						}catch(RuntimeException re){
							new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
						}
					}else{
						new MessageUtil().showFatalMessage("Oops!", "Tipo de mascota no definido, verifique e intente nuevamente!");
					}
				}
				
				if(ok){
					new MessageUtil().showInfoMessage("Exito!", "Registro completo!");
				}
			}
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
		//return "";//"mascota.jsf?faces-redirect=true&idmascota="+this.idmascota+"&tipo="+this.tipo;
	}

	public void eliminar(){
		try{
			Petestado petestado = new Petestado();
			petestado.setIdestado(2);//inactivo
			petmascota.setPetestado(petestado);
			PetmascotaBO petmascotaBO = new PetmascotaBO();
			petmascotaBO.updatePet(petmascota, lisPetmascotacolorOld, lisPetmascotacolor);
			FacesUtil facesUtil = new FacesUtil();
			facesUtil.redirect("mascotas.jsf?iditem=2");
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
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
		
		if(petmascota.getNombre() == null || petmascota.getNombre().trim().length() == 0){
			new MessageUtil().showErrorMessage("Datos incompletos!", "El Nombre es obligatorio!");
			ok = false;
		}else{
			/*if(petmascota.getCaracteristicas() == null || petmascota.getCaracteristicas().trim().length() == 0){
				ok = false;
			}*/
			if(petmascota.getCotpersona() == null || petmascota.getCotpersona().getIdpersona() == 0 ){
				new MessageUtil().showErrorMessage("Datos incompletos!", "El Propietarios es obligatorio!");
				ok = false;
			}else{
				if(petmascota.getPetraza() == null || petmascota.getPetraza().getIdraza() == 0 ){
					new MessageUtil().showErrorMessage("Datos incompletos!", "La Raza es obligatoria!");
					ok = false;
				}else{
					if(cottipoidentificacionselected != null && cottipoidentificacionselected.getIdtipoidentificacion() > 0 && (petmascota.getNumeroidentificacion() == null || petmascota.getNumeroidentificacion().trim().length() == 0)){
						new MessageUtil().showErrorMessage("Datos incompletos!", "Si selecciona el Tipo de Identificaci�n tambien debe ingresar el N�mero de Identificaci�n!");
						ok = false;
					}else{
						if(petmascota.getNumeroidentificacion() != null && petmascota.getNumeroidentificacion().trim().length() > 0 && (cottipoidentificacionselected == null || cottipoidentificacionselected.getIdtipoidentificacion() == 0)){
							new MessageUtil().showErrorMessage("Datos incompletos!", "Si ingresa El N�mero de Identificaci�n tambien debe seleccionar el Tipo de Identificaci�n!");
							ok = false;
						}
						else{ 
							if(petmascota.getFechafallecimiento() != null && petmascota.getFechanacimiento() != null && (petmascota.getFechafallecimiento().before(petmascota.getFechanacimiento()) )){
								new MessageUtil().showErrorMessage("Datos incompletos!", "Fecha de Fallecimiento debe ser mayor a Fecha de Nacimiento!");
								ok = false;
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
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void agregarColor(){
		try{
			if(cotcolorselected != null && cotcolorselected.getIdcolor() > 0){
				Petmascotacolor petmascotacolor = new Petmascotacolor(0, new Petmascota(), new Petestado(), new Cotcolor(), new Setusuario(), null, null);
				petmascotacolor.getPetmascota().setIdmascota(idmascota);
				petmascotacolor.setCotcolor(cotcolorselected);
				lisPetmascotacolor.add(petmascotacolor);
			}
		}catch(Exception e){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
}