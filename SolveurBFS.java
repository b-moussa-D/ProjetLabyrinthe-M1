import java.util.*;

/**
 * Resolution par Recherche en Largeur (BFS).
 * Garantit le chemin le plus court en nombre de cases.
 * @author Koumba Samb
 */
public class SolveurBFS {

    private static final int[][] DIRS = {{-1,0},{1,0},{0,-1},{0,1}};

    private Labyrinthe labyrinthe;
    private int        nbEtapes;
    private long       tempsMs;

    public SolveurBFS(Labyrinthe lab) { this.labyrinthe = lab; }

    /**
     * Lance la resolution BFS.
     * @return chemin le plus court, ou null si aucun chemin.
     */
    public List<int[]> resoudre() {
        long t = System.currentTimeMillis();
        nbEtapes = 0;
        int[] dep = labyrinthe.getDepart();
        int[] arr = labyrinthe.getArrivee();
        boolean[][] visite = new boolean[labyrinthe.getNbLignes()][labyrinthe.getNbColonnes()];
        File file = new File();

        file.enfiler(new Cellule(dep[0], dep[1], null));
        visite[dep[0]][dep[1]] = true;
        Cellule cellArr = null;

        while (!file.estVide()) {
            Cellule cur = (Cellule) file.defiler();
            nbEtapes++;
            if (cur.getLigne() == arr[0] && cur.getColonne() == arr[1]) {
                cellArr = cur;
                break;
            }
            for (int[] d : DIRS) {
                int nl = cur.getLigne() + d[0];
                int nc = cur.getColonne() + d[1];
                if (nl >= 0 && nl < labyrinthe.getNbLignes()
                        && nc >= 0 && nc < labyrinthe.getNbColonnes()
                        && !visite[nl][nc] && labyrinthe.estPassage(nl, nc)) {
                    visite[nl][nc] = true;
                    file.enfiler(new Cellule(nl, nc, cur));
                }
            }
        }
        tempsMs = System.currentTimeMillis() - t;

        if (cellArr == null) return null;

        // Retracer le chemin en remontant les parents
        List<int[]> ch = new ArrayList<>();
        for (Cellule c = cellArr; c != null; c = c.getParent())
            ch.add(0, new int[]{c.getLigne(), c.getColonne()});
        return ch;
    }

    public int    getNbEtapes()                    { return nbEtapes; }
    public long   getTempsMs()                     { return tempsMs;  }
    public int    getLongueurChemin(List<int[]> c) { return c != null ? c.size() : 0; }

    public String getStats(List<int[]> ch) {
        return String.format("[BFS] Cases explorees : %d | Longueur chemin : %d | Temps : %d ms",
            nbEtapes, getLongueurChemin(ch), tempsMs);
    }
}