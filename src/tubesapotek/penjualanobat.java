package tubesapotek;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.koneksi;
import java.util.Scanner;

public class penjualanobat extends kasir{
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Scanner scanner;

    public penjualanobat() throws SQLException {
        conn = koneksi.getConnection();
        scanner = new Scanner(System.in);
    }
    
   public void tambahPenjualanObat() {
        try {
            System.out.println("== Tambah Penjualan Obat ==");
            
            obat ob = new obat();
            ob.view();

            System.out.print("Masukkan Nama Obat: ");
            String namaObat = scanner.nextLine();
            
            ob.getIdObat(namaObat);
            int idObat = ob.idObat;
            
            System.out.print("Masukkan Tanggal Transaksi (yyyy-MM-dd): ");
            String tglTransaksi = scanner.nextLine();
            Date tanggalTransaksi = Date.valueOf(tglTransaksi);

            System.out.print("Masukkan jumlah obat: ");
            int jumlahObat = scanner.nextInt();
            
            ob.updateStockObatById(idObat, jumlahObat);
           

            ob.getHargaByIdObat(namaObat);
            double harga = ob.hargaObat;

            double total = harga*jumlahObat;
            
            String querypenjualanobat = "INSERT INTO penjualanobat (idKasir, namaObat,                                     tglTransaksi, total) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(querypenjualanobat);
            pstmt.setInt(1, this.idKasir);
            pstmt.setString(2, namaObat);
            pstmt.setDate(3, tanggalTransaksi);
            pstmt.setDouble(4, total);

            int rowsAffected1 = pstmt.executeUpdate();

            String querydetailpenjualanobat = "INSERT INTO detailpenjualanobat (idObat, jumlahObat, harga) "
                    + "VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(querydetailpenjualanobat);
            pstmt.setInt(1, idObat);
            pstmt.setInt(2, jumlahObat);
            pstmt.setDouble(3, harga);

            int rowsAffected2 = pstmt.executeUpdate();

            if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                System.out.println("Penjualan obat berhasil ditambahkan.");
            } else {
                System.out.println("Gagal menambahkan penjualan obat.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   



    public void viewAllPenjualanObat() {
        try {
            System.out.println("== Daftar Penjualan Obat ==");
            String query = "SELECT * FROM penjualanobat";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idPenjualanObat = rs.getInt("idPenjualanObat");
                int idKasir = rs.getInt("idKasir");
                String namaObat = rs.getString("namaObat");
                Date tglTransaksi = rs.getDate("tglTransaksi");
                double total = rs.getDouble("total");

                System.out.println("ID Penjualan Obat: " + idPenjualanObat);
                System.out.println("ID Kasir: " + idKasir);
                System.out.println("Nama Obat: " + namaObat);
                System.out.println("Tanggal Transaksi: " + tglTransaksi);
                System.out.println("Total: " + total);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePenjualanObat() {
        try {
            System.out.println("== Hapus Penjualan Obat ==");

            System.out.print("Masukkan ID Penjualan Obat yang akan dihapus beserta detailnya: ");
            int idPenjualanObat = scanner.nextInt();
            scanner.nextLine();

            String queryDetail = "DELETE FROM detailpenjualanobat WHERE idPenjualanObat=?";
            pstmt = conn.prepareStatement(queryDetail);
            pstmt.setInt(1, idPenjualanObat);

            int rowsAffected2 = pstmt.executeUpdate();

            String query = "DELETE FROM penjualanobat WHERE idPenjualanObat=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idPenjualanObat);

            int rowsAffected1 = pstmt.executeUpdate();

            if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                System.out.println("Penjualan obat berhasil dihapus.");
            } else {
                System.out.println("Gagal menghapus penjualan obat.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void editPenjualanObat() {
        try {
            System.out.println("== Edit Detail Penjualan Obat ==");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan ID Detail Penjualan Obat yang akan di-edit: ");
            int idDetailPenjualanObat = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Masukkan Jumlah Obat Baru: ");
            int jumlahObat = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Masukkan Harga Baru: ");
            int harga = scanner.nextInt();
            scanner.nextLine();

            Connection conn = koneksi.getConnection();
            String query = "UPDATE detailpenjualanobat SET jumlahObat=?, harga=? WHERE                                                  idDetailPenjualanObat=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jumlahObat);
            pstmt.setInt(2, harga);
            pstmt.setInt(3, idDetailPenjualanObat);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Detail Penjualan obat berhasil di-edit.");
            } else {
                System.out.println("Gagal mengedit detail penjualan obat.");
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
