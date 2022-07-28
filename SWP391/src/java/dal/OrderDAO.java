/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Cart;
import model.Guest;
import model.Item;
import model.Order;
import model.OrderDetail;
import model.OrderInformation;
import model.OrderStatus;
import model.Product;
import util.DateTimeUtil;

/**
 *
 * @author win
 */
public class OrderDAO extends DBContext {

    private Order filOrderInfor(ResultSet rs) throws SQLException {
        OrderDAO order_dao = new OrderDAO();
        int order_id = rs.getInt("order_id");
        return new Order(order_id, rs.getInt("user_id"), rs.getString("order_date"), rs.getString("require_date"),
                rs.getString("shipped_date"), rs.getInt("ship_via"), rs.getDouble("freight"), rs.getString("ship_name"),
                rs.getString("ship_address"), rs.getBoolean("ship_gender"), rs.getString("ship_mobile"), rs.getString("ship_email"), rs.getString("ship_city"),
                rs.getInt("status"), rs.getString("note"), rs.getString("payment"), rs.getDouble("total_price"), rs.getString("sale_note"));
    }

    public Order addOrderUser(Account customer, Cart cart, String note) {
        double freight = cart.getFreight();//ship
        try {
            //add order
            String sql = "";
            sql = "insert into orders(user_id,order_date,ship_name,ship_gender,ship_address,ship_email,\n"
                    + "freight,ship_mobile,ship_city,status,note,total_price)\n"
                    + "values(?,getdate(),?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, customer.getUser_id());
            st.setString(2, customer.getFull_name());
            st.setBoolean(3, customer.isGender());
            st.setString(4, customer.getAddress());
            st.setString(5, customer.getEmail());
            st.setDouble(6, freight);
            st.setString(7, customer.getPhone());
            st.setString(8, customer.getCity());
            st.setInt(9, 1);//Default
            st.setString(10, note);
            st.setDouble(11, cart.getTotalMoney() + freight);//total=price-freight
            st.executeUpdate();
            // get id of newsest order added
            String sql1 = "select top 1 order_id from [orders] order by order_id desc";
            PreparedStatement st1 = connection.prepareStatement(sql1);
            ResultSet rs = st1.executeQuery();
            // add table order details
            int order_id = 0;
            if (rs.next()) {//have item found
                order_id = rs.getInt("order_id");
                for (Item i : cart.getItems()) {
                    String sql2 = "insert into order_details\n"
                            + "values(?,?,?,?)";
                    PreparedStatement st2 = connection.prepareStatement(sql2);
                    st2.setInt(1, order_id);
                    st2.setInt(2, i.getProduct().getProduct_id());
                    st2.setInt(3, i.getQuantity());
                    st2.setDouble(4, i.getPrice());
                    st2.executeUpdate();
                }
            }
            //update quantity product
            String sql3 = "update products set "
                    + "unit_in_stock=unit_in_stock-? where product_id=?";
            PreparedStatement st3 = connection.prepareStatement(sql3);
            for (Item i : cart.getItems()) {
                st3.setInt(1, i.getQuantity());
                st3.setInt(2, i.getProduct().getProduct_id());
                st3.executeUpdate();
            }
            //return order id
            Order order = getOrderByOrderID(order_id);
            return order;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null; //exception
    }

    public Order addOrderGuest(Guest guest, Cart cart, String note) {

        double freight = cart.getFreight();//ship
        try {
            //add order
            String sql = "";
            sql = "insert into orders(order_date,ship_name,ship_gender,ship_address,ship_email,\n"
                    + "freight,ship_mobile,ship_city,status,note,total_price)\n"
                    + "values(GETDATE(),?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, guest.getName());
            st.setBoolean(2, guest.isGender());
            st.setString(3, guest.getAddress());
            st.setString(4, guest.getEmail());
            st.setDouble(5, freight);
            st.setString(6, guest.getPhone());
            st.setString(7, guest.getCity());
            st.setInt(8, 1);//Default
            st.setString(9, note);
            st.setDouble(10, cart.getTotalMoney() + freight);
            st.executeUpdate();
            // get id of newsest order added
            int order_id = 0;
            String sql1 = "select top 1 order_id from [orders] order by order_id desc";
            PreparedStatement st1 = connection.prepareStatement(sql1);
            ResultSet rs = st1.executeQuery();
            // add table order details
            if (rs.next()) {  //have item found
                order_id = rs.getInt("order_id");
                for (Item i : cart.getItems()) {
                    String sql2 = "insert into order_details\n"
                            + "values(?,?,?,?)";
                    PreparedStatement st2 = connection.prepareStatement(sql2);
                    st2.setInt(1, order_id);
                    st2.setInt(2, i.getProduct().getProduct_id());
                    st2.setInt(3, i.getQuantity());
                    st2.setDouble(4, i.getPrice());
                    st2.executeUpdate();
                }
            }
            //update quantity product
            String sql3 = "update products set "
                    + "unit_in_stock=unit_in_stock-? where product_id=?";
            PreparedStatement st3 = connection.prepareStatement(sql3);
            for (Item i : cart.getItems()) { //select item in cart
                st3.setInt(1, i.getQuantity());
                st3.setInt(2, i.getProduct().getProduct_id());
                st3.executeUpdate();
            }
            // return order_id
            Order order = getOrderByOrderID(order_id);
            return order;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null; //exception
    }

    //get order by order id
    public Order getOrderByOrderID(int orderID) {
        String sql = "select* from [orders] where order_id=? ";
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, orderID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {//result set return a order
                Order order = filOrderInfor(rs);
                order.setOrderDetailList(orderDetailDAO.getOrderDetailByOrderId(order.getOrder_id()));
                order.setOrderStatus(getOrderStatusById(order.getStatus()));
                order.setOrderDate(DateTimeUtil.GetDateFromString(order.getOrder_Date()));
                return order;
            }
        } catch (SQLException e) {
        } catch (ParseException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;//exception or can't find order
    }

    //get order detail by order ID
    public List<OrderDetail> getOrderDetailsByOrderID(int orderID) {
        List<OrderDetail> listOrderDetails = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        String sql = "select od.* from orders o inner join order_details od\n"
                + "on o.order_id=od.order_id\n"
                + "inner join products p\n"
                + "on p.product_id=od.product_id\n"
                + "where o.order_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, orderID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) { //result set have next item
                //add an item in list order details
                listOrderDetails.add(new OrderDetail(rs.getInt("order_id"), productDAO.getProduct(rs.getInt("product_id")),
                        rs.getInt("quantity"), rs.getDouble("price")));
            }
            return listOrderDetails;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //get Cart Summited by order ID coresspinding
    public Cart getCartSubmitted(int orderID) {
        ProductDAO productDAO = new ProductDAO();
        Cart cart = new Cart();
        List<OrderDetail> listOrderDetails = getOrderDetailsByOrderID(orderID);
        for (OrderDetail orderDetail : listOrderDetails) { //iterate over the list order details
            Product product = productDAO.getProduct(orderDetail.getProduct().getProduct_id());
            Item item = new Item(product, orderDetail.getQuantity(), orderDetail.getPrice());
            cart.addItem(item);  //add  item to cart
        }
        return cart;
    }

    //get order information by order id
    public OrderInformation getOrderInformationByOrderID(int orderID) {
        Order orderByOrderID = getOrderByOrderID(orderID);
        Cart cartSubmitted = getCartSubmitted(orderID);
        OrderInformation orderInformation = new OrderInformation(orderByOrderID, cartSubmitted);//get an orderInformation
        return orderInformation;
    }

    //change information by order Order (add payment step)
    public void UpdateOrderInformation(Order order) {
        int order_id = order.getOrder_id();
        String sql = "update orders\n"
                + "set ship_name=?,ship_gender=?,ship_address=?,ship_email=?\n"
                + ",freight=?,ship_mobile=?,ship_city=?,status=?,note=?,payment=?,total_price=?\n"
                + "where order_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, order.getShip_name());
            st.setBoolean(2, order.isShip_gender());
            st.setString(3, order.getShip_address());
            st.setString(4, order.getShip_email());
            st.setDouble(5, order.getFreight());
            st.setString(6, order.getShip_mobile());
            st.setString(7, order.getShip_city());
            st.setInt(8, order.getStatus());
            st.setString(9, order.getNote());
            st.setString(10, order.getPayment());
            st.setDouble(11, order.getTotal_price());
            st.setDouble(12, order.getOrder_id());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // delete assign order by order id
    public void DeleteOrderByOrderID(int orderID) {
        String sql1 = "delete from order_details\n"
                + "where order_id=? ";
        String sql2 = "delete from orders\n"
                + "where order_id=?";
        try {
            //delete order from order details
            PreparedStatement st1 = connection.prepareStatement(sql1);
            st1.setInt(1, orderID);
            st1.executeUpdate();
            // delete order from order
            PreparedStatement st2 = connection.prepareStatement(sql2);
            st2.setInt(1, orderID);
            st2.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void cancelOrder(Order order) {
        ProductDAO productDAO = new ProductDAO();
        for (OrderDetail o : order.getOrderDetailList()) {
            productDAO.UpdateQuantity(o.getProduct(), o.getQuantity());
        }

        String sql = "update orders\n"
                + "set status = 6 where order_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, order.getOrder_id());
            st.executeUpdate();
        } catch (SQLException e) {

        }

    }

    public List<Order> getOrderByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "select * from orders where user_id = ?";
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Order c = getOrder(rs);
                c.setOrderDetailList(orderDetailDAO.getOrderDetailByOrderId(c.getOrder_id()));
                c.setOrderDate(DateTimeUtil.GetDateFromString(c.getOrder_Date()));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ParseException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getTotalOrderBySaleId(int userId) {
        String sql = "select count(*) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n";
        if (userId != 0) {
            sql += "where om.user_id = " + userId;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public int getTotalSuccessOrderBySaleId(int userId) {
        String sql = "select count(*) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "where o.status = 5";
        if (userId != 0) {
            sql += " and om.user_id = " + userId;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public int getTotalRevenueBySaleId(int userId) {
        String sql = "select sum(total_price) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "where o.status != 6";
        if (userId != 0) {
            sql += " and om.user_id = " + userId;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public int getTotalRevenueBySaleIdAndTime(int userId, String start, String end) {
        String sql = "select sum(total_price) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "where o.status != 6";
        sql += " and order_date between '" + start + "' and '" + end + "'";
        if (userId != 0) {
            sql += " and om.user_id = " + userId;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public List<Order> getOrderByUserId(int userId, int start, int end) {
        List<Order> list = new ArrayList<>();
        String sql = "select * from (select ROW_NUMBER() over (order by order_id desc) as Row,* from orders where user_id = ?) as allorders where Row between ? and ?";
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, start);
            st.setInt(3, end);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Order c = getOrder(rs);
                c.setOrderDetailList(orderDetailDAO.getOrderDetailByOrderId(c.getOrder_id()));
                c.setOrderDate(DateTimeUtil.GetDateFromString(c.getOrder_Date()));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ParseException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Order> getOrderByPage(int start, int end) {
        List<Order> list = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO();
        String sql = "select * from (select ROW_NUMBER() over (order by order_date desc) as Row,* from orders) as allorders where Row between ? and ?";
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, start);
            st.setInt(2, end);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Order c = getOrder(rs);
                c.setOrderDetailList(orderDetailDAO.getOrderDetailByOrderId(c.getOrder_id()));
                c.setOrderDate(DateTimeUtil.GetDateFromString(c.getOrder_Date()));
                c.setAccount(accountDAO.getAccountByID(c.getUser_id()));
                list.add(c);
            }
        } catch (ParseException e) {
            System.out.println(e);
        } catch (SQLException e) {

        }
        return list;
    }
    //    public List<Order> getOrderByPage(int start, int end, String[] saleId, String[] status, String orderOption, String key) {
//        List<Order> list = new ArrayList<>();
//        AccountDAO accountDAO = new AccountDAO();
//        String sql = "select * from (select ROW_NUMBER() over (order by " + orderOption + ") as Row,o.*, a.full_name, sale.full_name as sale_name from orders o\n"
//                + "inner join accounts a\n"
//                + "on o.user_id = a.user_id\n"
//                + "inner join orders_management om\n"
//                + "on om.order_id = o.order_id\n"
//                + "inner join accounts sale\n"
//                + "on sale.user_id = om.user_id\n"
//                + "where (o.order_id like '%" + key + "%' or a.full_name like '%" + key + "%')";
//        sql = appendSaleId(sql, saleId);
//        sql = appendStatus(sql, status);
//        sql += ") as allorders where Row between " + start + " and " + end;
//        System.out.println(sql);
//        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            ResultSet rs = st.executeQuery();
//
//            while (rs.next()) {
//                Order c = getOrder(rs);
//                c.setOrderDetailList(orderDetailDAO.getOrderDetailByOrderId(c.getOrder_id()));
//                c.setOrderDate(DateTimeUtil.GetDateFromString(c.getOrder_Date()));
//                c.setAccount(accountDAO.getAccountByID(c.getUser_id()));
//                list.add(c);
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        } catch (ParseException ex) {
//            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }

//    public int getTotalOrder(String[] saleId, String[] status, String key) {
//        String sql = "select COUNT(*) from orders o\n"
//                + "inner join accounts a\n"
//                + "on o.user_id = a.user_id\n"
//                + "inner join orders_management om\n"
//                + "on om.order_id = o.order_id\n"
//                + "inner join accounts sale\n"
//                + "on sale.user_id = om.user_id\n"
//                + "where (o.order_id like '%" + key + "%' or a.full_name like '%" + key + "%')";
//        sql = appendSaleId(sql, saleId);
//        sql = appendStatus(sql, status);
//        System.out.println(sql);
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return 0;
//    }
    public List<Order> getOrderByPage(int start, int end, String[] saleId, String[] status, String orderOption, String key, String startDate, String endDate) {
        List<Order> list = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO();
        String sql = "select * from (select ROW_NUMBER() over (order by " + orderOption + ") as Row,o.*, a.full_name, sale.full_name as sale_name from orders o\n"
                + "inner join accounts a\n"
                + "on o.user_id = a.user_id\n"
                + "inner join orders_management om\n"
                + "on om.order_id = o.order_id\n"
                + "inner join accounts sale\n"
                + "on sale.user_id = om.user_id\n"
                + "where (o.order_id like '%" + key + "%' or a.full_name like N'%" + key + "%')";
        sql = appendSaleId(sql, saleId);
        sql = appendStatus(sql, status);
        sql += " and o.order_date between '" + startDate + "' and '" + endDate + "'";
        sql += ") as allorders where Row between " + start + " and " + end;
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Order c = getOrder(rs);
                c.setOrderDetailList(orderDetailDAO.getOrderDetailByOrderId(c.getOrder_id()));
                c.setOrderDate(DateTimeUtil.GetDateFromString(c.getOrder_Date()));
                c.setAccount(accountDAO.getAccountByID(c.getUser_id()));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ParseException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getTotalOrder(String[] saleId, String[] status, String key, String startDate, String endDate) {
        String sql = "select COUNT(*) from orders o\n"
                + "inner join accounts a\n"
                + "on o.user_id = a.user_id\n"
                + "inner join orders_management om\n"
                + "on om.order_id = o.order_id\n"
                + "inner join accounts sale\n"
                + "on sale.user_id = om.user_id\n"
                + "where (o.order_id like '%" + key + "%' or a.full_name like N'%" + key + "%')";
        sql = appendSaleId(sql, saleId);
        sql = appendStatus(sql, status);
        sql += " and o.order_date between '" + startDate + "' and '" + endDate + "'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    private String appendStatus(String sql, String[] status) {
        if (status != null) {
            sql += " and status in(";
            for (String statu : status) {
                sql += statu + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
        return sql;
    }

    private String appendSaleId(String sql, String[] saleId) {
        if (saleId != null) {
            sql += " and sale.user_id in (";
            for (String saleId1 : saleId) {
                sql += saleId1 + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
        return sql;
    }

    public Order getOrderByUserIdAndOrderId(int userId, int orderId) {
        String sql = "select * from orders where user_id = ? and order_id =?";
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, orderId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Order c = getOrder(rs);
                c.setOrderDetailList(orderDetailDAO.getOrderDetailByOrderId(c.getOrder_id()));
                c.setOrderDate(DateTimeUtil.GetDateFromString(c.getOrder_Date()));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ParseException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getTotalOrderByUserid(int userId) {
        String sql = "select COUNT(*) from orders where user_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public int getTotalOrder() {
        String sql = "select COUNT(*) from orders";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public int getTotalOrderToday() {
        String sql = "select COUNT(*) from orders";
        String end = DateTimeUtil.Now();
        String start = DateTimeUtil.getStartDate(0).toString();
        sql += " where order_date between '" + start + "' and '" + end + "'";
        System.out.println(sql);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }
    public int getTotalOrderByStatusAndTime(int status, String time) {
        String sql = "select COUNT(*) from orders";
        String end = DateTimeUtil.Now();
        String start = DateTimeUtil.getStartDate(time).toString();
        sql += " where order_date between '" + start + "' and '" + end + "'";
        sql += " and status = "+status;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public void updateOrder(Order order, String shipName, boolean shipGender, String shipEmail, String shipMobile, String shipAddress, String shipCity, String payment) {
        String sql = "update orders\n"
                + "set ship_name = ?, ship_address = ?, ship_gender = ?, ship_mobile = ?, ship_email = ?, ship_city = ?, payment = ? where order_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, shipName);
            st.setString(2, shipAddress);
            st.setBoolean(3, shipGender);
            st.setString(4, shipMobile);
            st.setString(5, shipEmail);
            st.setString(6, shipCity);
            st.setString(7, payment);
            st.setInt(8, order.getOrder_id());
            st.executeUpdate();
        } catch (SQLException e) {

        }
    }

    private Order getOrder(ResultSet rs) throws SQLException {
        return new Order(rs.getInt("order_id"), rs.getInt("user_id"), rs.getString("order_date"), rs.getString("require_date"), rs.getString("shipped_Date"), rs.getInt("ship_via"), rs.getDouble("freight"), rs.getString("ship_name"), rs.getString("ship_address"), rs.getBoolean("ship_gender"), rs.getString("ship_mobile"), rs.getString("ship_email"), rs.getString("ship_city"), rs.getInt("status"), rs.getString("note"), rs.getString("payment"), rs.getDouble("total_price"));
    }

    private OrderStatus getOrderStatusById(int id) {
        String sql = "select * from order_status where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new OrderStatus(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public List<OrderStatus> getAllOrderStatusForSale() {
        String sql = "select * from order_status where id != 6";
        List<OrderStatus> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                OrderStatus os = new OrderStatus(rs.getInt(1), rs.getString(2));
                list.add(os);
            }
            return list;
        } catch (SQLException e) {

        }
        return null;
    }

    public List<OrderStatus> getAllOrderStatus() {
        String sql = "select * from order_status";
        List<OrderStatus> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                OrderStatus os = new OrderStatus(rs.getInt(1), rs.getString(2));
                list.add(os);
            }
            return list;
        } catch (SQLException e) {

        }
        return null;
    }

    public String getFirstOrderDate() {
        String sql = "select top 1 order_date from orders order by order_date asc";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public void updateOrder(int orderId, int status, String saleNote) {
        String sql = "update orders\n"
                + "set status = ?, sale_note = ? where order_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, status);
            st.setInt(3, orderId);
            st.setString(2, saleNote);
            st.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public void updateOrderManagement(int orderId, int saleId) {
        String sql = "update orders_management\n"
                + "set user_id = ? where order_id = ?";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, saleId);
            st.setInt(2, orderId);
            st.executeUpdate();
        } catch(SQLException e){
            
        }
    }

    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO();
        System.out.println(orderDAO.getOrderByOrderID(21).getSale_note());
    }
}
