package com.instancedev.aceuron.aceuron;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewNoteActivity extends AppCompatActivity {

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);

        final boolean encrypted = getIntent().getBooleanExtra("encrypted", false);

        saveButton = (Button) findViewById(R.id.SaveButton);

        //retrieve title and text from the two EditText views
        final EditText titleEditText = (EditText) findViewById(R.id.addTitle);
        final EditText contentEditText = (EditText) findViewById(R.id.addContent);

        //click listener saveButton
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            // TODO encryption checkbox
            if(!titleEditText.getText().toString().isEmpty()){
                //Add the note to the database
                TextUtil.insertNote(
                        getApplicationContext(),
                        titleEditText.getText().toString(),
                        contentEditText.getText().toString(),
                        encrypted
                );

                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Choose a title", Toast.LENGTH_LONG).show();
            }
            }
        });
    }
}