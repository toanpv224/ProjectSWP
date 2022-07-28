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
 * @author Admin
 */
public class SettingsList extends HttpServlet {

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
            out.println("<title>Servlet SettingsList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SettingsList at " + request.getContextPath() + "</h1>");
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
        int page = 1;
        SettingDAO settingDAO = new SettingDAO();
        int total = settingDAO.getTotalSetting();

        int start = Pagination.getStart(page);
        int end = Pagination.getEnd(page, total);
        int numberPage = Pagination.getNumberPage(total);

        List<Setting> settingsList = settingDAO.getSettingsByPage(start, end, "");
        List type = settingDAO.getTypes();

        request.setAttribute("type", type);
        request.setAttribute("settingsList", settingsList);
        request.setAttribute("pageNumber", page);
        request.setAttribute("numberPage", numberPage);

        request.setAttribute("orderOption", "setting_id");
        request.setAttribute("sequence", "asc");

        List value = settingDAO.getValue();
        List<Role> order = settingDAO.getOrder();

        request.setAttribute("value", value);
        request.setAttribute("order", order);

        request.getRequestDispatcher("/admin/settingslist.jsp").forward(request, response);
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
        SettingDAO settingDAO = new SettingDAO();

        String[] type = request.getParameterValues("type");
        String[] status = request.getParameterValues("status");
        String key = request.getParameter("key");
        String page_raw = request.getParameter("page");
        String orderOption = request.getParameter("orderOption");
        String sequence = request.getParameter("sequence");
        int page;
        try {
            page = Integer.parseInt(page_raw);

            List Alltype = settingDAO.getTypes();
            boolean[] checkedType = new boolean[Alltype.size()];
            checkType(Alltype, checkedType, type);

            int total = settingDAO.getTotalSettings(type, status, key);
            int numberPage = Pagination.getNumberPage(total);
            if (page > numberPage) {
                page = 1;
            }
            int start = Pagination.getStart(page);
            int end = Pagination.getEnd(page, total);
            List<Setting> settingsList = settingDAO.getSettingsByPage(start + 1, end, orderOption + " " + sequence, type, status, key);
            request.setAttribute("type", Alltype);
            request.setAttribute("settingsList", settingsList);
            request.setAttribute("pageNumber", page);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("orderOption", orderOption);
            request.setAttribute("sequence", sequence);
            request.setAttribute("key", key);
            request.setAttribute("checkedType", checkedType);

            boolean activate = false, deactivate = false;
            if (status != null) {
                for (String statu : status) {
                    if (statu.equals("1")) {
                        activate = true;
                    }
                    if (statu.equals("0")) {
                        deactivate = true;
                    }
                }
            }

            List value = settingDAO.getValue();
            List<Role> order = settingDAO.getOrder();

            request.setAttribute("value", value);
            request.setAttribute("order", order);
            request.setAttribute("activate", activate);
            request.setAttribute("deactivate", deactivate);
            request.getRequestDispatcher("/admin/settingslist.jsp").forward(request, response);
        } catch (NumberFormatException e) {

        }
    }

    private boolean checked(String[] arr, String a) {
        if (arr == null) {
            return false;
        } else {
            for (String saleId1 : arr) {
                if (saleId1.equalsIgnoreCase("" + a)) {
                    return true;
                }
            }
            return false;

        }
    }

    private void checkType(List Alltype, boolean[] checkedType, String[] type) {
        for (int i = 0; i < Alltype.size(); i++) {
            if (checked(type, (String) Alltype.get(i))) {
                checkedType[i] = true;
            }
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
