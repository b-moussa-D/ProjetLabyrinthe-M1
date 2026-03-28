/**
 * Represente une case du labyrinthe lors du parcours BFS.
 * La reference {@code parent} permet de retracer le chemin complet
 * depuis l'arrivee jusqu'au depart.
 *
 * @author Baye Moussa Diongue
 */
public class Cellule {

    private int     ligne;
    private int     colonne;
    private Cellule parent;   // cellule precedente dans le chemin BFS

    /**
     * Constructeur pour la case de depart (pas de parent).
     * @param ligne    Indice de ligne dans la grille
     * @param colonne  Indice de colonne dans la grille
     */
    public Cellule(int ligne, int colonne) {
        this.ligne   = ligne;
        this.colonne = colonne;
        this.parent  = null;
    }

    /**
     * Constructeur pour toutes les autres cases.
     * @param ligne    Indice de ligne dans la grille
     * @param colonne  Indice de colonne dans la grille
     * @param parent   Cellule dont on vient (chemin BFS)
     */
    public Cellule(int ligne, int colonne, Cellule parent) {
        this.ligne   = ligne;
        this.colonne = colonne;
        this.parent  = parent;
    }

    public int     getLigne()   { return ligne;   }
    public int     getColonne() { return colonne; }
    public Cellule getParent()  { return parent;  }

    @Override
    public String toString() {
        return "[" + ligne + "," + colonne + "]";
    }
}
