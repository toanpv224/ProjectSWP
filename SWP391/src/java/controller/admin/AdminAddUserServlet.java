/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import static controller.authentication.RegisterServlet.getMd5;
import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

/**
 *
 * @author tretr
 */
public class AdminAddUserServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String fullname = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String mail = request.getParameter("mail");
        String phone = request.getParameter("phone");
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String re_pass = request.getParameter("repass");
        String role = request.getParameter("role");
        
        //Create Hash code which helps in Activation link
        String myHash;
        Random random = new Random();
        random.nextInt(9999999);
        myHash = getMd5("" + random);

        //Add to bean
        AccountDAO dao = new AccountDAO();
        Account a = new Account();
        a.setFull_name(fullname);
        if (gender.endsWith("1")) {
        a.setGender(true);
        }else{
        a.setGender(false);
        }
        a.setEmail(mail);
        a.setPhone(phone);
        a.setAddress(address);
        a.setUsername(user);
        a.setMyHash(myHash);
        a.setRole_id(Integer.parseInt(role));
        request.setAttribute("acc", a);

        if (!pass.equals(re_pass)) {
            request.setAttribute("signmess", "Password does not match!");
            request.getRequestDispatcher("userslist").forward(request, response);
        } else if (dao.checkAccountExist(user) != null) {
            request.setAttribute("signmess", "Username already used !");
            request.getRequestDispatcher("userslist").forward(request, response);
        } else if (dao.checkEmailExist(mail) != null) {
            request.setAttribute("signmess", "Email already used !");
            request.getRequestDispatcher("userslist").forward(request, response);
        } else if (pass.length() < 8) {
            request.setAttribute("signmess", "Password must contain 8 characters !");
            request.getRequestDispatcher("userslist").forward(request, response);
        } else {
            a.setPassword(getMd5(pass));
            //dc signup
            String str = dao.singup(a,1);
            response.sendRedirect("userslist");
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
        processRequest(request, response);
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
