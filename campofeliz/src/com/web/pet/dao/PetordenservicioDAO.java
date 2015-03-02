package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Petordenservicio;

public class PetordenservicioDAO {

	public int maxIdPetordenservicio(Session session) throws Exception {
		int max = 0;
		
		Criteria criteria = session.createCriteria(Petordenservicio.class)
		.setProjection(Projections.max("idordenservicio"));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	public Petordenservicio getPetordenservicioById(Session session,
			int idordenservicio) throws Exception {
		Petordenservicio petordenservicio = null;
		
		Criteria criteria = session.createCriteria(Petordenservicio.class)
		.add( Restrictions.eq("idordenservicio", idordenservicio))
		.add( Restrictions.eq("setestado.idestado", 1))
		.createAlias("petmascotahomenaje", "mascota")
		.createAlias("cotlugar", "lugar", Criteria.LEFT_JOIN);
		
		petordenservicio = (Petordenservicio) criteria.uniqueResult();
		
		return petordenservicio;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petordenservicio> lisPetordenservicioByPage(Session session,
			String[] nombres, int pageSize, int pageNumber, int[] args)
			throws Exception {
		List<Petordenservicio> lisPetordenservicio = null;
		
		Criteria criteria = session.createCriteria(Petordenservicio.class)
		.add( Restrictions.eq("setestado.idestado", 1))
		.add( Restrictions.eq("m.setestado.idestado", 1))
		.createCriteria("petmascotahomenaje", "m");
		
		Criteria secondCriteria = criteria.createCriteria("m.cotpersona", "p", Criteria.LEFT_JOIN)
		.add( Restrictions.eq("setestado.idestado", 1));
		
		if(nombres != null && nombres.length > 0){
			String query = "(";
			for(int i=0;i<nombres.length;i++)
			{
				query += "(lower({alias}.apellido1) like lower('%"+nombres[i]+"%') or ";
				query += "lower({alias}.apellido2) like lower('%"+nombres[i]+"%') or ";
				query += "lower({alias}.nombre1) like lower('%"+nombres[i]+"%') or ";
				query += "lower({alias}.nombre2) like lower('%"+nombres[i]+"%')) ";
				if(i<nombres.length-1){
					query += "and ";
				}
			}
			query += ")";
			
			secondCriteria.add(Restrictions.sqlRestriction(query));
		}
		
		secondCriteria.setMaxResults(pageSize)
		.setFirstResult(pageNumber)
		.addOrder(Order.asc("p.apellido1"))
		.addOrder(Order.asc("p.apellido2"))
		.addOrder(Order.asc("p.nombre1"))
		.addOrder(Order.asc("p.nombre2"));
		
		lisPetordenservicio = (List<Petordenservicio>) criteria.list();
		
		if(lisPetordenservicio != null && lisPetordenservicio.size() > 0){
			Criteria criteriaCount = session.createCriteria(Petordenservicio.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("setestado.idestado", 1))
			.add( Restrictions.eq("m.setestado.idestado", 1))
			.createCriteria("petmascotahomenaje", "m");
			
			Criteria secondCriteriaCount = criteriaCount.createCriteria("m.cotpersona", "p", Criteria.LEFT_JOIN)
			.add( Restrictions.eq("setestado.idestado", 1));
			
			if(nombres != null && nombres.length > 0){
				String query = "(";
				for(int i=0;i<nombres.length;i++)
				{
					query += "(lower({alias}.apellido1) like lower('%"+nombres[i]+"%') or ";
					query += "lower({alias}.apellido2) like lower('%"+nombres[i]+"%') or ";
					query += "lower({alias}.nombre1) like lower('%"+nombres[i]+"%') or ";
					query += "lower({alias}.nombre2) like lower('%"+nombres[i]+"%')) ";
					if(i<nombres.length-1){
						query += "and ";
					}
				}
				query += ")";
				
				secondCriteriaCount.add(Restrictions.sqlRestriction(query));
			}
			
			Object object = secondCriteriaCount.uniqueResult();
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
