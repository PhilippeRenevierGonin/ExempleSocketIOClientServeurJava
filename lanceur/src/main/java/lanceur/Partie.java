package lanceur;

import client.Client;
import client.reseau.ConnexionClient;
import client.vue.Vue;
import com.corundumstudio.socketio.Configuration;
import reseau.ConnexionServeur;
import serveur.Serveur;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Partie {

    public final static void main(String [] args) {

        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /*** création du serveur ***/
        Serveur serveur = new Serveur();
        ConnexionServeur connexionS = new ConnexionServeur("127.0.0.1", 10101);

        connexionS.setMoteur(serveur);
        serveur.setConnexion(connexionS);
        serveur.démarrer();

        /*** création du client ***/
        Client client = new Client();
        Vue vue = new Vue(client);
        ConnexionClient connexionC = new ConnexionClient("http://127.0.0.1:10101", client);
        client.seConnecter();


    }
}
