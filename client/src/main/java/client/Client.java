package client;

import client.reseau.Connexion;
import commun.Coup;
import commun.Identification;


import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Client {

    Identification moi = new Identification("Michel B", 42);

    Connexion connexion;
    int propositionCourante = 50;

    // Objet de synchro
    final Object attenteDéconnexion = new Object();

    public Client() {
    }

    private void seConnecter() {
        // on se connecte
        this.connexion.seConnecter();

        System.out.println("en attente de déconnexion");
        synchronized (attenteDéconnexion) {
            try {
                attenteDéconnexion.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("erreur dans l'attente");
            }
        }
    }



    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }

    public void aprèsConnexion() {
        this.connexion.envoyerId(moi);
    }

    public void finPartie() {
        System.out.println("on a gagné !! ");
        synchronized (attenteDéconnexion) {
            attenteDéconnexion.notify();
        }
    }


    public void rejouer(boolean plusGrand, ArrayList<Coup> coups) {
        int pas = 1;

        if (plusGrand)  pas=-1;
        else pas=+1;

        // on ne fait toujours rien de coups
        // pour l'instant

        propositionCourante += pas;
        System.out.println("on répond "+propositionCourante);
        connexion.envoyerCoup(propositionCourante);
    }

    public void premierCoup() {
        // au premier coup, on envoie le nombre initial
        connexion.envoyerCoup(propositionCourante);
    }





    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Client client = new Client();
        Connexion connexion = new Connexion("http://127.0.0.1:10101", client);
        client.seConnecter();



        System.out.println("fin du main pour le client");

    }
}
