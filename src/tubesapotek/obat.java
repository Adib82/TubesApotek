package tubesapotek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
    public double hargaObat;

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
    
    public void view(int a) {
        try {
            String query = "SELECT * FROM obat";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int idObat = rs.getInt("idObat");
                String namaObat = rs.getString("namaObat");
                String keterangan = rs.getString("keterangan");
                Date tglExp = rs.getDate("tglExp");
                int stokObat = rs.getInt("stokObat");
                double hargaObat = rs.getDouble("hargaObat");

                System.out.println("ID: " + idObat);
                System.out.println("Nama Obat: " + namaObat);
                System.out.println("Keterangan: " + keterangan);
                System.out.println("Tanggal Exp: " + tglExp);
                System.out.println("Stok Obat: " + stokObat);
                System.out.println("Harga Obat: " + hargaObat);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}