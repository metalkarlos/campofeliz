package com.web.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.web.pet.bo.CotservicioBO;
import com.web.pet.pojo.annotations.Cotservicio;
import com.web.util.MessageUtil;

public class ServicioConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
		if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int id = Integer.parseInt(submittedValue);
                Cotservicio cotservicio = new CotservicioBO().getCotservicioById(id);
                return cotservicio;
            } catch(Exception ex) {
            	ex.printStackTrace();
            	new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
            }
        }

        return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent component, Object value) {
		if (value instanceof Cotservicio){
			return String.valueOf(((Cotservicio) value).getIdservicio());
		} else {
			return "";
		}
	}

}
