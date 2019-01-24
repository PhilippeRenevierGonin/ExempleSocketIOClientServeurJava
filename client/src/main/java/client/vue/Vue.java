package client.vue;

import client.Client;

public class Vue {

    private final Client client;

    public Vue(Client client) {
        this.client = client;
        client.setVue(this);
    }


    public void afficheMessage(String msg) {
        System.out.println(client.getIdentification().getNom()+"> "+msg);

    }


    public void finit() {
        System.out.println(client.getIdentification().getNom()+"> j'ai gagné !");
    }

    public void afficheMessageErreur(String s) {
        System.err.println(client.getIdentification().getNom()+"> "+s);
    }
}
