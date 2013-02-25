package com.web.pet.pojo.annotations;


import java.util.ArrayList;
import java.util.List;

public class Menu extends Sevmenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4290141524707592838L;
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
