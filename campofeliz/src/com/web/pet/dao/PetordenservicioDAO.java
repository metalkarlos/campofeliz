package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

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
		.createAlias("mascota.petespecie", "especie", JoinType.LEFT_OUTER_JOIN)
		.createAlias("mascota.petraza", "raza", JoinType.LEFT_OUTER_JOIN)
		.createAlias("mascota.cotpersona", "persona", JoinType.LEFT_OUTER_JOIN)
		.createAlias("cotlugar", "lugar", JoinType.LEFT_OUTER_JOIN)
		.createAlias("lugar.cottipolugar", "tipolugar", JoinType.LEFT_OUTER_JOIN)
		.createAlias("cotestadopago", "estadopago", JoinType.LEFT_OUTER_JOIN);
		
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
		.addOrder(Order.desc("fechaemision").ignoreCase());
		
		Criteria joinPetmascotahomenaje = criteriaPetordenservicio.createCriteria("petmascotahomenaje", "m", JoinType.LEFT_OUTER_JOIN, Restrictions.eq("m.setestado.idestado", 1));
		
		if(nombres != null && nombres.length > 0){
			String query = "(";
			for(int i=0;i<nombres.length;i++)
			{
				query += "lower({alias}.nombre) like lower('%"+nombres[i]+"%') ";
				
				if(i<nombres.length-1){
					query += "and ";
				}
			}
			query += ")";
			
			joinPetmascotahomenaje.add(Restrictions.sqlRestriction(query));
		}
		
		Criteria joinCotpersona = joinPetmascotahomenaje.createCriteria("m.cotpersona", "p", JoinType.LEFT_OUTER_JOIN, Restrictions.eq("p.setestado.idestado", 1));
		
		joinCotpersona.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
		
		lisPetordenservicio = (List<Petordenservicio>) joinCotpersona.list();
		
		if(lisPetordenservicio != null && lisPetordenservicio.size() > 0){
			Criteria criteriaCount = session.createCriteria(Petordenservicio.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("setestado.idestado", 1));
			
			Criteria joinPetmascotahomenajeCount = criteriaCount.createCriteria("petmascotahomenaje", "m", JoinType.LEFT_OUTER_JOIN, Restrictions.eq("m.setestado.idestado", 1));
			
			if(nombres != null && nombres.length > 0){
				String query = "(";
				for(int i=0;i<nombres.length;i++)
				{
					query += "lower({alias}.nombre) like lower('%"+nombres[i]+"%') ";
					
					if(i<nombres.length-1){
						query += "and ";
					}
				}
				query += ")";
				
				joinPetmascotahomenajeCount.add(Restrictions.sqlRestriction(query));
			}
			
			Criteria joinCotpersonaCount = joinPetmascotahomenajeCount.createCriteria("m.cotpersona", "p", JoinType.LEFT_OUTER_JOIN, Restrictions.eq("p.setestado.idestado", 1));
			
			Object object = joinCotpersonaCount.uniqueResult();
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
