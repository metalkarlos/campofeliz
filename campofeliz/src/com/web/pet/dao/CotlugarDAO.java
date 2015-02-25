package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Cotlugar;

public class CotlugarDAO {

	public int maxIdCotlugar(Session session) throws Exception {
		int max = 0;

		Criteria criteria = session.createCriteria(Cotlugar.class)
		.setProjection( Projections.max("idlugar"));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	public void saveCotlugar(Session session, Cotlugar cotlugar)
			throws Exception {
		session.save(cotlugar);
	}

	public void updateCotlugar(Session session, Cotlugar cotlugar)
			throws Exception {
		session.update(cotlugar);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cotlugar> lisCotlugar(Session session) throws Exception {
		List<Cotlugar> arraydatos = null;
		
		Criteria criteria = session.createCriteria(Cotlugar.class)
		.add( Restrictions.eq("setestado.idestado", 1))
		.addOrder(Order.asc("nombre"));
			
		arraydatos = (List<Cotlugar>) criteria.list();
		
		return arraydatos;
	}

	@SuppressWarnings("unchecked")
	public List<Cotlugar> lisCotlugarByPage(Session session, int pageSize,
			int pageNumber, int[] args) throws Exception {
		List<Cotlugar> lisCotlugar = null;
		
		Criteria criteria = session.createCriteria(Cotlugar.class)
		.add( Restrictions.eq("setestado.idestado", 1))
		.addOrder(Order.asc("nombre"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		lisCotlugar = (List<Cotlugar>) criteria.list();
		
		if(lisCotlugar != null && lisCotlugar.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Cotlugar.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("setestado.idestado", 1));
			
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		}
		else
		{
			args[0] = 0;
		}
		
		return lisCotlugar;
	}

	public Cotlugar getCotlugarById(Session session, int id) throws Exception {
		Cotlugar cotlugar = null;
		
		Criteria criteria = session.createCriteria(Cotlugar.class)
		.add( Restrictions.eq("setestado.idestado", 1) )
		.add( Restrictions.eq("idlugar", id));
		
		cotlugar = (Cotlugar) criteria.uniqueResult();
		
		return cotlugar;
	}

}
