/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.sale;

import dal.OrderDAO;
import dal.SaleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Order;
import model.OrderStatus;
import model.Sale;
import util.DateTimeUtil;
import util.Pagination;

/**
 *
 * @author Admin
 */
public class OrderListServlet extends HttpServlet {

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
        OrderDAO orderDAO = new OrderDAO();
        int total = orderDAO.getTotalOrder();

        //pagination
        String pageNumberRaw = request.getParameter("page");

        int pageNumber, numberPage;
        int start, end;

        try {
            if (pageNumberRaw == null) {
                pageNumber = 1;
            } else {
                pageNumber = Integer.parseInt(pageNumberRaw);

            }
            numberPage = Pagination.getNumberPage(total);

            start = Pagination.getStart(pageNumber);
            end = Pagination.getEnd(pageNumber, total);

            List<Order> myOrder = orderDAO.getOrderByPage(start + 1, end);

            SaleDAO saleDAO = new SaleDAO();
            List<Sale> saleList = saleDAO.getAllSaleMember();

            List<OrderStatus> statusList = orderDAO.getAllOrderStatus();

            request.setAttribute("saleList", saleList);
            request.setAttribute("statusList", statusList);

            request.setAttribute("orderOption", "order_date");
            request.setAttribute("sequence", "desc");
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("myorders", myOrder);
            request.getRequestDispatcher("/sale/orderslist.jsp").forward(request, response);
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
        request.setCharacterEncoding("utf-8");

        OrderDAO orderDAO = new OrderDAO();
        String key = request.getParameter("key");
        String startDate_raw = request.getParameter("startDate");
        String endDate_raw = request.getParameter("endDate");
        String[] saleId = request.getParameterValues("saleId");
        String[] status = request.getParameterValues("status");
        String pageNumberRaw = request.getParameter("page");
        String orderOption = request.getParameter("orderOption");
        String sequence = request.getParameter("sequence");
        int total;
        List<Order> myOrder;
        //pagination
        int pageNumber, numberPage;
        String startDate, endDate;
        try {

            if (pageNumberRaw == null) {
                pageNumber = 1;
            } else {
                pageNumber = Integer.parseInt(pageNumberRaw);

            }
            if (startDate_raw.isEmpty()) {
                startDate = orderDAO.getFirstOrderDate();
            } else {
                startDate = startDate_raw;
            }

            if (endDate_raw.isEmpty()) {
                endDate = DateTimeUtil.Now();
            } else {
                endDate = DateTimeUtil.getEndDate2(endDate_raw);
            }

            total = orderDAO.getTotalOrder(saleId, status, key, startDate, endDate);

            numberPage = Pagination.getNumberPage(total);
            if (pageNumber > numberPage) {
                pageNumber = 1;
            }
            int start, end;
            start = Pagination.getStart(pageNumber);
            end = Pagination.getEnd(pageNumber, total);

            myOrder = orderDAO.getOrderByPage(start, end, saleId, status, orderOption+" "+sequence, key, startDate, endDate);

            SaleDAO saleDAO = new SaleDAO();
            List<Sale> saleList = saleDAO.getAllSaleMember();
            List<OrderStatus> statusList = orderDAO.getAllOrderStatus();

            boolean[] checkedSale = new boolean[saleList.size()];
            boolean[] checkedStatus = new boolean[statusList.size()];

            checkSale(saleList, checkedSale, saleId);
            checkStatus(statusList, checkedStatus, status);
            for (int i = 0; i < checkedStatus.length; i++) {
                System.out.println(checkedStatus[i]);
            }
            request.setAttribute("key", key);
            request.setAttribute("startDate", startDate_raw);
            request.setAttribute("endDate", endDate_raw);

            request.setAttribute("saleList", saleList);
            request.setAttribute("statusList", statusList);

            request.setAttribute("orderOption", orderOption);
            request.setAttribute("sequence", sequence);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("myorders", myOrder);

            request.setAttribute("checkedStatus", checkedStatus);
            request.setAttribute("checkedSale", checkedSale);
            request.getRequestDispatcher("/sale/orderslist.jsp").forward(request, response);
        } catch (IOException e) {

        }
    }

    private boolean checked(String[] arr, int a) {
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

    private void checkSale(List<Sale> saleList, boolean[] checkedSale, String[] saleId) {
        for (int i = 0; i < saleList.size(); i++) {
            if (checked(saleId, saleList.get(i).getAccount().getUser_id())) {
                checkedSale[i] = true;
            }
        }
    }

    private void checkStatus(List<OrderStatus> statusList, boolean[] checkStatus, String[] status) {
        for (int i = 0; i < statusList.size(); i++) {
            if (checked2(status, statusList.get(i).getId())) {
                checkStatus[i] = true;
            }
        }
    }

    private boolean checked2(String[] status, int statusId) {
        if (status == null) {
            return false;
        } else {
            for (String statu : status) {
                if (statu.equals(statusId + "")) {
                    return true;
                }
            }
            return false;

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
