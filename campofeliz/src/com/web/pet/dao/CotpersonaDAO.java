package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.web.pet.pojo.annotations.Cotpersona;

public class CotpersonaDAO {

	public int maxIdCotpersona(Session session) throws Exception {
		int max = 0;
		
		Criteria criteria = session.createCriteria(Cotpersona.class)
		.setProjection(Projections.max("idpersona"));
		
		Object object = criteria.uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}

	public Cotpersona getCotpersonaById(Session session, int idpersona) throws Exception {
		Cotpersona cotpersona = null;
		
		Criteria criteria = session.createCriteria(Cotpersona.class)
		.add( Restrictions.eq("idpersona", idpersona) )
		.add( Restrictions.eq("setestado.idestado", 1) )
		.createAlias("cottipoidentificacion", "tipoidentificacion", JoinType.LEFT_OUTER_JOIN);
		
		cotpersona = (Cotpersona)criteria.uniqueResult();
		
		return cotpersona;
	}
	
	public Cotpersona getCotpersonaConObjetosById(Session session, int idpersona) throws Exception {
		Cotpersona cotpersona = null;
		
		Criteria criteria = session.createCriteria(Cotpersona.class, "per")
				.add( Restrictions.eq("per.idpersona", idpersona))
				.add( Restrictions.eq("per.setestado.idestado", 1))
				//.createAlias("per.cotfotopersonas", "foto", Criteria.LEFT_JOIN, Restrictions.eq("foto.setestado.idestado", 1))
				.createAlias("per.cottipoidentificacion", "tid", JoinType.LEFT_OUTER_JOIN);
				//.add( Restrictions.eq("foto.setestado.idestado", 1));
		
		cotpersona = (Cotpersona) criteria.uniqueResult();
		
		/*if(cotpersona != null && cotpersona.getCotfotopersonas() != null && cotpersona.getCotfotopersonas().size() > 0){
			
				Set<Cotfotopersona> tmp = new HashSet<Cotfotopersona>();
				for(Cotfotopersona foto : cotpersona.getCotfotopersonas()){
					if(foto.getSetestado().getIdestado() == 1){
						tmp.add(foto);
					}
				}
				cotpersona.setCotfotopersonas(tmp);
				
		}*/
		
		return cotpersona;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cotpersona> lisCotpersonaByPage(Session session, String[] nombres, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Cotpersona> lisCotpersona = null;
		
		Criteria criteria = session.createCriteria(Cotpersona.class)
		.add( Restrictions.eq("setestado.idestado", 1))
		.createAlias("cottipoidentificacion", "tid", JoinType.LEFT_OUTER_JOIN);
		
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
			
			criteria.add(Restrictions.sqlRestriction(query));
		}
		
		criteria.addOrder(Order.asc("nombre1").ignoreCase())
		.addOrder(Order.asc("nombre2").ignoreCase())
		.addOrder(Order.asc("apellido1").ignoreCase())
		.addOrder(Order.asc("apellido2").ignoreCase())
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
		
		lisCotpersona = (List<Cotpersona>) criteria.list();
		
		if(lisCotpersona != null && lisCotpersona.size() > 0){
			Criteria criteriaCount = session.createCriteria(Cotpersona.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("setestado.idestado", 1))
			.createAlias("cottipoidentificacion", "tid", JoinType.LEFT_OUTER_JOIN);
			
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
				
				criteriaCount.add(Restrictions.sqlRestriction(query));
			}
			
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		} else {
			args[0] = 0;
		}
		
		return lisCotpersona;
	}
	
	public void saveCotpersona(Session session, Cotpersona cotpersona) throws Exception {
		session.save(cotpersona);
	}

	public void updateCotpersona(Session session, Cotpersona cotpersona) throws Exception {
		session.update(cotpersona);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cotpersona> lisCotpersonaBusqueda(Session session, Cotpersona cotpersona) throws Exception {
		List<Cotpersona> lisCotpersona = null;
		
		Criteria criteria = session.createCriteria(Cotpersona.class)
		.add( Restrictions.eq("setestado.idestado", 1));
		
		if(cotpersona.getApellido1() != null && cotpersona.getApellido1().trim().length() > 0){
			criteria.add( Restrictions.like("apellido1", "%"+cotpersona.getApellido1()+"%").ignoreCase());
		}
		
		if(cotpersona.getApellido2() != null && cotpersona.getApellido2().trim().length() > 0){
			criteria.add( Restrictions.like("apellido2", "%"+cotpersona.getApellido2()+"%").ignoreCase());
		}
		
		if(cotpersona.getNombre1() != null && cotpersona.getNombre1().trim().length() > 0){
			criteria.add( Restrictions.like("nombre1", "%"+cotpersona.getNombre1()+"%").ignoreCase());
		}
		
		if(cotpersona.getNombre2() != null && cotpersona.getNombre2().trim().length() > 0){
			criteria.add( Restrictions.like("nombre2", "%"+cotpersona.getNombre2()+"%").ignoreCase());
		}
		
		if(cotpersona.getAlias() != null && cotpersona.getAlias().trim().length() > 0){
			criteria.add( Restrictions.like("alias", cotpersona.getAlias()).ignoreCase());
		}
		
		if(cotpersona.getCottipoidentificacion().getIdtipoidentificacion() > 0){
			criteria.add( Restrictions.eq("cottipoidentificacion.idtipoidentificacion", cotpersona.getCottipoidentificacion().getIdtipoidentificacion()));
		}
		
		if(cotpersona.getNumeroidentificacion() != null && cotpersona.getNumeroidentificacion().trim().length() > 0){
			criteria.add( Restrictions.eq("numeroidentificacion", cotpersona.getNumeroidentificacion()).ignoreCase());
		}
		
		if(cotpersona.getFechanacimiento() != null){
			criteria.add( Restrictions.eq("fechanacimiento", cotpersona.getFechanacimiento()));
		}
		
		if(cotpersona.getTelefono() != null && cotpersona.getTelefono().trim().length() > 0){
			criteria.add( Restrictions.like("telefono", "%"+cotpersona.getTelefono().replaceAll(" ", "%")+"%").ignoreCase());
		}
		
		if(cotpersona.getDireccion() != null && cotpersona.getDireccion().trim().length() > 0){
			criteria.add( Restrictions.like("direccion", "%"+cotpersona.getDireccion().replaceAll(" ", "%")+"%").ignoreCase());
		}
		
		if(cotpersona.getEmail() != null && cotpersona.getEmail().trim().length() > 0){
			criteria.add( Restrictions.eq("email", cotpersona.getEmail()));
		}
		
		if(cotpersona.getSexo() > 0){
			criteria.add( Restrictions.eq("sexo", cotpersona.getSexo()));
		}
		
		criteria.addOrder(Order.asc("nombre1").ignoreCase())
		.addOrder(Order.asc("nombre2").ignoreCase())
		.addOrder(Order.asc("apellido1").ignoreCase())
		.addOrder(Order.asc("apellido2").ignoreCase());
		
		lisCotpersona = criteria.list();
		
		return lisCotpersona;
	}

	@SuppressWarnings("unchecked")
	public List<Cotpersona> lisCotpersonaBusquedaByPage(Session session, Cotpersona cotpersona, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Cotpersona> lisCotpersona = null;
		
		Criteria criteria = session.createCriteria(Cotpersona.class)
		.add( Restrictions.eq("setestado.idestado", 1));
		
		if(cotpersona.getApellido1() != null && cotpersona.getApellido1().trim().length() > 0){
			criteria.add( Restrictions.like("apellido1", "%"+cotpersona.getApellido1()+"%").ignoreCase());
		}
		
		if(cotpersona.getApellido2() != null && cotpersona.getApellido2().trim().length() > 0){
			criteria.add( Restrictions.like("apellido2", "%"+cotpersona.getApellido2()+"%").ignoreCase());
		}
		
		if(cotpersona.getNombre1() != null && cotpersona.getNombre1().trim().length() > 0){
			criteria.add( Restrictions.like("nombre1", "%"+cotpersona.getNombre1()+"%").ignoreCase());
		}
		
		if(cotpersona.getNombre2() != null && cotpersona.getNombre2().trim().length() > 0){
			criteria.add( Restrictions.like("nombre2", "%"+cotpersona.getNombre2()+"%").ignoreCase());
		}
		
		if(cotpersona.getAlias() != null && cotpersona.getAlias().trim().length() > 0){
			criteria.add( Restrictions.like("alias", cotpersona.getAlias()).ignoreCase());
		}
		
		if(cotpersona.getCottipoidentificacion().getIdtipoidentificacion() > 0){
			criteria.add( Restrictions.eq("cottipoidentificacion.idtipoidentificacion", cotpersona.getCottipoidentificacion().getIdtipoidentificacion()));
		}
		
		if(cotpersona.getNumeroidentificacion() != null && cotpersona.getNumeroidentificacion().trim().length() > 0){
			criteria.add( Restrictions.eq("numeroidentificacion", cotpersona.getNumeroidentificacion()).ignoreCase());
		}
		
		if(cotpersona.getFechanacimiento() != null){
			criteria.add( Restrictions.eq("fechanacimiento", cotpersona.getFechanacimiento()));
		}
		
		if(cotpersona.getTelefono() != null && cotpersona.getTelefono().trim().length() > 0){
			criteria.add( Restrictions.like("telefono", "%"+cotpersona.getTelefono().replaceAll(" ", "%")+"%").ignoreCase());
		}
		
		if(cotpersona.getDireccion() != null && cotpersona.getDireccion().trim().length() > 0){
			criteria.add( Restrictions.like("direccion", "%"+cotpersona.getDireccion().replaceAll(" ", "%")+"%").ignoreCase());
		}
		
		if(cotpersona.getEmail() != null && cotpersona.getEmail().trim().length() > 0){
			criteria.add( Restrictions.eq("email", cotpersona.getEmail()));
		}
		
		if(cotpersona.getSexo() > 0){
			criteria.add( Restrictions.eq("sexo", cotpersona.getSexo()));
		}
		
		criteria.addOrder(Order.asc("nombre1").ignoreCase())
		.addOrder(Order.asc("nombre2").ignoreCase())
		.addOrder(Order.asc("apellido1").ignoreCase())
		.addOrder(Order.asc("apellido2").ignoreCase())
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
		
		lisCotpersona = criteria.list();
		
		if(lisCotpersona != null && lisCotpersona.size() > 0){
			Criteria criteriaCount = session.createCriteria(Cotpersona.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("setestado.idestado", 1));
			
			if(cotpersona.getApellido1() != null && cotpersona.getApellido1().trim().length() > 0){
				criteriaCount.add( Restrictions.like("apellido1", "%"+cotpersona.getApellido1()+"%").ignoreCase());
			}
			
			if(cotpersona.getApellido2() != null && cotpersona.getApellido2().trim().length() > 0){
				criteriaCount.add( Restrictions.like("apellido2", "%"+cotpersona.getApellido2()+"%").ignoreCase());
			}
			
			if(cotpersona.getNombre1() != null && cotpersona.getNombre1().trim().length() > 0){
				criteriaCount.add( Restrictions.like("nombre1", "%"+cotpersona.getNombre1()+"%").ignoreCase());
			}
			
			if(cotpersona.getNombre2() != null && cotpersona.getNombre2().trim().length() > 0){
				criteriaCount.add( Restrictions.like("nombre2", "%"+cotpersona.getNombre2()+"%").ignoreCase());
			}
			
			if(cotpersona.getAlias() != null && cotpersona.getAlias().trim().length() > 0){
				criteriaCount.add( Restrictions.like("alias", cotpersona.getAlias()).ignoreCase());
			}
			
			if(cotpersona.getCottipoidentificacion().getIdtipoidentificacion() > 0){
				criteriaCount.add( Restrictions.eq("cottipoidentificacion.idtipoidentificacion", cotpersona.getCottipoidentificacion().getIdtipoidentificacion()));
			}
			
			if(cotpersona.getNumeroidentificacion() != null && cotpersona.getNumeroidentificacion().trim().length() > 0){
				criteriaCount.add( Restrictions.eq("numeroidentificacion", cotpersona.getNumeroidentificacion()).ignoreCase());
			}
			
			if(cotpersona.getFechanacimiento() != null){
				criteriaCount.add( Restrictions.eq("fechanacimiento", cotpersona.getFechanacimiento()));
			}
			
			if(cotpersona.getTelefono() != null && cotpersona.getTelefono().trim().length() > 0){
				criteriaCount.add( Restrictions.like("telefono", "%"+cotpersona.getTelefono().replaceAll(" ", "%")+"%").ignoreCase());
			}
			
			if(cotpersona.getDireccion() != null && cotpersona.getDireccion().trim().length() > 0){
				criteriaCount.add( Restrictions.like("direccion", "%"+cotpersona.getDireccion().replaceAll(" ", "%")+"%").ignoreCase());
			}
			
			if(cotpersona.getEmail() != null && cotpersona.getEmail().trim().length() > 0){
				criteriaCount.add( Restrictions.eq("email", cotpersona.getEmail()));
			}
			
			if(cotpersona.getSexo() > 0){
				criteria.add( Restrictions.eq("sexo", cotpersona.getSexo()));
			}
			
			criteriaCount.setMaxResults(pageSize)
			.setFirstResult(pageNumber);
			
			Object object = criteriaCount.uniqueResult();
			int max = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = max;
		}
		else
		{
			args[0] = 0;
		}
		
		return lisCotpersona;
	}

}
