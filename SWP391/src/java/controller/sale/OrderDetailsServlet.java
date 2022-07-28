/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.sale;

import dal.AccountDAO;
import dal.OrderDAO;
import dal.SaleDAO;
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
import model.OrderDetail;
import model.OrderStatus;
import model.Sale;
import service.EmailServiceIml;

/**
 *
 * @author Admin
 */
public class OrderDetailsServlet extends HttpServlet {

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
            out.println("<title>Servlet OrderDetailsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderDetailsServlet at " + request.getContextPath() + "</h1>");
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
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        AccountDAO accountDAO = new AccountDAO();
        SaleDAO saleDAO = new SaleDAO();
        Account sale = (Account) session.getAttribute("account");

        String orderId_raw = request.getParameter("orderId");
        int orderId;
        try {
            orderId = Integer.parseInt(orderId_raw);
            OrderDAO orderDAO = new OrderDAO();
            Order myOrder = orderDAO.getOrderByOrderID(orderId);

            Account customer = accountDAO.getAccountByID(myOrder.getUser_id());
            myOrder.setAccount(customer);

            Account saleMember = saleDAO.getSaleByOrderId(orderId);
            myOrder.setSale(saleMember);
            System.out.println(myOrder.getSale_note());
            boolean permission = saleDAO.checkedSalePermission(orderId, sale);

            List<OrderStatus> statusList = orderDAO.getAllOrderStatus();
            List<Account> saleList = saleDAO.getAllAccountOfSaleMember();
            request.setAttribute("saleList", saleList);
            request.setAttribute("statusList", statusList);
            request.setAttribute("myOrder", myOrder);
            request.setAttribute("permission", permission);

            request.getRequestDispatcher("/sale/orderdetails.jsp").forward(request, response);
        } catch (NumberFormatException e) {

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
        request.setCharacterEncoding("utf-8");
        OrderDAO orderDAO = new OrderDAO();
        String action = request.getParameter("action");
        String orderId_raw = request.getParameter("orderId");
        switch (action) {
            case "edit":
                String newNote = request.getParameter("newNote");
                String newStatus_raw = request.getParameter("newStatus");
                try {
                    int newStatus = Integer.parseInt(newStatus_raw);
                    int orderId = Integer.parseInt(orderId_raw);
                    orderDAO.updateOrder(orderId, newStatus, newNote);
                    if (newStatus == 5) {
                        Order order = orderDAO.getOrderByOrderID(orderId);
                        AccountDAO accountDAO = new AccountDAO();
                        Account a = accountDAO.getAccountByID(order.getUser_id());

                        String content = "";
                        content += "        <style>\n"
                                + "            .total {\n"
                                + "                margin-top: 20px;\n"
                                + "                font-size: 150%;\n"
                                + "            }\n"
                                + "            .total span {\n"
                                + "                font-weight: bold;\n"
                                + "            }\n"
                                + "            a {\n"
                                + "                text-decoration: none;\n"
                                + "            }\n"
                                + "        </style>\n"
                                + "        <div>\n"
                                + "            <h3>Your order is finished</h3>\n"
                                + "            <span>List of ordered product</span>\n"
                                + "            <table border=\"1px\">\n"
                                + "                <tr>\n"
                                + "                    <th>Product Name</th>\n"
                                + "                    <th>Price</th>\n"
                                + "                    <th>Quantity</th>\n"
                                + "                    <th>Subtotal</th>\n"
                                + "                    <th>Action</th>\n"
                                + "                </tr>\n";
                        for (OrderDetail orderDetail : order.getOrderDetailList()) {
                            content += "                <tr>\n"
                                    + "                    <td>" + orderDetail.getProduct().getName() + "</td>\n"
                                    + "                    <td>" + orderDetail.getPrice() + "</td>\n"
                                    + "                    <td>" + orderDetail.getQuantity() + "</td>\n"
                                    + "                    <td>" + (orderDetail.getQuantity() * orderDetail.getPrice()) + "</td>\n"
                                    + "                    <td><a href=\"http://localhost:8080/swp/feedback?id=" + orderDetail.getProduct().getProduct_id() + "\">Feedback</a></td>\n"
                                    + "                </tr>";
                        }
                        content += "            </table>\n"
                                + "            <div class=\"total\">\n"
                                + "                <span>Freight: </span><span>" + order.getFreight() + "</span>\n"
                                + "                <span>Total: </span><span>" + (order.getTotal_price()) + "</span>\n"
                                + "            </div>\n"
                                + "            <div>\n"
                                + "                <a href=\"http://localhost:8080/swp/feedback\">Feedback this order</a>"
                                + "            </div>\n"
                                + "        </div>";
                        EmailServiceIml esi = new EmailServiceIml();
                        esi.sendEmailFinishOrder(getServletContext(), a, "finishOrder", content);
                    }
                } catch (NumberFormatException e) {

                }
                response.sendRedirect("/swp/sale/orderdetails?orderId=" + orderId_raw);
                break;
            case "assign":
                String saleId_raw = request.getParameter("newSaleId");
                try {
                    int saleId = Integer.parseInt(saleId_raw);
                    int orderId = Integer.parseInt(orderId_raw);
                    orderDAO.updateOrderManagement(orderId, saleId);
                } catch (NumberFormatException e) {

                }
                response.sendRedirect("/swp/sale/orderdetails?orderId=" + orderId_raw);
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
