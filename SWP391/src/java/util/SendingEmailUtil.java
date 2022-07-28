/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

/**
 *
 * @author tretr
 */
public class SendingEmailUtil {

    public static void sendEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.socket Factory.port", "587");
        properties.put("mail.smtp.socket Factory.class", "javax.net.ssl.SSLSocket Factory");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);

        // sends the e-mail
        Transport.send(msg);
    }

    public static void sendEmailFinishOrder(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.socket Factory.port", "587");
        properties.put("mail.smtp.socket Factory.class", "javax.net.ssl.SSLSocket Factory");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userName));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
        msg.setSubject("Order confirmation ");

        msg.setText(message, "utf-8", "html");

        /* Transport class is used to deliver the message to the recipients */
        Transport.send(msg);
    }

    public static void sendEmailConfirmationOrder(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message, String contentForm, ServletContext context) throws AddressException,
            MessagingException {

        try {
            // sets SMTP server properties
            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.socket Factory.port", "587");
            properties.put("mail.smtp.socket Factory.class", "javax.net.ssl.SSLSocket Factory");

            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            };

            Session session = Session.getInstance(properties, auth);

            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(userName));
            InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            //send a mesage
            MimeBodyPart messageContentPart = new MimeBodyPart();
            messageContentPart.setContent(message, "text/html; charset=UTF-8");
            // attactment guide 
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart attachment = new MimeBodyPart();
            String filePath = context.getRealPath("/").replace("\\build", "");
            filePath += "file\\paymentguide\\paymentguide.pdf";
            attachment.attachFile(new File(filePath));
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(contentForm, "text/html;charset=UTF-8");
            multipart.addBodyPart(messageContentPart);
            multipart.addBodyPart(attachment);
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
            // sends the e-mail
            Transport.send(msg);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
}
