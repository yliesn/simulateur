import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Tab2D extends JFrame {
    private JPanel gridPanel;
    private JButton[][] cellules;
    private int taille;
    private final int MIN_CELL_SIZE = 20; // Taille minimale d'une cellule en pixels
    private JComboBox<String> selecteur; // Ajout du sélecteur


    public Tab2D() {
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

        // Création du sélecteur
        String[] options = {"Option 1", "Option 2", "Option 3"}; // Remplacez par vos options
        selecteur = new JComboBox<>(options);
        selecteur.setPreferredSize(new Dimension(150, 25)); // Taille personnalisée

        // Création du panel principal avec scroll
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Création du panel pour les boutons de contrôle
        JPanel controlPanel = new JPanel();
        
        // Bouton pour changer la taille
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> start());

        // Bouton de réinitialisation
        JButton initButton = new JButton("init");
        initButton.addActionListener(e -> {
            init();
            initButton.setEnabled(false);
        });

        // Ajout d'un écouteur pour le sélecteur
        selecteur.addActionListener(e -> {
            String selectedOption = (String) selecteur.getSelectedItem();
            handleSelection(selectedOption);
        });
        


        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Sélectionner : "));
        inputPanel.add(selecteur);

        controlPanel.add(inputPanel);
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
    // Méthode pour gérer la sélection
    private void handleSelection(String selectedOption) {
        switch (selectedOption) {
            case "Option 1":
                // Traitement pour l'option 1
                System.out.println("Option 1 sélectionnée");
                break;
            case "Option 2":
                // Traitement pour l'option 2
                System.out.println("Option 2 sélectionnée");
                break;
            case "Option 3":
                // Traitement pour l'option 3
                System.out.println("Option 3 sélectionnée");
                break;
        }
    }

    // Méthode pour obtenir l'option sélectionnée
    public String getSelectedOption() {
        return (String) selecteur.getSelectedItem();
    }

    // Méthode pour définir les options du sélecteur
    public void setOptions(String[] options) {
        selecteur.removeAllItems();
        for (String option : options) {
            selecteur.addItem(option);
        }
    }

    private void createGrid() {
        gridPanel.removeAll();
        cellules = new JButton[taille][taille];
        
        // Calcul de la taille des cellules
        int cellSize = Math.max(MIN_CELL_SIZE, 
                              Math.min(800/taille, 50)); // Limite la taille entre MIN_CELL_SIZE et 50 pixels
        
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cellules[i][j] = createCell(i, j, cellSize);
                gridPanel.add(cellules[i][j]);
            }
        }
        
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JButton createCell(int row, int col, int size) {
        JButton cell = new JButton();
        cell.setBackground(Color.WHITE);
        cell.setPreferredSize(new Dimension(size, size));
        cell.setToolTipText("DAT #0 #0");
        
        // Optimisation pour les grandes grilles
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
        int startRow = random.nextInt(taille);
        int startCol = random.nextInt(taille);
        int dataIndex = 0;
        int cellsVisited = 0;
        int totalCells = taille * taille;
        
        ArrayList<String> data = LectFile.import_csv("DoubleTir.txt");
        // Réinitialise toutes les cellules en blanc d'abord
        if (data == null || data.isEmpty()) {
            return;
        }
    
        
    
        // Assigne la première donnée à la cellule de départ
        cellules[startRow][startCol].setBackground(Color.RED);
        cellules[startRow][startCol].setToolTipText(data.get(dataIndex++));
        cellsVisited++;
    
        // Continue tant qu'il y a des données et des cellules à remplir
        while (dataIndex < data.size() && cellsVisited < totalCells) {
            // Calcule la prochaine position
            startCol++;
            if (startCol >= taille) {
                startCol = 0;
                startRow++;
                if (startRow >= taille) {
                    startRow = 0;
                }
            }
    
            cellules[startRow][startCol].setToolTipText(data.get(dataIndex++));
            cellules[startRow][startCol].setBackground(Color.RED);

            cellsVisited++;
        }
        }

    private void start() {


    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         UnlimitedGridGUI frame = new UnlimitedGridGUI();
    //         frame.setLocationRelativeTo(null);
    //         frame.setVisible(true);
    //     });
    // }
}
