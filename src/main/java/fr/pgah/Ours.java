package fr.pgah;

import java.awt.Color;
import java.awt.*;

public class Ours extends Bestiole {

  private boolean polaire;

  public Ours(boolean polaire) {
    this.polaire = polaire;
  }

  public Action getAction(BestioleInfo info) {
    if (info.getEnFace() == Voisin.RIEN) {
      return Action.SAUTER;  // Avance si la case devant est libre
    } else if (info.getEnFace() == Voisin.AUTRE) {
      return Action.INFECTER;  // Infecte s'il y a une autre bestiole
    } else {
      return Action.GAUCHE;  // Tourne sinon (mur ou même espèce)
    }
  }

  public Color getCouleur() {
    if (polaire) {
      return Color.WHITE;
    } else {
      return new Color(165, 42, 42);
    }
  }

  public String toString() {
    return "O";
  }

}
