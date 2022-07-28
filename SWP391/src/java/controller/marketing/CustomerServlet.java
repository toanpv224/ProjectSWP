/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.HistoryProfile;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CustomerServlet", urlPatterns = {"/marketing/customer"})
public class CustomerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Access denied');");
            out.println("window.location.href = \"http://localhost:8080/swp/home#divOne\";");
            out.println("</script>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        if (acc == null) { //have  not login
            processRequest(request, response);
        }
        int user_id = Integer.parseInt(request.getParameter("id"));
        AccountDAO ac = new AccountDAO();
        Account account = ac.getAccountByID(user_id);
        int numper_page = 6;
        int currentPage = 1;
        List<HistoryProfile> histories = ac.getHistory_profiles(user_id, currentPage, numper_page);//default
        int maxPage = ac.countPagingHistories(user_id, currentPage, numper_page);
        request.setAttribute("curpage", currentPage);
        request.setAttribute("maxpage", maxPage);
        request.setAttribute("cus", account);
        request.setAttribute("histories", histories);
        request.getRequestDispatcher("/marketing/customer.jsp").forward(request, response);
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
