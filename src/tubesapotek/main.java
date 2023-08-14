package tubesapotek;

import config.koneksi;
import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class main extends koneksi {
    static main tk = new main();
    static Scanner scanner = new Scanner(System.in);
    static boolean isLoggedIn = false;
    boolean isAdmin = false;

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void clear() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "cls");
                processBuilder.inheritIO().start().waitFor();
            } else {
                ProcessBuilder processBuilder = new ProcessBuilder("clear");
                processBuilder.inheritIO().start().waitFor();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, Exception {
        tk.clear();
        tk.login();
        int ulang = 0;
        do {
            tk.clear();
            if (isLoggedIn == true) {
                if (tk.isAdmin) {
                    tk.menuAdmin();
                    System.out.print("ingin kembali?(y)");
                    String pilih = scanner.next();
                    if (String.valueOf(pilih).equals("y")) {
                        try {
                            tk.clear();
                            System.out.println("KEMBALI KE MENU SEBELUMNYA!");
                            Thread.sleep(3000);
                            tk.clear();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    } else {
                        try {
                            tk.clear();
                            System.out.println("input salah, kembali ke menu awal");
                            Thread.sleep(3000);
                            tk.clear();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                } else {
                    tk.menuKasir();
                    System.out.print("ingin kembali?(y)");
                    String pilih = scanner.next();
                    if (String.valueOf(pilih).equals("y")) {
                        try {
                            tk.clear();
                            System.out.println("KEMBALI KE MENU SEBELUMNYA!");
                            Thread.sleep(3000);
                            tk.clear();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    } else {
                        try {
                            tk.clear();
                            System.out.println("input salah, kembali ke menu awal");
                            Thread.sleep(3000);
                            tk.clear();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                }
            }
        } while (ulang != 4);
    }

    private void login() throws SQLException, Exception {
        System.out.println("Pilih login sebagai");
        System.out.println("1. kasir");
        System.out.println("2. admin");
        System.out.print("pilih: ");
        String pilih = scanner.next();

        if (isNumeric(String.valueOf(pilih))) {
            if (pilih.equals("1")) {
                tk.clear();
                String username = "";
                String password = "";
                Console console = System.console();
                if (console == null) {
                    System.out.println("===LOGIN Kasir===");
                    System.out.print("Username: ");
                    username = scanner.next();
                    System.out.print("Password: ");
                    password = scanner.next();
                } else {
                    System.out.println("===LOGIN Kasir===");
                    username = console.readLine("Enter Username: ");
                    password = new String(console.readPassword("Enter Password: "));
                }
                if (authenticateKasir(username, password)) {
                    isAdmin = false;
                    isLoggedIn = true;
                    System.out.println("Login berhasil sebagai kasir!");
                } else {
                    try {
                        tk.clear();
                        System.out.println("Login gagal. Periksa kembali username dan password Anda.");
                        Thread.sleep(3000);
                        tk.clear();
                        main(null);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            } else if (pilih.equals("2")) {
                tk.clear();
                String username = "";
                String password = "";
                Console console = System.console();
                if (console == null) {
                    System.out.println("===LOGIN Admin===");
                    System.out.print("Username: ");
                    username = scanner.next();
                    System.out.print("Password: ");
                    password = scanner.next();
                } else {
                    System.out.println("===LOGIN Admin===");
                    username = console.readLine("Enter Username: ");
                    password = new String(console.readPassword("Enter Password: "));
                }
                if (authenticateAdmin(username, password)) {
                    isAdmin = true;
                    isLoggedIn = true;
                    System.out.println("Login berhasil sebagai admin!");
                } else {
                    try {
                        tk.clear();
                        System.out.println("Login gagal. Periksa kembali username dan password Anda.");
                        Thread.sleep(3000);
                        tk.clear();
                        main(null);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            } else {
                try {
                    tk.clear();
                    System.out.println("Masukkan input yang benar");
                    Thread.sleep(3000);
                    tk.clear();
                    main(null);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        } else {
            try {
                tk.clear();
                System.out.println("Masukkan input yang benar (angka)");
                Thread.sleep(3000);
                tk.clear();
                main(null);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

    }

    public void menuKasir() throws SQLException, Exception {
        System.out.println("===MENU Kasir===");
        System.out.println("1. Penjualan Obat");
        System.out.println("2. Pesanan");
        System.out.println("3. Log out");
        System.out.print("Pilih: ");
        String menu = scanner.next();
        tk.clear();
        penjualanobat ks1 = new penjualanobat();
        if (isNumeric(String.valueOf(menu))) {
            switch (menu) {
                case "1":
                    System.out.println("==Penjualan Obat==");
                    System.out.println("1. Tambah");
                    System.out.println("2. View");
                    System.out.print("Pilih: ");
                    menu = scanner.next();
                    if (isNumeric(String.valueOf(menu))) {
                        if (menu.equals("1")) {
                            ks1.tambahPesanan();
                        } else if (menu.equals("2")) {
                            ks1.viewAllPenjualanObat();
                        }
                    } else {
                        try {
                            tk.clear();
                            System.out.println("Masukkan input yang benar(angka)");
                            Thread.sleep(3000);
                            tk.clear();
                            tk.menuKasir();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                    break;
                case "2":
                    System.out.println("==Pesanan==");
                    System.out.println("1. View");
                    System.out.print("Pilih: ");
                    menu = scanner.next();
                    if (isNumeric(String.valueOf(menu))) {
                        if (menu.equals("1")) {
                            ks1.viewPesanan();
                        } else {
                            System.out.println("yang anda masukkan salah");
                        }
                    } else {
                        try {
                            tk.clear();
                            System.out.println("Masukkan input yang benar(angka)");
                            Thread.sleep(3000);
                            tk.clear();
                            tk.menuKasir();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }

                    break;
                case "3":
                    main(null);
                    break;
                default:
                    System.out.println("tidur");
            }
        } else {
            try {
                tk.clear();
                System.out.println("Masukkan input yang benar(angka)");
                Thread.sleep(3000);
                tk.clear();
                tk.menuKasir();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

    }

    public void menuAdmin() throws SQLException, Exception {
        System.out.println("===MENU Admin===");
        System.out.println("1. Pembelian Obat");
        System.out.println("2. Detail Pembelian Obat");
        System.out.println("3. Supplier");
        System.out.println("4. Obat");
        System.out.println("5. Kasir");
        System.out.println("6. Penjualan Obat");
        System.out.println("7. Log out");
        System.out.print("Pilih: ");

        String menu = scanner.next();
        tk.clear();
        pembelianobat adm = new pembelianobat();
        supplier sup = new supplier();
        obat ob = new obat();
        kasir ks = new kasir();
        penjualanobat po = new penjualanobat();
        if (isNumeric(String.valueOf(menu))) {
            switch (menu) {
                case "1":
                    System.out.println("==Pembelian Obat==");
                    System.out.println("1. Tambah");
                    System.out.println("2. View");
                    System.out.print("Pilih: ");
                    menu = scanner.next();
                    if (isNumeric(String.valueOf(menu))) {
                        if (menu.equals("1")) {
                            adm.tambahPembelianObat();
                        } else if (menu.equals("2")) {
                            adm.viewAllPembelianObat();
                        }
                    } else {
                        try {
                            tk.clear();
                            System.out.println("Masukkan input yang benar(angka)");
                            Thread.sleep(3000);
                            tk.clear();
                            tk.menuAdmin();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                    break;
                case "2":
                    System.out.println("==Detail Pembelian Obat==");
                    System.out.println("1. View");
                    System.out.print("Pilih: ");
                    menu = scanner.next();
                    if (isNumeric(String.valueOf(menu))) {
                        if (menu.equals("1")) {
                            adm.viewAllDetailPembelianObat();
                        }
                    } else {
                        try {
                            tk.clear();
                            System.out.println("Masukkan input yang benar(angka)");
                            Thread.sleep(3000);
                            tk.clear();
                            tk.menuAdmin();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                    break;
                case "3":
                    System.out.println("==Supplier==");
                    System.out.println("1. Tambah");
                    System.out.println("2. Edit");
                    System.out.println("3. Delete");
                    System.out.println("4. View");
                    System.out.print("Pilih: ");
                    menu = scanner.next();
                    if (isNumeric(String.valueOf(menu))) {
                        if (menu.equals("1")) {
                            sup.tambahSupplier();
                        } else if (menu.equals("2")) {
                            sup.editSupplier();
                        } else if (menu.equals("3")) {
                            sup.deleteSupplier();
                        } else if (menu.equals("4")) {
                            sup.viewSupplier();
                        }
                    } else {
                        try {
                            tk.clear();
                            System.out.println("Masukkan input yang benar(angka)");
                            Thread.sleep(3000);
                            tk.clear();
                            tk.menuAdmin();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                    break;
                case "4":
                    System.out.println("==Obat==");
                    System.out.println("1. Tambah");
                    System.out.println("2. Edit");
                    System.out.println("3. Delete");
                    System.out.println("4. View");
                    System.out.print("Pilih: ");
                    menu = scanner.next();
                    if (isNumeric(String.valueOf(menu))) {
                        if (menu.equals("1")) {
                            ob.tambahObat();
                        } else if (menu.equals("2")) {
                            ob.updateObat();
                        } else if (menu.equals("3")) {
                            ob.hapusObat();
                        } else if (menu.equals("4")) {
                            ob.view(Integer.parseInt(menu));
                        }
                    } else {
                        try {
                            tk.clear();
                            System.out.println("Masukkan input yang benar(angka)");
                            Thread.sleep(3000);
                            tk.clear();
                            tk.menuAdmin();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                    break;
                case "5":
                    System.out.println("==Kasir==");
                    System.out.println("1. Tambah");
                    System.out.println("2. Edit");
                    System.out.println("3. Delete");
                    System.out.println("4. View");
                    System.out.print("Pilih: ");
                    menu = scanner.next();
                    if (isNumeric(String.valueOf(menu))) {
                        if (menu.equals("1")) {
                            ks.tambahKasir();
                        } else if (menu.equals("2")) {
                            ks.editKasir();
                        } else if (menu.equals("3")) {
                            ks.deleteKasir();
                        } else if (menu.equals("4")) {
                            ks.viewKasir();
                        }
                    } else {
                        try {
                            tk.clear();
                            System.out.println("Masukkan input yang benar(angka)");
                            Thread.sleep(3000);
                            tk.clear();
                            tk.menuAdmin();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                    break;
                case "6":
                    System.out.println("==PenjualanObat==");
                    System.out.println("1. View penjualan obat");
                    System.out.println("2. View pesanan");
                    System.out.print("Pilih: ");
                    menu = scanner.next();
                    if (isNumeric(String.valueOf(menu))) {
                        if (menu.equals("1")) {
                            po.viewAllPenjualanObat();
                        } else if (menu.equals("2")) {
                            po.viewPesanan();
                        }
                    } else {
                        try {
                            tk.clear();
                            System.out.println("Masukkan input yang benar(angka)");
                            Thread.sleep(3000);
                            tk.clear();
                            tk.menuAdmin();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                    break;
                case "7":
                    main(null);
                default:
                    System.out.println("yang anda masukkan salah");

            }
        } else

        {
            try {
                tk.clear();
                System.out.println("Masukkan input yang benar(angka)");
                Thread.sleep(3000);
                tk.clear();
                tk.menuAdmin();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

}
