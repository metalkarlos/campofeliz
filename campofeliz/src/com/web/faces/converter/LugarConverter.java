package com.web.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.web.pet.bo.CotlugarBO;
import com.web.pet.pojo.annotations.Cotlugar;
import com.web.util.MessageUtil;

public class LugarConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
		if (submittedValue != null && submittedValue.trim().length() > 0) {
            try {
                int id = Integer.parseInt(submittedValue);
                CotlugarBO cotlugarBO = new CotlugarBO();
                Cotlugar cotlugar = cotlugarBO.getCotlugarById(id);
                cotlugar = cotlugar == null ? new Cotlugar() : cotlugar;
                return cotlugar;
            } catch(Exception ex) {
            	ex.printStackTrace();
            	new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
            }
        }

        return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent component, Object value) {
		if (value instanceof Cotlugar){
			return String.valueOf(((Cotlugar) value).getIdlugar());
		} else {
			return "";
		}
	}

}
