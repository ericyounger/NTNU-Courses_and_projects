package com.example.friendregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ArrayList<User> users = new ArrayList<User>();
    ArrayAdapter<User> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users.add(new User("Eric", "120190"));
        initListview();
    }

    public void initListview(){


        this.arrayAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item, users);
        ListView friendList = (ListView) findViewById(R.id.friendsView);

        friendList.setAdapter(arrayAdapter);
        friendList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View valgt, int posisjon, long id) {
                Intent intent = new Intent("com.example.friendsregistration.EditFriendActivity");
                intent.putExtra("posisjon", posisjon);
                intent.putExtra("name", users.get(posisjon).getName());
                intent.putExtra("bday", users.get(posisjon).getBirthday());
                startActivityForResult(intent, 1);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    public void addPerson(View view){
        Log.i("AAA", "Button clicked");
        Intent intent = new Intent("com.example.friendsregistration.AddFriendsActivity");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && !data.getExtras().getBoolean("new")){
            String name = data.getStringExtra("changedName");
            String birthday = data.getStringExtra("changedBirthday");
            int posisjon = data.getExtras().getInt("posisjon");

            this.users.get(posisjon).setBirthday(birthday);
            this.users.get(posisjon).setName(name);
            this.arrayAdapter.notifyDataSetChanged();

        }

        else if(resultCode == RESULT_OK && data.getExtras().getBoolean("new")){
            String newName = data.getStringExtra("name");
            String bday = data.getStringExtra("birthday");
            this.users.add(new User(newName, bday));
            this.arrayAdapter.notifyDataSetChanged();
        }
    }

}