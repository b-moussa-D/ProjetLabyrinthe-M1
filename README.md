# Projet Resolution de Labyrinthe - Master 1 GLSI

Resolution de labyrinthe en Java avec les algorithmes DFS et BFS,
structures de donnees maison (liste chainee, pile, file).

## Membres du groupe
| Membre | Lot | Fichiers |
|--------|-----|---------|
| Baye Moussa Diongue | Lot 1 - Structures de base | `Maillon.java`, `Cellule.java`, `Labyrinthe.java` |
| Papa Amady Diallo | Lot 2 - DFS | `Pile.java`, `SolveurDFS.java` |
| Koumba Samb | Lot 3 - BFS + Comparaison | `File.java`, `SolveurBFS.java`, `Comparateur.java` |
| Tous | Integration | `Main.java` |

## Compilation
```bash
javac *.java
```

## Execution
```bash
java Main
```

## Fichiers exemples
Placer les fichiers dans `exemples/` a la racine :
- `exemples/labyrinthe1.txt` (7x7, simple)
- `exemples/labyrinthe2.txt` (11x11, complexe)

Format : `#`=mur, `=`=passage, `S`=depart, `E`=arrivee

## Fonctionnalites
- Chargement depuis fichier texte
- Generation aleatoire (algorithme de creusage DFS)
- Resolution DFS avec backtracking
- Resolution BFS (chemin optimal garanti)
- Comparaison DFS vs BFS (cases explorees, longueur, temps)
- Affichage console avec chemin marque en `+`

## Video de demonstration
[Lien YouTube ici]
