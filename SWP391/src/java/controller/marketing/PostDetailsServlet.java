/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.CategoryDAO;
import dal.PostCategoryDAO;
import dal.PostDAO;
import dal.PostSubcategoryDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
public class PostDetailsServlet extends HttpServlet {

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
            out.println("<title>Servlet PostDetailsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostDetailsServlet at " + request.getContextPath() + "</h1>");
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
        PostDAO postDAO = new PostDAO();
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
        PostSubcategoryDAO postSubcategoryDAO = new PostSubcategoryDAO();
        String id_raw = request.getParameter("id");
        String action = request.getParameter("action");
        int id;
        try {
            id = Integer.parseInt(id_raw);
            Post post = postDAO.getPostWithSubCategory(id);
            List<PostCategory> postCategoryList = postCategoryDAO.getPostCategorys();
            List<PostSubCategory> postSubcategoryList = postSubcategoryDAO.getPostSubCategorysByCategory(post.getPostSubCategory().getCategory().getCategory_id());
            request.setAttribute("postCategoryList", postCategoryList);
            request.setAttribute("postSubcategoryList", postSubcategoryList);
            request.setAttribute("post", post);
            if (action == null) {
                System.out.println("a");
            } else {

                if (action.equalsIgnoreCase("edit")) {
                    request.setAttribute("edit", true);
                } else {
                    request.setAttribute("view", true);
                }
            }
            request.getRequestDispatcher("/marketing/postdetails.jsp").forward(request, response);
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
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        String postId_raw = request.getParameter("postId");
        PostDAO postDAO = new PostDAO();
        int postId;
        try {
            postId = Integer.parseInt(postId_raw);

            switch (action) {
                case "update":
                    String title = request.getParameter("title");
                    String subTitle = request.getParameter("subtitle");
                    String cate_raw = request.getParameter("categoryId");
                    String subCategoryId_raw = request.getParameter("subCategoryId");
                    String featured_raw = request.getParameter("featured");
                    String postContent = request.getParameter("postContent");

                    int subCate = Integer.parseInt(subCategoryId_raw);
                    boolean featured;
                    if (featured_raw == null) {
                        featured = false;
                    } else {
                        featured = true;
                    }

                    postDAO.updatePost(title, postContent, featured, subTitle, subCate, postId);
                    PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
                    PostSubcategoryDAO postSubcategoryDAO = new PostSubcategoryDAO();

                    Post post = postDAO.getPostWithSubCategory(postId);
                    List<PostCategory> postCategoryList = postCategoryDAO.getPostCategorys();
                    List<PostSubCategory> postSubcategoryList = postSubcategoryDAO.getPostSubCategorysByCategory(post.getPostSubCategory().getCategory().getCategory_id());
                    request.setAttribute("postCategoryList", postCategoryList);
                    request.setAttribute("postSubcategoryList", postSubcategoryList);
                    request.setAttribute("post", post);
                    request.getRequestDispatcher("/marketing/postdetails.jsp").forward(request, response);
                    break;
                case "changeThumbnail":
                    String path = getFolderUploadPath();
                    final Part filePart = request.getPart("thumbnail");
                    String fileName = getFileName(filePart);
//            
                    Random r = new Random();
                    if (!fileName.equals("")) {
                        while (postDAO.checkThumbnailExist(fileName)) {
                            fileName = r.nextInt(10) + fileName;
                        }
                        fileName = "images/post-thumbnails/" + fileName;
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
                            File file = new File(path + postDAO.getPostWithSubCategory(postId).getThumbnail());
                            postDAO.changeThumbnail(postId, fileName);
                            TimeUnit.SECONDS.sleep(2);
                            Post p = postDAO.getPostWithSubCategory(postId);
                            out.print(p.getThumbnail());
                            if (file.exists()) {
                                while (!file.delete()) {
                                }
                                System.out.println("deleted: " + path + postDAO.getPostWithSubCategory(postId).getThumbnail());
                            }
                        } catch (FileNotFoundException fne) {
                            writer.println("<br/> ERROR: " + fne.getMessage());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    break;
            }
        } catch (IOException | NumberFormatException | ServletException e) {
            System.out.println(e);
        }
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

    private String getFolderUploadPath() {
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
