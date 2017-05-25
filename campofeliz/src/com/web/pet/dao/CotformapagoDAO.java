package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Cotformapago;

public class CotformapagoDAO {

	public int maxIdCotformapago(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idformapago) as max from Cotformapago").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	public void saveCotformapago(Session session, Cotformapago cotformapago) throws Exception {
		session.save(cotformapago);
	}

	public void updateCotformapago(Session session, Cotformapago cotformapago) throws Exception {
		session.update(cotformapago);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cotformapago> lisCotformapago(Session session) throws Exception {
		List<Cotformapago> arraydatos = null;
		
		Criteria criteria = session.createCriteria(Cotformapago.class)
		.add( Restrictions.eq("setestado.idestado", 1))
		.addOrder(Order.asc("nombre").ignoreCase());
			
		arraydatos = (List<Cotformapago>) criteria.list();
		
		return arraydatos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cotformapago> lisCotformapagoByPage(Session session, int pageSize, int pageNumber, int args[]) throws Exception {
		List<Cotformapago> arraydatos = null;
		
		Criteria criteria = session.createCriteria(Cotformapago.class)
		.add( Restrictions.eq("setestado.idestado", 1))
		.addOrder(Order.asc("nombre").ignoreCase())
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		arraydatos = (List<Cotformapago>) criteria.list();
		
		if(arraydatos != null && arraydatos.size() > 0)
		{
			int max = 0;
			Object object = session.createQuery("select count(*) from Cotformapago where setestado.idestado = 1").uniqueResult();
			max = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = max;
		}
		else
		{
			args[0] = 0;
		}
		
		return arraydatos;
	}
	
	public Cotformapago getCotformapagoById(Session session, int id) throws Exception {
		Cotformapago cotformapago = new Cotformapago();
		
		Criteria criteria = session.createCriteria(Cotformapago.class)
		.add( Restrictions.eq("idformapago", id) )
		.add( Restrictions.eq("setestado.idestado", 1));
			
		cotformapago = (Cotformapago) criteria.uniqueResult();
		
		return cotformapago;
	}
	
}
