/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.order;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Order;

/**
 *
 * @author Admin
 */
public class MyOrderServlet extends HttpServlet {

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
            out.println("<title>Servlet MyOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyOrderServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        OrderDAO orderDAO = new OrderDAO();
        int total = orderDAO.getTotalOrderByUserid(account.getUser_id());

        //pagination
        String pageNumberRaw = request.getParameter("page");
        int pageNumber, numberPage;
        final int NUMBER_ITEMS_PER_PAGE = 5;

        try {
            if (pageNumberRaw == null) {
                pageNumber = 1;
            } else {
                pageNumber = Integer.parseInt(pageNumberRaw);

            }

            if (total % NUMBER_ITEMS_PER_PAGE == 0) {
                numberPage = total / NUMBER_ITEMS_PER_PAGE;
            } else {
                numberPage = total / NUMBER_ITEMS_PER_PAGE + 1;
            }

            int start, end;
            start = (pageNumber - 1) * NUMBER_ITEMS_PER_PAGE;
            end = Math.min(pageNumber * NUMBER_ITEMS_PER_PAGE, total);
            List<Order> myOrder = orderDAO.getOrderByUserId(account.getUser_id(), start + 1, end);

            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("myorders", myOrder);
            request.getRequestDispatcher("myorders.jsp").forward(request, response);
        } catch (IOException e) {

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
                HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        OrderDAO orderDAO = new OrderDAO();
        int total = orderDAO.getTotalOrderByUserid(account.getUser_id());

        //pagination
        String pageNumberRaw = request.getParameter("page");
        int pageNumber, numberPage;
        final int NUMBER_ITEMS_PER_PAGE = 8;

        try {
            if (pageNumberRaw == null) {
                pageNumber = 1;
            } else {
                pageNumber = Integer.parseInt(pageNumberRaw);

            }

            if (total % NUMBER_ITEMS_PER_PAGE == 0) {
                numberPage = total / NUMBER_ITEMS_PER_PAGE;
            } else {
                numberPage = total / NUMBER_ITEMS_PER_PAGE + 1;
            }

            int start, end;
            start = (pageNumber - 1) * NUMBER_ITEMS_PER_PAGE;
            end = Math.min(pageNumber * NUMBER_ITEMS_PER_PAGE, total);
            List<Order> myOrder = orderDAO.getOrderByUserId(account.getUser_id(), start + 1, end);
            
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("myorders", myOrder);
            request.getRequestDispatcher("myorders.jsp").forward(request, response);
        } catch (IOException e) {

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
