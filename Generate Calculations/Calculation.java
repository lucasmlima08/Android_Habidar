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

    public String equationString = ""; // Formata��o do c�lculo na tela.
    public int[] optionsChoose = null; // Op��es de escolha.
    public int responseSolution = -1; // Resposta esperada.
    public int resultCalculate = 0; // Resultado do c�lculo.

    //- Retorna um array com 3 n�meros aleat�rios (entre 0 e 99).
    private int[] generationNumbers(){
        int[] numbers = new int[]{randomNumber_1(100), randomNumber_1(100), randomNumber_1(100)};
        return numbers;
    }

    //- Retorna um array com 2 operadores aritm�ticos.
    private char[] generationArithmetics(){
        char[] arithmetic = new char[]{'+','-'};
        int op1 = randomNumber_1(2);
        int op2 = randomNumber_1(2);
        char[] generation = new char[]{arithmetic[op1], arithmetic[op2]};
        return generation;
    }

    //- Retorna o resultado do c�lculo.
    private int returnResult(String[] numbers, char[] arithmetics){
        int result = Integer.parseInt(numbers[0]);
        result = arithmetics[0] == '+' ? result + Integer.parseInt(numbers[1]) : result - Integer.parseInt(numbers[1]);
        result = arithmetics[1] == '+' ? result + Integer.parseInt(numbers[2]) : result - Integer.parseInt(numbers[2]);
        //System.out.println("Calculo: "+Arrays.toString(numbers)+" Resultado: "+result);
        return result;
    }

    //- Condi��es para as op��es de escolha.
    private int[] conditionsGenerationOptionsChoose(int[] optionsChoose, int type){
        for (int i=0; i < optionsChoose.length; i++){
            // Para os tipos de jogo [1,2,3] as op��es devem ter apenas 1 d�gito.
            if ((type == 1 || type == 2 || type == 3) && (optionsChoose[i] > 9)){
                optionsChoose[i] -= 7;
            }
            // As op��es de escolha n�o podem ser negativas, exceto no tipo de jogo [5].
            if ((type != 5) && (optionsChoose[i] < 0)){
                optionsChoose[i] += 7;
            }
        }
        return optionsChoose;
    }

    //- Gera as op��es de escolha do c�lculo gerado.
    //- O m�todo sorteia um valor aleat�rio entre result-6 e result e o inclui na primeira op��o.
    //- As pr�ximas posi��es ser�o os n�meros posteriores do n�mero sorteado.
    private int[] generationOptionsChoose(int result, int type){
        int bottom = result - 6;
        int random = randomNumber_2(result, bottom);
        int[] options = new int[6];
        for (int i=0; i < options.length; i++) {
            options[i] = random;
            random++;
        }
        // Faz a checagem das exig�ncias das op��es de escolha.
        options = conditionsGenerationOptionsChoose(options, type);
        // Ordena o array ap�s a checagem das exig�ncias.
        Arrays.sort(options);
        return options;
    }

    //- Retorna um n�mero aleat�rio entre 0 e o limite.
    private int randomNumber_1(int limit){
        Random random = new Random();
        int generation = random.nextInt(limit);
        return generation;
    }

    //- Retorna um n�mero aleat�rio entre os limites top e bottom.
    private int randomNumber_2(int top, int bottom){
        Random random = new Random();
        int generation = random.nextInt(top - bottom + 1) + bottom;
        return generation;
    }

    //- Retorna 2 posi��es aleat�rias para esconder no c�lculo.
    //- Para os tipos de jogo: 1, 2 e 3.
    private int[] generationPositionSelection1(int[] numbers){
        int random1 = randomNumber_1(3);
        int random2 = randomNumber_1(3);
        // Impede a repeti��o das posi��es.
        if (random1 == random2)
            if (randomNumber_1(2) == 0)
                random2 = random1 == 2 ? 0 : random2+1;
            else
                random2 = random1 == 0 ? 2 : random2-1;
        int[] generation = new int[]{random1, random2};
        return generation;
    }

    //- Retorna 1 posi��o aleat�ria para esconder no c�lculo.
    //- Para o tipo de jogo: 4.
    private int generationPositionSelection2(int[] numbers){
        int random = randomNumber_1(numbers.length);
        return random;
    }

    //- Formata��o de n�meros.
    private String numberFormat(String numberStr){
    	int number = Integer.parseInt(numberStr);
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("00");
        String format = decimalFormat.format(number);
        return format;
    }

    //- Para os tipos de jogo: 1 e 2.
    //- Impede que mais de uma op��o chegue ao mesmo resultado.
    //- Iguala os operadores impedindo que os n�meros se anulem.
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

    //- Faz o procedimento para igualar os d�gitos que ser�o descobertos.
    //- Realiza o c�lculo.
    //- Esconde os n�meros sorteados para descoberta do usu�rio.
    private void processCalculate(int[] numbers, char[] arithmetics, int type){
        // Guarda todos os n�meros no array de strings.
        String[] numberStr = new String[3];
        for (int i=0; i < numbers.length; i++)
        	numberStr[i] = numbers[i] + "";

        // Equa��o com o d�gito da direita escondido.
        if (type == 1){
        	// Gera 2 posi��o aleat�rias entre os 3 n�meros.
            int[] positions = generationPositionSelection1(numbers);
        	// Corrige a equa��o sorteando um dos d�gitos dos n�meros escolhidos.
        	int random = randomNumber_1(2);
        	String responseSolution = random == 0 ? numberFormat(numberStr[positions[0]]).charAt(1) + "" : numberFormat(numberStr[positions[1]]).charAt(1) + "";
        	numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? numberStr[positions[0]].charAt(0) + responseSolution : responseSolution;
        	numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? numberStr[positions[1]].charAt(0) + responseSolution : responseSolution;
        	arithmetics = processArithmetics(positions, arithmetics);
        	// Calcula o resultado.
        	this.resultCalculate = returnResult(numberStr, arithmetics);
        	this.responseSolution = Integer.parseInt(responseSolution + "");
        	// Esconde os d�gitos.
        	numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? numberStr[positions[0]].charAt(0) + "?" : "?";
        	numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? numberStr[positions[1]].charAt(0) + "?" : "?";
            // Gera as op��es de escolha.
            this.optionsChoose = generationOptionsChoose(this.responseSolution, type);
        }

        // Equa��o com o d�gito da esquerda escondido.
        else if (type == 2){
        	// Gera 2 posi��o aleat�rias entre os 3 n�meros.
            int[] positions = generationPositionSelection1(numbers);
        	// Corrige a equa��o sorteando um dos d�gitos dos n�meros escolhidos.
        	int random = randomNumber_1(2);
        	String responseSolution = random == 0 ? numberStr[positions[0]].charAt(0) + "" : numberStr[positions[1]].charAt(0) + "";
        	numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? responseSolution + numberStr[positions[0]].charAt(1) : responseSolution;
        	numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? responseSolution + numberStr[positions[1]].charAt(1) : responseSolution;
        	arithmetics = processArithmetics(positions, arithmetics);
        	// Calcula o resultado.
        	this.resultCalculate = returnResult(numberStr, arithmetics);
        	this.responseSolution = Integer.parseInt(responseSolution + "");
        	// Esconde os d�gitos.
        	numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? "?" + numberStr[positions[0]].charAt(1) : "?";
        	numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? "?" + numberStr[positions[1]].charAt(1) : "?";
            // Gera as op��es de escolha.
            this.optionsChoose = generationOptionsChoose(this.responseSolution, type);
        }

        // Equa��o com primeiro e segundo d�gito escondido.
        else if (type == 3){
        	// Gera 2 posi��o aleat�rias entre os 3 n�meros.
            int[] positions = generationPositionSelection1(numbers);
            // Sequ�ncia dos n�meros sorteados.
            int random = randomNumber_1(2); // Sorteia n�mero de 'positions'.
            String digRandom = numberStr[positions[random]];
            int random2 = randomNumber_1(digRandom.length()); // Sorteia d�gito do n�mero.
            String responseSolution = digRandom.charAt(random2) + "";
            // Corrige a equa��o.
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
            // Esconde os d�gitos.
            if (random3 == 0){
            	numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? numberStr[positions[0]].charAt(0) + "?" : "?";
            	numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? "?" + numberStr[positions[1]].charAt(1) : "?";
            } else {
            	numberStr[positions[0]] = numberStr[positions[0]].length() > 1 ? "?" + numberStr[positions[0]].charAt(1) : "?";
            	numberStr[positions[1]] = numberStr[positions[1]].length() > 1 ? numberStr[positions[1]].charAt(0) + "?" : "?";
            }
            // Gera as op��es de escolha.
            this.optionsChoose = generationOptionsChoose(this.responseSolution, type);
        }

        // Equa��o com um n�mero escondido.
        else if (type == 4){
        	// Sorteia a posi��o do n�mero.
        	int positionHide = generationPositionSelection2(numbers);
        	// Calcula o resultado.
        	this.resultCalculate = returnResult(numberStr, arithmetics);
            this.responseSolution = numbers[positionHide];
            // Esconde o n�mero.
            numberStr[positionHide] = "??";
            // Gera as op��es de escolha.
            this.optionsChoose = generationOptionsChoose(this.responseSolution, type);
        }

        // Equa��o com o resultado escondido.
        else if (type == 5){
        	this.resultCalculate = returnResult(numberStr, arithmetics);
            this.responseSolution = this.resultCalculate;
            // Gera as op��es de escolha.
            this.optionsChoose = generationOptionsChoose(this.resultCalculate, type);
        }
        
        // String para imprimir na tela..
        String string = "";
        for (int i = 0; i < numbers.length; i++)
            string += i == 0 ? numberStr[0] : " "+arithmetics[i-1]+" "+numberStr[i];
        string += type != 5 ? " = " + this.resultCalculate : " = " + "??";
        this.equationString = string;
    }

    //- Inicia a gera��o do c�lculo do jogo.
    public void startGame(int type){
        // Sorteia os n�meros.
        int[] numbers = generationNumbers();
        // Sorteia os operadores aritm�ticos.
        char[] arithmetics = generationArithmetics();
        // Processa o c�lculo a partir do tipo de jogo.
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