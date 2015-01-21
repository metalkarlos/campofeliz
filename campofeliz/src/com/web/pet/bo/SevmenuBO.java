package com.web.pet.bo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.web.pet.daointerface.SevmenuDAOInterface;
import com.web.pet.pojo.annotations.Menu;
import com.web.pet.pojo.annotations.Sevmenu;
import com.web.util.HibernateUtil;

public class SevmenuBO {
	
	private Session session = null; 
	private SevmenuDAOInterface sevmenuDAOInterface;
	
	public SevmenuBO() throws RuntimeException {
		try{
			sevmenuDAOInterface = (SevmenuDAOInterface) SevmenuBO.class.getClassLoader().loadClass("com.web.pet.dao.SevmenuDAO").newInstance();
        }catch(Exception ex){
            throw new RuntimeException("Problemas al cargar la interfaz SevmenuDAOInterface");
        }
	}

	public List<Menu> lisCrearMenu() throws Exception {
		List<Sevmenu> dataset = null;
		Menu menu = new Menu();
		List<Menu> lisMenu = new ArrayList<Menu>();
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			dataset = sevmenuDAOInterface.lisSevmenu(session);
			
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

}
