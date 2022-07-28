/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import service.EmailService;
import service.EmailServiceIml;

/**
 *
 * @author Duy Phuong
 */
@WebServlet(name = "AddUserServlet", urlPatterns = {"/admin/addnewuser"})
public class AddUserServlet extends HttpServlet {
    private EmailService emailService = new EmailServiceIml();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        AccountDAO accountDAO = new AccountDAO();
        
        String email = request.getParameter("mail");
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        int gender = Integer.parseInt(request.getParameter("gender"));
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String address = request.getParameter("address");
        int role_id = Integer.parseInt(request.getParameter("role_id"));
        Account a = new Account(username, generatePassword(), fullname, role_id, gender==1, email, city, country, address, phone, true, null, 1);
        System.out.println(a);
        boolean pos = accountDAO.creatUser(a);
        emailService.sendEmailGetNewAccount(getServletContext(), a);
        if(!pos){
            response.sendError(1, "something wrong");
        }
    }
    
    private String generatePassword() {
        String key = "1234567890!@#$%&abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ";
        String result = "";
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            result += key.charAt(r.nextInt(key.length()));
        }
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
