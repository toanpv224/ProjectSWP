/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetail;

/**
 *
 * @author Admin
 */
public class OrderDetailDAO extends DBContext {

    public List<OrderDetail> getOrderDetailByOrderId(int orderId) {
        String sql = "select od.* from orders o \n"
                + "inner join order_details od \n"
                + "on o.order_id = od.order_id\n"
                + "where o.order_id = ?";
        CategoryDAO categoryDAO = new CategoryDAO();
        List<OrderDetail> list = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                OrderDetail od = new OrderDetail(rs.getInt("order_id"), productDAO.getProduct(rs.getInt("product_id")), rs.getInt("quantity"), rs.getDouble("price"));
                od.setCategory(categoryDAO.getProductCategoryBySubCategory(od.getProduct().getSubCategory().getId()));
                list.add(od);
            }
            return list;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public static void main(String[] args) {
        OrderDetailDAO odd = new OrderDetailDAO();
        System.out.println(odd.getOrderDetailByOrderId(32).size());
    }
}
