package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.daointerface.PetordenserviciodetalleDAOInterface;
import com.web.pet.pojo.annotations.Petordenserviciodetalle;
import com.web.pet.pojo.annotations.PetordenserviciodetalleId;

public class PetordenserviciodetalleDAO implements
		PetordenserviciodetalleDAOInterface {

	@Override
	public int maxIdPetordenserviciodetalleByParent(Session session,
			int idordenservicio) throws Exception {
		int max = 0;
		
		Criteria criteria = session.createCriteria(Petordenserviciodetalle.class)
		.setProjection( Projections.max("id.idordenserviciodetalle"))
		.add( Restrictions.eq("petordenservicio.idordenservicio", idordenservicio));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	@Override
	public Petordenserviciodetalle getPetordenserviciodetalleById(
			Session session,
			PetordenserviciodetalleId petordenserviciodetalleId)
			throws Exception {
		Petordenserviciodetalle petordenserviciodetalle = null;
		
		Criteria criteria = session.createCriteria(Petordenserviciodetalle.class)
		.add( Restrictions.eq("id.idordenservicio", petordenserviciodetalleId.getIdordenservicio()))
		.add( Restrictions.eq("id.idordenserviciodetalle", petordenserviciodetalleId.getIdordenserviciodetalle()))
		.add( Restrictions.eq("petestado.idestado", 1));
		
		petordenserviciodetalle = (Petordenserviciodetalle) criteria.uniqueResult();
		
		return petordenserviciodetalle;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Petordenserviciodetalle> lisPethistoriaclinicadetalleByPage(
			Session session, int pageSize, int pageNumber, int[] args,
			int idordenservicio) throws Exception {
		List<Petordenserviciodetalle> lisPetordenserviciodetalle = null;
		
		Criteria criteria = session.createCriteria(Petordenserviciodetalle.class)
		.add( Restrictions.eq("petordenservicio.idordenservicio", idordenservicio))
		.add( Restrictions.eq("petestado.idestado", 1))
		.createAlias("cotservicio", "s")
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
		
		lisPetordenserviciodetalle = (List<Petordenserviciodetalle>) criteria.list();
		
		if(lisPetordenserviciodetalle != null && lisPetordenserviciodetalle.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Petordenserviciodetalle.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("petordenservicio.idordenservicio", idordenservicio))
			.add( Restrictions.eq("petestado.idestado", 1));
			
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		} else {
			args[0] = 0;
		}
		
		return lisPetordenserviciodetalle;
	}

	@Override
	public void savePetordenserviciodetalle(Session session,
			Petordenserviciodetalle petordenserviciodetalle)
			throws Exception {
		session.save(petordenserviciodetalle);
	}

	@Override
	public void updatePetordenserviciodetalle(Session session,
			Petordenserviciodetalle petordenserviciodetalle)
			throws Exception {
		session.update(petordenserviciodetalle);
	}

	@Override
	public void deletePetordenserviciodetalle(Session session,
			PetordenserviciodetalleId petordenserviciodetalleId)
			throws Exception {
		String hqlUpdate = "delete Petordenserviciodetalle d where d.idordenservicio = :idordenservicio and d.idordenserviciodetalle = :idordenserviciodetalle";
		session.createQuery( hqlUpdate )
		.setInteger("idordenservicio", petordenserviciodetalleId.getIdordenservicio() )
		.setInteger("idordenserviciodetalle", petordenserviciodetalleId.getIdordenserviciodetalle())
		.executeUpdate();
	}

}
