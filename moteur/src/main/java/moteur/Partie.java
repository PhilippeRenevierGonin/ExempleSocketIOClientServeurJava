package moteur;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import config.CONFIG;

public class Partie {
    SocketIOServer serveur;


    public Partie() {

        // création du serveur (peut-être externalisée)
        Configuration config = new Configuration();
        config.setHostname(CONFIG.IP);
        config.setPort(CONFIG.PORT);
        serveur = new SocketIOServer(config);

        // abonnement aux connexions
        serveur.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("serveur > connexion de "+socketIOClient.getRemoteAddress());
                System.out.println("serveur > connexion de "+socketIOClient);
            }
        });


    }


    public void démarrer() {
        // démarrage du serveur
        serveur.start();
    }





    public static final void main(String  [] args) {
        Partie p = new Partie();
        p.démarrer();
    }
}
