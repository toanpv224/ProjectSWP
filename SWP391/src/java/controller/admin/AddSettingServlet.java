/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.SettingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Setting;

/**
 *
 * @author tretr
 */
public class AddSettingServlet extends HttpServlet {

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
            out.println("<title>Servlet AddSettingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddSettingServlet at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
        SettingDAO sdb = new SettingDAO();
        Setting s = new Setting();
        String id = request.getParameter("ID");
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String rName = request.getParameter("rName");
        String value = request.getParameter("value");
        String order = request.getParameter("order");

        s.setDescription(description);
        s.setName(name);
        s.setStatus(1);
        s.setType(type);
        
        switch (type) {
            case "Role":
                s.setValue(rName);
                
                sdb.insertRoleToGetId(rName);
                s.setOrder((String.valueOf(sdb.getRoleId(rName))));
                sdb.insertRoleSetting(s);
                response.sendRedirect("/swp/admin/settingslist");
                
                break;
            case "Permission":

                s.setValue(value);
                s.setOrder(order);
                sdb.insertPermissions(s);
                sdb.insertRoleSetting(s);
                response.sendRedirect("/swp/admin/settingslist");
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

}
