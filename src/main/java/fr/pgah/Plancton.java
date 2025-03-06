package fr.pgah;

import java.awt.*;

/**
 * Le plancton reste sur place, et parvient parfois tant bien que mal à infecter d'autres bestioles
 * qui passent devant.
 */
public class Plancton extends Bestiole {

  public Action getAction(BestioleInfo info) {
    return Action.INFECTER;
  }

  public Color getCouleur() {
    return Color.PINK;
  }

  public String toString() {
    return "P";
  }
}
