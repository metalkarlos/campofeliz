package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.web.pet.daointerface.PetfotoDAOInterface;
import com.web.pet.pojo.annotations.Petfoto;

public class PetfotoDAO implements PetfotoDAOInterface {
	
	public int maxIdPetfoto(Session session) throws Exception {
		int max = 0;
		
		Object object = session.createQuery("select max(idfoto) as max from Petfoto").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petfoto> lisPetfotoByPetId(Session session, int idmascota) throws Exception {
		List<Petfoto> arraydatos = null;
		
		Criteria criteria = session.createCriteria(Petfoto.class)
		.add( Restrictions.eq("petmascota.idmascota", idmascota) )
		.add( Restrictions.eq("petestado.idestado", 1));
			
		arraydatos = (List<Petfoto>) criteria.list();
		
		return arraydatos;
	}

	public Petfoto getPetfotoById(Session session, int id) throws Exception {
		Petfoto petfoto = new Petfoto();
		
		Criteria criteria = session.createCriteria(Petfoto.class)
		.add( Restrictions.eq("id.idfoto", id) )
		.add( Restrictions.eqProperty("id.fecharegistro", "pk.fecharegistro"))
		.createCriteria("petfotopk","pk");
		
		petfoto = (Petfoto) criteria.uniqueResult();
		
		return petfoto;
	}
	
	public Petfoto getPetfotoPerfilByPetId(Session session, int idmascota) throws Exception {
		Petfoto petfoto = new Petfoto();
		
		Criteria criteria = session.createCriteria(Petfoto.class)
		.add( Restrictions.eq("petmascota.idmascota", idmascota) )
		.add( Restrictions.eq("mostrar", 1) )
		.add( Restrictions.eq("petestado.idestado", 1) );
		
		petfoto = (Petfoto) criteria.uniqueResult();
		
		return petfoto;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petfoto> lisPetfotoPerfil(Session session, int tipo) throws Exception {
		List<Petfoto> arraydatos = null;
		
		Criteria criteria = session
		.createCriteria(Petfoto.class)
		.add( Restrictions.eq("mostrar", 1) )
		.add( Restrictions.eqProperty("m.idmascota", "petmascota.idmascota") )
		.add( Restrictions.eq("m.pettipo.idtipo", tipo) )
		.createCriteria("petmascota","m")
		.addOrder(Order.asc("nombre"));
			
		arraydatos = (List<Petfoto>) criteria.list();
		
		return arraydatos;
	}
	
	public void savePetfoto(Session session, Petfoto petfoto) throws Exception {
		session.save(petfoto);
	}

	@Override
	public void updatePetfoto(Session session, Petfoto petfoto)
			throws Exception {
		session.update(petfoto);
	}

	@Override
	public void resetPetfotoPerfilByPetId(Session session, int idmascota)
			throws Exception {
		String hqlUpdate = "update Petfoto foto set foto.mostrar = 0 where foto.petmascota.idmascota = :idmascota";
		session.createQuery( hqlUpdate )
		.setInteger( "idmascota", idmascota )
		.executeUpdate();
	}

	@Override
	public void deletePetfoto(Session session, int idfoto)
			throws Exception {
		String hqlUpdate = "delete Petfoto foto where foto.idfoto = :idfoto";
		session.createQuery( hqlUpdate )
		.setInteger( "idfoto", idfoto )
		.executeUpdate();
	}
}

