/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.CategoryDAO;
import dal.PostCategoryDAO;
import dal.PostDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Account;
import model.Post;
import model.PostCategory;
import model.PostSubCategory;
import model.ProductCategory;

/**
 *
 * @author Admin
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet(name = "AddPostServlet", urlPatterns = {"/marketing/addpost"})
public class AddPostServlet extends HttpServlet {

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
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Access denied');");
            out.println("window.location.href = \"http://localhost:8080/swp/home#divOne\";");
            out.println("</script>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                 HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
//        if(account==null){ //have  not login
//            processRequest(request, response);
//        }
        PostCategoryDAO pcd=new PostCategoryDAO();
        List<PostCategory> categories = pcd.getPostCategory();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/marketing/addpost.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
         Account account = (Account) session.getAttribute("account");
        if(account==null){ //have  not login
            processRequest(request, response);
        }
        
        //get attribure
        String title=request.getParameter("title");
     
//        int categoryID=Integer.parseInt(request.getParameter("category"));
        int sub_categoryID=Integer.parseInt(request.getParameter("sub_category"));
        int feature_raw=Integer.parseInt(request.getParameter("feature"));
        boolean feature=(feature_raw==1);
        String postDetail = request.getParameter("content");
        CategoryDAO categoryDAO=new CategoryDAO();
        PostSubCategory postCategory = categoryDAO.getPostSubCategory(sub_categoryID);
        
        //get image
         response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        final PrintWriter writer = response.getWriter();
         String path = getFolderUploadPath();
        Part filePart = request.getPart("thumbnail");
        String fileName = getFileName(filePart);
        fileName = "images/post-image/images/post-thumbnail/" + fileName;
        try {
        File f = new File(path + fileName);
        OutputStream output = new FileOutputStream(f);
        InputStream filecontent = filePart.getInputStream();
            
         int read = 0;
                    final byte[] bytes = new byte[1024];

                    while ((read = filecontent.read(bytes)) != -1) {
                        output.write(bytes, 0, read);
                    }
               
        } catch (Exception e) {
            System.out.println(e);
        }
        Post post=new Post(account.getUser_id(), fileName, title, postCategory , postDetail, feature);
        PostDAO postDAO=new PostDAO();
        boolean check = postDAO.InsertPost(post);
        PrintWriter out = response.getWriter();
        if(check){//insert success
              out.println("<script type=\"text/javascript\">");
            out.println("alert('Successfully!');");
            out.println("window.location.href = \"http://localhost:8080/swp/marketing/postslist\";");
            out.println("</script>");
        }
        else{
                                   out.println("<script type=\"text/javascript\">");
            out.println("alert('failed!');");
            out.println("window.location.href = \"http://localhost:8080/swp/marketing/addpost\";");
            out.println("</script>");
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
