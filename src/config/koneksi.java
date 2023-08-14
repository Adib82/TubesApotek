package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import tubesapotek.admin;
import tubesapotek.kasir;

public class koneksi {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/apotek";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    // ENCRYPT PASS
    private String encrypt(String inputan) {
        String encodedString = Base64.getEncoder().encodeToString(inputan.getBytes());
        return encodedString;
    }

    // DECRYPT PASS
    private String decrypt(String password) {
        String originalInput = password;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }

    protected boolean authenticateAdmin(String username, String password) throws Exception {
        try (Connection conn = getConnection()) {
            admin adm = new admin();
            adm.getIdAdmin(username);
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

    protected boolean authenticateKasir(String username, String password) throws Exception {
        try (Connection conn = getConnection()) {
            if (String.valueOf(decrypt(password)).equals(String.valueOf(password))) {
                kasir ks = new kasir();
                ks.getIdKasir(username);
                String query = "SELECT * FROM kasir WHERE username=? AND password=?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, username);
                pstmt.setString(2, encrypt(password));
                ResultSet rs = pstmt.executeQuery();
                System.out.println(decrypt(password));
                return rs.next();
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
