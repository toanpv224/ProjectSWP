/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.product;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Cart;
import model.Order;
import service.EmailService;
import service.EmailServiceIml;

/**
 *
 * @author Admin
 */
public class CartCompletionServlet extends HttpServlet {
 private EmailService emailService = new EmailServiceIml();
    //get payment name by ID
    String getPaymentByID(int paymentID) {
        switch (paymentID) { //select payment name
            case 1:
                return "Ship COD";
            case 2:
                return "BANK TRANSFER";
            case 3:
                return "VN PAY";
        }
        return null;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CartCompletionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartCompletionServlet at " + request.getContextPath() + "</h1>");
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
        try {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
             //decode orderID
          String orderID_raw=request.getParameter("orderid");
          byte[] decode=Base64.getDecoder().decode(orderID_raw);
          String OrderID_decoded=new String(decode,"UTF-8");
          int orderID=Integer.parseInt(OrderID_decoded);
          OrderDAO orderDAO=new OrderDAO();
            Cart cartSubmitted = orderDAO.getCartSubmitted(orderID);
            Order order = orderDAO.getOrderByOrderID(orderID);
            request.setAttribute("order", order);
            request.setAttribute("cart", cartSubmitted);
            request.getRequestDispatcher("cartcompletion.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
            //get Information of cart contact
            int oderID = Integer.parseInt(request.getParameter("orderID"));
            OrderDAO orderDAO = new OrderDAO();
            Order order = orderDAO.getOrderByOrderID(oderID);
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            int gender_raw = Integer.parseInt(request.getParameter("gender"));
            boolean gender;
            if (gender_raw == 0) {
                gender = false;
            } else {
                gender = true;
            }
            int status = 2;//after choose paymentmethod
            String note = request.getParameter("note");
            int paymentID = Integer.parseInt(request.getParameter("payment"));
            String payment = getPaymentByID(paymentID);
            String freight_raw=request.getParameter("freight");
            double freight = Double.parseDouble(freight_raw);
            double total_price = Double.parseDouble(request.getParameter("total_price"));
            //set information to update order
            order.setOrder_id(oderID);
            order.setShip_name(name);
            order.setShip_email(email);
            order.setShip_mobile(phone);
            order.setShip_address(address);
            order.setShip_city(city);
            order.setShip_gender(gender);
            order.setStatus(status);
            order.setPayment(payment);
            order.setFreight(freight);
            order.setNote(note);
            order.setTotal_price(total_price);
            orderDAO.UpdateOrderInformation(order);  //update order
            //send mail
            emailService.sendEmailComfirmUpdateOrder(getServletContext(), name, email, order.getOrder_id());
            Cart cartSubmitted = orderDAO.getCartSubmitted(oderID);
            request.setAttribute("order", order);
            request.setAttribute("cart", cartSubmitted);
            request.getRequestDispatcher("cartcompletion.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
