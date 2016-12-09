package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Petordenservicio;
import com.web.pet.pojo.annotations.PetordenservicioId;

public class PetordenservicioDAO {

	public int maxIdPetordenservicio(Session session, int idanio) throws Exception {
		int max = 0;
		
		Criteria criteria = session.createCriteria(Petordenservicio.class)
		.setProjection(Projections.max("id.idordenservicio"))
		.add( Restrictions.eq("id.idanio", idanio));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	public Petordenservicio getPetordenservicioById(Session session,
			PetordenservicioId petordenservicioId) throws Exception {
		Petordenservicio petordenservicio = null;
		
		Criteria criteria = session.createCriteria(Petordenservicio.class)
		.add( Restrictions.eq("id.idordenservicio", new Integer(petordenservicioId.getIdordenservicio())))
		.add( Restrictions.eq("id.idanio", new Integer(petordenservicioId.getIdanio())))
		.add( Restrictions.eq("setestado.idestado", 1))
		.createAlias("petmascotahomenaje", "mascota")
		.createAlias("mascota.petespecie", "especie", Criteria.LEFT_JOIN)
		.createAlias("mascota.petraza", "raza", Criteria.LEFT_JOIN)
		.createAlias("mascota.cotpersona", "persona", Criteria.LEFT_JOIN)
		.createAlias("cotlugar", "lugar", Criteria.LEFT_JOIN)
		.createAlias("lugar.cottipolugar", "tipolugar", Criteria.LEFT_JOIN);
		
		petordenservicio = (Petordenservicio) criteria.uniqueResult();
		
		return petordenservicio;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petordenservicio> lisPetordenservicioByPage(Session session,
			String[] nombres, int pageSize, int pageNumber, int[] args)
			throws Exception {
		List<Petordenservicio> lisPetordenservicio = null;
		
		Criteria criteriaPetordenservicio = session.createCriteria(Petordenservicio.class)
		.add( Restrictions.eq("setestado.idestado", 1))
		.addOrder(Order.asc("fechaemision").ignoreCase());
		
		Criteria joinPetmascotahomenaje = criteriaPetordenservicio.createCriteria("petmascotahomenaje", "m", Criteria.INNER_JOIN, Restrictions.eq("m.setestado.idestado", 1));
		
		//Criteria joinCotpersona = joinPetmascotahomenaje.createCriteria("m.cotpersona", "p", Criteria.LEFT_JOIN, Restrictions.eq("p.setestado.idestado", 1));
		
		if(nombres != null && nombres.length > 0){
			String query = "(";
			for(int i=0;i<nombres.length;i++)
			{
				/*query += "(lower({alias}.apellido1) like lower('%"+nombres[i]+"%') or ";
				query += "lower({alias}.apellido2) like lower('%"+nombres[i]+"%') or ";
				query += "lower({alias}.nombre1) like lower('%"+nombres[i]+"%') or ";
				query += "lower({alias}.nombre2) like lower('%"+nombres[i]+"%')) ";*/
				query += "lower({alias}.nombre) like lower('%"+nombres[i]+"%') ";
				
				if(i<nombres.length-1){
					query += "and ";
				}
			}
			query += ")";
			
			//joinCotpersona.add(Restrictions.sqlRestriction(query));
			joinPetmascotahomenaje.add(Restrictions.sqlRestriction(query));
		}
		
		Criteria joinCotpersona = joinPetmascotahomenaje.createCriteria("m.cotpersona", "p", Criteria.LEFT_JOIN, Restrictions.eq("p.setestado.idestado", 1));
		
		joinCotpersona.setMaxResults(pageSize)
		//joinPetmascotahomenaje.setMaxResults(pageSize)
		.setFirstResult(pageNumber)
		.addOrder(Order.asc("m.nombre").ignoreCase());
		/*.addOrder(Order.asc("p.apellido1"))
		.addOrder(Order.asc("p.apellido2"))
		.addOrder(Order.asc("p.nombre1"))
		.addOrder(Order.asc("p.nombre2"));*/
		
		lisPetordenservicio = (List<Petordenservicio>) criteriaPetordenservicio.list();
		
		if(lisPetordenservicio != null && lisPetordenservicio.size() > 0){
			Criteria criteriaCount = session.createCriteria(Petordenservicio.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("setestado.idestado", 1));
			
			Criteria joinPetmascotahomenajeCount = criteriaCount.createCriteria("petmascotahomenaje", "m", Criteria.INNER_JOIN, Restrictions.eq("m.setestado.idestado", 1));
			
			//Criteria joinCotpersonaCount = joinPetmascotahomenajeCount.createCriteria("m.cotpersona", "p", Criteria.LEFT_JOIN, Restrictions.eq("p.setestado.idestado", 1));
			
			if(nombres != null && nombres.length > 0){
				String query = "(";
				for(int i=0;i<nombres.length;i++)
				{
					/*query += "(lower({alias}.apellido1) like lower('%"+nombres[i]+"%') or ";
					query += "lower({alias}.apellido2) like lower('%"+nombres[i]+"%') or ";
					query += "lower({alias}.nombre1) like lower('%"+nombres[i]+"%') or ";
					query += "lower({alias}.nombre2) like lower('%"+nombres[i]+"%')) ";*/
					query += "lower({alias}.nombre) like lower('%"+nombres[i]+"%') ";
					
					if(i<nombres.length-1){
						query += "and ";
					}
				}
				query += ")";
				
				//joinCotpersonaCount.add(Restrictions.sqlRestriction(query));
				joinPetmascotahomenajeCount.add(Restrictions.sqlRestriction(query));
			}
			
			Criteria joinCotpersonaCount = joinPetmascotahomenajeCount.createCriteria("m.cotpersona", "p", Criteria.LEFT_JOIN, Restrictions.eq("p.setestado.idestado", 1));
			
			Object object = joinCotpersonaCount.uniqueResult();
			//Object object = joinPetmascotahomenajeCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		} else {
			args[0] = 0;
		}
		
		return lisPetordenservicio;
	}

	public void savePetordenservicio(Session session,
			Petordenservicio petordenservicio) throws Exception {
		session.save(petordenservicio);
	}

	public void updatePetordenservicio(Session session,
			Petordenservicio petordenservicio) throws Exception {
		session.update(petordenservicio);
	}

}
