package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.web.pet.daointerface.CotcolorDAOInterface;
import com.web.pet.pojo.annotations.Cotcolor;

public class CotcolorDAO implements CotcolorDAOInterface {

	public int maxIdCotcolor(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idcolor) as max from Cotcolor").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	public void saveCotcolor(Session session, Cotcolor cotcolor) throws Exception {
		session.save(cotcolor);
	}

	public void updateCotcolor(Session session, Cotcolor cotcolor) throws Exception {
		session.update(cotcolor);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cotcolor> lisCotcolor(Session session) throws Exception {
		List<Cotcolor> arraydatos = null;
		
		Criteria criteria = session.createCriteria(Cotcolor.class)
		.add( Restrictions.eq("cotestado.idestado", 1))
		.addOrder(Order.asc("nombre"));
			
		arraydatos = (List<Cotcolor>) criteria.list();
		
		return arraydatos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cotcolor> lisCotcolorByPage(Session session, int pageSize, int pageNumber, int args[]) throws Exception {
		List<Cotcolor> arraydatos = null;
		
		Criteria criteria = session.createCriteria(Cotcolor.class)
		.add( Restrictions.eq("cotestado.idestado", 1))
		.addOrder(Order.asc("nombre"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		arraydatos = (List<Cotcolor>) criteria.list();
		
		if(arraydatos != null && arraydatos.size() > 0)
		{
			int max = 0;
			Object object = session.createQuery("select count(*) from Cotcolor where cotestado.idestado = 1").uniqueResult();
			max = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = max;
		}
		else
		{
			args[0] = 0;
		}
		
		return arraydatos;
	}
	
	public Cotcolor getCotcolorById(Session session, int id) throws Exception {
		Cotcolor cotcolor = new Cotcolor();
		
		Criteria criteria = session.createCriteria(Cotcolor.class)
		.add( Restrictions.eq("idcolor", id) )
		.add( Restrictions.eq("cotestado.idestado", 1));
			
		cotcolor = (Cotcolor) criteria.uniqueResult();
		
		return cotcolor;
	}
	
}
