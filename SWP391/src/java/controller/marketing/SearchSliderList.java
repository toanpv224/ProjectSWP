/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.SliderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Slider;

/**
 *
 * @author Admin
 */
public class SearchSliderList extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null) { //have  not login
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SliderDAO sliderDAO = new SliderDAO();
        String word = request.getParameter("search");
        int option = Integer.parseInt(request.getParameter("option_search"));
        int featureID = Integer.parseInt(request.getParameter("feature"));
        int currentPage = Integer.parseInt(request.getParameter("page"));
        int numper_page = 6;
        int maxPage = sliderDAO.countSliderPaging(word, option, featureID, 6); //num of max page 6 post per page
        List<Slider> sliders = sliderDAO.getSliders(word, option, featureID, currentPage, numper_page);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String content = "";//contain htm; of list slider
        content += "<div class=\"row navbar-content\">\n"
                + "                               <div class=\"col-2\">ID</div>\n"
                + "                               <div class=\"col-4\">Title</div>\n"
                + "                               <div class=\"col-2\">Backlink</div>\n"
                + "                               <div class=\"col-2\">Status</div>\n"
                + "                               <div class=\"col-2\">Action </div>\n"
                + "                           </div>\n"
                + "                              <!--list-item-->\n"
                + "                            <div class=\"list-post_container \">\n"
                + "                                <!--a post-->\n"
                + "                                <ul class=\"list-post\" id=\"listpost\">";
        for (Slider s : sliders) {
            content += " <li>\n"
                    + "                                <div class=\"row item-detail\">\n";
            content += "<div class=\"col-1\">";
            content += s.getId();
            content += "</div>\n"
                    + "                                    <div class=\"col-5 title-item\">\n"
                    + "                                        <div class=\"col-4 image-item\">";
            content += "<img src=\"../";
            content += s.getImagePath();
            content += "\" />\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"col-8 title\">\n"
                    + "                                            <p class=\"title-detail\">\n";
            content += s.getTitle();
            content += "</p>\n"
                    + "                                        </div>\n"
                    + "                                    </div>\n"
                    + "                                               <div class=\"col-2\"><a href=\"";
            content += s.getUrl();
            content += "\">";
            content += s.getUrl();
            content += "</a></div>\n"
                    + "                                    <div class=\"col-2\">\n"
                    + "                                        <select id=\"feature_item\" style=\" width: 65%; height: 40px;font-size: 13px;\" onchange=\"ChangeFeature(";
            content += s.getId();
            content += ",$(this).children('option:selected').val())\">\n"
                    + "                                            <option value=\"1\" style=\"font-size: 13px;\"";
            if (s.getStatus() == 1) {
                content += "selected";
            }
            content += ">";
            content += "  Show \n"
                    + "                                            </option>\n"
                    + "                                            <option value=\"0\" style=\"font-size: 13px;\" ";
            if (s.getStatus() == 0) {
                content += "selected";
            }
            content += " >\n"
                    + "                                                Hide\n"
                    + "                                            </option>\n"
                    + "                                        </select>\n"
                    + "                                    </div>\n"
                    + "                                    <div class=\"col-2 action-container\">\n"
                    + "                                      <a type=\"button\" class=\" view-btn btn btn-primary btn-sm \" href=\"slider?id=" + s.getId() + "\">\n"
                    + "                                                                        <i class=\"fa-solid fa-eye\"></i>View more</a>"
                    + "                                    </div>\n"
                    + "                                </div>\n"
                    + "                                    </li>   ";

        }
        content += " </ul>\n"
                + "                           </div>\n"
                + "                              <!--//footer-->\n"
                + "                              <footer>\n"
                + "                                  <div class=\"pagination\">\n"
                + "                                                 <div class=\"content-paging content-paging-footer\" name=\"page\">";
        //Convert Page to html
        content
                += "                                </ul>\n"
                + "                           </div>\n"
                + "                              <!--//footer-->\n"
                + "                              <footer>\n"
                + "                                  <div class=\"pagination\">\n"
                + "                                                 <div class=\"content-paging content-paging-footer\" name=\"page\">\n"
                + "                                                     <div class=\"title-paging\"> <span>Page "
                + currentPage
                + " of "
                + maxPage
                + "<span></div>\n"
                + "                                                     <nav class=\"\" aria-label=\"...\">\n"
                + "                                                        <ul class=\"pagination\">\n";
        if (currentPage != 1) {//currentpage in interval page
            String previos = "    <li class=\"page-item\" >\n"
                    + "                                            <a class=\"page-link\"  onclick=\"Paging("
                    + (currentPage - 1)
                    + ")\" >Previous</a>\n"
                    + "                                            </li>";
            content += previos;
        } else {
            String previos = "  <li class=\"page-item disabled\">\n"
                    + "                                           <a class=\"page-link\" >Previous</a>\n"
                    + "                                         </li>";
            content += previos;
        }

        content
                += "                                            <select class=\"select-paginate\" id=\"paging\" onchange=\"SubmitForm($('#paging').children('option:selected').val())\">\n";
        for (int i = 1; i <= maxPage; i++) {
            String tmp = "";

            tmp = "<option value=\""
                    + i
                    + "\"";
            content += tmp;

            if (currentPage == i) {//match currenpage
                content += "selected";
            }
            tmp = " >"
                    + i
                    + "</option>";
            content += tmp;
        }
        content += "</select>";

        if (currentPage != maxPage) {//currentpage in interval page
            String next = "    <li class=\"page-item\" >\n"
                    + "                                            <a class=\"page-link\"  onclick=\"Paging("
                    + (currentPage + 1)
                    + ")\" >Next</a>\n"
                    + "                                            </li>";
            content += next;
        } else {
            String next = "  <li class=\"page-item disabled\">\n"
                    + "                                           <a class=\"page-link\" >Next</a>\n"
                    + "                                         </li>";
            content += next;
        }
        content += "</ul>\n"
                + "                                                      </nav>\n"
                + "                                               </div>\n"
                + "                                  </div>\n"
                + "                              </footer>\n"
                + "                            \n"
                + "                       </div>";

        out.println(content);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
