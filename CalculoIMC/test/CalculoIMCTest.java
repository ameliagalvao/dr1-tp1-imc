import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CalculoIMCTest {

    // ----------------- Peso ----------

    @Test
    public void testPesoValorValido() {
        assertEquals(70.0, CalculoIMC.parsePeso("70.0"));
    }

    @Test
    public void testPesoValorValidoLimiteInferior() {
        assertEquals(2.0, CalculoIMC.parsePeso("2.0"));
    }

    @Test
    public void testPesoValorValidoLimiteSuperior() {
        assertEquals(300.0, CalculoIMC.parsePeso("300.0"));
    }

    @Test
    public void testPesoValorInvalidoLimiteInferior() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.parsePeso("1.99"));
        assertEquals("Peso fora dos limites", e.getMessage());
    }

    @Test
    public void testPesoValorInvalidoLimiteSuperior() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.parsePeso("300.1"));
        assertEquals("Peso fora dos limites", e.getMessage());
    }

    @Test
    public void testPesoValorVazio() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.parsePeso(""));
        assertEquals("Peso não pode ser vazio", e.getMessage());
    }

    @Test
    public void testPesoSeparadorInvalido() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.parsePeso("70,5"));
        assertEquals("Separador inválido, use ponto decimal", e.getMessage());
    }

    @Test
    public void testPesoValorNaoNumerico() {
        Exception e = assertThrows(NumberFormatException.class, () -> CalculoIMC.parsePeso("abc"));
    }

    // ------------------- Altura -------------

    @Test
    public void testAlturaValorValido() {
        assertEquals(1.75, CalculoIMC.parseAltura("1.75"));
    }

    @Test
    public void testAlturaValorValidoLimiteInferior() {
        assertEquals(0.5, CalculoIMC.parseAltura("0.5"));
    }

    @Test
    public void testAlturaValorValidoLimiteSuperior() {
        assertEquals(2.5, CalculoIMC.parseAltura("2.5"));
    }

    @Test
    public void testAlturaValorInvalidoLimiteInferior() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.parseAltura("0.49"));
        assertEquals("Altura fora dos limites", e.getMessage());
    }

    @Test
    public void testAlturaValorInvalidoLimiteSuperior() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.parseAltura("2.51"));
        assertEquals("Altura fora dos limites", e.getMessage());
    }

    @Test
    public void testAlturaValorVazio() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.parseAltura(""));
        assertEquals("Altura não pode ser vazia", e.getMessage());
    }

    @Test
    public void testAlturaSeparadorInvalido() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> CalculoIMC.parseAltura("1,75"));
        assertEquals("Separador inválido, use ponto decimal", e.getMessage());
    }

    @Test
    public void testAlturaValorNaoNumerico() {
        Exception e = assertThrows(NumberFormatException.class, () -> CalculoIMC.parseAltura("xyz"));
    }

    // ----------------- calcularIMC ----------------

    @Test
    public void testCalcularIMCValorValido() {
        double imc = CalculoIMC.calcularIMC(70, 1.75);
        assertEquals(22.8571, imc, 0.0001);
    }

    @Test
    public void testCalcularIMCValorValidoLimiteInferior() {
        double imc = CalculoIMC.calcularIMC(2.0, 0.5);
        assertEquals(8.0, imc, 0.01);
    }

    @Test
    public void testCalcularIMCValorValidoLimiteSuperior() {
        double imc = CalculoIMC.calcularIMC(300.0, 2.5);
        assertEquals(48.0, imc, 0.01);
    }

    // --------- classificarIMC ------------

    @Test
    public void testClassificarIMCMagrezaGraveLimiteSuperior() {
        assertEquals("Magreza grave", CalculoIMC.classificarIMC(15.9));
    }

    @Test
    public void testClassificarIMCMagrezaModeradaLimiteInferior() {
        assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.0));
    }

    @Test
    public void testClassificarIMCMagrezaModeradaLimiteSuperior() {
        assertEquals("Magreza moderada", CalculoIMC.classificarIMC(16.9));
    }

    @Test
    public void testClassificarIMCMagrezaLeveLimiteInferior() {
        assertEquals("Magreza leve", CalculoIMC.classificarIMC(17.0));
    }

    @Test
    public void testClassificarIMCMagrezaLeveLimiteSuperior() {
        assertEquals("Magreza leve", CalculoIMC.classificarIMC(18.4));
    }

    @Test
    public void testClassificarIMCSaudavelLimiteInferior() {
        assertEquals("Saudável", CalculoIMC.classificarIMC(18.5));
    }

    @Test
    public void testClassificarIMCSaudavelLimiteSuperior() {
        assertEquals("Saudável", CalculoIMC.classificarIMC(24.9));
    }

    @Test
    public void testClassificarIMCSobrepesoLimiteInferior() {
        assertEquals("Sobrepeso", CalculoIMC.classificarIMC(25.0));
    }

    @Test
    public void testClassificarIMCSobrepesoLimiteSuperior() {
        assertEquals("Sobrepeso", CalculoIMC.classificarIMC(29.9));
    }

    @Test
    public void testClassificarIMCObesidadeILimiteInferior() {
        assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(30.0));
    }

    @Test
    public void testClassificarIMCObesidadeILimiteSuperior() {
        assertEquals("Obesidade Grau I", CalculoIMC.classificarIMC(34.9));
    }

    @Test
    public void testClassificarIMCObesidadeIILimiteInferior() {
        assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(35.0));
    }

    @Test
    public void testClassificarIMCObesidadeIILimiteSuperior() {
        assertEquals("Obesidade Grau II", CalculoIMC.classificarIMC(39.9));
    }

    @Test
    public void testClassificarIMCObesidadeIIILimiteInferior() {
        assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(40.0));
    }

    @Test
    public void testClassificarIMCObesidadeIIILimiteSuperior() {
        assertEquals("Obesidade Grau III", CalculoIMC.classificarIMC(50.0));
    }
}