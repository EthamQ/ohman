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


    public static void setText(String title, String content){
        TITLE = title;
        CONTENT = content;

    }



    public static void setNote(int noteID){
        id = noteID;
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


        Button save = (Button) findViewById(R.id.SaveButtonEdit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextUtil.editNote(getApplicationContext(), id, titleEditText.getText().toString(), contentEditText.getText().toString());
                finish();

            }
        });
    }
}
