/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.FeedbackDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Duy Phuong
 */
@WebServlet(name = "FeedbacksListServlet", urlPatterns = {"/marketing/feedbackslist"})
public class FeedbacksListServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ProductDAO productDAO = new ProductDAO();
        int pageNumber, numberPage, start, end, count;
        final int NUMBER_ITEMS_PER_PAGE = 8;

        String page = request.getParameter("page");
        String type = request.getParameter("type");
        String status = request.getParameter("status");
        String key = request.getParameter("key");
        String[] stars_raw = request.getParameterValues("rated_star");
        String orderOption = request.getParameter("orderby");
        String selectOption = request.getParameter("typeProduct");
        if (page == null) {
            page = "1";
        }
        if (selectOption == null) {
            selectOption = "1";
        }
        if (status == null) {
            status = "-1";
        }
        if (stars_raw == null || stars_raw.length == 0) {
            stars_raw = new String[1];
            stars_raw[0] = "-1";
        }
        if (orderOption == null) {
            orderOption = "feedback_id desc";
        }
        if (key == null) {
            key = "";
        }
        if (type == null) {
            type = "1";
        }
//        key = removeAccent(key);s
        try {
            int[] stars = new int[stars_raw.length];

            for (int i = 0; i < stars_raw.length; i++) {
                stars[i] = Integer.parseInt(stars_raw[i]);
            }
            if (type.equals("1")) {
                // General Feedbacks
                count = feedbackDAO.countGeneralFeedbacks(key, Integer.parseInt(status), stars);
                numberPage = (count % NUMBER_ITEMS_PER_PAGE == 0) ? (count / NUMBER_ITEMS_PER_PAGE) : (count / NUMBER_ITEMS_PER_PAGE + 1);

                pageNumber = Integer.parseInt(page);
                start = (pageNumber - 1) * NUMBER_ITEMS_PER_PAGE;
                end = Math.min(pageNumber * NUMBER_ITEMS_PER_PAGE, count);

                request.setAttribute("feedbackslist", feedbackDAO.getGeneralFeedbacksByRange(orderOption, key, Integer.parseInt(status), stars, start, end));
            } else {
                System.out.println("b");
                // Products Feedbacks
                String product_id_raw = request.getParameter("product_id");
                int product_id = product_id_raw == null ? -1 : Integer.parseInt(product_id_raw);
                System.out.println(product_id);
                if (selectOption.equals("1")) {
                    product_id = -1;
                }
                count = feedbackDAO.countProductFeedbacks(product_id, key, Integer.parseInt(status), stars);
                numberPage = (count % NUMBER_ITEMS_PER_PAGE == 0) ? (count / NUMBER_ITEMS_PER_PAGE) : (count / NUMBER_ITEMS_PER_PAGE + 1);

                pageNumber = Integer.parseInt(page);
                start = (pageNumber - 1) * NUMBER_ITEMS_PER_PAGE;
                end = Math.min(pageNumber * NUMBER_ITEMS_PER_PAGE, count);

                request.setAttribute("feedbackslist", feedbackDAO.getProductFeedbacksByRange(orderOption, product_id, key, Integer.parseInt(status), stars, 0, 10));
                request.setAttribute("selectedProduct", productDAO.getProduct(product_id));

            }
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("selectOption", selectOption);
            request.setAttribute("status", status);
            request.setAttribute("stars", stars);
            request.setAttribute("type", type);
            request.setAttribute("orderOption", orderOption);
            request.setAttribute("searchKey", key);
            request.getRequestDispatcher("/marketing/feedbackslist.jsp").forward(request, response);
        } catch (NumberFormatException nfe) {
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
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
