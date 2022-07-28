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
import javax.servlet.http.HttpSession;
import model.Account;
import model.Customer;

/**
 *
 * @author win
 */
@WebServlet(name = "SearchCustomerList", urlPatterns = {"/marketing/searchcustomerlist"})
public class SearchCustomerList extends HttpServlet {

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
        AccountDAO accountDAO = new AccountDAO();
        String word = request.getParameter("search");
        int option = Integer.parseInt(request.getParameter("option_search"));
        int featureID = Integer.parseInt(request.getParameter("feature"));
        int currentPage = Integer.parseInt(request.getParameter("page"));
        int orderID = Integer.parseInt(request.getParameter("orderID"));
        int op=Integer.parseInt(request.getParameter("op"));
        
        int numper_page = 6;
        int maxPage = accountDAO.countCustomerPaging(word, option, featureID, 6); //num of max page 6 post per page 
        List<Customer> customers = accountDAO.getCustomers(word, option, featureID, orderID,op, currentPage, numper_page);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String content = "";//contain htm; of list customer
        content += " <div class=\"list-post_container \">\n"
                + "                                <!--a post-->\n"
                + "                                <ul class=\"list-post\" id=\"listpost\"style=\"list-style-type: none; padding-left: 20px; margin-top: 50px;\">";
        for (Customer customer : customers) {
            content += " <li>\n"
                    + "                                <div class=\"row item-detail\">\n"
                    + "                                    <div class=\"col-1 item\">\n"
                    + customer.getUser_id()
                    + "\n"
                    + "                                    </div>\n"
                    + "                                    <div class=\"col-2 item title-item\">\n"
                    + "                                            <p class=\"title-detail\">\n"
                    + customer.getFull_name()
                    + "                                            </p>\n"
                    + "                                    </div>";

            content += "<div class=\"col-1 item gender-item\">\n"
                    + "                                            <p class=\"title-detail\">";
            if (customer.isGender()) {
                content += "Male";
            } else {
                content += "Female";
            }
            content += " </p>\n"
                    + "                                    </div>\n"
                    + "                                    <div class=\"col-3 item email-item\">\n"
                    + "                                            <p class=\"title-detail\">\n"
                    + "                                                "
                    + customer.getEmail()
                    + "\n"
                    + "                                            </p>\n"
                    + "                                    </div>\n"
                    + "                                    <div class=\"col-2 item mobile-item\">\n"
                    + "                                            <p class=\"title-detail\">\n"
                    + "                                                "
                    + customer.getPhone()
                    + "\n"
                    + "                                            </p>\n"
                    + "                                    </div>";
            content += "<div class=\"col-1 item id-item \">\n"
                    + "                                        <select id=\"feature_item\" style=\" width: 90%; height: 40px;font-size: 13px;\" onchange=\"ChangeFeature("
                    + customer.getUser_id()
                    + ",$(this).children('option:selected').val())\">\n"
                    + "                                            <option value=\"1\" style=\"font-size: 13px;\"";

            if (customer.isFeature()) {
                content += "selected";
            }
            content += ">";
            content += "  Active \n"
                    + "                                            </option>\n"
                    + "                                            <option value=\"0\" style=\"font-size: 13px;\" ";
            if (!customer.isFeature()) {
                content += "selected";
            }
            content += " >\n"
                    + "                                                Inactive\n"
                    + "                                            </option>\n"
                    + "                                        </select>\n"
                    + "                                    </div>\n"
                    + "                                    <div class=\"col-2 action-container\">\n"
                    + "                                      <a type=\"button\" class=\" view-btn btn btn-primary btn-sm \" href=\"slider?" + customer.getUser_id() + "\">\n"
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

        out.print(content);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
