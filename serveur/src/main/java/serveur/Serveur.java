package serveur;


import com.corundumstudio.socketio.SocketIOClient;

import commun.Coup;
import commun.Identification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reseau.ConnexionServeur;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * attend une connexion, on envoie une question puis on attend une réponse, jusqu'à la découverte de la bonne réponse
 * le client s'identifie (som, niveau)
 */
@SpringBootApplication
public class Serveur {

    private int àTrouvé = 42;
    Identification leClient ;

    ArrayList<Coup> coups = new ArrayList<>();

    ConnexionServeur connexion;

    public ConnexionServeur getConnexion() {
        return connexion;
    }

    public void setConnexion(ConnexionServeur connexion) {
        this.connexion = connexion;
    }

    public Serveur()  {
        this(42);
    }

    public Serveur(int valeurVoulue) {
        this.àTrouvé = valeurVoulue;
    }

    // todo : refactoring de leClient en leJoueur
    public void setLeClient(Identification leClient) {
        this.leClient = leClient;
    }

    public Identification getLeClient() {
        return leClient;
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

        SpringApplication.run(Serveur.class);
    }

    @Bean
    public CommandLineRunner run() throws Exception {
        return args -> {
            String nomHote;
            String adresseIPLocale = "172.17.0.2";

            try {
                InetAddress inetadr = InetAddress.getLocalHost();
                //nom de machine
                nomHote = (String) inetadr.getHostName();
                System.out.println("Nom de la machine = " + nomHote);
                //adresse ip sur le réseau
                adresseIPLocale = (String) inetadr.getHostAddress();
                System.out.println("Adresse IP locale = " + adresseIPLocale);

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            // Serveur serveur = new Serveur();
            // ack de connexion sur l'adresse docker
            ConnexionServeur connexion = new ConnexionServeur(adresseIPLocale, 10101);

            connexion.setMoteur(this);
            setConnexion(connexion);

            démarrer();


        };
    }


    public synchronized boolean nouveauJoueur(SocketIOClient socketIOClient, Identification identification) {
        if (leClient == null)
        {
            System.out.println("Le client est "+identification.getNom());
            leClient = new Identification(identification.getNom(), identification.getNiveau());
            connexion.associer(leClient, socketIOClient);
            // on enchaine sur une question
            new Thread(new Runnable() {
                @Override
                public void run() {
                    poserUneQuestion();
                }
            }).start();
            return true;
        }
        else {
            System.out.println("ce serveur n'est fait que pour un client");
            return false;
        }
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
}
