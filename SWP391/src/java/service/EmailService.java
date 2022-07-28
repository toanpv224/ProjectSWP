package service;

import javax.servlet.ServletContext;
import model.Account;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tretr
 */
public interface EmailService {

    void sendEmail(ServletContext context, Account recipient, String type, String text);
    void sendEmailComfirmOrder(ServletContext context, String name, String email, int orderID);
    void sendEmailComfirmUpdateOrder(ServletContext context, String name, String email, int orderID);
    void sendEmailGetNewAccount(ServletContext context, Account recipient);
}
