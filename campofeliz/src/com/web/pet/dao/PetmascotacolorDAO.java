package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.daointerface.PetmascotacolorDAOInterface;
import com.web.pet.pojo.annotations.Petmascotacolor;

public class PetmascotacolorDAO implements PetmascotacolorDAOInterface {

	@Override
	public int maxIdPetmascotacolor(Session session) throws Exception {
		int max = 0;
		
		Criteria criteria = session.createCriteria(Petmascotacolor.class)
		.setProjection(Projections.max("idmascotacolor"));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Petmascotacolor> lisPetmascotacolor(Session session, int idmascota) throws Exception {
		List<Petmascotacolor> lisPetmascotacolor = null;
		
		Criteria criteria = session.createCriteria(Petmascotacolor.class)
		.add( Restrictions.eq("petmascota.idmascota", idmascota))
		.add( Restrictions.eq("petestado.idestado", 1))
		.createAlias("cotcolor", "color");
		
		lisPetmascotacolor = (List<Petmascotacolor>)criteria.list();
		
		return lisPetmascotacolor;
	}

	@Override
	public void savePetmascotacolor(Session session, Petmascotacolor petmascotacolor) throws Exception {
		session.save(petmascotacolor);
	}

	@Override
	public void updatePetmascotacolor(Session session, Petmascotacolor petmascotacolor) throws Exception {
		session.update(petmascotacolor);
	}

}
