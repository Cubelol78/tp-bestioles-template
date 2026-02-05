package fr.pgah;

import java.awt.*;

/**
 * Les tigres blancs ont le même comportement que les tigres mais sont toujours blancs.
 * Leur symbole change après avoir tenté d'infecter pour la première fois.
 */
public class TigreBlanc extends Bestiole {

  private boolean aInfecte;

  public TigreBlanc() {
    this.aInfecte = false;
  }

  public Action getAction(BestioleInfo info) {
    Action action;

    // Infecte si un ennemi est en face
    if (info.getEnFace() == Voisin.AUTRE) {
      action = Action.INFECTER;
      aInfecte = true;  // Marque qu'il a tenté d'infecter
    }
    // Si un mur est en face ou à droite, tourne à gauche
    else if (info.getEnFace() == Voisin.MUR || info.getADroite() == Voisin.MUR) {
      action = Action.GAUCHE;
    }
    // Si un autre tigre blanc est en face, tourne à droite
    else if (info.getEnFace() == Voisin.MEME) {
      action = Action.DROITE;
    }
    // Sinon saute
    else {
      action = Action.SAUTER;
    }

    return action;
  }

  public Color getCouleur() {
    return Color.WHITE;
  }

  public String toString() {
    return aInfecte ? "B" : "b";
  }
}
