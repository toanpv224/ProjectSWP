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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.OrderStatus;
import model.Sale;
import util.DateTimeUtil;

/**
 *
 * @author Admin
 */
public class SaleServlet extends HttpServlet {

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
            out.println("<title>Servlet SaleServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaleServlet at " + request.getContextPath() + "</h1>");
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
        SaleDAO saleDAO = new SaleDAO();
        OrderDAO orderDAO = new OrderDAO();
//        get startDate and endDate
        LocalDate start, end;
        start = DateTimeUtil.getStartDateDefault();
        end = DateTimeUtil.getEndDateDefault();

//        get list of dateList and revenue by date
        List<String> dates = DateTimeUtil.getStringOfDateItems(start, end);
        List revenue = saleDAO.getListOfTotalRevenueByDay(start, end);

//        get all sale and status of order
        List<Sale> saleList = saleDAO.getAllSaleMember();
        List<OrderStatus> statusList = orderDAO.getAllOrderStatusForSale();

        Account a = new Account();
        a.setFull_name("All sale members");
        Sale sale = new Sale();
        sale.setAccount(a);

        request.setAttribute("sale", sale);
        int aa = orderDAO.getTotalOrderBySaleId(0);
        int ba = orderDAO.getTotalSuccessOrderBySaleId(0);
        request.setAttribute("totalOrderAllTime", aa);
        request.setAttribute("totalSuccessOrderAllTime", ba);
        request.setAttribute("totalRevenueAllTime", orderDAO.getTotalRevenueBySaleId(0));
        request.setAttribute("totalDays", DateTimeUtil.getTotalDays());
        double success;
        if (aa == 0) {
            success = 0;
        } else {
            success = (double)ba / aa;
        }
        request.setAttribute("successRate", success);
        request.setAttribute("revenueBytime", orderDAO.getTotalRevenueBySaleIdAndTime(0, DateTimeUtil.getStartDate(6).toString(), DateTimeUtil.getEndDateDefault().toString()));
        totalSuccessOrderDoGet(request, response);

        request.setAttribute("dates", dates);
        request.setAttribute("revenue", revenue);
        request.setAttribute("saleList", saleList);
        request.setAttribute("statusList", statusList);
        request.setAttribute("time", 1);

        request.getRequestDispatcher("sale/dashboard.jsp").forward(request, response);
    }

    private void totalSuccessOrderDoGet(HttpServletRequest request, HttpServletResponse response) {
        SaleDAO saleDAO = new SaleDAO();
        LocalDate start, end;
        start = DateTimeUtil.getStartDateDefault();
        end = DateTimeUtil.getEndDateDefault();
        List totalOrder = saleDAO.getListOfTotalOrderByDayBySale(start, end, 0);
        List successOrder = saleDAO.getListOfTotalSuccessOrderByDayBySale(start, end, 0);
        List<String> dates = DateTimeUtil.getStringOfDateItems(start, end);

        int countTotalOrder = getTotal(totalOrder);
        int countSuccessOrder = getTotal(successOrder);

        request.setAttribute("countTotalOrder", countTotalOrder);
        request.setAttribute("countSuccessOrder", countSuccessOrder);
        request.setAttribute("labels", dates);
        request.setAttribute("totalOrder", totalOrder);
        request.setAttribute("successOrder", successOrder);
        request.setAttribute("saleIdOrder", 0);
        request.setAttribute("timeOrder", 1);
    }

    private int getTotal(List<Integer> list) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            count += list.get(i);
        }
        return count;
    }

    private void totalSuccessOrderDoPost(HttpServletRequest request, HttpServletResponse response) {
        String time = request.getParameter("orderTrendTime");
        String saleId_raw = request.getParameter("orderTrendSaleId");
        AccountDAO accountDAO = new AccountDAO();
        OrderDAO orderDAO = new OrderDAO();
        int saleId;
        SaleDAO saleDAO = new SaleDAO();
        LocalDate start, end;

        start = DateTimeUtil.getStartDate(time);
        end = DateTimeUtil.getEndDateDefault();
        try {
            saleId = Integer.parseInt(saleId_raw);

            List totalOrder = saleDAO.getListOfTotalOrderByDayBySale(start, end, saleId);
            List successOrder = saleDAO.getListOfTotalSuccessOrderByDayBySale(start, end, saleId);
            List<String> dates = DateTimeUtil.getStringOfDateItems(start, end);

            int countTotalOrder = getTotal(totalOrder);
            int countSuccessOrder = getTotal(successOrder);

            Sale sale = new Sale();
//        get sale member
            if (saleId == 0) {
                Account a = new Account();
                a.setFull_name("All sale members");
                sale = new Sale();
                sale.setAccount(a);
            } else {
                Account a = accountDAO.getAccountByID(saleId);
                sale = new Sale();
                sale.setAccount(a);
            }
            request.setAttribute("sale", sale);
            request.setAttribute("countTotalOrder", countTotalOrder);
            request.setAttribute("countSuccessOrder", countSuccessOrder);

            int a = orderDAO.getTotalOrderBySaleId(saleId);
            int b = orderDAO.getTotalSuccessOrderBySaleId(saleId);
            request.setAttribute("totalOrderAllTime", a);
            request.setAttribute("totalSuccessOrderAllTime", b);
            request.setAttribute("totalRevenueAllTime", orderDAO.getTotalRevenueBySaleId(saleId));
            double success;
            if (a == 0) {
                success = 0;
            } else {
                success = (double)b / a;
            }
            request.setAttribute("successRate", success);
            request.setAttribute("totalDays", DateTimeUtil.getTotalDays());
                    request.setAttribute("revenueBytime", orderDAO.getTotalRevenueBySaleIdAndTime(saleId, DateTimeUtil.getStartDate(6).toString(), DateTimeUtil.getEndDateDefault().toString()));
            request.setAttribute("labels", dates);
            request.setAttribute("totalOrder", totalOrder);
            request.setAttribute("successOrder", successOrder);
            request.setAttribute("saleIdOrder", saleId);
            request.setAttribute("timeOrder", time);
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
        SaleDAO saleDAO = new SaleDAO();
        OrderDAO orderDAO = new OrderDAO();
//        get parameter
        String time = request.getParameter("time");
        String saleId_raw = request.getParameter("saleId");
        String orderStatus_raw = request.getParameter("status");
        int saleId = 0, orderStatus = 0;

        try {
            saleId = Integer.parseInt(saleId_raw);
            orderStatus = Integer.parseInt(orderStatus_raw);
        } catch (NumberFormatException e) {

        }
//        get startDate and endDate
        LocalDate start, end;

        start = DateTimeUtil.getStartDate(time);
        end = DateTimeUtil.getEndDateDefault();

//        get list of dateList and revenue by date
        List<String> dates = DateTimeUtil.getStringOfDateItems(start, end);
        List revenue = saleDAO.getListOfTotalRevenueByDayBySale(start, end, saleId, orderStatus);

//        get all sale and status of order
        List<Sale> saleList = saleDAO.getAllSaleMember();
        List<OrderStatus> statusList = orderDAO.getAllOrderStatusForSale();

        totalSuccessOrderDoPost(request, response);
        request.setAttribute("dates", dates);
        request.setAttribute("revenue", revenue);
        request.setAttribute("saleList", saleList);
        request.setAttribute("statusList", statusList);
        request.setAttribute("saleId", saleId);
        request.setAttribute("orderStatus", orderStatus);
        request.setAttribute("time", time);

        request.getRequestDispatcher("sale/dashboard.jsp").forward(request, response);
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
