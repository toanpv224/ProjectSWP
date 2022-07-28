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
import model.Role;
import model.Setting;
import org.jboss.weld.util.collections.ArrayListSupplier;

/**
 *
 * @author Admin
 */
public class SettingDAO extends DBContext {

    public List<Setting> getSettingsByPage(int start, int end, String orderOption) {
        String sql = "select * from (select ROW_NUMBER() over(order by setting_id) as Row, * from settings) a where Row between ? and ?";
        List<Setting> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, start);
            st.setInt(2, end);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Setting s = new Setting(rs.getInt("setting_id"), rs.getString("name"), rs.getString("description"), rs.getString("type"), rs.getString("value"), rs.getString("order"), rs.getInt("status"));
                list.add(s);
            }
        } catch (SQLException e) {

        }
        return list;
    }
//    ) a where Row between ? and ?";

    public Setting getSettingsByID(int settingID) {
        String sql = "select * from [settings] where [setting_id] = ?";
        List<Setting> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, settingID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return new Setting(rs.getInt("setting_id"), rs.getString("name"), rs.getString("description"), rs.getString("type"), rs.getString("value"), rs.getString("order"), rs.getInt("status"));
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public List<Setting> getSettingsByPage(int start, int end, String orderOption, String[] type, String[] status, String key) {
        String sql = "select * from (select ROW_NUMBER() over(order by " + orderOption + ") as Row, * from settings where 1 = 1";
        if (type != null) {
            sql += " and type in(";
            for (String string : type) {
                sql += "'" + string + "',";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
        if (status != null) {
            sql += " and status in(";
            for (String string : status) {
                sql += string + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
        sql += " and value like '%" + key + "%'";
        sql += ") a where Row between ? and ?";
        System.out.println(sql);
        List<Setting> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, start);
            st.setInt(2, end);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Setting s = new Setting(rs.getInt("setting_id"), rs.getString("name"), rs.getString("description"), rs.getString("type"), rs.getString("value"), rs.getString("order"), rs.getInt("status"));
                list.add(s);
            }
        } catch (SQLException e) {

        }
        return list;
    }

    public int getTotalSettings(String[] type, String[] status, String key) {
        String sql = "select count(setting_id) from settings where 1 = 1";
        if (type != null) {
            sql += " and type in(";
            for (String string : type) {
                sql += "'" + string + "',";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
        if (status != null) {
            sql += " and status in(";
            for (String string : status) {
                sql += string + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
        }
        sql += " and value like '%" + key + "%'";
        System.out.println(sql);

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

    public int getTotalSetting() {
        String sql = "select COUNT(setting_id) from settings";
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

    public List getTypes() {
        String sql = "select distinct type from settings";
        List l = new ArrayList();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                l.add(rs.getString(1));
            }
        } catch (SQLException e) {

        }
        return l;
    }

    public List getValue() {
        String sql = "select * from [screens]";
        List l = new ArrayList();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                l.add(rs.getString("screen_description"));
            }
        } catch (SQLException e) {

        }
        return l;
    }

    public static int getScreenID(String s) {
        String sql = "SELECT [screen_id]\n"
                + "      ,[screen_name]\n"
                + "      ,[screen_description]\n"
                + "  FROM [screens]\n"
                + "  WHERE [screen_description] like ?";
        int i = 0;
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, s);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                i = rs.getInt("screen_id");
            }
        } catch (SQLException e) {

        }
        return i;
    }

    public List<Role> getOrder() {
        String sql = "select * from roles";
        List<Role> l = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setrId(rs.getInt(1));
                r.setrName(rs.getString(2));
                l.add(r);
            }
        } catch (SQLException e) {

        }
        return l;
    }

    public void updateSetting(Setting s) {
        String sql = "UPDATE [settings]\n"
                + "   SET [description] = ?\n"
                + "      ,[status] = ?\n"
                + " WHERE [setting_id] = ?\n"
                + " UPDATE [permissions]\n"
                + "   SET [status] = ?\n"
                + "   FROM [permissions] p join [screens] s \n"
                + "ON p.screen_id = s.screen_id\n"
                + "WHERE s.screen_description like ? AND p.[role_id] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, s.getDescription());
            st.setInt(2, s.getStatus());
            st.setInt(3, s.getSettingId());
            st.setInt(4, s.getStatus());
            st.setString(5, s.getValue());
            st.setInt(6, Integer.parseInt(s.getOrder()));

            ResultSet rs = st.executeQuery();
        } catch (SQLException e) {

        }
    }

    public void insertRoleToGetId(String s) {
        String sql = "INSERT INTO [roles]\n"
                + "           ([role_name])\n"
                + "     VALUES\n"
                + "           (?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, s);
            ResultSet rs = st.executeQuery();
        } catch (SQLException e) {

        }
    }

    public int getRoleId(String s) {
        String sql = "SELECT [role_id]\n"
                + "FROM [roles]\n"
                + "WHERE [role_name] like ?";
        int i = 0;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, s);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                i = rs.getInt(1);
                return i;
            }
        } catch (SQLException e) {

        }
        return i;
    }

    public void insertRoleSetting(Setting s) {
        String sql = "INSERT INTO [settings]\n"
                + "           ([name]\n"
                + "           ,[description]\n"
                + "           ,[type]\n"
                + "           ,[value]\n"
                + "           ,[order]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,1)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, s.getName());
            st.setString(2, s.getDescription());
            st.setString(3, s.getType());
            st.setString(4, s.getValue());
            st.setString(5, s.getOrder());
            ResultSet rs = st.executeQuery();
        } catch (Exception e) {
        }
    }

    public void insertPermissions(Setting s) {
        String sql = "INSERT INTO [permissions]\n"
                + "           ([role_id]\n"
                + "           ,[screen_id]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "           (?,?,1)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(s.getOrder()));
            st.setInt(2, getScreenID(s.getValue()));
            
            ResultSet rs = st.executeQuery();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        SettingDAO d = new SettingDAO();
//        System.out.println(d.getSettingsByPage(0, 10, "setting_id asc", null, null, "ta").size());
//        System.out.println(d.getTotalSettings(null, null, "ta"));
        System.out.println(d.getRoleId("Sale"));
    }
}
