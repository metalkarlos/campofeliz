package com.web.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.web.pet.global.Parametro;

public class MailUtil {

	private String usuario;
	private String clave;
	
	public MailUtil() {
	}
	
	/***
	 * Env�a correo
	 * @param destinatario Si es null se env�a �nicamente al administrador
	 * @param asunto
	 * @param contenido
	 * @throws Exception
	 */
	public void enviarMail(String destinatario, String asunto, String contenido) throws Exception {
		Properties properties = new FileUtil()
				.getPropertiesFile(Parametro.PROPERTIES_MAIL);
		usuario = properties.getProperty("mail.user");
		clave = properties.getProperty("mail.password");

		destinatario = destinatario == null ? usuario : destinatario;
		
		InternetAddress internetAddressDestinatario = new InternetAddress(destinatario);
		internetAddressDestinatario.validate();
		
		InternetAddress internetAddressUsuario = new InternetAddress(usuario);
		internetAddressUsuario.validate();

		//Session mailSession = Session.getDefaultInstance(properties, null);
		Session mailSession = Session.getDefaultInstance(properties, 
			    new javax.mail.Authenticator(){
			        protected PasswordAuthentication getPasswordAuthentication() {
			            return new PasswordAuthentication(
			            		usuario, clave);
			        }
			});

		mailSession.setDebug(true);
		
		MimeMessage message = new MimeMessage(mailSession);
		message.setFrom(internetAddressUsuario);
		message.addRecipient(Message.RecipientType.TO, internetAddressDestinatario);
		message.setSubject(asunto, "utf-8");
		message.setContent(contenido, "text/html; charset=utf-8");
		
		Transport.send(message);
		//Transport tr = mailSession.getTransport("smtp");
		//tr.connect(host, to, clave);
		//message.saveChanges();      // don't forget this
		//tr.sendMessage(message, message.getAllRecipients());
		//tr.close();
		
		mailSession.getDebugOut();
	}
	
}