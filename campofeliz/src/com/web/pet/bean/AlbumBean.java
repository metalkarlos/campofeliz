package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.web.pet.bo.PetfotoBO;
import com.web.pet.bo.PetmascotaBO;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Petestado;
import com.web.pet.pojo.annotations.Petfoto;
import com.web.pet.pojo.annotations.Petmascota;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@Named
@ViewScoped
public class AlbumBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1517352078005227312L;
	private List<Petfoto> lispetfoto;
	private int idmascota;
	private int tipo;
	private Petmascota petmascota;
	private Petfoto petfotoSelected;
	private String fileSeparator;
	private String blankImage;
	private String mascotasPath;
	private UploadedFile uploadedFile;
	private String resources_server_url;
	
	public AlbumBean(){
		FileUtil fileUtil = new FileUtil();
		Properties petsoftProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
		resources_server_url = petsoftProperties.getProperty("resources_server_url");
		
		lispetfoto = new ArrayList<Petfoto>();
		petmascota = new Petmascota(0, new Petestado(), new Cottipoidentificacion(), new Petraza(), new Setusuario(), new Petespecie(), null, null, null, null, null, null, null, null, null, false, false, null);
		setPetfotoSelected(new Petfoto());
		setFileSeparator(Parametro.FILE_SEPARATOR);
		setBlankImage(Parametro.BLANK_IMAGE_PATH);
		setMascotasPath(Parametro.MASCOTAS_PATH);
	}
	
	public void setIdmascota(int idmascota) {
		this.idmascota = idmascota;
		if(idmascota>0){
			consultarmascota();
		}
	}
	
	public int getIdmascota() {
		return idmascota;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

	public Petmascota getPetmascota() {
		return petmascota;
	}

	public void setPetmascota(Petmascota petmascota) {
		this.petmascota = petmascota;
	}

	public List<Petfoto> getLispetfoto() {
		return lispetfoto;
	}

	public void setLispetfoto(List<Petfoto> lispetfoto) {
		this.lispetfoto = lispetfoto;
	}

	public Petfoto getPetfotoSelected() {
		return petfotoSelected;
	}

	public void setPetfotoSelected(Petfoto petfotoSelected) {
		this.petfotoSelected = petfotoSelected;
	}

	public String getFileSeparator() {
		return fileSeparator;
	}

	public void setFileSeparator(String fileSeparator) {
		this.fileSeparator = fileSeparator;
	}

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

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getResources_server_url() {
		return resources_server_url;
	}

	private void consultarmascota(){
		try{
			petmascota = new PetmascotaBO().getPetmascotaById(idmascota);//Usado en p�gina side
			lispetfoto = new PetfotoBO().lisPetfotoByPetId(idmascota);
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			if(idmascota > 0){
				Petfoto petfoto = new Petfoto(0, new Petmascota(), new Petestado(), new Setusuario(), null, null, null, null, null, null, null);
				//petfoto.getPetmascota().setIdmascota(idmascota);
				petfoto.setPetmascota(petmascota);
				petfoto.setMostrar(0);
				petfoto.setNombrearchivo(event.getFile().getFileName());
				petfoto.setObjeto(event.getFile().getContents());
				
				new PetfotoBO().newPetfoto(petfoto);
				lispetfoto = new PetfotoBO().lisPetfotoByPetId(idmascota);
				new MessageUtil().showInfoMessage("Exito!", " Foto registrada!");
			}else{
				new MessageUtil().showWarnMessage("No procede!", "Antes de subir la im�gen debe consultar una mascota.");
			}
		} catch(Exception re) {
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void subirImagen(){
		try {
			if(uploadedFile != null){
				if(idmascota > 0){
					Petfoto petfoto = new Petfoto(0, new Petmascota(), new Petestado(), new Setusuario(), null, null, null, null, null, null, null);
					petfoto.setPetmascota(petmascota);
					petfoto.setMostrar(0);
					petfoto.setNombrearchivo(uploadedFile.getFileName());
					petfoto.setObjeto(uploadedFile.getContents());
					
					new PetfotoBO().newPetfoto(petfoto);
					lispetfoto = new PetfotoBO().lisPetfotoByPetId(idmascota);
					new MessageUtil().showInfoMessage("Exito!", " Foto registrada!");
				}else{
					new MessageUtil().showWarnMessage("Aviso!", "Antes de subir la im�gen debe consultar una mascota.");
				}
			}else{
				new MessageUtil().showWarnMessage("Aviso!", "Antes de subir la im�gen debe seleccionar una im�gen.");
			}
		} catch(Exception re) {
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void ponerFotoPerfil(ActionEvent actionEvent)
	{
		try {
			new PetfotoBO().ponerFotoPerfil(petfotoSelected);
			new MessageUtil().showInfoMessage("Exito!", "Im�gen puesta como foto del perfil!");
		} catch(Exception e){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void eliminarFotoAlbum(ActionEvent actionEvent)
	{
		try {
			String realpath = new FacesUtil().getRealPath("");
			FileUtil disco = new FileUtil();
			
			if(disco.deleteFile(realpath+Parametro.MASCOTAS_PATH+idmascota+Parametro.FILE_SEPARATOR+petfotoSelected.getNombrearchivo())){
				new PetfotoBO().eliminarFotoAlbum(petfotoSelected.getIdfoto());
				new MessageUtil().showInfoMessage("Exito!", " Foto eliminada!");
				lispetfoto = new PetfotoBO().lisPetfotoByPetId(idmascota);
			}
		} catch(Exception re) {
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public String verInformacion(){
		MenuBean menuBean = (MenuBean)new FacesUtil().getSessionBean("menuBean");
		String url = "mascota.jsf?faces-redirect=true&idmascota="+idmascota+"&tipo="+petmascota.getPetespecie().getIdespecie()+"&iditem="+menuBean.getActiveIdItem();
		return url;
	}
}