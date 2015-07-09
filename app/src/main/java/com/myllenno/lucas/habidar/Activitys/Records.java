package com.myllenno.lucas.habidar.Activitys;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.myllenno.lucas.habidar.Functions.ProcessFiles;
import com.myllenno.lucas.habidar.R;
import java.util.ArrayList;

public class Records extends Activity {

    TableLayout tableRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);
        tableRecords = (TableLayout) findViewById(R.id.tableRecords);
        readRecords();
        Toast.makeText(this, getResources().getString(R.string.clickResetRecord2).replaceAll("#d%b#", "6"), Toast.LENGTH_SHORT).show();
    }

    private void readRecords(){
        try {
            ArrayList<String> scores = ProcessFiles.readFile(this, "scores.txt");
            ArrayList<String> nameScores = ProcessFiles.readFile(this, "name_scores.txt");
            for (int i=0; i < nameScores.size(); i++)
                includeTableRecords(nameScores.get(i), scores.get(i));
        } catch (Exception e){}
    }

    private void includeTableRecords(String name, String score){
        TextView txtName = new TextView(this);
        txtName.setText(name + " : ");
        txtName.setGravity(Gravity.RIGHT);
        txtName.setTextColor(0xfffaf9ff);
        txtName.setTextSize(getResources().getDimension(R.dimen.records));
        TextView txtScore = new TextView(this);
        txtScore.setText(score);
        txtScore.setGravity(Gravity.LEFT);
        txtScore.setTextColor(0xfffaf9ff);
        txtScore.setTextSize(getResources().getDimension(R.dimen.records));
        TableRow tableRow = new TableRow(this);
        tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
        tableRow.addView(txtName);
        tableRow.addView(txtScore);
        tableRecords.addView(tableRow);
    }

    private int clicks = 5;
    public void eventResetScores(View view){
        if (clicks == 0){
            try {
                ProcessFiles.removeFile(this, "name_scores.txt");
                ProcessFiles.removeFile(this, "scores.txt");
                ProcessFiles.createFile(this, "name_scores.txt");
                ProcessFiles.createFile(this, "scores.txt");
                Toast.makeText(this, getResources().getString(R.string.recordsReset), Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, Records.class));
                super.finish();
            } catch (Exception e){
                Toast.makeText(this, getResources().getString(R.string.errorResetRecords), Toast.LENGTH_LONG).show();
            }
            clicks = 6;
        } else {
            if (clicks == 1)
                Toast.makeText(this, getResources().getString(R.string.clickResetRecord1).replaceAll("#d%b#", "1"), Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, getResources().getString(R.string.clickResetRecord2).replaceAll("#d%b#", clicks + ""), Toast.LENGTH_SHORT).show();
            clicks--;
        }
    }
}
