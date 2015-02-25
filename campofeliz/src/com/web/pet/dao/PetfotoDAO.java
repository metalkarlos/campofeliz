package com.web.pet.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.web.pet.pojo.annotations.Petfotomascota;

public class PetfotoDAO {
	
	public int maxIdPetfoto(Session session) throws Exception {
		int max = 0;
		
		Object object = session.createQuery("select max(idfotomascota) as max from Petfotomascota").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petfotomascota> lisPetfotoByPetId(Session session, int idmascota) throws Exception {
		List<Petfotomascota> lisPetfotomascota = null;
		
		Criteria criteria = session.createCriteria(Petfotomascota.class)
		.add( Restrictions.eq("petmascotahomenaje.idmascota", idmascota) )
		.add( Restrictions.eq("setestado.idestado", 1));
			
		lisPetfotomascota = (List<Petfotomascota>) criteria.list();
		
		return lisPetfotomascota;
	}

	public Petfotomascota getPetfotoPerfilByPetId(Session session, int idmascota) throws Exception {
		Petfotomascota petfotomascota = new Petfotomascota();
		
		Criteria criteria = session.createCriteria(Petfotomascota.class)
		.add( Restrictions.eq("petmascotahomenaje.idmascota", idmascota) )
		.add( Restrictions.eq("mostrar", 1) )
		.add( Restrictions.eq("setestado.idestado", 1) );
		
		petfotomascota = (Petfotomascota) criteria.uniqueResult();
		
		return petfotomascota;
	}
	
	public void savePetfoto(Session session, Petfotomascota petfotomascota) throws Exception {
		session.save(petfotomascota);
	}

	public void updatePetfoto(Session session, Petfotomascota petfotomascota)
			throws Exception {
		session.update(petfotomascota);
	}

	public void resetPetfotoPerfilByPetId(Session session, int idmascota)
			throws Exception {
		String hqlUpdate = "update Petfotomascota foto set foto.mostrar = 0 where foto.petmascotahomenaje.idmascota = :idmascota";
		session.createQuery( hqlUpdate )
		.setInteger( "idmascota", idmascota )
		.executeUpdate();
	}
	
	public void setPetfotoPerfil(Session session, int idfotomascota) throws Exception {
		String hqlUpdate = "update Petfotomascota foto set foto.mostrar = 1 where foto.idfotomascota = :idfotomascota";
		session.createQuery( hqlUpdate )
		.setInteger( "idfotomascota", idfotomascota )
		.executeUpdate();
	}

	public void deletePetfoto(Session session, int idfotomascota)
			throws Exception {
		String hqlUpdate = "delete Petfotomascota foto where foto.idfotomascota = :idfotomascota";
		session.createQuery( hqlUpdate )
		.setInteger( "idfotomascota", idfotomascota )
		.executeUpdate();
	}

}

