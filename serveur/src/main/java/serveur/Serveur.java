package serveur;


import com.corundumstudio.socketio.SocketIOClient;

import commun.Coup;
import commun.Identification;
import reseau.ConnexionServeur;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * attend une connexion, on envoie une question puis on attend une réponse, jusqu'à la découverte de la bonne réponse
 * le client s'identifie (som, niveau)
 */
public class Serveur {

    private int àTrouvé = 42;
    Identification leClient ;

    ArrayList<Coup> coups = new ArrayList<>();

    ConnexionServeur connexion;

    public Serveur(int valeurVoulue) {
        this.àTrouvé = valeurVoulue;
    }

    public ConnexionServeur getConnexion() {
        return connexion;
    }

    public void setConnexion(ConnexionServeur connexion) {
        this.connexion = connexion;
    }

    public Serveur()  {
        this(42);
    }


    public void démarrer() {

        connexion.démarrer();


    }


    private void poserUneQuestion() {
        getConnexion().envoyerMessage(leClient, "question");
    }

    private void poserUneQuestion(boolean plusGrand) {
        getConnexion().envoyerMessage(leClient, "question", plusGrand, coups);
    }



    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Serveur serveur = new Serveur();
        ConnexionServeur connexion = new ConnexionServeur("127.0.0.1", 10101);

        connexion.setMoteur(serveur);
        serveur.setConnexion(connexion);

        serveur.démarrer();



    }


    public void nouveauJoeur(SocketIOClient socketIOClient, Identification identification) {
        System.out.println("Le client est "+identification.getNom());
        setLeClient(new Identification(identification.getNom(), identification.getNiveau()));
        connexion.associer(leClient, socketIOClient);
        // on enchaine sur une question
        poserUneQuestion();
    }

    public void reçoitRéponse(Integer integer) {
        System.out.println("La réponse de  "+leClient.getNom()+" est "+integer);
        Coup coup = new Coup(integer, integer > àTrouvé);
        if (integer == àTrouvé) {
            System.out.println("le client a trouvé ! ");
            // fin brutale
            getConnexion().arrêter();
        } else
        {
            coups.add(coup);
            System.out.println("le client doit encore cherché ");
            poserUneQuestion(coup.isPlusGrand());
        }
    }


    // todo : refactoring de leClient en leJoueur
    public void setLeClient(Identification leClient) {
        this.leClient = leClient;
    }

    public Identification getLeClient() {
        return leClient;
    }
}
