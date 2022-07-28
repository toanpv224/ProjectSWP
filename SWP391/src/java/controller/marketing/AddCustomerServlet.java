/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import static controller.authentication.RegisterServlet.getMd5;
import dal.AccountDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Account;
import model.Customer;

/**
 *
 * @author win
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet(name = "AddCustomerServlet", urlPatterns = {"/marketing/addcustomer"})
public class AddCustomerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddCustomerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCustomerServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    //get folder will upload
    public String getFolderUploadPath() {
        String path = getServletContext().getRealPath("/").replace("\\build", "");
        File folderUpload = new File(path);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return path;
    }
//get file name will add

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    //endocding MD5
    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        Customer customer = new Customer();
        request.setAttribute("cus", customer);
        request.setAttribute("mess", message);
        request.getRequestDispatcher("/marketing/addcustomer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        AccountDAO accountDAO = new AccountDAO();
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String phone = request.getParameter("phone");
        int gender_raw = Integer.parseInt(request.getParameter("gender"));
        boolean gender = (gender_raw == 1) ? true : false;
        int feature_raw = Integer.parseInt(request.getParameter("featured"));
        boolean feature = (feature_raw == 1) ? true : false;
        String password = "12345678";// default
        int role_id = 1;//role customer
        Random random = new Random();
        random.nextInt(9999999);
        String myHash = getMd5("" + random);
        int active = 1;//auto active

        //get image
        final PrintWriter writer = response.getWriter();
        String path = getFolderUploadPath();
        Part filePart = request.getPart("imageUpload");
        String fileName = "";

       
        try {
            if (filePart.getSize() == 0) {//not choose
                fileName = "../images/account-images/acc.png";
            } else { //chosen avatar
                fileName = getFileName(filePart);
                fileName = "images/account-images/" + fileName;
                File f = new File(path + fileName);
                OutputStream output = new FileOutputStream(f);
                InputStream filecontent = filePart.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    output.write(bytes, 0, read);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String message = "";
        //create new customer 
        Customer customer = new Customer(username, getMd5(password), fullname, role_id,
                gender, email, city, country, address, phone, fileName, feature, myHash, active);
        //add customer
        message = accountDAO.AddCustomer(customer);
        request.setAttribute("cus", customer);
        request.setAttribute("mess", message);
        request.getRequestDispatcher("/marketing/addcustomer.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
