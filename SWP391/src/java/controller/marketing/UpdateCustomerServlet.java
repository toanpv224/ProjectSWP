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
import model.HistoryProfile;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateCustomerServlet", urlPatterns = {"/marketing/updatecustomer"})
public class UpdateCustomerServlet extends HttpServlet {

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
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        if(acc==null){
            processRequest(request, response);
        }
             //get information
            int user_id=Integer.parseInt(request.getParameter("id"));
            String name=request.getParameter("name");
            int gender_raw=Integer.parseInt(request.getParameter("gender"));
            boolean gender=(gender_raw==1);
            String phone=request.getParameter("phone");
            String address=request.getParameter("address");
            int feature_raw=Integer.parseInt(request.getParameter("status"));
            boolean feature=(feature_raw==1);
             //update information
            AccountDAO ac=new AccountDAO();
             Account account = ac.getAccountByID(user_id);
            account.setFull_name(name);
            account.setGender(gender);
            account.setPhone(phone);
            account.setAddress(address);
            account.setPhone(phone);
            account.setFeature(feature);
            AccountDAO accountDAO=new AccountDAO();
            //update account
            accountDAO.updateCustomer(account, acc.getUsername());
             response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String content="Update successfully!";
        out.println(content);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
