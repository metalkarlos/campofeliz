package com.web.pet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@SessionScoped
public class ThemeBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7325594951285571070L;
	private String defaultTheme;
	private String themeSelected;
	private List<SelectItem> themeList;
	private Properties properties;

	public ThemeBean() {
		themeList = new ArrayList<SelectItem>();
		properties = new Properties();
		loadThemes();
	}
	
	private void loadThemes()
	{
		try{
			properties = new FileUtil().getPropertiesFile("themes.properties");
			Enumeration<Object> keys = properties.keys();
			while(keys.hasMoreElements()){
				String key = (String) keys.nextElement();
				String value = properties.getProperty(key);
				themeList.add(new SelectItem(key, value));
			}
			
			if(properties.containsKey("default")){
				themeSelected = "default";
				defaultTheme = properties.getProperty(themeSelected);
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error al cargar los Temas. Comunicar al Webmaster!");
		}
	}
	
	public String getDefaultTheme() {
		return defaultTheme;
	}

	public void setDefaultTheme(String defaultTheme) {
		this.defaultTheme = defaultTheme;
	}

	public String getThemeSelected() {
		return themeSelected;
	}

	public void setThemeSelected(String themeSelected) {
		this.themeSelected = themeSelected;
		
		if(themeSelected.compareTo("default") == 0){
			this.defaultTheme = properties.getProperty(themeSelected);
		}
		else{
			this.defaultTheme = themeSelected;
		}
	}

	public List<SelectItem> getThemeList() {
		return themeList;
	}

	public void valueChangeListener(ValueChangeEvent valuechangeEvent){	}

}
