package com.example.sudoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseManager  extends SQLiteOpenHelper {
    static final String TABLE_NAME = "sudoku";
    static final String KEY_ROWID = "_id";
    static final String KEY_DIFFICULTY = "diffiulty";
    static final String KEY_BOARD = "board";

    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME="SudokuDB";
    static final int DATABASE_VERSION=1;
    static final String DATABASE_CREATE1 = "create table if not exists " + TABLE_NAME
            + " ("+KEY_ROWID+" integer primary key autoincrement, "
            + KEY_DIFFICULTY +" text not null, "
            + KEY_BOARD + " text not null);";


    static final String GET_ALL_BOARD_BY_DIFFICULTY =
            "select "+ KEY_BOARD +  " from "
                    + TABLE_NAME+" where "
                    + KEY_DIFFICULTY + "=?";
    private Context context;

    public DatabaseManager(Context context) throws Exception {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        onCreate(db);
    }
    public void clean() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 0, 0);
        db.close();
    }

    public long insertBoard(String difficulty, String board){
        SQLiteDatabase db = null;
        db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DIFFICULTY, difficulty);
        initialValues.put(KEY_BOARD, board);

        long id = db.insert(TABLE_NAME, null, initialValues);
        return id;
    }


    public ArrayList<String> getAllBoardsByDifficulty(String difficulty)  {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> res = new ArrayList<>();
        Cursor cursor =
                db.rawQuery(GET_ALL_BOARD_BY_DIFFICULTY, new String[]{difficulty});
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                res.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        db.close();
        return res;
    }
}
