package controller.product;

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

@WebServlet(name = "ProductsListServlet", urlPatterns = {"/productslist"})
public class ProductsListServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductListServlet at " + request.getContextPath() + "</h1>");
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
        ProductDAO productDAO = new ProductDAO();
        SubCategoryDAO subCategoryDAO = new SubCategoryDAO();

        //get subCategory List
        List<SubCategory> subCategoryList; //khoi tao list chua subcategory

        //chuyen category hoac subcategory thanh List chua subcategory tuong ung        
        if (request.getParameter("categoryId") != null || request.getParameter("subCategoryId") != null) {
            if (request.getParameter("categoryId") != null) {
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                subCategoryList = subCategoryDAO.getSubCategoryByCategoryId(categoryId);
                request.setAttribute("categoryId", categoryDAO.getProductCategory(categoryId));
            } else {
                subCategoryList = new ArrayList<>();

                int subCategoryId = Integer.parseInt(request.getParameter("subCategoryId"));
                SubCategory subCategory = subCategoryDAO.getSubCategoryById(subCategoryId);
                subCategoryList.add(subCategory);
                ProductCategory category = categoryDAO.getProductCategoryBySubCategory(subCategoryId);
                request.setAttribute("categoryIdParent", category);
                request.setAttribute("subCategoryId", subCategory);

            }
            //set subcategoryList = null neu khong co parameter categoryid hoac subcategoryid
        } else {
            subCategoryList = null;
        }

        //get total number
        int productCount = productDAO.countProducts(subCategoryList, "");
        
        
        //pagination
        String pageNumberRaw = request.getParameter("page");
        int pageNumber, numberPage;
        final int NUMBER_ITEMS_PER_PAGE = 8;

        try {
            //assign pageNumber = 1 if it null, otherwise parse
            if (pageNumberRaw == null) {
                pageNumber = 1;
            } else {
                pageNumber = Integer.parseInt(pageNumberRaw);

            }
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
            List<Product> productListByPage = productDAO.getProductsByRange(subCategoryList, "", "newest", start + 1, end);


            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("productListByPage", productListByPage);


//            forward to jsp
            request.getRequestDispatcher("productslist.jsp").forward(request, response);
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
        response.setCharacterEncoding("utf-8");
//        get search key
        String key = request.getParameter("key");

        CategoryDAO categoryDAO = new CategoryDAO();
        SubCategoryDAO subCategoryDAO = new SubCategoryDAO();
        List<SubCategory> subCategoryList;
        if (request.getParameter("categoryId") != null || request.getParameter("subCategoryId") != null) {
            if (request.getParameter("categoryId") != null) {
                subCategoryList = subCategoryDAO.getSubCategoryByCategoryId(Integer.parseInt(request.getParameter("categoryId")));
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                ProductCategory productCate = categoryDAO.getProductCategory(categoryId);
                request.setAttribute("categoryId", productCate);
            } else {
                subCategoryList = new ArrayList<>();

                int subCategoryId = Integer.parseInt(request.getParameter("subCategoryId"));
                SubCategory subCategory = subCategoryDAO.getSubCategoryById(subCategoryId);
                subCategoryList.add(subCategory);
                ProductCategory category = categoryDAO.getProductCategoryBySubCategory(subCategoryId);

                request.setAttribute("categoryIdParent", category);
                request.setAttribute("subCategoryId", subCategory);
            }
        } else {
            subCategoryList = null;
        }

//        assign key is empty if it is null
        if (key == null) {
            key = "";
        }

//        get order option, assign orderOption = newest neu null
        String orderOption = request.getParameter("orderOption");
        if (orderOption == null) {
            orderOption = "newest";
        }

        ProductDAO productDAO = new ProductDAO();

        int productCount = productDAO.countProducts(subCategoryList, key);

        //pagination
        String pageNumberRaw = request.getParameter("page");
        int pageNumber, numberPage;
        final int NUMBER_ITEMS_PER_PAGE = 8;

        try {

            //assign pageNumber = 1 if it null, otherwise parse
            if (pageNumberRaw == null) {
                pageNumber = 1;
            } else {
                pageNumber = Integer.parseInt(pageNumberRaw);

            }

            //get number of page 
            if (productCount % NUMBER_ITEMS_PER_PAGE == 0) {
                numberPage = productCount / NUMBER_ITEMS_PER_PAGE;
            } else {
                numberPage = productCount / NUMBER_ITEMS_PER_PAGE + 1;
            }

            if (pageNumber > numberPage) {
                pageNumber = 1;
            }

            //get start and end position in the list of all product
            int start, end;
            start = (pageNumber - 1) * NUMBER_ITEMS_PER_PAGE;
            end = Math.min(pageNumber * NUMBER_ITEMS_PER_PAGE, productCount);

            //get product by page
            List<Product> productListByPage = productDAO.getProductsByRange(subCategoryList, key, orderOption, start + 1, end);

//            set attributes
            request.setAttribute("key", key);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("numberPage", numberPage);
            request.setAttribute("productListByPage", productListByPage);
            request.setAttribute("orderOption", orderOption);
//            forward to jsp
            request.getRequestDispatcher("productslist.jsp").forward(request, response);
        } catch (NumberFormatException e) {

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
