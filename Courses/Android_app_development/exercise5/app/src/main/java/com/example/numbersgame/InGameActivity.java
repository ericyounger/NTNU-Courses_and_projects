package com.example.numbersgame;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InGameActivity extends AppCompatActivity {
    private String name;
    private TextView instructions;
    private int creditCardNumber;
    private EditText guessInput;
    private HttpWrapperThreaded con;
    private final String URL = "http://tomcat.stud.iie.ntnu.no/studtomas/tallspill.jsp";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_game);
        name = getIntent().getExtras().getString("playerName");
        creditCardNumber = getIntent().getExtras().getInt("creditCard");
        guessInput = (EditText) findViewById(R.id.editTextNumber);
        instructions = (TextView) findViewById(R.id.instructionText);
        con = new HttpWrapperThreaded(this, URL);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("navn", name);
        parameters.put("kortnummer", Integer.toString(creditCardNumber));
        con.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_GET, parameters);

    }

    public void sendInGameHandler(View v){
        String guess = guessInput.getText().toString();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tall", guess);
        con.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_GET, parameters);
    }


    public void  httpResponse(String response){
        instructions.setText(response);
    }

    public void restartInGameHandler(View v){
        finish();
    }
}