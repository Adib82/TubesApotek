package tubesapotek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.koneksi;
import java.util.Scanner;

public class supplier extends obat {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Scanner scanner;

    public int idSupplier;
    public int idObat;
    public int maxidSupplier;
    private String namaSupplier;
    private String noHP;
    private String alamat;

    public supplier() throws SQLException {
        conn = koneksi.getConnection();
        scanner = new Scanner(System.in);
    }

    public int getIdSupplier(String namaSupplier) {
        try {
            String query = "SELECT idSupplier FROM supplier WHERE namaSupplier=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, namaSupplier);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                this.idSupplier = rs.getInt("idSupplier");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idSupplier;
    }

    public int getIdObat(int idSupplier) {
        try {
            String query = "SELECT idObat FROM supplier WHERE idSupplier=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idSupplier);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                this.idObat = rs.getInt("idObat");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idObat;
    }

    public int getMaxIdSupplier() {
        try {
            String query = "SELECT MAX(idSupplier) AS maxId FROM supplier";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                this.maxidSupplier = rs.getInt("maxId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxidSupplier;
    }

    public void tambahSupplier() {
        try {
            System.out.println("== Tambah Data Supplier ==");

            getMaxIdSupplier();
            int idSupplier = maxidSupplier;
            idSupplier++;

            System.out.print("Masukkan Nama Supplier: ");
            String namaSupplier = scanner.nextLine();

            System.out.print("Masukkan Nomor Handphone: ");
            String noHP = scanner.nextLine();

            System.out.print("Masukkan Alamat Supplier: ");
            String alamat = scanner.nextLine();

            String querysupplier = "INSERT INTO supplier (idSupplier, namaSupplier, noHP, alamat) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(querysupplier);
            pstmt.setInt(1, idSupplier);
            pstmt.setString(2, namaSupplier);
            pstmt.setString(3, noHP);
            pstmt.setString(4, alamat);

            int rowsAffected1 = pstmt.executeUpdate();

            if (rowsAffected1 > 0) {
                System.out.println("Data Supplier berhasil ditambahkan.");
            } else {
                System.out.println("Gagal menambahkan data Supplier.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewSupplier() {

        try {
            conn = koneksi.getConnection();
            String query = "SELECT * FROM supplier";
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

    public void deleteSupplier() {
        try {
            System.out.println("== Hapus Data Supplier ==");

            System.out.print("Masukkan ID Suppplier yang akan dihapus beserta detailnya: ");
            int idSupplier = scanner.nextInt();
            scanner.nextLine();

            String query = "DELETE FROM supplier WHERE idSupplier=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idSupplier);

            int rowsAffected1 = pstmt.executeUpdate();

            if (rowsAffected1 > 0) {
                System.out.println("Data Supplier berhasil dihapus.");
            } else {
                System.out.println("Gagal menghapus data Supplier.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editSupplier() {
        try {
            System.out.println("== Edit Data Supplier ==");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan ID Supplier yang akan di-edit: ");
            int idSupplier = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Masukkan Nama Supplier: ");
            String namaSupplier = scanner.next();

            System.out.print("Masukkan Nomor HP Supplier: ");
            String noHP = scanner.next();

            System.out.print("Masukkan Alamat Supplier: ");
            String alamat = scanner.next();

            Connection conn = koneksi.getConnection();
            String query = "UPDATE supplier SET namaSupplier=?, noHP=?, alamat=?                  WHERE                           idSupplier=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, namaSupplier);
            pstmt.setString(2, noHP);
            pstmt.setString(3, alamat);
            pstmt.setInt(4, idSupplier);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data Supplier berhasil di-edit.");
            } else {
                System.out.println("Gagal mengedit data Supplier.");
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewsupobat() {
        try {
            conn = koneksi.getConnection();
            String query = "SELECT s.idSupplier, s.namaSupplier, o.namaObat from supplier s join obat o on o.idObat = s.idObat ORDER BY s.idSupplier ASC;";
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

}