package com.example.sudoku;


import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class CreateBoard extends AppCompatActivity {

    private String difficulty;
    private GridLayout boardView;
    private EditText[][] board = new EditText[9][9];
    private RadioButton easyRadioBtn;
    private RadioButton mediumRadioBtn;
    private RadioButton hardRadioBtn;
    private DatabaseManager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_board);
        createBoard();
        easyRadioBtn = (RadioButton) findViewById(R.id.easyRadioBtn);
        mediumRadioBtn = (RadioButton) findViewById(R.id.mediumRadioBtn);
        hardRadioBtn = (RadioButton) findViewById(R.id.hardRadioBtn);

        try{
            db = new DatabaseManager(this);

        } catch (Exception e){
            Log.e("exception", e.toString());
        }
    }




    public void inGameExit(View v){
        finish();
    }

    public void onSaveBoard(View v){

        StringBuilder sb = new StringBuilder();
        if(easyRadioBtn.isChecked() || mediumRadioBtn.isChecked() || hardRadioBtn.isChecked()){

            for(int x=0; x<board.length; x++){
                for (int y=0; y<board[x].length; y++){
                    if(board[x][y].getText().toString().trim().equals("")){
                        sb.append("x");
                    } else {
                        sb.append(board[x][y].getText().toString());
                    }

                }
                sb.append(";");
            }
            if(easyRadioBtn.isChecked()){
                db.insertBoard("easy", sb.toString());
            } else if(mediumRadioBtn.isChecked()){
                db.insertBoard("medium", sb.toString());
            } else if(hardRadioBtn.isChecked()){
                db.insertBoard("hard", sb.toString());
            }

            Toast.makeText(this, getString(R.string.board_saved).toString() , Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, getString(R.string.needToCheckDifficulty).toString() , Toast.LENGTH_SHORT).show();
        }
    }

    public void createBoard(){
        boardView = (GridLayout) findViewById(R.id.sudokuGrid);
        boardView.setUseDefaultMargins(true);
        boardView.setBackgroundColor(Color.DKGRAY);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        for(int row=0; row<9; row++){
            for(int col=0; col<9; col++){
                EditText entry = new EditText(this);
                entry.setInputType(InputType.TYPE_CLASS_NUMBER);
                entry.setWidth((width/10)-10);
                entry.setHeight((width/10)-10);
                int maxLength = 1;
                entry.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
                entry.setGravity(Gravity.CENTER);
                entry.setBackgroundColor(Color.LTGRAY);
                entry.setPadding(0,0,0,0);
                boardView.addView(entry);
                board[row][col] = entry;
            }
        }

        colorGroups();
    }

    public void colorGroups(){
        for(int row=0; row<board.length; row++){
            for(int col=0; col<board[row].length; col++){
                if(row >= 0 && row < 3){
                    if(col >= 0 && col < 3){
                        board[row][col].setBackgroundColor(Color.WHITE);
                    } else if(col >= 3 && col < 6){
                        board[row][col].setBackgroundColor(Color.LTGRAY);
                    } else if(col >= 6){
                        board[row][col].setBackgroundColor(Color.WHITE);
                    }

                } else if(row >= 3 && row < 6){
                    if(col >= 0 && col < 3){
                        board[row][col].setBackgroundColor(Color.LTGRAY);
                    } else if(col >= 3 && col < 6){
                        board[row][col].setBackgroundColor(Color.WHITE);
                    } else if(col >= 6){
                        board[row][col].setBackgroundColor(Color.LTGRAY);
                    }
                } else if(row >= 6){
                    if(col >= 0 && col < 3){
                        board[row][col].setBackgroundColor(Color.WHITE);
                    } else if(col >= 3 && col < 6){
                        board[row][col].setBackgroundColor(Color.LTGRAY);
                    } else if(col >= 6){
                        board[row][col].setBackgroundColor(Color.WHITE);
                    }
                }
            }
        }
    }

}
