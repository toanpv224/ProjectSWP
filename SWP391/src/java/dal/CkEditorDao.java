package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CkEditorDao extends DBContext {
    private void truncate() {
        String sql = "TRUNCATE TABLE [dbo].[editor]";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    public String insertContent(String content) {
        truncate();
        
        String sql = "INSERT INTO [dbo].[editor]\n"
                + "           ([content])"
                + "     VALUES\n"
                + "           (N'" + content + "')";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            int i = stm.executeUpdate();
            if (i != 0) {
                return "Success";
            }
        } catch (Exception e) {
        }
        return "Success";
    }
    
    public String getContent() {
        String sql = "select * from [dbo].[editor]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String content = rs.getString("content");
                return content;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return null;
    }
}
