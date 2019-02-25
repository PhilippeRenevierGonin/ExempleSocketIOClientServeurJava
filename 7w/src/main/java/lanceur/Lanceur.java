package lanceur;

import joueur.Joueur;
import moteur.Partie;

public class Lanceur {


    public static final void main(String  [] args) {
        Partie p = new Partie();
        Joueur joueur = new Joueur("un joueur");

        p.démarrer();
        joueur.démarrer();
    }
}
