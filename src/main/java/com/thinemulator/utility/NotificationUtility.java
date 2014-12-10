package com.thinemulator.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.thinemulator.beans.UserBean;


public class NotificationUtility {

	public static void sendEmail(String hash,  String url, UserBean user){
		
		Properties properties = Config.readProperties();
		// Host server to send email

		String SMTP_HOST = properties.getProperty("SMTP_HOST");

     	// Sender's email address
        String FROM_ADDRESS = properties.getProperty("FROM_ADDRESS"); 

        // Name of the sender 
        String FROM_NAME = properties.getProperty("FROM_NAME");  

        // Receiver's email address
        String TO_ADDRESS = user.email;
        
        String SMTP_PORT = properties.getProperty("SMTP_PORT");
        //Message to be sent to user
        String WELCOME_MESSAGE = properties.getProperty("WELCOME_MESSAGE");
       
        try {  
            Properties props = new Properties();  
            props.put("mail.smtp.host", SMTP_HOST);  
            props.put("mail.smtp.auth", "true");  
            props.put("mail.debug", "false");  
            props.put("mail.smtp.ssl.enable", "false");  
            props.put("mail.smtp.port",SMTP_PORT);
            
            Session session = Session.getInstance(props, new SocialAuth());  
            MimeMessage msg = new MimeMessage(session);  
  
            InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);  
            msg.setFrom(from);  
  
            InternetAddress toAddresses = new InternetAddress(TO_ADDRESS);  
             
            msg.setRecipient(Message.RecipientType.TO, toAddresses); 
            
            msg.setSubject("Welcome to ThinEmulator");  
            String URL = url + hash;
            System.out.println("URL : "+URL);
            //msg.setContent(URL, "text/plain");  
            msg.setText(WELCOME_MESSAGE, "UTF-8", "html");
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
