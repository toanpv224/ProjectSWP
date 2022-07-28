/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dal.OrderDAO;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import model.Account;
import model.Cart;
import model.Item;
import model.Order;
import util.SendingEmailUtil;

/**
 *
 * @author tretr
 */
public class EmailServiceIml implements EmailService {

    private static final String EMAIL_WELCOME_SUBJECT = "Welcome to OnlineShop";
    private static final String EMAIL_REGISTER_ACTIVE = "OnlineShop - Active Account!";
    private static final String EMAIL_FORGOT_PASSWORD = "OnlineShop - New Password";
    private static final String EMAIL_CANCEL_ORDER = "OnlineShop - Cancel Order";
    private static final String EMAIL_UPDATE_ORDER = "OnlineShop - Update Order";
    private static final String EMAIL_FINISHED_ORDER = "OnlineShop - Order finished";

    private static final String EMAIL_CONFIRMATION_ORDER = "OnlineShop - CONFIRMATION ORDER";
    private static final String EMAIL_UPDATE_METHOD_ORDER = "OnlineShop - UPDATE ORDER";
    private static final String EMAIL_GET_ACCOUNT = "OnlineShop - Account";

    @Override
    public void sendEmail(ServletContext context, Account recipient, String type, String text) {
        String host = context.getInitParameter("host");
        String port = context.getInitParameter("port");
        String user = context.getInitParameter("user");
        String pass = context.getInitParameter("pass");

        try {
            String content = null;
            String subject = null;
            switch (type) {
                case "active":
                    subject = EMAIL_REGISTER_ACTIVE;
                    content = "Dear " + recipient.getFull_name() + " ,your account need to be active!\n"
                            + "Click the link below to active your account:\n"
                            + text + "\n"
                            + "Thank you very much!\n";
                    break;
                case "welcome":
                    subject = EMAIL_WELCOME_SUBJECT;
                    content = "Dear " + recipient.getFull_name() + " , hope you have a good time!";
                    break;
                case "forgot":
                    subject = EMAIL_FORGOT_PASSWORD;
                    content = "Dear " + recipient.getFull_name() + " , we send you the reset password link!\n"
                            + "Click the link below to come to reset password:\n"
                            + text + "\n"
                            + "Thank you very much!\n";
                    break;
                case "cancel":
                    subject = EMAIL_CANCEL_ORDER;
                    content = "Dear " + recipient.getFull_name() + ",\n"
                            + text + "\n"
                            + "Thank you very much!\n";
                    break;
                case "update":
                    subject = EMAIL_UPDATE_ORDER;
                    content = "Dear " + recipient.getFull_name() + ",\n"
                            + text + "\n"
                            + "Thank you very much!\n";
                    break;
                default:
                    subject = "OnlineShop";
                    content = "Maybe this email is wrong!";
            }
            SendingEmailUtil.sendEmail(host, port, user, pass, recipient.getEmail(), subject, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmailFinishOrder(ServletContext context, Account recipient, String type, String text) {
        String host = context.getInitParameter("host");
        String port = context.getInitParameter("port");
        String user = context.getInitParameter("user");
        String pass = context.getInitParameter("pass");
        String subject = EMAIL_FINISHED_ORDER;
        String content = "Dear " + recipient.getFull_name() + ",\n"
                + text + "\n"
                + "Thank you very much!\n";
        try {
            SendingEmailUtil.sendEmailFinishOrder(host, port, user, pass, recipient.getEmail(), subject, content);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailServiceIml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //send email confirmation order
    @Override
    public void sendEmailComfirmOrder(ServletContext context, String name, String email, int orderID) {
        try {
            String host = context.getInitParameter("host");
            String port = context.getInitParameter("port");
            String user = context.getInitParameter("user");
            String pass = context.getInitParameter("pass");
            String content = "<h5>Dear!  " + name + "\nThank you for shopping on our website."
                    + "You need complete choose payment method to continue processing order\n"
                    + "Below is your order information and payment guide attacch<h5>";
            String subject = EMAIL_CONFIRMATION_ORDER;
            String form = getOrderInformationForm(orderID);//return html form order information
            SendingEmailUtil.sendEmailConfirmationOrder(host, port, user, pass, email, subject, content, form, context);
        } catch (MessagingException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void sendEmailComfirmUpdateOrder(ServletContext context, String name, String email, int orderID) {
        try {
            String host = context.getInitParameter("host");
            String port = context.getInitParameter("port");
            String user = context.getInitParameter("user");
            String pass = context.getInitParameter("pass");
            String content = "<h5>Dear!" + name + " Congratulations, you have successfully updated your order.\n"
                    + "Below is your order information and payment guide attacch</h5>";
            String subject = EMAIL_UPDATE_METHOD_ORDER;
            String form = getOrderInformationForm(orderID);//return html form order information
            SendingEmailUtil.sendEmailConfirmationOrder(host, port, user, pass, email, subject, content, form, context);
        } catch (MessagingException ex) {
            System.out.println(ex);
        }

    }

    //get order information form 
    public String getOrderInformationForm(int orderID) {
        try {
            //format money
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            OrderDAO orderDAO = new OrderDAO();
            Cart cart = orderDAO.getCartSubmitted(orderID);
            double freight = cart.getFreight();
            Order order = orderDAO.getOrderByOrderID(orderID);
            //Encoding the link:
            String orderID_encode = orderID + "";
            String orderID_encoded = Base64.getEncoder().encodeToString(orderID_encode.getBytes("UTF-8"));
            String gender;
            if (order.isShip_gender()) {
                gender = "male";
            } else {
                gender = "female";
            }
            //add header
            String content = "<p><strong>Name:"
                    + order.getShip_name()
                    + "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</strong><strong><a href=\"mailto:Email:"
                    + order.getShip_email()
                    + "\">Email:"
                    + order.getShip_email()
                    + "</a></strong></p>\n"
                    + "<p><strong>Mobile:"
                    + order.getShip_mobile()
                    + "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</strong><strong>Address: "
                    + order.getShip_address()
                    + "</strong></p>\n"
                    + "<p><strong>Gender:"
                    + gender
                    + "&nbsp;</strong></p>\n"
                    + "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</strong></p>\n"
                    + "<p><br></p>"
                    + "\n"
                    + "<div style=\"border:1px solid rgb(19, 18, 18); height: fit-content;width: fit-content;\">\n"
                    + "\n"
                    + "    <p style=\"margin-left: 48px;\"><strong>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Order Information</strong></p>\n"
                    + "    <table style=\"border-collapse:collapse;border:none;\">\n"
                    + "        <tbody>\n"
                    + "            <tr>\n"
                    + "                <th style=\"width: 35.2pt;border: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;\">\n"
                    + "                    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:  normal;font-size:19px;font-family:\"Times New Roman\",serif;'>ID</p>\n"
                    + "                </th>\n"
                    + "                <th style=\"width: 146pt;border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0cm 5.4pt;vertical-align: top;\">\n"
                    + "                    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:  normal;font-size:19px;font-family:\"Times New Roman\",serif;'>Product</p>\n"
                    + "                </th>\n"
                    + "                <th style=\"width: 90.6pt;border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0cm 5.4pt;vertical-align: top;\">\n"
                    + "                    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:  normal;font-size:19px;font-family:\"Times New Roman\",serif;'>Price</p>\n"
                    + "                </th>\n"
                    + "                <th style=\"width: 61.1pt;border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0cm 5.4pt;vertical-align: top;\">\n"
                    + "                    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:  normal;font-size:19px;font-family:\"Times New Roman\",serif;'>Quantity</p>\n"
                    + "                </th>\n"
                    + "                <th style=\"width: 120.2pt;border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0cm 5.4pt;vertical-align: top;\">\n"
                    + "                    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:  normal;font-size:19px;font-family:\"Times New Roman\",serif;'>&nbsp; &nbsp; Subtotal</p>\n"
                    + "                </th>\n"
                    + "            </tr>";
            // add body
            for (Item i : cart.getItems()) {
                String bodyTable = " <tr>\n"
                        + "                <td style=\"width: 35.2pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0cm 5.4pt;vertical-align: top;\">\n"
                        + "                    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:  normal;font-size:19px;font-family:\"Times New Roman\",serif;'>"
                        + i.getProduct().getProduct_id()
                        + "</p>\n"
                        + "                </td>\n"
                        + "                <td style=\"width: 146pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;\">\n"
                        + "                    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:  normal;font-size:19px;font-family:\"Times New Roman\",serif;'>"
                        + i.getProduct().getName()
                        + "</p>\n"
                        + "                </td>\n"
                        + "                <td style=\"width: 90.6pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;\">\n"
                        + "                    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:  normal;font-size:19px;font-family:\"Times New Roman\",serif;'>"
                        + formatter.format(i.getPrice()) + "VNĐ"
                        + "</p>\n"
                        + "                </td>\n"
                        + "                <td style=\"width: 61.1pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;\">\n"
                        + "                    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:  normal;font-size:19px;font-family:\"Times New Roman\",serif;'>&nbsp; "
                        + i.getQuantity()
                        + "</p>\n"
                        + "                </td>\n"
                        + "                <td style=\"width: 120.2pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0cm 5.4pt;vertical-align: top;\">\n"
                        + "                    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:  normal;font-size:19px;font-family:\"Times New Roman\",serif;'>"
                        + formatter.format(i.getPrice() * i.getQuantity()) + "VNĐ"
                        + "</p>\n"
                        + "                </td>\n"
                        + "            </tr>";
                content += bodyTable;
            }
            //add cart
            String totalContent = "</tbody>\n"
                    + "    </table>\n"
                    + "    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:19px;font-family:\"Times New Roman\",serif;'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p>\n"
                    + "    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:19px;font-family:\"Times New Roman\",serif;'>Sub Total &nbsp;: &nbsp;<span style='font-size:18px;line-height:107%;font-family:\"Arial\",sans-serif;color:red;background:white;'>"
                    + formatter.format(cart.getTotalMoney()) + "VNĐ"
                    + "</span> &nbsp;</p>\n"
                    + "    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:19px;font-family:\"Times New Roman\",serif;'>------------------------------------- &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</p>\n"
                    + "    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:19px;font-family:\"Times New Roman\",serif;'>Shipping fee : &nbsp; &nbsp; <span style='font-size:18px;line-height:107%;font-family:\"Arial\",sans-serif;color:red;background:white;'>"
                    + formatter.format(freight) + "VNĐ"
                    + "</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</p>\n"
                    + "    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:19px;font-family:\"Times New Roman\",serif;'>-------------------------------------</p>\n"
                    + "    <p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:19px;font-family:\"Times New Roman\",serif;'>Total Cost: &nbsp;<span style='font-size:18px;line-height:107%;font-family:\"Arial\",sans-serif;color:red;background:white;'>"
                    + formatter.format(cart.getTotalMoney() - cart.getFreight()) + "VNĐ"
                    + "</span>&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</p>\n"
                    + "    <button style=\"height:42px; width: 90px; margin: 20px 0px 20px 40px;background-color: rgb(245, 187, 160); border: none; border-radius:3px;\">\n"
                    + "    <a style=\"text-decoration:none;\" href=\"http://localhost:8080/swp/cartcompletion?orderid="
                    + orderID_encoded
                    + "\">View Detail</a>\n"
                    + "    </button>"
                    + "</div>";
            content += totalContent;
            return content;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EmailServiceIml.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;//exception
    }

    @Override
    public void sendEmailGetNewAccount(ServletContext context, Account recipient) {
        try {
            String host = context.getInitParameter("host");
            String port = context.getInitParameter("port");
            String user = context.getInitParameter("user");
            String pass = context.getInitParameter("pass");
            String content = "Dear " + recipient.getEmail() + "! you have been created an new account in OnlineShopSystem. "
                    + "Below is your password to login to system, please change your password to protect your account.\n";
            content += "Username: " + recipient.getUsername() + "\n";
            content += "Password: " + recipient.getPassword() + "\n";
            String subject = EMAIL_GET_ACCOUNT;
            SendingEmailUtil.sendEmail(host, port, user, pass, recipient.getEmail(), subject, content);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailServiceIml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
