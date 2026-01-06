// File: src/ManajemenBarberShop/view/MainView.java
package ManajemenBarberShop.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainView extends JFrame {
    // Komponen Input Form
    public JTextField txtNama = new JTextField(20);
    public JTextField txtTelepon = new JTextField(20);
    public JComboBox<String> cmbLayanan = new JComboBox<>(new String[]{"Haircut", "Shaving", "Hair Color", "Full Package"});

    // Komponen Tombol
    public JButton btnTambah = new JButton("Simpan");
    public JButton btnUbah = new JButton("Update");
    public JButton btnHapus = new JButton("Hapus");
    public JButton btnReset = new JButton("Reset Form");

    // FITUR BARU: Komponen Filter
    public JComboBox<String> cmbFilter = new JComboBox<>(new String[]{"Semua Data", "Haircut", "Shaving", "Hair Color", "Full Package"});

    // Komponen Tabel
    public JTable table = new JTable();
    public DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Nama Pelanggan", "No. Telepon", "Layanan"}, 0);

    public MainView() {
        setTitle("BarberBar");
        setSize(900, 600); // Sedikit diperlebar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. PANEL INPUT (FORM) ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Input Data Pelanggan"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Baris 1-4 (Sama seperti sebelumnya)
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Nama Pelanggan:"), gbc);
        gbc.gridx = 1; panelForm.add(txtNama, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("No. Telepon:"), gbc);
        gbc.gridx = 1; panelForm.add(txtTelepon, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Pilih Layanan:"), gbc);
        gbc.gridx = 1; panelForm.add(cmbLayanan, gbc);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTombol.add(btnTambah); panelTombol.add(btnReset);
        gbc.gridx = 1; gbc.gridy = 3; panelForm.add(panelTombol, gbc);

        // --- 2. PANEL TENGAH (FILTER + TABEL) ---
        JPanel panelCenter = new JPanel(new BorderLayout());

        // Panel Filter
        JPanel panelFilter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelFilter.add(new JLabel("Layanan: "));
        panelFilter.add(cmbFilter); // Menambahkan combobox filter

        // Setup Tabel
        table.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Gabungkan Filter dan Tabel
        panelCenter.add(panelFilter, BorderLayout.NORTH); // Filter ditaruh di atas tabel
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        // --- 3. PANEL BAWAH (AKSI) ---
        JPanel panelBawah = new JPanel();
        panelBawah.add(new JLabel("Aksi: "));
        panelBawah.add(btnUbah);
        panelBawah.add(btnHapus);

        // SUSUN KE FRAME UTAMA
        add(panelForm, BorderLayout.WEST); // Form pindah ke Kiri agar lebih lega
        add(panelCenter, BorderLayout.CENTER); // Tabel di tengah
        add(panelBawah, BorderLayout.SOUTH);
    }
}