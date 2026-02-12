package fr.pgah;

/**
 * Contient la méthode principale de la simulation. Vous pouvez modifier les bestioles présentes,
 * leur nombre ou encore la taille de la Frame (valeurs passées au constructeur de BestioleFrame).
 */
public class BestiolesApp {

  public static void main(String[] args) {
    BestioleFrame frame = new BestioleFrame(60, 40);

    // Dé-commentez les lignes au fur et à mesure que les classes sont implémentées
    frame.add(50, Plancton.class);
    frame.add(100, Crocodile.class);
    frame.add(100, Ours.class);
    frame.add(100, Tigre.class);
    frame.add(100, TigreBlanc.class);
    frame.add(100, Yeti.class);
    frame.add(5, ChatNinja.class);

    // Démarre la simulation
    frame.start();
  }
}
