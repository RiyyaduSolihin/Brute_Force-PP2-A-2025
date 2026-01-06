package ManajemenBarberShop.model;

// Merepresentasikan satu baris data pelanggan
public class Pelanggan {
    private int id;
    private String nama;
    private String telepon;
    private String layanan;

    public Pelanggan(int id, String nama, String telepon, String layanan) {
        this.id = id;
        this.nama = nama;
        this.telepon = telepon;
        this.layanan = layanan;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getTelepon() { return telepon; }
    public String getLayanan() { return layanan; }
}