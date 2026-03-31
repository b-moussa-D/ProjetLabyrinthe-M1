/**
 * Pile LIFO basee sur Maillon. Complexite O(1) pour toutes les operations.
 * @author Papa Amady Diallo
 */
public class Pile {

    private Maillon sommet;
    private int     taille;

    public Pile() { this.sommet = null; this.taille = 0; }

    /**
     * EMPILER - Ajoute un element au sommet. O(1).
     */
    public void empiler(Object item) {
        sommet = new Maillon(item, sommet);
        taille++;
    }

    /**
     * DEPILER - Retire et retourne l'element du sommet. O(1).
     * @throws RuntimeException si la pile est vide
     */
    public Object depiler() {
        if (estVide()) throw new RuntimeException("Depiler : pile vide.");
        Object val = sommet.getValeur();
        sommet = sommet.getSuivant();
        taille--;
        return val;
    }

    /** Consulte le sommet sans le retirer. */
    public Object  sommet()    { return (sommet != null) ? sommet.getValeur() : null; }
    public boolean estVide()   { return taille == 0; }
    public int     getTaille() { return taille; }

    @Override
    public String toString() {
        if (estVide()) return "[pile vide]";
        StringBuilder sb = new StringBuilder("[sommet -> ");
        Maillon c = sommet;
        while (c != null) {
            sb.append(c.getValeur());
            if (c.getSuivant() != null) sb.append(", ");
            c = c.getSuivant();
        }
        return sb.append("]").toString();
    }
}