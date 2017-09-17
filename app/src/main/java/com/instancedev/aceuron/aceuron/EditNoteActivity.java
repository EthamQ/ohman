package com.instancedev.aceuron.aceuron;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class EditNoteActivity extends AppCompatActivity {

    private static String TITLE = "Default";
    private static String CONTENT = "Default";
    static int id;


    //Retrieve arguments from the NotesFragment
    public static void setText(String title, String content){
        TITLE = title;
        CONTENT = content;

    }

    public static void setNoteID(int noteID){
        id = noteID;
    }

    //pass a fragment to this class in order to access and update it
    //(pass and refresh methods may be moved to a Util class later)
    static NotesFragment fragment;
    public static void passFragment(NotesFragment nf){
        fragment = nf;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        //Display the previous saved title and content
        final EditText titleEditText = (EditText) findViewById(R.id.editTitle);
        titleEditText.setText(TITLE);
        final EditText contentEditText = (EditText) findViewById(R.id.editContent);
        contentEditText.setText(CONTENT);


        //set up the click listener for the save button
        Button save = (Button) findViewById(R.id.SaveButtonEdit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit title and content from the note accessed via its id
                //NotesFragment uses setNoteID
                TextUtil.editNote(getApplicationContext(), id, titleEditText.getText().toString(), contentEditText.getText().toString());

                //refresh the ListView ad close this activity
                fragment.refreshArrayAdapter();
                finish();

            }
        });
    }
}
