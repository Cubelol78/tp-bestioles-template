package fr.pgah;

import java.awt.*;

/**
 * Les crocodiles tentent d'infecter dès qu'ils ont une autre bestiole en face. Le reste du temps
 * ils tournent sur eux-mêmes en attendant d'autres proies.
 */
public class Crocodile extends Bestiole {

  public Action getAction(BestioleInfo info) {
    if (info.getEnFace() == Voisin.AUTRE) {
      return Action.INFECTER;
    } else {
      return Action.GAUCHE;
    }
  }

  public Color getCouleur() {
    return Color.GREEN;
  }

  public String toString() {
    return "C";
  }
}
