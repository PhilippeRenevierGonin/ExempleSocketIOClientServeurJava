package lanceur;

import config.CONFIG;
import joueur.Joueur;
import moteur.Partie;

public class Lanceur {


    public static final void main(String  [] args) {
        Partie p = new Partie();
        Joueur [] joueurs = new Joueur[CONFIG.NB_JOUEURS];

        // création des 4 joueurs
        for(int i = 0 ; i < CONFIG.NB_JOUEURS; i++) {
            joueurs[i] = new Joueur("Joueur"+(i+1));
        }

        // démarrage du jeu
        p.démarrer();
        for(int i = 0 ; i < CONFIG.NB_JOUEURS; i++) {
            joueurs[i].démarrer();
        }
    }
}
