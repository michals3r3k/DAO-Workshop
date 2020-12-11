package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtil {
    private static String DB_URL="jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8";
    private static String DB_USER="root";
    private static String DB_PASS="coderslab";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static void updateById(String sql, int id, String email, String username, String password){
        try(Connection conn = getConnection()){
            PreparedStatement preStmt= conn.prepareStatement(sql);
            preStmt.setInt(4, id);
            preStmt.setString(1, email);
            preStmt.setString(2, email);
            preStmt.setString(3, password);
            preStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteById(String sql, int id){
        try(Connection conn = getConnection()){
            PreparedStatement preStmt= conn.prepareStatement(sql);
            preStmt.setInt(1, id);
            preStmt.executeUpdate();
            System.out.println("User has been deleted");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
