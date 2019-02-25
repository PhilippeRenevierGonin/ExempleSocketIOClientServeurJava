package donnees;

public class Merveille {
    private String nom ;
    private String ressource;

    public String getRessource() {
        return ressource;
    }

    public void setRessource(String ressource) {
        this.ressource = ressource;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Merveille() { setRessource("-vide-");}
    public Merveille(String n) { this(); setNom(n);}


    public String toString() {
        return "Merveille "+getNom();
    }
}
