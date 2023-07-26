package tubesapotek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.koneksi;
import java.util.Scanner;

public class supplier {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Scanner scanner;

    private int idSupplier;
    private int idObat;
    private String namaSupplier;
    private String noHP;
    private String alamat;

    public supplier() {
        this.idSupplier = idSupplier;
        this.idObat = idObat;
        this.namaSupplier = namaSupplier;
        this.noHP = noHP;
        this.alamat = alamat;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public int getIdObat() {
        return idObat;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public String getNoHP() {
        return noHP;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public void tambahSupplier() {
        try {
            System.out.println("== Tambah Data Supplier ==");

            System.out.print("Masukkan ID Supplier: ");
            int idSupplier = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Masukkan ID Obat: ");
            int idObat = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Masukkan Nama Supplier: ");
            String namaSupplier = scanner.nextLine();

            System.out.print("Masukkan Nomor Handphone: ");
            String noHP = scanner.nextLine();

            System.out.print("Masukkan Alamat Supplier: ");
            String alamat = scanner.nextLine();

            String querysupplier = "INSERT INTO supplier (idSupplier, idObat, namaSupplier, noHP, alamat) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(querysupplier);
            pstmt.setInt(1, idSupplier);
            pstmt.setInt(2, idObat);
            pstmt.setString(3, namaSupplier);
            pstmt.setString(4, noHP);
            pstmt.setString(5, alamat);

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
            System.out.println("== Daftar Data Supplier ==");
            String query = "SELECT * FROM supplier";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idSupplier = rs.getInt("idSupplier");
                int idObat = rs.getInt("idObat");
                String namaSupplier = rs.getString("namaSuppplier");
                String noHP = rs.getString("noHP");
                String alamat = rs.getString("alamat");

                System.out.println("ID Supplier: " + idSupplier);
                System.out.println("ID Obat: " + idObat);
                System.out.println("Nama Supplier: " + namaSupplier);
                System.out.println("Nomor HP: " + noHP);
                System.out.println("Alamat: " + alamat);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            String namaSupplier = scanner.nextLine();

            System.out.print("Masukkan Nomor HP Supplier: ");
            String noHP = scanner.nextLine();

            System.out.print("Masukkan Alamat Supplier: ");
            String alamat = scanner.nextLine();

            Connection conn = koneksi.getConnection();
            String query = "UPDATE supplier SET namaSupplier=?, noHP=?, alamat=? WHERE idSupplier=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, namaSupplier);
            pstmt.setString(2, noHP);
            pstmt.setString(3, alamat);

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

}