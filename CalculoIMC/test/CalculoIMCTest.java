import net.jqwik.api.*;
import net.jqwik.api.constraints.DoubleRange;
import net.jqwik.api.constraints.Positive;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class CalculoIMCTest {

    //Método para verificar se o cálculo do IMC está correto
    private void assertCalculoIMCCorreto(double peso, double altura) {
        double imc = CalculoIMC.calcularIMC(peso, altura);
        double esperado = peso / (altura * altura);
        // Verifica se o cálculo segue a fórmula com precisão
        assertEquals(esperado, imc, 0.0001, "O cálculo do IMC deve seguir a fórmula: peso / (altura * altura)");
        // Garante que o IMC seja um valor positivo
        assertTrue(imc > 0, "IMC deve ser um valor positivo");
    }

    @Property
    public void testImcNuncaNegativo(
            @ForAll @Positive double peso,
            @ForAll @Positive double altura
    ) {
        double imc = CalculoIMC.calcularIMC(peso, altura);
        assertThat(imc).isGreaterThanOrEqualTo(0);
    }

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

    // Geradores personalizados para testar valores extremos/improváveis

    @Provide
    Arbitrary<Double> pesosExtremos() {
        Arbitrary<Double> pesosValidos = Arbitraries.doubles().between(2.0, 300.0);
        Arbitrary<Double> pesosImprovaveis = Arbitraries.of(400.0);
        return Arbitraries.oneOf(pesosValidos, pesosImprovaveis);
    }

    @Provide
    Arbitrary<Double> alturasExtremas() {
        Arbitrary<Double> alturasValidas = Arbitraries.doubles().between(0.5, 2.5);
        Arbitrary<Double> alturasImprovaveis = Arbitraries.of(0.1);
        return Arbitraries.oneOf(alturasValidas, alturasImprovaveis);
    }

    @Property
    public void imcCalculadoCorretamenteComValoresNormais(
            @ForAll @DoubleRange(min = 2.0, max = 300.0) double peso,
            @ForAll @DoubleRange(min = 0.5, max = 2.5) double altura
    ) {
        assertCalculoIMCCorreto(peso, altura);
    }

    @Property
    public void imcCalculadoCorretamenteComValoresExtremos(
            @ForAll("pesosExtremos") double peso,
            @ForAll("alturasExtremas") double altura
    ) {
        assertCalculoIMCCorreto(peso, altura);
    }

    @Property
    public void classificacaoDoIMC(
            @ForAll @DoubleRange(min = 2.0, max = 300.0) double peso,
            @ForAll @DoubleRange(min = 0.5, max = 2.5) double altura
    ) {
        double imc = CalculoIMC.calcularIMC(peso, altura);
        String classificacao = CalculoIMC.classificarIMC(imc);

        // Verifica a classificação de acordo com os intervalos do IMC
        if(imc < 16.0) {
            assertEquals("Magreza grave", classificacao, "Para IMC < 16.0, a classificação deve ser 'Magreza grave'");
        } else if(imc < 17.0) {
            assertEquals("Magreza moderada", classificacao, "Para IMC entre 16.0 e 17.0, a classificação deve ser 'Magreza moderada'");
        } else if(imc < 18.5) {
            assertEquals("Magreza leve", classificacao, "Para IMC entre 17.0 e 18.5, a classificação deve ser 'Magreza leve'");
        } else if(imc < 25.0) {
            assertEquals("Saudável", classificacao, "Para IMC entre 18.5 e 25.0, a classificação deve ser 'Saudável'");
        } else if(imc < 30.0) {
            assertEquals("Sobrepeso", classificacao, "Para IMC entre 25.0 e 30.0, a classificação deve ser 'Sobrepeso'");
        } else if(imc < 35.0) {
            assertEquals("Obesidade Grau I", classificacao, "Para IMC entre 30.0 e 35.0, a classificação deve ser 'Obesidade Grau I'");
        } else if(imc < 40.0) {
            assertEquals("Obesidade Grau II", classificacao, "Para IMC entre 35.0 e 40.0, a classificação deve ser 'Obesidade Grau II'");
        } else {
            assertEquals("Obesidade Grau III", classificacao, "Para IMC >= 40.0, a classificação deve ser 'Obesidade Grau III'");
        }
    }

}