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
}