/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.authentication;

import dal.AccountDAO;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import service.EmailService;
import service.EmailServiceIml;

/**
 *
 * @author tretr
 */
public class ForgotPasswordServlet extends HttpServlet {

    private EmailService emailService = new EmailServiceIml();
    Account a = new Account();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("mail");
        String myHash = "";
        Random random = new Random();
        AccountDAO db = new AccountDAO();
        a = db.getAccountByEmail(email);
        if (a != null) {
            Account acc = new Account();

            db.updateFeatured(a, "false");

            long start = System.currentTimeMillis();
            long end = start + 60 * 1000;

            emailService.sendEmail(getServletContext(), a, "forgot", "http://localhost:8080/swp/resetpass?key1=" + a.getEmail() + "&key2=" + a.getMyHash());
            request.setAttribute("mess", "Check your mail!");
            request.setAttribute("title", "Reset Password!");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
            while (a.isFeature() == false) {
                if (System.currentTimeMillis() == end) {

                    acc = db.getAccountByEmail(email);
                    random.nextInt(9999999);
                    myHash = getMd5("" + random);
                    acc.setMyHash(myHash);
                    db.updateHashCode(acc);               
                    start = System.currentTimeMillis();
                    end = start + 60 * 1000;
                }
                a = db.getAccountByEmail(email);
            }

        } else {
            request.setAttribute("mess", "Unregistered email!");
            request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
        }
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
        response.sendRedirect("forgotpassword.jsp");
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
