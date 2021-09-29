package client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateurDeNombreTest {

    @Mock
    Random rand;

    GenerateurDeNombre generateurTesté;

    @BeforeEach
    void setUp() {
        generateurTesté = new GenerateurDeNombre();
        generateurTesté.setAlea(rand);
    }

    @Test
    void testGenerate() {
        when(rand.nextInt(anyInt())).thenReturn(5);
        assertEquals(10, generateurTesté.generate(5,15));
        assertEquals(20, generateurTesté.generate(15,15));
    }

}