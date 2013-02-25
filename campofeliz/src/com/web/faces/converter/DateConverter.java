package com.web.faces.converter;

import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class DateConverter implements Converter {
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		return submittedValue;
	}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
		return value == null ? null : format.format(value);
	}
}
