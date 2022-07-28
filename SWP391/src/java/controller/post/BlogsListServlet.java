/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.post;

import dal.PostDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Duy Phuong
 */
@WebServlet(name = "BlogsListServlet", urlPatterns = {"/blogslist"})
public class BlogsListServlet extends HttpServlet {

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
        PostDAO postDAO = new PostDAO();
        final int NUMBER_ITEMS_PER_PAGE = 8;

        String orderOption = request.getParameter("orderOption");
        String subcategory_id_raw = request.getParameter("subCategoryId");
        String category_id_raw = request.getParameter("categoryId");
        String[] tag_id_raw_list = request.getParameterValues("tag_id");
        String key = request.getParameter("key");
        String pageNumberRaw = request.getParameter("page");

        if (orderOption == null) {
            orderOption = "publication_date";
        }
        if (subcategory_id_raw == null) {
            subcategory_id_raw = "-1";
        }
        if (category_id_raw == null) {
            category_id_raw = "-1";
        }
        if (key == null) {
            key = "";
        }
        try {
            int sub_category_id = Integer.parseInt(subcategory_id_raw);
            int category_id = Integer.parseInt(category_id_raw);
            List<Integer> tag_id_list = new ArrayList<>();
            if (tag_id_raw_list != null) {
                for (String tag_id_raw : tag_id_raw_list) {
                    tag_id_list.add(Integer.parseInt(tag_id_raw));
                }
            }
            int postsCount = postDAO.countPosts(1, key, sub_category_id, category_id, tag_id_list);
            int pageNumber = pageNumberRaw == null ? 1 : Integer.parseInt(pageNumberRaw);
            int numberPage = postsCount % NUMBER_ITEMS_PER_PAGE == 0 ? postsCount / NUMBER_ITEMS_PER_PAGE : postsCount / NUMBER_ITEMS_PER_PAGE + 1;

            int start = (pageNumber - 1) * NUMBER_ITEMS_PER_PAGE + 1;
            int end = Math.min(pageNumber * NUMBER_ITEMS_PER_PAGE, postsCount);
            System.out.println(postsCount);
            request.setAttribute("postsList", postDAO.getPosts(orderOption, 1, key, sub_category_id, category_id, tag_id_list, start, end));
            request.setAttribute("categoryId", category_id);
            request.setAttribute("subCategoryId", sub_category_id);
            request.setAttribute("orderOption", orderOption);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("key", key);
        } catch (NumberFormatException nfe) {
            System.out.println(nfe);
        }
        request.getRequestDispatcher("blogslist.jsp").forward(request, response);
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
