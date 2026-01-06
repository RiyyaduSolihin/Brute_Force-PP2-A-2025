package ManajemenBarberShop;

import ManajemenBarberShop.model.PelangganModel;
import ManajemenBarberShop.view.MainView;
import ManajemenBarberShop.controller.PelangganController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Menjalankan di Event Dispatch Thread agar Thread-Safe
        SwingUtilities.invokeLater(() -> {
            try {
                // Inisialisasi MVC
                PelangganModel model = new PelangganModel();
                MainView view = new MainView();
                
                // Menghubungkan View dan Model lewat Controller
                new PelangganController(model, view);
                
                // Tampilkan Aplikasi
                view.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
