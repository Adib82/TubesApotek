/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubesapotek;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.koneksi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ADIB FIRMANSYAH
 */
public class pembelianobat extends admin {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Scanner scanner;

    public pembelianobat() throws SQLException {
        conn = koneksi.getConnection();
        scanner = new Scanner(System.in);
    }

    public int getMaxIdPembelianObat() throws SQLException {
        String sql = "SELECT MAX(idPembelianObat) AS maxId FROM pembelianobat";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("maxId");
            }
        }
        return 0;
    }

    public void tambahPembelianObat() throws Exception {
        try {
            List<detailpembelianobat> pembelianobatList = new ArrayList<>();
            while (true) {
                System.out.println("===Tambah Pembelian Obat===");
                obat ob = new obat();
                ob.view();

                System.out.print("Masukkan id Obat: ");
                int idObat = scanner.nextInt();

                System.out.print("Masukkan Jumlah: ");
                int jumlah = scanner.nextInt();

                System.out.print("Masukkan Tanggal EXP (YYYY-MM-DD):");
                String tglEXPStr = scanner.next();
                Date tglEXP = Date.valueOf(tglEXPStr);

                ob.updatePembelianStockObatById(idObat, jumlah);
                double harga = ob.getHargaByIdObat(idObat);

                double total_harga = harga * jumlah;

                pembelianobatList.add(new detailpembelianobat(idObat, jumlah, tglEXP, total_harga));

                System.out.print("Apakah Anda ingin menambah pesanan lagi? (y/n): ");
                String lanjut = scanner.next();

                if (!lanjut.equalsIgnoreCase("y")) {
                    break;
                }
            }

            double totalHarga = 0.0;
            for (detailpembelianobat pembelianobat : pembelianobatList) {
                totalHarga += pembelianobat.getTotalHarga();
            }

            int idadmin = idAdmin;
            scanner.nextLine();

            System.out.print("Masukkan Tanggal Transaksi (YYYY-MM-DD):");
            String tglTransaksiStr = scanner.next();
            Date tanggalTransaksi = Date.valueOf(tglTransaksiStr);

            int maxIdPembelianObat = getMaxIdPembelianObat();

            int idPembelianObat = maxIdPembelianObat + 1;
            insertPembelianObat(idPembelianObat, idadmin, tanggalTransaksi, totalHarga);
            updateTotalHargaPembelianObat(idPembelianObat, totalHarga);

            // Simpan data pesanan ke tabel pesanan
            for (detailpembelianobat pembelianobat : pembelianobatList) {
                simpandetailpembelianobat(pembelianobat, idPembelianObat);
            }
            strukPembelian(idPembelianObat, idadmin, tanggalTransaksi, totalHarga, pembelianobatList);

            System.out.println("Pesanan berhasil ditambahkan!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPembelianObat(int idPembelianObat, int idAdmin, Date tglTransaksi, double total)
            throws SQLException {
        String sql = "INSERT INTO pembelianobat (idPembelianObat, idAdmin, tglTransaksi, total) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPembelianObat);
            pstmt.setInt(2, idAdmin);
            pstmt.setDate(3, tglTransaksi);
            pstmt.setDouble(4, total);

            int rowsAffected = pstmt.executeUpdate();

            // if (rowsAffected > 0) {
            // System.out.println("Data penjualanobat berhasil disimpan.");
            // } else {
            // System.out.println("Gagal menyimpan data penjualanobat.");
            // }
        }
    }

    public void updateTotalHargaPembelianObat(int idPembelianObat, double totalHarga) throws SQLException {
        String sql = "UPDATE pembelianobat SET total = ? WHERE idPembelianObat = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, totalHarga);
            pstmt.setInt(2, idPembelianObat);

            int rowsAffected = pstmt.executeUpdate();

            // if (rowsAffected > 0) {
            // System.out.println("Total harga pada tabel penjualanobat berhasil
            // diupdate.");
            // } else {
            // System.out.println("Gagal update total harga pada tabel penjualanobat.");
            // }
        }
    }

    private void simpandetailpembelianobat(detailpembelianobat pembelianobat, int idPembelianObat) throws SQLException {
        String sql = "INSERT INTO detailpembelianobat (idObat, jumlahObat, tglEXP, total_harga, idPembelianObat) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pembelianobat.getIdObat());
            pstmt.setInt(2, pembelianobat.getJumlah());
            pstmt.setDate(3, pembelianobat.getTglEXP());
            pstmt.setDouble(4, pembelianobat.getTotalHarga());
            pstmt.setInt(5, idPembelianObat);

            pstmt.executeUpdate();

        }
    }

    private void strukPembelian(int idPembelianObat, int idAdmin, Date tanggalTransaksi, double totalHarga,
            List<detailpembelianobat> pembelianobatList) throws Exception {
        System.out.println("=========================================================");
        System.out.println("           Struk Pembelian Obat        ");
        System.out.println("=========================================================");
        System.out.println("ID Pembelian Obat: " + idPembelianObat);
        System.out.println("ID Admin         : " + idAdmin);
        System.out.println("Tanggal Transaksi: " + tanggalTransaksi);
        obat ob = new obat();
        for (detailpembelianobat pembelianobat : pembelianobatList) {
            String namaObat = ob.getNamaObat(pembelianobat.getIdObat());
            System.out.printf("%-20s %-10s %-15s%n", "Nama Obat: " + namaObat, "Jumlah: " + pembelianobat.getJumlah(),
                    "Total Harga: " + pembelianobat.getTotalHarga());
        }

        System.out.println("=========================================================");
        System.out.printf("%-20s %-25s%n", "Total Harga Keseluruhan:", totalHarga);
        System.out.println("=========================================================");
    }

    public void viewAllPembelianObat() {
        try {
            conn = koneksi.getConnection();
            String query = "SELECT * FROM pembelianobat";
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

    public void viewAllDetailPembelianObat() throws SQLException {
        try {
            conn = koneksi.getConnection();
            String query = "SELECT * FROM detailpembelianobat";
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

    // public void deletePembelianObat() {
    // try {
    // System.out.println("== Hapus Pembelian Obat ==");

    // System.out.print("Masukkan ID Pembelian Obat yang akan dihapus beserta
    // detailnya: ");
    // int idPenjualanObat = scanner.nextInt();
    // scanner.nextLine();

    // String queryDetail = "DELETE FROM detailpembelianobat WHERE
    // idPembelianObat=?";
    // pstmt = conn.prepareStatement(queryDetail);
    // pstmt.setInt(1, idPenjualanObat);

    // int rowsAffected2 = pstmt.executeUpdate();

    // String query = "DELETE FROM pembelianobat WHERE idPembelianObat=?";
    // pstmt = conn.prepareStatement(query);
    // pstmt.setInt(1, idPenjualanObat);

    // int rowsAffected1 = pstmt.executeUpdate();

    // if (rowsAffected1 > 0 && rowsAffected2 > 0) {
    // System.out.println("Pembelian obat berhasil dihapus.");
    // } else {
    // System.out.println("Gagal menghapus pembelian obat.");
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }
}


