package tubesapotek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import config.koneksi;

public class obat{

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Scanner scanner;

    // Constructor yang disesuaikan untuk Dependency Injection
    public obat() throws SQLException {
        conn = koneksi.getConnection(); 
        scanner = new Scanner(System.in);
    }

    public int idObat;
    public String namaObat;
    public String keterangan;
    public Date tglExp;
    public int stokObat;
    private double hargaObat;

    public int getIdObat() {
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
    public int getStokObat() {
        return stokObat;
    }
    public void setStokObat(int stokObat) {
        this.stokObat = stokObat;
    }
    public double getHargaObat() {
        return hargaObat;
    }
    public void setHargaObat(double hargaObat) {
        this.hargaObat = hargaObat;
    }

    // Metode Create: Menambahkan data obat ke database
    public void tambahObat(String namaObat, String keterangan, Date tglExp, int stokObat, double hargaObat) throws SQLException {
        String sql = "INSERT INTO obat (namaObat, keterangan, tglEXP, stokObat, harga) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, namaObat);
        pstmt.setString(2, keterangan);
        pstmt.setDate(3, new java.sql.Date(tglExp.getTime()));
        pstmt.setInt(4, stokObat);
        pstmt.setDouble(5, hargaObat);

        pstmt.executeUpdate();
        System.out.println("Data obat berhasil ditambahkan ke database.");
    }

    // Metode Read: Mengambil data obat dari database berdasarkan ID
    public obat getObatById(int idObat) throws SQLException {
        String sql = "SELECT * FROM obat WHERE idObat = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idObat);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            obat Obat = new obat();
            Obat.setIdObat(rs.getInt("idObat"));
            Obat.setNamaObat(rs.getString("namaObat"));
            Obat.setKeterangan(rs.getString("keterangan"));
            Obat.setTglExp(rs.getDate("tglEXP"));
            Obat.setStokObat(rs.getInt("stokObat"));
            Obat.setHargaObat(rs.getDouble("harga"));
            return Obat;
        }

        return null;
    }

    // Metode Update: Mengubah data obat di database berdasarkan ID
    public void updateObat(int idObat) throws SQLException {
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
    }

    // Metode Delete: Menghapus data obat dari database berdasarkan ID
    public void hapusObat(int idObat) throws SQLException {
        String sql = "DELETE FROM obat WHERE idObat = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idObat);

        pstmt.executeUpdate();
        System.out.println("Data obat berhasil dihapus dari database.");
    }

    // Metode untuk mengambil semua data obat dari database
    public List<obat> getAllObat() throws SQLException {
        List<obat> obatList = new ArrayList<>();
        String sql = "SELECT * FROM obat";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            obat obat = new obat();
            obat.setIdObat(rs.getInt("idObat"));
            obat.setNamaObat(rs.getString("namaObat"));
            obat.setKeterangan(rs.getString("keterangan"));
            obat.setTglExp(rs.getDate("tglEXP"));
            obat.setStokObat(rs.getInt("stokObat"));
            obat.setHargaObat(rs.getDouble("harga"));

            obatList.add(obat);
        }

        return obatList;
    }
}
