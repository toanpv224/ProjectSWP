/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.SliderDAO;
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
import model.Slider;

/**
 *
 * @author Duy Phuong
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet(name = "EditSliderServlet", urlPatterns = {"/marketing/editslider"})
public class EditSliderServlet extends HttpServlet {

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
            out.println("<title>Servlet EditSliderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditSliderServlet at " + request.getContextPath() + "</h1>");
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
        SliderDAO sliderDAO = new SliderDAO();

        boolean pos;
        String action = request.getParameter("action");
        String slider_id_raw = request.getParameter("slider_id");
        System.out.println(slider_id_raw);
        try {
            int slider_id = Integer.parseInt(slider_id_raw);
            switch (action) {
                case "activate":
                    pos = sliderDAO.changeSliderStatus(slider_id, 1);
                    if (!pos) {
                        response.sendError(1, "acivate fail");
                    }
                    break;
                case "disactivate":
                    pos = sliderDAO.changeSliderStatus(slider_id, 0);
                    if (!pos) {
                        response.sendError(1, "disacivate fail");
                    }
                    break;
                case "change_information":
                    String title = request.getParameter("title");
                    String backlink = request.getParameter("backlink");
                    String notes = request.getParameter("notes");
                    pos = sliderDAO.changeSliderInformation(slider_id, title, backlink, notes);
                    if (!pos) {
                        response.sendError(1, "update slidedr information fail");
                    } else {
                        String output = "<div class=\"input-group mb-3\">\n"
                                + "                            <span class=\"input-group-text\">Title</span>\n"
                                + "                            <input type=\"text\" class=\"form-control\" placeholder=\"Slider Title\" value=\"" + title + "\" readonly>\n"
                                + "                        </div>\n"
                                + "                        <div class=\"input-group mb-3\">\n"
                                + "                            <span class=\"input-group-text\">BackLinks</span>\n"
                                + "                            <input type=\"text\" class=\"form-control\" placeholder=\"Slider Title\" value=\"" + backlink + "\" readonly>\n"
                                + "                        </div>\n"
                                + "                        <div class=\"input-group\">\n"
                                + "                            <span class=\"input-group-text\">Notes</span>\n"
                                + "                            <textarea class=\"form-control\" placeholder=\"Slider Notes\" name=\"content\" readonly>" + notes + "</textarea>\n"
                                + "                        </div>";
                        out.print(output);
                    }
                    break;
                case "change_inmage":
                    String path = getFolderUploadPath();
                    
                    Slider slider = sliderDAO.getSlider(slider_id);
                    String image = slider.getImagePath();
                    String fileName = saveImage(request, response);
                    sliderDAO.changeSliderImage(slider_id, fileName);
                    File f = new File(path + image);
                    while(f.exists()){
                        f.delete();
                    }
                    out.print(fileName);
                    break;
                default:
                    response.sendError(2, "action fail");
            }
        } catch (NumberFormatException nfe) {
            System.out.println(nfe);
        }
    }

    private String saveImage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = getFolderUploadPath();
        final Part filePart = request.getPart("input-slider-image");
        String fileName = getFileName(filePart);
        Random r = new Random();

        if (!fileName.equals("")) {
            while (new File(path + "images/slider-images/" +  fileName).exists()) {
                fileName = r.nextInt(10) + fileName;
            }
            fileName = "images/slider-images/" + fileName;
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
                TimeUnit.SECONDS.sleep(2);
            } catch (FileNotFoundException fne) {
                writer.println("<br/> ERROR: " + fne.getMessage());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return fileName;
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
