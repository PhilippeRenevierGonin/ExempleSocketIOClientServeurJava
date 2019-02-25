package moteur;

import com.corundumstudio.socketio.SocketIOClient;


public class Participant {

    private SocketIOClient socket;
    private String nom;


    public Participant(SocketIOClient socketIOClient) {
        setSocket(socketIOClient);
    }

    public void setSocket(SocketIOClient socket) {
        this.socket = socket;
    }

    public SocketIOClient getSocket() {
        return socket;
    }



    public String toString() {
        return "[Joueur  : "+getSocket().getRemoteAddress()+"]";
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
