/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.authentication;

import dal.AccountDAO;
import dal.DBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

/**
 *
 * @author tretr
 */
public class ResetPasswordServlet extends HttpServlet {

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
            out.println("<title>Servlet ResetPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordServlet at " + request.getContextPath() + "</h1>");
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
        String email = request.getParameter("key1");
        String hash = request.getParameter("key2");

        Connection con = DBContext.getConnection();

        try {
            String sql = "SELECT [email]    \n"
                    + "      ,[hash]\n"
                    + "      ,[active]\n"
                    + "  FROM [accounts]\n"
                    + "  WHERE [email]=? AND [hash]=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, hash);
            ResultSet rs = stm.executeQuery();
            if (!rs.next()) {
                request.setAttribute("title", "Can not reset Password!");
                request.setAttribute("mess", "Link is no longer available!");
                request.getRequestDispatcher("verify.jsp").forward(request, response);
            } else {
                PreparedStatement pst1 = con.prepareStatement("UPDATE [accounts]\n"
                        + "   SET [featured] = 1\n"
                        + "      ,[hash] = '1'\n"
                        + " WHERE [email] = ? AND [hash] = ?");
                pst1.setString(1, email);
                pst1.setString(2, hash);
                int i = pst1.executeUpdate();
                if (i == 1) {
                    request.setAttribute("mail", email);
                    request.getRequestDispatcher("createnewpassword.jsp").forward(request, response);
                } else {
                    response.sendRedirect("home");
                }
            }
        } catch (Exception ex) {
            System.out.println("ActivateAccount File :: " + ex);
        }
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
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String npass = request.getParameter("npass");
        String re_pass = request.getParameter("repass");
        
        String choise = request.getParameter("choise");
        
        AccountDAO dao = new AccountDAO();
        Account a = new Account();
        a = dao.getAccountByEmail(email);
        switch (choise) {
            case "forgot":
                if (!npass.equals(re_pass)) {
                    request.setAttribute("mess", "New Password does not match!");
                    request.setAttribute("title", "Check your New Password !");
                    request.getRequestDispatcher("verify.jsp").forward(request, response);
                } else {
                    dao.updatePassword(email, getMd5(npass));
                    request.setAttribute("mess", "Thank!");
                    request.setAttribute("title", "Password have been changed !");
                    request.getRequestDispatcher("verify.jsp").forward(request, response);
                }
                break;
            case "reset":
                if (a.getPassword().equals(getMd5(pass)) == false) {
                    request.setAttribute("mess", "Password incorect!");
                    request.setAttribute("title", "Check your current Password !");
                    request.getRequestDispatcher("verify.jsp").forward(request, response);
                } else if (!npass.equals(re_pass)) {
                    request.setAttribute("mess", "New Password does not match!");
                    request.setAttribute("title", "Check your New Password !");
                    request.getRequestDispatcher("verify.jsp").forward(request, response);
                } else {
                    dao.updatePassword(email, getMd5(npass));
                    request.setAttribute("mess", "Thank!");
                    request.setAttribute("title", "Password have been changed !");
                    request.getRequestDispatcher("verify.jsp").forward(request, response);
                }
                break;
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
