package donnees;

import java.util.ArrayList;

public class Main {

    private ArrayList<Carte> cartes ;

    public ArrayList<Carte> getCartes() {
        return cartes;
    }

    public void setCartes(ArrayList<Carte> cartes) {
        this.cartes = cartes;
    }

    public Main() {cartes = new ArrayList<>();}
    public Main(ArrayList<Carte> cartes) {
        this.cartes = cartes;
    }


    public void ajouterCarte(Carte c) {
        getCartes().add(c);
    }


    public String toString()  {
        String texte = "[";

        for(Carte c : cartes) texte += c +" ; ";

        if (texte.length() > 4) texte = texte.substring(0, texte.length()-3);

        texte += "]";
        return texte;
    }
}
