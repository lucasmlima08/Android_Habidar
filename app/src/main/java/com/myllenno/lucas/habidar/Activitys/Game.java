package com.myllenno.lucas.habidar.Activitys;
import com.myllenno.lucas.habidar.Functions.Definitions;
import com.myllenno.lucas.habidar.Functions.GenerateCalculation;
import com.myllenno.lucas.habidar.Functions.ProcessSound;
import com.myllenno.lucas.habidar.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Game extends Activity {

    private GenerateCalculation calculation = new GenerateCalculation();

    private TextView score, timeGame, equation;
    private ProgressBar progressBar;
    private Button op1, op2, op3, op4, op5, op6;
    private String equationString = "";
    private int timeCount = Definitions.timeGame;
    private Thread timeThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        // Defined text display.
        score = (TextView) findViewById(R.id.score);
        timeGame = (TextView) findViewById(R.id.timeGame);
        equation = (TextView) findViewById(R.id.equation);
        // Defined progressbar.
        progressBar = (ProgressBar) findViewById(R.id.progressGame);
        progressBar.setMax(timeCount);
        // Defined buttons
        op1 = (Button) findViewById(R.id.op1);
        op2 = (Button) findViewById(R.id.op2);
        op3 = (Button) findViewById(R.id.op3);
        op4 = (Button) findViewById(R.id.op4);
        op5 = (Button) findViewById(R.id.op5);
        op6 = (Button) findViewById(R.id.op6);
        // Sorted new equation to display.
        sortedEquation();
        // Defined equation on string display.
        equationString = calculation.stringCalculationHide;
        // Include text display.
        if (Definitions.score == 1)
            score.setText(getResources().getString(R.string.point).replaceAll("#d%b#", Definitions.score+""));
        else
            score.setText(getResources().getString(R.string.points).replaceAll("#d%b#", Definitions.score + ""));
        equation.setText(equationString);
        // Include values in buttons of options.
        includeValuesOnOptions();
        // Tempo de jogo.
        timeThread = timeRunning();
        timeThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeThread.interrupt();
    }

    // Define o tipo de jogo de acordo com a sequência atual.
    private int typeOfGame(){
        int typeOfGame;
        // Inclui o tipo de jogo pela sequência.
        if (Definitions.typeOfGame == 0){
            typeOfGame = Definitions.typeOfGameAux;
            Definitions.typeOfGameAux++;
            if (Definitions.typeOfGameAux == 6)
                Definitions.typeOfGameAux = 1;
        // Inclui o tipo de jogo para o atual.
        } else {
            typeOfGame = Definitions.typeOfGame;
        }
        return typeOfGame;
    }

    // Sorteia uma equação de acordo com o tipo atual de jogo.
    private void sortedEquation(){
        int typeOfGame = typeOfGame();
        calculation.startGame(typeOfGame);
    }

    // Inclui as opções de resposta nos botões.
    private void includeValuesOnOptions(){
        int valuesOptionsToArray[] = calculation.optionsChoose;
        for (int i=0; i<valuesOptionsToArray.length; i++)
            includeOnButtonsOptions(i, valuesOptionsToArray[i]);
    }

    private void includeOnButtonsOptions(int button, int value){
        if (button == 0)
            op1.setText(value + "");
        else if (button == 1)
            op2.setText(value + "");
        else if (button == 2)
            op3.setText(value + "");
        else if (button == 3)
            op4.setText(value + "");
        else if (button == 4)
            op5.setText(value+"");
        else if (button == 5)
            op6.setText(value+"");
    }

    public void eventGame(View view){
        Button buttonOption = (Button) view;
        if (Integer.parseInt(buttonOption.getText()+"") == calculation.responseSolution){
            ProcessSound.playSoundEffect("accepted");
            // Aumenta a pontuação de acordo com seu peso.
            if (Definitions.timeGame == 10)
                Definitions.score += timeCount * 5;
            else if (Definitions.timeGame == 20)
                Definitions.score += timeCount * 2;
            else if (Definitions.timeGame == 30)
                Definitions.score += timeCount;
            alternateScreens("accepted");
        } else {
            ProcessSound.playSoundEffect("failed");
            alternateScreens("failed");
        }
    }

    private void alternateScreens(String alternate){
        Intent intent;
        if (alternate.equals("accepted"))
            intent = new Intent(this, Accepted.class);
        else
            intent = new Intent(this, Failed.class);
        intent.putExtra("calculation", calculation.stringCalculation);
        timeThread.interrupt();
        startActivity(intent);
        super.finish();
    }

    public Thread timeRunning(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (timeCount > 0){
                        progressBar.setProgress(timeCount);
                        timeGame.post(new Runnable() { // Mostra o tempo no Text View.
                            public void run() {
                                timeGame.setText(getResources().getString(R.string.timeResponse).replaceAll("#d%b#", timeCount + ""));
                            }
                        });
                        Thread.sleep(1000);
                        timeCount--;
                    }
                    // Condição de término caso extrapole o tempo de resposta.
                    if (timeCount <= 0)
                        alternateScreens("failed");
                } catch (Exception e){}
            }
        });
        return thread;
    }
}
