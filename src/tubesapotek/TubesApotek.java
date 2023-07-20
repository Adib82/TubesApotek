package tubesapotek;

import config.koneksi;
import java.util.Scanner;

public class TubesApotek {
    koneksi db = new koneksi();
    static TubesApotek tk = new TubesApotek();
    Scanner scanner = new Scanner(System.in);
    boolean isLoggedIn = false;
    boolean isAdmin = false;

    public static void main(String[] args) {
        do {
            tk.login();
        } while (!tk.isLoggedIn);

        if (tk.isAdmin) {
            tk.menuAdmin();
        } else {
            tk.menuKasir();
        }
    }

    public void login() {
        System.out.println("===LOGIN===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Implementasi proses cek login menggunakan koneksi ke database
        if (db.authenticateAdmin(username, password)) {
            isAdmin = true;
            isLoggedIn = true;
            System.out.println("Login berhasil sebagai admin!");
        } else if (db.authenticateKasir(username, password)) {
            isAdmin = false;
            isLoggedIn = true;
            System.out.println("Login berhasil sebagai kasir!");
        } else {
            System.out.println("Login gagal. Periksa kembali username dan password Anda.");
        }
    }

    public void menuKasir() {
        System.out.println("===MENU Kasir===");
        System.out.println("1. Tambah Penjualan Obat");
        System.out.println("2. Tambah Detail Penjualan Obat");
        // Tambahkan kode untuk masing-masing pilihan menu kasir
    }

    public void menuAdmin() {
        System.out.println("===MENU Admin===");
        System.out.println("1. Pembelian Obat");
        System.out.println("2. Detail Pembelian Obat");
        System.out.println("3. Supplier");
        System.out.println("4. Obat");
        System.out.println("5. Kategori");
        // Tambahkan kode untuk masing-masing pilihan menu admin
    }
}
