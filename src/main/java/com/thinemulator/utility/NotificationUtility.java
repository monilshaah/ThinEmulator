package com.thinemulator.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class NotificationUtility {

	public static void sendEmail(String hash,  String url){
		
		Properties properties = Config.readProperties();
		// Host server to send email

		String SMTP_HOST = properties.getProperty("SMTP_HOST");

     	// Sender's email address
        String FROM_ADDRESS = properties.getProperty("FROM_ADDRESS"); 

        // Name of the sender 
        String FROM_NAME = properties.getProperty("FROM_NAME");  

        // Receiver's email address
        String TO_ADDRESS = properties.getProperty("TO_ADDRESS");

       
        try {  
            Properties props = new Properties();  
            props.put("mail.smtp.host", SMTP_HOST);  
            props.put("mail.smtp.auth", "true");  
            props.put("mail.debug", "false");  
            props.put("mail.smtp.ssl.enable", "true");  
  
            Session session = Session.getInstance(props, new SocialAuth());  
            Message msg = new MimeMessage(session);  
  
            InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);  
            msg.setFrom(from);  
  
            InternetAddress toAddresses = new InternetAddress(TO_ADDRESS);  
             
            msg.setRecipient(Message.RecipientType.TO, toAddresses); 
            
            msg.setSubject("Create password for your login");  
            String URL = url + hash;
            System.out.println("URL : "+URL);
            msg.setContent(URL, "text/plain");  
            Transport.send(msg);  
             
        } catch (Exception ex) { 
       	 System.out.print(ex);
        }
    }

}
class SocialAuth extends Authenticator {  
	  
    @Override  
    protected PasswordAuthentication getPasswordAuthentication() {  

        return new PasswordAuthentication(
        		Config.readProperties().getProperty("FROM_ADDRESS"), 
        		Config.readProperties().getProperty("FROM_ADDRESS_PWD")
        	   );  
    }  
}  
