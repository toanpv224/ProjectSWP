/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import static controller.authentication.RegisterServlet.getMd5;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import util.SendingEmailUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import model.Account;
import model.Customer;
import model.HistoryProfile;
import model.Slider;
import model.Role;
import model.UpdateDate;
import service.EmailService;
import service.EmailServiceIml;

/**
 *
 * @author tretr
 */
public class AccountDAO extends DBContext {

    public Account getAccountByUsernamePassword(String username, String password) {
        try {
            String sql = "SELECT *"
                    + "  FROM [accounts]\n"
                    + "  WHERE [username] = ? AND [password] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("full_name"), rs.getInt("role_id"), rs.getBoolean("gender"), rs.getString("email"),
                        rs.getString("city"), rs.getString("country"), rs.getString("address"), rs.getString("phone"),
                        rs.getString("image_url"), rs.getBoolean("featured"), rs.getString("hash"), rs.getInt("active"));
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Account getAccountByID(int id) {
        Account account = new Account();
        account.setUser_id(id);
        return getAccountByID(account);
    }

    public static List<String> getListScreen(int role_id) {
        try {
            Connection conn = DBContext.getConnection();
            List<String> s = new ArrayList<>();
            String sql = "SELECT s.screen_name\n"
                    + "FROM [permissions] p  join [screens] s \n"
                    + "ON p.screen_id = s.screen_id\n"
                    + "WHERE p.[role_id] = ? AND p.[status] = 1";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, role_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                s.add(rs.getString("screen_name"));
            }
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Role> getListRole() {
        List<Role> s = new ArrayList<>();
        try {
            Connection conn = DBContext.getConnection();
            String sql = "SELECT [role_id]\n"
                    + "      ,[role_name]\n"
                    + "  FROM [roles]";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                if (rs.getInt("role_id") != 6) {
                    Role r = new Role();
                    r.setrId(rs.getInt("role_id"));
                    r.setrName(rs.getString("role_name"));
                    s.add(r);
                }
            }
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Role getRoleByID(int role_id) {
        Role role = new Role();
        try {
            Connection conn = DBContext.getConnection();
            String sql = "SELECT [role_id]\n"
                    + "      ,[role_name]\n"
                    + "  FROM [roles]\n"
                    + "where role_id="
                    + role_id;
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {

                role.setrId(rs.getInt("role_id"));
                role.setrName(rs.getString("role_name"));

            }

            return role;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Account> searchByRid(int did, int gender, int active, String search) {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT a.[user_id]\n"
                    + "      ,a.[username]\n"
                    + "      ,a.[password]\n"
                    + "      ,a.[full_name]\n"
                    + "      ,a.[role_id]\n"
                    + "      ,a.[gender]\n"
                    + "      ,a.[email]\n"
                    + "      ,a.[city]\n"
                    + "      ,a.[country]\n"
                    + "      ,a.[address]\n"
                    + "      ,a.[phone]\n"
                    + "      ,a.[image_url]\n"
                    + "      ,a.[featured]\n"
                    + "      ,a.[hash]\n"
                    + "      ,a.[active]\n"
                    + "      ,a.[registered_date]\n"
                    + "	  ,r.[role_name]\n"
                    + "  FROM [accounts] a INNER JOIN [roles] r on a.[role_id] = r.[role_id]";
            if (did > 0 && gender > -1 && active > -1 && search != "") {
                sql += " WHERE a.[role_id] = ? and a.[gender] = ? and a.[active] = ? and a.[full_name] like ? or a.[phone] like ? or a.[email] like ?";
            }
            if (did > 0 && gender > -1 && active > -1 && search == "") {
                sql += " WHERE a.[role_id] = ? and a.[gender] = ? and a.[active] = ?";
            }
            if (did == 0 && gender > -1 && active > -1 && search != "") {
                sql += " WHERE a.[gender] = ? and a.[active] = ? and a.[full_name] like ? or a.[phone] like ? or a.[email] like ?";
            }
            if (did > 0 && gender == -1 && active > -1 && search != "") {
                sql += " WHERE a.[role_id] = ? and a.[active] = ? and a.[full_name] like ? or a.[phone] like ? or a.[email] like ?";
            }
            if (did > 0 && gender > -1 && active == -1 && search != "") {
                sql += " WHERE a.[role_id] = ? and a.[gender] = ? and a.[full_name] like ? or a.[phone] like ? or a.[email] like ?";
            }
            if (did == 0 && gender > -1 && active > -1 && search == "") {
                sql += " WHERE a.[gender] = ? and a.[active] = ?";
            }
            if (did > 0 && gender == -1 && active > -1 && search == "") {
                sql += " WHERE a.[role_id] = ? and a.[active] = ?";
            }
            if (did > 0 && gender > -1 && active == -1 && search == "") {
                sql += " WHERE a.[role_id] = ? and a.[gender] = ?";
            }
            if (did == 0 && gender == -1 && active > -1 && search != "") {
                sql += " WHERE a.[active] = ? and a.[full_name] like ? or a.[phone] like ? or a.[email] like ?";
            }
            if (did > 0 && gender == -1 && active == -1 && search != "") {
                sql += " WHERE a.[role_id] = ? and a.[full_name] like ? or a.[phone] like ? or a.[email] like ?";
            }
            if (did == 0 && gender > -1 && active == -1 && search != "") {
                sql += " WHERE a.[gender] = ? and a.[full_name] like ? or a.[phone] like ? or a.[email] like ?";
            }
            if (did == 0 && gender == -1 && active > -1 && search == "") {
                sql += " WHERE a.[active] = ?";
            }
            if (did > 0 && gender == -1 && active == -1 && search == "") {
                sql += " WHERE a.[role_id] = ?";
            }
            if (did == 0 && gender > -1 && active == -1 && search == "") {
                sql += " WHERE a.[gender] = ?";
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            if (did > 0 && gender > -1 && active > -1 && search != "") {
                stm.setInt(1, did);
                stm.setInt(2, gender);
                stm.setInt(3, active);
                stm.setString(4, "%" + search + "%");
                stm.setString(5, "%" + search + "%");
                stm.setString(6, "%" + search + "%");
            }
            if (did > 0 && gender > -1 && active > -1 && search == "") {
                stm.setInt(1, did);
                stm.setInt(2, gender);
                stm.setInt(3, active);
            }
            if (did == 0 && gender > -1 && active > -1 && search != "") {
                stm.setInt(1, gender);
                stm.setInt(2, active);
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");
                stm.setString(5, "%" + search + "%");
            }
            if (did > 0 && gender == -1 && active > -1 && search != "") {
                stm.setInt(1, did);
                stm.setInt(2, active);
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");
                stm.setString(5, "%" + search + "%");
            }
            if (did > 0 && gender > -1 && active == -1 && search != "") {
                stm.setInt(1, did);
                stm.setInt(2, gender);
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");
                stm.setString(5, "%" + search + "%");
            }
            if (did == 0 && gender > -1 && active > -1 && search == "") {
                stm.setInt(1, gender);
                stm.setInt(2, active);
            }
            if (did > 0 && gender == -1 && active > -1 && search == "") {
                stm.setInt(1, did);
                stm.setInt(2, active);
            }
            if (did > 0 && gender > -1 && active == -1 && search == "") {
                stm.setInt(1, did);
                stm.setInt(2, gender);
            }
            if (did == 0 && gender == -1 && active > -1 && search != "") {
                stm.setInt(1, active);
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");
            }
            if (did > 0 && gender == -1 && active == -1 && search != "") {
                stm.setInt(1, did);
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");
            }
            if (did == 0 && gender > -1 && active == -1 && search != "") {
                stm.setInt(1, gender);
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");
            }
            if (did == 0 && gender == -1 && active > -1 && search == "") {
                stm.setInt(1, active);
            }
            if (did > 0 && gender == -1 && active == -1 && search == "") {
                stm.setInt(1, did);
            }
            if (did == 0 && gender > -1 && active == -1 && search == "") {
                stm.setInt(1, gender);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setrId(rs.getInt("role_id"));
                r.setrName(rs.getString("role_name"));
                Account a = new Account();
                a.setUser_id(rs.getInt("user_id"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                a.setFull_name(rs.getString("full_name"));
                a.setRole(r);
                a.setGender(rs.getBoolean("gender"));
                a.setEmail(rs.getString("email"));
                a.setCity(rs.getString("city"));
                a.setCountry(rs.getString("country"));
                a.setPhone(rs.getString("phone"));
                a.setImage_url(rs.getString("image_url"));
                a.setActive(rs.getInt("active"));
                accounts.add(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }

    public List<Account> getListByPage(List<Account> list, int start, int end) {
        ArrayList<Account> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public void updateAcc(Account model) {
        try {
            String sql = "UPDATE [accounts]\n"
                    + "   SET [full_name] = ?\n"
                    + "      ,[role_id] = ?\n"
                    + "      ,[gender] = ?\n"
                    + "      ,[email] = ?\n"
                    + "      ,[phone] = ?\n"
                    + "      ,[active] = ?\n"
                    + " WHERE [user_id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, model.getFull_name());
            stm.setBoolean(3, model.isGender());
            stm.setInt(2, model.getRole().getrId());
            stm.setString(4, model.getEmail());
            stm.setString(5, model.getPhone());
            stm.setInt(6, model.getActive());
            stm.setInt(7, model.getUser_id());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Account getAccountByID(Account acc) {
        try {
            String sql = "SELECT a.*, r.role_name FROM [accounts] a inner join [roles] r on a.[role_id] = r.[role_id] WHERE [user_id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, acc.getUser_id());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Role r = new Role();
                r.setrId(rs.getInt("role_id"));
                r.setrName(rs.getString("role_name"));
                Account a = new Account();
                a.setUser_id(rs.getInt("user_id"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                a.setFull_name(rs.getString("full_name"));
                a.setRole(r);
                a.setAddress(rs.getString("address"));
                a.setGender(rs.getBoolean("gender"));
                a.setEmail(rs.getString("email"));
                a.setCity(rs.getString("city"));
                a.setCountry(rs.getString("country"));
                a.setPhone(rs.getString("phone"));
                a.setImage_url(rs.getString("image_url"));
                a.setActive(rs.getInt("active"));
                a.setFeature(rs.getBoolean("featured"));
                return a;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//    public Account getAccountByID(int id) {
//        try {
//            String sql = "SELECT *"
//                    + "  FROM [accounts]\n"
//                    + "  WHERE [user_id] = ?";
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, id);
//            ResultSet rs = stm.executeQuery();
//            if (rs.next()) {
//                Account account = new Account(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
//                        rs.getString("full_name"), rs.getInt("role_id"), rs.getBoolean("gender"), rs.getString("email"),
//                        rs.getString("city"), rs.getString("country"), rs.getString("address"), rs.getString("phone"),
//                        rs.getString("image_url"), rs.getBoolean("featured"), rs.getString("hash"), rs.getInt("active"));
//                return account;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

    public Account getAccountByEmail(String email) {
        try {
            String sql = "SELECT *"
                    + "  FROM [accounts]\n"
                    + "  WHERE [email] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("full_name"), rs.getInt("role_id"), rs.getBoolean("gender"), rs.getString("email"),
                        rs.getString("city"), rs.getString("country"), rs.getString("address"), rs.getString("phone"),
                        rs.getString("image_url"), rs.getBoolean("featured"), rs.getString("hash"), rs.getInt("active"));
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Account checkAccountExist(String user) {
        String sql = "select * from [accounts]\n"
                + "where [username] = ?\n";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public Account checkEmailExist(String mail) {
        String sql = "select * from [accounts]\n"
                + "where [email] = ?\n";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, mail);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean checkAccountImageExist(String img) {
        String sql = "SELECT [image_url]\n"
                + "  FROM [accounts]\n"
                + "  WHERE [image_url] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "images\\account-images\\" + img);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public String singup(Account acc, int choise) {
        String sql = "INSERT INTO [dbo].[accounts]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[full_name]\n"
                + "           ,[gender]\n"
                + "           ,[email]\n"
                + "           ,[phone]\n"
                + "           ,[address]\n"
                + "           ,[hash]\n"
                + "           ,[image_url]\n"
                + "           ,[featured]\n"
                + "           ,[city]\n"
                + "           ,[country]\n"
                + "           ,[registered_date]\n"
                + "           ,[role_id]\n"
                + "           ,[active])\n"
                + "     VALUES";
        if (choise == 1) {
            sql += "(?,?,?,?,?,?,?,?,'images\\account-images\\acc.png',"+1+",'(Nah)','(Nah)',getdate(),?,'1')";
        } else {
            sql += "(?,?,?,?,?,?,?,?,'images\\account-images\\acc.png',"+1+",'(Nah)','(Nah)',getdate(),1,'0')";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, acc.getUsername());
            stm.setString(2, acc.getPassword());
            stm.setString(3, acc.getFull_name());
            stm.setBoolean(4, acc.isGender());
            stm.setString(5, acc.getEmail());
            stm.setString(6, acc.getPhone());
            stm.setString(7, acc.getAddress());
            stm.setString(8, acc.getMyHash());
            if (choise == 1) {
                stm.setInt(9, acc.getRole_id());
            }
            int i = stm.executeUpdate();
            if (i != 0) {
                //send Email
                return "Success";
            }
        } catch (Exception e) {
        }
        return "Success";
    }

    public UpdateDate getUpdateDate(int id) {
        String sql = "select * from update_date\n"
                + "where id="
                + id;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new UpdateDate(rs.getInt("id"), rs.getInt("user_id"), rs.getTimestamp("date"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;//exception
    }

    public String AddCustomer(Customer acc) {
        //check id exist
        if (checkAccountExist(acc.getUsername()) != null) {
            return "User name have been exist";
        }
        String sql = "INSERT INTO [dbo].[accounts]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[full_name]\n"
                + "           ,[gender]\n"
                + "           ,[email]\n"
                + "           ,[phone]\n"
                + "           ,[address]\n"
                + "           ,[hash]\n"
                + "           ,[image_url]\n"
                + "           ,[city]\n"
                + "           ,[country]\n"
                + "           ,[registered_date]\n"
                + "           ,[role_id]\n"
                + "           ,[active]\n"
                + "           ,[featured])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?,?,getdate(),?,?,?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, acc.getUsername());
            stm.setString(2, acc.getPassword());
            stm.setString(3, acc.getFull_name());
            stm.setBoolean(4, acc.isGender());
            stm.setString(5, acc.getEmail());
            stm.setString(6, acc.getPhone());
            stm.setString(7, acc.getAddress());
            stm.setString(8, acc.getMyHash());
            stm.setString(9, acc.getImage_url());
            stm.setString(10, acc.getCity());
            stm.setString(11, acc.getCountry());
            stm.setInt(12, acc.getRole_id());
            stm.setInt(13, acc.getActive());
            stm.setBoolean(14, acc.isFeature());
            int i = stm.executeUpdate();
            if (i != 0) {
                //send Email
                return "Success";
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Fail";
    }

    public void updateAccount(Account a) {
        String sql = "UPDATE [accounts]\n"
                + "   SET [full_name] = ?\n"
                + "      ,[gender] = ?\n"
                + "      ,[city] = ?\n"
                + "      ,[country] = ?\n"
                + "      ,[address] = ?\n"
                + "      ,[phone] = ?\n"
                + " WHERE [user_id] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, a.getFull_name());
            stm.setBoolean(2, a.isGender());
            stm.setString(3, a.getCity());
            stm.setString(4, a.getCountry());
            stm.setString(5, a.getAddress());
            stm.setString(6, a.getPhone());
            stm.setInt(7, a.getUser_id());

            ResultSet rs = stm.executeQuery();
        } catch (SQLException e) {
        }
    }

    public void deleteAccount(String aId) {
        String query = "DELETE FROM [accounts]\n"
                + "      WHERE [role_id] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, aId);
            ResultSet rs = stm.executeQuery();
        } catch (Exception e) {
        }
    }

    public void updateAccountImg(Account a) {
        String sql = "UPDATE [accounts]\n"
                + "   SET [image_url] = ?\n"
                + " WHERE [user_id] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, a.getImage_url());
            stm.setInt(2, a.getUser_id());

            ResultSet rs = stm.executeQuery();
        } catch (SQLException e) {
        }
    }

    public void updateHashCode(Account acc) {
        String sql = "UPDATE [accounts]\n"
                + "   SET [hash] = ?\n"
                + " WHERE [email] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, acc.getMyHash());
            stm.setString(2, acc.getEmail());
            ResultSet rs = stm.executeQuery();
        } catch (SQLException e) {
        }
    }

    public void updateFeatured(Account acc, String text) {
        String sql = "UPDATE [accounts]\n"
                + "   SET [featured] = ?\n"
                + " WHERE [email] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, text);
            stm.setString(2, acc.getEmail());
            ResultSet rs = stm.executeQuery();
        } catch (SQLException e) {
        }
    }

    public void updatePassword(String mail, String pass) {
        String sql = "UPDATE [accounts]\n"
                + "   SET [password] = ?\n"
                + " WHERE [email] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, pass);
            stm.setString(2, mail);
            ResultSet rs = stm.executeQuery();
        } catch (SQLException e) {
        }
    }

    public int getTotalCustomers() {
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

    public int getNumberOfRegisteredCustomerByDay(LocalDate start) {
        String sql = "select COUNT(user_id) from accounts where registered_date between ? and ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, start.toString());
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

    public List getCustomersByDays(LocalDate start, LocalDate end) {
        List list = new ArrayList<>();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            list.add(getNumberOfRegisteredCustomerByDay(i));
        }

        return list;
    }

    public int getNumberOfTotalCustomersByDay(LocalDate start) {
        String sql = "select COUNT(user_id) from accounts where role_id = 1 and registered_date < ? ";
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

    public List getTotalCustomersByDay(LocalDate start, LocalDate end) {
        List list = new ArrayList<>();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            list.add(getNumberOfTotalCustomersByDay(i));
        }

        return list;
    }

    public List<Account> getAuthors() {
        List<Account> list = new ArrayList<>();
        try {
            String sql = "select distinct a.user_id from  accounts a \n"
                    + "inner join \n"
                    + "posts p on a.user_id=p.user_id\n"
                    + "group by a.user_id";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = getAccountByID(rs.getInt("user_id"));
                list.add(account);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //convert order id to string 
    private String convertOrderID(int id) {
        String content = "";
        switch (id) {
            case 1:
                return "user_id";
            case 2:
                return "full_name";
            case 3:
                return "gender";
            case 4:
                return "email";
            case 5:
                return "featured";
            default:
                return "user_id";//default 
        }
    }

    //get property of customer from database
    public Customer fillCustomerDetails(ResultSet rs) {
        try {
            return new Customer(rs.getInt("user_id"), rs.getString("full_name"),
                    rs.getBoolean("gender"), rs.getString("email"), rs.getString("phone"), rs.getBoolean("featured"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null; //exception
    }

    //count page of customer search
    public int countCustomerPaging(String word, int searchOption, int status, int numperpage) {
        int num = 1;//default
        String sql = "select  count(user_id) from\n"
                + "accounts where role_id=1";
        if (!word.equals("") && searchOption == 1) { //have option word
            sql += " and  full_name like '%"
                    + word
                    + "%'";
        }
        if (!word.equals("") && searchOption == 2) { //have option word
            sql += " and  email like '%"
                    + word
                    + "%'";
        }
        if (!word.equals("") && searchOption == 3) { //have option word
            sql += " and  phone like '%"
                    + word
                    + "%'";
        }
        if (status == 1) {
            sql += " and featured ="
                    + 1;
        }
        if (status == 0) {
            sql += " and featured ="
                    + 0;
        }
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

    //Get customer paging for customer list in admin role
    public List<Customer> getCustomers(String word, int searchOption, int status, int orderID, int op, int page, int numperpage) {
        List<Customer> list = new ArrayList<>();
        String sql = "select  user_id,full_name,featured, email,phone,gender from\n"
                + "accounts where role_id=1";
        if (!word.equals("") && searchOption == 1) { //have option word
            sql += " and  full_name like '%"
                    + word
                    + "%'";
        }
        if (!word.equals("") && searchOption == 2) { //have option word
            sql += " and  email like '%"
                    + word
                    + "%'";
        }
        if (!word.equals("") && searchOption == 3) { //have option word
            sql += " and  phone like '%"
                    + word
                    + "%'";
        }
        if (status == 1) {
            sql += " and featured ="
                    + 1;
        }
        if (status == 0) {
            sql += " and featured ="
                    + 0;
        }
        sql += " \norder by";
        sql += " " + convertOrderID(orderID);
        sql += (op == 1) ? " asc" : " desc";

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
                Customer customer = fillCustomerDetails(rs);
                list.add(customer);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    //update status of slider
    public boolean updateFeatured(int id, int featured) {
        String sql = "UPDATE Accounts\n"
                + "SET featured = "
                + featured
                + "\n"
                + "WHERE user_id="
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

    public List getListOfNewlyRegisterCustomer(LocalDate start, LocalDate end) {
        List result = new ArrayList();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            result.add(RegisteredCustomerByDay(i));
        }
        return result;
    }

    private int RegisteredCustomerByDay(LocalDate start) {
        String sql = "select COUNT(*) from accounts where registered_date between ? and ?";
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

    public List getListOfNewlyBoughtCustomer(LocalDate start, LocalDate end) {
        List result = new ArrayList();
        for (LocalDate i = start; i.compareTo(end) < 0; i = i.plusDays(1)) {
            result.add(NewlyBoughtCustomerByDay(i));
        }
        return result;
    }

    private int NewlyBoughtCustomerByDay(LocalDate start) {
        String sql = "select count(distinct o.user_id) from orders o\n"
                + "inner join accounts a\n"
                + "on o.user_id = a.user_id\n"
                + "where order_date between ? and ?";
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

    //get history information
    //update customer information
    public void updateCustomer(Account a, String update_by) {
        //insert into account table
        String sql = "UPDATE [accounts]\n"
                + "   SET [full_name] = ?\n"
                + "      ,[gender] = ?\n"
                + "      ,[city] = ?\n"
                + "      ,[country] = ?\n"
                + "      ,[address] = ?\n"
                + "      ,[phone] = ?\n"
                + "      ,[featured]=?\n"
                + " WHERE [user_id] = ?";
        try{
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, a.getFull_name());
            stm.setBoolean(2, a.isGender());
            stm.setString(3, a.getCity());
            stm.setString(4, a.getCountry());
            stm.setString(5, a.getAddress());
            stm.setString(6, a.getPhone());
            stm.setBoolean(7, a.isFeature());
            stm.setInt(8, a.getUser_id());
            
            stm.execute();
       
            
            int update_id = 0;
            //insert update date
            String sql1 = "insert into update_date (user_id,date)\n"
                    + "values(?,getdate())";
            PreparedStatement st1 = connection.prepareStatement(sql1);
            st1.setInt(1, a.getUser_id());
            st1.executeUpdate();

            //get  id of this update
            String sql2 = "select top 1 u.id from update_date u\n"
                    + "where u.user_id=? "
                    + "\norder by date desc";
            PreparedStatement st2 = connection.prepareStatement(sql2);
            st2.setInt(1, a.getUser_id());
            ResultSet rs2 = st2.executeQuery();
            if (rs2.next()) {
                update_id = rs2.getInt("id");
            }
            //insert  into history_table
            String sql3 = "insert into history_profile (update_id,full_name,gender,email,address,phone,feature,update_by)\n"
                    + "values(?,?,?,?,?,?,?,?)";
            PreparedStatement st3 = connection.prepareStatement(sql3);
            st3.setInt(1, update_id);
            st3.setString(2, a.getFull_name());
            st3.setBoolean(3, a.isGender());
            st3.setString(4, a.getEmail());
            st3.setString(5, a.getAddress());
            st3.setString(6, a.getPhone());
            st3.setBoolean(7, a.isFeature());
            st3.setString(8, update_by);
            st3.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public HistoryProfile fillHistoryProfile(ResultSet rs) {
        try {
            UpdateDate update = getUpdateDate(rs.getInt("update_id"));
            String full_name = rs.getString("full_name");
            boolean gender = rs.getBoolean("gender");
            String email = rs.getString("email");
            String address = rs.getString("address");
            String phone = rs.getString("phone");
            boolean feature = rs.getBoolean("feature");
            String update_by =rs.getString("update_by");
            return new HistoryProfile(update, full_name, gender, email, address, phone, feature, update_by);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;//exception or not found
    }
    //paging
    public List<HistoryProfile> getHistory_profiles(int user_id , int page, int numperpage) {
        List<HistoryProfile> list = new ArrayList<>();
        String sql ="select u.date,h.full_name,h.gender,h.email,h.address,h.phone,h.feature,h.update_by,h.update_id from accounts a\n" +
"inner join update_date u\n" +
"on u.user_id=a.user_id\n" +
"inner join history_profile h\n" +
"on h.update_id=u.id\n" +
"where u.user_id="
                + user_id;
                sql += " order by date desc";
                 sql += " OFFSET "
                + (page - 1) * numperpage
                + " ROWS FETCH NEXT "
                + numperpage
                + " ROWS ONLY";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                HistoryProfile history = fillHistoryProfile(rs);
                list.add(history);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
        public int countPagingHistories(int user_id , int page, int numperpage){
            int num=1;
             String sql="select count(h.id) from accounts a\n" +
            "inner join update_date u\n" +
            "on u.user_id=a.user_id\n" +
            "inner join history_profile h\n" +
            "on h.update_id=u.id\n" +
            "where u.user_id="
                     + user_id;
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
        } else if (num % numperpage == 0) { //number full apge
            return num / numperpage;
        }
        return num / numperpage + 1;

        }
    public boolean changeActive(int user_id, int active_status) {
        String sql = "update accounts set active = ? where user_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, active_status);
            st.setInt(2, user_id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean changeRole(int user_id, int role_id) {
        String sql = "update accounts set role_id = ? where user_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, role_id);
            st.setInt(2, user_id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {

        }
        return false;
    }

    public Role getRole(int role_id) {
        String sql = "select * from roles where role_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, role_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Role role = new Role(
                        rs.getInt("role_id"),
                        rs.getString("role_name")
                );
                return role;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public boolean creatUser(Account a) {
        String sql = "insert into accounts (username, password, full_name, role_id, gender, email, city, country, address, phone, featured, hash, active, registered_date) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";
        try {
            Random random = new Random();
            random.nextInt(9999999);
            String myHash = getMd5("" + random);

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, a.getUsername());
            st.setString(2, getMd5(a.getPassword()));
            st.setString(3, a.getFull_name());
            st.setInt(4, a.getRole_id());
            st.setInt(5, a.isGender() ? 1: 0);
            st.setString(6, a.getEmail());
            st.setString(7, a.getCity());
            st.setString(8, a.getCountry());
            st.setString(9, a.getAddress());
            st.setString(10, a.getPhone());
            st.setInt(11, a.isFeature() ? 1 : 0);
            st.setString(12, myHash);
            st.setInt(13, a.getActive());
            System.out.println(sql);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {

        }
        return false;
    }

    private String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
