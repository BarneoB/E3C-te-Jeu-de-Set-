public enum Figure {

    /**
     * Représente la figure (forme) d'une Carte : ovale , triangle ...
     */
    LOSANGE(1),
    CARRE(2),
    OVALE(3);

    private int prio;
    private Figure(int a){
        this.prio = a;
    }

    public int getPrio(){
        return this.prio;
    }
}
