public enum Couleur {

    /**
     * Représente la couleur d'une Carte : jaune, rouge ...
     * En plus de donner une liste énumérative des couleurs possibles,
     * cette enumération doit permettre à la méthode toString d'une Carte de réaliser un affichage en couleur.
     */
    ROUGE("\u001B[31m", 1),
    JAUNE("\u001B[33m", 2),
    BLEU("\u001B[34m", 3);

    private String codeCouleur;
    private int prio;
    private Couleur(String a, int b){
        this.codeCouleur = a;
        this.prio = b;
    }

    public String getCode(){
        return this.codeCouleur;
    }

    public int getPrio(){
        return this.prio;
    }
}
