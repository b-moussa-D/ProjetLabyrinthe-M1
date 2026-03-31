/**
 * File FIFO basee sur Maillon.
 * debut -> tete O(1) pour defiler | fin -> queue O(1) pour enfiler
 * @author Koumba Samb
 */
public class File {

    private Maillon debut;
    private Maillon fin;
    private int     taille;

    public File() { debut = null; fin = null; taille = 0; }

    /** ENFILER - Ajoute en queue. O(1). */
    public void enfiler(Object item) {
        Maillon nvx = new Maillon(item);
        if (estVide()) {
            debut = nvx;
        } else {
            fin.setSuivant(nvx);
        }
        fin = nvx;
        taille++;
    }

    /** DEFILER - Retire et retourne la tete. O(1). */
    public Object defiler() {
        if (estVide()) throw new RuntimeException("Defiler : file vide.");
        Object val = debut.getValeur();
        debut = debut.getSuivant();
        if (debut == null) fin = null;
        taille--;
        return val;
    }

    public Object  tete()      { return (debut != null) ? debut.getValeur() : null; }
    public boolean estVide()   { return taille == 0; }
    public int     getTaille() { return taille; }

    @Override
    public String toString() {
        if (estVide()) return "[file vide]";
        StringBuilder sb = new StringBuilder("[tete -> ");
        Maillon c = debut;
        while (c != null) {
            sb.append(c.getValeur());
            if (c.getSuivant() != null) sb.append(", ");
            c = c.getSuivant();
        }
        return sb.append("]").toString();
    }
}