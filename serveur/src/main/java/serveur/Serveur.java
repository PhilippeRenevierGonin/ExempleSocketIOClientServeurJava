package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import commun.Coup;
import commun.Identification;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * attend une connexion, on envoie une question puis on attend une réponse, jusqu'à la découverte de la bonne réponse
 * le client s'identifie (som, niveau)
 */
public class Serveur {

    SocketIOServer serveur;
    final Object attenteConnexion = new Object();
    private int àTrouvé = 42;
    Identification leClient ;

    ArrayList<Coup> coups = new ArrayList<>();


    public Serveur(Configuration config) {
        // creation du serveur
        serveur = new SocketIOServer(config);

        // Objet de synchro

        System.out.println("préparation du listener");

        // on accept une connexion
        serveur.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("connexion de "+socketIOClient.getRemoteAddress());

                // on ne s'arrête plus ici
            }
        });

        // réception d'une identification
        serveur.addEventListener("identification", Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest) throws Exception {
                System.out.println("Le client est "+identification.getNom());
                leClient = new Identification(identification.getNom(), identification.getNiveau());

                // on enchaine sur une question
                poserUneQuestion(socketIOClient);
            }
        });


            // on attend une réponse
        serveur.addEventListener("réponse", int.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer integer, AckRequest ackRequest) throws Exception {
                System.out.println("La réponse de  "+leClient.getNom()+" est "+integer);
                Coup coup = new Coup(integer, integer > àTrouvé);
                if (integer == àTrouvé) {
                    System.out.println("le client a trouvé ! ");
                    synchronized (attenteConnexion) {
                        attenteConnexion.notify();
                    }
                } else
                {
                    coups.add(coup);
                    System.out.println("le client doit encore cherché ");
                    poserUneQuestion(socketIOClient, coup.isPlusGrand());
                }

            }
        });



    }


    private void démarrer() {

        serveur.start();

        System.out.println("en attente de connexion... la connexion se fera sur un thread de SocketIO, ce thread dit 'principal' du lancement du programme est en attente de la fin du jeu (exécuté sur le thread de socketIO)");
        
        // il n'y a plus rien à faire sur ce thread, on attend la fin de la partie
        synchronized (attenteConnexion) {
            try {
                // on attend qu'une notification, depuis un thread de socketIO, quand le jeu sera fini
                attenteConnexion.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("erreur dans l'attente");
            }
        }

        System.out.println("Le jeu vient de se terminer, on vient d'être notifier depuis un thread de socketIO, on arrête");
        serveur.stop();

    }


    private void poserUneQuestion(SocketIOClient socketIOClient) {
        socketIOClient.sendEvent("question");
    }

    private void poserUneQuestion(SocketIOClient socketIOClient, boolean plusGrand) {
        socketIOClient.sendEvent("question", plusGrand, coups);
    }



    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);


        Serveur serveur = new Serveur(config);
        serveur.démarrer();


        System.out.println("fin du main");

    }


}
