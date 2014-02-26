/**
 * 
 */
package com.cookedspecially.utility;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring3.SpringTemplateEngine;

/**
 * @author sagarwal
 *
 */
public class MailerUtility {

	final static String username = "support@cookedspecially.com";
	final static String password = "P@$$word";
	final static MailSender mailSender = new JavaMailSenderImpl();
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
	
	public static void sendHTMLMail(String toAddress, String subject, String messageStr) {
		 
		 
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
			message.setContent(messageStr, "text/html");
 
			Transport.send(message);
 
			System.out.println("Email Sent to : " + toAddress);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void sendMailWithInline(JavaMailSenderImpl mailSender,
			SpringTemplateEngine templateEngine,
	        final String recipientName, final String recipientEmail, final Locale locale)
	        throws MessagingException {
	  
	    // Prepare the evaluation context
	    final Context ctx = new Context(locale);
	    ctx.setVariable("name", recipientName);
	    ctx.setVariable("subscriptionDate", new Date());
	    ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
	    //ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML
	 
	    // Prepare message using a Spring helper
	    final MimeMessage mimeMessage = mailSender.createMimeMessage();
	    final MimeMessageHelper message =
	        new MimeMessageHelper(mimeMessage, false, "UTF-8"); // true = multipart

	    message.setSubject("Example HTML email with inline image");
	    message.setFrom("thymeleaf@example.com");
	    message.setTo(recipientEmail);
	 
	    // Create the HTML body using Thymeleaf
	    final String htmlContent = templateEngine.process("email-inlineimage.html", ctx);
	    message.setText(htmlContent, true); // true = isHtml
	 
	    // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
	    //final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
	    //message.addInline(imageResourceName, imageSource, imageContentType);
	 
	    // Send mail
	    mailSender.send(mimeMessage);
	 
	}
}
