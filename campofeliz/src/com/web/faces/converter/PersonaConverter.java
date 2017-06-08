package com.web.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.web.pet.bo.CotpersonaBO;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@FacesConverter("persona")
public class PersonaConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
		if (submittedValue.trim().equals("")) {
            return null;
        } else {
		//if (submittedValue != null && submittedValue.trim().length() > 0) {
            try {
                int id = Integer.parseInt(submittedValue);
                Cotpersona cotpersona = new Cotpersona(0, new Cottipoidentificacion(0,null,null), new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
                
                if(id > 0){
	                cotpersona = new CotpersonaBO().getCotpersonaById(id);
	                if(cotpersona == null){
	                	cotpersona = new Cotpersona(0, new Cottipoidentificacion(0,null,null), new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, null);
	                }else{
						if(cotpersona.getCottipoidentificacion() == null){
							cotpersona.setCottipoidentificacion(new Cottipoidentificacion(0,null,null));
						}
					}
                }
                
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
