/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.ProductDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Product;

/**
 *
 * @author Duy Phuong
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet(name = "AddproductServlet", urlPatterns = {"/marketing/addproduct"})
public class AddproductServlet extends HttpServlet {

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
            out.println("<title>Servlet AddproductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddproductServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("/marketing/addproduct.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        ProductDAO p_dao = new ProductDAO();

        String name = request.getParameter("name");
        String model = request.getParameter("model");
        String unit_in_stock_raw = request.getParameter("unit_in_stock");
        String sub_category_id_raw = request.getParameter("subCategoryId");
        String original_price_raw = request.getParameter("original_price");
        String sale_price_raw = request.getParameter("sale_price");
        String brief_infor = request.getParameter("brief_infor");
        String product_details = request.getParameter("product_details");
        String featured_raw = request.getParameter("featured");

        String path = getFolderUploadPath();
        Part filePart = request.getPart("thumbnail");
        String fileName = getFileName(filePart);
        Random r = new Random();

        if (!fileName.equals("")) {
            while (p_dao.checkThumbnailExist(fileName)) {
                fileName = r.nextInt(10) + fileName;
            }
            fileName = "images/product_images/" + fileName;
            final PrintWriter writer = response.getWriter();

            try {
                File f = new File(path + fileName);
                if (f.exists()) {
                    writer.println("File " + fileName + " already exist at " + path);
                } else {
                    OutputStream output = new FileOutputStream(f);
                    InputStream filecontent = filePart.getInputStream();

                    int read = 0;
                    final byte[] bytes = new byte[1024];

                    while ((read = filecontent.read(bytes)) != -1) {
                        output.write(bytes, 0, read);
                    }
                }
            } catch (FileNotFoundException fne) {
                writer.println("<br/> ERROR: " + fne.getMessage());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        filePart = request.getPart("image");
        String fileName2 = getFileName(filePart);
        if (!fileName2.equals("")) {
            while (p_dao.checkThumbnailExist(fileName2)) {
                fileName2 = r.nextInt(10) + fileName2;
            }
            fileName2 = "images/product_images/" + fileName2;
            final PrintWriter writer = response.getWriter();

            try {
                File f = new File(path + fileName2);
                if (f.exists()) {
                    writer.println("File " + fileName2 + " already exist at " + path);
                } else {
                    OutputStream output = new FileOutputStream(f);
                    InputStream filecontent = filePart.getInputStream();

                    int read = 0;
                    final byte[] bytes = new byte[1024];

                    while ((read = filecontent.read(bytes)) != -1) {
                        output.write(bytes, 0, read);
                    }
                }
            } catch (FileNotFoundException fne) {
                writer.println("<br/> ERROR: " + fne.getMessage());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        int unit_in_stock;
        int sub_category_id;
        int original_price;
        int sale_price;
        int featured;

        try {
            unit_in_stock = Integer.parseInt(unit_in_stock_raw);
        } catch (NumberFormatException nfe) {
            response.sendError(1000, "Unit in Stock in wrong format");
            return;
        }
        try {
            sub_category_id = Integer.parseInt(sub_category_id_raw);
        } catch (NumberFormatException nfe) {
            response.sendError(1000, "Sub Category in wrong format");
            return;
        }
        try {
            original_price = Integer.parseInt(original_price_raw);
        } catch (NumberFormatException nfe) {
            response.sendError(1000, "Original price in wrong format");
            return;
        }
        try {
            sale_price = Integer.parseInt(sale_price_raw);
        } catch (NumberFormatException nfe) {
            response.sendError(1000, "Sale price in wrong format");
            return;
        }
        try {
            if (featured_raw!=null) {
                featured = Integer.parseInt(featured_raw);
            } else {
                featured = 0;
            }
        } catch (NumberFormatException nfe) {
            response.sendError(1000, "Sale price in wrong format");
            return;
        }

        p_dao.addProduct(name, model, unit_in_stock, sub_category_id, original_price, sale_price, featured, brief_infor, product_details, fileName);
        p_dao.addProductImage(p_dao.getLastProducts().getProduct_id(), fileName2);
        out.print("add successfull");
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public String getFolderUploadPath() {
        String path = getServletContext().getRealPath("/").replace("\\build", "");
        File folderUpload = new File(path);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return path;
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
