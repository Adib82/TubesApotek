package tubesapotek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.koneksi;

import java.util.Scanner;

import java.util.Base64;

abstract class varKasir {
    public int idKasir;
    public String username;
    public String password;
    public Connection conn;
    public PreparedStatement pstmt;
    public ResultSet rs;
    public Scanner scanner;
    public int maxId;

    public int getIdMaxKasir() throws SQLException {
        return 0;
    }
}