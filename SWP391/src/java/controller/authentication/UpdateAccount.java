/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.authentication;

import dal.AccountDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Account;

/**
 *
 * @author tretr
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50)
public class UpdateAccount extends HttpServlet {

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

        String id = request.getParameter("id");
        String fname = request.getParameter("fname");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String address = request.getParameter("address");

        String type = request.getParameter("type");

        AccountDAO dao = new AccountDAO();
        Account a = new Account();
        HttpSession session = request.getSession();

        if (type.equals("1")) {

            // Create path components to save the file
            final String path = getFolderUploadPath();
            final Part filePart = request.getPart("file");
            String fileName = getFileName(filePart);
            Random r = new Random();

            if (!fileName.equals("")) {
                while (dao.checkAccountImageExist(fileName)) {
                    fileName = r.nextInt(10) + fileName;
                }
                fileName = "images\\account-images\\" + fileName;
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

            a.setUser_id(Integer.parseInt(id));
            a.setImage_url(fileName);

            dao.updateAccountImg(a);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(UpdateAccount.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            a.setUser_id(Integer.parseInt(id));
            a.setFull_name(fname);
            a.setPhone(phone);
            if (gender.endsWith("1")) {
                a.setGender(true);
            } else {
                a.setGender(false);
            }
            a.setCity(city);
            a.setCountry(country);
            a.setAddress(address);
            dao.updateAccount(a);
        }

        a = (Account) request.getSession().getAttribute("account");

        a = dao.getAccountByID(a);
        a.setRole_id(a.getRole().getrId());
        session.setAttribute("account", a);
        if (a.getRole().getrId() == 2) {
            response.sendRedirect("marketing/dashboard");
        } else if (a.getRole().getrId() == 3) {
            response.sendRedirect("sale");
        } else if (a.getRole().getrId() == 4) {
            response.sendRedirect("sale");
        } else if (a.getRole().getrName().equals("Admin")) {
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("home");
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

}
