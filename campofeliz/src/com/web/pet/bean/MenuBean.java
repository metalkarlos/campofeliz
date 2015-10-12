package com.web.pet.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.TabChangeEvent;

import com.web.pet.bo.SevmenuBO;
import com.web.pet.pojo.annotations.Menu;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;

@ManagedBean
@SessionScoped
public class MenuBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3939592107192490958L;
	private List<Menu> lisMenu;
	private int activeIndex;
	private int activeIdItem;
	private String logoMenu;

	public MenuBean() {
		initializeMenu();
		activeIndex = 0;
		activeIdItem = 0;
	}
	
	public void initializeMenu(){
		try{
			lisMenu = new SevmenuBO().lisCrearMenuManual();
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}
	
	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public int getActiveIdItem() {
		return activeIdItem;
	}

	public void setActiveIdItem(int activeIdItem) {
		this.activeIdItem = activeIdItem;
	}

	public String getLogoMenu() {
		return logoMenu;
	}

	public void setLogoMenu(String logoMenu) {
		this.logoMenu = logoMenu;
	}

	public List<Menu> getLisMenu() {
		return lisMenu;
	}
	
	public void tabChangeListener(TabChangeEvent tabChangeEvent){
		//para mostrar una página por cada tab
		
		/*String id = tabChangeEvent.getTab().getId();
		setActiveIndex(Integer.parseInt(id.substring(3, id.length())));*/

		try{
			Menu menu = (Menu)lisMenu.get(getActiveIndex());
			setLogoMenu(menu.getIconourl());
			
			if(menu.getPaginaurl() != null){
				new FacesUtil().redirect("../admin/"+menu.getPaginaurl()+"?iditem="+menu.getIdmenu());
			}
		}catch(Exception re){
			re.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
		}
	}

}
