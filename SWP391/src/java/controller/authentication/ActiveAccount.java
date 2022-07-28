/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.authentication;

import dal.DBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tretr
 */
public class ActiveAccount extends HttpServlet {

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
            out.println("<title>Servlet ActiveAccount</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ActiveAccount at " + request.getContextPath() + "</h1>");
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
        String email = request.getParameter("key1");
        String hash = request.getParameter("key2");

        Connection con = DBContext.getConnection();

        try {
            String sql = "SELECT [email]    \n"
                    + "      ,[hash]\n"
                    + "      ,[active]\n"
                    + "  FROM [accounts]\n"
                    + "  WHERE [email]=? AND [hash]=? AND [active]='0'";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, hash);
            ResultSet rs = stm.executeQuery();
            if (!rs.next()) {
                request.setAttribute("mess", "Link is no longer available!");
                request.getRequestDispatcher("verify.jsp").forward(request, response);
            } else {
                PreparedStatement pst1 = con.prepareStatement("UPDATE [accounts]\n"
                        + "   SET [active] = '1'\n"
                        + " WHERE [email] = ? AND [hash] = ?");
                pst1.setString(1, email);
                pst1.setString(2, hash);
                int i = pst1.executeUpdate();
                if (i == 1) {
                    request.setAttribute("mess", "Your account has been activated!");
                    request.getRequestDispatcher("verify.jsp").forward(request, response);
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
