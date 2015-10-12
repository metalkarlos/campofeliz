package com.web.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.web.pet.bo.CotpersonaBO;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.util.MessageUtil;

public class PersonaConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
		if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int id = Integer.parseInt(submittedValue);
                Cotpersona cotpersona = new CotpersonaBO().getCotpersonaById(id);
                return cotpersona;
            } catch(Exception ex) {
            	ex.printStackTrace();
            	new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
            }
        }

        return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent component, Object value) {
		if (value instanceof Cotpersona){
			return String.valueOf(((Cotpersona) value).getIdpersona());
		} else {
			return "";
		}
	}

}
