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
public class admin {

    protected int idAdmin;
    protected String username;
    protected String password;

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Scanner scanner;

    public admin() throws SQLException {
        conn = koneksi.getConnection();
        scanner = new Scanner(System.in);
    }

    public int getIdAdmin(String username) throws SQLException {
        String query = "SELECT idAdmin FROM admin WHERE username=?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, username);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            this.idAdmin = rs.getInt("idAdmin");
        }

        return idAdmin;
    }

}
