package fr.pgah;

import java.awt.*;

/**
 * Les crocodiles tentent d'infecter dès qu'ils ont une autre bestiole en face. Le reste du temps
 * ils tournent sur eux-mêmes en attendant d'autres proies.
 */
public class Crocodile extends Bestiole {

  public Action getAction(BestioleInfo info) {
    // Infecte si un ennemi est en face
    if (info.getEnFace() == Voisin.AUTRE) {
      return Action.INFECTER;
    }
    // Si un autre crocodile est à côté, avance pour rester avec le groupe
    else if (info.getADroite() == Voisin.MEME || info.getAGauche() == Voisin.MEME) {
      if (info.getEnFace() == Voisin.RIEN) {
        return Action.SAUTER;  // Avance le long du groupe
      } else {
        return Action.DROITE;  // Tourne pour longer le groupe
      }
    }
    // Si un mur est en face, tourne
    else if (info.getEnFace() == Voisin.MUR) {
      return Action.GAUCHE;
    }
    // Si un autre crocodile est en face, tourne pour le contourner
    else if (info.getEnFace() == Voisin.MEME) {
      return Action.DROITE;
    }
    // Sinon avance
    else {
      return Action.SAUTER;
    }
  }
  

  public Color getCouleur() {
    return Color.GREEN;
  }

  public String toString() {
    return "C";
  }
}
