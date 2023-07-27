package tubesapotek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;
import config.koneksi;

public class obat{

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Scanner scanner;

    public obat() throws SQLException {
        conn = koneksi.getConnection(); 
        scanner = new Scanner(System.in);
    }

    public int idObat;
    public String namaObat;
    public String keterangan;
    public Date tglExp;
    public int stokObat;
    double hargaObat;

   public int getIdObat(String namaObat) {
        try {
            String query = "SELECT idobat FROM obat WHERE namaobat=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, namaObat);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                this.idObat = rs.getInt("idobat");
            }else{
                System.out.println("anda memasukkan nama obat yang salah");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idObat;
    }
    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }
    public String getNamaObat() {
        return namaObat;
    }
    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }
    public String getKeterangan() {
        return keterangan;
    }
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    public Date getTglExp() {
        return tglExp;
    }
    public void setTglExp(Date tglExp) {
        this.tglExp = tglExp;
    }
    
    public int updateStockObatById(int idObat, int jumlahObat) {
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
        return stokObat ;
    }

    
    public void setStokObat(int stokObat) {
        this.stokObat = stokObat;
    }
    
    public double getHargaByIdObat(String namaObat) {
        try {
            String query = "SELECT harga FROM obat WHERE namaObat=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, namaObat);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                this.hargaObat = rs.getDouble("harga");
            } else {
                System.out.println("Obat dengan nama " + namaObat + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hargaObat;
    }

    
    public void setHargaObat(double hargaObat) {
        this.hargaObat = hargaObat;
    }
    
    public void view() {
        try {
            String queryidnama = "SELECT idObat, namaObat FROM obat";
            pstmt = conn.prepareStatement(queryidnama);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int idObat = rs.getInt("idObat");
                String namaObat = rs.getString("namaObat");
                System.out.println("-------------------------");
                System.out.print("ID: " + idObat+" ");
                System.out.println("Nama Obat: " + namaObat);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void tambahObat() throws SQLException {
        try{
        System.out.println("== Tambah Obat ==");

            System.out.print("Masukkan Nama Obat: ");
            String namaObat = scanner.nextLine();

            System.out.print("Masukkan Keterangan Obat: ");
            String keterangan = scanner.nextLine();
            scanner.nextLine();

            System.out.print("Masukkan Tanggal Expired: ");
            String tglExpiredString = scanner.nextLine();
            Date tglExp = Date.valueOf(tglExpiredString);

            System.out.print("Masukkan Stok Obat: ");
            int stokObat = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Masukkan Harga Obat: ");
            double hargaObat = scanner.nextInt();
            scanner.nextLine();

        String sql = "INSERT INTO obat (namaObat, keterangan, tglEXP, stokObat, harga) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, namaObat);
        pstmt.setString(2, keterangan);
        pstmt.setDate(3, new java.sql.Date(tglExp.getTime()));
        pstmt.setInt(4, stokObat);
        pstmt.setDouble(5, hargaObat);

        pstmt.executeUpdate();
        System.out.println("Data obat berhasil ditambahkan ke database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    


    // Metode Update: Mengubah data obat di database berdasarkan ID
    public void updateObat() throws SQLException {
        try{
            System.out.println("== Edit Obat ==");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan ID Obat yang akan di-edit: ");
            int idObat = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Masukkan Keterangan Obat Baru: ");
            String keterangan = scanner.nextLine();

            System.out.print("Masukkan Tanggal Expired Obat Baru: ");
            String tglExpiredString = scanner.nextLine();
            Date tglExp = Date.valueOf(tglExpiredString);

            System.out.print("Masukkan Harga Obat Baru: ");
            Double hargaObat = scanner.nextDouble();
            scanner.nextLine();

        String sql = "UPDATE obat SET namaObat = ?, keterangan = ?, tglEXP = ?, stokObat = ?, harga = ? WHERE idObat = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, namaObat);
        pstmt.setString(2, keterangan);
        pstmt.setDate(3, new java.sql.Date(tglExp.getTime()));
        pstmt.setInt(4, stokObat);
        pstmt.setDouble(5, hargaObat);
        pstmt.setInt(6, idObat);

        pstmt.executeUpdate();
        System.out.println("Data obat berhasil diperbarui.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode Delete: Menghapus data obat dari database berdasarkan ID
    public void hapusObat() throws SQLException {
        try{
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

    // Metode untuk mengambil semua data obat dari database menggunakan arraylist
//    public List<obat> getAllObat() throws SQLException {
//        List<obat> obatList = new ArrayList<>();
//        String sql = "SELECT * FROM obat";
//
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        ResultSet rs = pstmt.executeQuery();
//
//        while (rs.next()) {
//            obat obat = new obat();
//            obat.setIdObat(rs.getInt("idObat"));
//            obat.setNamaObat(rs.getString("namaObat"));
//            obat.setKeterangan(rs.getString("keterangan"));
//            obat.setTglExp(rs.getDate("tglEXP"));
//            obat.setStokObat(rs.getInt("stokObat"));
//            obat.setHargaObat(rs.getDouble("harga"));
//
//            obatList.add(obat);
//        }
//
//        return obatList;
//    }
}

