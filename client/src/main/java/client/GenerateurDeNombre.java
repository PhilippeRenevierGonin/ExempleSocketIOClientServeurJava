package client;

import java.util.Random;

public class GenerateurDeNombre {

    private Random alea = new Random();

    public Random getAlea() {
        return alea;
    }

    public void setAlea(Random alea) {
        this.alea = alea;
    }

    /**
     * méthode pour illustrer le mock et when / thenReturn
     * @param min
     * @param max
     * @return
     */
    public int generate(int min, int max) {
        return min+alea.nextInt(max-min);
    }

}
