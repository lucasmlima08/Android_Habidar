package com.myllenno.lucas.habidar.Activitys;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.myllenno.lucas.habidar.Functions.Definitions;
import com.myllenno.lucas.habidar.Functions.ProcessFiles;
import com.myllenno.lucas.habidar.Functions.ProcessSound;
import com.myllenno.lucas.habidar.R;
import java.util.ArrayList;

public class Failed extends Activity {

    private TextView calculation, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.failed);
        calculation = (TextView) findViewById(R.id.calculationError);
        score = (TextView) findViewById(R.id.scoreError);
        // Score.
        if (Definitions.score == 1)
            score.setText(getResources().getString(R.string.point).replaceAll("#d%b#", Definitions.score+""));
        else
            score.setText(getResources().getString(R.string.points).replaceAll("#d%b#", Definitions.score + ""));
        setCalculation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Definitions.score = 0;
    }

    private void setCalculation(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            calculation.setText(bundle.getString("calculation"));
    }

    private void saveScore(String nameScore){
        try {
            // Lê os arquivos de pontuação e nomes.
            ArrayList<String> scores = ProcessFiles.readFile(this, "scores.txt");
            ArrayList<String> name_scores = ProcessFiles.readFile(this, "name_scores.txt");
            // Salva a pontuação em ordem decrescente.
            int position = name_scores.size();
            for (int i=0; i < position; i++){
                if (Integer.parseInt(scores.get(i)) <= Definitions.score){
                    position = i;
                    break;
                }
            }
            scores.add(position, Definitions.score + "");
            if (nameScore.length() > 20)
                name_scores.add(position, nameScore.substring(0,20)+"..");
            else
                name_scores.add(position, nameScore);
            // Reescreve os arquivos com a nova pontuação.
            ProcessFiles.removeFile(this, "scores.txt");
            ProcessFiles.removeFile(this, "name_scores.txt");
            ProcessFiles.createFile(this, "scores.txt");
            ProcessFiles.createFile(this, "name_scores.txt");
            ProcessFiles.writeFile(this, "scores.txt", scores);
            ProcessFiles.writeFile(this, "name_scores.txt", name_scores);
            Toast.makeText(this, R.string.successfullySaved, Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    private void readNameScore(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.name);
        final EditText editText = new EditText(this);
        alertDialog.setView(editText);
        final String[] name_score = {""};
        alertDialog.setPositiveButton(R.string.save, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                name_score[0] = editText.getText().toString();
                verificationsName(name_score[0]);
                dialog.dismiss();
            }
        }).show();
    }

    private Boolean verificationsName(String name_score){
        if (name_score.equals("")) {
            Toast.makeText(this, R.string.notName, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            saveScore(name_score);
            return true;
        }
    }

    public void eventError(View view){
        ProcessSound.playSoundEffect("beep");
        if (view.getId() == R.id.continuation){
            startActivity(new Intent(this, Game.class));
            this.finish();
        } else if (view.getId() == R.id.saveScore){
            readNameScore();
        } else {
            this.finish();
        }
    }
}
