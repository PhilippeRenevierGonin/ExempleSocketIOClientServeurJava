package serveur;

import commun.Identification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reseau.ConnexionServeur;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ServeurTest {

    Serveur serveurTesté;
    int valeurÀTrouvée;
    Identification joueurTest;

    @Mock
    ConnexionServeur connexion;

    @BeforeEach
    void setUp() {
        valeurÀTrouvée = 24;
        serveurTesté = new Serveur(valeurÀTrouvée);
        serveurTesté.setConnexion(connexion);
        joueurTest = new Identification("joueur pour le test", 99);
        serveurTesté.setLeClient(joueurTest);
    }

    @Test
    void testRéponseTrouvée() {
        serveurTesté.reçoitRéponse(valeurÀTrouvée);
        verify(connexion, times(1)).arrêter();
    }

    @Test
    void testRéponseTropPetite() {
        serveurTesté.reçoitRéponse(valeurÀTrouvée-2);
        //    -> appel de getConnexion().envoyerMessage(leClient, "question", plusGrand, coups);
        verify(connexion, times(1)).envoyerMessage(eq(joueurTest), eq("question"), eq(false), any());
    }

    @Test
    void testRéponseTropGrande() {
        serveurTesté.reçoitRéponse(valeurÀTrouvée+2);
        //    -> appel de getConnexion().envoyerMessage(leClient, "question", plusGrand, coups);
        verify(connexion, times(1)).envoyerMessage(eq(joueurTest), eq("question"), eq(true), any());
    }
}