import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Interface graphique Swing pour la resolution de labyrinthe.
 * Affiche la grille coloree et les chemins DFS/BFS avec statistiques.
 * @author Baye Moussa Diongue
 */
public class LabyrintheGUI extends JFrame {

    private static final Color COULEUR_MUR      = new Color(40,  40,  40);
    private static final Color COULEUR_PASSAGE  = new Color(240, 240, 230);
    private static final Color COULEUR_DEPART   = new Color(46,  125, 50);
    private static final Color COULEUR_ARRIVEE  = new Color(198, 40,  40);
    private static final Color COULEUR_CHEMIN   = new Color(33,  150, 243);
    private static final int   CELL_SIZE        = 24;

    private JPanel    panGrille;
    private JTextArea taStats;
    private JButton   btnCharger, btnGenerer, btnDFS, btnBFS, btnComparer, btnReset;
    private JLabel    lblStatut;
    private Labyrinthe labyrinthe = null;

    public LabyrintheGUI() {
        setTitle("Resolution de Labyrinthe - DFS et BFS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        construireInterface();
        pack();
        setLocationRelativeTo(null);
    }

    private void construireInterface() {
        setLayout(new BorderLayout(8, 8));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panControle = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        panControle.setBackground(new Color(45, 45, 55));
        btnCharger  = creerBouton("Charger fichier",   new Color(21,  101, 192));
        btnGenerer  = creerBouton("Generer aleatoire", new Color(0,   105, 92));
        btnDFS      = creerBouton("Resoudre DFS",      new Color(94,  53,  177));
        btnBFS      = creerBouton("Resoudre BFS",      new Color(173, 20,  87));
        btnComparer = creerBouton("Comparer",           new Color(230, 81,  0));
        btnReset    = creerBouton("Reinitialiser",      new Color(69,  90,  100));
        setActionsEnabled(false);
        panControle.add(btnCharger); panControle.add(btnGenerer);
        panControle.add(Box.createHorizontalStrut(10));
        panControle.add(btnDFS); panControle.add(btnBFS);
        panControle.add(btnComparer); panControle.add(btnReset);
        add(panControle, BorderLayout.NORTH);

        panGrille = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (labyrinthe != null) dessinerGrille(g);
            }
        };
        panGrille.setBackground(new Color(30, 30, 30));
        panGrille.setPreferredSize(new Dimension(600, 420));
        JScrollPane scroll = new JScrollPane(panGrille);
        scroll.setBorder(BorderFactory.createTitledBorder("Labyrinthe"));
        add(scroll, BorderLayout.CENTER);

        JPanel panBas = new JPanel(new BorderLayout(6, 4));
        panBas.setBackground(new Color(45, 45, 55));
        panBas.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        taStats = new JTextArea(5, 60);
        taStats.setEditable(false);
        taStats.setFont(new Font("Monospaced", Font.PLAIN, 12));
        taStats.setBackground(new Color(25, 25, 35));
        taStats.setForeground(new Color(180, 230, 180));
        taStats.setBorder(BorderFactory.createTitledBorder("Statistiques"));
        panBas.add(new JScrollPane(taStats), BorderLayout.CENTER);
        panBas.add(construireLegend(), BorderLayout.EAST);

        lblStatut = new JLabel("  Chargez ou generez un labyrinthe pour commencer.");
        lblStatut.setForeground(Color.LIGHT_GRAY);
        lblStatut.setFont(new Font("SansSerif", Font.ITALIC, 11));
        panBas.add(lblStatut, BorderLayout.SOUTH);
        add(panBas, BorderLayout.SOUTH);

        btnCharger.addActionListener(e  -> chargerFichier());
        btnGenerer.addActionListener(e  -> genererAleatoire());
        btnDFS.addActionListener(e      -> resoudreDFS());
        btnBFS.addActionListener(e      -> resoudreBFS());
        btnComparer.addActionListener(e -> comparer());
        btnReset.addActionListener(e    -> reinitialiser());
    }

    private JPanel construireLegend() {
        JPanel p = new JPanel(new GridLayout(0, 1, 2, 2));
        p.setBackground(new Color(45, 45, 55));
        p.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        ajouterLegende(p, COULEUR_MUR,     "Mur (#)");
        ajouterLegende(p, COULEUR_PASSAGE, "Passage (=)");
        ajouterLegende(p, COULEUR_DEPART,  "Depart (S)");
        ajouterLegende(p, COULEUR_ARRIVEE, "Arrivee (E)");
        ajouterLegende(p, COULEUR_CHEMIN,  "Chemin (+)");
        return p;
    }

    private void ajouterLegende(JPanel p, Color c, String label) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        row.setBackground(new Color(45, 45, 55));
        JLabel carre = new JLabel("   ");
        carre.setOpaque(true); carre.setBackground(c);
        carre.setPreferredSize(new Dimension(14, 14));
        JLabel txt = new JLabel(label);
        txt.setForeground(Color.LIGHT_GRAY);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 10));
        row.add(carre); row.add(txt);
        p.add(row);
    }

    private void dessinerGrille(Graphics g) {
        char[][] grille = labyrinthe.getGrille();
        int rows = labyrinthe.getNbLignes(), cols = labyrinthe.getNbColonnes();
        int w = cols * CELL_SIZE, h = rows * CELL_SIZE;
        if (panGrille.getPreferredSize().width != w) {
            panGrille.setPreferredSize(new Dimension(w, h));
            panGrille.revalidate();
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = grille[i][j];
                Color couleur;
                switch (c) {
                    case Labyrinthe.MUR:     couleur = COULEUR_MUR;     break;
                    case Labyrinthe.DEPART:  couleur = COULEUR_DEPART;  break;
                    case Labyrinthe.ARRIVEE: couleur = COULEUR_ARRIVEE; break;
                    case Labyrinthe.CHEMIN:  couleur = COULEUR_CHEMIN;  break;
                    default:                 couleur = COULEUR_PASSAGE; break;
                }
                g2.setColor(couleur);
                g2.fillRect(j*CELL_SIZE, i*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g2.setColor(new Color(0, 0, 0, 50));
                g2.drawRect(j*CELL_SIZE, i*CELL_SIZE, CELL_SIZE-1, CELL_SIZE-1);
                if (c == Labyrinthe.DEPART || c == Labyrinthe.ARRIVEE) {
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("SansSerif", Font.BOLD, CELL_SIZE-8));
                    FontMetrics fm = g2.getFontMetrics();
                    String s = String.valueOf(c);
                    g2.drawString(s, j*CELL_SIZE+(CELL_SIZE-fm.stringWidth(s))/2,
                        i*CELL_SIZE+(CELL_SIZE+fm.getAscent()-fm.getDescent())/2);
                }
            }
        }
    }

    private void chargerFichier() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Fichiers texte (*.txt)", "txt"));
        fc.setCurrentDirectory(new java.io.File("exemples"));
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try {
            labyrinthe = new Labyrinthe(fc.getSelectedFile().getAbsolutePath());
            setActionsEnabled(true);
            taStats.setText("Labyrinthe charge : " + labyrinthe.getNbLignes()
                + " lignes x " + labyrinthe.getNbColonnes() + " colonnes.");
            lblStatut.setText("  Labyrinthe charge. Cliquez DFS, BFS ou Comparer.");
            panGrille.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void genererAleatoire() {
        String ls = JOptionPane.showInputDialog(this, "Lignes (impair, ex: 21) :", "21");
        String cs = JOptionPane.showInputDialog(this, "Colonnes (impair, ex: 31) :", "31");
        if (ls == null || cs == null) return;
        try {
            labyrinthe = new Labyrinthe(Integer.parseInt(ls.trim()), Integer.parseInt(cs.trim()));
            setActionsEnabled(true);
            taStats.setText("Genere : " + labyrinthe.getNbLignes() + "x" + labyrinthe.getNbColonnes());
            lblStatut.setText("  Labyrinthe genere. Cliquez DFS, BFS ou Comparer.");
            panGrille.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Entiers requis.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resoudreDFS() {
        if (labyrinthe == null) return;
        labyrinthe.reset();
        SolveurDFS dfs = new SolveurDFS(labyrinthe);
        List<int[]> ch = dfs.resoudre();
        if (ch == null) { taStats.setText("DFS : aucun chemin."); return; }
        labyrinthe.marquerChemin(ch);
        taStats.setText(dfs.getStats(ch));
        lblStatut.setText("  DFS resolu - chemin affiche en bleu.");
        panGrille.repaint();
    }

    private void resoudreBFS() {
        if (labyrinthe == null) return;
        labyrinthe.reset();
        SolveurBFS bfs = new SolveurBFS(labyrinthe);
        List<int[]> ch = bfs.resoudre();
        if (ch == null) { taStats.setText("BFS : aucun chemin."); return; }
        labyrinthe.marquerChemin(ch);
        taStats.setText(bfs.getStats(ch));
        lblStatut.setText("  BFS resolu - chemin optimal affiche en bleu.");
        panGrille.repaint();
    }

    private void comparer() {
        if (labyrinthe == null) return;
        labyrinthe.reset();
        SolveurDFS dfs    = new SolveurDFS(labyrinthe);
        List<int[]> chDFS = dfs.resoudre();
        labyrinthe.reset();
        SolveurBFS bfs    = new SolveurBFS(labyrinthe);
        List<int[]> chBFS = bfs.resoudre();
        if (chBFS != null) labyrinthe.marquerChemin(chBFS);
        panGrille.repaint();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-30s %-14s %-14s%n", "Critere", "DFS", "BFS"));
        sb.append("--------------------------------------------------------------\n");
        sb.append(String.format("%-30s %-14d %-14d%n", "Cases explorees",
            dfs.getNbEtapes(), bfs.getNbEtapes()));
        sb.append(String.format("%-30s %-14d %-14d%n", "Longueur chemin",
            dfs.getLongueurChemin(chDFS), bfs.getLongueurChemin(chBFS)));
        sb.append(String.format("%-30s %-14d %-14d ms%n", "Temps",
            dfs.getTempsMs(), bfs.getTempsMs()));
        sb.append("--------------------------------------------------------------\n");
        if (chDFS != null && chBFS != null) {
            int d = dfs.getLongueurChemin(chDFS) - bfs.getLongueurChemin(chBFS);
            sb.append(d > 0 ? "-> BFS plus court de "+d+" case(s)."
                : d == 0 ? "-> Meme longueur." : "-> DFS plus court de "+(-d)+" case(s).");
        }
        taStats.setText(sb.toString());
        lblStatut.setText("  Comparaison terminee - chemin BFS affiche.");
    }

    private void reinitialiser() {
        if (labyrinthe == null) return;
        labyrinthe.reset();
        taStats.setText("Labyrinthe reinitialise.");
        lblStatut.setText("  Chemin efface.");
        panGrille.repaint();
    }

    private JButton creerBouton(String t, Color bg) {
        JButton b = new JButton(t);
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 11));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void setActionsEnabled(boolean e) {
        btnDFS.setEnabled(e); btnBFS.setEnabled(e);
        btnComparer.setEnabled(e); btnReset.setEnabled(e);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new LabyrintheGUI().setVisible(true);
        });
    }
}
