/**
 * 
 */
package com.cookedspecially.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author sagarwal
 *
 */
public class MailerUtility {

	final static String username = "support@cookedspecially.com";
	final static String password = "P@$$word";
	
	final static Properties props = new Properties();
	
	static {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}
	
	public static void sendMail(String toAddress, String subject, String messageStr) {
		 
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(toAddress));
			message.setSubject(subject);
			message.setText(messageStr);
 
			Transport.send(message);
 
			System.out.println("Email Sent to : " + toAddress);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
