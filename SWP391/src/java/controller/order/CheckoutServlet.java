/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.order;

import controller.product.*;
import dal.OrderDAO;
import dal.ProductDAO;
import dal.SaleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
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
import model.Customer;
import model.Guest;
import model.Item;
import model.Order;
import model.Product;
import service.EmailService;
import service.EmailServiceIml;

/**
 *
 * @author win
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
    private EmailService emailService = new EmailServiceIml();
    /*separate carts other this cart*/
    public static String removeCartCookieContent(String txt, int user_id) {
        String detailCart = "";
        String[] splitField = txt.split("<"); //split cookie into cart
        List<String> listCart = new ArrayList<>();
        for (String splitField1 : splitField) {//split each cart into id>item:quantity
            String[] field = splitField1.split(">");
            for (String field1 : field) { //add each cart conresspond id into id and cart detail
                listCart.add(field1);
            }
        }

        for (int i = 0; i < listCart.size(); i++) {
            if (i % 2 == 1) {//cart id
                if (Integer.parseInt(listCart.get(i)) != user_id) {// cart  other user_id 
                    detailCart += "<" + listCart.get(i) + ">";
                    detailCart += listCart.get(i + 1);
                }
            }
        }

        return detailCart;
    }
    
       /*Check valid item quantity*/
      public static boolean checkValidItem(Cart cart){
          for (Item item : cart.getItems()) {
              if(item.getQuantity()>item.getProduct().getUnit_in_stock()){// quantity of item > quantity in stocl
                  return false;
              }
          }
          return true;
      }
              
              
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
               out.println("<script type=\"text/javascript\">");
            out.println("alert('Some item in cart have changed');");
            out.println("window.location.href = \"showcart\";");
            out.println("</script>");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession(true);
        Account account = (Account) session.getAttribute("account");
        int userID;
        if (account == null) { //role:guest
            userID = -1;
            account = new Account();
        } else {  //role:user
            userID = account.getUser_id();
        }
        ProductDAO productDAO = new ProductDAO();
        List<Product> allproduct = productDAO.getAllProducts();
        Cookie[] arr = request.getCookies();  //get cookie in browsing
        String cookieContent = "";
        if (arr != null) {//exist cookie
            for (Cookie cookie : arr) {
                if (cookie.getName().equals("cart"))//cookie name cart
                {
                    cookieContent += cookie.getValue();
                }
            }
        }

        Cart cart;
        cart = new Cart(cookieContent, allproduct, userID);
        if(cart.getItems().isEmpty()||cart.getItems().size()==0){//cart empty
            try (PrintWriter out = response.getWriter()) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Your cart have change');");
            out.println("window.location.href = \"showcart\";");
            out.println("</script>");
            return;
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        if(!checkValidItem(cart)){//cart invalid item
            processRequest(request, response);
        }
        request.setAttribute("cart", cart);
        request.setAttribute("cus", account);
        request.getRequestDispatcher("cartcontact.jsp").forward(request, response);
//        processRequest(request, response);
    }

   
    @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession(true);
        Account account = (Account) session.getAttribute("account");
        int userID;
        if (account == null) { //role:guest
            userID = -1;
            account = new Account();
        } else {  //role:user
            userID = account.getUser_id();
        }
        ProductDAO productDAO = new ProductDAO();
        List<Product> allproduct = productDAO.getAllProducts();
        Cookie[] arr = request.getCookies();  //get cookie in browsing
        String cookieContent = "";
        //convert cart content from cookie
        Cart cart = null;
        Order order=new Order();
        if (arr != null) {//exist cookie
            for (Cookie cookie : arr) {
                if (cookie.getName().equals("cart"))//cookie name cart matched
                {
                    cookieContent += cookie.getValue();
                    cart = new Cart(cookieContent, allproduct, userID);
                    if(!checkValidItem(cart)){// cart have invalid item
                     processRequest(request, response);
                     return;
                    }else{
                    cookie.setMaxAge(0);//remove cookie
                    response.addCookie(cookie);
                }
                    }
            }
        }
        //get information form
        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            int gender_raw = Integer.parseInt(request.getParameter("gender"));
            boolean gender;
            if(gender_raw==0){
                gender=false;
            }else{
                gender=true;
            }
            String note = request.getParameter("note");
            if(userID!=-1){ //role:user
                Customer customer = new Customer();
            customer.setUser_id(userID);
            customer.setFull_name(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setAddress(address);
            customer.setCity(city);
            customer.setGender(gender);
            /*remove cart after add  checkout*/
            OrderDAO oderDAO = new OrderDAO();
            order=oderDAO.addOrderUser(customer, cart, note);
            }else{
               Guest guest=new Guest(name, gender, email, phone, address, city);
               OrderDAO oderDAO = new OrderDAO();
            order=oderDAO.addOrderGuest(guest, cart, note);
            }
            String newContentCart = removeCartCookieContent(cookieContent, userID);
            Cookie c = new Cookie("cart", newContentCart);
            /*send email after checkout*/
            emailService.sendEmailComfirmOrder(getServletContext(), name, email, order.getOrder_id());
            /*assign a sale */
            SaleDAO saleDao=new SaleDAO();
            saleDao.AssignSale(order.getOrder_id());
            response.addCookie(c);
            request.setAttribute("order",order);
            request.setAttribute("cart",cart);
            request.getRequestDispatcher("cartcompletion.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
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
