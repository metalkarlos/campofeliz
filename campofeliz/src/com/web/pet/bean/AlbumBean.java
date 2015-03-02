package com.web.pet.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.web.pet.bo.PetfotoBO;
import com.web.pet.bo.PetmascotaBO;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Petfotomascota;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class AlbumBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1517352078005227312L;
	private List<Petfotomascota> lispetfotomascota;
	private int idmascota;
	private int tipo;
	private Petmascotahomenaje petmascotahomenaje;
	private Petfotomascota petfotomascotaSelected;
	private String fileSeparator;
	private UploadedFile uploadedFile;
	
	public AlbumBean(){
		lispetfotomascota = new ArrayList<Petfotomascota>();
		petmascotahomenaje = new Petmascotahomenaje(0,new Setestado(),new Setusuario(),new Petespecie(),null,null,null,null,null,null,null,null,null,null,null,null,new Petraza(),new Cotpersona(),new Cottipoidentificacion(),0,new BigDecimal(0),null,false,false,null);
		petfotomascotaSelected = new Petfotomascota();
		setFileSeparator(Parametro.FILE_SEPARATOR);
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

	public Petmascotahomenaje getPetmascotahomenaje() {
		return petmascotahomenaje;
	}

	public void setPetmascotahomenaje(Petmascotahomenaje petmascotahomenaje) {
		this.petmascotahomenaje = petmascotahomenaje;
	}

	public List<Petfotomascota> getLispetfotomascota() {
		return lispetfotomascota;
	}

	public void setLispetfotomascota(List<Petfotomascota> lispetfotomascota) {
		this.lispetfotomascota = lispetfotomascota;
	}

	public Petfotomascota getPetfotomascotaSelected() {
		return petfotomascotaSelected;
	}

	public void setPetfotomascotaSelected(Petfotomascota petfotomascotaSelected) {
		this.petfotomascotaSelected = petfotomascotaSelected;
	}

	public String getFileSeparator() {
		return fileSeparator;
	}

	public void setFileSeparator(String fileSeparator) {
		this.fileSeparator = fileSeparator;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	private void consultarmascota(){
		try{
			petmascotahomenaje = new PetmascotaBO().getPetmascotaById(idmascota);//Usado en página side
			lispetfotomascota = new PetfotoBO().lisPetfotoByPetId(idmascota);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			if(idmascota > 0){
				Petfotomascota petfotomascota = new Petfotomascota(0,new Setestado(),new Petmascotahomenaje(),new Setusuario(),null,null,null,0,null,null,null,0);
				petfotomascota.setPetmascotahomenaje(petmascotahomenaje);
				petfotomascota.setMostrar(0);
				petfotomascota.setNombrearchivo(event.getFile().getFileName());
				petfotomascota.setObjeto(event.getFile().getContents());
				
				new PetfotoBO().newPetfoto(petfotomascota);
				lispetfotomascota = new PetfotoBO().lisPetfotoByPetId(idmascota);
				new MessageUtil().showInfoMessage("Exito!", " Foto registrada!");
			}else{
				new MessageUtil().showWarnMessage("No procede!", "Antes de subir la imágen debe consultar una mascota.");
			}
		} catch(Exception re) {
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void subirImagen(){
		try {
			if(uploadedFile != null){
				if(idmascota > 0){
					Petfotomascota petfotomascota = new Petfotomascota(0,new Setestado(),new Petmascotahomenaje(),new Setusuario(),null,null,null,0,null,null,null,0);
					petfotomascota.setPetmascotahomenaje(petmascotahomenaje);
					petfotomascota.setMostrar(0);
					petfotomascota.setNombrearchivo(uploadedFile.getFileName());
					petfotomascota.setObjeto(uploadedFile.getContents());
					
					new PetfotoBO().newPetfoto(petfotomascota);
					lispetfotomascota = new PetfotoBO().lisPetfotoByPetId(idmascota);
					new MessageUtil().showInfoMessage("Exito!", " Foto registrada!");
				}else{
					new MessageUtil().showWarnMessage("Aviso!", "Antes de subir la imágen debe consultar una mascota.");
				}
			}else{
				new MessageUtil().showWarnMessage("Aviso!", "Antes de subir la imágen debe seleccionar una imágen.");
			}
		} catch(Exception re) {
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void ponerFotoPerfil(ActionEvent actionEvent)
	{
		try {
			new PetfotoBO().ponerFotoPerfil(petfotomascotaSelected);
			new MessageUtil().showInfoMessage("Exito!", "Imágen puesta como foto del perfil!");
		} catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void eliminarFotoAlbum(ActionEvent actionEvent)
	{
		try {
			//eliminar foto del disco
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			
			String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
			String rutaArchivo = rutaImagenes + petfotomascotaSelected.getRuta();
			
			fileUtil.deleteFile(rutaArchivo);
			
			if(fileUtil.deleteFile(rutaArchivo)){
				new PetfotoBO().eliminarFotoAlbum(petfotomascotaSelected.getIdfotomascota());
				new MessageUtil().showInfoMessage("Exito!", " Foto eliminada!");
				lispetfotomascota = new PetfotoBO().lisPetfotoByPetId(idmascota);
			}
		} catch(Exception re) {
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public String verInformacion(){
		MenuBean menuBean = (MenuBean)new FacesUtil().getSessionBean("menuBean");
		String url = "mascota.jsf?faces-redirect=true&idmascota="+idmascota+"&tipo="+petmascotahomenaje.getPetespecie().getIdespecie()+"&iditem="+menuBean.getActiveIdItem();
		return url;
	}

}
