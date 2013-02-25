package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.daointerface.CottiposervicioDAOInterface;
import com.web.pet.pojo.annotations.Cottiposervicio;

public class CottiposervicioDAO implements CottiposervicioDAOInterface {

	@Override
	public int maxIdCottiposervicio(Session session) throws Exception {
		int max = 0;

		Criteria criteria = session.createCriteria(Cottiposervicio.class)
		.setProjection( Projections.max("idtiposervicio"));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	@Override
	public void saveCottiposervicio(Session session,
			Cottiposervicio cottiposervicio) throws Exception {
		session.save(cottiposervicio);
	}

	@Override
	public void updateCottiposervicio(Session session,
			Cottiposervicio cottiposervicio) throws Exception {
		session.update(cottiposervicio);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cottiposervicio> lisCottiposervicioByPage(Session session,
			int pageSize, int pageNumber, int[] args) throws Exception {
		List<Cottiposervicio> lisCottiposervicio = null;
		
		Criteria criteria = session.createCriteria(Cottiposervicio.class)
		.add( Restrictions.eq("cotestado.idestado", 1))
		.addOrder(Order.asc("nombre"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		lisCottiposervicio = (List<Cottiposervicio>) criteria.list();
		
		if(lisCottiposervicio != null && lisCottiposervicio.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Cottiposervicio.class)
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
		
		return lisCottiposervicio;
	}

	@Override
	public Cottiposervicio getCottiposervicioById(Session session, int id)
			throws Exception {
		Cottiposervicio cottiposervicio = null;
		
		Criteria criteria = session.createCriteria(Cottiposervicio.class)
		.add( Restrictions.eq("cotestado.idestado", 1) )
		.add( Restrictions.eq("idtiposervicio", id));
		
		cottiposervicio = (Cottiposervicio) criteria.uniqueResult();
		
		return cottiposervicio;
	}

}
