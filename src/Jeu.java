import java.util.Random;
import java.util.Scanner;

/**
 * La classe Jeu permet de faire des parties du jeu "E3Cète" soit avec un humain, soit avec un ordinateur.
 *
 * Règles :
 *
 *  - On possède un paquet de cartes qui représentent entre une et trois figures (losange, carre ou ovale), une texture
 *   (vide, hachuré ou plein) et une couleur (rouge, jaune ou bleu). La cardinalité des énumérations est fixée à 3 pour cette partie 2 de la SAE uniquement.
 *
 *  - Une table 3x3 permet de stocker 9 cartes. Au début de la partie, on dispose 9 cartes sur la table, piochées sur le dessus du paquet.
 *
 *  - A chaque tour, le joueur doit essayer de trouver un E3C.
 *
 *  - Le joueur doit désigner trois cartes par leurs coordonnées.
 *      - Si ces cartes forment un E3C, il gagne trois points sur son score.
 *      - Si ce n'est pas un E3C, il perd 1 point sur son score.
 *
 *   - Les trois cartes sont remplacées par de nouvelles cartes piochées dans le paquet.
 *
 *   - La partie se termine quand il n'y a plus de cartes dans le paquet (même s'il reste des cartes sur la table).
 *
 * On a donc besoin :
 *
 * - D'un paquet pour stocker toutes les cartes et avoir une pioche.
 * - D'une table.
 * - De quoi stocker le score du joueur (humain ou ordinateur).
 */
public class Jeu {

    /**
     * Action :
     * - Initialise le jeu "E3Cète" en initialisant le score du joueur, le paquet et la table.
     * - La table doit être remplie.
     */
    private Paquet paquet;
    private Table table;
    private int score;
    public Jeu() {
        this.paquet = new Paquet(new Couleur[] {Couleur.BLEU, Couleur.JAUNE, Couleur.ROUGE}, 3, new Figure[] {Figure.CARRE, Figure.LOSANGE, Figure.OVALE}, new Texture[] {Texture.HACHURÉ, Texture.PLEIN, Texture.VIDE});
        this.table = new Table(3, 3);
        this.table.initTable(this.paquet);
        this.score = 0;
    }

    /**
     * Action : Pioche autant de cartes qu'il y a de numéros de cartes dans le tableau numerosDeCartes et les place
     * aux positions correspondantes sur la table.
     */

    public void piocherEtPlacerNouvellesCartes(int[] numerosDeCartes) {
        Carte[] p = this.paquet.piocher(numerosDeCartes.length);
        for (int i = 0; i < p.length; i++) {
            this.table.ajouterCarte(p[i], numerosDeCartes[i]);
        }
    }

    /**
     * Action : Ré-initialise les données et variables du jeu afin de rejouer une nouvelle partie.
     */

    public void resetJeu() {
        this.paquet = new Paquet(new Couleur[] {Couleur.BLEU, Couleur.JAUNE, Couleur.ROUGE}, 3, new Figure[] {Figure.CARRE, Figure.LOSANGE, Figure.OVALE}, new Texture[] {Texture.HACHURÉ, Texture.PLEIN, Texture.VIDE});
        this.table = new Table(3, 3);
        this.table.initTable(this.paquet);
        this.score = 0;
    }

    /**
     * Résullat : Vrai si les cartes passées en paramètre forment un E3C.
     */

    public static boolean estUnE3C(Carte[] cartes) {
        Couleur[] c = new Couleur[] {cartes[0].getCouleur(), cartes[1].getCouleur(), cartes[2].getCouleur()};
        int[] n = new int[] {cartes[0].getNbFigures(), cartes[1].getNbFigures(), cartes[2].getNbFigures()};
        Figure[] f = new Figure[] {cartes[0].getFigure(), cartes[1].getFigure(), cartes[2].getFigure()};
        Texture[] t = new Texture[] {cartes[0].getTexture(), cartes[1].getTexture(), cartes[2].getTexture()};

        if (!(c[0] == c[1] && c[1] == c[2] || c[0] != c[1] && c[1] != c[2] && c[0] != c[2])) {
            return false;
        }
        if (!(n[0] == n[1] && n[1] == n[2] || n[0] != n[1] && n[1] != n[2] && n[0] != n[2])) {
            return false;
        }
        if (!(f[0] == f[1] && f[1] == f[2] || f[0] != f[1] && f[1] != f[2] && f[0] != f[2])) {
            return false;
        }
        if (!(t[0] == t[1] && t[1] == t[2] || t[0] != t[1] && t[1] != t[2] && t[0] != t[2])) {
            return false;
        }
        return true;
    }

    /**
     * Action : Recherche un E3C parmi les cartes disposées sur la table.
     * Résullat :
     *  - Si un E3C existe, un tableau contenant les numéros de cartes (de la table) qui forment un E3C.
     *  - Sinon, la valeur null.
     */

    public int[] chercherE3CSurTableOrdinateur() {
        for (int i = 0; i < table.getTaille(); i++) {
            for (int j = i+1; j < table.getTaille(); j++) {
                for (int k = j+1; k < table.getTaille(); k++) {
                    if (estUnE3C(new Carte[] {this.table.getCarte(i), this.table.getCarte(j), this.table.getCarte(k)})) {
                        return new int[] {i, j, k};
                    }
                }
            }
        }
        return null;
    }

    /**
     * Action : Sélectionne alétoirement trois cartes sur la table.
     * La sélection ne doit pas contenir de doublons
     * Résullat : un tableau contenant les numéros des cartes sélectionnées alétaoirement
     */

    public int[] selectionAleatoireDeCartesOrdinateur() {
        int i = randomMinMax(0,8);
        int j = randomMinMax(0,8);
        int k = randomMinMax(0,8);
        while (!(i != j && j!=k && i!=k)) {
            i = randomMinMax(0,8);
            j = randomMinMax(0,8);
            k = randomMinMax(0,8);
        }
        return new int[] {i,j,k};
    }

    /**
     * Résullat : Vrai si la partie en cours est terminée.
     */

    public boolean partieEstTerminee() {
        return this.paquet.estVide();
    }

    /**
     * Action : Fait jouer un tour à un joueur humain.
     * La Table et le score du joueur sont affichés.
     * Le joueur sélectionne 3 cartes.
     *  - Si c'est un E3C, il gagne trois points.
     *  - Sinon, il perd un point.
     * Les cartes sélectionnées sont remplacées.
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void jouerTourHumain() {
        System.out.println(this.table.toString());
        System.out.println("Veuillez choisir 3 cartes que vous pensez être un E3C.");
        System.out.print("1ere carte : ");
        Scanner clavier = new Scanner(System.in);
        int i = clavier.nextInt();
        System.out.print("2eme carte : ");
        int j = clavier.nextInt();
        System.out.print("3eme carte : ");
        int k = clavier.nextInt();
        if (estUnE3C(new Carte[] {this.table.getCarte(i), this.table.getCarte(j), this.table.getCarte(k)})) {
            System.out.println("C'est en effet un E3C. Bravo.");
            this.score += 3;
        }
        else{
            System.out.println("Bien tenté mais ce n'en est pas un.");
            this.score --;
        }
        System.out.println("Votre score est de : " + score + "\n");
        piocherEtPlacerNouvellesCartes(new int[] {i,j,k});
    }

    /**
     * Action : Fait jouer une partie à un joueur humain.
     * A la fin, le score final du joueur est affiché.
     */

    public void jouerHumain() {
        while (!this.partieEstTerminee()) {
            this.jouerTourHumain();
        }
        System.out.println("La partie est terminée");
        System.out.println("Score : " + score);
    }

    /**
     * Action : Fait jouer un tour à l'ordinateur.
     * La Table et le score de l'ordinateur sont affichés.
     * L'ordinateur sélectionne des cartes :
     *  - L'ordinateur essaye toujours de trouver un E3C sur la table. S'il en trouve un, il gagne donc trois points.
     *  - S'il n'en trouve pas, il se rabat sur 3 cartes sélectionnées aléatoirement et perd un point.
     * Les cartes sélectionnées sont remplacées.
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void joueurTourOrdinateur() {
        System.out.println(this.table.toString());
        int [] c = chercherE3CSurTableOrdinateur();
        if (c != null) {
            System.out.println("Un E3C a été trouvé : " + this.table.getCarte(c[0]).toString() +" "+ this.table.getCarte(c[1]).toString() +" "+ this.table.getCarte(c[2]).toString());
            score += 3;
        }
        else{
            System.out.println("Aucun E3C trouvé. Des cartes aléatoires sont retirées.");
            c = selectionAleatoireDeCartesOrdinateur();
        }
        piocherEtPlacerNouvellesCartes(c);
        System.out.println(this.table.toString());
        System.out.println("Score : " + score);
    }

    /**
     * Action : Fait jouer une partie à l'ordinateur.
     * Une pause est faite entre chaque tour (500 ms ou plus) afin de pouvoir observer la progression de l'ordinateur.
     * A la fin, le score final de l'ordinateur est affiché.
     * Rappel : Ut.pause(temps) permet de faire une pause de "temps" millisecondes
     */

    public void jouerOrdinateur() {
        while (!this.partieEstTerminee()) {
            this.joueurTourOrdinateur();
            Ut.pause(1000);
        }
        System.out.println("La partie est terminée");
    }

    /**
     * Action : Permet de lancer des parties de "E3Cète" au travers d'un menu.
     * Le menu permet au joueur de sélectionner une option parmi :
     *  - humain : lance une partie avec un joueur humain
     *  - ordinateur : lance une partie avec un ordinateur
     *  - terminer : arrête le programme.
     * Après la fin de chaque partie, les données de jeu sont ré-initialisées et le menu est ré-affiché
     * (afin de faire une nouvelle sélection).
     * Les erreurs de saisie doivent être gérées (si l'utilisateur sélectionne une option inexistante).
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void jouer() {
        System.out.println("Bienvenue dans ce jeu de carte E3C, vous allez passer un bon moment je vous le garanti. \n");
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez choisir si vous voulez jouer contre un humain (taper h), un robot (r) ou arreter le programme (autre)");
        String r = sc.next();
        if (r.equals("h")) {
            System.out.println("Vous vous apprêtez à jouer à 2, que le joueur 1 s'avance pour commencer la partie. \n");
            Ut.pause(6000);
            System.out.println("C'est partie \n");
            jouerHumain();
            System.out.println("\n Au tour du joueur 2");
            Ut.pause(4000);
            System.out.println("C'est partie \n");
            jouerHumain();
            System.out.println("Partie terminée.");
        }
        else if(r.equals("r")){
            System.out.println("Vous vous apprêtez à jouer contre l'ordinateur, vous allez jouer en premier. \n");
            Ut.pause(4000);
            System.out.println("C'est partie \n");
            jouerHumain();
            System.out.println("\n Au tour de l'ordinateur.");
            System.out.println("C'est partie \n");
            jouerOrdinateur();
            System.out.println("Partie terminée.");
        }
        else{
            System.out.println("Pas de soucis, à bientot !");
            return;
        }
    }



    public static int randomMinMax(int min, int max) {
        // Resultat : un entier entre min et max choisi aleatoirement
        Random rand = new Random();
        int res = rand.nextInt(max - min + 1) + min;
        // System.out.println(res + " in [" + min + "," + max + "]");
        // assert min <= res && res <= max : "tirage aleatoire hors des bornes";
        return res;
    }
}
