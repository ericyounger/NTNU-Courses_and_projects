package com.example.numbersgame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText nameInput;
    private EditText creditCardInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameInput = (EditText) findViewById(R.id.nameInput);
        creditCardInput = (EditText) findViewById(R.id.creditCardInput);

    }


    public void onSendClick(View v){
        Log.i("button", "On send click");
        Intent intent = new Intent("com.example.numbersgame.InGameActivity");
        intent.putExtra("playerName", nameInput.getText().toString());
        intent.putExtra("creditCard", Integer.parseInt(creditCardInput.getText().toString()));
        startActivity(intent);
    }


    public void onRestartClicked(View v){
        Log.i("button", "on restart clicked");
        nameInput.getText().clear();
        creditCardInput.getText().clear();
    }
}