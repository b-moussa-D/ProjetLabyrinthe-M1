# Resolution de Labyrinthe -- Master 1 GLSI / SRT

Projet realise dans le cadre du module **Programmation et Algorithmique Avancee** (Dr. Mouhamed DIOP).
Implementation des algorithmes DFS et BFS pour resoudre un labyrinthe represente sous forme de matrice,
avec des structures de donnees codees de zero (pas de Collections Java).

---

## Groupe 6

| Etudiant | Partie traitee |
|----------|----------------|
| Baye Moussa Diongue | Structures de base : Maillon, Cellule, Labyrinthe, LabyrintheGUI |
| Papa Amady Diallo | Pile + algorithme DFS |
| Koumba Samb | File + algorithme BFS + comparaison |

---

## Architecture du projet

```
ProjetLabyrinthe-M1/
|
|-- Maillon.java          [OK - Baye Moussa Diongue]   liste chainee generique, base de Pile et File
|-- Cellule.java          [OK - Baye Moussa Diongue]   noeud BFS (coordonnees + reference parent)
|-- Labyrinthe.java       [OK - Baye Moussa Diongue]   matrice char[][], chargement, generation aleatoire
|-- LabyrintheGUI.java    [OK - Baye Moussa Diongue]   interface graphique Swing (BONUS)
|
|-- Pile.java             [OK - Papa Amady Diallo]     pile LIFO sur liste chainee, O(1)
|-- SolveurDFS.java       [OK - Papa Amady Diallo]     parcours en profondeur avec backtracking
|
|-- File.java             [OK - Koumba Samb]           file FIFO sur liste chainee, O(1)
|-- SolveurBFS.java       [OK - Koumba Samb]           parcours en largeur, chemin optimal garanti
|-- Comparateur.java      [OK - Koumba Samb]           comparaison DFS vs BFS (stats + tableau)
|
|-- Main.java             [OK - Tous]                  menu interactif, integration finale
|
\-- exemples/
    |-- labyrinthe1.txt   [OK - Baye Moussa Diongue]   grille 7x7 simple
    \-- labyrinthe2.txt   [OK - Baye Moussa Diongue]   grille 11x11 complexe
```

---

## Format du fichier labyrinthe

```
#######
#S====#
#=###=#
#=====#
###=###
#====E#
#######
```

- `#` -- mur infranchissable
- `=` -- passage libre
- `S` -- point de depart
- `E` -- sortie
- `+` -- chemin trouve (affiche apres resolution)

---

## Compilation et execution

```bash
# Compiler toutes les classes
javac *.java

# Mode console (menu interactif)
java Main

# Mode graphique avec interface Swing (BONUS)
java LabyrintheGUI
```

Dans le menu console, saisir `exemples/labyrinthe1.txt` ou `exemples/labyrinthe2.txt` pour charger un fichier de test.

---

## Fonctionnement des algorithmes

**DFS (Depth First Search)** : explore une direction a fond avant de revenir en arriere (backtracking). Rapide mais ne garantit pas le chemin le plus court.

**BFS (Breadth First Search)** : explore case par case par niveaux de distance. Garantit toujours le chemin le plus court en nombre de cases.

**Comparateur** : lance les deux algorithmes sur le meme labyrinthe et affiche un tableau : cases explorees, longueur du chemin, temps d'execution.

---

## Lien video de presentation

[https://youtu.be/9TPx6LmuiNg](https://youtu.be/9TPx6LmuiNg)

---

*Groupe 6 -- Deadline : 12 avril 2026 -- envoitp@gmail.com -- Projet_Labyrinthe_G6*