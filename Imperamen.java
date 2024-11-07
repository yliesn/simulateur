import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Imperamen extends JFrame {
    private JPanel gridPanel;
    private JButton[] cellules;
    private ImperaInstruction[] cell_imp;
    private int taille;
    private final int MIN_CELL_SIZE = 20;

    public Imperamen() {
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
        cell_imp = new ImperaInstruction[taille * taille];
        
        int cellSize = Math.max(MIN_CELL_SIZE, Math.min(800/taille, 50));
        
        for (int i = 0; i < taille * taille; i++) {
            cellules[i] = createCell(i, cellSize);
            gridPanel.add(cellules[i]);
        }
        for (int i = 0; i < taille * taille; i++) {
            cell_imp[i] = new ImperaInstruction();
        }
        
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JButton createCell(int index, int size) {
        JButton cell = new JButton();
        ImperaInstruction imp= new ImperaInstruction();
        cell.setBackground(Color.WHITE);
        cell.setPreferredSize(new Dimension(size, size));
        cell.setToolTipText(imp.getCommande()+ " "+ imp.getParametreA()+ " "+ imp.getParametreB());
        
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
        
        ArrayList<String> data = LectFile.import_txt("DoubleTir.txt");
        if (data == null || data.isEmpty()) {
            return;
        }

        // Place la première donnée à la position aléatoire
        cellules[startIndex].setBackground(Color.RED);
        cellules[startIndex].setToolTipText(data.get(dataIndex++));

        // Continue avec les positions suivantes
        for (int i = 0; i < taille * taille && dataIndex < data.size(); i++) {
            int currentIndex = (startIndex + i) % (taille * taille);
            cellules[currentIndex].setToolTipText(data.get(dataIndex++));
            cellules[currentIndex].setBackground(Color.RED);
        }
        String[] parts;
        for (int i = 0; i < taille * taille ; i++) {
            //System.out.println(Arrays.toString(cellules[i].getToolTipText().split(" ")));
            parts = (cellules[i].getToolTipText().split(" "));
            this.cell_imp[i].setCommande(parts[0]);
            this.cell_imp[i].setParametreA(parts[1]);
            this.cell_imp[i].setParametreB(parts[2]);

        }
        // for (int idx = 0; idx < cell_imp.length; idx++) {
        //     cell_imp[idx].affiche();           
        // }
        //System.out.println(cell_imp[0].getCommande());

        
    }

    private void start() {
        System.out.println(cell_imp.length);
        String command;
        
        for (int i = 0; i < cell_imp.length; i++) {
            //System.out.println(cell_imp[i].getCommande());
            command = cell_imp[i].getCommande();
            System.out.println(command);
            switch (command) {
                case "MOV":
                    System.out.println("mov");
                    break;
                case "ADD":
                    System.out.println("add");
                break;
                case "JMP":
                    System.out.println("jump");
                default:
                    System.out.println("defaut");
            }
        }





        // if (checkAllSameColor()) {
        //     // System.out.println("Toutes les cellules sont de la même couleur !");
        //     JOptionPane.showMessageDialog(
        //     this,  // 'this' fait référence à la fenêtre principale
        //     "Toutes les cellules sont de la même couleur !",
        //     "Information",
        //     JOptionPane.INFORMATION_MESSAGE
        // );
            
        // }else{
        //     System.out.println("Toutes les cellules ne sont pas de la même couleur !");
        // }
        
        
    }

    private boolean checkAllSameColor() {
        if (cellules == null || cellules.length == 0) {
            return false;
        }
        
        // Récupère la couleur de la première cellule comme référence
        Color referenceColor = cellules[0].getBackground();
        
        // Compare chaque cellule avec la couleur de référence
        for (int i = 1; i < cellules.length; i++) {
            if (!cellules[i].getBackground().equals(referenceColor)) {
                return false;
            }
        }
        
        return true;
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