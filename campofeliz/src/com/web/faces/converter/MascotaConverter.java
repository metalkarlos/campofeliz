package com.web.faces.converter;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.web.pet.bo.PetmascotaBO;
import com.web.pet.pojo.annotations.Cotpersona;
import com.web.pet.pojo.annotations.Cottipoidentificacion;
import com.web.pet.pojo.annotations.Petespecie;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.pet.pojo.annotations.Petraza;
import com.web.pet.pojo.annotations.Setestado;
import com.web.pet.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

public class MascotaConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
		if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int id = Integer.parseInt(submittedValue);
                
                Petmascotahomenaje petmascotahomenaje = new Petmascotahomenaje(0, new Setestado(), new Setusuario(), new Petespecie(), null, null, null, null, null, null, null, null, null, null, null, null, new Petraza(), new Cotpersona(), new Cottipoidentificacion(), 1, new BigDecimal(0), null, false, false, null);
                
                if(id > 0){
                	PetmascotaBO petmascotaBO = new PetmascotaBO();
	                petmascotahomenaje = petmascotaBO.getPetmascotaById(id);
	                
	                if(petmascotahomenaje != null){
						if(petmascotahomenaje.getCottipoidentificacion() == null){
							petmascotahomenaje.setCottipoidentificacion(new Cottipoidentificacion(0,null,null));
						}
						if(petmascotahomenaje.getCotpersona() == null){
							petmascotahomenaje.setCotpersona(new Cotpersona());
						}
						if(petmascotahomenaje.getCottipoidentificacion() == null){
							petmascotahomenaje.setCottipoidentificacion(new Cottipoidentificacion());
						}
						if(petmascotahomenaje.getPetraza() == null){
							petmascotahomenaje.setPetraza(new Petraza());
						}
						if(petmascotahomenaje.getPetespecie() == null){
							petmascotahomenaje.setPetespecie(new Petespecie());
						}
					}else{
						petmascotahomenaje = new Petmascotahomenaje(0, new Setestado(), new Setusuario(), new Petespecie(), null, null, null, null, null, null, null, null, null, null, null, null, new Petraza(), new Cotpersona(), new Cottipoidentificacion(), 1, new BigDecimal(0), null, false, false, null);
					}
                }
                
                return petmascotahomenaje;
            } catch(Exception ex) {
            	ex.printStackTrace();
            	new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!","");
            }
        }

        return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent component, Object value) {
		if (value instanceof Petmascotahomenaje){
			return String.valueOf(((Petmascotahomenaje) value).getIdmascota());
		} else {
			return "";
		}
	}

}
