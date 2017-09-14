package com.instancedev.aceuron.aceuron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    static String title = "Default";
    static String text = "Default";

    public static void setText(String tit, String content){
        title = tit;
        text = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        final EditText titleEditText = (EditText) findViewById(R.id.editTexte);
        titleEditText.setText(text);
        final EditText titleEditText2 = (EditText) findViewById(R.id.editText2e);
        titleEditText2.setText(title);
    }
}
