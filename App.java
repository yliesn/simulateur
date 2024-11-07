import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        //String cheminFichier = "DoubleTir.txt";
        //LectFile.import_csv("DoubleTir.txt");

        // SwingUtilities.invokeLater(() -> {
        //     Tab2d frame = new Tab2d();
        //     frame.setLocationRelativeTo(null);
        //     frame.setVisible(true);
        // });

        SwingUtilities.invokeLater(() -> {
            Tab1D frame = new Tab1D();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }
}