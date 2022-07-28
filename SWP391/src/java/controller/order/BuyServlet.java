
package controller.order;

import controller.product.*;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Cart;
import model.Product;


@WebServlet(name = "BuyServlet", urlPatterns = {"/buy"})
public class BuyServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Buy1Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Buy1Servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    //Handle content assign item in correct cart
    public static String handleCookieContent(String txt, int user_id, String id, String num) {
        String detailCart = "";
        String[] splitField = txt.split("<"); //split cookie into cart
        List<String> listCart = new ArrayList<>();
        for (String splitField1 : splitField) {//split each cart into id>item:quantity
            String[] field = splitField1.split(">");
            for (String field1 : field) { //add each cart conresspond id into id and cart detail
                listCart.add(field1);
            }
        }
        boolean matched = false;
        for (int i = 0; i < listCart.size(); i++) {
            if (i % 2 == 1) {   //contain user_id 
                if (Integer.parseInt(listCart.get(i)) == user_id) { // cart match user_id conresponding
                    matched = true;
                    String cartDetail = listCart.get(i + 1);    //content cart detail user conrestponding
                    if (cartDetail.isEmpty()) { //cart empty
                        detailCart = "<" + user_id + ">" + id + ":" + num; //a script cookie like <user_id>id:quantity
                    } else {
                        detailCart += "<" + listCart.get(i) + ">";
                        detailCart += listCart.get(i + 1);
                        detailCart = detailCart + "," + id + ":" + num;
                    }
                } else {//other id
                    detailCart += "<" + listCart.get(i) + ">";
                    detailCart += listCart.get(i + 1);
                }

            }

        }
        if (matched == false) {//not has cart in cookie
            detailCart += "<" + user_id + ">";
            detailCart = detailCart + id + ":" + num;
        }
        return detailCart;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
            ProductDAO productDAO = new ProductDAO();
        String num = request.getParameter("quantity");
        String id = request.getParameter("id");
           //if product sold redirect product details 
        try {
        Product product = productDAO.getProduct(Integer.parseInt(id));
            System.out.println(product.getUnit_in_stock());
        if(product.getUnit_in_stock()==0){//unit in stock of product=0
           response.sendRedirect("product?id="+id);
           return;
        }
        } catch (Exception e) {
            System.out.println(e);
        }
        HttpSession session = request.getSession(true);
        Account account = (Account) session.getAttribute("account");
        int userID;
        if (account == null) { //role:guest
            userID = -1;
        } else {  //role:user
            userID = account.getUser_id();
        }
        List<Product> allproduct = productDAO.getAllProducts();
        Cookie[] arr = request.getCookies();  //get cookie in browsing
        String cookieContent = "";
        //convert cart content from cookie
        if (arr != null) {//exist cookie
            for (Cookie cookie : arr) {
                if (cookie.getName().equals("cart"))//cookie name cart matched
                {
                    cookieContent += cookie.getValue();
                    cookie.setMaxAge(0);//remove cookie
                    response.addCookie(cookie);
                }
            }
        }
        String newCookieContent;
        newCookieContent = handleCookieContent(cookieContent, userID, id, num);
        //add cart content to cookie
        Cookie c = new Cookie ("cart", newCookieContent);
        c.setMaxAge(20*365 * 24 * 60 * 60);//COOKIE exist 20 year
        response.addCookie(c);
        response.sendRedirect("showcart");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
