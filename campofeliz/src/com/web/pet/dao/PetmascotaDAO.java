package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.pet.daointerface.PetmascotaDAOInterface;
import com.web.pet.pojo.annotations.Petmascota;

public class PetmascotaDAO implements PetmascotaDAOInterface {
	
	public int maxIdPetmascota(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idmascota) as max from Petmascota").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascota> lisPetmascota(Session session, int especie) throws Exception {
		List<Petmascota> lisPetmascota = null;
		
		Criteria criteria = session.createCriteria(Petmascota.class)
		.add( Restrictions.eq("petespecie.idespecie", especie) )
		.add( Restrictions.eq("petestado.idestado", 1) )
		.addOrder(Order.asc("nombre"));
		
		lisPetmascota = (List<Petmascota>) criteria.list();

		return lisPetmascota;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascota> lisPetmascotaByEspecieByPage(Session session, int especie, String nombre, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Petmascota> lisPetmascota = null;
		
		Criteria criteria = session.createCriteria(Petmascota.class)
		.add( Restrictions.eq("petespecie.idespecie", especie) )
		.add( Restrictions.eq("petestado.idestado", 1) )
		.createAlias("petraza", "raza")
		.createAlias("cotpersona", "persona");
		
		if(nombre != null && nombre.trim().length() > 0){
			criteria.add( Restrictions.like("nombre", "%"+nombre.replaceAll(" ", "%")+"%").ignoreCase());
		}
		
		criteria.addOrder(Order.asc("nombre"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		lisPetmascota = (List<Petmascota>) criteria.list();
		
		if(lisPetmascota != null && lisPetmascota.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Petmascota.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("petespecie.idespecie", especie))
			.add( Restrictions.eq("petestado.idestado", 1))
			.createAlias("petraza", "raza")
			.createAlias("cotpersona", "persona");
			
			if(nombre != null && nombre.trim().length() > 0){
				criteriaCount.add( Restrictions.like("nombre", "%"+nombre.replaceAll(" ", "%")+"%").ignoreCase());
			}
			
			//Object object = session.createQuery("select count(*) from Petmascota where petespecie.idespecie = "+especie+" and petestado.idestado = 1").uniqueResult();
			Object object = criteriaCount.uniqueResult();
			int count = (object==null?0:Integer.parseInt(object.toString()));
			args[0] = count;
		} else {
			args[0] = 0;
		}
		
		return lisPetmascota;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascota> lisPetmascotaByPage(Session session, String nombre, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Petmascota> lisPetmascota = null;
		
		Criteria criteria = session.createCriteria(Petmascota.class, "m")
		.add( Restrictions.eq("petestado.idestado", 1) )
		.createAlias("petraza", "r")
		.createAlias("cotpersona", "p")
		.createAlias("petespecie", "e");
		
		if(nombre != null && nombre.trim().length() > 0){
			criteria.add( Restrictions.like("nombre", "%"+nombre.replaceAll(" ", "%")+"%").ignoreCase());
		}
		
		criteria.addOrder(Order.asc("nombre"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
			
		lisPetmascota = (List<Petmascota>) criteria.list();
		
		if(lisPetmascota != null && lisPetmascota.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria( Petmascota.class)
			.setProjection( Projections.rowCount())
			.add( Restrictions.eq("petestado.idestado", 1))
			.createAlias("petraza", "r")
			.createAlias("cotpersona", "p")
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
		
		return lisPetmascota;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Petmascota> lisPetmascotaBusqueda(Session session, Petmascota petmascota, String[] caracteristicas)
			throws Exception {
		List<Petmascota> lisPetmascota = null;
		
		Criteria criteria = session.createCriteria(Petmascota.class)
		.add(Restrictions.eq("petestado.idestado", 1));
		
		
		if(petmascota.getNombre() != null && petmascota.getNombre().trim().length() > 0){
			criteria.add(Restrictions.like("nombre", "%"+petmascota.getNombre().replaceAll(" ", "%")+"%").ignoreCase());
		}
		if(petmascota.getPetespecie().getIdespecie() > 0){
			criteria.add(Restrictions.eq("petespecie.idespecie", petmascota.getPetespecie().getIdespecie()));
		}
		if(petmascota.getPetraza().getIdraza() > 0){
			criteria.add(Restrictions.eq("petraza.idraza", petmascota.getPetraza().getIdraza()));
		}
		if(petmascota.getSexo() > 0){
			criteria.add(Restrictions.eq("sexo", petmascota.getSexo()));
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
		
		criteria.addOrder(Order.asc("nombre"));
		
		lisPetmascota = criteria.list();
		
		return lisPetmascota;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascota> lisPetmascotaBusquedaByPage(Session session, Petmascota petmascota, String[] caracteristicas, int pageSize, int pageNumber, int[] args) throws Exception {
		List<Petmascota> lisPetmascota = null;
		
		Criteria criteria = session.createCriteria(Petmascota.class)
		.add(Restrictions.eq("petestado.idestado", 1));
		
		
		if(petmascota.getNombre() != null && petmascota.getNombre().trim().length() > 0){
			criteria.add(Restrictions.like("nombre", "%"+petmascota.getNombre().replaceAll(" ", "%")+"%").ignoreCase());
		}
		if(petmascota.getPetespecie().getIdespecie() > 0){
			criteria.add(Restrictions.eq("petespecie.idespecie", petmascota.getPetespecie().getIdespecie()));
		}
		if(petmascota.getPetraza().getIdraza() > 0){
			criteria.add(Restrictions.eq("petraza.idraza", petmascota.getPetraza().getIdraza()));
		}
		if(petmascota.getSexo() != null && petmascota.getSexo() > 0){
			criteria.add(Restrictions.eq("sexo", petmascota.getSexo()));
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
		
		criteria.addOrder(Order.asc("nombre"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
		
		lisPetmascota = criteria.list();

		if(lisPetmascota != null && lisPetmascota.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria(Petmascota.class)
			.setProjection( Projections.rowCount())
			.add(Restrictions.eq("petestado.idestado", 1));
			
			if(petmascota.getNombre() != null && petmascota.getNombre().trim().length() > 0){
				criteriaCount.add(Restrictions.like("nombre", "%"+petmascota.getNombre().replaceAll(" ", "%")+"%").ignoreCase());
			}
			if(petmascota.getPetespecie().getIdespecie() > 0){
				criteriaCount.add(Restrictions.eq("petespecie.idespecie", petmascota.getPetespecie().getIdespecie()));
			}
			if(petmascota.getPetraza().getIdraza() > 0){
				criteriaCount.add(Restrictions.eq("petraza.idraza", petmascota.getPetraza().getIdraza()));
			}
			if(petmascota.getSexo() != null && petmascota.getSexo() > 0){
				criteriaCount.add(Restrictions.eq("sexo", petmascota.getSexo()));
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
		
		return lisPetmascota;
	}
	
	public Petmascota getPetmascotaById(Session session, int id) throws Exception {
		Petmascota petmascota = new Petmascota();
		
		Criteria criteria = session.createCriteria(Petmascota.class)
		.add( Restrictions.eq("idmascota", id) )
		.add( Restrictions.eq("petestado.idestado", 1))
		.createAlias("cotpersona","persona")
		.createAlias("petraza","raza")
		.createAlias("petespecie", "especie")
		.createAlias("cottipoidentificacion", "tipoidentificacion", Criteria.LEFT_JOIN)
		;
		
		petmascota = (Petmascota) criteria.uniqueResult();
		
		return petmascota;
	}
	
	public void savePetmascota(Session session, Petmascota petmascota) throws Exception {
		session.save(petmascota);
	}
	
	public void updatePetmascota(Session session, Petmascota petmascota) throws Exception {
		session.update(petmascota);
	}
	
	public List<Petmascota> lisMascotas(Session session, int especie) throws Exception {
		List<Petmascota> arraydatos = null;
		
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
		
		return arraydatos;
	}

}
