/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubesapotek;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.koneksi;
import java.util.Scanner;


/**
 *
 * @author ADIB FIRMANSYAH
 */
public class kasir {
    public int idKasir;
    public String username;
    public String password;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Scanner scanner;
    
    public kasir() throws SQLException {
        conn = koneksi.getConnection();
        scanner = new Scanner(System.in);
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    
    public int getIdKasir(String username) throws SQLException {
        String query = "SELECT idKasir FROM kasir WHERE username=?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, username);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            this.idKasir = rs.getInt("idKasir");
        }

        return idKasir;
    }
    
    public void setIdKasir(int idKasir) {
        this.idKasir = idKasir;
    }
    
    
}
