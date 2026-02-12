package fr.pgah;

import java.awt.Color;

public class ChatNinja extends Bestiole {

  @Override
  public Action getAction(BestioleInfo info) {
    Voisin enFace = info.getEnFace();
    Voisin aDroite = info.getADroite();
    Voisin aGauche = info.getAGauche();

    boolean menaceDroite = info.MenaceADroite();
    boolean menaceGauche = info.MenaceAGauche();
    boolean menaceDerriere = info.MenaceDerriere();

    // Ennemi en face : infecter immediatement
    if (enFace == Voisin.AUTRE) {
      return Action.INFECTER;
    }

    // Menace laterale avec ennemi : pivoter pour contre-attaquer
    if (aDroite == Voisin.AUTRE && menaceDroite) {
      return Action.DROITE;
    }
    if (aGauche == Voisin.AUTRE && menaceGauche) {
      return Action.GAUCHE;
    }

    // Menace par derriere : fuir en sautant (immunite + bonus hop)
    if (menaceDerriere && enFace == Voisin.RIEN) {
      return Action.SAUTER;
    }

    // Ennemi a proximite : chasser
    if (aGauche == Voisin.AUTRE) {
      return Action.GAUCHE;
    }
    if (aDroite == Voisin.AUTRE) {
      return Action.DROITE;
    }

    // Voie libre : sauter (immunite ce tour + bypass resistance au prochain tour)
    if (enFace == Voisin.RIEN) {
      return Action.SAUTER;
    }

    // Chercher un passage libre
    if (aGauche == Voisin.RIEN) {
      return Action.GAUCHE;
    }
    if (aDroite == Voisin.RIEN) {
      return Action.DROITE;
    }

    // Bloque de partout : tourner
    return Action.GAUCHE;
  }

  @Override
  public Color getCouleur() {
    return Color.RED;
  }

  @Override
  public String toString() {
    return "N";
  }
}
