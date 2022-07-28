/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.SettingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Role;
import model.Setting;
import util.Pagination;

/**
 *
 * @author tretr
 */
public class SettingDetailsServlet extends HttpServlet {

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
            out.println("<title>Servlet SettingDetailsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SettingDetailsServlet at " + request.getContextPath() + "</h1>");
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
        SettingDAO settingDAO = new SettingDAO();
        if (request.getParameter("settingID") == null || request.getParameter("settingID") == "") {
            response.sendRedirect("admin/settingslist");
        } else {
            int settingID = Integer.parseInt(request.getParameter("settingID"));
            Setting settingsList = settingDAO.getSettingsByID(settingID);
            List type = settingDAO.getTypes();
            List value = settingDAO.getValue();
            List<Role> order = settingDAO.getOrder();
            
            request.setAttribute("type", type);
            request.setAttribute("value", value);
            request.setAttribute("order", order);
            request.setAttribute("settingsList", settingsList);
            
            request.getRequestDispatcher("/admin/settingdetails.jsp").forward(request, response);
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
//        processRequest(request, response);
        SettingDAO sdb = new SettingDAO();
        int id = Integer.parseInt(request.getParameter("ID"));
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String value = request.getParameter("value");
        String order = request.getParameter("order");
        String active = request.getParameter("active");
        String description = request.getParameter("description");
        if (active == null) {
            active = "0";
        }
        Setting set = new Setting();
        set.setSettingId(id);
        set.setName(name);
        set.setType(type);
        set.setValue(value);
        set.setOrder(order);
        set.setDescription(description);
        if (active.equalsIgnoreCase("true") || active.equals("1")||active.equalsIgnoreCase("on")) {
            set.setStatus(1);
        } else {
            set.setStatus(0);
        }
        
        sdb.updateSetting(set);
         response.sendRedirect("settingdetails?settingID="+id);
        
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
