package fr.pgah;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Composant graphique qui affiche l'écosystème.
 */
public class BestiolePanel extends JPanel {
  // private static final long serialVersionUID = -2894195307394787789L;
  private BestioleModel myModel;
  private Font myFont;
  private static boolean created;

  public static final int FONT_SIZE = 12;

  public BestiolePanel(BestioleModel model) {
    if (created)
      throw new RuntimeException("Un seul écosystème");
    created = true;

    myModel = model;
    myFont = new Font("Monospaced", Font.BOLD, FONT_SIZE + 4);
    setBackground(Color.BLACK);
    setPreferredSize(
        new Dimension(FONT_SIZE * model.getWidth() + 20, FONT_SIZE * model.getHeight() + 20));
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Dessine les bestioles comme des cases colorées
    Iterator<Bestiole> i = myModel.iterator();
    while (i.hasNext()) {
      Bestiole next = i.next();
      Point p = myModel.getPoint(next);

      // Dessine une case remplie de la couleur de la bestiole
      g.setColor(myModel.getColor(next));
      g.fillRect(p.x * FONT_SIZE + 11, p.y * FONT_SIZE + 11, FONT_SIZE - 1, FONT_SIZE - 1);

      // En mode debug, affiche la flèche de direction par-dessus
      String appearance = myModel.getAppearance(next);
      if (!appearance.equals(myModel.getString(next))) {
        g.setColor(Color.WHITE);
        g.setFont(myFont);
        g.drawString(appearance, p.x * FONT_SIZE + 11, p.y * FONT_SIZE + FONT_SIZE + 9);
      }
    }
  }
}
