package com.web.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.web.pet.bo.CotcolorBO;
import com.web.pet.pojo.annotations.Cotcolor;
import com.web.util.MessageUtil;

@FacesConverter("color")
public class ColorConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
		if (submittedValue != null && submittedValue.trim().length() > 0) {
            try {
                int id = Integer.parseInt(submittedValue);
                Cotcolor cotcolor = new CotcolorBO().getCotcolorById(id);
                return cotcolor;
            } catch(Exception ex) {
            	ex.printStackTrace();
            	new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
            }
        }

        return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent component, Object value) {
		if (value instanceof Cotcolor){
			return String.valueOf(((Cotcolor) value).getIdcolor());
		} else {
			return "";
		}
	}

}
