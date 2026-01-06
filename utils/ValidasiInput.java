package ManajemenBarberShop.utils;

public class ValidasiInput {
    // Mengecek apakah input kosong atau null
    public static boolean isValid(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Validasi khusus nomor telepon (hanya angka)
    public static boolean isNumeric(String input) {
        return input.matches("\\d+");
    }
}