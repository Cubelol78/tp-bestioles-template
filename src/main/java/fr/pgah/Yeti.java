package fr.pgah;

import java.awt.Color;

public class Yeti extends Bestiole {

  public Action getAction(BestioleInfo info) {
    if (info.getEnFace() == Voisin.AUTRE) {
      return Action.INFECTER;  // Infecte s'il y a une autre bestiole en face
    } else if (info.getEnFace() == Voisin.RIEN) {
      return Action.SAUTER;  // Avance si la case devant est libre
    } else {
      return Action.DROITE;  // Tourne sinon (mur ou même espèce)
    }
  }

  public Color getCouleur() {
    return Color.WHITE;  // Le Yeti est blanc
  }

  public String toString() {
    return "Y";
  }
    
}
