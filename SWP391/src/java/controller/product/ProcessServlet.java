/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.product;

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
import model.Product;

@WebServlet(name = "ProcessServlet", urlPatterns = {"/process"})
public class ProcessServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Process1Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Process1Servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
            contentCart = "<" + user_id + ">";
            contentCart = contentCart + items.get(0).getProduct().getProduct_id() + ":"
                    + items.get(0).getQuantity(); //note first item
            for (int i = 1; i < items.size(); i++) {//note first item continue item
                contentCart = contentCart + "," + items.get(i).getProduct().getProduct_id() + ":"
                        + items.get(i).getQuantity();
            }
        }
        return contentCart;
    }

    /*get update cart*/
    public static Cart getUpdateCart(Cart cart, ProductDAO productDAO, int productID, int num) {
        try {
            Product p = productDAO.getProduct(productID);
            int numStore = p.getUnit_in_stock();
            if (num == -1 && (cart.getQuantityById(productID) <= 1)) {// -1 item and item in cart <=1--> delete
                cart.removeItem(productID);
            } else {
                if (num == 1 && cart.getQuantityById(productID) >= numStore) { //quantity in cart greeater than in stock
                    num = 0;
                }
                double price = p.getSale_price();
                Item t = new Item(p, num, price);
                cart.addItem(t);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return cart;
    }

    public static Cart deleteItem(Cart cart, ProductDAO productDAO, int productID) {
        try {
            Product p = productDAO.getProduct(productID);
            cart.removeItem(productID);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        String num_raw = request.getParameter("num");
        String id_raw = request.getParameter("id");
        int id = 0;   //save id modification
        int num = 0; //save number item modification
        Cart updateCart;
        try {
            id = Integer.parseInt(id_raw);
            num = Integer.parseInt(num_raw);
            updateCart = getUpdateCart(cart, productDAO, id, num);
            newContentCookie += getUpdateCartContent(userID, updateCart);
        } catch (Exception e) {
            System.out.println(e);
        }
        Cookie c = new Cookie("cart", newContentCookie);
        c.setMaxAge(20*365 * 24 * 60 * 60);//set cookie in 20 year
        response.addCookie(c);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cartdetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession(true);
        Account account = (Account) session.getAttribute("account");
        int userID;
        if (account == null) {//role:guest
            userID = -1;
        } else {  //role:user
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
        String id_raw = request.getParameter("id");
        int productID = 0;   //save id product need delete
        Cart updateCart;
        try {
            productID = Integer.parseInt(id_raw);
            updateCart = deleteItem(cart, productDAO, productID);
            newContentCookie += getUpdateCartContent(userID, updateCart);
        } catch (Exception e) {
            System.out.println(e);
        }
        Cookie c = new Cookie("cart", newContentCookie);
        c.setMaxAge(20*365 * 24 * 60 * 60);//set cookie in 20 year
        response.addCookie(c);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cartdetail.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
