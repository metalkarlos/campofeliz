package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.web.pet.bo.CotfotopersonaBO;
import com.web.pet.bo.CotpersonaBO;
import com.web.pet.bo.CottipoidentificacionBO;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Cotfotopersona;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class PersonaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2947013478292278955L;
	
	private String rutaImagenes;
	private int idpersona;
	private Cotfotopersona cotfotopersona;
	private Cotpersona cotpersona;
	private Cotfotopersona cotfotopersonaSelected;
	private Cottipoidentificacion cottipoidentificacionselected;
	private List<Cottipoidentificacion> lisCottipoidentificacion;
	private List<Cotfotopersona> liscotfotopersona;	

	
	public PersonaBean() {
		cotpersona = new Cotpersona(0, null, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		cottipoidentificacionselected = new Cottipoidentificacion();
		cotfotopersonaSelected = new Cotfotopersona();
		cotfotopersona = new Cotfotopersona();
		//idpersona= 0;
		llenarLisTipoidentificacion();
	}
	
	@PostConstruct
	public void initPersonaBean() {
		FacesUtil facesUtil = new FacesUtil();
		liscotfotopersona = new ArrayList<Cotfotopersona>();
		idpersona = Integer.parseInt(facesUtil.getParametroUrl("idpersona") != null ? facesUtil
						.getParametroUrl("idpersona").toString() : "0");
		if(idpersona > 0){
			try{
				CotpersonaBO cotpersonaBO = new CotpersonaBO();
				cotpersona = cotpersonaBO.getCotpersonaById(idpersona);
	            if(cotpersona != null && cotpersona.getCottipoidentificacion() != null && cotpersona.getCottipoidentificacion().getIdtipoidentificacion() > 0){
					setCottipoidentificacionselected(cotpersona.getCottipoidentificacion());
				}
	            //Consultar fotos
				CotfotopersonaBO cotfotopersonaBO = new  CotfotopersonaBO();
				liscotfotopersona = cotfotopersonaBO.lisCotfotopersonaByCotId(idpersona);
			   } catch (Exception e) {
				e.printStackTrace();
				new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			  }
		}
	}
	
	
	
	private void llenarLisTipoidentificacion(){
		try{
			Cottipoidentificacion cottipoidentificacion = new Cottipoidentificacion();
			cottipoidentificacion.setIdtipoidentificacion(0);
			cottipoidentificacion.setNombre("Seleccione");
			cottipoidentificacion.setSetestado(new Setestado());
			cottipoidentificacion.setSetusuario(new Setusuario());
		
			lisCottipoidentificacion = new ArrayList<Cottipoidentificacion>();
			lisCottipoidentificacion.add(cottipoidentificacion);
			
			CottipoidentificacionBO cottipoidentificacionBO = new CottipoidentificacionBO();
			List<Cottipoidentificacion> lisTmp = cottipoidentificacionBO.lisCottipoidentificacion();
			if(lisTmp != null && lisTmp.size() > 0){
				lisCottipoidentificacion.addAll(lisTmp);
			}
		}catch(Exception e){
			e.printStackTrace();
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
	
	
	public Cotpersona getCotpersona() {
		return cotpersona;
	}
	public void setCotpersona(Cotpersona cotpersona) {
		this.cotpersona = cotpersona;
	}

	public int getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

	public List<Cottipoidentificacion> getLisCottipoidentificacion() {
		return lisCottipoidentificacion;
	}

	public void setLisCottipoidentificacion(List<Cottipoidentificacion> lisCottipoidentificacion) {
		this.lisCottipoidentificacion = lisCottipoidentificacion;
	}
	
	public Cotfotopersona getCotfotopersona() {
		return cotfotopersona;
	}

	public void setCotfotopersona(Cotfotopersona cotfotopersona) {
		this.cotfotopersona = cotfotopersona;
	}
	/*public UploadedFile getFile() {
		return file;
	}*/

	/*public void setFile(UploadedFile file) {
		this.file = file;
	}*/

	/*public StreamedContent getFoto() {
		FacesUtil facesUtil = new FacesUtil();
		StreamedContent object = (StreamedContent) facesUtil.getSessionBean("foto");
		if(object != null){
			foto = object;
		}
		return foto;
	}*/

	/*public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}*/

	/*public void handleFileUpload(FileUploadEvent event) {
		try {
			cotpersona.setObjeto(event.getFile().getContents());
			nombreFoto = event.getFile().getFileName();

			//se crea la imagen en el disco del servidor temporalmente
			FileUtil fileUtil = new FileUtil();
			String fileExtention = fileUtil.getFileExtention(nombreFoto);
			String newNombreFoto = cotpersona.getSexo()+"-"+cotpersona.getIdpersona()+"."+fileExtention;
			FacesUtil facesUtil = new FacesUtil();
			String rutaCompletaDir = facesUtil.getRealPath("")+Parametro.PERSONAS_PATH+cotpersona.getIdpersona();
			
			if(fileUtil.createDir(rutaCompletaDir)){
				String rutaCompletaFile = rutaCompletaDir+Parametro.FILE_SEPARATOR+newNombreFoto;
				fileUtil.createFile(rutaCompletaFile,cotpersona.getObjeto());
			}
			
			//una vez creada la imagen en el servidor se actualiza la ruta
			rutaFotoWar = Parametro.PERSONAS_PATH + cotpersona.getIdpersona() + Parametro.FILE_SEPARATOR + newNombreFoto;
			cotpersona.setRuta(rutaFotoWar);
		} catch(Exception re) {
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}*/
	
	/*public void handleFileUpload2(FileUploadEvent event) {
		try {
			cotpersona.setObjeto(event.getFile().getContents());
			nombreFoto = event.getFile().getFileName();

			StreamedContent object = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType(), "nombreFoto");
			FacesUtil facesUtil = new FacesUtil();
			facesUtil.setSessionBean("foto", object);
			//foto = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType(), "nombreFoto");
		} catch(Exception re) {
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}*/
	
	public Cottipoidentificacion getCottipoidentificacionselected() {
		return cottipoidentificacionselected;
	}

	public void setCottipoidentificacionselected(
			Cottipoidentificacion cottipoidentificacionselected) {
		this.cottipoidentificacionselected = cottipoidentificacionselected;
	}

	public Cotfotopersona getCotfotopersonaSelected() {
		return cotfotopersonaSelected;
	}

	public void setCotfotopersonaSelected(Cotfotopersona cotfotopersonaSelected) {
		this.cotfotopersonaSelected = cotfotopersonaSelected;
	}

	public List<Cotfotopersona> getLiscotfotopersona() {
		return liscotfotopersona;
	}

	public void setLiscotfotopersona(List<Cotfotopersona> liscotfotopersona) {
		this.liscotfotopersona = liscotfotopersona;
	}

	public void grabar(){
		try{
			if(validarCampos()){
				CotpersonaBO cotpersonaBO = new CotpersonaBO();
				boolean ok = false;
				
				if(cottipoidentificacionselected != null && cottipoidentificacionselected.getIdtipoidentificacion() > 0){
					cotpersona.setCottipoidentificacion(cottipoidentificacionselected);
				}else{
					cotpersona.setCottipoidentificacion(null);
				}
				
				if(idpersona > 0){
					ok = cotpersonaBO.updateCotpersona(cotpersona);
				}else{
					ok = cotpersonaBO.newCotpersona(cotpersona);
				}
				
				if(ok){
					new MessageUtil().showInfoMessage("Exito!", "Registro completo!");
				}
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public String cancelar(){
		//si subió una foto pero luego cancela, hay que eliminar la foto del disco del servidor
		/*if(rutaFotoWar != null){
			try {
				FileUtil fileUtil = new FileUtil();
				String fileExtention = fileUtil.getFileExtention(nombreFoto);
				
				FacesUtil facesUtil = new FacesUtil();
				String rutaCompletaFile = facesUtil.getRealPath("")+Parametro.PERSONAS_PATH+cotpersona.getIdpersona()+Parametro.FILE_SEPARATOR+cotpersona.getSexo()+"-"+cotpersona.getIdpersona()+"."+fileExtention;
				
				fileUtil.deleteFile(rutaCompletaFile);
			} catch(Exception re) {
				new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}*/
		return null;//"personas.jsf?faces-redirect=true&iditem=36";
	}
	
	private boolean validarCampos(){
		boolean ok = true;
		
		if(cotpersona.getApellido1() == null || cotpersona.getApellido1().trim().length() == 0){
			new MessageUtil().showWarnMessage("Datos incompletos!", "El Primer Apellido es obligatorio!");
			ok = false;
		}else{
			if(cotpersona.getNombre1() == null || cotpersona.getNombre1().trim().length() == 0){
				new MessageUtil().showWarnMessage("Datos incompletos!", "El Primer Nombre es obligatorio!");
				ok = false;
			}else{
				if(cottipoidentificacionselected != null && cottipoidentificacionselected.getIdtipoidentificacion() > 0 && (cotpersona.getNumeroidentificacion() == null || cotpersona.getNumeroidentificacion().trim().length() == 0)){
					new MessageUtil().showWarnMessage("Datos incompletos!", "Si selecciona el Tipo de Identificación tambien debe ingresar el Número de Identificación!");
					ok = false;
				}else{
					if(cotpersona.getNumeroidentificacion() != null && cotpersona.getNumeroidentificacion().trim().length() > 0 && (cottipoidentificacionselected == null || cottipoidentificacionselected.getIdtipoidentificacion() == 0)){
						new MessageUtil().showWarnMessage("Datos incompletos!", "Si ingresa El Número de Identificación tambien debe seleccionar el Tipo de Identificación!");
						ok = false;
					}
				}
			}
		}
		
		return ok;
	}
	
	public void eliminar(){
		try{
			Setestado setestado = new Setestado();
			setestado.setIdestado(2);//inactivo
			cotpersona.setSetestado(setestado);
			CotpersonaBO cotpersonaBO = new CotpersonaBO();
			cotpersonaBO.updateCotpersona(cotpersona);
			FacesUtil facesUtil = new FacesUtil();
			facesUtil.redirect("../admin/personas.jsf?iditem=36");
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
}
