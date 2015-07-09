package com.myllenno.lucas.habidar.Activitys;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.myllenno.lucas.habidar.Functions.GenerateCalculation;
import com.myllenno.lucas.habidar.Functions.ProcessSound;
import com.myllenno.lucas.habidar.R;
import com.myllenno.lucas.habidar.Functions.Definitions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TypeGame extends Activity {

    Button type1, type2, type3, type4, type5;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_game);
        type1 = (Button) findViewById(R.id.typeGame1);
        type2 = (Button) findViewById(R.id.typeGame2);
        type3 = (Button) findViewById(R.id.typeGame3);
        type4 = (Button) findViewById(R.id.typeGame4);
        type5 = (Button) findViewById(R.id.typeGame5);
        setTextButtons();
        // Banner.
        AdView adView = (AdView) this.findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Definitions.score = 0;
    }

    private void setTextButtons(){
        GenerateCalculation calculation = new GenerateCalculation();
        calculation.startGame(1);
        type1.setText(calculation.stringCalculationHide);
        calculation.startGame(2);
        type2.setText(calculation.stringCalculationHide);
        calculation.startGame(3);
        type3.setText(calculation.stringCalculationHide);
        calculation.startGame(4);
        type4.setText(calculation.stringCalculationHide);
        calculation.startGame(5);
        type5.setText(calculation.stringCalculationHide);
    }

    public void eventsTypeOfGame(View view){
        ProcessSound.playSoundEffect("beep");
        if (view.getId() == R.id.typeGameFull)
            Definitions.typeOfGame = 0;
        else if (view.getId() == R.id.typeGame1)
            Definitions.typeOfGame = 1;
        else if (view.getId() == R.id.typeGame2)
            Definitions.typeOfGame = 2;
        else if (view.getId() == R.id.typeGame3)
            Definitions.typeOfGame = 3;
        else if (view.getId() == R.id.typeGame4)
            Definitions.typeOfGame = 4;
        else if (view.getId() == R.id.typeGame5)
            Definitions.typeOfGame = 5;
        startActivity(new Intent(this, Game.class));
    }
}
