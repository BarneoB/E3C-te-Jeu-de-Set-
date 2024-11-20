public class Proba3CR {
    public static void main(String[] args) {
        Proba3CR p = new Proba3CR();
        System.out.println(p.proba3CR());
    }
    public float proba3CR() {
        int nbCarteR = 0;
        Paquet paquet = new Paquet(new Couleur[]{Couleur.ROUGE, Couleur.BLEU, Couleur.JAUNE}, 3, new Figure[]{Figure.LOSANGE, Figure.OVALE, Figure.CARRE}, new Texture[]{Texture.PLEIN, Texture.HACHURÉ, Texture.VIDE});
        int nbTableR = 0;
        int nbTable = 0;
        while (paquet.getNbCartesRestantes() > 0) {
            nbCarteR=0;
            boolean TROISCR = false;
            int compteur = 0;
            Table table = new Table(3, 3);
            table.initTable(paquet);
            while (compteur < table.getTaille() && !TROISCR) {
                if (table.getCarte(compteur).getCouleur() == Couleur.ROUGE) {
                    nbCarteR++;
                }
                if (nbCarteR == 3) {
                    TROISCR = true;
                    nbTableR++;
                }
                compteur++;
            }
            nbTable++;
        }
        return nbTableR/nbTable;
    }
}
