package fr.unice.reneviergonin.exemplecours.trouverlenombre;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import commun.Coup;
import commun.Identification;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connexion  {

    private final Controleur controleur;
    Socket connexion;

    public Connexion(String urlServeur, Controleur ctrl) {
        this.controleur = ctrl;
        controleur.setConnexion(this);

        try {
            connexion = IO.socket(urlServeur);

            System.out.println("on s'abonne à la connection / déconnection ");;

            connexion.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" on est connecté ! et on s'identifie ");

                    // on s'identifie
                    controleur.aprèsConnexion();

                }
            });

            connexion.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" !! on est déconnecté !! ");
                    connexion.disconnect();
                    connexion.close();
                    controleur.finPartie();

                }
            });


            // on recoit une question
            connexion.on("question", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println("on a reçu une question avec "+objects.length+" paramètre(s) ");
                    if (objects.length > 0 ) {
                        System.out.println("la réponse précédente était : "+objects[0]);

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
        Log.e("debug", "essaie de se connecter");
        connexion.connect();

    }

    public void envoyerId(Identification moi) {
        // pas de conversion automatique obj <-> json avec le json de base d'android
        JSONObject pieceJointe = new JSONObject();
        try {
            pieceJointe.put("nom", moi.getNom());
            pieceJointe.put("niveau", moi.getNiveau());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        connexion.emit("identification", pieceJointe);
    }

    public void envoyerCoup(int val) {
        connexion.emit("réponse",val);
    }
}
