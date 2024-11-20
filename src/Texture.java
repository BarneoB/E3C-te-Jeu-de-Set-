public enum Texture {

    /**
     * Représente la texture d'une Carte : pleine , à pois...
     */
    VIDE(1),
    HACHURÉ(2),
    PLEIN(3);

    private int prio;
    private Texture(int a){
        this.prio = a;
    }

    public int getPrio(){
        return this.prio;
    }
}
