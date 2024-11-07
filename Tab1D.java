import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Tab1D extends JFrame {
    private JPanel gridPanel;
    private JButton[] cellules;
    private int taille;
    private final int MIN_CELL_SIZE = 20;

    public Tab1D() {
        // Demande la taille de la grille à l'utilisateur
        String input = JOptionPane.showInputDialog(
            null,
            "Entrez la taille de la grille :",
            "Configuration de la grille",
            JOptionPane.QUESTION_MESSAGE
        );

        try {
            taille = Integer.parseInt(input);
            if (taille <= 0) {
                JOptionPane.showMessageDialog(
                    null,
                    "La taille doit être positive. Utilisation de la taille par défaut (10)",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );
                taille = 10;
            }
        } catch (NumberFormatException | NullPointerException e) {
            taille = 10;
            JOptionPane.showMessageDialog(
                null,
                "Entrée invalide. Utilisation de la taille par défaut (10)",
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );
        }

        // Configuration de la fenêtre principale
        setTitle("Simulateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panel principal avec scroll
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Création du panel pour les boutons de contrôle
        JPanel controlPanel = new JPanel();
        
        // Bouton pour démarrer
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> start());

        // Bouton d'initialisation
        JButton initButton = new JButton("Init");
        initButton.addActionListener(e -> {
            init();
            initButton.setEnabled(false);
        });

        controlPanel.add(initButton);
        controlPanel.add(startButton);

        // Initialisation du panel de grille
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(taille, taille, 1, 1));

        // Création d'un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Calcul de la taille de la fenêtre
        int screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment()
                         .getMaximumWindowBounds().width;
        int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment()
                          .getMaximumWindowBounds().height;
        
        int windowSize = Math.min(screenHeight - 100, screenWidth - 100);
        setSize(windowSize, windowSize);
        
        // Création des cellules
        createGrid();

        // Ajout des composants
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void createGrid() {
        gridPanel.removeAll();
        cellules = new JButton[taille * taille];
        
        int cellSize = Math.max(MIN_CELL_SIZE, Math.min(800/taille, 50));
        
        for (int i = 0; i < taille * taille; i++) {
            cellules[i] = createCell(i, cellSize);
            gridPanel.add(cellules[i]);
        }
        
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JButton createCell(int index, int size) {
        JButton cell = new JButton();
        cell.setBackground(Color.WHITE);
        cell.setPreferredSize(new Dimension(size, size));
        cell.setToolTipText("DAT #1 #1");
        
        cell.setFocusPainted(false);
        cell.setBorderPainted(true);
        
        cell.addActionListener(e -> {
            if (cell.getBackground() == Color.WHITE) {
                cell.setBackground(Color.BLUE);
            } else {
                cell.setBackground(Color.WHITE);
            }
            cell.setBackground(Color.RED);
        });
        
        return cell;
    }

    private void init() {
        Random random = new Random();
        int startIndex = random.nextInt(taille * taille);
        int dataIndex = 0;
        
        ArrayList<String> data = LectFile.import_csv("DoubleTir.txt");
        if (data == null || data.isEmpty()) {
            return;
        }

        // Place la première donnée à la position aléatoire
        cellules[startIndex].setBackground(Color.RED);
        cellules[startIndex].setToolTipText(data.get(dataIndex++));

        // Continue avec les positions suivantes
        for (int i = 1; i < taille * taille && dataIndex < data.size(); i++) {
            int currentIndex = (startIndex + i) % (taille * taille);
            cellules[currentIndex].setToolTipText(data.get(dataIndex++));
            cellules[currentIndex].setBackground(Color.RED);
        }
    }

    private void start() {
        // À implémenter selon vos besoins
    }

    // Méthodes utilitaires pour la conversion entre index 1D et coordonnées 2D
    private int getIndex(int row, int col) {
        return row * taille + col;
    }

    private int[] getCoordinates(int index) {
        return new int[] {
            index / taille,    // row
            index % taille     // col
        };
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         UnlimitedGridGUI frame = new UnlimitedGridGUI();
    //         frame.setLocationRelativeTo(null);
    //         frame.setVisible(true);
    //     });
    // }
}