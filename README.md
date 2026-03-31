# Resolution de Labyrinthe -- Master 1 GLSI / SRT

Projet realise dans le cadre du module **Programmation et Algorithmique Avancee** (Dr. Mouhamed DIOP).
L'objectif est d'implementer deux algorithmes de parcours (DFS et BFS) pour resoudre un labyrinthe
represente sous forme de matrice, avec des structures de donnees codees de zero.

---

## Groupe

| Etudiant | Partie traitee |
|----------|----------------|
| Baye Moussa Diongue | Structures de base : Maillon, Cellule, Labyrinthe |
| Papa Amady Diallo | Pile + algorithme DFS |
| Koumba Samb | File + algorithme BFS + comparaison |

---

## Architecture du projet

```
ProjetLabyrinthe-M1/
|
|-- Maillon.java         [OK - Baye Moussa Diongue]   liste chainee generique, base de Pile et File
|-- Cellule.java         [OK - Baye Moussa Diongue]   noeud BFS (coordonnees + reference parent)
|-- Labyrinthe.java      [OK - Baye Moussa Diongue]   matrice char[][], chargement, generation aleatoire
|
|-- Pile.java            [OK - Papa Amady Diallo]      pile LIFO sur liste chainee, O(1)
|-- SolveurDFS.java      [OK - Papa Amady Diallo]      parcours en profondeur avec backtracking
|
|-- File.java            [OK - Koumba Samb]            file FIFO sur liste chainee, O(1)
|-- SolveurBFS.java      [OK - Koumba Samb]            parcours en largeur, chemin optimal garanti
|-- Comparateur.java     [OK - Koumba Samb]            comparaison DFS vs BFS (stats + tableau)
|
|-- Main.java            [OK - Tous]                   menu interactif, integration finale
|
\-- exemples/
    |-- labyrinthe1.txt  [OK - Baye Moussa Diongue]   grille 7x7 simple
    \-- labyrinthe2.txt  [OK - Baye Moussa Diongue]   grille 11x11 complexe
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
# Depuis le dossier racine (tous les .java au meme endroit)
javac *.java

# Lancer
java Main
```

Quand le menu s'affiche, saisir `exemples/labyrinthe1.txt` ou `exemples/labyrinthe2.txt`
pour charger un fichier de test.

---

## Fonctionnement des algorithmes

**DFS** : explore une direction a fond avant de revenir en arriere (backtracking). Rapide mais
ne garantit pas le chemin le plus court.

**BFS** : explore case par case en s'eloignant progressivement du depart. Garantit toujours
le chemin le plus court en nombre de cases.

---

## Lien video de presentation

> [YouTube -- lien a ajouter apres enregistrement]

---

*Deadline : 12 avril 2026 -- mail a envoitp@gmail.com, objet : Projet_Labyrinthe_Gx*