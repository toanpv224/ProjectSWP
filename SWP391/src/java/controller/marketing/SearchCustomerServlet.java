/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.HistoryProfile;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SearchCustomerServlet", urlPatterns = {"/marketing/searchcustomer"})
public class SearchCustomerServlet extends HttpServlet {

   //Convert content to html
    public String getContent(List<HistoryProfile> histories) {
        String content = "";
        for (HistoryProfile h : histories) {
            content += "                                                <tr>\n"
                    + "                                                            <td>"
                    + h.getFull_name()
                    + "</td>\n"
                    + "                                                            <td class=\"\">\n"
                    + h.getEmail()
                    + ""
                    + "\n"
                    + "                                                            </td>\n"
                    + "                                                            <td>\n";
            if (h.isGender()) {
                content += "Male";
            } else {
                content += "Female";
            }
            content
                    += "                                                                </td>\n"
                    + "                                                                <td >\n"
                    + "                                                                  "
                    + h.getPhone()
                    + "\n"
                    + "                                                                 </td>\n"
                    + "                                                                <td >\n"
                    + "                                                                  "
                    + h.getAddress()
                    + "\n"
                    + "                                                                 </td>\n"
                    + "                                                                <td >\n"
                    + "                                                                  "
                    + h.getUpdate_by()
                    + "\n"
                    + "                                                                 </td>\n"
                    + "                                                                <td >\n"
                    + "                                                                  "
                    + h.getUpdate().getDate()
                    + "\n"
                    + "                                                                </td>\n"
                    + "                                                        </tr>   ";
        }
        content += "";
        return content;
    }
   
//Convert Page to html
    public String getPaging(int maxPage,int currentPage){
        String pagingHTML="";    
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
                 return pagingHTML;
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchCustomerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchCustomerServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //        HttpSession session = request.getSession();
//        Account account = (Account) session.getAttribute("account");
//        if(account==null){ //have  not login
//            processRequest(request, response);
//        }
              int user_id = Integer.parseInt(request.getParameter("id"));
        AccountDAO ac = new AccountDAO();
        int numper_page = 6;
        int currentPage = Integer.parseInt(request.getParameter("page"));
         int maxPage=ac.countPagingHistories(user_id, currentPage, numper_page);
        List<HistoryProfile> histories = ac.getHistory_profiles(user_id, currentPage, numper_page);//default
        String content = getPaging(maxPage, currentPage);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(content);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //        HttpSession session = request.getSession();
//        Account account = (Account) session.getAttribute("account");
//        if(account==null){ //have  not login
//            processRequest(request, response);
//        }
        int user_id = Integer.parseInt(request.getParameter("id"));
        AccountDAO ac = new AccountDAO();
        int numper_page = 6;
        int currentPage = Integer.parseInt(request.getParameter("page"));
        List<HistoryProfile> histories = ac.getHistory_profiles(user_id, currentPage, numper_page);//default
        String content = getContent(histories);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(content);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
