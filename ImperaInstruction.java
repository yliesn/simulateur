public class ImperaInstruction {
    private String commande;
    private String parametreA;
    private String parametreB;
    private boolean Visited;

    // Constructeur par défaut pour DAT #0 #0
    public ImperaInstruction() {
        this.commande = "DAT";
        this.parametreA = "#0";
        this.parametreB = "#0";
        this.Visited = false;
    }

    // Constructeur avec des valeurs personnalisées
    public ImperaInstruction(String commande, String parametreA, String parametreB) {
        this.commande = commande;
        this.parametreA = parametreA;
        this.parametreB = parametreB;
    }

    // Getters et Setters
    public String getCommande() {
        return commande;
    }

    public void setCommande(String commande) {
        this.commande = commande;
    }

    public String getParametreA() {
        return parametreA;
    }

    public void setParametreA(String parametreA) {
        this.parametreA = parametreA;
    }

    public boolean  getVisited() {
        return Visited;
    }

    public void setVisited(boolean Visited) {
        this.Visited = Visited;
    }


    public String getParametreB() {
        return parametreB;
    }

    public void setParametreB(String parametreB) {
        this.parametreB = parametreB;
    }

    public void affiche(){
        System.out.println(commande +" " + parametreA+ " "+ parametreB);
    }
}
