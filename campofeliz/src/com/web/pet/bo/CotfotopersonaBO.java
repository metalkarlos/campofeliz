package com.web.pet.bo;

    import java.util.Calendar;
	import java.util.Date;
	import java.util.List;

	import org.hibernate.Session;

	import com.web.pet.bean.UsuarioBean;
	import com.web.pet.dao.CotfotopersonaDAO;
	import com.web.pet.pojo.annotations.Cotfotopersona;
	import com.web.util.FacesUtil;
	import com.web.util.HibernateUtil;
	import com.web.util.FileUtil;
	
	public class CotfotopersonaBO {
	
		public List<Cotfotopersona> lisPetfotopersonaByIdpersona(int idpersona) throws Exception {
			List<Cotfotopersona> lisCotfotopersona = null;
			Session session = null;
			
			try{
				session = HibernateUtil.getSessionFactory().openSession();
				
				CotfotopersonaDAO cotfotopersonaDAO = new CotfotopersonaDAO();
				
				lisCotfotopersona = cotfotopersonaDAO.lisCotfotopersonaByIdpersona(session, idpersona);
			}catch(Exception re){
				throw new Exception();
			}finally{
				session.close();
			}
			
			return lisCotfotopersona;
		}
		
		public boolean newCotfotopersona(Cotfotopersona cotfotopersona) throws Exception {
			boolean ok = false;
			Session session = null;
			
			try{
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				
				CotfotopersonaDAO cotfotopersonaDAO = new CotfotopersonaDAO();
				
				Date fecharegistro = new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				
				int maxid = cotfotopersonaDAO.maxIdCotfotopersona(session)+1;
				cotfotopersona.setIdfotopersona(maxid);
				int secuencia = cotfotopersonaDAO.getCantFotosPorPersona(session, cotfotopersona.getCotpersona().getIdpersona());
				
				//foto en disco
				FileUtil fileUtil = new FileUtil();
				FacesUtil facesUtil = new FacesUtil();
				Calendar fecha = Calendar.getInstance();
				
				String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
				String rutaMascota =  fileUtil.getPropertyValue("repositorio-personas") + "/" + fecha.get(Calendar.YEAR);
				String nombrearchivo = fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + "-" + cotfotopersona.getCotpersona().getIdpersona()+"-"+secuencia+"."+fileUtil.getFileExtention(cotfotopersona.getNombrearchivo()).toLowerCase();
				
				String rutaCompleta = rutaImagenes + "/" + rutaMascota;
				
				//asignar ruta y nombre de archivo en objeto
				cotfotopersona.setNombrearchivo(nombrearchivo);
				cotfotopersona.setRuta("/" + rutaMascota + "/" + nombrearchivo);
				
				//Auditoria
				cotfotopersona.setFecharegistro(fecharegistro);
				cotfotopersona.setIplog(usuarioBean.getIp());
				cotfotopersona.getSetestado().setIdestado(1);
				cotfotopersona.setSetusuario(usuarioBean.getSetUsuario());
		
				if(fileUtil.createDir(rutaCompleta)){
					//crear foto en disco
					String rutaArchivo = rutaCompleta + "/" + nombrearchivo;
					fileUtil.createFile(rutaArchivo,cotfotopersona.getObjeto());
				}
				
				cotfotopersona.setObjeto(null);
				cotfotopersonaDAO.saveCotfotopersona(session, cotfotopersona);
				
				session.getTransaction().commit();
				ok = true;
			}catch(Exception re){
				session.getTransaction().rollback();
				throw new Exception(); 
			}finally{
				session.close();
			}
			
			return ok;
		}
		
		public boolean updateCotfotopersona(Cotfotopersona cotfotopersona) throws Exception {
			boolean ok = false;
			Session session = null;
			
			try{
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				
				CotfotopersonaDAO cotfotopersonaDAO = new CotfotopersonaDAO();
				
				Date fecharegistro = new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				
				cotfotopersona.setFecharegistro(fecharegistro);
				cotfotopersona.setIplog(usuarioBean.getIp());
				cotfotopersona.setSetusuario(usuarioBean.getSetUsuario());
				cotfotopersonaDAO.updateCotfotopersona(session, cotfotopersona);
				
				session.getTransaction().commit();
				ok = true;
			}catch(Exception he){
				session.getTransaction().rollback();
				throw new Exception();
			}finally{
				session.close();
			}
			
			return ok;
		}
		
		public Cotfotopersona getCotfotopersonaPerfilByIdpersona(int idpersona) throws Exception{
			Cotfotopersona cotfotopersona = null;
			Session session = null;
			
			try {
				session = HibernateUtil.getSessionFactory().openSession();
				
				CotfotopersonaDAO cotfotopersonaDAO = new CotfotopersonaDAO();
				
				cotfotopersona = cotfotopersonaDAO.getCotfotopersonaPerfilByIdpersona(session, idpersona);
			} catch(Exception he){
				throw new Exception(); 
			}finally{
				session.close();
			}
			
			return cotfotopersona;
		}
		
		public boolean resetCotfotopersonaPerfilByIdpersona(int idpersona) throws Exception {
			boolean ok = false;
			Session session = null;
			
			try {
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				
				CotfotopersonaDAO cotfotopersonaDAO = new CotfotopersonaDAO();
				
				cotfotopersonaDAO.resetCotfotopersonaPerfilByIdpersona(session, idpersona);
				
				session.getTransaction().commit();
				ok = true;
			} catch(Exception he){
				session.getTransaction().rollback();
				throw new RuntimeException(); 
			}finally{
				session.close();
			}
			
			return ok;
		}
		
		public boolean ponerFotoPerfil(Cotfotopersona cotfotopersona) throws Exception {
			boolean ok = false;
			Session session = null;
			
			try {
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				
				CotfotopersonaDAO cotfotopersonaDAO = new CotfotopersonaDAO();
				
				//Primero se quita la imágen que está actualmente como perfil
				cotfotopersonaDAO.resetCotfotopersonaPerfilByIdpersona(session, cotfotopersona.getCotpersona().getIdpersona());
				
				//Luego se pone la imágen seleccionada como foto del perfil
				cotfotopersonaDAO.setCotfotopersonaPerfil(session, cotfotopersona.getIdfotopersona());
				
				session.getTransaction().commit();
				ok = true;
			} catch(Exception he){
				session.getTransaction().rollback();
				throw new Exception(); 
			}finally{
				session.close();
			}
			
			return ok;
		}
		
		public boolean eliminarFotoAlbum(int idfotopersona) throws Exception {
			boolean ok = false;
			Session session = null;
			
			try {
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				
				CotfotopersonaDAO cotfotopersonaDAO = new CotfotopersonaDAO();
				
				cotfotopersonaDAO.deletePetfoto(session, idfotopersona);
				
				session.getTransaction().commit();
				ok = true;
			} catch(Exception he){
				session.getTransaction().rollback();
				throw new Exception(); 
			}finally{
				session.close();
			}
			
			return ok;
		}
		
	}
