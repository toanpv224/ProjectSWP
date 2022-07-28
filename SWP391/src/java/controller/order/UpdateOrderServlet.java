/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.order;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Order;
import model.OrderDetail;
import service.EmailService;
import service.EmailServiceIml;

/**
 *
 * @author Admin
 */
public class UpdateOrderServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateOrderServlet at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        Account acocunt = (Account) session.getAttribute("account");
        OrderDAO orderDAO = new OrderDAO();

        String orderId_raw = request.getParameter("orderId");
        int orderId;
        try {
            orderId = Integer.parseInt(orderId_raw);
            Order myOrder = orderDAO.getOrderByUserIdAndOrderId(acocunt.getUser_id(), orderId);
            if (myOrder == null) {
                PrintWriter out = response.getWriter();
                out.println("access denied");
            } else {
                
//                get updated infor
                String shipName = request.getParameter("shipName");
                String receiverGender_raw = request.getParameter("receiverGender");
                boolean gender;
                if (receiverGender_raw.equals("1")) {
                    gender = true;
                } else {
                    gender = false;
                }
                String shipEmail = request.getParameter("shipEmail");
                String shipMobile = request.getParameter("shipMobile");
                orderDAO.updateOrder(myOrder, shipName, gender, shipEmail, shipMobile, myOrder.getShip_address(), myOrder.getShip_city(), myOrder.getPayment());
                

//                send mail
                String message = "Your order has been updated!\n";
                message += "Ship Name: " + shipName + "\n";
                message += "Ship Email: " + shipEmail + "\n";
                message += "Ship Mobile: " + shipMobile + "\n";
                message += "Ship Address: " + myOrder.getShip_address() + "\n";
                message += "Ship City: " + myOrder.getShip_city() + "\n";

                EmailServiceIml sendMail = new EmailServiceIml();
                sendMail.sendEmail(getServletContext(), acocunt, "update", message);

//                get new order
                myOrder = orderDAO.getOrderByOrderID(myOrder.getOrder_id());
                
//                set payment
                List<String> payments = new ArrayList<String>();
                payments.add("Ship COD");
                payments.add("BANK TRANSFER");
                payments.add("VN PAY");
                request.setAttribute("payments", payments);
                request.setAttribute("order", myOrder);
                request.setAttribute("message", "Successfully");
                request.getRequestDispatcher("updateorders.jsp").forward(request, response);
            }

        } catch (IOException | NumberFormatException e) {

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
        HttpSession session = request.getSession();
        Account acocunt = (Account) session.getAttribute("account");
        OrderDAO orderDAO = new OrderDAO();
        String orderId_raw = request.getParameter("orderId");
        int orderId;
        try {
            orderId = Integer.parseInt(orderId_raw);
            Order myOrder = orderDAO.getOrderByUserIdAndOrderId(acocunt.getUser_id(), orderId);
            if (myOrder == null) {
                PrintWriter out = response.getWriter();
                out.println("access denied");
            } else {
                String message = "Your order have been canceled.\nProduct List: \n";
                for (OrderDetail o : myOrder.getOrderDetailList()) {
                    message += o.getProduct().getName() + "   " + o.getPrice() + " x " + o.getQuantity() + "\n";
                }
                message += (myOrder.getTotal_price() + myOrder.getFreight());

                EmailServiceIml sendMail = new EmailServiceIml();
                sendMail.sendEmail(getServletContext(), acocunt, "cancel", message);
                orderDAO.cancelOrder(myOrder);
                response.sendRedirect("home");
            }

        } catch (IOException | NumberFormatException e) {

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
