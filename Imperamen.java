import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class Imperamen extends JFrame {
    private JPanel gridPanel;
    private JButton[] cellules;
    private ImperaInstruction[] cell_imp;
    private int taille;
    private int startIndex;
    private final int MIN_CELL_SIZE = 20;
    private static int timerMs;
    private static javax.swing.Timer timer;

    public Imperamen() {
        // Demande la taille de la grille à l'utilisateur
        String input = JOptionPane.showInputDialog(
                null,
                "Entrez la taille de la grille :",
                "Configuration de la grille",
                JOptionPane.QUESTION_MESSAGE);

        try {
            taille = Integer.parseInt(input);
            if (taille <= 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "La taille doit être positive. Utilisation de la taille par défaut (10)",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                taille = 10;
            }
        } catch (NumberFormatException | NullPointerException e) {
            taille = 10;
            JOptionPane.showMessageDialog(
                    null,
                    "Entrée invalide. Utilisation de la taille par défaut (10)",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
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

        // Ajouter un champs de texte pour le temps en ms du timer
        JTextField timerField = new JTextField("100", 5);
        controlPanel.add(new JLabel("Timer (ms):"));
        controlPanel.add(timerField);

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

        int cellSize = Math.max(MIN_CELL_SIZE, Math.min(800 / taille, 50));

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
        ImperaInstruction imp = new ImperaInstruction();
        cell.setBackground(Color.WHITE);
        cell.setPreferredSize(new Dimension(size, size));
        cell.setToolTipText(imp.getCommande() + " " + imp.getParametreA() + " " + imp.getParametreB());

        cell.setFocusPainted(false);
        cell.setBorderPainted(true);

        return cell;
    }

    private void init() {
        Random random = new Random();
        startIndex = random.nextInt(taille * taille);
        int dataIndex = 0;

        ArrayList<String> data = LectFile.import_txt("tir.txt");
        if (data == null || data.isEmpty()) {
            return;
        }

        // Place la première donnée à la position aléatoire
        cellules[startIndex].setBackground(Color.RED);
        cellules[startIndex].setToolTipText(data.get(dataIndex++));
        cell_imp[startIndex].setVisited(true);
        

        // Continue avec les positions suivantes
        for (int i = 1; i < taille * taille && dataIndex < data.size(); i++) {
            int currentIndex = (startIndex + i) % (taille * taille);
            cellules[currentIndex].setToolTipText(data.get(dataIndex++));
            cellules[currentIndex].setBackground(Color.RED);
            cell_imp[currentIndex].setVisited(true);
        }
        String[] parts;
        for (int i = 0; i < taille * taille; i++) {
            // System.out.println(Arrays.toString(cellules[i].getToolTipText().split(" ")));
            parts = (cellules[i].getToolTipText().split(" "));
            this.cell_imp[i].setCommande(parts[0]);
            this.cell_imp[i].setParametreA(parts[1]);
            this.cell_imp[i].setParametreB(parts[2]);

        }
        // for (int idx = 0; idx < cell_imp.length; idx++) {
        // cell_imp[idx].affiche();
        // }
        // System.out.println(cell_imp[0].getCommande());

    }
private void startTimer(){


    timer = new javax.swing.Timer(200, new ActionListener() {
        // @Override
        public void actionPerformed(ActionEvent e) {
            exec();

    private void start() {
        System.out.println(cell_imp.length);
        // String command;
        for (int i = 0; i < cell_imp.length; i++) {
            
            switch (cell_imp[i].getCommande()) {
                case "MOV":
                    move(i);
                    break;
                case "ADD":
                    System.out.println("add");
                    add(i, cell_imp[i].getParametreA(), cell_imp[i].getParametreB());
                    break;
                case "JMP":
                    jmp(i, cell_imp[i].getParametreA());
                    System.out.println("jump");
                    break;
                case "DAT":
                    
                    break;
                default:
                    this.cellules[i].setBackground(Color.yellow);
                    JOptionPane.showMessageDialog(
                            this,
                            "L'instruction de la celulle " + (i + 1) + " n'existe pas (bleu)",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    throw new AssertionError();
            }


    private void changeGrid() {
        for (int i = 0; i < cellules.length; i++) {

            cellules[i].setToolTipText(
                    cell_imp[i].getCommande() + " " + cell_imp[i].getParametreA() + " " + cell_imp[i].getParametreB());

        }
    }
    private void changeColor() {
        for (int i = 0; i < cellules.length; i++) {
            if(cell_imp[i].getVisited()){

                cellules[i].setBackground(Color.red);
            }

        }
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

    private int jmp(int index, String parametreA) {
        // Le paramètre de saut (parametreA) est un nombre qui indique de combien de cases sauter
            int jumpValue = Integer.parseInt(parametreA);  // Convertir le paramètre en entier
            int newIndex = index + jumpValue;
    
            // S'assurer que l'index ne dépasse pas les bornes de la grille
            if (newIndex < 0) {
                newIndex = 0;
            } else if (newIndex >= cell_imp.length) {
                newIndex = cell_imp.length - 1;
            }
        
            // Mettre à jour l'index courant et exécuter la cellule de l'index 'newIndex'
            index = newIndex;

            // Afficher l'index actuel après le saut (pour le débogage ou suivi)
            return newIndex;
    }
    private void add(int index, String parametreA, String parametreB) {
        // Gérer le cas où PARAMETREA est une valeur immédiate (commence par '#')
        int valeurA = 0;
        if (parametreA.charAt(0) == '#') {
            // Si parametreA commence par '#', c'est une valeur immédiate
            valeurA = Integer.parseInt(parametreA.substring(1));  // Retirer le '#' et convertir en entier
        } else {
            // Sinon, parametreA est une adresse, on va chercher la valeur de DAT à cette adresse
            int adresseA = Integer.parseInt(parametreA);
            // Supposons que 'cell_imp' contient les instructions des cellules
            valeurA = Integer.parseInt(cell_imp[adresseA].getParametreA());  // Récupérer A de la cellule à l'adresse donnée
        }

        // Gérer le cas où PARAMETREB est une adresse
        int valeurB = 0;
        if (parametreB.charAt(0) == '#') {
            // Si parametreB commence par '#', c'est une valeur immédiate
            valeurB = Integer.parseInt(parametreB.substring(1));  // Retirer le '#' et convertir en entier
        } else {
            // Sinon, parametreB est une adresse, on va chercher la valeur de DAT à cette adresse
            int adresseB = Integer.parseInt(parametreB);
            // Supposons que 'cell_imp' contient les instructions des cellules
            valeurB = Integer.parseInt(cell_imp[adresseB].getParametreB());  // Récupérer B de la cellule à l'adresse donnée
        }

        // Additionner les valeurs de A et B
        int somme = valeurA + valeurB;

        // Remplacer le paramètre B de l'adresse spécifiée par la somme
        int adresseB = Integer.parseInt(parametreB);
        cell_imp[adresseB].setParametreB(String.valueOf(somme));  // Mettre à jour le paramètre B avec la somme

        // Déplacer la position courante à la cellule suivante
        // Calculer l'index de la cellule suivante de manière sécurisée
        int nextIndex = (index + 1) % cell_imp.length;  // Se déplacer à la cellule suivante (circularité)

        // Si nextIndex est négatif ou invalide, le ramener à une position valide
        if (nextIndex < 0) {
            nextIndex = 0;  // Revenir à la première cellule si l'index est négatif
        }

        System.out.println("Saut vers la cellule suivante : " + nextIndex);
    }


    
    public void move(int positionActuelle) {

        cell_imp[positionActuelle].setParametreA(cell_imp[positionActuelle].getParametreA().replaceAll("#", ""));
        int parametreA = Integer.parseInt(cell_imp[positionActuelle].getParametreA());
        cell_imp[positionActuelle].setParametreB(cell_imp[positionActuelle].getParametreB().replaceAll("#", ""));
        int parametreB = Integer.parseInt(cell_imp[positionActuelle].getParametreB());

        int source = parametreA + positionActuelle;
        if (source < 0 ) {
            source += cell_imp.length;
        } else if (source > cell_imp.length) {
            source -= cell_imp.length;
        }

        int destination = parametreB + positionActuelle;
        if (destination < 0 ) {
            destination += cell_imp.length;
        } else if (destination >= cell_imp.length) {
            destination -= cell_imp.length;
        }
        
        cell_imp[destination].setCommande(cell_imp[source].getCommande());
        cell_imp[destination].setParametreA(cell_imp[source].getParametreA());
        cell_imp[destination].setParametreB(cell_imp[source].getParametreB());
    }

}