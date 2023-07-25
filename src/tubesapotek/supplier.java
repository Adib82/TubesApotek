package tubesapotek;

public class supplier {
    private int idSupplier;
    private int idObat;
    private String namaSupplier;
    private String noHP;
    private String alamat;

    public supplier() {
        this.idSupplier = idSupplier;
        this.idObat = idObat;
        this.namaSupplier = namaSupplier;
        this.noHP = noHP;
        this.alamat = alamat;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public int getIdObat() {
        return idObat;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public String getNoHP() {
        return noHP;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

}