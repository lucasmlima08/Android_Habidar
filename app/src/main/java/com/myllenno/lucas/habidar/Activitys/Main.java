package com.myllenno.lucas.habidar.Activitys;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.myllenno.lucas.habidar.Functions.Definitions;
import com.myllenno.lucas.habidar.Functions.ProcessFiles;
import com.myllenno.lucas.habidar.Functions.ProcessSound;
import com.myllenno.lucas.habidar.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Definição do áudio.
        ProcessSound.context = getApplicationContext();
        processMusic("create");
        // Criação dos arquivos.
        try {
            ProcessFiles.createFileSettings(this);
            ProcessFiles.createFile(this, "scores.txt");
            ProcessFiles.createFile(this, "name_scores.txt");
        } catch (Exception e){}
        // Importação das configurações.
        importSettings();
        // Banner.
        AdView adView = (AdView) this.findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveSettings();
        processMusic("stop");
        ProcessSound.playSoundEffect("exit");
    }

    private void importSettings(){
        try {
            // Import settings
            ArrayList<String> settings = ProcessFiles.readFile(this, "settings.txt");
            processSettings(settings);
        } catch (Exception e){}
        // Música de fundo.
        if (Definitions.music)
            processMusic("play");
    }

    private void saveSettings(){
        try {
            ProcessFiles.removeFile(this, "settings.txt");
            ProcessFiles.createFileSettings(this);
        } catch (Exception e){}
    }

    private void processSettings(ArrayList<String> settings){
        Definitions.music = Boolean.parseBoolean(settings.get(0));
        Definitions.effectsSound = Boolean.parseBoolean(settings.get(1));
        Definitions.timeGame = Integer.parseInt(settings.get(2));
        Definitions.timeInterval = Integer.parseInt(settings.get(3));
    }

    private void processMusic(String command){
        try {
            if (command.equals("create"))
                ProcessSound.definedBackgroundMusic("music");
            else if (command.equals("play"))
                ProcessSound.playBackgroundMusic();
            else if (command.equals("stop"))
                ProcessSound.stopBackgroundMusic();
        } catch (Exception e){}
    }

    public void eventsMain(View view){
        ProcessSound.playSoundEffect("beep");
        if (view.getId() == R.id.play)
            startActivity(new Intent(this, TypeGame.class));
        else if (view.getId() == R.id.records)
            startActivity(new Intent(this, Records.class));
        else if (view.getId() == R.id.instructions)
            startActivity(new Intent(this, Instructions.class));
        else if (view.getId() == R.id.settings)
            startActivity(new Intent(this, Settings.class));
    }
}
