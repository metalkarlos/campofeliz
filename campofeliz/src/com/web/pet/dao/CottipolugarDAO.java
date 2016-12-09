package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Cottipolugar;

public class CottipolugarDAO {
	
	public int maxIdCottipolugar(Session session) throws Exception {
		int max = 0;

		Criteria criteria = session.createCriteria(Cottipolugar.class)
		.setProjection( Projections.max("idtipolugar"));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cottipolugar> lisCottipolugar(Session session) throws Exception {
		List<Cottipolugar> lisCottipolugar = null;
		
		Criteria criteria = session.createCriteria(Cottipolugar.class)
		.add( Restrictions.eq("setestado.idestado", 1) );
		
		lisCottipolugar = (List<Cottipolugar>) criteria.list();
		
		return lisCottipolugar;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cottipolugar> lisCottipolugarByPage(Session session, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Cottipolugar> lisCottipolugar = null;
		
		Criteria criteria = session.createCriteria(Cottipolugar.class)
		.add( Restrictions.eq("setestado.idestado", 1))
		.addOrder(Order.asc("nombre").ignoreCase())
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		lisCottipolugar = (List<Cottipolugar>) criteria.list();
		
		if(lisCottipolugar != null && lisCottipolugar.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Cottipolugar.class)
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
		
		return lisCottipolugar;
	}
	
	public void ingresarCottipolugar(Session session, Cottipolugar cottipolugar) throws Exception {
		session.save(cottipolugar);
	}

	public void modificarCottipolugar(Session session, Cottipolugar cottipolugar) throws Exception {
		session.update(cottipolugar);
	}

}
