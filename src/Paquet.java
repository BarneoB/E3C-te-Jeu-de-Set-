import java.util.*;
/**
 * La classe Paquet représente un paquet de cartes.
 * Les cartes sont stockées dans un tableau fixe et un indice (entier) permet de connaître le nombre de cartes
 * restantes (non piochées) dans le paquet. Quand on pioche, cet indice diminue.
 * Dans les traitements, on considère alors seulement les cartes se trouvant entre 0 et cet indice (exclus).
 * Par conséquent, on ne supprime pas vraiment les cartes piochées, on les ignore juste.
 * On a donc besoin de connaître :
 * - Le tableau stockant les cartes.
 * - Le nombre de cartes restantes dans le paquet.
 */
public class Paquet {

    /**
     * Pre-requis : figures.length > 0, couleurs.length > 0, textures.length > 0, nbFiguresMax > 0
     *
     * Action : Construit un paquet de cartes mélangé contenant toutes les cartes incluant 1 à nbFiguresMax figures
     * qu'il est possibles de créer en combinant les différentes figures, couleurs et textures précisées en paramètre.
     * Bien sûr, il n'y a pas de doublons.
     *
     * Exemple :
     *  - couleurs = [Rouge, Jaune]
     *  - nbFiguresMax = 2
     *  - figures = [A, B]
     *  - textures = [S, T]
     *  Génère un paquet (mélangé) avec toutes les combinaisons de cartes possibles pour ces caractéristiques : 1-A-S (rouge), 1-A-T (rouge), etc...
     */

    private Carte[] cartes;
    private int nbCartesRestantes;
    public Paquet(Couleur[] couleurs, int nbFiguresMax, Figure[] figures, Texture[] textures) {
        this.cartes = new Carte[0];
        for (Couleur c : couleurs) {
            for (int i = 1; i <= nbFiguresMax; i++) {
                for (Figure f : figures) {
                    for (Texture t : textures) {
                        Carte[] temp = new Carte[this.cartes.length+1];
                        int pos = 0;
                        for(Carte j : cartes){
                            temp[pos] = j;
                            pos++;
                        }
                        temp[pos] = new Carte(c,i,f,t);
                        List<Carte> l = Arrays.asList(temp);
                        Collections.shuffle(l);
                        this.cartes = l.toArray(new Carte[0]);
                    }
                }
            }
        }
        this.nbCartesRestantes = this.cartes.length;
    }

    /**
     * Action : Construit un paquet par recopie en copiant les données du paquet passé en paramètre.
     */

    public Paquet(Paquet paquet) {
        this.nbCartesRestantes = paquet.nbCartesRestantes;
        this.cartes = new Carte[nbCartesRestantes];
        for (int i = 0; i < this.nbCartesRestantes; i++) {
            this.cartes[i] = paquet.cartes[i];
        }
    }
    public int getNbCartesRestantes() {
        return this.nbCartesRestantes;
    }
    public Paquet(Carte[] Cartes) {
        this.cartes = Cartes;
        this.nbCartesRestantes = Cartes.length;
    }
    /**
     * Pre-requis : figures.length > 0, couleurs.length > 0, textures.length > 0, nbFiguresMax > 0
     *
     * Resultat : Le nombre de cartes uniques contenant entre 1 et nbFiguresMax figures qu'il est possible de générer en
     * combinant les différentes figures, couleurs et textures précisées en paramètre.
     */

    public static int getNombreCartesAGenerer(Couleur[] couleurs, int nbFiguresMax, Figure[] figures, Texture[] textures) {
        return couleurs.length * nbFiguresMax * figures.length * textures.length;
    }

    /**
     * Action : Mélange aléatoirement les cartes restantes dans le paquet.
     * Attention, on rappelle que le paquet peut aussi contenir des cartes déjà piochées qu'il faut ignorer.
     */

    public void melanger() {
        List <Carte> l = new ArrayList<Carte>();
        for (int i = 0; i < this.nbCartesRestantes; i++) {
            l.add(this.cartes[i]);
        }
        Collections.shuffle(l);
        for (int j = this.nbCartesRestantes; j < this.cartes.length; j++) {
            l.add(this.cartes[j]);
        }
        this.cartes = l.toArray(new Carte[0]);
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri selection.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=Ns4TPTC8whw&t=2s vidéo explicative
     */

    public Paquet trierSelection() {
        Paquet p = new Paquet(this);
        int min = 0;
        for (int i = 0; i < p.nbCartesRestantes-1; i++) {
            for (int j = i+1; j < p.nbCartesRestantes; j++) {
                if (p.cartes[j].compareTo(p.cartes[min]) == -1) {
                    min = j;
                }
            }
            if (p.cartes[i].compareTo(p.cartes[min]) != -1) {
                Carte temp = p.cartes[min];
                p.cartes[min]=p.cartes[i];
                p.cartes[i]=temp;
            }
        }
        return p;
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri bulles.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=lyZQPjUT5B4&embeds_referring_euri=https%3A%2F%2Fwww.developpez.com%2F&source_ve_path=Mjg2NjY&feature=emb_logo
     * vidéo explicative
     */

    public Paquet trierBulles() {
        Paquet p = new Paquet(this);
        for (int i = 1; i < p.nbCartesRestantes; i++) {
            for (int j = 0; j < p.nbCartesRestantes-i; j++) {
                if (p.cartes[j].compareTo(p.cartes[j+1]) == 1) {
                    Carte temp = p.cartes[j];
                    p.cartes[j]=p.cartes[j+1];
                    p.cartes[j+1]=temp;
                }
            }
        }
        return p;
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri insertion.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=ROalU379l3U&t=1s : vidéo explicative
     */

    public Paquet trierInsertion() {
        Paquet p = new Paquet(this);
        for (int i = 1; i < p.nbCartesRestantes; i++) {
            int c=i;
            while (c>0) {
                if (p.cartes[c].compareTo(p.cartes[c-1]) == -1) {
                    Carte temp = p.cartes[c];
                    p.cartes[c] = p.cartes[c-1];
                    p.cartes[c-1]=temp;
                }
                c--;
            }
        }
        return p;
    }

    /**
     * Action : Permet de tester les différents tris.
     * On doit s'assurer que chaque tri permette effectivement de trier un paquet mélangé.
     * Des messages d'informations devront être affichés.
     * La méthode est "static" et ne s'effectue donc pas sur la paquet courant "this".
     */
    public static void testTris() {
        Couleur[] c = {Couleur.ROUGE, Couleur.JAUNE, Couleur.BLEU};
        Figure[] f = {Figure.OVALE,Figure.LOSANGE};
        int nb = 2;
        Texture[] t = {Texture.PLEIN,Texture.HACHURÉ};
        Paquet test = new Paquet(c, nb, f, t);
        System.out.println("Paquet à trier : \n" + test + "\n");

        Paquet s =test.trierSelection();
        System.out.println("Paquet trié (selection) : \n" + s);
        boolean check1 = true;
        for (int j = 0; j < s.nbCartesRestantes-1; j++) {
            if (s.cartes[j].compareTo(s.cartes[j+1]) != -1) {
                check1 = false;
            }
        }
        System.out.println("Le trie par selection fonctionne : " + check1 + "\n");

        Paquet b =test.trierBulles();
        System.out.println("Paquet trié (bulle) : \n" + b);
        boolean check2 = true;
        for (int j = 0; j < b.nbCartesRestantes-1; j++) {
            if (b.cartes[j].compareTo(b.cartes[j+1]) != -1) {
                check2 = false;
            }
        }
        System.out.println("Le trie à bulle fonctionne : " + check2 + "\n");

        Paquet i =test.trierInsertion();
        System.out.println("Paquet trié (insertion) : \n" + i);
        boolean check3 = true;
        for (int j = 0; j < i.nbCartesRestantes-1; j++) {
            if (i.cartes[j].compareTo(i.cartes[j+1]) != -1) {
                check3 = false;
            }
        }
        System.out.println("Le trie par insertion fonctionne : " + check3 + "\n");
    }

    /**
     * Pre-requis : 0 < nbCartes <= nombre de cartes restantes dans le paquet.
     *
     * Action : Pioche nbCartes Cartes au dessus du Paquet this (et met à jour son état).
     *
     * Résultat : Un tableau contenant les nbCartes Cartes piochees dans le Paquet.
     *
     * Exemple :
     * Contenu paquet : [A,B,C,D,E,F,G]
     * Nombre de cartes restantes : 5. On considère donc seulement les cartes de 0 à 4.
     *
     * piocher(3)
     * Contenu paquet : [A,B,C,D,E,F,G]
     * Nombre de cartes restantes : 2
     * Renvoie [E,D,C]
     */

    public Carte[] piocher(int nbCartes) {
        Carte[] p = new Carte[nbCartes];
        for (int i = this.nbCartesRestantes - 1, j=0; j < nbCartes; i--, j++) {
            p[j] = this.cartes[i];
        }
        this.nbCartesRestantes -= nbCartes;
        return p;
    }

    /**
     * Résultat : Vrai s'il reste assez de cartes dans le paquet pour piocher nbCartes.
     */

    public boolean peutPicoher(int nbCartes) {
        return nbCartes <= this.nbCartesRestantes;
    }

    /**
     * Résultat : Vrai s'il ne reste plus aucune cartes dans le paquet.
     */

    public boolean estVide() {
        return nbCartesRestantes == 0;
    }

    /**
     * Résultat : Une chaîne de caractères représentant le paquet sous la forme d'un tableau
     * [X, Y, Z...] représentant les cartes restantes dans le paquet.
     *
     * Exemple :
     * Contenu paquet : 1-O-P (rouge), 2-C-V (jaune), 3-L-P (jaune), 3-L-P (rouge), 1-L-V (bleu)
     * Nombre de cartes restantes : 3
     * Retourne [1-O-P, 2-C-V, 3-L-P] (et chaque représentation d'une carte est coloré selon la couleur de la carte...)
     */

    @Override
    public String toString() {
        String[] c = new String[this.nbCartesRestantes];
        for (int i = 0; i < this.nbCartesRestantes; i++) {
            String f = "L";
            if (this.cartes[i].getFigure() == Figure.CARRE) { f = "C";}
            else if (this.cartes[i].getFigure() == Figure.OVALE) { f = "O";}
            String t = "V";
            if (this.cartes[i].getTexture() == Texture.HACHURÉ) { t = "H";}
            else if (this.cartes[i].getTexture() == Texture.PLEIN) { t = "P";}
            c[i] = this.cartes[i].getCouleur().getCode() + this.cartes[i].getNbFigures() +"-"+ f +"-"+ t;
        }
        return Arrays.toString(c);
    }

    public static void main(String[] args) {
        testTris();
    }
}
