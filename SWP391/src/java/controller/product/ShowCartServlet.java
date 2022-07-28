/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.product;

import static controller.product.ProcessServlet.getUpdateCart;
import static controller.product.ProcessServlet.getUpdateCartContent;
import static controller.product.ProcessServlet.separateCookieContent;
import dal.OrderDAO;
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
import model.Item;
import model.Order;
import model.Product;

/**
 *
 * @author win
 */
@WebServlet(name = "ShowCartServlet", urlPatterns = {"/showcart"})
public class ShowCartServlet extends HttpServlet {

    /*separate carts other this cart*/
    public static String separateCookieContent(String txt, int user_id) {
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

    /*get update cart content*/
    public static String getUpdateCartContent(int user_id, Cart cart) {
        List<Item> items = cart.getItems();
        String contentCart = "";
        if (items.size() > 0) {//cart has item
            int quantity = items.get(0).getQuantity();
            if (quantity > (items.get(0)).getProduct().getUnit_in_stock()) {//quantity  in cart > quantity in stock
                quantity = (items.get(0)).getProduct().getUnit_in_stock();
            }
            contentCart = "<" + user_id + ">";
            contentCart = contentCart + items.get(0).getProduct().getProduct_id() + ":"
                    + quantity; //note first item
            for (int i = 1; i < items.size(); i++) {//note first item continue item
                quantity = items.get(i).getQuantity();
                if (quantity > (items.get(i)).getProduct().getUnit_in_stock()) {//quantity  in cart > quantity in stock
                    quantity = (items.get(i)).getProduct().getUnit_in_stock();
                }
                contentCart = contentCart + "," + items.get(i).getProduct().getProduct_id() + ":"
                        + quantity;
            }
        }
        return contentCart;
    }

    /*Check valid item quantity*/
    public static boolean checkValidItem(Cart cart) {
        for (Item item : cart.getItems()) {
            if (item.getQuantity() > item.getProduct().getUnit_in_stock()) {// quantity of item > quantity in stocl
                return false;
            }
        }
        return true;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("utf-8");
            HttpSession session = request.getSession(true);
            Account account = (Account) session.getAttribute("account");
            int userID;
            if (account == null) {    //role:guest
                userID = -1;
            } else {
                userID = account.getUser_id();
            }
            ProductDAO productDAO = new ProductDAO();
            List<Product> allproduct = productDAO.getAllProducts();
            Cookie[] arr = request.getCookies();  //get cookie in browsing
            String contentCookie = "";
            if (arr != null) {//exist cookie
                for (Cookie cookie : arr) {
                    if (cookie.getName().equals("cart"))//cookie name cart
                    {
                        contentCookie += cookie.getValue();
                        cookie.setMaxAge(0);//remove
                        response.addCookie(cookie);
                    }
                }
            }
            Cart cart = new Cart(contentCookie, allproduct, userID);
            String newContentCookie = ""; //equal othercarts+this cart update
            newContentCookie = separateCookieContent(contentCookie, userID);

            try {
                newContentCookie += getUpdateCartContent(userID, cart);
            } catch (Exception e) {
                System.out.println(e);
            }
            Cookie c = new Cookie("cart", newContentCookie);
            c.setMaxAge(20 * 365 * 24 * 60 * 60);//set cookie in 20 year
            response.addCookie(c);
            request.setAttribute("cart", cart);
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Some item in cart have changed');");
            out.println("</script>");
            request.getRequestDispatcher("cartdetail.jsp").forward(request, response);
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
        if (!checkValidItem(cart)) {//quantity in cart is not valid
            processRequest(request, response);
        }
        request.setAttribute("cart", cart);

        request.getRequestDispatcher("cartdetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account acocunt = (Account) session.getAttribute("account");
        OrderDAO orderDAO = new OrderDAO();
        String orderId_raw = request.getParameter("orderId");
        int orderId;
        try {
            orderId = Integer.parseInt(orderId_raw);
            Order myOrder = orderDAO.getOrderByUserIdAndOrderId(acocunt.getUser_id(), orderId);
            if (myOrder == null) {
                PrintWriter out = response.getWriter();
                out.println("access denied");
            } else {
                List<String> payments = new ArrayList<String>();
                payments.add("Ship COD");
                payments.add("BANK TRANSFER");
                payments.add("VN PAY");
                request.setAttribute("payments", payments);
                request.setAttribute("order", myOrder);
                request.getRequestDispatcher("updateorders.jsp").forward(request, response);
            }

        } catch (IOException | NumberFormatException e) {

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
