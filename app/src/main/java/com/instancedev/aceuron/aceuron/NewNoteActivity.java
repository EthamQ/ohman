package com.instancedev.aceuron.aceuron;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewNoteActivity extends AppCompatActivity {

    Button saveButton;
    static boolean encrypted = false;


    //method is invoked if this class is accessed through NotesEncryptedFragment
    public static void encrypt(){
        encrypted = true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);

        saveButton = (Button) findViewById(R.id.SaveButton);

        final EditText titleEditText = (EditText) findViewById(R.id.editText2);
        final EditText contentEditText = (EditText) findViewById(R.id.editText);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO encryption checkbox
                TextUtil.insertNote(
                        getApplicationContext(),
                        titleEditText.getText().toString(),
                        contentEditText.getText().toString(),
                        encrypted
                );
                finish();


            }
        });
    }
}
