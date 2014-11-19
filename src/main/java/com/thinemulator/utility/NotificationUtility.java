package com.thinemulator.utility;

import java.net.UnknownHostException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class NotificationUtility {

	public static void sendEmail(String hash,  String url){
		// Host server to send email
   	    String SMTP_HOST = "smtp.gmail.com";       
     	// Sender's email address
        String FROM_ADDRESS = "ram.0737@gmail.com"; 
        // Name of the sender 
        String FROM_NAME = "Ramnivas";  
        // Receiver's email address
        String TO_ADDRESS = "ram.0737@gmail.com"; 
        
         
       
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

        return new PasswordAuthentication("", "");  

    }  
}  
