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
    static boolean encrypted = false;

    //pass a fragment to this class in order to access and update it
    //(pass and refresh methods may be moved to a Util class later)
    static NotesFragment fragment;
    public static void passFragment(NotesFragment nf){
        fragment = nf;
    }

    //method is invoked if this class is accessed through NotesEncryptedFragment
    public static void encrypt(){
        encrypted = true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);

        saveButton = (Button) findViewById(R.id.SaveButton);

        //retrieve title and text from the two EditText views
        final EditText titleEditText = (EditText) findViewById(R.id.addTitle);
        final EditText contentEditText = (EditText) findViewById(R.id.addContent);


        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO encryption checkbox
                //set up the arguments before inserting the note into the database
                NotesDBUtil notesDB = new NotesDBUtil(getApplicationContext());
                String table = "notes";
                String[] selArgs = new String[]{titleEditText.getText().toString()};

                //Add the note to the database
                    TextUtil.insertNote(
                            getApplicationContext(),
                            titleEditText.getText().toString(),
                            contentEditText.getText().toString(),
                            encrypted
                    );

                    //refresh the NotesFragment and close this activity
                    fragment.refreshArrayAdapter();
                    finish();
                }
        });
    }
}
