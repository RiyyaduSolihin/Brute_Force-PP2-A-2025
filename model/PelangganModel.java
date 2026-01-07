package ManajemenBarberShop.model;

import ManajemenBarberShop.db.KoneksiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PelangganModel {

    // Fitur Create: Menyimpan 3 data utama (Nama, Telepon, Layanan)
    public void insertPelanggan(String nama, String telepon, String layanan) throws SQLException {
        String sql = "INSERT INTO pelanggan (nama, telepon, layanan) VALUES (?, ?, ?)";
        try (PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setString(2, telepon);
            ps.setString(3, layanan);
            ps.executeUpdate();
        }
    }

    // Fitur Update: Mengubah 3 data utama berdasarkan ID
    public void updatePelanggan(int id, String nama, String telepon, String layanan) throws SQLException {
        String sql = "UPDATE pelanggan SET nama=?, telepon=?, layanan=? WHERE id=?";
        try (PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setString(2, telepon);
            ps.setString(3, layanan);
            ps.setInt(4, id);
            ps.executeUpdate();
        }
    }

    // Fitur Delete: Menghapus data
    public void deletePelanggan(int id) throws SQLException {
        String sql = "DELETE FROM pelanggan WHERE id=?";
        try (PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Fitur Read: Mengambil seluruh data untuk JTable
    public List<Pelanggan> getAllPelanggan() throws SQLException {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan";
        try (Statement st = KoneksiDB.getKoneksi().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Pelanggan(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("telepon"),
                        rs.getString("layanan")
                ));
            }
        }
        return list;
    }
    // FITUR BARU: Filter Data berdasarkan Layanan
    public List<Pelanggan> getPelangganByLayanan(String kategori) throws SQLException {
        List<Pelanggan> list = new ArrayList<>();
        // Query menggunakan WHERE untuk filter
        String sql = "SELECT * FROM pelanggan WHERE layanan = ?";

        try (PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql)) {
            ps.setString(1, kategori);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Pelanggan(
                            rs.getInt("id"),
                            rs.getString("nama"),
                            rs.getString("telepon"),
                            rs.getString("layanan")
                    ));
                }
            }
        }
        return list;
    }
}