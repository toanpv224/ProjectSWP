/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import com.google.gson.Gson;
import dal.AccountDAO;
import dal.FeedbackDAO;
import dal.PostDAO;
import dal.ProductDAO;
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
import javax.servlet.http.HttpSession;
import model.Account;
import util.DateTimeUtil;

/**
 *
 * @author Admin
 */
@WebServlet(name = "MarketingDashBoardServlet", urlPatterns = {"/marketing/dashboard"})
public class MarketingDashBoardServlet extends HttpServlet {

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
            out.println("<title>Servlet MarketingDashBoardServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MarketingDashBoardServlet at " + request.getContextPath() + "</h1>");
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

//        get session account
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            //khai bao doi tuong cac lop data access object
            PostDAO postDAO = new PostDAO();
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            ProductDAO productDAO = new ProductDAO();
            AccountDAO accountDAO = new AccountDAO();

            //khoi tao cac bien va gan gia tri
            int totalPosts, totalGeneralFeedbacks, totalProductFeedbacks, totalProducts, totalAccounts;
            totalPosts = postDAO.countPosts();
            totalAccounts = accountDAO.getTotalCustomers();
            totalGeneralFeedbacks = feedbackDAO.getTotalGeneralFeedbacks();
            totalProductFeedbacks = feedbackDAO.getTotalProductFeedbacks();
            totalProducts = productDAO.getTotalProducts();

            //statistic 
            request.setAttribute("totalPosts", totalPosts);
            request.setAttribute("totalAccounts", totalAccounts);
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("totalFeedbacks", totalProductFeedbacks + totalGeneralFeedbacks);
            request.getRequestDispatcher("/marketing/dashboard.jsp").forward(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println("access denied");
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
        PrintWriter out = response.getWriter();
        String time = request.getParameter("time");
        String choice = request.getParameter("choice");
        AccountDAO accountDAO = new AccountDAO();
        Gson g = new Gson();
        LocalDate start = DateTimeUtil.getStartDate(time);
        LocalDate end = DateTimeUtil.getEndDateDefault();
        List label = DateTimeUtil.getStringOfDateItems(start, end);

        switch (choice) {
            case "newCustomer":
                List data = accountDAO.getCustomersByDays(start, end);
                HashMap map = new HashMap();
                map.put("label", label);
                map.put("data", data);
                String result = g.toJson(map);
                out.print(result);
                break;
            case "post":
                PostDAO postDAO = new PostDAO();
                data = postDAO.getPostByDays(start, end);
                map = new HashMap();
                map.put("label", label);
                map.put("data", data);
                int addedData = ((int) data.get(data.size() - 1) - (int) data.get(0));
                map.put("new", addedData);
                result = g.toJson(map);
                out.print(result);
                break;
            case "product":
                ProductDAO productDAO = new ProductDAO();
                data = productDAO.getProductsByDays(start, end);
                map = new HashMap();
                map.put("label", label);
                map.put("data", data);
                addedData = ((int) data.get(data.size() - 1) - (int) data.get(0));
                map.put("new", addedData);
                result = g.toJson(map);
                out.print(result);
                break;
            case "customer":
                data = accountDAO.getTotalCustomersByDay(start, end);
                map = new HashMap();
                map.put("label", label);
                map.put("data", data);
                addedData = ((int) data.get(data.size() - 1) - (int) data.get(0));
                map.put("new", addedData);
                result = g.toJson(map);
                out.print(result);
                break;
            case "feedback":
                FeedbackDAO feedbackDAO = new FeedbackDAO();
                data = feedbackDAO.getTotalFeedbacksByDay(start, end);
                map = new HashMap();
                map.put("label", label);
                map.put("data", data);
                addedData = ((int) data.get(data.size() - 1) - (int) data.get(0));
                map.put("new", addedData);
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
