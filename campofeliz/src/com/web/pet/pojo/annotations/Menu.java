package com.web.pet.pojo.annotations;


import java.util.ArrayList;
import java.util.List;

public class Menu extends Sevmenu implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -151373518798583230L;
	private List<Sevmenu> lisSevOpcionMenu;
	
	public Menu(){
		lisSevOpcionMenu = new ArrayList<Sevmenu>();
	}
	
	public List<Sevmenu> getLisSevOpcionMenu() {
		return lisSevOpcionMenu;
	}

	public void setLisSevOpcionMenu(List<Sevmenu> lisSevOpcionMenu) {
		this.lisSevOpcionMenu = lisSevOpcionMenu;
	}
	
}
