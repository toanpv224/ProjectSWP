package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Post;
import model.Tag;

public class PostDAO extends DBContext {

    private Post filpostDetails(ResultSet rs) throws SQLException {
        CategoryDAO category_dao = new CategoryDAO();
        int post_id = rs.getInt("post_id");
        return new Post(
                post_id,
                rs.getInt("user_id"),
                rs.getString("thumbnail"),
                rs.getString("title"),
                rs.getString("sub_title"),
                getTags(post_id),
                rs.getTimestamp("publication_date"),
                category_dao.getPostSubCategory(rs.getInt("post_subcategories_id")),
                rs.getTimestamp("updated_date"),
                rs.getString("post_details"),
                rs.getBoolean("featured")
        );
    }

    public List<Post> getPosts(int featured, int start, int end) {
        return getPosts("publication_date", featured, "", -1, -1, null, start, end);
    }

    public List<Post> getPosts(boolean featured, int start, int end) {
        return getPosts("publication_date", featured ? 1 : 0, "", -1, -1, null, start, end);
    }

    public List<Post> getPosts(String orderOption, int featured, String title_search_key, int sub_category_id, int category_id, List<Integer> tag_id_list, int start, int end) {
        /**
         * select * from ( select ROW_NUMBER() over (order by updated_date desc)
         * as Row,p.* from posts p inner join tag_post tp on p.post_id =
         * tp.post_id where p.title like '%%' and p.category_id = 1 and
         * tp.tag_id in (1) ) all_posts where Row between 1 and 22
         */

        List<Post> list = new ArrayList<>();
        String sql = "select * from (select ROW_NUMBER() over (order by " + orderOption + ") as Row,p.* from posts p "
                + "inner join post_sub_categories psc on p.post_subcategories_id = psc.id "
                + "where p.featured=" + featured + " and p.title like '%" + title_search_key + "%' ";
        if (sub_category_id > 0) {
            sql += "and p.post_subcategories_id =" + sub_category_id;
        } else if (category_id > 0) {
            sql += "and psc.category_id =" + category_id;
        }
        if (tag_id_list != null && tag_id_list.size() > 0) {
            sql += " and tp.tag_id in (";
            for (int tag_id : tag_id_list) {
                sql += tag_id + ",";
            }
            if (sql.endsWith(",")) {
                sql = sql.substring(0, sql.length() - 1);
            }
            sql += ")";
        }
        sql += ") all_posts where Row between " + start + " and " + end;
        System.out.println(sql);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Post post = filpostDetails(rs);
                list.add(post);
            }
            st.close();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return list;
    }

    //convert order option to string
    public String convertOrderByID(int id) {
        switch (id) {
            case 1:
                return "post_id";
            case 2:
                return "title";
            case 3:
                return "user_id";
            case 4:
                return "featured";
            case 5:
                return "category_id";
            default:
        }
        return null;
    }

    // count filter and paging
    public int countPostPaging(String word, int categoryID, int subCategoryID, int authorID, int feature, int numperpage) {
        int num = 1;
        String sql = "select count(post_id)  from posts p,post_sub_categories ps\n"
                + "where p.post_subcategories_id=ps.id";
        if (!word.equals("")) { // have option word
            sql += " and title like'%"
                    + word
                    + "%'";
        }
        if (categoryID != 0) {
            sql += " and  ps.category_id="
                    + categoryID;
        }
        if (subCategoryID != 0) { // have option category
            sql += " and post_subcategories_id="
                    + subCategoryID;
        }
        if (authorID != 0) { //have option author
            sql += " and user_id ="
                    + authorID;
        }
        if (feature != -1) { //have option feature
            sql += " and featured="
                    + feature
                    + "\n";
        }

        System.out.println(sql);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        if (num == 0) {
            return 1;//minimum=1
        } else if (num % numperpage == 0) { //number full apge
            return num / numperpage;
        }
        return num / numperpage + 1;

    }
    //get post and filter

    public List<Post> getPosts(String word, int categoryID, int subCategoryID, int authorID, int feature, int orderByID, int op, int page, int numperpage) {
        List<Post> list = new ArrayList<>();
        String sql = "select * from posts p,post_sub_categories ps  \n"
                + "where p.post_subcategories_id=ps.id";
        if (!word.equals("")) { // have option word
            sql += " and title like'%"
                    + word
                    + "%'";
        }
        if (categoryID != 0) {
            sql += " and  ps.category_id="
                    + categoryID;
        }
        if (subCategoryID != 0) { // have option category
            sql += " and post_subcategories_id="
                    + subCategoryID;
        }
        if (authorID != 0) { //have option author
            sql += " and user_id ="
                    + authorID;
        }
        if (feature != -1) { //have option feature
            sql += " and featured="
                    + feature
                    + "\n";
        }
        if (convertOrderByID(orderByID) != null) { //have option order
            sql += " order by "
                    + convertOrderByID(orderByID);

        } else {
            sql += " order by post_id ";
        }
        if (op == 1) {
            sql += " asc \n";
        } else {
            sql += " desc \n";
        }
        sql += " OFFSET "
                + (page - 1) * numperpage
                + " ROWS FETCH NEXT "
                + numperpage
                + " ROWS ONLY";
        System.out.println(sql);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Post post = filpostDetails(rs);
                list.add(post);
            }
            st.close();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return list;
    }

    public Post getPost(int id) {
        String sql = "select * from posts where post_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return filpostDetails(rs);
            }
            st.close();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public Post getPostWithSubCategory(int id) {
        String sql = "select * from posts where post_id=?";
        PostSubcategoryDAO postSubCategoryDAO = new PostSubcategoryDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Post p = filpostDetails(rs);
                return p;
            }
            st.close();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public int countPosts() {
        String sql = "select count(post_id) from posts";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            st.close();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return 0;
    }

    public int countPosts(int featured, String title_search_key, int sub_category_id, int category_id, List<Integer> tag_id_list) {
        String sql = "select count(p.post_id) as count from posts p "
                + "inner join post_sub_categories psc on p.post_subcategories_id = psc.id "
                + "where p.featured=? and p.title like '%" + title_search_key + "%' ";
        if (sub_category_id > 0) {
            sql += "and p.post_subcategories_id =" + sub_category_id;
        } else if (category_id > 0) {
            sql += "and psc.category_id =" + category_id;
        }
        if (tag_id_list != null && tag_id_list.size() > 0) {
            sql += "and tp.tag_id in (";
            for (int tag_id : tag_id_list) {
                sql += tag_id + ",";
            }
            if (sql.endsWith(",")) {
                sql = sql.substring(0, sql.length() - 1);
            }
            sql += ")";
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, featured);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
            st.close();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return 0;
    }

    //update status of post
    public boolean updateStatusPost(int post_id, int featured) {
        String sql = "UPDATE posts\n"
                + "SET featured = "
                + featured
                + "\n"
                + "WHERE post_id="
                + post_id;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;//exception
    }
    //add new post category

    public boolean InsertPostCategory(String category_name, String description) {
        String sql = "insert into post_categories(category_name,description)\n"
                + "values(?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, category_name);
            st.setString(2, description);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;//exception
    }
    //add new post

    public boolean InsertPost(Post post) {
        String sql = "insert into posts(user_id,title,thumbnail,post_subcategories_id,post_details,featured,publication_date)\n"
                + "values(?,?,?,?,?,?,getdate())";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, post.getUser_id());
            st.setString(2, post.getTitle());
            st.setString(3, post.getThumbnail());
            st.setInt(4, post.getPostSubCategory().getId());
            st.setString(5, post.getPost_details());
            st.setBoolean(6, post.isFeatured());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;//exception
    }

    public boolean upload(String file) {
        String sql = "insert into upload(url)\n"
                + "values(?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, file);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="Tag">
    public List<Tag> getTags(int post_id) {
        List<Tag> list = new ArrayList<>();
        String sql = "select t.* from tags t "
                + "inner join tag_post tp on t.tag_id = tp.tag_id "
                + "where tp.post_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, post_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Tag tag = new Tag(rs.getInt("tag_id"), rs.getString("tag_name"));
                list.add(tag);
            }
            st.close();
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return list;
    }
    // </editor-fold>

    public boolean checkThumbnailExist(String fileName) {
        String sql = "select * from posts where thumbnail like ?";
        System.out.println(fileName);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "images/post-thumbnails/" + fileName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return false;
    }

    public boolean changeThumbnail(int id, String imagePath) {
        String sql = "update posts set thumbnail=? where post_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, imagePath);
            st.setInt(2, id);
            st.executeUpdate();
            return true;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return false;
    }

    public void updatePost(String title, String content, boolean featured, String subTitle, int subcate, int postId) {
        String sql = "update posts\n"
                + "set title=?, updated_date = GETDATE(), post_details=?, featured = ?, sub_title = ?, post_subcategories_id = ? where post_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, title);
            st.setString(2, content);
            st.setBoolean(3, featured);
            st.setString(4, subTitle);
            st.setInt(5, subcate);
            st.setInt(6, postId);
            st.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public int getNumberOfPostsByDay(LocalDate start) {
        String sql = "select count(post_id) from posts where publication_date < ? ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, start.plusDays(1).toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return 0;
    }

    public List getPostByDays(LocalDate start, LocalDate end) {
        List list = new ArrayList<>();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            list.add(getNumberOfPostsByDay(i));
        }

        return list;
    }

    public static void main(String[] args) {
        PostDAO pd = new PostDAO();
        System.out.println(pd.getPostWithSubCategory(1).getPost_details());
    }
}
