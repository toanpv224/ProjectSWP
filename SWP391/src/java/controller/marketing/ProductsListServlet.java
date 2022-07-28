/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.CategoryDAO;
import dal.ProductDAO;
import dal.SubCategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Product;
import model.ProductCategory;
import model.SubCategory;

/**
 *
 * @author Duy Phuong
 */
@WebServlet(name = "MarketingProductsListServlet", urlPatterns = {"/marketing/productslist"})
public class ProductsListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();

        String featured_raw = request.getParameter("featured");
        String category_id_raw = request.getParameter("categoryId");
        String sub_category_id_raw = request.getParameter("subCategoryId");
        String orderby = request.getParameter("orderby");
        String key = request.getParameter("search_key");
        if (featured_raw == null) {
            featured_raw = "-1";
        }
        if (category_id_raw == null) {
            category_id_raw = "-1";
        }
        if (sub_category_id_raw == null) {
            sub_category_id_raw = "-1";
        }
        if (orderby == null || orderby.equals("")) {
            orderby = "product_id asc";
        }
        if (key == null) {
            key = "";
        }

        //pagination
        String pageNumberRaw = request.getParameter("page");
        int pageNumber, numberPage;
        final int NUMBER_ITEMS_PER_PAGE = 8;

        try {
            //assign pageNumber = 1 if it null, otherwise parse
            pageNumber = pageNumberRaw == null ? 1 : Integer.parseInt(pageNumberRaw);

            int featured = Integer.parseInt(featured_raw);
            int category_id = Integer.parseInt(category_id_raw);
            int sub_category_id = Integer.parseInt(sub_category_id_raw);

            int productCount = productDAO.countAllProducts(sub_category_id, category_id, key, featured);
            //get number of page 
            if (productCount % NUMBER_ITEMS_PER_PAGE == 0) {
                numberPage = productCount / NUMBER_ITEMS_PER_PAGE;
            } else {
                numberPage = productCount / NUMBER_ITEMS_PER_PAGE + 1;
            }
            //get start and end position in the list of all product
            int start, end;
            start = (pageNumber - 1) * NUMBER_ITEMS_PER_PAGE;
            end = Math.min(pageNumber * NUMBER_ITEMS_PER_PAGE, productCount);

//            get product by page with key = "", orderOption = newest
            List<Product> productListByPage = productDAO.getAllProductsByRange(sub_category_id, category_id, key, orderby, start + 1, end, featured);

            request.setAttribute("choosen_featured", featured);
            request.setAttribute("choosen_category_id", category_id);
            request.setAttribute("choosen_sub_category_id", sub_category_id);
//            request.setAttribute("choosen_sub_category", c_dao.getProductCategory(category_id));
            request.setAttribute("choosen_orderby", orderby);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("numberPage", numberPage);
            System.out.println(pageNumber);
            System.out.println(numberPage);
            request.setAttribute("productListByPage", productListByPage);

//            forward to jsp
            request.getRequestDispatcher("/marketing/productslist.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.out.println("");
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
