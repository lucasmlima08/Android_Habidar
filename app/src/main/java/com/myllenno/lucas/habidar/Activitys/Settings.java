package com.myllenno.lucas.habidar.Activitys;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.myllenno.lucas.habidar.Functions.ProcessFiles;
import com.myllenno.lucas.habidar.Functions.ProcessSound;
import com.myllenno.lucas.habidar.R;
import com.myllenno.lucas.habidar.Functions.Definitions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class Settings extends Activity {

    Button tr1, tr2, tr3;
    Button it1, it2, it3;
    Switch music, effectsSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        // Defined buttons of time game response.
        tr1 = (Button) findViewById(R.id.tr1);
        tr2 = (Button) findViewById(R.id.tr2);
        tr3 = (Button) findViewById(R.id.tr3);
        // Defined buttons of time interval.
        it1 = (Button) findViewById(R.id.it1);
        it2 = (Button) findViewById(R.id.it2);
        it3 = (Button) findViewById(R.id.it3);
        // Defined switch of decision sounds.
        music = (Switch) findViewById(R.id.music);
        effectsSound = (Switch) findViewById(R.id.effectsSound);
        // Import definitions.
        importDefinitions();
        // Banner.
        AdView adView = (AdView) this.findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Definitions.music = music.isChecked();
        Definitions.effectsSound = effectsSound.isChecked();
        saveDefinitionsSound();
        saveDefinitionsFile();
    }

    private void importDefinitions(){
        music.setChecked(Definitions.music);
        effectsSound.setChecked(Definitions.effectsSound);
        // music.
        if (Definitions.timeGame == 10)
            eventTimeResponse(tr1);
        else if (Definitions.timeGame == 20)
            eventTimeResponse(tr2);
        else if (Definitions.timeGame == 30)
            eventTimeResponse(tr3);
        // effect sound.
        if (Definitions.timeInterval == 5)
            eventInterval(it1);
        else if (Definitions.timeInterval == 10)
            eventInterval(it2);
        else if (Definitions.timeInterval == 15)
            eventInterval(it3);
    }

    private void saveDefinitionsFile(){
        try {
            ProcessFiles.removeFile(this, "settings.txt");
            ProcessFiles.createFileSettings(this);
        } catch (Exception e){
            Toast.makeText(this, getResources().getString(R.string.errorSaveSettings), Toast.LENGTH_LONG).show();
        }
    }

    private void saveDefinitionsSound(){
        try {
            if (Definitions.music)
                ProcessSound.playBackgroundMusic();
            else
                ProcessSound.stopBackgroundMusic();
        } catch (Exception e){}
    }

    public void eventTimeResponse(View view){
        tr1.setBackgroundColor(0xfffff2fc);
        tr2.setBackgroundColor(0xfffff2fc);
        tr3.setBackgroundColor(0xfffff2fc);
        if (view.getId() == R.id.tr1){
            tr1.setBackgroundColor(0xff35adec);
            Definitions.timeGame = 10;
        } else if (view.getId() == R.id.tr2){
            tr2.setBackgroundColor(0xff35adec);
            Definitions.timeGame = 20;
        } else if (view.getId() == R.id.tr3){
            tr3.setBackgroundColor(0xff35adec);
            Definitions.timeGame = 30;
        }
    }

    public void eventInterval(View view){
        it1.setBackgroundColor(0xfffff2fc);
        it2.setBackgroundColor(0xfffff2fc);
        it3.setBackgroundColor(0xfffff2fc);
        if (view.getId() == R.id.it1){
            it1.setBackgroundColor(0xff35adec);
            Definitions.timeInterval = 5;
        } else if (view.getId() == R.id.it2){
            it2.setBackgroundColor(0xff35adec);
            Definitions.timeInterval = 10;
        } else if (view.getId() == R.id.it3){
            it3.setBackgroundColor(0xff35adec);
            Definitions.timeInterval = 15;
        }
    }

    public void eventSettings(View view){
        ProcessSound.playSoundEffect("beep");
        if (view.getId() == R.id.credits)
            startActivity(new Intent(this, Credits.class));
    }
}
