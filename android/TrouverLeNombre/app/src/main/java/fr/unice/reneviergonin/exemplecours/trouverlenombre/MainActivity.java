package fr.unice.reneviergonin.exemplecours.trouverlenombre;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import commun.Coup;
import commun.Identification;

public class MainActivity extends Activity implements Vue {

    Button btnJouer;
    TextView msg;
    EditText valeur;

    Controleur ctrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // récupération des widget
        btnJouer= findViewById(R.id.button);
        msg = findViewById(R.id.textView);
        valeur = findViewById(R.id.editText);

        // création de la logique de l'application
        ctrl = new Controleur(this);
        ajouteÉcouteur();

        // connexion : grandement récupérer de client (javastd)
        // juste un découpage par rapport à javastd : on "refactore"
        // adresse pour parler depuis l'émulateur à la machien hote : 10.0.2.2
        Connexion connexion = new Connexion("http://10.0.2.2:10101", ctrl);

        // ici pas d'IA mais un joueur humain


        // on pourrait le faire à partir d'un bouton...
        connexion.seConnecter();

    }


    @Override
    public void afficheMessage(final String texte) {

        // appelé depuis le thread de socketIO
        msg.post(new Runnable() {
            @Override
            public void run() {
                msg.setText(texte);
            }
        });

    }


    // adapteur entre le controleur et la vue, pour que le controleur ne dépendent pas d'android
    private void ajouteÉcouteur() {
        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.jeRejoue();
            }
        });
    }

    @Override
    public int valeurJouée() {
        String saisie = valeur.getText().toString();
        int val = -1;
        Log.e("debug", "saisie = "+saisie);
        if ((saisie != null) && (! saisie.equals(""))) {
            val = Integer.parseInt(saisie);
        }
        return val;
    }

    @Override
    public void finit() {
        // appelé depuis le thread de socketIO
        btnJouer.post(new Runnable() {
            @Override
            public void run() {
                btnJouer.setEnabled(false);
                valeur.setEnabled(false);
            }
        });
    }
}
