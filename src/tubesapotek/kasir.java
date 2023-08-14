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

import java.util.Base64;

/**
 *
 * @author ADIB FIRMANSYAH
 */
public class kasir extends varKasir {

    public kasir() throws SQLException {
        conn = koneksi.getConnection();
        scanner = new Scanner(System.in);
    }

    // public String getPassword(String username) throws SQLException {
    // String query = "SELECT password FROM kasir WHERE username=?";
    // pstmt = conn.prepareStatement(query);
    // pstmt.setString(1, username);
    // rs = pstmt.executeQuery();
    // if (rs.next()) {
    // this.password = rs.getString("password");
    // }

    // return password;
    // }

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

    @Override
    public int getIdMaxKasir() throws SQLException {

        String query = "SELECT MAX(idKasir) AS maxId FROM kasir";
        pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            this.maxId = rs.getInt("maxId");
        }

        return maxId;
    }

    public String encrypt(String inputan) {
        String encodedString = Base64.getEncoder().encodeToString(inputan.getBytes());
        return encodedString;
    }

    public void tambahKasir() throws SQLException, Exception {
        getIdMaxKasir();
        int idKasir = maxId;
        idKasir++;

        System.out.print("Masukkan username kasir: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password kasir: ");
        String password = scanner.nextLine();

        String query = "INSERT INTO kasir (idKasir,username, password) VALUES (?,?, ?)";
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, idKasir);
        pstmt.setString(2, username);
        pstmt.setString(3, encrypt(password));
        pstmt.executeUpdate();

        System.out.println("Kasir berhasil ditambahkan!");
    }

    public void editKasir() throws SQLException, Exception {
        System.out.print("Masukkan ID kasir yang ingin diubah: ");
        int idKasir = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Masukkan username baru kasir: ");
        String newUsername = scanner.nextLine();
        System.out.print("Masukkan password baru kasir: ");
        String newPassword = scanner.nextLine();

        String query = "UPDATE kasir SET username=?, password=? WHERE idKasir=?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, newUsername);
        pstmt.setString(2, encrypt(newPassword));
        pstmt.setInt(3, idKasir);
        pstmt.executeUpdate();

        System.out.println("Kasir berhasil diubah!");
    }

    public void deleteKasir() throws SQLException {
        System.out.print("Masukkan ID kasir yang ingin dihapus: ");
        int idKasir = scanner.nextInt();
        scanner.nextLine();

        String query = "DELETE FROM kasir WHERE idKasir=?";
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, idKasir);
        pstmt.executeUpdate();

        System.out.println("Kasir berhasil dihapus!");
    }

    public void viewKasir() {

        try {
            conn = koneksi.getConnection();
            String query = "SELECT * FROM kasir";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            // Get jumlah kolom dari query
            int columns = rs.getMetaData().getColumnCount();

            printLine(columns);

            for (int i = 1; i <= columns; i++) {
                System.out.printf("| %-15s ", rs.getMetaData().getColumnName(i));
            }
            System.out.println("|");
            printLine(columns);

            // Print tabel
            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.printf("| %-15s ", rs.getString(i));
                }
                System.out.println("|");
            }

            printLine(columns);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printLine(int columns) {
        for (int i = 0; i < columns; i++) {
            System.out.print("+---------------");
        }
        System.out.println("+");
    }
}