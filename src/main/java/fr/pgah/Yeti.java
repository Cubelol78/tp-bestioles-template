package fr.pgah;

import java.awt.Color;

public class Yeti extends Bestiole {

  private int nbMouvements;

  public Action getAction(BestioleInfo info) {
    nbMouvements++;
    if (info.getEnFace() == Voisin.AUTRE) {
      return Action.INFECTER;
    } else if (info.getEnFace() == Voisin.RIEN) {
      return Action.SAUTER;
    } else {
      return Action.DROITE;
    }
  }

  public Color getCouleur() {
    return Color.WHITE; 
  }

  public String toString() {
    String[] lettres = {"Y", "E", "T", "I"};
    int index = (nbMouvements / 6) % 4;
    return lettres[index];
  }
    
}
