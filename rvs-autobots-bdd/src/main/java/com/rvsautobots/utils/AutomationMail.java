package com.rvsautobots.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.rvsautobots.datahandler.PropertyDataHandler;
import com.rvsautobots.exceptions.AutomationException;


public class AutomationMail {

	/**
	 * @Author AutobotsBDD Cucumber Team
	 * @Description :BDD
	 */

	/**
	 * Method for sending the report via email.
	 * @throws AutomationException
	 */
	
	
	public static void sendMailReport() throws AddressException, MessagingException, IOException, AutomationException {
		String subject = "", message = "", maillist = "", EMAIL_REPORT_FOLDER;
		
		subject = PropertyDataHandler.fetchPropertyValue("emailConfig", "subject");
		message = PropertyDataHandler.fetchPropertyValue("emailConfig", "message");
		maillist = PropertyDataHandler.fetchPropertyValue("emailConfig", "maillist");
		EMAIL_REPORT_FOLDER = PropertyDataHandler.fetchPropertyValue("emailConfig", "EMAIL_REPORT_FOLDER");
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		
		final String userName;
		final String password;
		userName = "rvsautobotsbddteam@gmail.com";
		password = "rvsbdd@1234";

		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);
		DateFormat dff = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm:ss z");
		
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(userName));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(maillist));
		msg.setSubject(subject + " – " + dff.format(new Date()).toString());
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		
		// adds only the latest Report file in attachments
		MimeBodyPart attachPart = new MimeBodyPart();
		attachPart.attachFile(lastFileModified(System.getProperty("user.dir") + EMAIL_REPORT_FOLDER));
		multipart.addBodyPart(attachPart);
		
		// sets the multi-part as e-mail’s content
		msg.setContent(multipart);
		
		// sends the e-mail
		Transport.send(msg);
	}
	/**
	 * Method for set the file modified details
	 * @param dir
	 */
	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
	}
}


