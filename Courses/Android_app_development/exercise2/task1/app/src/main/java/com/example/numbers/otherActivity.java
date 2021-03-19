package com.example.numbers;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class otherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rnd = new Random();

        //Toast.makeText(this, number, Toast.LENGTH_LONG).show();

        int upperLimit = getIntent().getIntExtra("upperLimit", 1);

        int number = rnd.nextInt(upperLimit);

        Intent intent = new Intent();
        intent.putExtra("number", number);
        setResult(RESULT_OK, intent);
        finish();

    }




}