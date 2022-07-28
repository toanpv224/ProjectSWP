/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Account;
import model.Order;
import model.Sale;
import util.DateTimeUtil;

/**
 *
 * @author Admin
 */
public class SaleDAO extends DBContext {

    public List<Sale> getAllSaleMember() {
        String sql = "select * from accounts where role_id = 3";
        List<Sale> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                Account account = new Account(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("full_name"), rs.getInt("role_id"), rs.getBoolean("gender"), rs.getString("email"),
                        rs.getString("city"), rs.getString("country"), rs.getString("address"), rs.getString("phone"),
                        rs.getString("image_url"), rs.getBoolean("featured"), rs.getString("hash"), rs.getInt("active"));
                sale.setAccount(account);

                sale.setOrderList(getOrderBySaleMember(account.getUser_id()));

                list.add(sale);
            }
            return list;
        } catch (SQLException e) {

        }
        return null;
    }

    public List<Account> getAllAccountOfSaleMember() {
        String sql = "select * from accounts where role_id = 3";
        List<Account> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("full_name"), rs.getInt("role_id"), rs.getBoolean("gender"), rs.getString("email"),
                        rs.getString("city"), rs.getString("country"), rs.getString("address"), rs.getString("phone"),
                        rs.getString("image_url"), rs.getBoolean("featured"), rs.getString("hash"), rs.getInt("active"));

                list.add(account);
            }
            return list;
        } catch (SQLException e) {

        }
        return null;
    }

    public List<Order> getOrderBySaleMember(int userId) {
        String sql = "select o.* from orders o \n"
                + "inner join orders_management om \n"
                + "on o.order_id = om.order_id\n"
                + "inner join accounts a \n"
                + "on om.user_id = a.user_id\n"
                + "where a.user_id = ?";
        List<Order> list = new ArrayList<>();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order order = filOrderInfor(rs);
                order.setOrderDetailList(orderDetailDAO.getOrderDetailByOrderId(order.getOrder_id()));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {

        }
        return null;
    }

    public List getListOfTotalRevenueByDay(LocalDate start, LocalDate end) {
        List list = new ArrayList();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            list.add(getTotalRevenueByDay(i));
        }

        return list;
    }

    private double getTotalRevenueByDay(LocalDate start) {
        String sql = "select SUM(total_price) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "inner join accounts a \n"
                + "on a.user_id = om.user_id\n"
                + "where order_date between ? and ?";
        System.out.println(sql);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, start.toString());
            st.setString(2, start.plusDays(1).toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    public double getTotalRevenueToday() {
        String sql = "select SUM(total_price) from orders o\n"
                + "where order_date between ? and ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, DateTimeUtil.getStartDate(0).toString());
            st.setString(2, DateTimeUtil.Now());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    public double getTotalRevenue() {
        String sql = "select SUM(total_price) from orders o\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    public List getListOfTotalRevenueByDayBySale(LocalDate start, LocalDate end, int saleId) {
        List list = new ArrayList();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            list.add(getTotalRevenueByDayBySale(i, saleId));
        }

        return list;
    }

    private double getTotalRevenueByDayBySale(LocalDate start, int saleId) {
        String sql = "select SUM(total_price) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "inner join accounts a \n"
                + "on a.user_id = om.user_id\n"
                + "where a.user_id = ? and  order_date between ? and ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, saleId);
            st.setString(2, start.toString());
            st.setString(3, start.plusDays(1).toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    public List getListOfTotalRevenueByDayBySale(LocalDate start, LocalDate end, int saleId, int status) {
        List list = new ArrayList();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            list.add(getTotalRevenueByDayBySale(i, saleId, status));
        }

        return list;
    }

    private double getTotalRevenueByDayBySale(LocalDate start, int saleId, int status) {
//        a.user_id = ? and  
//                and o.status = ?
        String sql = "select SUM(total_price) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "inner join accounts a\n"
                + "on a.user_id = om.user_id\n"
                + "where order_date between ? and ?";
        if (saleId != 0) {
            sql += " and a.user_id = " + saleId;
        }
        if (status != 0) {
            sql += " and o.status = " + status;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, start.toString());
            st.setString(2, start.plusDays(1).toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    private Order filOrderInfor(ResultSet rs) throws SQLException {
        OrderDAO order_dao = new OrderDAO();
        int order_id = rs.getInt("order_id");
        return new Order(order_id, rs.getInt("user_id"), rs.getString("order_date"), rs.getString("require_date"),
                rs.getString("shipped_date"), rs.getInt("ship_via"), rs.getDouble("freight"), rs.getString("ship_name"),
                rs.getString("ship_address"), rs.getBoolean("ship_gender"), rs.getString("ship_mobile"), rs.getString("ship_email"), rs.getString("ship_city"),
                rs.getInt("status"), rs.getString("note"), rs.getString("payment"), rs.getDouble("total_price"));
    }

    public int getTotalOrder() {
        String sql = "select COUNT(*) from orders where status != 6";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    public int getTotalSuccessOrder() {
        String sql = "select COUNT(*) from orders where status = 5";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    public int getTotalOrderBySale(int saleId) {
        String sql = "select  COUNT(*) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "inner join accounts a\n"
                + "on a.user_id = om.user_id\n"
                + "where o.status != 6";
        if (saleId != 0) {
            sql += " and a.user_id = " + saleId;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    public int getTotalSuccessOrderBySale(int saleId) {
        String sql = "select  COUNT(*) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "inner join accounts a\n"
                + "on a.user_id = om.user_id\n"
                + "where o.status = 5";
        if (saleId != 0) {
            sql += " and a.user_id = " + saleId;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    private int getTotalOrderBySaleByDay(int saleId, LocalDate start) {
        String sql = "select  COUNT(*) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "inner join accounts a\n"
                + "on a.user_id = om.user_id\n"
                + "where o.status != 6 and o.order_date between ? and ?";
        if (saleId != 0) {
            sql += " and a.user_id = " + saleId;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, start.toString());
            st.setString(2, start.plusDays(1).toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }
    private int getAllOrderByDay(LocalDate start) {
        String sql = "select  COUNT(*) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "inner join accounts a\n"
                + "on a.user_id = om.user_id\n"
                + "where o.order_date between ? and ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, start.toString());
            st.setString(2, start.plusDays(1).toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    private int getTotalSuccessOrderBySaleByDay(int saleId, LocalDate start) {
        String sql = "select  COUNT(*) from orders o\n"
                + "inner join orders_management om\n"
                + "on o.order_id = om.order_id\n"
                + "inner join accounts a\n"
                + "on a.user_id = om.user_id\n"
                + "where o.status = 5 and o.order_date between ? and ?";
        if (saleId != 0) {
            sql += " and a.user_id = " + saleId;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, start.toString());
            st.setString(2, start.plusDays(1).toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    public List getListOfTotalOrderByDayBySale(LocalDate start, LocalDate end, int saleId) {
        List list = new ArrayList();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            list.add(getTotalOrderBySaleByDay(saleId, i));
        }

        return list;
    }
    public List getListOfAllOrderByDay(LocalDate start, LocalDate end) {
        List list = new ArrayList();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            list.add(getAllOrderByDay(i));
        }

        return list;
    }

    public List getListOfTotalSuccessOrderByDayBySale(LocalDate start, LocalDate end, int saleId) {
        List list = new ArrayList();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            list.add(getTotalSuccessOrderBySaleByDay(saleId, i));
        }

        return list;
    }

    public boolean checkedSalePermission(int orderId, Account sale) {
        if (sale.getRole_id() == 4) {
            return true;
        }
        String sql = "select * from orders_management where order_id = ? and user_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, orderId);
            st.setInt(2, sale.getUser_id());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {

        }
        return false;
    }

    public Account getSaleByOrderId(int orderId) {
        String sql = "select a.* from orders o\n"
                + "inner join orders_management om \n"
                + "on o.order_id = om.order_id\n"
                + "inner join accounts a\n"
                + "on om.user_id = a.user_id\n"
                + "where o.order_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Account a = new Account();
                a.setUser_id(rs.getInt("user_id"));
                a.setFull_name(rs.getString("full_name"));
                a.setUsername(rs.getString("username"));
                return a;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public double getRevenueByProductCategoryId(int id, String time) {
        String end = DateTimeUtil.Now();
        String start = DateTimeUtil.getStartDate(time).toString();
        String sql = "select SUM(total_price) from orders o\n"
                + "inner join order_details od\n"
                + "on o.order_id = od.order_id\n"
                + "inner join products p\n"
                + "on od.product_id = p.product_id\n"
                + "inner join product_sub_categories psc\n"
                + "on p.sub_category_id = psc.sub_category_id\n"
                + "inner join product_categories pc\n"
                + "on pc.category_id = psc.category_id\n"
                + "where o.status != 6 and order_date between '"+start+"' and '"+end+"'";
        if(id != 0){
            sql += " and pc.category_id = "+id;
        }
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return rs.getDouble(1);
            }
        }catch(SQLException e){
            
        }
        return 0;
    }
        //Assign a sale
    public void AssignSale(int order_id){
        Map.Entry<Integer, Timestamp> saleAssign = getSaleAssign();
        int user_id=saleAssign.getKey();//id
        AssignDate(user_id);
        AssignOrder(order_id, user_id);
        
    }
        //get a map of sale for next assign
    public Map.Entry<Integer, Timestamp>getSaleAssign(){
        HashMap<Integer,Timestamp> map =new HashMap<>();
        List<Sale> list = getAllSaleMember();
        for (Sale sale : list) {
            int user_id = sale.getAccount().getUser_id();
            Timestamp dateAssign = getDateAssign(user_id);
            map.put(user_id, dateAssign);
        }
//        sort map asc
        List<Map.Entry<Integer, Timestamp>> sortMap = map.entrySet().stream().sorted((k1, k2) -> {
            
            return k1.getValue().compareTo(k2.getValue());
        }).collect(Collectors.toList()); 
        
              return sortMap.get(0);
          
    }
      //get assign date of a sale
    public Timestamp getDateAssign(int user_id){
        String sql="select top 1 a.user_id,u.date  from accounts a inner join roles r\n" +
                    "on a.role_id=r.role_id\n" +
                    "inner join update_date u\n" +
                    "on a.user_id =u.user_id\n" +
                    "where a.role_id=3 and a.user_id="
                + user_id
                + "\n" +
                    "order by date desc";
        try {
            PreparedStatement st=connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery();
             if (rs.next()) {                
                Timestamp timestamp = rs.getTimestamp("date");
               return timestamp;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null; //exception or null
    }
  
          //Update assign date for sale
    public void AssignDate(int user_id){
        String sql="insert into update_date (user_id,date)\n" +
        "values(?,GETDATE())";
        try {
             PreparedStatement st = connection.prepareStatement(sql);
             st.setInt(1, user_id);
             st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void AssignOrder(int order_id,int user_id){
        String sql="insert into orders_management (order_id,user_id)\n" +
                    "values(?,?)";
        try {
            PreparedStatement st=connection.prepareCall(sql);
            st.setInt(1, order_id);
            st.setInt(2, user_id);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        SaleDAO saleDAO = new SaleDAO();
        System.out.println(saleDAO.getTotalRevenueByDay(LocalDate.now()));
    }
}
