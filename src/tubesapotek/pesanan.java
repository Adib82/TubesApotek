package tubesapotek;

class Pesanan {
    private int idObat;
    private int jumlah;
    private double totalHarga;

    public Pesanan(int idObat, int jumlah, double totalHarga) {
        this.idObat = idObat;
        this.jumlah = jumlah;
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
}