package client;

import client.reseau.ConnexionClient;
import client.vue.Vue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClientTropPetitTest {

    Client client;

    @Mock
    ConnexionClient connexion;

    @Mock
    Vue vue;



    @BeforeEach
    void setUp() {

        client = new Client();
        // vue = spy(new Vue(client)); // si vue n'est pas un mock
        client.setVue(vue);
        client.setConnexion(connexion);


    }

    @Disabled("c'était juste pour montrer...")
    @Test
    void rejouer() {
        client.rejouer(true,null);
    }

    @Test
    void testScenarioTropPetit(){
        final int bonneRéponse = 26;

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {

                Object [] args = invocationOnMock.getArguments();
                int val = (int) args[0];

                if (bonneRéponse != val) {
                    client.rejouer((val > bonneRéponse), null);
                }
                else {
                    client.finPartie();
                }

                return null;
            }
        }).when(connexion).envoyerCoup(anyInt());


        client.setPropositionCourante(10);

        // un ordre pour les messages textuels
        InOrder ordreMsg = inOrder(vue);

        client.premierCoup();

        // envoie du premier nombre
        verify(connexion, times(1)).envoyerCoup(10);

        // rejouer a été appele après 40... 39.. 35... donc 6 fois
        for(int i = 11; i < bonneRéponse; i++) {
            verify(connexion, times(1)).envoyerCoup(i);
            ordreMsg.verify(vue).afficheMessage("la réponse précédente était : trop petite");
            ordreMsg.verify(vue).afficheMessage("on répond "+i);
        }

        // 6 trop grand, 16 on répond et on a gagné
        // @TODO appel de deux fois finPartie... pourquoi ? => déconnecté
        verify(vue, times(32)).afficheMessage(anyString());

        verify(connexion, times(1)).envoyerCoup(bonneRéponse);
        ordreMsg.verify(vue).finit();

        assertEquals(bonneRéponse, client.getPropositionCourante(), "normalement on a trouvé "+bonneRéponse);

    }
}