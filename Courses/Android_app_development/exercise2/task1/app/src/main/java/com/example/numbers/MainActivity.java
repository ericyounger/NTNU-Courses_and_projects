package com.example.numbers;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Har satt onClick til å peke på denne funksjonen
    public void generateNumber(View view){
        int upperLimit = 100;
        Intent intent = new Intent("com.example.numbers.otherActivity");
        intent.putExtra("upperLimit", upperLimit);
        startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int randomTall = data.getIntExtra("number", 1);
            TextView textView = (TextView) findViewById(R.id.numberView);
            textView.setText(Integer.toString(randomTall));
        }
    }



}