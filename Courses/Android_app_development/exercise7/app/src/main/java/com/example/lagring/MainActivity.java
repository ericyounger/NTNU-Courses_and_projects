package com.example.lagring;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity{
    private int selectedBook = 0;
    private ArrayList<Book> books;
    private DatabaseManager db;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    private Button author;
    private Button title;

    public ArrayList<Book> readInFromCSV(){
        ArrayList<Book> books = new ArrayList<>();
        try {
            String row;
            InputStream is = getResources().openRawResource(R.raw.books);
            BufferedReader csvReader = new BufferedReader(new InputStreamReader(is));

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split("<");
                if(!row.contains("Author")){
                    Log.i("førsterad", data[0]);
                    Book a = new Book(data);
                    books.add(a);
                }
            }
            csvReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = (ListView) findViewById(R.id.listView);
                // Load books from file
        this.books = readInFromCSV();

        try{
            db = new DatabaseManager(this);
            db.clean();
            long id;
            for (Book book : this.books){
                id = db.insert(book.getAuthor(),book.getTitle());
                Log.i("Insert", book.getTitle());
            }

        } catch (Exception e){
            Log.e("exception", e.toString());
        }

        ArrayList<String> res = db.getAllBooks();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res);
        listView.setAdapter(adapter);

        author = (Button) findViewById(R.id.listByAuthor);
        title = (Button) findViewById(R.id.listByTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.changeListColour) {
            Intent intent = new Intent(this, BackgroundPref.class);
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ListView listView = findViewById(R.id.listView);
            String backgroundColor = getDefaultSharedPreferences(this).getString("backgroundColorPref", "#ffffff");
            listView.setBackgroundColor(Color.parseColor(backgroundColor));
        }
    }

    public void changeListColor(){
        SharedPreferences prefs = getDefaultSharedPreferences(this);
        String chosenColour = prefs.getString("listPreference","#ffffff");
        listView.setBackgroundColor(Color.parseColor(chosenColour));
    }

    @Override
    public void onResume(){
        super.onResume();
        changeListColor();

    }

    public void onSelectListMode(View v) {

        ArrayList<String> res2 = null;


        Log.i("button", "button clicked");

        if(v.getId() == author.getId()){
            Log.i("button", "author");
            res2 = db.getAllAuthors();

        }

        if(v.getId() == title.getId()){
            Log.i("button", "title");
            res2 = db.getAllBooks();
        }

        if(res2 != null){
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res2);
            listView.setAdapter(adapter);

        }
    }


}