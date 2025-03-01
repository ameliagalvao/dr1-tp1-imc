import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CalculoIMCTest {

    @Test
    public void testEmptyPeso() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CalculoIMC.parsePeso("");
        });
        assertEquals("Peso não pode ser vazio", exception.getMessage());
    }

    @Test
    public void testEmptyAltura() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CalculoIMC.parseAltura("");
        });
        assertEquals("Altura não pode ser vazia", exception.getMessage());
    }

    @Test
    public void testInvalidSeparatorPeso() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CalculoIMC.parsePeso("70,5");
        });
        assertEquals("Separador inválido, use ponto decimal", exception.getMessage());
    }

    @Test
    public void testInvalidSeparatorAltura() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CalculoIMC.parseAltura("1,75");
        });
        assertEquals("Separador inválido, use ponto decimal", exception.getMessage());
    }

    @Test
    public void testPesoOutOfRange() {
        // Testa peso abaixo do mínimo
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CalculoIMC.parsePeso("1.5");
        });
        assertEquals("Peso fora dos limites", exception.getMessage());

        // Testa peso acima do máximo
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            CalculoIMC.parsePeso("400");
        });
        assertEquals("Peso fora dos limites", exception2.getMessage());
    }

    @Test
    public void testAlturaOutOfRange() {
        // Testa altura abaixo do mínimo
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CalculoIMC.parseAltura("0.3");
        });
        assertEquals("Altura fora dos limites", exception.getMessage());

        // Testa altura acima do máximo
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            CalculoIMC.parseAltura("3.0");
        });
        assertEquals("Altura fora dos limites", exception2.getMessage());
    }

    @Test
    public void testCalcularIMC_ValorNormal() {
        // 70 / (1.75 * 1.75) = 22.857142857...
        double imc = CalculoIMC.calcularIMC(70, 1.75);
        assertEquals(22.8571, imc, 0.0001);
    }

    @Test
    public void testCalcularIMC_ValorMenor() {
        // 50 / (1.60 * 1.60) = 19.53
        double imc = CalculoIMC.calcularIMC(50, 1.60);
        assertEquals(19.53, imc, 0.01);
    }

    @Test
    public void testClassificarIMC_MagrezaGrave() {
        assertEquals("Magreza grave", CalculoIMC.classificarIMC(15.9));
    }

    @Test
    public void testClassificarIMC_MagrezaModerada_16() {
        assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.0));
    }

    @Test
    public void testClassificarIMC_MagrezaModerada_16_5() {
        assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.5));
    }

    @Test
    public void testClassificarIMC_MagrezaLeve_17() {
        assertEquals("Magreza leve", CalculoIMC.classificarIMC(17.0));
    }

    @Test
    public void testClassificarIMC_MagrezaLeve_18_0() {
        assertEquals("Magreza leve", CalculoIMC.classificarIMC(18.0));
    }

    @Test
    public void testClassificarIMC_Saudavel_18_5() {
        assertEquals("Saudável", CalculoIMC.classificarIMC(18.5));
    }

    @Test
    public void testClassificarIMC_Saudavel_24_9() {
        assertEquals("Saudável", CalculoIMC.classificarIMC(24.9));
    }

    @Test
    public void testClassificarIMC_Sobrepeso_25() {
        assertEquals("Sobrepeso", CalculoIMC.classificarIMC(25.0));
    }

    @Test
    public void testClassificarIMC_Sobrepeso_29_9() {
        assertEquals("Sobrepeso", CalculoIMC.classificarIMC(29.9));
    }

    @Test
    public void testClassificarIMC_ObesidadeI_30() {
        assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(30.0));
    }

    @Test
    public void testClassificarIMC_ObesidadeI_34_9() {
        assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(34.9));
    }

    @Test
    public void testClassificarIMC_ObesidadeII_35() {
        assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(35.0));
    }

    @Test
    public void testClassificarIMC_ObesidadeII_39_9() {
        assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(39.9));
    }

    @Test
    public void testClassificarIMC_ObesidadeIII_40() {
        assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(40.0));
    }

    @Test
    public void testClassificarIMC_ObesidadeIII_50() {
        assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(50.0));
    }

}