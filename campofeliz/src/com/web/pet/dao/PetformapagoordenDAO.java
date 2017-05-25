package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Petformapagoorden;
import com.web.pet.pojo.annotations.PetformapagoordenId;
import com.web.pet.pojo.annotations.PetordenservicioId;

public class PetformapagoordenDAO {

	public int maxIdByParent(Session session,
			PetordenservicioId petordenservicioId) throws Exception {
		int max = 0;
		
		Criteria criteria = session.createCriteria(Petformapagoorden.class)
		.setProjection( Projections.max("id.idformapagoorden"))
		.add( Restrictions.eq("petordenservicio.id.idordenservicio", petordenservicioId.getIdordenservicio()))
		.add( Restrictions.eq("petordenservicio.id.idanio", petordenservicioId.getIdanio()));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	public Petformapagoorden getPetformapagoordenById(
			Session session,
			PetformapagoordenId petformapagoordenId)
			throws Exception {
		Petformapagoorden petformapagoorden = null;
		
		Criteria criteria = session.createCriteria(Petformapagoorden.class)
		.add( Restrictions.eq("id.idordenservicio", petformapagoordenId.getIdordenservicio()))
		.add( Restrictions.eq("id.idanio", petformapagoordenId.getIdanio()))
		.add( Restrictions.eq("id.idformapagoorden", petformapagoordenId.getIdformapagoorden()))
		.add( Restrictions.eq("setestado.idestado", 1));
		
		petformapagoorden = (Petformapagoorden) criteria.uniqueResult();
		
		return petformapagoorden;
	}

	@SuppressWarnings("unchecked")
	public List<Petformapagoorden> lisPetformapagoordenByPage(
			Session session, int pageSize, int pageNumber, int[] args,
			PetordenservicioId petordenservicioId) throws Exception {
		List<Petformapagoorden> lisPetformapagoorden = null;
		
		Criteria criteria = session.createCriteria(Petformapagoorden.class)
		.add( Restrictions.eq("petordenservicio.idordenservicio", petordenservicioId.getIdordenservicio()))
		.add( Restrictions.eq("petordenservicio.idanio", petordenservicioId.getIdanio()))
		.add( Restrictions.eq("setestado.idestado", 1))
		.createAlias("cotformapago", "f")
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
		
		lisPetformapagoorden = (List<Petformapagoorden>) criteria.list();
		
		if(lisPetformapagoorden != null && lisPetformapagoorden.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Petformapagoorden.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("petordenservicio.idordenservicio", petordenservicioId.getIdordenservicio()))
			.add( Restrictions.eq("petordenservicio.idanio", petordenservicioId.getIdanio()))
			.add( Restrictions.eq("setestado.idestado", 1))
			.createAlias("cotformapago", "f");
			
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		} else {
			args[0] = 0;
		}
		
		return lisPetformapagoorden;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petformapagoorden> lisPetformapagoorden(Session session, PetordenservicioId petordenservicioId) throws Exception {
		List<Petformapagoorden> lisPetformapagoorden = null;
		
		Criteria criteria = session.createCriteria(Petformapagoorden.class)
		.add( Restrictions.eq("id.idordenservicio", petordenservicioId.getIdordenservicio()))
		.add( Restrictions.eq("id.idanio", petordenservicioId.getIdanio()))
		.add( Restrictions.eq("setestado.idestado", 1))
		.createAlias("cotformapago", "f");
		//.createAlias("petordenservicio", "o");
		
		lisPetformapagoorden = (List<Petformapagoorden>) criteria.list();
		
		return lisPetformapagoorden;
	}

	public void grabarPetformapagoorden(Session session,
			Petformapagoorden petformapagoorden)
			throws Exception {
		session.save(petformapagoorden);
	}

	public void actualizarPetformapagoorden(Session session,
			Petformapagoorden petformapagoorden)
			throws Exception {
		session.update(petformapagoorden);
	}

}
