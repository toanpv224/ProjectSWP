/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.product;

import dal.FeedbackDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Account;
import model.Feedback;
import model.Product;

/**
 *
 * @author tretr
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50)
public class ProductFeedbackServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductFeedbackServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductFeedbackServlet at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
        String productId = request.getParameter("id");
        request.setAttribute("productId", productId);
        request.getRequestDispatcher("feedback_1.jsp").forward(request, response);
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
//        processRequest(request, response);
        FeedbackDAO fdb = new FeedbackDAO();
        response.setContentType("text/html;charset=UTF-8");

        String userId = request.getParameter("userId");
        String productId = request.getParameter("productId");

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        
        String rate = request.getParameter("rate");
        String feedback = request.getParameter("feedback");
        if(userId==null || productId.equals("")) userId="0";
        if(productId==null || productId.equals("")) productId="0";
        // Create path components to save the file
        final String path = getFolderUploadPath();
        final Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);
        Random r = new Random();

        if (!fileName.equals("")) {
            while (fdb.checkImageExist(fileName) || fdb.checkImageGeneralExist(fileName)) {
                fileName = r.nextInt(10) + fileName;
            }
            fileName = "images\\feedback-images\\" + fileName;
            OutputStream out = null;
            InputStream filecontent = null;
            final PrintWriter writer = response.getWriter();

            try {
                File f = new File(path + fileName);
                System.out.println(path + fileName);
                if (f.exists()) {
                    writer.println("File " + fileName + " already exist at " + path);
                } else {
                    out = new FileOutputStream(f);
                    filecontent = filePart.getInputStream();

                    int read = 0;
                    final byte[] bytes = new byte[1024];

                    while ((read = filecontent.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                }
            } catch (FileNotFoundException fne) {
                writer.println("<br/> ERROR: " + fne.getMessage());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
           
        Account a = new Account();
        a.setUser_id(Integer.parseInt(userId));
        Product p = new Product();
        p.setProduct_id(Integer.parseInt(productId));
        Feedback f = new Feedback();
        f.setUser(a);
        f.setProduct(p);
        f.setFullname(name);
        f.setPhone(phone);
        f.setGender(Boolean.parseBoolean(gender));
        f.setEmail(email);
        f.setStar(Integer.parseInt(rate));
        f.setContent(feedback);
        f.setImage_url(fileName);
        if (!productId.equals("0")) {
            fdb.insertFeedback(f);
        } else {
            fdb.insertGeneralFeedback(f);
        }
        response.sendRedirect("home");
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
