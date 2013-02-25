package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.daointerface.PetespecieDAOInterface;
import com.web.pet.pojo.annotations.Petespecie;

public class PetespecieDAO implements PetespecieDAOInterface {

	@Override
	public int maxIdPetespecie(Session session) throws Exception {
		int max = 0;

		Criteria criteria = session.createCriteria(Petespecie.class)
		.setProjection( Projections.max("idespecie"));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	@Override
	public void savePetespecie(Session session, Petespecie petespecie) throws Exception {
		session.save(petespecie);
	}

	@Override
	public void updatePetespecie(Session session, Petespecie petespecie) throws Exception {
		session.update(petespecie);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Petespecie> lisPetespecie(Session session) throws Exception {
		List<Petespecie> lisPettipo = null;
		
		Criteria criteria = session.createCriteria(Petespecie.class)
		.add( Restrictions.eq("petestado.idestado", 1) );
		
		lisPettipo = (List<Petespecie>) criteria.list();
		
		return lisPettipo;
	}

	@Override
	public Petespecie getPetespecieById(Session session, int id) throws Exception {
		Petespecie pettipo = null;
		
		Criteria criteria = session.createCriteria(Petespecie.class)
		.add( Restrictions.eq("petestado.idestado", 1) )
		.add( Restrictions.eq("idtipo", id));
		
		pettipo = (Petespecie) criteria.uniqueResult();
		
		return pettipo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Petespecie> lisPetespecieByPage(Session session, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Petespecie> lisPetespecie = null;
		
		Criteria criteria = session.createCriteria(Petespecie.class)
		.add( Restrictions.eq("petestado.idestado", 1))
		.addOrder(Order.asc("nombre"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		lisPetespecie = (List<Petespecie>) criteria.list();
		
		if(lisPetespecie != null && lisPetespecie.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Petespecie.class)
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
		
		return lisPetespecie;
	}

}
