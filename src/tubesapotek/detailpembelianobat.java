
package tubesapotek;

import java.sql.Date;

class detailpembelianobat {
    private int idObat;
    private int jumlah;
    private double totalHarga;
    private Date tglEXP;

    public detailpembelianobat(int idObat, int jumlah, Date tglEXP, double totalHarga) {
        this.idObat = idObat;
        this.jumlah = jumlah;
        this.tglEXP = tglEXP;
        this.totalHarga = totalHarga;
    }

    public int getIdObat() {
        return idObat;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public Date getTglEXP() {
        return tglEXP;
    }
}