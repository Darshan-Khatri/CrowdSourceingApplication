/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author darsh
 */
@Named(value = "shareDeal")
@SessionScoped
public class ShareDeal implements Serializable {

    private static String Title;
    private static String Email;

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public static void shareThisDeal(Thread threadObject, String senderName) {
        
        String to = Email;

        String from = "sharebestdeals@gmail.com";
        final String username = "sharebestdeals";
        final String password = "1234561!";

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("ATTENTION: Deal Alert!");
            message.setContent("<h2>Hi, Your friend "+senderName+" wants to share this deal with you!</h2>"
                    + "<h4>Title: " + threadObject.getTitle() + "</h4>"
                    + "<p>About: "+threadObject.getContent()+" </p>"        
                    + "<p>Price($): " + threadObject.getPrice() + "</p>"
                    + "<p>Login to your Slickdeals account and get best deals!<p>", "text/html");

            Transport.send(message);

            Email = null;

        } catch (MessagingException e) {
            System.out.println("Error occured while emailing the deal to receipent!!!!!");
        }

    }

}
