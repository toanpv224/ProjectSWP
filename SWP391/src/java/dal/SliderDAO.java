package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Slider;

public class SliderDAO extends DBContext {

//    public Slider fillSliderDetails(ResultSet rs) throws SQLException {
//        return new Slider(rs.getInt("slider_id"),
//                rs.getString("slider_image"),
//                rs.getString("title"),
//                rs.getString("slider_link"),
//                rs.getString("status"),
//                rs.getString("notes"));
//    }
    public List<Slider> getSliders() {
        List<Slider> list = new ArrayList<>();
        String sql = "select * from sliders where status=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, 1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Slider s = fillSliderDetails(rs);
                list.add(s);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return list;
    }

    private Slider fillSliderDetails(ResultSet rs) {
        try {
            return new Slider(rs.getInt("slider_id"),
                    rs.getString("slider_image"),
                    rs.getString("title"),
                    rs.getString("slider_link"),
                    rs.getInt("status"),
                    rs.getString("notes")); // bi
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    //get slider pagging
    public List<Slider> getSliders(String word, int searchOption, int status, int page, int numperpage) {
        List<Slider> list = new ArrayList<>();
        String sql = "select * from sliders\n"
                + "where slider_id=slider_id";

        if (!word.equals("") && searchOption == 1) { //have option word
            sql += " and  title like '%"
                    + word
                    + "%'";
        }
        if (!word.equals("") && searchOption == 2) { //have option word
            sql += " and  slider_link like '%"
                    + word
                    + "%'";
        }
        if (status == 1) {
            sql += " and status ="
                    + 1;
        }
        if (status == 0) {
            sql += " and status ="
                    + 0;
        }
        sql += " \norder by slider_id";
        sql += " OFFSET "
                + (page - 1) * numperpage
                + " ROWS FETCH NEXT "
                + numperpage
                + " ROWS ONLY";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Slider slider = fillSliderDetails(rs);
                list.add(slider);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    // count filter and paging

    public int countSliderPaging(String word, int searchOption, int status, int numperpage) {
        int num = 1;//default
        String sql = "select count(slider_id) from sliders\n"
                + "where slider_id=slider_id";

        if (!word.equals("") && searchOption == 1) { //have option word
            sql += " and  title like '%"
                    + word
                    + "%'";
        }
        if (!word.equals("") && searchOption == 2) { //have option word
            sql += " and  slider_link like '%"
                    + word
                    + "%'";
        }
        if (status == 1) {
            sql += " and status ="
                    + 1;
        }
        if (status == 0) {
            sql += " and status ="
                    + 0;
        }
        System.out.println(sql);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        if (num == 0) {
            return 1;//minimum=1
        } else if (num % numperpage == 0) { //number full page
            return num / numperpage;
        }
        return num / numperpage + 1;
    }

    //update status of slider
    public boolean updateStatusSlider(int id, int status) {
        String sql = "UPDATE sliders\n"
                + "SET status = "
                + status
                + "\n"
                + "WHERE slider_id="
                + id;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;//exception
    }

    public Slider getSlider(int id) {
        String sql = "select * from sliders where slider_id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Slider s = fillSliderDetails(rs);
                return s;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }

    public boolean changeSliderStatus(int id, int status) {
        String sql = "update sliders set status = ? where slider_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, status);
            st.setInt(2, id);
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return false;
    }

    public boolean changeSliderInformation(int id, String title, String backlink, String notes) {
        String sql = "update sliders set title = ?, slider_link = ?, notes = ? where slider_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, title);
            st.setString(2, backlink);
            st.setString(3, notes);
            st.setInt(4, id);
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return false;
    }

    public boolean changeSliderImage(int id, String filename) {
        String sql = "update sliders set slider_image = ? where slider_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, filename);
            st.setInt(2, id);
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return false;
    }
}
