package fr.pgah;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

/**
 * Classe responsable de l'interface graphique
 */
public class BestioleFrame extends JFrame {
  private static final long serialVersionUID = 3370125876685455991L;
  private BestioleModel myModel;
  private BestiolePanel myPicture;
  private javax.swing.Timer myTimer;
  private JButton[] counts;
  private JButton countButton;
  private boolean started;
  private static boolean created;

  public BestioleFrame(int width, int height) {
    // Interdit la création de plusieurs fenêtres
    if (created)
      throw new RuntimeException("Seulement une instance svp");
    created = true;

    // crée la Frame et le modèle (l'écosystème de bestioles)
    setTitle("Simulation de bestioles");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    myModel = new BestioleModel(width, height);

    // Le Panel où on va dessiner les bestioles
    myPicture = new BestiolePanel(myModel);
    add(myPicture, BorderLayout.CENTER);

    addTimer();

    constructSouth();

    // On ne démarre pas automatiquement
    started = false;
  }

  // Construit l'UI du panel du bas
  private void constructSouth() {
    JPanel p = new JPanel();
    p.setBackground(new Color(245, 245, 245));
    p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Slider de vitesse
    final JSlider slider = new JSlider();
    slider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        double ratio = 1000.0 / (1 + Math.pow(slider.getValue(), 0.3));
        myTimer.setDelay((int) (ratio - 180));
      }
    });
    slider.setValue(20);
    slider.setBackground(new Color(245, 245, 245));

    JLabel labelLent = new JLabel("Lent");
    labelLent.setFont(new Font("Arial", Font.PLAIN, 12));
    p.add(labelLent);
    p.add(slider);
    JLabel labelRapide = new JLabel("Rapide");
    labelRapide.setFont(new Font("Arial", Font.PLAIN, 12));
    p.add(labelRapide);

    // Séparateur
    p.add(Box.createHorizontalStrut(15));

    // Boutons de contrôle
    JButton b1 = createStyledButton("▶ Démarrer", new Color(76, 175, 80));
    b1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myTimer.start();
      }
    });
    p.add(b1);

    JButton b2 = createStyledButton("⏸ Stop", new Color(244, 67, 54));
    b2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myTimer.stop();
      }
    });
    p.add(b2);

    JButton b3 = createStyledButton("➜ Step", new Color(33, 150, 243));
    b3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doOneStep();
      }
    });
    p.add(b3);

    JButton b4 = createStyledButton("🔍 Debug", new Color(156, 39, 176));
    b4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myModel.toggleDebug();
        myPicture.repaint();
      }
    });
    p.add(b4);

    JButton b5 = createStyledButton("+100 steps", new Color(255, 152, 0));
    b5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        multistep(100);
      }
    });
    p.add(b5);

    add(p, BorderLayout.SOUTH);
  }

  // Crée un bouton avec un style moderne
  private JButton createStyledButton(String text, Color color) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.setBackground(color);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setOpaque(true);
    return button;
  }

  // Démarre la simulation (les bestioles doivent déjà avoir été ajoutées)
  public void start() {
    // Interdit les démarrages multiples
    if (started) {
      return;
    }
    if (myModel.getCounts().isEmpty()) {
      System.out.println("Rien à simuler : pas de bestioles !");
      return;
    }
    started = true;
    addClassCounts();
    myModel.updateColorString();
    pack();
    setVisible(true);
  }

  // L'UI de la colonne de droite (nombre de bestioles en vie)
  private void addClassCounts() {
    Set<Map.Entry<String, Integer>> entries = myModel.getCounts();
    JPanel p = new JPanel(new GridLayout(entries.size() + 1, 1, 5, 5));
    p.setBackground(new Color(245, 245, 245));
    p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    counts = new JButton[entries.size()];
    for (int i = 0; i < counts.length; i++) {
      counts[i] = new JButton();
      counts[i].setFont(new Font("Monospaced", Font.BOLD, 14));
      counts[i].setFocusPainted(false);
      counts[i].setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
        BorderFactory.createEmptyBorder(8, 12, 8, 12)
      ));
      counts[i].setBackground(Color.WHITE);
      counts[i].setHorizontalAlignment(SwingConstants.LEFT);
      p.add(counts[i]);
    }

    countButton = new JButton();
    countButton.setFont(new Font("Monospaced", Font.BOLD, 14));
    countButton.setForeground(new Color(33, 150, 243));
    countButton.setBackground(new Color(227, 242, 253));
    countButton.setFocusPainted(false);
    countButton.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(new Color(100, 181, 246), 2),
      BorderFactory.createEmptyBorder(8, 12, 8, 12)
    ));
    countButton.setHorizontalAlignment(SwingConstants.CENTER);
    p.add(countButton);

    add(p, BorderLayout.EAST);
    setCounts();
  }

  private void setCounts() {
    int i = 0;
    int max = 0;
    int maxI = 0;
    for (Map.Entry<String, Integer> entry : myModel.getCounts()) {
      String s = String.format("%-12s %4d", entry.getKey(), (int) entry.getValue());
      counts[i].setText(s);
      counts[i].setForeground(new Color(60, 60, 60));
      counts[i].setBackground(Color.WHITE);
      if (entry.getValue() > max) {
        max = entry.getValue();
        maxI = i;
      }
      i++;
    }
    // Met en évidence l'espèce dominante
    counts[maxI].setForeground(new Color(76, 175, 80));
    counts[maxI].setBackground(new Color(232, 245, 233));
    counts[maxI].setFont(new Font("Monospaced", Font.BOLD, 15));

    String s = String.format("Step : %d", myModel.getSimulationCount());
    countButton.setText(s);
  }

  // Ajoute des bestioles d'une sous-classe particulière dans l'écosystème
  public void add(int number, Class<? extends Bestiole> c) {
    // on n'ajoute plus rien si ça a démarré
    if (started) {
      return;
    }
    // On simule un bref démarrage pour empêcher les constructeurs d'ajouter
    // des bestioles en même temps
    started = true;
    myModel.ajouterBestioles(number, c);
    started = false;
  }

  // Timer qui appelle la mise à jour du modèle à chaque "pas" (step)
  // et redessine l'écosystème avec le nouvel état
  private void addTimer() {
    ActionListener updater = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doOneStep();
      }
    };
    myTimer = new javax.swing.Timer(0, updater);
    myTimer.setCoalesce(true);
  }

  // un "pas" de la simulation
  private void doOneStep() {
    myModel.update();
    setCounts();
    myPicture.repaint();
  }

  // Avance la simulation (si n==100, à la centaine suivante)
  private void multistep(int n) {
    myTimer.stop();
    do {
      myModel.update();
    } while (myModel.getSimulationCount() % n != 0);
    setCounts();
    myPicture.repaint();
  }
}
