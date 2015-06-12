package com.web.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.web.pet.bo.PetfotomascotaBO;
import com.web.pet.bo.PetmascotaBO;
import com.web.pet.pojo.annotations.Mascotas;
import com.web.pet.pojo.annotations.Petfotomascota;
import com.web.pet.pojo.annotations.Petmascotahomenaje;
import com.web.util.MessageUtil;

public class MascotaConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
		if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int id = Integer.parseInt(submittedValue);
                
                Mascotas mascotas = new Mascotas();
                
                if(id > 0){
	                Petmascotahomenaje petmascotahomenaje = new PetmascotaBO().getPetmascotaById(id);
					Petfotomascota petfotomascota = new PetfotomascotaBO().getPetfotomascotaPerfilByIdmascota(petmascotahomenaje.getIdmascota());
					mascotas.setPetmascotahomenaje(petmascotahomenaje);
					mascotas.setPetfotomascota(petfotomascota);
                }
                
                return mascotas;
            } catch(Exception ex) {
            	ex.printStackTrace();
            	new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
            }
        }

        return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent component, Object value) {
		if (value instanceof Mascotas){
			return String.valueOf(((Mascotas) value).getPetmascotahomenaje().getIdmascota());
		} else {
			return "";
		}
	}

}
