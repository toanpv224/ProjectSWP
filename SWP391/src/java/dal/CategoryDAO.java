package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.PostCategory;
import model.PostSubCategory;
import model.ProductCategory;
import model.SubCategory;

public class CategoryDAO extends DBContext {
    // <editor-fold defaultstate="collapsed" desc="Product Category">
    public SubCategory getProductSubCategory(int subCategoryId) {
        String sql = "select * from product_sub_categories where sub_category_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subCategoryId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                SubCategory productSubCategory = new SubCategory(rs.getInt("sub_category_id"),
                        rs.getString("category_name"),
                        getProductCategory(rs.getInt("category_id")),
                        rs.getString("description"),
                        (rs.getInt("status") == 1)
                );
                return productSubCategory;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public ProductCategory getProductCategory(int category_id) {
        String sql = "select * from product_categories where category_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, category_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                ProductCategory product_category = new ProductCategory(rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getInt("featured")
                );
                return product_category;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public List<ProductCategory> getProductCategory() {
        List<ProductCategory> list = new ArrayList<>();
        String sql = "select * from product_categories";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ProductCategory product_category = new ProductCategory(rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getInt("featured")
                );
                product_category.setSubCategoryList();
                list.add(product_category);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return list;
    }

    public ProductCategory getProductCategoryBySubCategory(int subCategoryId) {
        String sql = "select pc.* from product_categories pc inner join product_sub_categories psc \n"
                + "on pc.category_id = psc.category_id\n"
                + "where psc.sub_category_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, subCategoryId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                ProductCategory product_category = new ProductCategory(rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getInt("featured")
                );
                return product_category;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Post category">
    public PostCategory getPostCategory(int category_id) {
        String sql = "select * from post_categories where category_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, category_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                PostCategory category = new PostCategory(rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getInt("status")==1
                );
                return category;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }
    public PostSubCategory getPostSubCategory(int sub_category_id) {
        String sql = "select * from post_sub_categories where id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, sub_category_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                PostSubCategory sub_category = new PostSubCategory(rs.getInt("id"),
                        getPostCategory(rs.getInt("category_id")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("feature")==1
                );
                return sub_category;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }
    public List<PostSubCategory> getPostSubCategorByCategory(int category_id) {
        List<PostSubCategory> list = new ArrayList<>();
        String sql = "select * from post_sub_categories where category_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, category_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostSubCategory sub_category = new PostSubCategory(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("feature")==1
                );
                System.out.println(sub_category.getId()+ " - " + sub_category.getName());
                list.add(sub_category);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return list;
    }

    public List<PostCategory> getPostCategory() {
        List<PostCategory> list = new ArrayList<>();
        String sql = "select * from post_categories";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("category_id");
                PostCategory category = new PostCategory(id,
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getInt("status")==1,
                        getPostSubCategorByCategory(id)
                );
                list.add(category);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return list;
    }

//    public ProductCategory getProductCategoryBySubCategory(int subCategoryId) {
//        String sql = "select pc.* from product_categories pc inner join product_sub_categories psc \n"
//                + "on pc.category_id = psc.category_id\n"
//                + "where psc.sub_category_id = ?";
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setInt(1, subCategoryId);
//            ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//                ProductCategory product_category = new ProductCategory(rs.getInt("category_id"),
//                        rs.getString("category_name"),
//                        rs.getString("description"),
//                        rs.getInt("featured")
//                );
//                return product_category;
//            }
//        } catch (SQLException sqle) {
//            System.out.println(sqle);
//        }
//        return null;
//    }

    public static void main(String[] args) {
        CategoryDAO c = new CategoryDAO();
        System.out.println(c.getProductCategory().size());
    }
}
