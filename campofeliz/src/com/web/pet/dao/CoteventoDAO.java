package com.web.pet.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Cotcolor;
import com.web.pet.pojo.annotations.Cotevento;

public class CoteventoDAO {

	public int maxIdCotevento(Session session) throws Exception {
		int max = 0;
		
		Criteria criteria = session.createCriteria(Cotevento.class)
		.setProjection(Projections.max("idevento"));
						
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cotevento> lisCotevento(Session session) throws Exception {
		List<Cotevento> lisCotevento = null;
		
		Criteria criteria = session.createCriteria(Cotcolor.class)
		.add( Restrictions.eq("cotestado.idestado", 1) );
		
		lisCotevento = (List<Cotevento>) criteria.list();
		
		return lisCotevento;
	}

	public Cotevento getCoteventoById(Session session, int id) throws Exception {
		Cotevento cotevento = null;
		
		Criteria criteria = session.createCriteria(Cotevento.class)
		.add(Restrictions.eq("cotestado.idestado", 1))
		.add(Restrictions.eq("idevento", id));
		
		cotevento = (Cotevento) criteria.uniqueResult();
		
		return cotevento;
	}

	public void saveCotevento(Session session, Cotevento cotevento) throws Exception {
		session.save(cotevento);
	}

	public void updateCotevento(Session session, Cotevento cotevento) throws Exception {
		session.update(cotevento);
	}

	@SuppressWarnings("unchecked")
	public List<Cotevento> lisCotevento(Session session, Date fechadesde, Date fechahasta) throws Exception {
		List<Cotevento> lisCotevento = null;
		
		Criteria criteria = session.createCriteria(Cotevento.class)
		.add(Restrictions.eq("cotestado.idestado", 1))
		.add(Restrictions.between("fechadesde", fechadesde, fechahasta))
		.add(Restrictions.between("fechahasta", fechadesde, fechahasta));
		
		lisCotevento = (List<Cotevento>) criteria.list();
		
		return lisCotevento;
	}

	public void deleteCotevento(Session session, int id) throws Exception {
		String hqlUpdate = "delete Cotevento evento where evento.idevento = :idevento";
		session.createQuery( hqlUpdate )
		.setInteger( "idevento", id )
		.executeUpdate();
	}

}
