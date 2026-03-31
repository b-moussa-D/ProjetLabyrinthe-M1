import java.util.*;

/**
 * Programme principal - Application Resolution de Labyrinthe.
 * Menu interactif permettant de charger, generer et resoudre un labyrinthe.
 * @author Baye Moussa Diongue, Papa Amady Diallo, Koumba Samb
 */
public class Main {

    private static Scanner    scanner    = new Scanner(System.in);
    private static Labyrinthe labyrinthe = null;

    public static void main(String[] args) {
        int choix = -1;
        while (choix != 0) {
            afficherMenu();
            choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1: chargerFichier();   break;
                case 2: genererAleatoire(); break;
                case 3: afficher();         break;
                case 4: resoudreDFS();      break;
                case 5: resoudreBFS();      break;
                case 6: comparer();         break;
                case 0: System.out.println("Au revoir !"); break;
                default: System.out.println("Choix invalide, reessayez.");
            }
        }
        scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("\n+-----------------------------------+");
        System.out.println("|    RESOLUTION DE LABYRINTHE       |");
        System.out.println("+-----------------------------------+");
        System.out.println("|  1. Charger depuis un fichier     |");
        System.out.println("|  2. Generer aleatoirement         |");
        System.out.println("|  3. Afficher le labyrinthe        |");
        System.out.println("|  4. Resoudre avec DFS             |");
        System.out.println("|  5. Resoudre avec BFS             |");
        System.out.println("|  6. Comparer DFS et BFS           |");
        System.out.println("|  0. Quitter                       |");
        System.out.println("+-----------------------------------+");
    }

    private static void chargerFichier() {
        System.out.print("Chemin du fichier : ");
        String ch = scanner.nextLine().trim();
        try {
            labyrinthe = new Labyrinthe(ch);
            System.out.println("Labyrinthe charge avec succes.");
            labyrinthe.afficher();
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private static void genererAleatoire() {
        int l = lireEntier("Nombre de lignes (impair, ex: 11) : ");
        int c = lireEntier("Nombre de colonnes (impair, ex: 21) : ");
        labyrinthe = new Labyrinthe(l, c);
        System.out.println("Labyrinthe genere :");
        labyrinthe.afficher();
    }

    private static void afficher() {
        if (labyrinthe == null) { System.out.println("Aucun labyrinthe charge."); return; }
        labyrinthe.afficher();
    }

    private static void resoudreDFS() {
        if (labyrinthe == null) { System.out.println("Aucun labyrinthe charge."); return; }
        labyrinthe.reset();
        SolveurDFS dfs = new SolveurDFS(labyrinthe);
        List<int[]> ch = dfs.resoudre();
        if (ch == null) { System.out.println("DFS : aucun chemin trouve."); return; }
        labyrinthe.marquerChemin(ch);
        labyrinthe.afficher();
        System.out.println(dfs.getStats(ch));
    }

    private static void resoudreBFS() {
        if (labyrinthe == null) { System.out.println("Aucun labyrinthe charge."); return; }
        labyrinthe.reset();
        SolveurBFS bfs = new SolveurBFS(labyrinthe);
        List<int[]> ch = bfs.resoudre();
        if (ch == null) { System.out.println("BFS : aucun chemin trouve."); return; }
        labyrinthe.marquerChemin(ch);
        labyrinthe.afficher();
        System.out.println(bfs.getStats(ch));
    }

    private static void comparer() {
        if (labyrinthe == null) { System.out.println("Aucun labyrinthe charge."); return; }
        Comparateur.comparer(labyrinthe);
    }

    private static int lireEntier(String msg) {
        while (true) {
            System.out.print(msg);
            try { return Integer.parseInt(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Veuillez entrer un entier."); }
        }
    }
}