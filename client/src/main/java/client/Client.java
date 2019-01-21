package client;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class Client {

    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // Objet de synchro
        final Object attenteDéconnexion = new Object();

        try {
            Socket mSocket = IO.socket("http://127.0.0.1:10101");

            System.out.println("on s'abonne à la connection / déconnection ");;

            mSocket.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" on est connecté ! ");
                }
            });

            mSocket.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" !! on est déconnecté !! ");
                    mSocket.disconnect();
                    mSocket.close();

                    synchronized (attenteDéconnexion) {
                        attenteDéconnexion.notify();
                    }
                }
            });

            // on se connecte
            mSocket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        System.out.println("en attente de déconnexion");
        synchronized (attenteDéconnexion) {
            try {
                attenteDéconnexion.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("erreur dans l'attente");
            }
        }
        System.out.println("fin du main pour le clien");

    }

}
