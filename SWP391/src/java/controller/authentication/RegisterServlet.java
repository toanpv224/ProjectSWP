/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.authentication;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
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
public class RegisterServlet extends HttpServlet {

    private EmailService emailService = new EmailServiceIml();

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
            throws ServletException, IOException {
        //Get data from request
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String fullname = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String mail = request.getParameter("mail");
        String phone = request.getParameter("phone");
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String re_pass = request.getParameter("repass");
        //Create Hash code which helps in Activation link
        String myHash;
        Random random = new Random();
        random.nextInt(9999999);
        myHash = getMd5("" + random);
        
        //Add to bean
        AccountDAO dao = new AccountDAO();
        Account a = new Account();
        a.setFull_name(fullname);
        if (gender.endsWith("1")) {
            a.setGender(true);
        } else {
            a.setGender(false);
        }
        a.setEmail(mail);
        a.setPhone(phone);
        a.setAddress(address);
        a.setUsername(user);
        a.setMyHash(myHash);
        request.setAttribute("acc", a);

        if (!pass.equals(re_pass)) {
            request.setAttribute("signmess", "Password does not match!");
            request.getRequestDispatcher("home").forward(request, response);
        } else if (dao.checkAccountExist(user) != null) {
            request.setAttribute("signmess", "Username already used !");
            request.getRequestDispatcher("home").forward(request, response);
        } else if (dao.checkEmailExist(mail) != null) {
            request.setAttribute("signmess", "Email already used !");
            request.getRequestDispatcher("home").forward(request, response);
        } else if (pass.length() < 8) {
            request.setAttribute("signmess", "Password must contain 8 characters !");
            request.getRequestDispatcher("home").forward(request, response);
        } else {
            a.setPassword(getMd5(pass));
            //dc signup
            emailService.sendEmail(getServletContext(), a, "active", "http://localhost:8080/swp/activate?key1=" + a.getEmail() + "&key2=" + a.getMyHash());

            String str = dao.singup(a, 2);

            if (str.equals("Success")) {
                request.setAttribute("mess", "You need active your account!\n Check your mail!");
                request.getRequestDispatcher("verify.jsp").forward(request, response);

            } else {
                request.setAttribute("signmess", "Register error !");
                request.getRequestDispatcher("home").forward(request, response);
            }
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

    public static void main(String[] args) {
        System.out.println(getMd5("15102001"));
    }
}
