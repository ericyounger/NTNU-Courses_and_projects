package com.example.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu meny){
        super.onCreateOptionsMenu(meny);//kaller metoden som vi arver, er dog ikke nødvendig
        meny.add("Eric"); //legger til meny-valg med teksten «Valg 1»
        meny.add("Younger");
        Log.i("onCreateOptionsMenu()","meny laget"); //skriver ut til logg, vises i LogCat
        return true; //true her gjør at menyen vil vises    }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getTitle().equals("Eric")){
            Log.i("onOptionsItemSelected()","Eric er valgt");
        } else if(item.getTitle().equals("Younger")){
            Log.i("onOptionsItemSelected()", "Younger er valgt");
        }
        return true;
    }
}