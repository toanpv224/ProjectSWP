/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
//import java.util.Arrays.ArrayList;
import java.util.List;
import model.Feedback;
import util.DateTimeUtil;

/**
 *
 * @author tretr
 */
public class FeedbackDAO extends DBContext {

    public Feedback fillGeneralFeedbackDetails(ResultSet rs) throws SQLException {
        return new Feedback(
                rs.getInt("feedback_id"),
                rs.getInt("star"),
                rs.getString("feedbacks_content"),
                rs.getString("full_name"),
                rs.getString("phone"),
                rs.getInt("gender") == 1,
                rs.getString("email"),
                rs.getString("image_url"),
                rs.getTimestamp("feedback_date"),
                rs.getInt("status")
        );
    }

    public Feedback fillProductFeedbackDetails(ResultSet rs) throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        ProductDAO productDAO = new ProductDAO();
        return new Feedback(
                rs.getInt("feedback_id"),
                accountDAO.getAccountByID(rs.getInt("user_id")),
                productDAO.getProduct(rs.getInt("product_id")),
                rs.getInt("star"),
                rs.getString("feedbacks_content"),
                rs.getString("full_name"),
                rs.getString("phone"),
                rs.getInt("gender") == 1,
                rs.getString("email"),
                rs.getString("image_url"),
                rs.getTimestamp("feedback_date"),
                rs.getInt("status")
        );
    }

    public void insertFeedback(Feedback f) {
        String sql = "INSERT INTO [product_feedbacks]\n"
                + "           ([user_id]\n"
                + "           ,[product_id]\n"
                + "           ,[star]\n"
                + "           ,[feedbacks_content]\n"
                + "           ,[full_name]\n"
                + "           ,[phone]\n"
                + "           ,[gender]\n"
                + "           ,[email]\n"
                + "           ,[image_url]\n"
                + "           ,[feedback_date]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,GETDATE(), 1)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, f.getUser().getUser_id());
            stm.setInt(2, f.getProduct().getProduct_id());
            stm.setFloat(3, f.getStar());
            stm.setString(4, f.getContent());
            stm.setString(5, f.getFullname());
            stm.setString(6, f.getPhone());
            stm.setBoolean(7, f.isGender());
            stm.setString(8, f.getEmail());
            if (f.getImage_url().isEmpty()) {
                stm.setString(9, null);
            } else {
                stm.setString(9, f.getImage_url());
            }
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void insertGeneralFeedback(Feedback f) {
        String sql = "INSERT INTO [general_feedbacks]\n"
                + "           ([star]\n"
                + "           ,[feedbacks_content]\n"
                + "           ,[full_name]\n"
                + "           ,[phone]\n"
                + "           ,[gender]\n"
                + "           ,[email]\n"
                + "           ,[image_url]\n"
                + "           ,[feedback_date]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,GETDATE(), 1)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setFloat(1, f.getStar());
            stm.setString(2, f.getContent());
            stm.setString(3, f.getFullname());
            stm.setString(4, f.getPhone());
            stm.setBoolean(5, f.isGender());
            stm.setString(6, f.getEmail());
            if (f.getImage_url().isEmpty()) {
                stm.setString(7, null);
            } else {
                stm.setString(7, f.getImage_url());
            }
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }

    public boolean checkImageExist(String img) {
        String sql = "SELECT [image_url]\n"
                + "  FROM [product_feedbacks]\n"
                + "  WHERE [image_url] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "images\\feedback-images\\" + img);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean checkImageGeneralExist(String img) {
        String sql = "SELECT [image_url]\n"
                + "  FROM [general_feedbacks]\n"
                + "  WHERE [image_url] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "images\\feedback-images\\" + img);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public int getTotalFeedbacks(String startDate, String endDate) {
        String sql = "select count(user_id) from accounts";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return 0;
    }

    public int getTotalGeneralFeedbacks() {
        String sql = "select count(*) from general_feedbacks ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return 0;
    }

    public int getTotalProductFeedbacks() {
        String sql = "select count(*) from product_feedbacks";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return 0;
    }

    public int getNumberFeedbacksByDays(String date) {
        String sql = "select count(user_id) from accounts";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return 0;
    }

    public Feedback getGeneralFeedback(int id) {
        String sql = "select * from general_feedbacks where feedback_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return fillGeneralFeedbackDetails(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Feedback getProductFeedback(int id) {
        String sql = "select * from product_feedbacks where feedback_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return fillProductFeedbackDetails(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Feedback> getGeneralFeedbacksByRange(int start, int end) {
        int[] stars = {1, 2, 3, 4, 5};
        return getGeneralFeedbacksByRange("feedback_id desc", "", -1, stars, start, end);
    }

    public List<Feedback> getGeneralFeedbacksByRange(String orderOption, String key, int status, int[] stars, int start, int end) {
        /**
         * select * from ( select ROW_NUMBER() over (order by feedback_id asc)
         * as Row,* from general_feedbacks where full_name like '%%' and
         * [status] = '1' and star in (1, 2, 3, 4, 5) ) all_feedbacks where Row
         * between 1 and 22
         */

        List<Feedback> feedbacksList = new ArrayList<>();
        String sql = "select * from (select ROW_NUMBER() over (order by " + orderOption + ") as Row,* from general_feedbacks "
                + "where (full_name like '%" + key + "%' or full_name like '" + key + "%' or full_name like '%" + key + "') ";
        if (status != -1) {
            sql += "and status = " + status + " ";
        }

        if (stars.length > 0 && stars[0] != -1) {
            sql += " and star in (";
            for (int i : stars) {
                sql += i + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
        sql += ") all_feedbacks where Row between " + start + " and " + end;
        System.out.println(sql);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback feedback = fillGeneralFeedbackDetails(rs);
                feedbacksList.add(feedback);
            }
            return feedbacksList;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public int countGeneralFeedbacks(String key, int status, int[] stars) {
        /**
         * select count(*) from general_feedbacks where full_name like '%%' and
         * [status] = '1' and star in (1, 2, 3, 4, 5)
         */

        String sql = "select count(*) from general_feedbacks where (full_name like '%" + key + "%' or full_name like '" + key + "%' or full_name like '%" + key + "') ";
        if (status != -1) {
            sql += "and status = " + status + " ";
        }

        if (stars.length > 0 && stars[0] != -1) {
            sql += " and star in (";
            for (int i : stars) {
                sql += i + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
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

    public List<Feedback> getProductFeedbacksByRange(String orderOption, int productId, String key, int status, int[] stars, int start, int end) {
        /**
         * select * from ( select ROW_NUMBER() over (order by feedback_id asc)
         * as Row,* from general_feedbacks where full_name like '%%' and
         * [status] = '1' and star in (1, 2, 3, 4, 5) ) all_feedbacks where Row
         * between 1 and 22
         */

        List<Feedback> feedbacksList = new ArrayList<>();
        String sql = "select * from (select ROW_NUMBER() over (order by " + orderOption + ") as Row,* from product_feedbacks "
                + "where (full_name like '%" + key + "%' or full_name like '" + key + "%' or full_name like '%" + key + "') ";
        if (productId > 0) {
            sql += " and product_id = " + productId;
        }
        if (status != -1) {
            sql += "and status = " + status + " ";
        }

        if (stars.length > 0 && stars[0] != -1) {
            sql += " and star in (";
            for (int i : stars) {
                sql += i + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
        sql += ") all_feedbacks where Row between " + start + " and " + end;
        System.out.println(sql);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback feedback = fillProductFeedbackDetails(rs);
                feedbacksList.add(feedback);
            }
            return feedbacksList;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public int countProductFeedbacks(int productId, String key, int status, int[] stars) {
        /**
         * select count(*) from general_feedbacks where full_name like '%%' and
         * [status] = '1' and star in (1, 2, 3, 4, 5)
         */

        List<Feedback> feedbacksList = new ArrayList<>();
        String sql = "select Count(*) from product_feedbacks "
                + "where (full_name like '%" + key + "%' or full_name like '" + key + "%' or full_name like '%" + key + "') ";
        if (productId > 0) {
            sql += " and product_id = " + productId;
        }
        if (status != -1) {
            sql += "and status = " + status + " ";
        }

        if (stars.length > 0 && stars[0] != -1) {
            sql += " and star in (";
            for (int i : stars) {
                sql += i + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
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

    public boolean changeGeneralFeedbackStatus(int id, int status) {
        String sql = "update general_feedbacks set status = ? where feedback_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, status);
            st.setInt(2, id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean changeProductFeedbackStatus(int id, int status) {
        String sql = "update product_feedbacks set status = ? where feedback_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, status);
            st.setInt(2, id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public String getAverageRated(int id, String time) {
        DecimalFormat df = new DecimalFormat("#.#");
        String end = DateTimeUtil.Now();
        String start = DateTimeUtil.getStartDate(time).toString();
        String sql = "select AVG(star) from products p\n"
                + "inner join product_feedbacks pf\n"
                + "on p.product_id = pf.product_id\n"
                + "inner join product_sub_categories psc \n"
                + "on p.sub_category_id = psc.sub_category_id\n"
                + "inner join product_categories pc \n"
                + "on psc.category_id = pc.category_id\n"
                + "where feedback_date between '" + start + "' and '" + end + "'";
        if (id != 0) {
            sql += " and pc.category_id = " + id;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return df.format(rs.getDouble(1));
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public int getNumberOfTotalFeedbacksByDay(LocalDate start) {
        String sql = "select (select COUNT(feedback_id) from product_feedbacks where feedback_date < ?) + (select COUNT(feedback_id) from general_feedbacks where feedback_date < ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, start.plusDays(1).toString());
            st.setString(2, start.plusDays(1).toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return 0;
    }

    public List getTotalFeedbacksByDay(LocalDate start, LocalDate end) {
        List list = new ArrayList<>();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            list.add(getNumberOfTotalFeedbacksByDay(i));
        }

        return list;
    }

    public static void main(String[] args) {
        FeedbackDAO fd = new FeedbackDAO();
        System.out.println(fd.getAverageRated(0, "1"));
    }
}
