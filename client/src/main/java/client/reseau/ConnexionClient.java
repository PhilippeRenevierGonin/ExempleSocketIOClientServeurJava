package client.reseau;

import client.Client;
import commun.Coup;
import commun.Identification;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class ConnexionClient {

    private final Client controleur;
    Socket connexion;

    public ConnexionClient(String urlServeur, Client ctrl) {
        this.controleur = ctrl;
        controleur.setConnexion(this);

        try {
            connexion = IO.socket(urlServeur);

            this.controleur.transfèreMessage("on s'abonne à la connection / déconnection ");;

            connexion.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    // déplacement du message dans Client/Controleur
                    // on s'identifie
                    controleur.aprèsConnexion();

                }
            });

            connexion.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    controleur.transfèreMessage(" !! on est déconnecté !! ");
                    connexion.disconnect();
                    connexion.close();
                    controleur.finPartie();

                }
            });


            // on recoit une question
            connexion.on("question", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    // message sans intérêt pour le client... // System.out.println("on a reçu une question avec "+objects.length+" paramètre(s) ");
                    if (objects.length > 0 ) {
                        // déplacement du message dans Client/Controleur

                        boolean plusGrand = (Boolean)objects[0];
                        // false, c'est plus petit... !! erreur... dans les commit d'avant

                        // conversion local en ArrayList, juste pour montrer
                        JSONArray tab = (JSONArray) objects[1];
                        ArrayList<Coup> coups = new ArrayList<Coup>();
                        for(int i = 0; i < tab.length(); i++) {

                            try {
                                coups.add(new Coup(tab.getJSONObject(i).getInt("coup"), tab.getJSONObject(i).getBoolean("plusGrand")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.println("@todo >>>> on reçoit bien la liste des coups, mais on en fait rien >>>>> "+coups);
                        controleur.rejouer(plusGrand, coups);


                    } else controleur.premierCoup();
                }
            });



        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void seConnecter() {
        // on se connecte
        connexion.connect();

    }

    public void envoyerId(Identification moi) {
        // conversion automatique obj <-> json
        JSONObject pieceJointe = new JSONObject(moi);
        connexion.emit("identification", pieceJointe);
    }

    public void envoyerCoup(int val) {
        connexion.emit("réponse",val);
    }

    public void stop() {

        connexion.off("connect");
        connexion.off("question");
        connexion.disconnect();
        connexion.close();
    }

    public void finishing() {

        // pour ne pas être sur le thread de SocketIO
        new Thread(new Runnable() {

            @Override
            public void run() {
                connexion.off("disconnect");
                connexion.close();
                System.out.println("@todo >>>> c'est fini");
                // hack pour arrêter plus vite (sinon attente de plusieurs secondes
                System.exit(0);
            }
        }).start() ;



    }


}
