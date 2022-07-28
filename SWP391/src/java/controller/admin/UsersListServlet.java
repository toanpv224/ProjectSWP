/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.AccountDAO;
import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.ProductCategory;
import model.Role;

/**
 *
 * @author tretr
 */
public class UsersListServlet extends HttpServlet {

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
            out.println("<title>Servlet UsersListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UsersListServlet at " + request.getContextPath() + "</h1>");
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
        
        AccountDAO adb = new AccountDAO();
        
        
        String raw_rid = request.getParameter("rid");
        String raw_gender = request.getParameter("gender");
        String raw_active = request.getParameter("active");
        String raw_search = request.getParameter("search");
        
        
        if (raw_rid == null || raw_rid.trim().length() == 0) {
            raw_rid = "0";
        }
        if (raw_gender == null || raw_gender.trim().length() == 0) {
            raw_gender = "-1";
        }
        if (raw_active == null || raw_active.trim().length() == 0) {
            raw_active = "-1";
        }
        if (raw_search == null || raw_search.trim().length() == 0) {
            raw_search = "";
        }
        
        int rid = Integer.parseInt(raw_rid);
        int gender = Integer.parseInt(raw_gender);
        int active = Integer.parseInt(raw_active);
        
        ArrayList<Account> accounts = adb.searchByRid(rid, gender, active, raw_search);
        List<Role> listRoles = adb.getListRole();

        int page, numperpage = 8;
        int size = accounts.size();
        int num = (size%8==0?(size/8):((size/8))+1);
        String xpage = request.getParameter("page");
        
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        int start, end;
        start = (page - 1) * numperpage;
        end = Math.min(page * numperpage, size);
        
        List<Account> list = adb.getListByPage(accounts, start, end);

        request.setAttribute("numberPage", num);
        request.setAttribute("pageNumber", page);
        
        request.setAttribute("rid", rid);
        request.setAttribute("gender", gender);
        request.setAttribute("active", active);
        request.setAttribute("rawsearch", raw_search);
        
        request.setAttribute("accounts", list);
        request.setAttribute("listRoles", listRoles);
        request.getRequestDispatcher("/admin/userslist.jsp").forward(request, response);
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
