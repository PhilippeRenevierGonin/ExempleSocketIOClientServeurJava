package client;

import client.reseau.ConnexionClient;
import client.vue.Vue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientStepdef {

    private Client client;

    // mock
    private ConnexionClient connexion;
    private Vue vue;
    GenerateurDeNombre alea;

    public ClientStepdef() {
        connexion = mock(ConnexionClient.class);
        vue = mock(Vue.class);
        alea = mock(GenerateurDeNombre.class);
        when(alea.generate(anyInt(), anyInt())).thenReturn(3);
    }


    @Given("A player and his first proposition is {int}")
    public void givenAPlayer(int prop){
        client = new Client(alea);
        client.setConnexion(connexion);
        client.setVue(vue);
        when(alea.generate(anyInt(), anyInt())).thenReturn(prop);
        client.premierCoup();
    }

    @When("that's not enough and he replays")
    public void thePlayerReplays () {
        client.rejouer(false, null);
    }

    @Then("he plays {int}")
    public void thenNbRentals(int newProp) {
        assertEquals(newProp, client.propositionCourante);
    }
}
