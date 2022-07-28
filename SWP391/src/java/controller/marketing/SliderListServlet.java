/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.CategoryDAO;
import dal.PostDAO;
import dal.SliderDAO;
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
import model.Slider;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SliderListServlet", urlPatterns = {"/marketing/sliderslist"})
public class SliderListServlet extends HttpServlet {
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
//        if(account==null){ //have  not login
//            processRequest(request, response);
//        }
         int currentPage=1;//start page=1
         int numPerPage=6;
         SliderDAO sliderDAO=new SliderDAO();
        List<Slider> sliders = sliderDAO.getSliders("", 1, -1, currentPage, numPerPage); //dafault page 1 and all status
        int maxPage= sliderDAO.countSliderPaging("", 1, -1, numPerPage); //dafault page 1 and all status
         request.setAttribute("curpage", currentPage);
         request.setAttribute("maxpage",maxPage);
         request.setAttribute("sliders", sliders);
         request.getRequestDispatcher("/marketing/sliderlist.jsp").forward(request, response);
    }

  
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
