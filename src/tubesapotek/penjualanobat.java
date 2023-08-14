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

public class penjualanobat extends kasir {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Scanner scanner;

    public penjualanobat() throws SQLException {
        conn = koneksi.getConnection();
        scanner = new Scanner(System.in);
    }

    // public void tambahPenjualanObat() throws Exception {
    // try {
    // System.out.println("== Tambah Penjualan Obat ==");

    // obat ob = new obat();
    // ob.view();

    // System.out.print("Masukkan Nama Obat: ");
    // String namaObat = scanner.nextLine();

    // ob.getIdObat(namaObat);
    // int idObat = ob.idObat;

    // System.out.print("Masukkan Tanggal Transaksi (yyyy-MM-dd): ");
    // String tglTransaksi = scanner.nextLine();
    // Date tanggalTransaksi = Date.valueOf(tglTransaksi);

    // System.out.print("Masukkan jumlah obat: ");
    // int jumlahObat = scanner.nextInt();

    // ob.updatePenjualanStockObatById(idObat, jumlahObat);

    // ob.getHargaByIdObat(namaObat);
    // double harga = ob.hargaObat;

    // double total = harga * jumlahObat;

    // String querypenjualanobat = "INSERT INTO pesanan (idKasir, namaObat,
    // tglTransaksi, total) VALUES (?, ?, ?, ?)";
    // pstmt = conn.prepareStatement(querypenjualanobat);
    // pstmt.setInt(1, idKasir);
    // pstmt.setString(2, namaObat);
    // pstmt.setDate(3, tanggalTransaksi);
    // pstmt.setDouble(4, total);

    // int rowsAffected1 = pstmt.executeUpdate();

    // String querydetailpenjualanobat = "INSERT INTO detailpenjualanobat (idObat,
    // jumlahObat, harga) "
    // + "VALUES (?, ?, ?)";
    // pstmt = conn.prepareStatement(querydetailpenjualanobat);
    // pstmt.setInt(1, idObat);
    // pstmt.setInt(2, jumlahObat);
    // pstmt.setDouble(3, harga);

    // int rowsAffected2 = pstmt.executeUpdate();

    // if (rowsAffected1 > 0 && rowsAffected2 > 0) {
    // System.out.println("Penjualan obat berhasil ditambahkan.");
    // strukTransaksi(namaObat, tanggalTransaksi, jumlahObat, harga, total);

    // } else {
    // System.out.println("Gagal menambahkan penjualan obat.");
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    public int getMaxIdPenjualanObat() throws SQLException {
        String sql = "SELECT MAX(idPenjualanObat) AS maxId FROM penjualanobat";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("maxId");
            }
        }
        return 0;
    }

    public void tambahPesanan() {
        try {
            List<Pesanan> pesananList = new ArrayList<>();
            while (true) {
                System.out.println("===Tambah Pesanan===");
                obat ob = new obat();
                ob.view();

                System.out.print("Masukkan id Obat: ");
                int idObat = scanner.nextInt();

                System.out.print("Masukkan Jumlah: ");
                int jumlah = scanner.nextInt();

                ob.updatePenjualanStockObatById(idObat, jumlah);
                double harga = ob.getHargaByIdObat(idObat);

                double total_harga = harga * jumlah;

                pesananList.add(new Pesanan(idObat, jumlah, total_harga));

                System.out.print("Apakah Anda ingin menambah pesanan lagi? (y/n): ");
                String lanjut = scanner.next();

                if (!lanjut.equalsIgnoreCase("y")) {
                    break;
                }
            }

            double totalHarga = 0.0;
            for (Pesanan pesanan : pesananList) {
                totalHarga += pesanan.getTotalHarga();
            }

            int idkasir = idKasir;
            scanner.nextLine();

            System.out.print("Masukkan Tanggal Transaksi (YYYY-MM-DD):");
            String tglTransaksiStr = scanner.next();
            Date tanggalTransaksi = Date.valueOf(tglTransaksiStr);

            int maxIdPenjualanObat = getMaxIdPenjualanObat();

            // Menambahkan 1 untuk mendapatkan nilai idPenjualanObat berikutnya
            int idPenjualanObat = maxIdPenjualanObat + 1;
            insertPenjualanObat(idPenjualanObat, idkasir, tanggalTransaksi, totalHarga);
            updateTotalHargaPenjualanObat(idPenjualanObat, totalHarga);

            // Simpan data pesanan ke tabel pesanan
            for (Pesanan pesanan : pesananList) {
                simpanPesanan(pesanan, idPenjualanObat);
            }
            strukPenjualan(idPenjualanObat, idkasir, tanggalTransaksi, totalHarga, pesananList);

            System.out.println("Pesanan berhasil ditambahkan!");
        } catch (Exception e) {
            System.err.println("Error saat menambahkan pesanan: ");
            // + e.getMessage()
        }
    }

    private void strukPenjualan(int idPenjualanObat, int idKasir, Date tanggalTransaksi, double totalHarga,
            List<Pesanan> pesananList) throws Exception {
        System.out.println("=========================================================");
        System.out.println("           Struk Penjualan Obat        ");
        System.out.println("=========================================================");
        System.out.println("ID Penjualan Obat: " + idPenjualanObat);
        System.out.println("ID Kasir         : " + idKasir);
        System.out.println("Tanggal Transaksi: " + tanggalTransaksi);
        obat ob = new obat();
        for (Pesanan pesanan : pesananList) {
            String namaObat = ob.getNamaObat(pesanan.getIdObat());
            System.out.printf("%-20s %-10s %-15s%n", "Nama Obat: " + namaObat, "Jumlah: " + pesanan.getJumlah(),
                    "Total Harga: " + pesanan.getTotalHarga());
        }

        System.out.println("=========================================================");
        System.out.printf("%-20s %-25s%n", "Total Harga Keseluruhan:", totalHarga);
        System.out.println("=========================================================");
    }

    private void simpanPesanan(Pesanan pesanan, int idPenjualanObat) throws SQLException {
        String sql = "INSERT INTO pesanan (idObat, jumlah, total_harga, idPenjualanObat) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pesanan.getIdObat());
            pstmt.setInt(2, pesanan.getJumlah());
            pstmt.setDouble(3, pesanan.getTotalHarga());
            pstmt.setInt(4, idPenjualanObat);

            pstmt.executeUpdate();

        }
    }

    public void updateTotalHargaPenjualanObat(int idPenjualanObat, double totalHarga) throws SQLException {
        String sql = "UPDATE penjualanobat SET total = ? WHERE idPenjualanObat = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, totalHarga);
            pstmt.setInt(2, idPenjualanObat);

            int rowsAffected = pstmt.executeUpdate();

            // if (rowsAffected > 0) {
            // System.out.println("Total harga pada tabel penjualanobat berhasil
            // diupdate.");
            // } else {
            // System.out.println("Gagal update total harga pada tabel penjualanobat.");
            // }
        }
    }

    public void viewPesanan() {
        try {
            conn = koneksi.getConnection();
            String query = "SELECT * from pesanan";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

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

    public void insertPenjualanObat(int idPenjualanObat, int idKasir, Date tglTransaksi, double total)
            throws SQLException {
        String sql = "INSERT INTO penjualanobat (idPenjualanObat, idKasir, tglTransaksi, total) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPenjualanObat);
            pstmt.setInt(2, idKasir);
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

    // public double HitungJumlahPesanan(int idPenjualanObat) {
    // try {
    // String sql = "SELECT idPenjualanObat, SUM(total_harga) AS total FROM pesanan
    // WHERE idPenjualanObat = ? GROUP BY idPenjualanObat";
    // pstmt = conn.prepareStatement(sql);
    // pstmt.setInt(1, idPenjualanObat);
    // rs = pstmt.executeQuery();
    // if (rs.next()) {
    // this.total = rs.getDouble("total");
    // } else {
    // System.out.println("error gan");
    // }

    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // return total;
    // }

    public void viewAllPenjualanObat() {
        try {
            conn = koneksi.getConnection();
            String query = "SELECT * FROM penjualanobat";
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

    // public void viewAllDetailPenjualanObat() throws SQLException {
    // try {
    // conn = koneksi.getConnection();
    // String query = "SELECT * FROM detailpenjualanobat";
    // pstmt = conn.prepareStatement(query);
    // rs = pstmt.executeQuery();

    // // Get jumlah kolom dari query
    // int columns = rs.getMetaData().getColumnCount();

    // printLine(columns);

    // for (int i = 1; i <= columns; i++) {
    // System.out.printf("| %-15s ", rs.getMetaData().getColumnName(i));
    // }
    // System.out.println("|");
    // printLine(columns);

    // // Print tabel
    // while (rs.next()) {
    // for (int i = 1; i <= columns; i++) {
    // System.out.printf("| %-15s ", rs.getString(i));
    // }
    // System.out.println("|");
    // }

    // printLine(columns);

    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    // public void deletePenjualanObat() {
    // try {
    // System.out.println("== Hapus Penjualan Obat ==");

    // System.out.print("Masukkan ID Penjualan Obat yang akan dihapus beserta
    // detailnya: ");
    // int idPenjualanObat = scanner.nextInt();
    // scanner.nextLine();

    // String queryDetail = "DELETE FROM detailpenjualanobat WHERE
    // idPenjualanObat=?";
    // pstmt = conn.prepareStatement(queryDetail);
    // pstmt.setInt(1, idPenjualanObat);

    // int rowsAffected2 = pstmt.executeUpdate();

    // String query = "DELETE FROM penjualanobat WHERE idPenjualanObat=?";
    // pstmt = conn.prepareStatement(query);
    // pstmt.setInt(1, idPenjualanObat);

    // int rowsAffected1 = pstmt.executeUpdate();

    // if (rowsAffected1 > 0 && rowsAffected2 > 0) {
    // System.out.println("Penjualan obat berhasil dihapus.");
    // } else {
    // System.out.println("Gagal menghapus penjualan obat.");
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    // public void editPenjualanObat() {
    // try {
    // System.out.println("== Edit Detail Penjualan Obat ==");
    //
    // Scanner scanner = new Scanner(System.in);
    // System.out.print("Masukkan ID Detail Penjualan Obat yang akan di-edit: ");
    // int idDetailPenjualanObat = scanner.nextInt();
    // scanner.nextLine();
    //
    // System.out.print("Masukkan Jumlah Obat Baru: ");
    // int jumlahObat = scanner.nextInt();
    // scanner.nextLine();
    //
    // System.out.print("Masukkan Harga Baru: ");
    // int harga = scanner.nextInt();
    // scanner.nextLine();
    //
    // Connection conn = koneksi.getConnection();
    // String query = "UPDATE detailpenjualanobat SET jumlahObat=?, harga=? WHERE
    // idDetailPenjualanObat=?";
    // PreparedStatement pstmt = conn.prepareStatement(query);
    // pstmt.setInt(1, jumlahObat);
    // pstmt.setInt(2, harga);
    // pstmt.setInt(3, idDetailPenjualanObat);
    //
    // int rowsAffected = pstmt.executeUpdate();
    // if (rowsAffected > 0) {
    // System.out.println("Detail Penjualan obat berhasil di-edit.");
    // } else {
    // System.out.println("Gagal mengedit detail penjualan obat.");
    // }
    //
    // pstmt.close();
    // conn.close();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }
}


