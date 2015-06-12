package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Cotfotopersona;

public class CotfotopersonaDAO {
	
	public int maxIdCotfotopersona(Session session) throws Exception {
		int max = 0;
		
		Object object = session.createQuery("select max(idfotopersona) as max from Cotfotopersona").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	public int getCantFotosPorPersona(Session session, int idpersona) throws Exception{
		int count = 0;
		
		String hql = " select count(idfotopersona)+1 as cantidad ";
		hql += " from Cotfotopersona  fp ";
		hql += " where fp.cotpersona.idpersona = :idpersona ";
		
		Query query = session.createQuery(hql)
				.setInteger("idpersona", idpersona);
		
		Object object = query.uniqueResult();                 
		count = (object==null ?1: Integer.parseInt(object.toString()));
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cotfotopersona> lisCotfotopersonaByIdpersona(Session session, int idpersona) throws Exception {
		List<Cotfotopersona> lisCotfotopersona = null;
		
		Criteria criteria = session.createCriteria(Cotfotopersona.class)
		.add( Restrictions.eq("cotpersona.idpersona", idpersona) )
		.add( Restrictions.eq("setestado.idestado", 1));
			
		lisCotfotopersona = (List<Cotfotopersona>) criteria.list();
		
		return lisCotfotopersona;
	}

	public Cotfotopersona getCotfotopersonaPerfilByIdpersona(Session session, int idpersona) throws Exception {
		Cotfotopersona cotfotomascota = new Cotfotopersona();
		
		Criteria criteria = session.createCriteria(Cotfotopersona.class)
		.add( Restrictions.eq("cotpersona.idpersona", idpersona) )
		.add( Restrictions.eq("mostrar", 1) )
		.add( Restrictions.eq("setestado.idestado", 1) );
		
		cotfotomascota = (Cotfotopersona) criteria.uniqueResult();
		
		return cotfotomascota;
	}
	
	public void saveCotfotopersona(Session session, Cotfotopersona cotfotopersona) throws Exception {
		session.save(cotfotopersona);
	}

	public void updateCotfotopersona(Session session, Cotfotopersona cotfotopersona)
			throws Exception {
		session.update(cotfotopersona);
	}

	public void resetCotfotopersonaPerfilByIdpersona(Session session, int idpersona)
			throws Exception {
		String hqlUpdate = "update Cotfotopersona foto set foto.mostrar = 0 where foto.cotpersona.idpersona = :idpersona";
		session.createQuery( hqlUpdate )
		.setInteger( "idpersona", idpersona )
		.executeUpdate();
	}
	
	public void setCotfotopersonaPerfil(Session session, int idfotopersona) throws Exception {
		String hqlUpdate = "update Cotfotopersona foto set foto.mostrar = 1 where foto.idfotopersona = :idfotopersona";
		session.createQuery( hqlUpdate )
		.setInteger( "idfotopersona", idfotopersona )
		.executeUpdate();
	}

	public void deletePetfoto(Session session, int idfotopersona)
			throws Exception {
		String hqlUpdate = "delete Cotfotopersona foto where foto.idfotopersona = :idfotopersona";
		session.createQuery( hqlUpdate )
		.setInteger( "idfotopersona", idfotopersona )
		.executeUpdate();
	}

}

