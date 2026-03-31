import java.util.List;

/**
 * Compare les performances de DFS et BFS sur un meme labyrinthe.
 * @author Koumba Samb
 */
public class Comparateur {

    public static void comparer(Labyrinthe lab) {
        System.out.println("================================================");
        System.out.println("         COMPARAISON DFS vs BFS");
        System.out.println("================================================");

        lab.reset();
        SolveurDFS dfs = new SolveurDFS(lab);
        List<int[]> chDFS = dfs.resoudre();

        lab.reset();
        SolveurBFS bfs = new SolveurBFS(lab);
        List<int[]> chBFS = bfs.resoudre();

        System.out.printf("%-28s %-12s %-12s%n", "Critere", "DFS", "BFS");
        System.out.println("------------------------------------------------");
        System.out.printf("%-28s %-12d %-12d%n",
            "Cases explorees", dfs.getNbEtapes(), bfs.getNbEtapes());
        System.out.printf("%-28s %-12d %-12d%n",
            "Longueur du chemin",
            dfs.getLongueurChemin(chDFS), bfs.getLongueurChemin(chBFS));
        System.out.printf("%-28s %-12d %-12d%n",
            "Temps d'execution (ms)", dfs.getTempsMs(), bfs.getTempsMs());
        System.out.println("------------------------------------------------");

        if (chBFS != null && chDFS != null) {
            int diff = dfs.getLongueurChemin(chDFS) - bfs.getLongueurChemin(chBFS);
            if (diff > 0)
                System.out.println("-> BFS trouve un chemin plus court de " + diff + " case(s).");
            else if (diff == 0)
                System.out.println("-> Les deux algorithmes trouvent la meme longueur.");
            else
                System.out.println("-> DFS trouve un chemin plus court de " + (-diff) + " case(s).");
        }
        System.out.println("================================================");
    }
}