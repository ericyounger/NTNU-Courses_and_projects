package com.example.sudoku;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class EnterGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_difficulty);
    }

    public void onDifficultySelection(View v){
        Intent intent = new Intent("com.example.sudoku.InGame");

        switch (v.getId()){
            case R.id.easyDifficulty:
                Log.i("Difficulty", "easy");
                intent.putExtra("difficulty", "easy");
                break;
            case R.id.mediumDifficulty:
                Log.i("Difficulty", "medium");
                intent.putExtra("difficulty", "medium");
                break;
            case R.id.hardDifficulty:
                Log.i("Difficulty", "hard");
                intent.putExtra("difficulty", "hard");
                break;
            default:
                break;
        }

        startActivityForResult(intent, 1);
    }

    public void startGame(){

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Log.i("Return data from intent", "LOL");
        }
    }


    public void onCancelEnterGame(View v){
        finish();
    }

}
