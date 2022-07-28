/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.AccountDAO;
import dal.CategoryDAO;
import dal.PostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Post;
import model.PostCategory;
import model.ProductCategory;

/**
 *
 * @author Admin
 */
public class SearchPostListServlet extends HttpServlet {

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
//        HttpSession session = request.getSession();
//        Account account = (Account) session.getAttribute("account");
//        if(account==null){ //have  not login
//            processRequest(request, response);
//        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pagingHTML="";
          String word=request.getParameter("search");
        int sub_categoryID=Integer.parseInt(request.getParameter("sub_category"));
        int categoryID=Integer.parseInt(request.getParameter("category"));
        int authorID=Integer.parseInt(request.getParameter("author"));
        int featureID=Integer.parseInt(request.getParameter("feature"));
//        int sortID=Integer.parseInt(request.getParameter("sort"));
        int currentPage=Integer.parseInt(request.getParameter("page"));
        int numper_page=6;
        PostDAO postDAO=new PostDAO();
        int maxPage=postDAO.countPostPaging(word,categoryID, sub_categoryID, authorID, featureID, numper_page); //num of max page 6 post per page
        
        response.setContentType("text/html;charset=UTF-8");
        //Convert Page to html
             pagingHTML+= 

"                                  <div class=\"pagination\">\n" +
"                                                 <div class=\"content-paging content-paging-footer\" name=\"page\">\n" +
"                                                     <div class=\"title-paging\"> <span>Page "
                     + currentPage
                     + " of "
                     + maxPage
                     + "<span></div>\n" +
"                                                     <nav class=\"\" aria-label=\"...\">\n" +
"                                                        <ul class=\"pagination\">\n";
                    if(currentPage!=1){//currentpage in interval page
                        String previos="    <li class=\"page-item\" >\n" +
"                                            <a class=\"page-link\"  onclick=\"Paging("
                                + (currentPage-1)
                                + ")\" >Previous</a>\n" +
"                                            </li>";
                        pagingHTML+=previos;
                    }else{
                        String previos="  <li class=\"page-item disabled\">\n" +
"                                           <a class=\"page-link\" >Previous</a>\n" +
"                                         </li>";
                        pagingHTML+=previos;
                    }
                    
                    pagingHTML+=
"                                            <select class=\"select-paginate\" id=\"paging\" onchange=\"SubmitForm($('#paging').children('option:selected').val())\">\n";
                                            for (int i = 1; i <=maxPage; i++) {
                                                String tmp="";
                                                
                                              tmp="<option value=\""
                                                      + i
                                                      + "\"";
                                              pagingHTML+=tmp;
                                              
                                              if(currentPage==i){//match currenpage
                                                  pagingHTML+="selected";
                                              }
                                               tmp=" >"
                                                       + i
                                                       + "</option>"; 
                                               pagingHTML+=tmp;
                                    }
                                               pagingHTML+="</select>";
                                                         
                     if(currentPage!=maxPage){//currentpage in interval page
                        String next="    <li class=\"page-item\" >\n" +
"                                            <a class=\"page-link\"  onclick=\"Paging("
                                + (currentPage+1)
                                + ")\" >Next</a>\n" +
"                                            </li>";
                        pagingHTML+=next;
                    }else{
                        String next="  <li class=\"page-item disabled\">\n" +
"                                           <a class=\"page-link\" >Next</a>\n" +
"                                         </li>";
                        pagingHTML+=next;
                    }
                 pagingHTML+=   "</ul>\n" +
"                                                      </nav>\n" +
"                                               </div>\n" +
"                                  </div>\n" +

"                            \n";
                 out.print(pagingHTML);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           HttpSession session = request.getSession();
//        Account account = (Account) session.getAttribute("account");
//        if(account==null){ //have  not login
//            processRequest(request, response);
//        }
        String word=request.getParameter("search");
        int sub_categoryID=Integer.parseInt(request.getParameter("sub_category"));
        int categoryID=Integer.parseInt(request.getParameter("category"));
        int authorID=Integer.parseInt(request.getParameter("author"));
        int featureID=Integer.parseInt(request.getParameter("feature"));
        int sortID=Integer.parseInt(request.getParameter("sort"));
        int currentPage=Integer.parseInt(request.getParameter("page"));
        int op=Integer.parseInt(request.getParameter("op"));
        int numper_page=6;
        PostDAO postDAO=new PostDAO();
        List<Post> posts = postDAO.getPosts(word,categoryID,sub_categoryID, authorID, featureID, sortID,op, currentPage,numper_page); //get all product for page 1
//        CategoryDAO categoryDAO=new CategoryDAO();
//        List<PostCategory> postCategory = categoryDAO.getPostCategory();
//        AccountDAO accountDAO=new AccountDAO();
//        List<Account> authors = accountDAO.getAuthors();
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String postHTML="";//contain htm; of list post
         
        for (Post p : posts) {
            postHTML+=" <tr>\n" +
"                                                            <td>"
                    + p.getPost_id()
                    + "</td>\n" +
"                                                            <td class=\"w-50\">\n" +
"                                                                <div class=\"row\">\n" +
"                                                                    <div class=\"col-3 image-item\">\n" +
"                                                                        <a href=\"postdetails?id="
                    + p.getPost_id()
                    + "\">\n" +
"\n" +
"                                                                            <img src=\"../"
                    + p.getThumbnail()
                    + "\" />\n" +
"                                                                        </a>\n" +
"                                                                    </div>\n" +
"                                                                    <div class=\"col-9 title\">\n" +
"                                                                        <p class=\"title-detail\">\n" +
"                                                                            "
                    + p.getTitle()
                    + "\n" +
"                                                                        </p>\n" +
"                                                                    </div>\n" +
"                                                                </div>\n" +
"                                                            </td>\n" +
"                                                            <td>"
                    + p.getAuthor()
                    + "</td>";
                    postHTML+=" <td>\n" +
"                                                                <select class=\"form-select form-select-sm\" id=\"feature_item\" style=\" width: 65%; height: 40px;font-size: 13px;\" onchange=\"ChangeFeature("
                            + p.getPost_id()
                            + ", $(this).children('option:selected').val())\">\n" +
"                                                                    <option value=\"1\" style=\"font-size: 13px;\" ";
                            if(p.isFeatured()){
                                postHTML+="selected";
                            }
                            postHTML += ">Show</option>\n";
                            postHTML+=" <option value=\"0\" style=\"font-size: 13px;\"";
                            if(!p.isFeatured()){
                                postHTML+="selected";
                            }
                            postHTML+=">Hide</option>";
                            postHTML+=" </select>\n" +
"                                                                </td>\n" +
"                                                                <td class=\"col-2 action-container\">\n" +
"                                                                    <button type=\"button\" class=\"edit-btn btn btn-secondary btn-sm \">\n" +
"                                                                        <i class=\"fa-solid fa-pen-to-square\"></i>";
                            postHTML+="<a href=\"postdetails?id="
                                    + p.getPost_id()
                                    + "&action=edit\">Edit</a>\n" +
"                                                                </button>\n" +
"                                                                <button type=\"button\" class=\" view-btn btn btn-primary btn-sm \">\n" +
"                                                                    <i class=\"fa-solid fa-eye\"></i><a href=\"postdetails?id="
                                    + p.getPost_id()
                                    + "&action=view\">View</a></button>\n" +
"                                                            </td>\n" +
"                                                        </tr>   ";
        }
                
                 out.println(postHTML);
        }

  
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
