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
import model.PostSubCategory;

/**
 *
 * @author Admin
 */
public class PostSubcategoryDAO extends DBContext {

    public PostSubCategory getPostSubCategory(int id) {
        String sql = "select * from post_sub_categories where id = ?";
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                PostSubCategory p = new PostSubCategory(rs.getInt("id"), postCategoryDAO.getPostCategory(rs.getInt("category_id")), rs.getString("name"), rs.getString("description"), rs.getBoolean("feature"));
                return p;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public List<PostSubCategory> getPostSubCategorys() {
        String sql = "select * from post_sub_categories";
        List<PostSubCategory> list = new ArrayList<>();
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostSubCategory p = new PostSubCategory(rs.getInt("id"), postCategoryDAO.getPostCategory(rs.getInt("category_id")), rs.getString("name"), rs.getString("description"), rs.getBoolean("feature"));
                list.add(p);
            }
            return list;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public List<PostSubCategory> getPostSubCategorysByCategory(int id) {
        String sql = "select * from post_sub_categories where category_id = ?";
        List<PostSubCategory> list = new ArrayList<>();
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostSubCategory p = new PostSubCategory(rs.getInt("id"), postCategoryDAO.getPostCategory(rs.getInt("category_id")), rs.getString("name"), rs.getString("description"), rs.getBoolean("feature"));
                list.add(p);
            }
            return list;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public List<PostSubCategory> getAllPostSubCategory() {
            List<PostSubCategory>list=new ArrayList<>();
        String sql = "select * from post_sub_categories ";
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostSubCategory p = new PostSubCategory(rs.getInt("id"), postCategoryDAO.getPostCategory(rs.getInt("category_id")), rs.getString("name"), rs.getString("description"), rs.getBoolean("feature"));
                list.add(p);
            }
            return list;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

public List<PostSubCategory> getSubCategoryByCategoryID(int id) {
            List<PostSubCategory>list=new ArrayList<>();
        String sql = "select * from post_sub_categories ps inner join post_categories pc\n" +
"on ps.category_id=pc.category_id\n" +
"where  pc.category_id="
                + id;
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostSubCategory p = new PostSubCategory(rs.getInt("id"), postCategoryDAO.getPostCategory(rs.getInt("category_id")), rs.getString("name"), rs.getString("description"), rs.getBoolean("feature"));
                list.add(p);
            }
            return list;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }    
    
    public static void main(String[] args) {
        PostSubcategoryDAO p = new PostSubcategoryDAO();
        System.out.println(p.getPostSubCategorysByCategory(1).size());
    }
}
