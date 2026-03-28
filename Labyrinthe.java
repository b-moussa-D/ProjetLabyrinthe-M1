import java.io.*;
import java.util.*;

/**
 * Labyrinthe represente par une matrice char[][].
 *
 * Conventions :
 *   MUR     = '#'   case infranchissable
 *   PASSAGE = '='   couloir libre
 *   DEPART  = 'S'   case de depart
 *   ARRIVEE = 'E'   case d'arrivee
 *   CHEMIN  = '+'   case faisant partie du chemin trouve
 *
 * Deux modes de construction :
 *   - chargement depuis un fichier texte
 *   - generation aleatoire par creusage DFS
 *
 * @author Baye Moussa Diongue
 */
public class Labyrinthe {

    // -------------------------------------------------------
    // Constantes publiques (utilisees par les solveurs)
    // -------------------------------------------------------
    public static final char MUR     = '#';
    public static final char PASSAGE = '=';
    public static final char DEPART  = 'S';
    public static final char ARRIVEE = 'E';
    public static final char CHEMIN  = '+';

    // -------------------------------------------------------
    // Attributs
    // -------------------------------------------------------
    private char[][] grille;       // grille courante (modifiable)
    private char[][] grilleOrigin; // copie initiale pour reset()
    private int      nbLignes;
    private int      nbColonnes;
    private int[]    depart;       // {ligne, colonne} du 'S'
    private int[]    arrivee;      // {ligne, colonne} du 'E'

    // -------------------------------------------------------
    // Constructeurs
    // -------------------------------------------------------

    /**
     * Charge le labyrinthe depuis un fichier texte.
     * Chaque ligne du fichier correspond a une ligne de la grille.
     *
     * @param cheminFichier  Chemin absolu ou relatif du fichier .txt
     * @throws Exception     Si le fichier est introuvable ou mal forme
     */
    public Labyrinthe(String cheminFichier) throws Exception {
        chargerDepuisFichier(cheminFichier);
        localiserEntreeSortie();
        sauvegarderOrigine();
    }

    /**
     * Genere un labyrinthe aleatoire de dimensions donnees.
     * Les dimensions sont forcees en valeurs impaires (requis par l'algo).
     *
     * @param lignes    Nombre de lignes souhaite
     * @param colonnes  Nombre de colonnes souhaite
     */
    public Labyrinthe(int lignes, int colonnes) {
        // Forcer des dimensions impaires (exige par l'algo de creusage)
        this.nbLignes   = (lignes   % 2 == 0) ? lignes   + 1 : lignes;
        this.nbColonnes = (colonnes % 2 == 0) ? colonnes + 1 : colonnes;
        genererAleatoire();
        localiserEntreeSortie();
        sauvegarderOrigine();
    }

    // -------------------------------------------------------
    // Chargement depuis fichier
    // -------------------------------------------------------
    private void chargerDepuisFichier(String chemin) throws Exception {
        List<String> lignesFichier = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(chemin));
        String ligne;
        int maxCol = 0;
        while ((ligne = br.readLine()) != null) {
            lignesFichier.add(ligne);
            if (ligne.length() > maxCol) maxCol = ligne.length();
        }
        br.close();

        this.nbLignes   = lignesFichier.size();
        this.nbColonnes = maxCol;
        this.grille     = new char[nbLignes][nbColonnes];

        for (int i = 0; i < nbLignes; i++) {
            String l = lignesFichier.get(i);
            for (int j = 0; j < nbColonnes; j++)
                grille[i][j] = (j < l.length()) ? l.charAt(j) : MUR;
        }
    }

    // -------------------------------------------------------
    // Generation aleatoire (creusage DFS)
    // -------------------------------------------------------
    private void genererAleatoire() {
        grille = new char[nbLignes][nbColonnes];
        for (int i = 0; i < nbLignes; i++) Arrays.fill(grille[i], MUR);
        creuserDFS(1, 1);
        grille[1][1]                        = DEPART;
        grille[nbLignes - 2][nbColonnes - 2] = ARRIVEE;
    }

    /**
     * Algorithme de creusage recursif (maze generation DFS).
     * Avance de 2 cases a la fois pour laisser des murs entre les couloirs.
     */
    private void creuserDFS(int ligne, int col) {
        grille[ligne][col] = PASSAGE;
        int[][] dirs = {{0, 2}, {0, -2}, {2, 0}, {-2, 0}};
        List<int[]> dl = Arrays.asList(dirs);
        Collections.shuffle(dl); // ordre aleatoire -> labyrinthe different a chaque fois
        for (int[] d : dl) {
            int nl = ligne + d[0];
            int nc = col   + d[1];
            if (nl > 0 && nl < nbLignes - 1
                && nc > 0 && nc < nbColonnes - 1
                && grille[nl][nc] == MUR) {
                // Creusse le mur intermediaire
                grille[ligne + d[0] / 2][col + d[1] / 2] = PASSAGE;
                creuserDFS(nl, nc);
            }
        }
    }

    // -------------------------------------------------------
    // Localisation S et E + sauvegarde
    // -------------------------------------------------------
    private void localiserEntreeSortie() {
        depart  = null;
        arrivee = null;
        for (int i = 0; i < nbLignes; i++)
            for (int j = 0; j < nbColonnes; j++) {
                if (grille[i][j] == DEPART)  depart  = new int[]{i, j};
                if (grille[i][j] == ARRIVEE) arrivee = new int[]{i, j};
            }
        if (depart  == null) throw new IllegalStateException("Pas de 'S' trouve dans le labyrinthe.");
        if (arrivee == null) throw new IllegalStateException("Pas de 'E' trouve dans le labyrinthe.");
    }

    private void sauvegarderOrigine() {
        grilleOrigin = new char[nbLignes][nbColonnes];
        for (int i = 0; i < nbLignes; i++)
            grilleOrigin[i] = Arrays.copyOf(grille[i], nbColonnes);
    }

    // -------------------------------------------------------
    // Methodes publiques
    // -------------------------------------------------------

    /**
     * Indique si la case (l, c) est praticable (pas un mur).
     * @param l  Ligne
     * @param c  Colonne
     * @return   true si la case est un passage, depart ou arrivee
     */
    public boolean estPassage(int l, int c) {
        return grille[l][c] != MUR;
    }

    /**
     * Indique si la case (l, c) est l'arrivee.
     * @param l  Ligne
     * @param c  Colonne
     * @return   true si la case est 'E'
     */
    public boolean estArrivee(int l, int c) {
        return grille[l][c] == ARRIVEE;
    }

    /**
     * Marque le chemin trouve avec '+' (sauf depart et arrivee).
     * @param chemin  Liste de cases {ligne, colonne} a marquer
     */
    public void marquerChemin(List<int[]> chemin) {
        for (int[] c : chemin)
            if (grille[c[0]][c[1]] != DEPART && grille[c[0]][c[1]] != ARRIVEE)
                grille[c[0]][c[1]] = CHEMIN;
    }

    /** Remet la grille dans son etat initial (efface les '+'). */
    public void reset() {
        for (int i = 0; i < nbLignes; i++)
            grille[i] = Arrays.copyOf(grilleOrigin[i], nbColonnes);
    }

    /** Affiche le labyrinthe dans la console avec espaces entre cases. */
    public void afficher() {
        System.out.println();
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) System.out.print(grille[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    // -------------------------------------------------------
    // Getters
    // -------------------------------------------------------
    public char[][] getGrille()    { return grille;     }
    public int      getNbLignes()  { return nbLignes;   }
    public int      getNbColonnes(){ return nbColonnes; }
    public int[]    getDepart()    { return depart;     }
    public int[]    getArrivee()   { return arrivee;    }
}
