package ManajemenBarberShop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                
                String url = "jdbc:mysql://localhost:3306/barber_booking";
                String user = "root";
                String password = ""; 
                
                // Load Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                koneksi = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Gagal Koneksi: " + e.getMessage());
            }
        }
        return koneksi;
    }
}