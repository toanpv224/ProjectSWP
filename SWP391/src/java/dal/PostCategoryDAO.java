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
import model.PostCategory;

/**
 *
 * @author Admin
 */
public class PostCategoryDAO extends DBContext {

    public PostCategory getPostCategory(int id) {
        String sql = "select * from post_categories where category_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                PostCategory category = new PostCategory(rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getBoolean("status")
                );
                return category;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public List<PostCategory> getPostCategorys() {
        String sql = "select * from post_categories";
        List<PostCategory> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostCategory category = new PostCategory(rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getBoolean("status")
                );
                list.add(category);
            }
            return list;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }
    public List<PostCategory> getPostCategory() {
        List<PostCategory> list = new ArrayList<>();
        String sql = "select * from post_categories";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            PostSubcategoryDAO ps=new PostSubcategoryDAO();
         
            while (rs.next()) {
                PostCategory category = new PostCategory(rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getBoolean("status"),
                        ps.getSubCategoryByCategoryID(rs.getInt("category_id"))
                );
                list.add(category);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return list;
    }

    public static void main(String[] args) {
        PostCategoryDAO p = new PostCategoryDAO();
        System.out.println(p.getPostCategorys().size());
    }
}
