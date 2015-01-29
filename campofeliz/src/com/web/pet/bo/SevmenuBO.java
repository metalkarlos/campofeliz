package com.web.pet.bo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.dao.SevmenuDAO;
import com.web.pet.pojo.annotations.Menu;
import com.web.pet.pojo.annotations.Sevmenu;
import com.web.util.HibernateUtil;

public class SevmenuBO {

	public List<Menu> lisCrearMenu() throws Exception {
		List<Sevmenu> dataset = null;
		Menu menu = new Menu();
		List<Menu> lisMenu = new ArrayList<Menu>();
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			SevmenuDAO setmenuDAO = new SevmenuDAO();
			
			dataset = setmenuDAO.lisSetmenu(session);
			
			for(Sevmenu fila : dataset){
				if(fila.getNivel()==0){//Nivel Padre
					if(menu.getIdmenu()!=null){
						lisMenu.add(menu);
						menu = new Menu();
					}
					menu.setIdmenu(fila.getIdmenu());
					menu.setNombre(fila.getNombre());
					menu.setIdmenupadre(fila.getIdmenupadre());
					menu.setIconourl(fila.getIconourl());
					menu.setPaginaurl(fila.getPaginaurl());
					menu.setOrden(fila.getOrden()-1);
				}
				if(fila.getIdmenupadre().compareTo(menu.getIdmenu())==0){//Nivel Hijo
					menu.getLisSevOpcionMenu().add(fila);
				}
			}
			//rezagado
			if(menu.getIdmenu()!=null){
				lisMenu.add(menu);
			}
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisMenu;
	}
	
	public List<Menu> lisCrearMenuManual() throws Exception {
		List<Sevmenu> lisSetmenu = null;
		Menu menu = new Menu();
		List<Menu> lisMenu = new ArrayList<Menu>();
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			SevmenuDAO setmenuDAO = new SevmenuDAO();
			
			lisSetmenu = setmenuDAO.lisSetmenuPadres(session);
			
			for(Sevmenu setmenuPadre : lisSetmenu){
				menu = new Menu();
				menu.setIdmenu(setmenuPadre.getIdmenu());
				menu.setNombre(setmenuPadre.getNombre());
				menu.setIdmenupadre(setmenuPadre.getIdmenupadre());
				menu.setIconourl(setmenuPadre.getIconourl());
				menu.setPaginaurl(setmenuPadre.getPaginaurl());
				menu.setOrden(setmenuPadre.getOrden()-1);
					
				List<Sevmenu> lisSetmenuHijo = setmenuDAO.lisSetmenuHijos(session, setmenuPadre.getIdmenu());
				
				for(Sevmenu setmenuHijo : lisSetmenuHijo){
					menu.getLisSevOpcionMenu().add(setmenuHijo);
				}
				
				lisMenu.add(menu);
			}
		}catch(Exception he){
			throw new Exception();
		}finally{
			session.close();
		}
		
		return lisMenu;
	}

}
