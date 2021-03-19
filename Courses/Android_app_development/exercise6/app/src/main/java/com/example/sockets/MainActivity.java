package com.example.sockets;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    private EditText firstNumber;
    private EditText secondNumber;
    private TextView resultLabel;
    private Client klient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstNumber = (EditText) findViewById(R.id.firstNumberEdit);
        secondNumber = (EditText) findViewById(R.id.secondNumberEdit);
        resultLabel = (TextView) findViewById(R.id.answerTextView);
        new Server().start();//start server
    }

    public void onCalculateClicked(View v){
        int number1 = Integer.parseInt(this.firstNumber.getText().toString());
        int number2 = Integer.parseInt(this.secondNumber.getText().toString());
        klient = new Client(this, number1, number2);
        klient.start();
    }

    public void onResultFromSocket(String result){
        resultLabel.setText(result);
    }
}