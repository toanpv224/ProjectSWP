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
import model.Customer;

/**
 *
 * @author win
 */
@WebServlet(name = "CustomersListServlet", urlPatterns = {"/marketing/customerslist"})
public class CustomerListServlet extends HttpServlet {

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
        Account account = (Account) session.getAttribute("account");
        if (account == null) { //have  not login
            processRequest(request, response);
        }
        int currentPage = 1;//start page=1
        int numPerPage = 6;
        int op=1;//descending
        AccountDAO accountDAO = new AccountDAO();
        List<Customer> customers = accountDAO.getCustomers("", 1, -1, 1,op, currentPage, numPerPage); //dafault
        int maxPage = accountDAO.countCustomerPaging("", 1, -1, numPerPage);
        request.setAttribute("curpage", currentPage);
        request.setAttribute("maxpage", maxPage);
        request.setAttribute("cus", customers);
        request.getRequestDispatcher("/marketing/customerlist.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
