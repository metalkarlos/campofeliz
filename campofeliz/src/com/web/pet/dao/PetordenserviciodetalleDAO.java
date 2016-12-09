package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.PetordenservicioId;
import com.web.pet.pojo.annotations.Petordenserviciodetalle;
import com.web.pet.pojo.annotations.PetordenserviciodetalleId;

public class PetordenserviciodetalleDAO {

	public int maxIdPetordenserviciodetalleByParent(Session session,
			PetordenservicioId petordenservicioId) throws Exception {
		int max = 0;
		
		Criteria criteria = session.createCriteria(Petordenserviciodetalle.class)
		.setProjection( Projections.max("id.idordenserviciodetalle"))
		.add( Restrictions.eq("petordenservicio.id.idordenservicio", petordenservicioId.getIdordenservicio()))
		.add( Restrictions.eq("petordenservicio.id.idanio", petordenservicioId.getIdanio()));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	public Petordenserviciodetalle getPetordenserviciodetalleById(
			Session session,
			PetordenserviciodetalleId petordenserviciodetalleId)
			throws Exception {
		Petordenserviciodetalle petordenserviciodetalle = null;
		
		Criteria criteria = session.createCriteria(Petordenserviciodetalle.class)
		.add( Restrictions.eq("id.idordenservicio", petordenserviciodetalleId.getIdordenservicio()))
		.add( Restrictions.eq("id.idanio", petordenserviciodetalleId.getIdanio()))
		.add( Restrictions.eq("id.idordenserviciodetalle", petordenserviciodetalleId.getIdordenserviciodetalle()))
		.add( Restrictions.eq("setestado.idestado", 1));
		
		petordenserviciodetalle = (Petordenserviciodetalle) criteria.uniqueResult();
		
		return petordenserviciodetalle;
	}

	@SuppressWarnings("unchecked")
	public List<Petordenserviciodetalle> lisPethistoriaclinicadetalleByPage(
			Session session, int pageSize, int pageNumber, int[] args,
			PetordenservicioId petordenservicioId) throws Exception {
		List<Petordenserviciodetalle> lisPetordenserviciodetalle = null;
		
		Criteria criteria = session.createCriteria(Petordenserviciodetalle.class)
		.add( Restrictions.eq("petordenservicio.idordenservicio", petordenservicioId.getIdordenservicio()))
		.add( Restrictions.eq("petordenservicio.idanio", petordenservicioId.getIdanio()))
		.add( Restrictions.eq("setestado.idestado", 1))
		.createAlias("petservicio", "s")
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
		
		lisPetordenserviciodetalle = (List<Petordenserviciodetalle>) criteria.list();
		
		if(lisPetordenserviciodetalle != null && lisPetordenserviciodetalle.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Petordenserviciodetalle.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("petordenservicio.idordenservicio", petordenservicioId.getIdordenservicio()))
			.add( Restrictions.eq("petordenservicio.idanio", petordenservicioId.getIdanio()))
			.add( Restrictions.eq("setestado.idestado", 1))
			.createAlias("petservicio", "s");
			
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		} else {
			args[0] = 0;
		}
		
		return lisPetordenserviciodetalle;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petordenserviciodetalle> lisPetordenserviciodetalle(Session session, PetordenservicioId petordenservicioId) throws Exception {
		List<Petordenserviciodetalle> lisPetordenserviciodetalle = null;
		
		Criteria criteria = session.createCriteria(Petordenserviciodetalle.class)
		.add( Restrictions.eq("id.idordenservicio", petordenservicioId.getIdordenservicio()))
		.add( Restrictions.eq("id.idanio", petordenservicioId.getIdanio()))
		.add( Restrictions.eq("setestado.idestado", 1))
		.createAlias("petservicio", "s")
		.createAlias("s.cotempresa", "e")
		.createAlias("s.cotoficina", "o");
		
		lisPetordenserviciodetalle = (List<Petordenserviciodetalle>) criteria.list();
		
		return lisPetordenserviciodetalle;
	}

	public void savePetordenserviciodetalle(Session session,
			Petordenserviciodetalle petordenserviciodetalle)
			throws Exception {
		session.save(petordenserviciodetalle);
	}

	public void updatePetordenserviciodetalle(Session session,
			Petordenserviciodetalle petordenserviciodetalle)
			throws Exception {
		session.update(petordenserviciodetalle);
	}

	public void deletePetordenserviciodetalle(Session session,
			PetordenserviciodetalleId petordenserviciodetalleId)
			throws Exception {
		String hqlDelete = "delete Petordenserviciodetalle d where d.id.idordenservicio = :idordenservicio and d.id.idanio = :idanio and d.id.idordenserviciodetalle = :idordenserviciodetalle";
		session.createQuery( hqlDelete )
		.setInteger("idordenservicio", petordenserviciodetalleId.getIdordenservicio() )
		.setInteger("idanio", petordenserviciodetalleId.getIdanio() )
		.setInteger("idordenserviciodetalle", petordenserviciodetalleId.getIdordenserviciodetalle())
		.executeUpdate();
	}

}
