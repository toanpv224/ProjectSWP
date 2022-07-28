/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing;

import dal.FeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Duy Phuong
 */
@WebServlet(name = "EditFeedbackServlet", urlPatterns = {"/marketing/editfeedback"})
public class EditFeedbackServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        boolean pos;
        
        String action = request.getParameter("action");
        String id_raw = request.getParameter("id");
        String type = request.getParameter("type");
        try {
            int id = Integer.parseInt(id_raw);
            switch (action) {
                case "showfeedback":
                    pos = type.equals("1") ? feedbackDAO.changeGeneralFeedbackStatus(id, 1) : feedbackDAO.changeProductFeedbackStatus(id, 1);
                    if(!pos) {
                        System.out.println("showfeedback fail");
                        response.sendError(1, "showfeedback fail");
                    }
                    break;
                    
                case "hidefeedback":
                    pos = type.equals("1") ? feedbackDAO.changeGeneralFeedbackStatus(id, 0) : feedbackDAO.changeProductFeedbackStatus(id, 0);
                    if(!pos) {
                        System.out.println("hide feedback fail");
                        response.sendError(1, "hide feedback fail");
                    }
                    break;
            }
        } catch (NumberFormatException nfe) {
            System.out.println(nfe);
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
