package fr.pgah;

import java.awt.*;
import java.util.Random;

/**
 * Les tigres changent de couleur tous les 3 mouvements et ont un comportement d'évitement des murs.
 */
public class Tigre extends Bestiole {

  private Color couleurActuelle;
  private int compteurMouvements;
  private static final Random random = new Random();

  public Tigre() {
    this.compteurMouvements = 0;
    this.couleurActuelle = choisirCouleurAleatoire();
  }

  private Color choisirCouleurAleatoire() {
    int choix = random.nextInt(3);
    switch (choix) {
      case 0:
        return Color.RED;
      case 1:
        return Color.GREEN;
      default:
        return Color.BLUE;
    }
  }

  public Action getAction(BestioleInfo info) {
    // Incrémenter le compteur et changer de couleur tous les 3 mouvements
    compteurMouvements++;
    if (compteurMouvements >= 3) {
      compteurMouvements = 0;
      couleurActuelle = choisirCouleurAleatoire();
    }

    // Infecte si un ennemi est en face
    if (info.getEnFace() == Voisin.AUTRE) {
      return Action.INFECTER;
    }
    // Si un mur est en face ou à droite, tourne à gauche
    else if (info.getEnFace() == Voisin.MUR || info.getADroite() == Voisin.MUR) {
      return Action.GAUCHE;
    }
    // Si un autre tigre est en face, tourne à droite
    else if (info.getEnFace() == Voisin.MEME) {
      return Action.DROITE;
    }
    // Sinon saute
    else {
      return Action.SAUTER;
    }
  }

  public Color getCouleur() {
    return couleurActuelle;
  }

  public String toString() {
    return "T";
  }
}
