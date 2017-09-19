package com.instancedev.aceuron.aceuron;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class EditNoteActivity extends AppCompatActivity {

    Button save;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        final int id = getIntent().getIntExtra("id", 0);
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        //Display the previous saved title and content
        final EditText titleEditText = (EditText) findViewById(R.id.editTitle);
        titleEditText.setText(title);
        final EditText contentEditText = (EditText) findViewById(R.id.editContent);
        contentEditText.setText(content);

        save = (Button) findViewById(R.id.SaveButtonEdit);
        delete = (Button) findViewById(R.id.DeleteButtonEdit);

        //set up the click listener for the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!titleEditText.getText().toString().isEmpty()) {
                    TextUtil.editNote(getApplicationContext(), id, titleEditText.getText().toString(), contentEditText.getText().toString());
                    finish();
                }
                else Toast.makeText(getApplicationContext(), "Choose a title", Toast.LENGTH_LONG).show();
            }
        });

        //set up the click listener for the delete button
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //AlertDialog set up
                new AlertDialog.Builder(EditNoteActivity.this)
                        .setTitle("Sure?")
                        .setMessage("Do you really want to delete this note?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                TextUtil.deleteNote(getApplicationContext(), id);
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }
}