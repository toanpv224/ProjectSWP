/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import com.google.gson.Gson;
import dal.AccountDAO;
import dal.CategoryDAO;
import dal.FeedbackDAO;
import dal.OrderDAO;
import dal.SaleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ProductCategory;
import util.DateTimeUtil;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

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
            out.println("<title>Servlet AdminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminServlet at " + request.getContextPath() + "</h1>");
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
        CategoryDAO categoryDAO = new CategoryDAO();
        List<ProductCategory> cateList = categoryDAO.getProductCategory();
        cateList.add(new ProductCategory(0, "Total", "", 1));
        request.setAttribute("cateList", cateList);
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
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
        PrintWriter out = response.getWriter();
        OrderDAO orderDAO = new OrderDAO();
        String time = request.getParameter("time");
        String choice = request.getParameter("choice");
        CategoryDAO categoryDAO = new CategoryDAO();
        SaleDAO saleDAO = new SaleDAO();
        Gson g = new Gson();
        
        switch (choice) {
            case "newOrder":
                int totalSubmittedOrder = orderDAO.getTotalOrderByStatusAndTime(1, time);
                int totalSuccessOrder = orderDAO.getTotalOrderByStatusAndTime(5, time);
                int totalCancelOrder = orderDAO.getTotalOrderByStatusAndTime(6, time);
                int[] array = {totalSubmittedOrder, totalSuccessOrder, totalCancelOrder};
                String result = g.toJson(array);
                out.print(result);
                break;
            case "revenue":
                List<ProductCategory> cateList = categoryDAO.getProductCategory();
                cateList.add(new ProductCategory(0, "Total", "", 1));
                double[] revenueArray = new double[cateList.size()];
                for (int i = 0; i < cateList.size(); i++) {
                    revenueArray[i] = saleDAO.getRevenueByProductCategoryId(cateList.get(i).getCategory_id(), time);
                }
                result = g.toJson(revenueArray);
                out.print(result);
                break;
            case "feedback":
                cateList = categoryDAO.getProductCategory();
                FeedbackDAO fd = new FeedbackDAO();
                cateList.add(new ProductCategory(0, "Total", "", 1));
                String[] feedbackArray = new String[cateList.size()];
                for (int i = 0; i < cateList.size(); i++) {
                    feedbackArray[i] = fd.getAverageRated(cateList.get(i).getCategory_id(), time);
                }
                result = g.toJson(feedbackArray);
                out.print(result);
                break;
            case "customer":
                AccountDAO accountDAO = new AccountDAO();
                LocalDate start = DateTimeUtil.getStartDate(time);
                LocalDate end = DateTimeUtil.getEndDateDefault();
                List<String> label = DateTimeUtil.getStringOfDateItems(start, end);
                List newlyRegister = accountDAO.getListOfNewlyRegisterCustomer(start, end);
                List newlyBought = accountDAO.getListOfNewlyBoughtCustomer(start, end);
                HashMap map = new HashMap();
                map.put("label", label);
                map.put("newlyRegister", newlyRegister);
                map.put("newlyBought", newlyBought);
                
                result = g.toJson(map);
                out.print(result);
                break;
            case "order":
                start = DateTimeUtil.getStartDate(time);
                end = DateTimeUtil.getEndDateDefault();
                label = DateTimeUtil.getStringOfDateItems(start, end);
                List allOrder = saleDAO.getListOfAllOrderByDay(start, end);
                List successOrder = saleDAO.getListOfTotalSuccessOrderByDayBySale(start, end, 0);
                map = new HashMap();
                map.put("label", label);
                map.put("allOrder", allOrder);
                map.put("successOrder", successOrder);
                result = g.toJson(map);
                out.print(result);
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
