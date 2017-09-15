package com.instancedev.aceuron.aceuron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    private static String TITLE = "Default";
    private static String CONTENT = "Default";


    public static void setText(String title, String content){
        TITLE = title;
        CONTENT = content;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        //Display the previous saved title and content
        EditText titleEditText = (EditText) findViewById(R.id.editTitle);
        titleEditText.setText(TITLE);
        EditText contentEditText = (EditText) findViewById(R.id.editContent);
        contentEditText.setText(CONTENT);
    }
}
