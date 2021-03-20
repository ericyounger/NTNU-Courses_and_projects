package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String easy = "3586xxx1x;xxxxx3xxx;xxxx2xxx4;xx4xxx35x;xx91xx2xx;xxxxx2x8x;54x86xxx1;xxxxxxxxx;6xxxx78xx;";
        String medium = "x3x16xx24;x5xxxxxx3;xxx9xxxxx;xxxxxxx51;xx1xx86xx;xx52xxxxx;x695xx7xx;x8xxx7xxx;xxxxx423x;";
        String hard = "xxxx793xx;4xxxx8x97;x6xxxxxxx;x5xxxxx8x;3xxxx6xxx;9xx12xxxx;x7x6xxxx3;1x5xx3x4x;xx2xxxx5x;";

        try{
            db = new DatabaseManager(this);

            db.clean();
            ArrayList<String> simpleCheck = db.getAllBoardsByDifficulty("easy");
            if(simpleCheck.size() == 0){
                db.insertBoard("easy", easy);
                db.insertBoard("medium", medium);
                db.insertBoard("hard", hard);
            }


        } catch (Exception e){
            Toast.makeText(this, getString(R.string.general_error).toString() , Toast.LENGTH_SHORT).show();
            Log.e("exception", e.toString());
        }

    }

    public void onEnterGame(View v){
        Intent intent =  new Intent("com.example.sudoku.EnterGame");
        startActivity(intent);
    }

    public void onCreateBoard(View v){
        Intent intent =  new Intent("com.example.sudoku.CreateBoard");
        startActivity(intent);
    }

    public void onInstructions(View v){
        Intent intent =  new Intent("com.example.sudoku.Instructions");
        startActivity(intent);
    }

    public void onLanguageSelection(View v){
        Log.i("language:", Integer.toString(v.getId()));

        if(v.getId() == R.id.englishBtn){
            String languageToLoad  = "en"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            this.setContentView(R.layout.activity_main);
        }

        if(v.getId() == R.id.norwegianBtn){
            String languageToLoad  = "no"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            this.setContentView(R.layout.activity_main);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Log.i("Return data from intent", "LOL");
        }
    }
}