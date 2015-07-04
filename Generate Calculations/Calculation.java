package android;

/* 
 * @Author: Lucas Myllenno S M Lima.
 * @Date: 04/07/2015
 * 
 */

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

public class Calculation {

    public String equationString = ""; // Formatação do cálculo na tela.
    public int[] optionsChoose = null; // Opções de escolha.
    public int responseSolution = -1; // Resposta esperada.
    public int resultCalculate = 0; // Resultado do cálculo.

    //- Retorna um array com 3 números aleatórios (entre 0 e 99).
    private int[] generationNumbers(){
        int[] numbers = new int[]{randomNumber_1(100), randomNumber_1(100), randomNumber_1(100)};
        return numbers;
    }

    //- Retorna um array com 2 operadores aritméticos.
    private char[] generationArithmetics(){
        char[] arithmetic = new char[]{'+','-'};
        int op1 = randomNumber_1(2);
        int op2 = randomNumber_1(2);
        char[] generation = new char[]{arithmetic[op1], arithmetic[op2]};
        return generation;
    }

    //- Retorna o resultado do cálculo.
    private int returnResult(String[] numbers, char[] arithmetics){
        int result = Integer.parseInt(numbers[0]);
        result = arithmetics[0] == '+' ? result + Integer.parseInt(numbers[1]) : result - Integer.parseInt(numbers[1]);
        result = arithmetics[1] == '+' ? result + Integer.parseInt(numbers[2]) : result - Integer.parseInt(numbers[2]);
        return result;
    }

    //- Condições para as opções de escolha.
    private int[] conditionsGenerationOptionsChoose(int[] optionsChoose, int type){
        for (int i=0; i < optionsChoose.length; i++){
            // Para os tipos de jogo [1,2,3] as opções devem ter apenas 1 dígito.
            if ((type == 1 || type == 2 || type == 3) && (optionsChoose[i] > 9))
                optionsChoose[i] -= 7;
            // As opções de escolha não podem ser negativas, exceto no tipo de jogo [5].
            if ((type != 5) && (optionsChoose[i] < 0))
                optionsChoose[i] += 7;
        }
        return optionsChoose;
    }

    //- Gera as opções de escolha do cálculo gerado.
    //- O método sorteia um valor aleatório entre result-6 e result e o inclui na primeira opção.
    //- As próximas posições serão os números posteriores do número sorteado.
    private int[] generationOptionsChoose(int result, int type){
        int bottom = result - 6;
        int random = randomNumber_2(result, bottom);
        int[] options = new int[6];
        for (int i=0; i < options.length; i++) {
            options[i] = random;
            random++;
        }
        // Faz a checagem das exigências das opções de escolha.
        options = conditionsGenerationOptionsChoose(options, type);
        // Ordena o array após a checagem das exigências.
        Arrays.sort(options);
        return options;
    }

    //- Retorna um número aleatório entre 0 e o limite.
    private int randomNumber_1(int limit){
        Random random = new Random();
        int generation = random.nextInt(limit);
        return generation;
    }

    //- Retorna um número aleatório entre os limites top e bottom.
    private int randomNumber_2(int top, int bottom){
        Random random = new Random();
        int generation = random.nextInt(top - bottom + 1) + bottom;
        return generation;
    }

    //- Retorna 2 posições aleatórias para esconder no cálculo.
    //- Para os tipos de jogo: 1, 2 e 3.
    private int[] generationPositionSelection1(int[] numbers){
        int random1 = randomNumber_1(3);
        int random2 = randomNumber_1(3);
        // Impede a repetição das posições.
        if (random1 == random2)
            if (randomNumber_1(2) == 0)
            	random2 = random1 == 2 ? 0 : random2+1;
            else
                random2 = random1 == 0 ? 2 : random2-1;
        int[] generation = new int[]{random1, random2};
        return generation;
    }

    //- Retorna 1 posição aleatória para esconder no cálculo.
    //- Para o tipo de jogo: 4.
    private int generationPositionSelection2(int[] numbers){
        int random = randomNumber_1(numbers.length);
        return random;
    }

    //- Formatação de números.
    private String numberFormat(String numberStr){
    	int number = Integer.parseInt(numberStr);
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("00");
        String format = decimalFormat.format(number);
        return format;
    }

    //- Para os tipos de jogo: 1 e 2.
    //- Impede que mais de uma opção chegue ao mesmo resultado.
    //- Iguala os operadores impedindo que os números se anulem.
    private char[] processArithmetics(int[] positions, char[] arithmetics){
    	if (positions[0] == 0 && positions[1] == 1 || positions[0] == 1 && positions[1] == 0){
    		arithmetics[0] = '+';
    	} else if (positions[0] == 0 && positions[1] == 2 || positions[0] == 2 && positions[1] == 0){
    		arithmetics[1] = '+';
    	} else if (positions[0] == 1 && positions[1] == 2 || positions[0] == 2 && positions[1] == 1){
    		int random = randomNumber_1(2);
    		if (random == 0)
    			arithmetics[0] = arithmetics[1];
    		else
    			arithmetics[1] = arithmetics[0];
    	}
    	return arithmetics;
    }

    //- Faz o procedimento para igualar os dígitos que serão descobertos.
    //- Realiza o cálculo.
    //- Esconde os números sorteados para descoberta do usuário.
    private void processCalculate(int[] numbers, char[] arithmetics, int type){
        // Guarda todos os números no array de strings.
        String[] numberStr = new String[3];
        for (int i=0; i < numbers.length; i++)
        	numberStr[i] = numbers[i] + "";

        // Equação com o dígito da direita escondido.
        if (type == 1){
        	// Gera 2 posição aleatórias entre os 3 números.
            	int[] positions = generationPositionSelection1(numbers);
        	// Corrige a equação sorteando um dos dígitos dos números escolhidos.
        	int random = randomNumber_1(2);
        	String responseSolution = random == 0 ? numberFormat(numberStr[positions[0]]).charAt(1) + "" : numberFormat(numberStr[positions[1]]).charAt(1) + "";
        	numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? numberStr[positions[0]].charAt(0) + responseSolution : responseSolution;
        	numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? numberStr[positions[1]].charAt(0) + responseSolution : responseSolution;
        	arithmetics = processArithmetics(positions, arithmetics);
        	// Calcula o resultado.
        	this.resultCalculate = returnResult(numberStr, arithmetics);
        	this.responseSolution = Integer.parseInt(responseSolution + "");
        	// Esconde os dígitos.
        	numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? numberStr[positions[0]].charAt(0) + "?" : "?";
        	numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? numberStr[positions[1]].charAt(0) + "?" : "?";
            	// Gera as opções de escolha.
            	this.optionsChoose = generationOptionsChoose(this.responseSolution, type);
        }

        // Equação com o dígito da esquerda escondido.
        else if (type == 2){
        	// Gera 2 posição aleatórias entre os 3 números.
            	int[] positions = generationPositionSelection1(numbers);
        	// Corrige a equação sorteando um dos dígitos dos números escolhidos.
        	int random = randomNumber_1(2);
        	String responseSolution = random == 0 ? numberStr[positions[0]].charAt(0) + "" : numberStr[positions[1]].charAt(0) + "";
        	numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? responseSolution + numberStr[positions[0]].charAt(1) : responseSolution;
        	numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? responseSolution + numberStr[positions[1]].charAt(1) : responseSolution;
        	arithmetics = processArithmetics(positions, arithmetics);
        	// Calcula o resultado.
        	this.resultCalculate = returnResult(numberStr, arithmetics);
        	this.responseSolution = Integer.parseInt(responseSolution + "");
        	// Esconde os dígitos.
        	numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? "?" + numberStr[positions[0]].charAt(1) : "?";
        	numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? "?" + numberStr[positions[1]].charAt(1) : "?";
            	// Gera as opções de escolha.
            	this.optionsChoose = generationOptionsChoose(this.responseSolution, type);
        }

        // Equação com primeiro e segundo dígito escondido.
        else if (type == 3){
        	// Gera 2 posição aleatórias entre os 3 números.
            	int[] positions = generationPositionSelection1(numbers);
            	// Sequência dos números sorteados.
            	int random = randomNumber_1(2); // Sorteia número de 'positions'.
            	String digRandom = numberStr[positions[random]];
            	int random2 = randomNumber_1(digRandom.length()); // Sorteia dígito do número.
            	String responseSolution = digRandom.charAt(random2) + "";
            	// Corrige a equação.
            	int random3 = randomNumber_1(2); // Sorteia o primeiro a receber o digito da direita.
            	if (random3 == 0){
            		numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? numberStr[positions[0]].charAt(0) + responseSolution : responseSolution;
            		numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? responseSolution + numberStr[positions[1]].charAt(1) : responseSolution;
            	} else {
            		numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? responseSolution + numberStr[positions[0]].charAt(1) : responseSolution;
            		numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? numberStr[positions[1]].charAt(0) + responseSolution : responseSolution;
            	}
        	// Calcula o resultado.
            	this.resultCalculate = returnResult(numberStr, arithmetics);
            	this.responseSolution = Integer.parseInt(responseSolution + "");
            	// Esconde os dígitos.
            	if (random3 == 0){
            		numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? numberStr[positions[0]].charAt(0) + "?" : "?";
            		numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? "?" + numberStr[positions[1]].charAt(1) : "?";
            	} else {
            		numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? "?" + numberStr[positions[0]].charAt(1) : "?";
            		numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? numberStr[positions[1]].charAt(0) + "?" : "?";
            	}
        	// Gera as opções de escolha.
            	this.optionsChoose = generationOptionsChoose(this.responseSolution, type);
        }

        // Equação com um número escondido.
        else if (type == 4){
        	// Sorteia a posição do número.
        	int positionHide = generationPositionSelection2(numbers);
        	// Calcula o resultado.
        	this.resultCalculate = returnResult(numberStr, arithmetics);
            	this.responseSolution = numbers[positionHide];
            	// Esconde o número.
            	numberStr[positionHide] = "??";
            	// Gera as opções de escolha.
            	this.optionsChoose = generationOptionsChoose(this.responseSolution, type);
        }

        // Equação com o resultado escondido.
        else if (type == 5){
        	this.resultCalculate = returnResult(numberStr, arithmetics);
            	this.responseSolution = this.resultCalculate;
            	// Gera as opções de escolha.
            	this.optionsChoose = generationOptionsChoose(this.resultCalculate, type);
        }
        
        // String para imprimir na tela..
        String string = "";
        for (int i = 0; i < numbers.length; i++)
        	string += i == 0 ? numberStr[0] : " "+arithmetics[i-1]+" "+numberStr[i];
        string += type != 5 ? " = " + this.resultCalculate : " = " + "??";
        this.equationString = string;
    }

    //- Inicia a geração do cálculo do jogo.
    public void startGame(int type){
        // Sorteia os números.
        int[] numbers = generationNumbers();
        // Sorteia os operadores aritméticos.
        char[] arithmetics = generationArithmetics();
        // Processa o cálculo a partir do tipo de jogo.
        processCalculate(numbers, arithmetics, type);
    }

	public static void main(String args[]){
		
		Calculation calculation = new Calculation();
		
		for (int i = 1; i <= 5; i++){
			calculation.startGame(i);
			System.out.println("Calculation Type " + i +": " + calculation.equationString);
			System.out.println("Expected Value: " + calculation.responseSolution);
			System.out.println("Choice Options: " + Arrays.toString(calculation.optionsChoose));
			System.out.println();
		}
	}
}
