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
@WebServlet(name = "EditProductServlet", urlPatterns = {"/marketing/editproduct"})
public class EditProductServlet extends HttpServlet {

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
            out.println("<title>Servlet EditProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditProductServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        ProductDAO p_dao = new ProductDAO();
        String action = request.getParameter("action");
        String product_id_raw = request.getParameter("product_id");
        boolean pos;
        try {
            int product_id = Integer.parseInt(product_id_raw);
            switch (action) {
                case "activate":
                    pos = p_dao.changeFeatured(product_id, true);
                    if (pos) {
                        out.print("active product with id = " + product_id_raw + " success");
//                    returnResult(response, product);
                    } else {
                        response.sendError(1, "acivate fail");
                    }
                    break;
                case "disactivate":
                    pos = p_dao.changeFeatured(Integer.parseInt(product_id_raw), false);
                    if (pos) {
                        out.print("disactive product with id = " + product_id_raw + " success");
                    } else {
                        response.sendError(2, "disacivate fail");
                    }
                    break;
                case "updateproductinformation":
                    String name = request.getParameter("name");
                    String model = request.getParameter("model");
                    String unit_in_stock_raw = request.getParameter("unit_in_stock");
                    String sub_category_id_raw = request.getParameter("subCategoryId");
                    String original_price_raw = request.getParameter("original_price");
                    String sale_price_raw = request.getParameter("sale_price");
                    String brief_infor = request.getParameter("brief_infor");
                    String product_details = request.getParameter("product_details");
                    int unit_in_stock;
                    int sub_category_id;
                    int original_price;
                    int sale_price;

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

                    p_dao.updateProductInformation(product_id, name, model, unit_in_stock, sub_category_id, original_price, sale_price, brief_infor, product_details);
                    out.print("update successfull");
                    break;
                case "changeThumbnail":
                    //////////////////////////
                    String path = getFolderUploadPath();
                    final Part filePart = request.getPart("thumbnail");
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
                            File file = new File(path + p_dao.getProduct(product_id).getThumbnail());
                            p_dao.changeThumbnail(product_id, fileName);
                            TimeUnit.SECONDS.sleep(2);
                            Product p = p_dao.getProduct(product_id);
                            out.print(p.getThumbnail());
                            if (file.exists()) {
                                while (!file.delete()) {
                                }
                                System.out.println("deleted: " + path + p_dao.getProduct(product_id).getThumbnail());
                            }
                        } catch (FileNotFoundException fne) {
                            writer.println("<br/> ERROR: " + fne.getMessage());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
///////////////////
                    break;
                case "deleteImage":
                    String imagePath = request.getParameter("product_image");
                    p_dao.deleteProductImage(product_id, imagePath);
                    File f = new File(getFolderUploadPath() + imagePath);
                    if (f.exists()) {
                        while (!f.delete()) {
                        }
                        System.out.println("deleted: "+getFolderUploadPath() + imagePath);
                    }
                    break;
                case "addImage":
                    String path1 = getFolderUploadPath();
                    final Part filePart1 = request.getPart("image");
                    String fileName1 = getFileName(filePart1);
                    Random ra = new Random();

                    if (!fileName1.equals("")) {
                        while (p_dao.checkThumbnailExist(fileName1)) {
                            fileName1 = ra.nextInt(10) + fileName1;
                        }
                        fileName1 = "images/product_images/" + fileName1;
                        final PrintWriter writer = response.getWriter();

                        try {
                            File f1 = new File(path1 + fileName1);
                            if (f1.exists()) {
                                writer.println("File " + fileName1 + " already exist at " + path1);
                            } else {
                                OutputStream output = new FileOutputStream(f1);
                                InputStream filecontent = filePart1.getInputStream();

                                int read = 0;
                                final byte[] bytes = new byte[1024];

                                while ((read = filecontent.read(bytes)) != -1) {
                                    output.write(bytes, 0, read);
                                }
                            }
                            TimeUnit.SECONDS.sleep(2);
                        } catch (FileNotFoundException fne) {
                            writer.println("<br/> ERROR: " + fne.getMessage());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    p_dao.addProductImage(product_id, fileName1);
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    private boolean checkExistedFile(String fileName, String path) {
        File f = new File(path + fileName);
        return f.exists();
    }

    private void deleteFile(String fileName, String path) {
        File f = new File(path + fileName);
        while (!f.delete()) {
        }
    }

    private boolean saveFile(String fileName, Part filePart, HttpServletResponse response) throws IOException {
        String path = getFolderUploadPath();
        Random r = new Random();
        if (!fileName.equals("")) {
            while (checkExistedFile("images/product_images/" + fileName, path)) {
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
                return true;
            } catch (FileNotFoundException fne) {
                writer.println("<br/> ERROR: " + fne.getMessage());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return false;
    }

    private String getFileName(final Part part) {
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
