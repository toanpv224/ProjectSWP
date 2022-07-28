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
import model.SubCategory;

/**
 *
 * @author Admin
 */
public class SubCategoryDAO extends DBContext {
//    public SubCategory[] getSubCategoryByCategoryId(int categoryId){
//        CategoryDAO categoryDAO = new CategoryDAO();
//        List<SubCategory> list= new ArrayList<>();
//        String sql = "select * from product_sub_categories where category_id = ?";
//        try{
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setInt(1, categoryId);
//            ResultSet rs = st.executeQuery();
//            while (rs.next()) {
//                SubCategory productSubCategory = new SubCategory(rs.getInt("sub_category_id"),
//                        rs.getString("category_name"),
//                        categoryDAO.getProductCategory(rs.getInt("category_id")),
//                        rs.getString("description"),
//                        rs.getInt("status")
//                );
//                list.add(productSubCategory);
//            }
//        } catch(SQLException e){
//            
//        }
//        SubCategory[] arr = new SubCategory[list.size()];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = list.get(i);
//        }
//        return arr;
//    }

    public List<SubCategory> getSubCategoryByCategoryId(int categoryId) {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<SubCategory> list = new ArrayList<>();
        String sql = "select * from product_sub_categories where category_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, categoryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SubCategory productSubCategory = new SubCategory(rs.getInt("sub_category_id"),
                        rs.getString("category_name"),
                        categoryDAO.getProductCategory(rs.getInt("category_id")),
                        rs.getString("description"),
                        (rs.getInt("status") == 1)
                );
                list.add(productSubCategory);
            }
        } catch (SQLException e) {

        }
        return list;
    }

    public List<PostSubCategory> getPostSubCategoryByCategoryId(int categoryId) {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<PostSubCategory> list = new ArrayList<>();
        String sql = "select * from post_sub_categories where category_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, categoryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostSubCategory postSubCategory = new PostSubCategory(rs.getInt("id"),
                        categoryDAO.getPostCategory(rs.getInt("category_id")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("featured")==1
                );
                list.add(postSubCategory);
            }
        } catch (SQLException e) {

        }
        return list;
    }

    public SubCategory getSubCategoryById(int subCategoryId) {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<SubCategory> list = new ArrayList<>();
        String sql = "select * from product_sub_categories where sub_category_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subCategoryId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                SubCategory productSubCategory = new SubCategory(rs.getInt("sub_category_id"),
                        rs.getString("category_name"),
                        categoryDAO.getProductCategory(rs.getInt("category_id")),
                        rs.getString("description"),
                        (rs.getInt("status") == 1)
                );
                return productSubCategory;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public static void main(String[] args) {
        SubCategoryDAO s = new SubCategoryDAO();
//         SubCategory[] arr = s.getSubCategoryByCategoryId(1);
//         for (int i = 0; i < arr.length; i++) {
//             System.out.println(arr[i].getName());
//        }
        System.out.println(s.getSubCategoryById(6).getName());
    }
}
