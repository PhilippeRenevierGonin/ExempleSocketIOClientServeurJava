package joueur;

import config.CONFIG;
import config.MESSAGES;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

public class Joueur {


    private String nom;
    Socket connexion ;

    public Joueur(String un_joueur) {
        setNom(un_joueur);

        System.out.println(nom +" > creation");
        try {
            // préparation de la connexion
            connexion = IO.socket("http://" + CONFIG.IP + ":" + CONFIG.PORT);

            // abonnement à la connexion
            connexion.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(getNom() + " > connecte");
                    System.out.println(getNom()+" > envoi de mon nom");
                    connexion.emit(MESSAGES.MON_NOM, getNom());
                }
            });
        } catch (
        URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void démarrer() {
        // connexion effective
        if (connexion != null) connexion.connect();
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }


    public static final void main(String  [] args) {
        Joueur j = new Joueur("toto");
        j.démarrer();
    }
}
