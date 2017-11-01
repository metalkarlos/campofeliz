package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.web.pet.pojo.annotations.Petmascotahomenaje;

public class PetmascotaDAO {
	
	public int maxIdPetmascota(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idmascota) as max from Petmascotahomenaje").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	public Petmascotahomenaje getPetmascotaById(Session session, int id) throws Exception {
		Petmascotahomenaje petmascotahomenaje = new Petmascotahomenaje();
		
		Criteria criteria = session.createCriteria(Petmascotahomenaje.class)
		.add( Restrictions.eq("idmascota", id) )
		.add( Restrictions.eq("setestado.idestado", 1))
		.createAlias("cotpersona","p", JoinType.LEFT_OUTER_JOIN)
		.createAlias("p.cottipoidentificacion", "t1", JoinType.LEFT_OUTER_JOIN)
		.createAlias("petraza","raza", JoinType.LEFT_OUTER_JOIN)
		.createAlias("petespecie", "especie")
		.createAlias("cottipoidentificacion", "t2", JoinType.LEFT_OUTER_JOIN)
		//.createAlias("petfotomascotas", "foto", Criteria.LEFT_JOIN, Restrictions.eq("foto.setestado.idestado", 1))
		//.add( Restrictions.eq("foto.setestado.idestado", 1))
		;
		
		petmascotahomenaje = (Petmascotahomenaje) criteria.uniqueResult();
		
		return petmascotahomenaje;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascotahomenaje> lisPetmascota(Session session, int especie) throws Exception {
		List<Petmascotahomenaje> lisPetmascota = null;
		
		Criteria criteria = session.createCriteria(Petmascotahomenaje.class)
		.add( Restrictions.eq("petespecie.idespecie", especie) )
		.add( Restrictions.eq("setestado.idestado", 1) )
		.addOrder(Order.asc("nombre").ignoreCase());
		
		lisPetmascota = (List<Petmascotahomenaje>) criteria.list();

		return lisPetmascota;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascotahomenaje> lisPetmascotaByEspecieByPage(Session session, int especie, String nombre, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Petmascotahomenaje> lisPetmascotahomenaje = null;
		
		Criteria criteria = session.createCriteria(Petmascotahomenaje.class)
		.add( Restrictions.eq("setestado.idestado", 1) )
		.createAlias("petraza", "raza", JoinType.LEFT_OUTER_JOIN)
		.createAlias("cotpersona", "persona", JoinType.LEFT_OUTER_JOIN)
		.createAlias("petespecie", "especie", JoinType.LEFT_OUTER_JOIN);
		
		if(especie > 0){
			criteria.add( Restrictions.eq("especie.idespecie", especie) );
		}
		
		if(nombre != null && nombre.trim().length() > 0){
			criteria.add( Restrictions.like("nombre", "%"+nombre.replaceAll(" ", "%")+"%").ignoreCase());
		}
		
		criteria.addOrder(Order.desc("fechafallecimiento").ignoreCase())
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		lisPetmascotahomenaje = (List<Petmascotahomenaje>) criteria.list();
		
		if(lisPetmascotahomenaje != null && lisPetmascotahomenaje.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Petmascotahomenaje.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("setestado.idestado", 1))
			.createAlias("petraza", "raza", JoinType.LEFT_OUTER_JOIN)
			.createAlias("cotpersona", "persona", JoinType.LEFT_OUTER_JOIN)
			.createAlias("petespecie", "especie", JoinType.LEFT_OUTER_JOIN);
			
			if(especie > 0){
				criteriaCount.add( Restrictions.eq("especie.idespecie", especie) );
			}
			
			if(nombre != null && nombre.trim().length() > 0){
				criteriaCount.add( Restrictions.like("nombre", "%"+nombre.replaceAll(" ", "%")+"%").ignoreCase());
			}
			
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		} else {
			args[0] = 0;
		}
		
		return lisPetmascotahomenaje;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascotahomenaje> lisPetmascotaByPage(Session session, String nombre, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Petmascotahomenaje> lisPetmascotahomenaje = null;
		
		Criteria criteria = session.createCriteria(Petmascotahomenaje.class)
		.add( Restrictions.eq("setestado.idestado", 1) )
		.createAlias("petraza", "r", JoinType.LEFT_OUTER_JOIN)
		.createAlias("cotpersona", "p", JoinType.LEFT_OUTER_JOIN)
		.createAlias("p.cottipoidentificacion", "tid", JoinType.LEFT_OUTER_JOIN)
		.createAlias("petespecie", "e");
		
		if(nombre != null && nombre.trim().length() > 0){
			criteria.add( Restrictions.like("nombre", "%"+nombre.replaceAll(" ", "%")+"%").ignoreCase());
		}
		
		criteria.addOrder(Order.asc("nombre").ignoreCase())
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		lisPetmascotahomenaje = (List<Petmascotahomenaje>) criteria.list();
		
		if(lisPetmascotahomenaje != null && lisPetmascotahomenaje.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Petmascotahomenaje.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("setestado.idestado", 1))
			.createAlias("petraza", "r", JoinType.LEFT_OUTER_JOIN)
			.createAlias("cotpersona", "p", JoinType.LEFT_OUTER_JOIN)
			.createAlias("petespecie", "e");
			
			if(nombre != null && nombre.trim().length() > 0){
				criteriaCount.add( Restrictions.like("nombre", "%"+nombre.replaceAll(" ", "%")+"%").ignoreCase());
			}
			
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		} else {
			args[0] = 0;
		}
		
		return lisPetmascotahomenaje;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascotahomenaje> lisPetmascotaBusqueda(Session session, Petmascotahomenaje petmascotahomenaje, String[] caracteristicas)
			throws Exception {
		List<Petmascotahomenaje> lisPetmascotahomenaje = null;
		
		Criteria criteria = session.createCriteria(Petmascotahomenaje.class)
		.add(Restrictions.eq("setestado.idestado", 1));
		
		
		if(petmascotahomenaje.getNombre() != null && petmascotahomenaje.getNombre().trim().length() > 0){
			criteria.add(Restrictions.like("nombre", "%"+petmascotahomenaje.getNombre().replaceAll(" ", "%")+"%").ignoreCase());
		}
		if(petmascotahomenaje.getPetespecie().getIdespecie() > 0){
			criteria.add(Restrictions.eq("petespecie.idespecie", petmascotahomenaje.getPetespecie().getIdespecie()));
		}
		if(petmascotahomenaje.getPetraza().getIdraza() > 0){
			criteria.add(Restrictions.eq("petraza.idraza", petmascotahomenaje.getPetraza().getIdraza()));
		}
		if(petmascotahomenaje.getSexo() > 0){
			criteria.add(Restrictions.eq("sexo", petmascotahomenaje.getSexo()));
		}

		if(caracteristicas != null && caracteristicas.length > 0){
			String query = "(";
			for(int i=0;i<caracteristicas.length;i++)
			{
				query += "lower({alias}.caracteristicas) like lower('%"+caracteristicas[i]+"%') ";
				if(i<caracteristicas.length-1){
					query += "or ";
				}
			}
			query += ")";
			
			criteria.add(Restrictions.sqlRestriction(query));
		}
		
		criteria.addOrder(Order.asc("nombre").ignoreCase());
		
		lisPetmascotahomenaje = criteria.list();
		
		return lisPetmascotahomenaje;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascotahomenaje> lisPetmascotaBusquedaByPage(Session session, Petmascotahomenaje petmascotahomenaje, String[] caracteristicas, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Petmascotahomenaje> lisPetmascotahomenaje = null;
		
		Criteria criteria = session.createCriteria(Petmascotahomenaje.class)
		.add(Restrictions.eq("setestado.idestado", 1));
		
		if(petmascotahomenaje.getIdmascota() > 0){
			criteria.add(Restrictions.eq("idmascota", petmascotahomenaje.getIdmascota()));
		}
		if(petmascotahomenaje.getIdmascotaveterinaria() != null && petmascotahomenaje.getIdmascotaveterinaria().trim().length() > 0){
			criteria.add(Restrictions.like("idmascotaveterinaria", petmascotahomenaje.getIdmascotaveterinaria()).ignoreCase());
		}
		if(petmascotahomenaje.getNombre() != null && petmascotahomenaje.getNombre().trim().length() > 0){
			criteria.add(Restrictions.like("nombre", "%"+petmascotahomenaje.getNombre().replaceAll(" ", "%")+"%").ignoreCase());
		}
		if(petmascotahomenaje.getPetespecie().getIdespecie() > 0){
			criteria.add(Restrictions.eq("petespecie.idespecie", petmascotahomenaje.getPetespecie().getIdespecie()));
		}
		if(petmascotahomenaje.getPetraza().getIdraza() > 0){
			criteria.add(Restrictions.eq("petraza.idraza", petmascotahomenaje.getPetraza().getIdraza()));
		}
		if(petmascotahomenaje.getSexo() != null && petmascotahomenaje.getSexo() > 0){
			criteria.add(Restrictions.eq("sexo", petmascotahomenaje.getSexo()));
		}
		if(petmascotahomenaje.getCotpersona() != null && petmascotahomenaje.getCotpersona().getIdpersona() > 0){
			criteria.add(Restrictions.eq("cotpersona.idpersona", petmascotahomenaje.getCotpersona().getIdpersona()));
		}

		if(caracteristicas != null && caracteristicas.length > 0){
			String query = "(";
			for(int i=0;i<caracteristicas.length;i++)
			{
				query += "lower({alias}.caracteristicas) like lower('%"+caracteristicas[i]+"%') ";
				if(i<caracteristicas.length-1){
					query += "or ";
				}
			}
			query += ")";
			
			criteria.add(Restrictions.sqlRestriction(query));
		}
		
		criteria.addOrder(Order.asc("nombre").ignoreCase())
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
		
		lisPetmascotahomenaje = criteria.list();

		if(lisPetmascotahomenaje != null && lisPetmascotahomenaje.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria(Petmascotahomenaje.class)
			.setProjection( Projections.rowCount())
			.add(Restrictions.eq("setestado.idestado", 1));
			
			if(petmascotahomenaje.getNombre() != null && petmascotahomenaje.getNombre().trim().length() > 0){
				criteriaCount.add(Restrictions.like("nombre", "%"+petmascotahomenaje.getNombre().replaceAll(" ", "%")+"%").ignoreCase());
			}
			if(petmascotahomenaje.getPetespecie().getIdespecie() > 0){
				criteriaCount.add(Restrictions.eq("petespecie.idespecie", petmascotahomenaje.getPetespecie().getIdespecie()));
			}
			if(petmascotahomenaje.getPetraza().getIdraza() > 0){
				criteriaCount.add(Restrictions.eq("petraza.idraza", petmascotahomenaje.getPetraza().getIdraza()));
			}
			if(petmascotahomenaje.getSexo() != null && petmascotahomenaje.getSexo() > 0){
				criteriaCount.add(Restrictions.eq("sexo", petmascotahomenaje.getSexo()));
			}
			if(petmascotahomenaje.getCotpersona() != null && petmascotahomenaje.getCotpersona().getIdpersona() > 0){
				criteriaCount.add(Restrictions.eq("cotpersona.idpersona", petmascotahomenaje.getCotpersona().getIdpersona()));
			}

			if(caracteristicas != null && caracteristicas.length > 0){
				String query = "(";
				for(int i=0;i<caracteristicas.length;i++)
				{
					query += "lower({alias}.caracteristicas) like lower('%"+caracteristicas[i]+"%') ";
					if(i<caracteristicas.length-1){
						query += "or ";
					}
				}
				query += ")";
				
				criteriaCount.add(Restrictions.sqlRestriction(query));
			}
			
			criteriaCount.setMaxResults(pageSize)
			.setFirstResult(pageNumber);
			
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		}
		else
		{
			args[0] = 0;
		}
		
		return lisPetmascotahomenaje;
	}
	
	public List<Petmascotahomenaje> lisMascotas(Session session, int especie) throws Exception {
		List<Petmascotahomenaje> lisPetmascotahomenaje = null;
		
		/*String query = "select foto, mascota from Petfoto foto join foto.petmascota mascota"; 
		query += " where mascota.pettipo.idtipo = :tipo";
		query += " and foto.mostrar = 1";
		query += " order by mascota.nombre";
		
		@SuppressWarnings("rawtypes")
		Iterator mascotafoto = session.createQuery(query)
		.setInteger("tipo", tipo)
		.list()
        .iterator();*/
		
		//Consulta resuelta con dialecto SQL nativo de Postgres
		/*String query = "select {foto.*}, {mascota.*} ";
		query += " from pets.Petfoto foto right join pets.Petmascota mascota";
		query += " on foto.idmascota = mascota.idmascota";
		query += " and foto.mostrar = " + 1;
		query += " where mascota.idtipo = " + tipo; 
		query += " order by mascota.nombre";
		
		@SuppressWarnings("rawtypes")
		Iterator mascotafoto = session.createSQLQuery(query)
		    .addEntity("foto", Petfoto.class)
		    .addEntity("mascota", Petmascota.class)
		    .list()
		    .iterator();*/
		
		/*@SuppressWarnings("rawtypes")
		Iterator mascotafoto = criteria.list().iterator();
				
		arraydatos = new ArrayList<Mascotas>();
		while ( mascotafoto.hasNext() ) {
		    Object[] tuple = (Object[]) mascotafoto.next();
		    Mascotas mascotas = new Mascotas();
		    mascotas.setPetfoto(tuple[0]==null?new Petfoto():(Petfoto) tuple[0]);
		    mascotas.setPetmascota((Petmascota) tuple[1]);
		    arraydatos.add(mascotas);
		}*/
		
		//Este método construye de forma correcta el query con el inconveniente de no cargar los objetos referenciales
		/*Criteria criteria = session.createCriteria(Petmascota.class, "mascota")
				.add( Restrictions.eq("mascota.pettipo.idtipo", tipo) )
				.createAlias("petfotos", "foto", Criteria.LEFT_JOIN, Restrictions.eq("foto.mostrar", 1) )
				.addOrder( Order.asc("mascota.nombre") );
		
		arraydatos = criteria.list();*/
		
		/*String query = "select foto, mascota from Petmascota mascota right join mascota.petfotos foto"; 
		query += " where mascota.pettipo.idtipo = :tipo";
		query += " and foto.mostrar = 1";
		query += " order by mascota.nombre";
		
		@SuppressWarnings("rawtypes")
		java.util.Iterator mascotafoto = session.createQuery(query)
		.setInteger("tipo", tipo)
		.list()
        .iterator();
		
		List<Mascotas> datos = new ArrayList<Mascotas>();
		while ( mascotafoto.hasNext() ) {
		    Object[] tuple = (Object[]) mascotafoto.next();
		    Mascotas mascotas = new Mascotas();
		    mascotas.setPetfoto(tuple[0]==null?new Petfoto():(Petfoto) tuple[0]);
		    mascotas.setPetmascota((Petmascota) tuple[1]);
		    datos.add(mascotas);
		}*/
		/*
		lisMascotas = new ArrayList<Mascotas>();
		while ( mascotafoto.hasNext() ) {
		    Petmascota petmascota = (Petmascota) mascotafoto.next();
		    Mascotas mascotas = new Mascotas();
			Iterator fotos = petmascota.getPetfotos().iterator(); 
		    while ( fotos.hasNext() ) {
		    	mascotas.setPetfoto((Petfoto) fotos.next());
		    }
		    petmascota.setPetfotos(new HashSet(0));
		    mascotas.setPetmascota(petmascota);
		    lisMascotas.add(mascotas);
		}*/
		
		return lisPetmascotahomenaje;
	}

	public void savePetmascota(Session session, Petmascotahomenaje petmascotahomenaje) throws Exception {
		session.save(petmascotahomenaje);
	}
	
	public void updatePetmascota(Session session, Petmascotahomenaje petmascotahomenaje) throws Exception {
		session.update(petmascotahomenaje);
	}
}
