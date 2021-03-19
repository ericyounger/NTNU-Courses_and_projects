package com.example.task2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class randomActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rnd = new Random();

        int upperLimit = getIntent().getIntExtra("upperLimit", 1);
        Log.i("Intent","Mottok intent med upper limit");


        int array[] = {rnd.nextInt(upperLimit), rnd.nextInt(upperLimit)};

        Intent intent = new Intent();
        intent.putExtra("numbers", array);
        setResult(RESULT_OK, intent);
        finish();

    }

}