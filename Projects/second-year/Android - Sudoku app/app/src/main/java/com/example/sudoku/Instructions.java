package com.example.sudoku;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions_activity);
    }


    public void onExitInstructions(View v){
        finish();
    }

}
