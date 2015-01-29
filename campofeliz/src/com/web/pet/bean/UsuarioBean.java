package com.web.pet.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.web.pet.pojo.annotations.Setusuario;
import com.web.pet.bo.SetusuarioBO;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5577462729566726719L;
	private Setusuario setUsuario;
	private String username;
	private String password;
	private String ip;
	private String sid;
	private boolean autenticado;
	
	public UsuarioBean(){
		ip = new FacesUtil().getIp();
		sid = new FacesUtil().getSid();
		setUsuario = new Setusuario();
	}

	public void setSetUsuario(Setusuario setUsuario) {
		this.setUsuario = setUsuario;
	}

	public Setusuario getSetUsuario() {
		return setUsuario;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getIp() {
		return ip;
	}

	public String getSid() {
		return sid;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public String login(){
		String strRedirect = null;
		
		try{
			setUsuario = new SetusuarioBO().getByUserPasswd(username, password);
			
			if(setUsuario!=null && setUsuario.getIdusuario()>0){
				autenticado = true;
				FacesUtil facesUtil = new FacesUtil();
				strRedirect = (String) facesUtil.getSessionBean("urlrequested");
				
				if(strRedirect != null && !strRedirect.isEmpty()){
					facesUtil.removeSessionBean("urlrequested");
				}else{
					FileUtil fileUtil = new FileUtil();
					strRedirect = fileUtil.getPropertyValue("home");
				}
			}else{
				new MessageUtil().showWarnMessage("Autenticación fallida","Usuario o Contraseña no existen.");
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
		return strRedirect;
	}
	
	public String logout(){
		FacesUtil facesUtil = new FacesUtil();
		
		try{
			facesUtil.logout();
			FileUtil fileUtil = new FileUtil();
			String strLogin = fileUtil.getPropertyValue("login");
			facesUtil.redirect("../pages/"+strLogin);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		return "";
	}
	
	public String redirect(){
		try{
			FileUtil fileUtil = new FileUtil();
			String strnotlogged = fileUtil.getPropertyValue("notlogged");
			new FacesUtil().redirect("../pages/"+strnotlogged);
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		return "";
	}
	
}