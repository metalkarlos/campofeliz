package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Cotfotopersona;

public class CotfotopersonaDAO {

		
		
		public int maxIdCotfotopersona(Session session) throws Exception {
			int max = 0;
			
			Object object = session.createQuery("select max(idfoto) as max from Cotfotopersona").uniqueResult();
			max = (object==null?0:Integer.parseInt(object.toString()));
			
			return max;
		}
		
		@SuppressWarnings("unchecked")
		public List<Cotfotopersona> lisCotfotopersonaByCotId(Session session, int idpersona) throws Exception {
			List<Cotfotopersona> arraydatos = null;
			
			Criteria criteria = session.createCriteria(Cotfotopersona.class)
			.add( Restrictions.eq("cotpersona.idpersona", idpersona) )
			.add( Restrictions.eq("setestado.idestado", 1));
				
			arraydatos = (List<Cotfotopersona>) criteria.list();
			
			return arraydatos;
		}

		public Cotfotopersona getCotfotopersonaById(Session session, int id) throws Exception {
			Cotfotopersona cotfotopersona = new Cotfotopersona();
			
			Criteria criteria = session.createCriteria(Cotfotopersona.class)
			.add( Restrictions.eq("id.idfoto", id) )
			.add( Restrictions.eqProperty("id.fecharegistro", "pk.fecharegistro"))
			.createCriteria("cotfotopersonapk","pk");
			
			cotfotopersona = (Cotfotopersona) criteria.uniqueResult();
			
			return cotfotopersona;
		}
		
		public Cotfotopersona getCotfotopersonaPerfilByCotId(Session session, int idpersona) throws Exception {
			Cotfotopersona cotfotopersona = new Cotfotopersona();
			
			Criteria criteria = session.createCriteria(Cotfotopersona.class)
			.add( Restrictions.eq("cotfotopersona.idpersona", idpersona) )
			.add( Restrictions.eq("mostrar", 1) )
			.add( Restrictions.eq("setestado.idestado", 1) );
			
			cotfotopersona = (Cotfotopersona) criteria.uniqueResult();
			
			return cotfotopersona;
		}
		
		@SuppressWarnings("unchecked")
		public List<Cotfotopersona> lisCotfotopersonaPerfil(Session session) throws Exception {
			List<Cotfotopersona> arraydatos = null;
			
			Criteria criteria = session
			.createCriteria(Cotfotopersona.class)
			.add( Restrictions.eq("mostrar", 1) )
			.add( Restrictions.eqProperty("m.idpersona", "cotpersona.idpersona") )
			.createCriteria("cotpersona","m")
			.addOrder(Order.asc("nombre"));
				
			arraydatos = (List<Cotfotopersona>) criteria.list();
			
			return arraydatos;
		}
		
		public void saveCotfotopersona(Session session, Cotfotopersona cotfotopersona) throws Exception {
			session.save(cotfotopersona);
		}

		public void updateCotfotopersona(Session session, Cotfotopersona cotfotopersona)
				throws Exception {
			session.update(cotfotopersona);
		}

		public void resetCotfotopersonaPerfilByCotId(Session session, int idpersona)
				throws Exception {
			String hqlUpdate = "update Cotfotopersona foto set foto.mostrar = 0 where foto.cotpersona.idpersona = :idpersona";
			session.createQuery( hqlUpdate )
			.setInteger( "idpersona", idpersona )
			.executeUpdate();
		}
		
		public void setCotfotopersonaPerfil(Session session, int idfoto) throws Exception {
			String hqlUpdate = "update Cotfotopersona foto set foto.mostrar = 1 where foto.idfoto = :idfoto";
			session.createQuery( hqlUpdate )
			.setInteger( "idfoto", idfoto )
			.executeUpdate();
		}

		public void deleteCotfotopersona(Session session, int idfoto)
				throws Exception {
			String hqlUpdate = "delete Cotfotopersona foto where foto.idfoto = :idfoto";
			session.createQuery( hqlUpdate )
			.setInteger( "idfoto", idfoto )
			.executeUpdate();
		}

	}



