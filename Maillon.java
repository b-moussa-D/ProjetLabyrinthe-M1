/**
 * Maillon generique utilise par la Pile (Lot 2) et la File (Lot 3).
 * Brique de base de la liste chainee.
 *
 * @author Baye Moussa Diongue
 */
public class Maillon {

    private Object  valeur;
    private Maillon suivant;

    /**
     * Cree un maillon sans successeur.
     * @param valeur  L'objet stocke dans ce maillon
     */
    public Maillon(Object valeur) {
        this.valeur  = valeur;
        this.suivant = null;
    }

    /**
     * Cree un maillon avec un successeur.
     * @param valeur   L'objet stocke dans ce maillon
     * @param suivant  Le maillon suivant dans la chaine
     */
    public Maillon(Object valeur, Maillon suivant) {
        this.valeur  = valeur;
        this.suivant = suivant;
    }

    public Object  getValeur()  { return valeur;  }
    public Maillon getSuivant() { return suivant; }

    public void setValeur(Object valeur)    { this.valeur  = valeur;  }
    public void setSuivant(Maillon suivant) { this.suivant = suivant; }

    @Override
    public String toString() { return String.valueOf(valeur); }
}
