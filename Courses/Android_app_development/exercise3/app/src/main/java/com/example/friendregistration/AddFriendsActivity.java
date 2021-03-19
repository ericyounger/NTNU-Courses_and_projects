package com.example.friendregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class AddFriendsActivity extends Activity {

    private ArrayList<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);
    }

    public void SaveNewFriendOnClick(View view){
        Intent intent = new Intent();
        EditText name = (EditText) findViewById(R.id.addFriendName);
        EditText bday = (EditText) findViewById(R.id.addFriendBirthday);

        if(!(name.getText().toString().isEmpty() || bday.getText().toString().isEmpty())){
            intent.putExtra("new", true);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("birthday", bday.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(view.getContext(), "Kan ikke ha blankt navn eller dato", Toast.LENGTH_LONG).show();
        }

    }

    public void cancelAddNewFriendOnClick(View view){
        setResult(RESULT_CANCELED);
        finish();
    }


}