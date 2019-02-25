package donnees;

public class Carte {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Carte() {}
    public Carte(String name) {
        this.name = name;
    }


    public String toString() {
        return "[carte - "+getName()+" -]";
    }

    public boolean equals(Object o) {
        if ((o != null) && (o instanceof Carte)) {
            return getName().equals(((Carte) o).getName());
        }
        else return false;
    }


}
