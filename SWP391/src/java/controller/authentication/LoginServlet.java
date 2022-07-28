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
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author tretr
 */
public class LoginServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
//        processRequest(request, response);

        request.getRequestDispatcher("login.jsp").forward(request, response);
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
//        processRequest(request, response);
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String remember = request.getParameter("remember");
        AccountDAO db = new AccountDAO();
        Account account = db.getAccountByUsernamePassword(username, getMd5(password));
        if (account != null) {
            if (account.isFeature()) {
                if (account.getActive() != 1) {
                    response.sendRedirect("home?mess=Your+or+account+needs+to+be+activated(check+account's+mail)#divOne");
                } else {
                    //luu account len tren cookie
                    Cookie u = new Cookie("userC", username);
                    Cookie p = new Cookie("passC", password);
                    Cookie r = new Cookie("rememC", remember);

                    if (remember == null) {
                        u.setMaxAge(0);
                        p.setMaxAge(0);
                        r.setMaxAge(0);
                    } else {
                        u.setMaxAge(3600 * 24 * 30);
                        p.setMaxAge(3600 * 24 * 30);
                        r.setMaxAge(3600 * 24 * 30);
                    }
                    response.addCookie(u);//luu u va p len tren chrome
                    response.addCookie(p);
                    response.addCookie(r);
                    HttpSession session = request.getSession();
                    session.setAttribute("account", account);
                    if (account.getRole_id() == 2) {
                        response.sendRedirect("marketing/dashboard");
                    } else if (account.getRole_id() == 3) {
                        response.sendRedirect("sale");
                    } else if (account.getRole_id() == 4) {
                        response.sendRedirect("sale");
                    } else if (account.getRole_id() == 5) {
                        response.sendRedirect("admin");
                    } else {
                        response.sendRedirect("home");
                    }
                }
            } else {
                response.sendRedirect("home?mess=Account+is+blocked#divOne");
            }
        } else {
            response.sendRedirect("home?mess=Username+or+password+is+incorrect#divOne");
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
