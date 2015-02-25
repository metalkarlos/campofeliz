package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Petmascotacolor;

public class PetmascotacolorDAO {

	public int maxIdPetmascotacolor(Session session) throws Exception {
		int max = 0;
		
		Criteria criteria = session.createCriteria(Petmascotacolor.class)
		.setProjection(Projections.max("idmascotacolor"));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascotacolor> lisPetmascotacolor(Session session, int idmascota) throws Exception {
		List<Petmascotacolor> lisPetmascotacolor = null;
		
		Criteria criteria = session.createCriteria(Petmascotacolor.class)
		.add( Restrictions.eq("petmascotahomenaje.idmascota", idmascota))
		.add( Restrictions.eq("setestado.idestado", 1))
		.createAlias("cotcolor", "color");
		
		lisPetmascotacolor = (List<Petmascotacolor>)criteria.list();
		
		return lisPetmascotacolor;
	}

	public void savePetmascotacolor(Session session, Petmascotacolor petmascotacolor) throws Exception {
		session.save(petmascotacolor);
	}

	public void updatePetmascotacolor(Session session, Petmascotacolor petmascotacolor) throws Exception {
		session.update(petmascotacolor);
	}

}
