package lanceur;

import client.Client;
import client.reseau.Connexion;
import client.vue.Vue;
import com.corundumstudio.socketio.Configuration;
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
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);


        Serveur serveur = new Serveur(config);
        serveur.démarrer();

        /*** création du client ***/
        Client client = new Client();
        Vue vue = new Vue(client);
        Connexion connexion = new Connexion("http://127.0.0.1:10101", client);
        client.seConnecter();


    }
}
