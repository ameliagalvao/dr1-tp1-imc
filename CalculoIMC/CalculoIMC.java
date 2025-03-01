/*
The MIT License (MIT)

Copyright (c) 2016 Wolfgang Almeida <wolfgang.almeida@yahoo.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import java.util.Scanner;

class CalculoIMC {

	// Limites para validação
	public static final double MIN_PESO = 2.0;
	public static final double MAX_PESO = 300.0;
	public static final double MIN_ALTURA = 0.5;
	public static final double MAX_ALTURA = 2.5;

	public static String classificarIMC(double imc) {
		if (imc < 16.0) {
			return "Magreza grave";
		}
		else if (imc == 16.0 || imc < 17.0) {
			return "Magreza moderada";
		}
		else if (imc == 17.0 || imc < 18.5) {
			return "Magreza leve";
		}
		else if (imc == 18.5 || imc < 25.0) {
			return "Saudável";
		}
		else if (imc == 25.0 || imc < 30.0) {
			return "Sobrepeso";
		}
		else if (imc == 30.0 || imc < 35.0) {
			return "Obesidade Grau I";
		}
		else if (imc == 35.0 || imc < 40.0) {
			return "Obesidade Grau II";
		}
		else {
			return "Obesidade Grau III";
		}
	}
	
	public static double calcularIMC(double peso, double altura) {
		return peso / (altura * altura);
	}

	//Validar e converter o peso
	public static double parsePeso(String input) {
		if (input == null || input.trim().isEmpty()) {
			throw new IllegalArgumentException("Peso não pode ser vazio");
		}
		if (input.contains(",")) {
			throw new IllegalArgumentException("Separador inválido, use ponto decimal");
		}
		double peso = Double.parseDouble(input);
		if (peso < MIN_PESO || peso > MAX_PESO) {
			throw new IllegalArgumentException("Peso fora dos limites");
		}
		return peso;
	}

	//Validar e converter a altura
	public static double parseAltura(String input) {
		if (input == null || input.trim().isEmpty()) {
			throw new IllegalArgumentException("Altura não pode ser vazia");
		}
		if (input.contains(",")) {
			throw new IllegalArgumentException("Separador inválido, use ponto decimal");
		}
		double altura = Double.parseDouble(input);
		if (altura < MIN_ALTURA || altura > MAX_ALTURA) {
			throw new IllegalArgumentException("Altura fora dos limites");
		}
		return altura;
	}


	public static void programaIMC(String versao) {
		double peso = 0.0;
		double altura = 0.0;
		Scanner scanner = new Scanner(System.in);

		System.out.println("===========================");
		System.out.printf("Cálculo do IMC - Versão %s\n", versao);
		System.out.println("===========================");

		//Loop para ler o peso até que uma entrada válida seja informada
		while (true) {
			System.out.print("Insira o seu peso em quilogramas: ");
			String inputPeso = scanner.nextLine();
			try {
				peso = parsePeso(inputPeso);
				break;
			} catch (IllegalArgumentException e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}

		//Loop para ler a altura até que uma entrada válida seja informada
		while (true) {
			System.out.print("Insira a sua altura em metros: ");
			String inputAltura = scanner.nextLine();
			try {
				altura = parseAltura(inputAltura);
				break;
			} catch (IllegalArgumentException e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}

		double imc = calcularIMC(peso, altura);
		System.out.printf("\nSeu índice de massa corporal é: %.2f kg/m²\n", imc);
		System.out.printf("Classificação: %s.\n", classificarIMC(imc));
	}

	public static void main(String[] args) {
		String versao = "1.0";
		programaIMC(versao);
	}
}