package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Cotservicio;

public class CotservicioDAO {

	public int maxIdCotservicio(Session session) throws Exception {
		int max = 0;

		Criteria criteria = session.createCriteria(Cotservicio.class)
		.setProjection( Projections.max("idservicio"));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	public void saveCotservicio(Session session, Cotservicio cotservicio)
			throws Exception {
		session.save(cotservicio);
	}

	public void updateCotservicio(Session session, Cotservicio cotservicio)
			throws Exception {
		session.update(cotservicio);
	}

	@SuppressWarnings("unchecked")
	public List<Cotservicio> lisCotservicio(Session session) throws Exception {
		List<Cotservicio> arraydatos = null;
		
		Criteria criteria = session.createCriteria(Cotservicio.class)
		.add( Restrictions.eq("cotestado.idestado", 1))
		.addOrder(Order.asc("nombre"));
			
		arraydatos = (List<Cotservicio>) criteria.list();
		
		return arraydatos;
	}

	@SuppressWarnings("unchecked")
	public List<Cotservicio> lisCotservicioByPage(Session session,
			int pageSize, int pageNumber, int[] args) throws Exception {
		List<Cotservicio> lisCotservicio = null;
		
		Criteria criteria = session.createCriteria(Cotservicio.class)
		.add( Restrictions.eq("cotestado.idestado", 1))
		.addOrder(Order.asc("nombre"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		lisCotservicio = (List<Cotservicio>) criteria.list();
		
		if(lisCotservicio != null && lisCotservicio.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Cotservicio.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("cotestado.idestado", 1));
			
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		}
		else
		{
			args[0] = 0;
		}
		
		return lisCotservicio;
	}

	public Cotservicio getCotservicioById(Session session, int id)
			throws Exception {
		Cotservicio cotservicio = null;
		
		Criteria criteria = session.createCriteria(Cotservicio.class)
		.add( Restrictions.eq("cotestado.idestado", 1) )
		.add( Restrictions.eq("idservicio", id));
		
		cotservicio = (Cotservicio) criteria.uniqueResult();
		
		return cotservicio;
	}

}
