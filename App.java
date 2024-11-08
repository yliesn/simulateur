
import javax.swing.SwingUtilities;


public class App {

    public static void main(String[] args) {
        //String cheminFichier = "DoubleTir.txt";
        //LectFile.import_csv("DoubleTir.txt");
        
        //Tableau 2 dimension

        // SwingUtilities.invokeLater(() -> {
        //     Tab2D frame = new Tab2D();
        //     frame.setLocationRelativeTo(null);
        //     frame.setVisible(true);
        // });

        //Tableau 1 dimension

        SwingUtilities.invokeLater(() -> {
            Imperamen frame = new Imperamen();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }
}