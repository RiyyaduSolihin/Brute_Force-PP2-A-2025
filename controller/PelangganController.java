package ManajemenBarberShop.controller;

import ManajemenBarberShop.model.Pelanggan;
import ManajemenBarberShop.model.PelangganModel;
import ManajemenBarberShop.view.MainView;
import ManajemenBarberShop.utils.ValidasiInput;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PelangganController {
    private PelangganModel model;
    private MainView view;

    public PelangganController(PelangganModel model, MainView view) {
        this.model = model;
        this.view = view;

        // 1. Load data awal saat aplikasi pertama kali dibuka
        loadDataToTable();

        // ============================================================
        // EVENT LISTENERS (LOGIKA INTERAKSI)
  
        // A. LOGIKA FILTER (Fitur Baru)
        view.cmbFilter.addActionListener(e -> {
            String kategoriDipilih = view.cmbFilter.getSelectedItem().toString();
            
            if (kategoriDipilih.equals("Semua Data")) {
                // Jika user memilih "Semua Data", tampilkan seluruh database
                loadDataToTable();
            } else {
                // Jika user memilih layanan tertentu, tampilkan data yang difilter
                loadFilteredData(kategoriDipilih);
            }
        });

        // B. LOGIKA TOMBOL TAMBAH
        view.btnTambah.addActionListener(e -> {
            String nama = view.txtNama.getText();
            String telp = view.txtTelepon.getText();
            String layanan = view.cmbLayanan.getSelectedItem().toString();

            if (validasiForm(nama, telp)) {
                try {
                    model.insertPelanggan(nama, telp, layanan);
                    JOptionPane.showMessageDialog(view, "Data Berhasil Disimpan!");
                    refreshDataSesuaiFilter(); // Refresh tabel sesuai filter yang sedang aktif
                    clearForm();
                } catch (SQLException ex) {
                    showError("Gagal Menyimpan Data: " + ex.getMessage());
                }
            }
        });

        // C. LOGIKA TOMBOL UBAH
        view.btnUbah.addActionListener(e -> {
            int selectedRow = view.table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Pilih data yang ingin diubah!");
                return;
            }

            int id = Integer.parseInt(view.tableModel.getValueAt(selectedRow, 0).toString());
            String nama = view.txtNama.getText();
            String telp = view.txtTelepon.getText();
            String layanan = view.cmbLayanan.getSelectedItem().toString();

            if (validasiForm(nama, telp)) {
                try {
                    model.updatePelanggan(id, nama, telp, layanan);
                    JOptionPane.showMessageDialog(view, "Data Berhasil Diubah!");
                    refreshDataSesuaiFilter();
                    clearForm();
                } catch (SQLException ex) {
                    showError("Gagal Update Data: " + ex.getMessage());
                }
            }
        });

        // D. LOGIKA TOMBOL HAPUS
        view.btnHapus.addActionListener(e -> {
            int selectedRow = view.table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Pilih data yang ingin dihapus!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(view.tableModel.getValueAt(selectedRow, 0).toString());
                try {
                    model.deletePelanggan(id);
                    JOptionPane.showMessageDialog(view, "Data Berhasil Dihapus!");
                    refreshDataSesuaiFilter();
                    clearForm();
                } catch (SQLException ex) {
                    showError("Gagal Menghapus Data: " + ex.getMessage());
                }
            }
        });

        // E. LOGIKA TOMBOL RESET
        view.btnReset.addActionListener(e -> clearForm());

        // F. LOGIKA KLIK TABEL (Mengisi Form saat baris diklik)
        view.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.table.getSelectedRow();
                if (row != -1) {
                    view.txtNama.setText(view.tableModel.getValueAt(row, 1).toString());
                    view.txtTelepon.setText(view.tableModel.getValueAt(row, 2).toString());
                    
                    // Set ComboBox layanan sesuai data tabel
                    String layanan = view.tableModel.getValueAt(row, 3).toString();
                    view.cmbLayanan.setSelectedItem(layanan);
                }
            }
        });
    }

    // HELPER METHODS (Fungsi Pembantu)
  

    // Load SEMUA data tanpa filter
    private void loadDataToTable() {
        try {
            view.tableModel.setRowCount(0); // Bersihkan tabel lama
            List<Pelanggan> data = model.getAllPelanggan();
            for (Pelanggan p : data) {
                view.tableModel.addRow(new Object[]{
                    p.getId(), p.getNama(), p.getTelepon(), p.getLayanan()
                });
            }
        } catch (SQLException e) {
            showError("Gagal Memuat Data: " + e.getMessage());
        }
    }

    // Load data TERFILTER berdasarkan kategori layanan
    private void loadFilteredData(String kategori) {
        try {
            view.tableModel.setRowCount(0); // Bersihkan tabel lama
            // Memanggil method baru di Model yang menggunakan WHERE clause
            List<Pelanggan> data = model.getPelangganByLayanan(kategori);
            for (Pelanggan p : data) {
                view.tableModel.addRow(new Object[]{
                    p.getId(), p.getNama(), p.getTelepon(), p.getLayanan()
                });
            }
        } catch (SQLException e) {
            showError("Gagal Memfilter Data: " + e.getMessage());
        }
    }

    // untuk refresh tabel agar tetap di filter yang sedang dipilih user
    private void refreshDataSesuaiFilter() {
        String filterSaatIni = view.cmbFilter.getSelectedItem().toString();
        if (filterSaatIni.equals("Semua Data")) {
            loadDataToTable();
        } else {
            loadFilteredData(filterSaatIni);
        }
    }

    // Validasi input form
    private boolean validasiForm(String nama, String telp) {
        if (!ValidasiInput.isValid(nama)) {
            JOptionPane.showMessageDialog(view, "Nama tidak boleh kosong!");
            return false;
        }
        if (!ValidasiInput.isValid(telp)) {
            JOptionPane.showMessageDialog(view, "Nomor Telepon tidak boleh kosong!");
            return false;
        }
        if (!ValidasiInput.isNumeric(telp)) {
            JOptionPane.showMessageDialog(view, "Nomor Telepon harus berupa angka!");
            return false;
        }
        return true;
    }

    // Membersihkan form input
    private void clearForm() {
        view.txtNama.setText("");
        view.txtTelepon.setText("");
        view.cmbLayanan.setSelectedIndex(0);
        view.table.clearSelection();
    }

    // Menampilkan pesan error
    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}