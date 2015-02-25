package com.web.pet.bean;


	import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import com.web.pet.bo.CotfotopersonaBO;
import com.web.pet.bo.CotpersonaBO;
import com.web.pet.global.Parametro;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cotfotopersona;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

	@ManagedBean
	@ViewScoped
	public class FotosPersonaBean implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1517352078005227312L;
		private List<Cotfotopersona> liscotfotopersona;
		private int idpersona;
		private Cotpersona cotpersona;
		private Cotfotopersona cotfotopersonaSelected;
		private String fileSeparator;
		private UploadedFile uploadedFile;
		private String rutaImagenes;
		
		public FotosPersonaBean(){
			liscotfotopersona = new ArrayList<Cotfotopersona>();
			cotpersona = new Cotpersona(0, null, null, null, null);
			setFileSeparator(Parametro.FILE_SEPARATOR);
			
			cargarRutaImagenes();
		}
		
		@PostConstruct
		public void initFotosPersonaBean() {
			FacesUtil facesUtil = new FacesUtil();
			idpersona = Integer.parseInt(facesUtil.getParametroUrl("idpersona") != null ? facesUtil
							.getParametroUrl("idpersona").toString() : "0");
			
			if(idpersona > 0){
				try{
					CotpersonaBO cotpersonaBO = new CotpersonaBO();
					CotfotopersonaBO cotfotopersonaBO = new  CotfotopersonaBO();
					cotpersona = cotpersonaBO.getCotpersonaById(idpersona);
					liscotfotopersona = cotfotopersonaBO.lisCotfotopersonaByCotId(idpersona);
				} catch (Exception e) {
					e.printStackTrace();
					new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
				}
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
		
		public void setIdpersona(int idpersona) {
			this.idpersona = idpersona;
		}
		
		public int getIdpersona() {
			return idpersona;
		}



		public List<Cotfotopersona> getLiscotfotopersona() {
			return liscotfotopersona;
		}

		public void setLiscotfotopersona(List<Cotfotopersona> liscotfotopersona) {
			this.liscotfotopersona = liscotfotopersona;
		}


		public Cotpersona getCotpersona() {
			return cotpersona;
		}

		public void setCotpersona(Cotpersona cotpersona) {
			this.cotpersona = cotpersona;
		}

		public Cotfotopersona getCotfotopersonaSelected() {
			return cotfotopersonaSelected;
		}

		public void setCotfotopersonaSelected(Cotfotopersona cotfotopersonaSelected) {
			this.cotfotopersonaSelected = cotfotopersonaSelected;
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

		public void handleFileUpload(FileUploadEvent event) {
			try {
				if(idpersona > 0){
					Cotfotopersona cotfotopersona = new Cotfotopersona(0, new Cotpersona(), new Setestado(), new Setusuario(), null, null, null, null, null, null, null);
					cotfotopersona.setCotpersona(cotpersona);
					cotfotopersona.setMostrar(0);
					cotfotopersona.setNombrearchivo(event.getFile().getFileName());
					cotfotopersona.setObjeto(event.getFile().getContents());
					
					new CotfotopersonaBO().newCotfotopersona(cotfotopersona);
					liscotfotopersona = new CotfotopersonaBO().lisCotfotopersonaByCotId(idpersona);
					new MessageUtil().showInfoMessage("Exito!", " Foto registrada!");
				}else{
					new MessageUtil().showWarnMessage("No procede!", "Antes de subir la imágen debe consultar el propietario de la mascota.");
				}
			} catch(Exception re) {
				re.printStackTrace();
				new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		
		public void subirImagen(){
			try {
				if(uploadedFile != null){
					if(idpersona > 0){
						Cotfotopersona cotfotopersona = new Cotfotopersona(0, new Cotpersona(), new Setestado(), new Setusuario(), null, null, null, null, null, null, null);
						cotfotopersona.setCotpersona(cotpersona);
						cotfotopersona.setMostrar(0);
						cotfotopersona.setNombrearchivo(uploadedFile.getFileName());
						cotfotopersona.setObjeto(uploadedFile.getContents());
						
						new CotfotopersonaBO().newCotfotopersona(cotfotopersona);
						liscotfotopersona = new CotfotopersonaBO().lisCotfotopersonaByCotId(idpersona);
						new MessageUtil().showInfoMessage("Exito!", " Foto registrada!");
					}else{
						new MessageUtil().showWarnMessage("Aviso!", "Antes de subir la imágen debe consultar el propietario de la mascota.");
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
				new CotfotopersonaBO().ponerFotoPerfil(cotfotopersonaSelected);
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
				String rutaArchivo = rutaImagenes + cotfotopersonaSelected.getRuta();
				
				fileUtil.deleteFile(rutaArchivo);
				
				if(fileUtil.deleteFile(rutaArchivo)){
					new CotfotopersonaBO().eliminarFotoAlbum(cotfotopersonaSelected.getIdfoto());
					new MessageUtil().showInfoMessage("Exito!", " Foto eliminada!");
					liscotfotopersona = new CotfotopersonaBO().lisCotfotopersonaByCotId(idpersona);
				}
			} catch(Exception re) {
				re.printStackTrace();
				new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		
		public String verInformacion(){
			MenuBean menuBean = (MenuBean)new FacesUtil().getSessionBean("menuBean");
			String url = "personas.jsf?faces-redirect=true&idpersona="+idpersona+"&iditem="+menuBean.getActiveIdItem();
			return url;
		}

		public String getRutaImagenes() {
			return rutaImagenes;
		}

		public void setRutaImagenes(String rutaImagenes) {
			this.rutaImagenes = rutaImagenes;
		}
	}

	

