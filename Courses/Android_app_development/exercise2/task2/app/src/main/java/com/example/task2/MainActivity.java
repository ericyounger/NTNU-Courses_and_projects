package com.example.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView leftTextView;
    private TextView rightTextView;
    private EditText answer;
    private EditText upperLimit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.leftTextView = (TextView) findViewById(R.id.leftNumber);
        this.rightTextView = (TextView) findViewById(R.id.rightNumber);
        this.answer = (EditText) findViewById(R.id.answerTextEdit);
        this.upperLimit = (EditText) findViewById(R.id.upperLimitTextEdit);

    }

    public void addNumbers(View view){

        int leftNumber = Integer.parseInt(this.leftTextView.getText().toString());
        int rightNumber = Integer.parseInt(this.rightTextView.getText().toString());

        int correctAnswer = leftNumber + rightNumber;

        if(correctAnswer == Integer.parseInt(this.answer.getText().toString())){
            Toast.makeText(this, getString(R.string.correct).toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.wrong).toString() + " " + correctAnswer , Toast.LENGTH_SHORT).show();
        }

        refreshRandomNumbers();
    }

    public void refreshRandomNumbers(){
        Intent intent = new Intent("com.example.numbers.randomActivity");
        intent.putExtra("upperLimit", Integer.parseInt(this.upperLimit.getText().toString()));
        Log.i("upper limit is" , this.upperLimit.getText().toString());
        startActivityForResult(intent, 1);
    }

    public void multiplyNumbers(View view){

        int leftNumber = Integer.parseInt(this.leftTextView.getText().toString());
        int rightNumber = Integer.parseInt(this.rightTextView.getText().toString());
        int correctAnswer =  leftNumber * rightNumber;


        if(correctAnswer == Integer.parseInt(this.answer.getText().toString())){
            Toast.makeText(this, getString(R.string.correct).toString(), Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, getString(R.string.wrong).toString() + " " + correctAnswer , Toast.LENGTH_SHORT).show();
        }

        refreshRandomNumbers();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Log.i("Return data from intent", "LOL");
            leftTextView.setText(Integer.toString(data.getIntArrayExtra("numbers")[0]));
            rightTextView.setText(Integer.toString(data.getIntArrayExtra("numbers")[1]));

        }
    }
}