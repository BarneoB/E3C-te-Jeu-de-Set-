/**
 * La classe Table représente une table de jeu contenant des cartes.
 * <p>
 * La table est représentée graphiquement par une matrice.
 * On peut donc avoir des tables de dimensions 3x3, 3x4, 4x4, 5x5, 10x15...
 * En mémoire, la Table est représentée par un simple tableau (à une dimension)
 * Quand elle est initialisée, la table est vide.
 * <p>
 * Pour désigner une carte sur la table, on utilise des coordonées (i,j) ou i représenta la ligne et j la colonne.
 * Les lignes et colonnes sont numérotés à partir de 1.
 * Les cartes sont numérotées à partir de 1.
 * <p>
 * Par exemple, sur une table 3x3, la carte en position (1,1) est la premiere carte du tableau, soit celle à l'indice 0.
 * La carte (2,1) => carte numéro 4 stockée à l'indice 3  dans le tableau représenatnt la table
 * La carte (3,3) => carte numéro 9 stockée à l'indice 8  dans le tableau représenatnt la table
 */
public class Table {

    /**
     * Pre-requis : hauteur >=3, largeur >=3
     * <p>
     * Action : Construit une table "vide" avec les dimensions précisées en paramètre.
     * <p>
     * Exemple : hauteur : 3, largeur : 3 => construit une table 3x3 (pouvant donc accueillir 9 cartes).
     */
    private Carte[] table;
    private Paquet paquet;
    private int hauteur;
    private int largeur;

    public Table(int hauteur, int largeur) {
        this.table = new Carte[hauteur * largeur];
        this.hauteur = hauteur;
        this.largeur = largeur;
    }   
    public void initTable(Paquet paquet){
        this.paquet = paquet;
        Carte[] cartes = paquet.piocher(table.length);
            for (int i = 0; i<table.length; i++){
                table[i] = cartes[i];
            }
    }
    /**
     * Résullat : Le nombre de cartes que la table peut stocker.
     */

    public int getTaille() {
        return this.table.length;
    }

    /**
     * Pre-requis : la table est pleine
     * Action : Affiche des cartes de la table sous forme de matrice
     * L'affichage des cartes doit respecter le format défini dans la classe Carte (chaque carte doit donc être colorée).
     * On ne donne volontairement pas d'exemple puisque celà depend du choix fait pour votre représentation de Carte
     */

    public String toString() {
        for (int i = 0; i<hauteur; i++){
            for (int j = 0; j<largeur; j++){
                System.out.print(table[i*largeur+j].toString()+" ");
            }
            System.out.println();
        }
        return "";
    }

    /**
     * Résullat : Vrai la carte située aux coordonnées précisées en paramètre est une carte possible pour la table.
     */
    public boolean carteExiste(Coordonnees coordonnees) {
        return coordonnees.getLigne() <= hauteur && coordonnees.getColonne() <= largeur;
    }

    /**
     * Pre-requis :
     * Il reste des cartes sur la table.
     * <p>
     * Action : Fait sélectionner au joueur (par saisie de ses coordonnées) une carte valide (existante) de la table.
     * L'algorithme doit faire recommencer la saisie au joueur s'il ne saisit pas une carte valide.
     * <p>
     * Résullat : Le numéro de carte sélectionné.
     */

    public int faireSelectionneUneCarte() {
        if (table.length == 0) {
            throw new RuntimeException("La table est vide !");
        }else {
            boolean carteExiste = false;
            Coordonnees c = null;
            while (!carteExiste) {
                System.out.println("Saisissez les coordonées de la carte que vous voulez sélectionner : ");
                c = new Coordonnees(Ut.saisirChaine());
                if (carteExiste(c)){
                    carteExiste = true;
                } else {
                    System.out.println("Les coordonées de la carte saisi n'existe pas !");
                }
            }
            int numeroCarte = (c.getLigne()+c.getColonne());
            return numeroCarte;
        }
    }

    /**
     * Pre-requis : 1<=nbCartes <= nombre de Cartes de this
     * Action : Fait sélectionner nbCartes Cartes au joueur  sur la table en le faisant recommencer jusqu'à avoir une sélection valide.
     * Il ne doit pas y avoir de doublons dans les numéros de cartes sélectionnées.
     * Résullat : Un tableau contenant les numéros de cartes sélectionnées.
     */

    public int[] selectionnerCartesJoueur(int nbCartes) {
        int[] selection = new int[nbCartes];
        for (int i = 0; i<nbCartes; i++){
            selection[i] = faireSelectionneUneCarte();
        }
        return selection;
    }

    /**
     * Action : Affiche les cartes de la table correspondantes aux numéros de cartes contenus dans selection
     * Exemple de format d'affichage : "Sélection : 2-O-H 3-O-H 2-C-H"
     * Les cartes doivent être affichées "colorées"
     */

    public void afficherSelection(int[] selection) {
        for (int i = 0; i<selection.length; i++){
            System.out.print(table[selection[i]-1].toString()+" ");
        }
        System.out.println();
    }

    public Carte getCarte(int compteur) {
        return table[compteur];
    }

    public Carte retirerCarte(int numeroCarte){
        Carte carte = null;
            if (table[numeroCarte] != null){
                 carte = table[numeroCarte];
                table[numeroCarte] = null;
            }
            return carte;
    }

    public void ajouterCarte(Carte carte, int numeroCarte){
        table[numeroCarte] = carte;
    }

}
