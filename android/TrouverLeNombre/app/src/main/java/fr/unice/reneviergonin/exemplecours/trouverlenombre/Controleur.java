package fr.unice.reneviergonin.exemplecours.trouverlenombre;

import java.util.ArrayList;

import commun.Coup;

public class Controleur {

    Connexion connexion;
    Vue vue;

    public Controleur(Vue vue) {
        this.vue = vue;
    }

    public Vue getVue() {
        return vue;
    }
    public void setVue(Vue vue) {
        this.vue = vue;
    }
    public Connexion getConnexion() {
        return connexion;
    }
    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }



    public void aprèsConnexion() {

    }

    public void finPartie() {

    }

    public void jeRejoue() {

    }

    public void rejouer(boolean plusGrand, ArrayList<Coup> coups) {

    }

    public void premierCoup() {

    }
}
