package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.daointerface.PetrazaDAOInterface;
import com.web.pet.pojo.annotations.Petraza;

public class PetrazaDAO implements PetrazaDAOInterface {

	@Override
	public int maxIdPetraza(Session session) throws Exception {
		int max = 0;

		Criteria criteria = session.createCriteria(Petraza.class)
		.setProjection( Projections.max("idraza"));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	@Override
	public void savePetraza(Session session, Petraza petraza) throws Exception {
		session.save(petraza);
	}

	@Override
	public void updatePetraza(Session session, Petraza petraza) throws Exception {
		session.update(petraza);
	}
	@SuppressWarnings("unchecked")
	public List<Petraza> lisPetraza(Session session) throws Exception {
		List<Petraza> lisPetraza = null;
		
		Criteria criteria = session.createCriteria(Petraza.class)
		.add( Restrictions.eq("petestado.idestado", 1));
		
		lisPetraza = (List<Petraza>) criteria.list();
		
		return lisPetraza;
	}
	
	public Petraza getPetrazaById(Session session, int id) throws Exception {
		Petraza petraza = new Petraza();
		
		Criteria criteria = session.createCriteria(Petraza.class)
		.add( Restrictions.eq("idraza", id) )
		.add( Restrictions.eq("petestado.idestado", 1));
		
		petraza = (Petraza) criteria.uniqueResult();
		
		return petraza;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Petraza> lisPetrazaByPage(Session session, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Petraza> lisPetraza = null;
		
		Criteria criteria = session.createCriteria(Petraza.class)
		.add( Restrictions.eq("petestado.idestado", 1))
		.addOrder(Order.asc("nombre"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		lisPetraza = (List<Petraza>) criteria.list();
		
		if(lisPetraza != null && lisPetraza.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Petraza.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("petestado.idestado", 1));
			
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		}
		else
		{
			args[0] = 0;
		}
		
		return lisPetraza;
	}
	
}
