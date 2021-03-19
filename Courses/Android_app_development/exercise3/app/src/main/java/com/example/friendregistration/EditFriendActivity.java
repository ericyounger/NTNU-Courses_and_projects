package com.example.friendregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class EditFriendActivity extends Activity {

    private EditText nameEditInput;
    private EditText bdayEditInput;
    private int posisjon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_friends_layout);
        nameEditInput = (EditText) findViewById(R.id.editName);
        bdayEditInput = (EditText) findViewById(R.id.editBirthday);

        String name = getIntent().getExtras().getString("name");
        String bday = getIntent().getExtras().getString("bday");
        this.posisjon = getIntent().getExtras().getInt("posisjon");
        nameEditInput.setText(name);
        bdayEditInput.setText(bday);

    }

    public void saveChanges(View view){
        Intent intent = new Intent();


        if(!(nameEditInput.getText().toString().isEmpty() || bdayEditInput.getText().toString().isEmpty())){
            intent.putExtra("new", false);
            intent.putExtra("changedName", nameEditInput.getText().toString());
            intent.putExtra("changedBirthday", bdayEditInput.getText().toString());
            intent.putExtra("posisjon", this.posisjon);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(view.getContext(), "Kan ikke ha blankt navn eller dato", Toast.LENGTH_LONG).show();
        }

    }

    public void cancelChanges(View view){
        setResult(RESULT_CANCELED);
        finish();
    }


}