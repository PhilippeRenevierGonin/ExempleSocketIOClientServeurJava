package client;

import client.reseau.ConnexionClient;
import client.vue.Vue;
import commun.Coup;
import commun.Identification;


import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Client {

    Identification moi = new Identification("Michel B", 42);

    ConnexionClient connexion;
    int propositionCourante = 30;

    // Objet de synchro
    private Vue vue;

    public Client() {
    }


    /** un ensemble de getter et setter **/


    public void setConnexion(ConnexionClient connexion) {
        this.connexion = connexion;
    }

    private ConnexionClient getConnexion() {
        return connexion;
    }

    public int getPropositionCourante() {
        return propositionCourante;
    }

    public void setPropositionCourante(int propositionCourante) {
        this.propositionCourante = propositionCourante;
    }

    public Identification getIdentification() {
        return moi;
    }

    public void setVue(Vue vue) {
        this.vue = vue;
    }

    public Vue getVue() {
        return vue;
    }

    public void seConnecter() {
        // on se connecte
        this.connexion.seConnecter();

        getVue().afficheMessage("en attente de déconnexion");

    }







    public void aprèsConnexion() {
        getVue().afficheMessage("on est connecté ! et on s'identifie ");
        this.connexion.envoyerId(moi);
    }

    public void finPartie() {
        getVue().finit();
        // @todo ici les deux sont en même temps car le serveur coupe brutalement
        getConnexion().stop();
        getConnexion().finishing();
    }


    public void rejouer(boolean plusGrand, ArrayList<Coup> coups) {
        getVue().afficheMessage("la réponse précédente était : "+(plusGrand?"trop grande":"trop petite"));

        int pas = 1;

        if (plusGrand)  pas=-1;
        else pas=+1;

        // on ne fait toujours rien de coups
        // pour l'instant

        propositionCourante += pas;
        getVue().afficheMessage("on répond "+propositionCourante);
        getConnexion().envoyerCoup(propositionCourante);
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

        String serveurIp = "127.0.0.1";
        if (args.length > 0) serveurIp = args[0];

        Client client = new Client();
        Vue vue = new Vue(client);
        ConnexionClient connexion = new ConnexionClient("http://"+serveurIp+":10101", client);
        client.seConnecter();



        System.out.println("fin du main pour le client");

    }


    public void transfèreMessage(String s) {
        getVue().afficheMessage(s);
    }
}
