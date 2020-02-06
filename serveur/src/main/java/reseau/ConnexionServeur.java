package reseau;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import commun.Coup;
import commun.Identification;
import serveur.Serveur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;

public class ConnexionServeur {

    SocketIOServer serveur;
    Serveur moteur;
    private HashMap<Identification, SocketIOClient> map;

    SocketIOServer getServeur() {
        return serveur;
    }

    void setServeur(SocketIOServer serveur) {
        this.serveur = serveur;
    }


    public ConnexionServeur(String ip, int port) {
        map = new HashMap<>();
        Configuration config = new Configuration();
        config.setHostname(ip);
        config.setPort(port);

        setServeur(new SocketIOServer(config));

        getServeur().addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("connexion de "+socketIOClient.getRemoteAddress());
            }
        });


        // réception d'une identification
        serveur.addEventListener("identification", Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest) throws Exception {
                moteur.nouveauJoeur(socketIOClient, identification);
            }
        });


        // on attend une réponse
        serveur.addEventListener("réponse", int.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer integer, AckRequest ackRequest) throws Exception {
                moteur.reçoitRéponse(integer);

            }
        });
    }


    public Serveur getMoteur() {
        return moteur;
    }

    public void setMoteur(Serveur moteur) {
        this.moteur = moteur;
    }

    public void démarrer() {
        getServeur().start();
    }

    public void envoyerMessage(Identification leClient, String question) {
        map.get(leClient).sendEvent(question);
    }

    public void associer(Identification leClient, SocketIOClient socketIOClient) {
        map.put(leClient,socketIOClient);
    }

    public void envoyerMessage(Identification leClient, String question, Object... attachement) {
        map.get(leClient).sendEvent(question, attachement);
    }

    public void arrêter() {
        System.out.println("fin du serveur - début");

        for(SocketIOClient c : map.values()) c.disconnect();


        System.out.println("fin du serveur - désabonnement");

        getServeur().removeAllListeners("réponse");
        getServeur().removeAllListeners("identification");


        System.out.println("fin du serveur - stop");

        new Thread(new Runnable() {
            @Override
            public void run() {
                getServeur().stop(); // à faire sur un autre thread que sur le thread de SocketIO
                System.out.println("fin du serveur - fin");

            }
        }).start();

    }
}