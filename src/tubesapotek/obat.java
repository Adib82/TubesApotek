package tubesapotek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;
import config.koneksi;

public class obat {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Scanner scanner;

    public obat() throws SQLException {
        conn = koneksi.getConnection();
        scanner = new Scanner(System.in);
    }

    main m = new main();

    public int idObat;
    public String namaObat;
    public int maxidObat;
    public String keterangan;
    public Date tglExp;
    public int stokObat;
    double hargaObat;

    // public int getIdObat(String namaObat) {
    // try {
    // String query = "SELECT idobat FROM obat WHERE namaobat=?";
    // pstmt = conn.prepareStatement(query);
    // pstmt.setString(1, namaObat);
    // rs = pstmt.executeQuery();
    // if (rs.next()) {
    // this.idObat = rs.getInt("idobat");
    // } else {
    // System.out.println("anda memasukkan nama obat yang salah");
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // return idObat;
    // }

    public String getNamaObat(int idObat) {
        try {
            String query = "SELECT namaObat FROM obat WHERE idObat=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idObat);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                this.namaObat = rs.getString("namaObat");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return namaObat;
    }

    public int updatePenjualanStockObatById(int idObat, int jumlahObat) throws Exception {
        try {
            String query = "SELECT stokObat FROM obat WHERE idObat=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idObat);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int currentStock = rs.getInt("stokObat");
                int updatedStock = currentStock - jumlahObat;

                if (updatedStock < 0) {
                    System.out.println("Tidak dapat mengurangi stok. Jumlah obat tidak mencukupi.");
                    m.menuKasir();
                    return currentStock;
                }

                String updateQuery = "UPDATE obat SET stokObat=? WHERE idObat=?";
                pstmt = conn.prepareStatement(updateQuery);
                pstmt.setInt(1, updatedStock);
                pstmt.setInt(2, idObat);
                pstmt.executeUpdate();

                this.stokObat = updatedStock;
                return this.stokObat;
            } else {
                System.out.println("Obat dengan ID " + idObat + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stokObat;
    }

    public int updatePembelianStockObatById(int idObat, int jumlahObat) {
        try {
            String query = "SELECT stokObat FROM obat WHERE idObat=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idObat);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int currentStock = rs.getInt("stokObat");
                int updatedStock = currentStock + jumlahObat;

                String updateQuery = "UPDATE obat SET stokObat=? WHERE idObat=?";
                pstmt = conn.prepareStatement(updateQuery);
                pstmt.setInt(1, updatedStock);
                pstmt.setInt(2, idObat);
                pstmt.executeUpdate();

                this.stokObat = updatedStock;
                return this.stokObat;
            } else {
                System.out.println("Obat dengan ID " + idObat + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stokObat;
    }

    public double getHargaByIdObat(int idObat) {
        try {
            String query = "SELECT harga FROM obat WHERE idObat=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idObat);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                this.hargaObat = rs.getDouble("harga");
            } else {
                System.out.println("id Obat " + idObat + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hargaObat;
    }

    public void view() {
        try {
            conn = koneksi.getConnection();
            String query = "SELECT idObat, namaObat, stokObat FROM obat";
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

    public int getMaxIdObat() {
        try {
            String query = "SELECT MAX(idObat) AS maxId FROM obat";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                this.maxidObat = rs.getInt("maxId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxidObat;
    }

    public void tambahObat() throws SQLException {
        try {
            System.out.println("== Tambah Obat ==");

            getMaxIdObat();
            int idObat = maxidObat;
            idObat++;

            supplier sup = new supplier();
            sup.viewSupplier();

            System.out.print("Masukkan id Supplier: ");
            int idSupplier = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Masukkan Nama Obat: ");
            String namaObat = scanner.nextLine();

            System.out.print("Masukkan Stok Obat: ");
            int stokObat = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Masukkan Harga Obat: ");
            double hargaObat = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Masukkan Keterangan Obat: ");
            String keterangan = scanner.nextLine();

            String sql = "INSERT INTO obat (idObat ,idSupplier , namaObat, keterangan, stokObat, harga) VALUES ( ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idObat);
            pstmt.setInt(2, idSupplier);
            pstmt.setString(3, namaObat);
            pstmt.setString(4, keterangan);
            pstmt.setInt(5, stokObat);
            pstmt.setDouble(6, hargaObat);

            pstmt.executeUpdate();

            System.out.println("Data obat berhasil ditambahkan ke database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateObat() throws SQLException {
        try {
            System.out.println("== Edit Obat ==");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan ID Obat yang akan di-edit: ");
            int idObat = scanner.nextInt();
            scanner.nextLine();

            supplier sup = new supplier();
            sup.viewSupplier();

            System.out.print("Masukkan id Supplier: ");
            int idSupplier = scanner.nextInt();

            System.out.print("nama Obat baru: ");
            String namaObat = scanner.next();
            scanner.nextLine();

            System.out.print("Masukkan Keterangan Obat Baru: ");
            String keterangan = scanner.next();

            System.out.print("Masukkan Harga Obat Baru: ");
            Double hargaObat = scanner.nextDouble();
            scanner.nextLine();

            String sql = "UPDATE obat SET idSupplier=?, namaObat = ?, keterangan = ?, stokObat = ?, harga = ? WHERE idObat = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idSupplier);
            pstmt.setString(2, namaObat);
            pstmt.setString(3, keterangan);
            pstmt.setInt(4, stokObat);
            pstmt.setDouble(5, hargaObat);
            pstmt.setInt(6, idObat);

            pstmt.executeUpdate();
            System.out.println("Data obat berhasil diperbarui.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusObat() throws SQLException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan ID Obat yang akan di-edit: ");
            int idObat = scanner.nextInt();
            scanner.nextLine();
            String sql = "DELETE FROM obat WHERE idObat = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idObat);

            pstmt.executeUpdate();
            System.out.println("Data obat berhasil dihapus dari database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void view(int a) {

        try {
            conn = koneksi.getConnection();
            String query = "SELECT * FROM obat";
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
