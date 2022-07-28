    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package controller.marketing;

    import dal.SliderDAO;
    import java.io.IOException;
    import java.io.PrintWriter;
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;
    import model.Account;

   
    public class UpdateFeatureSliderServlet extends HttpServlet {
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
            response.sendRedirect("sliderlist");
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            int id = Integer.parseInt(request.getParameter("slider"));
            int status = Integer.parseInt(request.getParameter("status"));
            SliderDAO sliderDAO = new SliderDAO();
            sliderDAO.updateStatusSlider(id, status);
        }

        @Override
        public String getServletInfo() {
            return "Short description";
        }// </editor-fold>

    }
