package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tubesapotek.kasir;

public class koneksi {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/apotek";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public boolean authenticateAdmin(String username, String password) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateKasir(String username, String password) {
        try (Connection conn = getConnection()) {
            kasir ks= new kasir();
            ks.getIdKasir(username);
            String query = "SELECT * FROM kasir WHERE username=? AND password=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
