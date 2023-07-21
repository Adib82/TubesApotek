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

        //proses cek login menggunakan koneksi ke database
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
        System.out.println("1. Penjualan Obat");       
        System.out.println("2. Detail Penjualan Obat");
        System.out.print("Pilih: ");
        int menu = scanner.nextInt();
          switch(menu) {
          case 1:
            System.out.println("==Penjualan Obat==");
            System.out.println("1. Tambah");
            System.out.println("2. Edit");
            System.out.println("3. Delete");      
            System.out.println("4. View"); 
            System.out.print("Pilih: ");
            menu = scanner.nextInt();
            kasir ks1 = new kasir(menu);
            
            break;
          case 2:
            System.out.println("==Detail Penjualan Obat==");
            System.out.println("1. Tambah");
            System.out.println("2. Edit");
            System.out.println("3. Delete");      
            System.out.println("4. View"); 
            System.out.print("Pilih: ");
            menu = scanner.nextInt();
            kasir ks2 = new kasir(menu);
            break;
          default:
            System.out.println("tidur");
        }
      
    }

    public void menuAdmin() {
        System.out.println("===MENU Admin===");
        System.out.println("1. Pembelian Obat");
        System.out.println("2. Detail Pembelian Obat");
        System.out.println("3. Supplier");
        System.out.println("4. Obat");
        System.out.println("5. Kategori");
        System.out.print("Pilih: ");
        
        int menu = scanner.nextInt();
        switch(menu) {
        case 1:
            System.out.println("==Pembelian Obat==");
            System.out.println("1. Tambah");
            System.out.println("2. Edit");
            System.out.println("3. Delete");      
            System.out.println("4. View"); 
            System.out.print("Pilih: ");
            menu = scanner.nextInt();
            admin adm1 = new admin(menu);
            
            break;
        case 2:
            System.out.println("==Detail Penjualan Obat==");
            System.out.println("1. Tambah");
            System.out.println("2. Edit");
            System.out.println("3. Delete");      
            System.out.println("4. View"); 
            System.out.print("Pilih: ");
            menu = scanner.nextInt();
            admin adm2 = new admin(menu);
            break;
        case 3:
            System.out.println("==Supplier==");
            System.out.println("1. Tambah");
            System.out.println("2. Edit");
            System.out.println("3. Delete");      
            System.out.println("4. View"); 
            System.out.print("Pilih: ");
            menu = scanner.nextInt();
            admin adm3 = new admin(menu);
            break;
        case 4:
            System.out.println("==Obat==");
            System.out.println("1. Tambah");
            System.out.println("2. Edit");
            System.out.println("3. Delete");      
            System.out.println("4. View"); 
            System.out.print("Pilih: ");
            menu = scanner.nextInt();
            admin adm4 = new admin(menu);
            break;
        case 5:
            System.out.println("==Kategori==");
            System.out.println("1. Tambah");
            System.out.println("2. Edit");
            System.out.println("3. Delete");      
            System.out.println("4. View"); 
            System.out.print("Pilih: ");
            menu = scanner.nextInt();
            admin adm5 = new admin(menu);
            break;
        default:
            System.out.println("tidur");
        
        }
    }

}
