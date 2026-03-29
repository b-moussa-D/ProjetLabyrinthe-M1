# Resolution de Labyrinthe — Master 1 GLSI / SRT

Projet realise dans le cadre du module **Programmation et Algorithmique Avancee** (Dr. Mouhamed DIOP).
L'objectif est d'implementer deux algorithmes de parcours de graphe (DFS et BFS) pour resoudre un labyrinthe
represente sous forme de matrice, en utilisant uniquement des structures de donnees codees de zero (pas de Collections Java).

---

## Groupe

| Etudiant | Partie traitee |
|----------|---------------|
| Baye Moussa Diongue | Structures de base : Maillon, Cellule, Labyrinthe |
| Papa Amady Diallo | Pile + algorithme DFS |
| Koumba Samb | File + algorithme BFS + comparaison |

---

## Architecture du projet

```
ProjetLabyrinthe-M1/
|
|-- Maillon.java              [OK - Baye]   liste chainee generique, base de Pile et File
|-- Cellule.java              [OK - Baye]   noeud pour le parcours BFS (coordonnees + parent)
|-- Labyrinthe.java           [OK - Baye]   matrice char[][], chargement fichier, generation aleatoire
|
|-- Pile.java                 [A COMPLETER - Papa Amady]   pile LIFO sur liste chainee
|-- SolveurDFS.java           [A COMPLETER - Papa Amady]   parcours en profondeur avec backtrack
|
|-- File.java                 [A COMPLETER - Koumba]   file FIFO sur liste chainee
|-- SolveurBFS.java           [A COMPLETER - Koumba]   parcours en largeur, chemin optimal
|-- Comparateur.java          [A COMPLETER - Koumba]   comparaison DFS vs BFS
|
|-- Main.java                 [A FAIRE ensemble]   menu interactif, integration finale
|
`-- exemples/
    |-- labyrinthe1.txt       [OK - Baye]   grille 7x7 simple
    `-- labyrinthe2.txt       [OK - Baye]   grille 11x11 plus complexe
```

> Les fichiers marques **[A COMPLETER]** doivent etre ajoutes au depot par le membre concerne.
> Une fois tous les fichiers presents, on complete **Main.java** ensemble avant la deadline.

---

## Format du fichier labyrinthe

Le labyrinthe est represente en texte brut. Chaque caractere correspond a une case :

```
#######
#S====#
#=###=#
#=====#
###=###
#====E#
#######
```

- `#` — mur infranchissable
- `=` — passage libre
- `S` — point de depart
- `E` — sortie
- `+` — chemin trouve (affiche apres resolution)

---

## Compilation et execution

Depuis le dossier racine du projet (la ou se trouvent tous les `.java`) :

```bash
# Compiler tout d'un coup
javac *.java

# Lancer
java Main
```

Le menu propose : charger un fichier, generer un labyrinthe aleatoire, resoudre avec DFS, resoudre avec BFS, comparer les deux.

> **Note** : pour tester avec les fichiers fournis, saisir `exemples/labyrinthe1.txt` ou `exemples/labyrinthe2.txt` quand le programme demande le chemin.

---

## Fonctionnement des algorithmes

**DFS (Depth First Search)**
Explore une direction a fond avant de revenir en arriere (backtracking). Rapide a implementer, mais ne garantit pas le chemin le plus court. On utilise la recursivite — la pile d'appels joue le role de pile de donnees.

**BFS (Breadth First Search)**
Explore case par case en s'eloignant progressivement du depart. Garantit le chemin le plus court en nombre de cases. Necessite une file pour stocker les cases a visiter.

La classe `Comparateur` lance les deux, mesure les cases explorees, la longueur du chemin trouve et le temps d'execution, puis affiche un tableau recapitulatif.

---

## Lien video de presentation

> A completer apres enregistrement : [YouTube — lien a ajouter]

---

*Deadline : 12 avril 2026 — envoi a envoitp@gmail.com, objet : Projet_Labyrinthe_Gx*
