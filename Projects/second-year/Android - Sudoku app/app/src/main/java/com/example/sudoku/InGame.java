package com.example.sudoku;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.Random;

public class InGame extends AppCompatActivity {

    private String difficulty;
    private GridLayout boardView;
    private EditText[][] board = new EditText[9][9];
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_game);
        difficulty = getIntent().getExtras().getString("difficulty");
        Log.i("Received", difficulty);
        createBoard();

        try{
            db = new DatabaseManager(this);
            ArrayList<String> boards = db.getAllBoardsByDifficulty(difficulty);
            loadBoard(boards);
            colorGroups();

        } catch (Exception e){
            Log.e("exception", e.toString());
        }

    }

    public void loadBoard(ArrayList<String> boards){
        Random random = new Random();
        int randomLevel = random.nextInt(boards.size());
        String level =  boards.get(randomLevel);

        int row=0;
        int col=0;
        for(int i=0; i<level.length(); i++){
            if(level.charAt(i) == ';'){
                row++;
                col = 0;
                continue;
            };

            if(!(level.charAt(i) == 'x')){
                board[row][col].setText(Character.toString(level.charAt(i)));
                board[row][col].setFocusable(false);
                board[row][col].setCursorVisible(false);
                board[row][col].setKeyListener(null);
            }
            col++;
        }
    }

    public Boolean checkHorizontally(){
        for (int row=0; row<board.length; row++){
            for(int col=0; col<board[row].length; col++){
                for (int other=col+1; other<board[row].length; other++){
                    if(board[row][col].getText().toString().equals(board[row][other].getText().toString())){
                        Log.i("v", board[row][col].getText().toString());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Boolean checkVertically(){
        for (int row=0; row<board.length; row++){
            for(int col=0; col<board[row].length; col++){
                for (int otherRow=row+1; otherRow<board.length; otherRow++){
                    if(board[row][col].getText().toString().equals(board[otherRow][col].getText().toString())){
                        Log.i("v", "false");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Boolean checkVerticallyRange(int rowStart, int colStart, int lengthOfGroup){
        for (int row=rowStart; row<rowStart+lengthOfGroup; row++){
            for(int col=colStart; col<colStart+lengthOfGroup; col++){
                for (int otherRow=row+1; otherRow<rowStart+lengthOfGroup; otherRow++){
                    if(board[row][col].getText().toString().equals(board[otherRow][col].getText().toString())){
                        Log.i("v", "false");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Boolean checkHorizontallyRange(int rowStart, int colStart, int lengthOfGroup){
        for (int row=rowStart; row<rowStart+lengthOfGroup; row++){
            for(int col=colStart; col<colStart+lengthOfGroup; col++){
                for (int other=col+1; other<colStart+lengthOfGroup; other++){
                    if(board[row][col].getText().toString().equals(board[row][other].getText().toString())){
                        Log.i("v", board[row][col].getText().toString());
                        return false;
                    }
                }
            }
        }
        return true;
    }




    public Boolean checkGroup(){
        int[] interval = {0,3,6};

        for (int rowCheckerNumber : interval){
            for(int colCheckerNumber : interval){
                boolean verticalCheck = checkVerticallyRange(rowCheckerNumber,colCheckerNumber, 3);
                boolean horionztalCheck = checkHorizontallyRange(rowCheckerNumber, colCheckerNumber, 3);
                if(!verticalCheck || !horionztalCheck){
                    Log.i("False", "aasd");
                    return false;
                }

            }
        }

        return true;
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
                entry.setBackgroundColor(Color.WHITE);
                entry.setPadding(0,0,0,0);
                entry.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {


                        ColorDrawable viewColor = (ColorDrawable) view.getBackground();
                        int colorId = viewColor.getColor();

                        if(colorId == Color.CYAN){
                            colorBasedOnGroup(view);
                        } else {
                            view.setBackgroundColor(Color.CYAN);
                        }
                        return true;
                    }
                });
                //entry.setText(0);
                boardView.addView(entry);
                board[row][col] = entry;
            }
        }
    }

    public void colorBasedOnGroup(View v){
        int foundRow = 0;
        int foundCol = 0;

        for(int row=0; row<board.length; row++){
            for(int col=0; col<board[row].length; col++){
                if (v.equals(board[row][col])){
                    foundCol=col;
                    foundRow=row;
                    break;
                }
            }
        }

        if(foundRow >= 0 && foundRow < 3){
            if(foundCol >= 0 && foundCol < 3){
                v.setBackgroundColor(Color.WHITE);
            } else if(foundCol >= 3 && foundCol < 6){
                v.setBackgroundColor(Color.LTGRAY);
            } else if(foundCol >= 6){
                v.setBackgroundColor(Color.WHITE);
            }

        } else if(foundRow >= 3 && foundRow < 6){
            if(foundCol >= 0 && foundCol < 3){
                v.setBackgroundColor(Color.LTGRAY);
            } else if(foundCol >= 3 && foundCol < 6){
                v.setBackgroundColor(Color.WHITE);
            } else if(foundCol >= 6){
                v.setBackgroundColor(Color.LTGRAY);
            }
        } else if(foundRow >= 6){
            if(foundCol >= 0 && foundCol < 3){
                v.setBackgroundColor(Color.WHITE);
            } else if(foundCol >= 3 && foundCol < 6){
                v.setBackgroundColor(Color.LTGRAY);
            } else if(foundCol >= 6){
                v.setBackgroundColor(Color.WHITE);
            }
        }

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

    public void inGameExit(View v){
        finish();
    }

    public void inGameCheck(View v){
        boolean horizontallyOk = checkHorizontally();
        boolean vertiallyOk = checkVertically();
        boolean groupOfThreeOk = checkGroup();

        if(horizontallyOk && vertiallyOk && groupOfThreeOk){
            Toast.makeText(this, getString(R.string.correct).toString() , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.wrong).toString() , Toast.LENGTH_SHORT).show();
        }

    }

}
