import java.util.*;

/**
 * Resolution par Recherche en Profondeur (DFS) avec backtracking.
 * @author Papa Amady Diallo
 */
public class SolveurDFS {

    private static final int[][] DIRS = {{-1,0},{1,0},{0,-1},{0,1}};

    private Labyrinthe labyrinthe;
    private int        nbEtapes;
    private long       tempsMs;

    public SolveurDFS(Labyrinthe lab) { this.labyrinthe = lab; }

    /**
     * Lance la resolution DFS.
     * @return chemin trouve (liste de {ligne,col}), ou null si aucun chemin.
     */
    public List<int[]> resoudre() {
        long t = System.currentTimeMillis();
        nbEtapes = 0;
        boolean[][] visite = new boolean[labyrinthe.getNbLignes()][labyrinthe.getNbColonnes()];
        List<int[]> chemin = new ArrayList<>();
        int[] dep = labyrinthe.getDepart();
        boolean trouve = dfs(dep[0], dep[1], visite, chemin);
        tempsMs = System.currentTimeMillis() - t;
        return trouve ? chemin : null;
    }

    private boolean dfs(int l, int c, boolean[][] visite, List<int[]> chemin) {
        if (l < 0 || l >= labyrinthe.getNbLignes())   return false;
        if (c < 0 || c >= labyrinthe.getNbColonnes()) return false;
        if (!labyrinthe.estPassage(l, c) || visite[l][c]) return false;

        nbEtapes++;
        visite[l][c] = true;
        chemin.add(new int[]{l, c});

        if (labyrinthe.estArrivee(l, c)) return true;

        for (int[] d : DIRS)
            if (dfs(l + d[0], c + d[1], visite, chemin)) return true;

        chemin.remove(chemin.size() - 1); // backtrack
        return false;
    }

    public int    getNbEtapes()                    { return nbEtapes; }
    public long   getTempsMs()                     { return tempsMs;  }
    public int    getLongueurChemin(List<int[]> c) { return c != null ? c.size() : 0; }

    public String getStats(List<int[]> ch) {
        return String.format("[DFS] Cases explorees : %d | Longueur chemin : %d | Temps : %d ms",
            nbEtapes, getLongueurChemin(ch), tempsMs);
    }
}