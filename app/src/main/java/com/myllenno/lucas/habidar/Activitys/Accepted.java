package com.myllenno.lucas.habidar.Activitys;
import com.myllenno.lucas.habidar.Functions.Definitions;
import com.myllenno.lucas.habidar.Functions.ProcessSound;
import com.myllenno.lucas.habidar.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Accepted extends Activity {

    private ProgressBar progressSleep;
    private int timeCount = Definitions.timeInterval;
    private TextView calculation, score;
    private Thread timeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accepted);
        progressSleep = (ProgressBar) findViewById(R.id.progressSleep);
        progressSleep.setMax(timeCount);
        calculation = (TextView) findViewById(R.id.calculationSuccess);
        setCalculation();
        score = (TextView) findViewById(R.id.scoreSuccess);
        if (Definitions.score == 1)
            score.setText(getResources().getString(R.string.point).replaceAll("#d%b#", Definitions.score+""));
        else
            score.setText(getResources().getString(R.string.points).replaceAll("#d%b#", Definitions.score + ""));
        // Tempo de descanso.
        timeThread = timeSleep();
        timeThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeThread.interrupt();
    }

    private void setCalculation(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            calculation.setText(bundle.getString("calculation"));
    }

    private void returnGame(){
        startActivity(new Intent(this, Game.class));
        this.finish();
    }

    public void eventSuccess(View view){
        ProcessSound.playSoundEffect("beep");
        if (view.getId() == R.id.continuation)
            returnGame();
    }

    public Thread timeSleep(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (timeCount > 0){
                        progressSleep.setProgress(timeCount);
                        Thread.sleep(1000);
                        timeCount--;
                    }
                    // Condição de término do tempo de espera.
                    if (timeCount <= 0)
                        returnGame();
                } catch (Exception e){}
            }
        });
        return thread;
    }
}
