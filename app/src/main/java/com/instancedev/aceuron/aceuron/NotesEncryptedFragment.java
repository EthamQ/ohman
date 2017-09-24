package com.instancedev.aceuron.aceuron;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotesEncryptedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotesEncryptedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesEncryptedFragment extends Fragment implements FragmentInterface {

    View view;

    //activity_password.xml
    View passwordView;

    //fragment_notes_encrypted.xml
    Button addNote;
    Button encryptAgain;
    ListView listview;

    ArrayList listViewItems;
    ArrayAdapter listViewAdapter;

    final boolean encrypted = true;

    //true when the correct password has been entered
    boolean correctPassword = false;

    public NotesEncryptedFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        view = inflater.inflate(R.layout.fragment_notes_encrypted, container, false);
        passwordView = inflater.inflate(R.layout.activity_password, null);
        return view;
    }

    /**
     * Displays an alert dialog that asks you to enter the correct password or gives you the
     * possibility to set a new password
     **/
    AlertDialog alertDialog;
    public void createPasswordDialog(){
            //retrieve EditText and Buttons from activity_password.xml
            final EditText enterPW = (EditText) passwordView.findViewById((R.id.enter_password));
            final EditText setPW = (EditText) passwordView.findViewById((R.id.set_password));
            Button enterPWButton = (Button) passwordView.findViewById(R.id.enter_password_button);
            Button setPWButton = (Button) passwordView.findViewById(R.id.set_password_button);

        //Enter the password and check if it is correct when the enterPWButton is clicked
            enterPWButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = ""+TextUtil.passwordExists(getContext());
                    Toast.makeText(getContext(), "Password exists: "+ s, Toast.LENGTH_SHORT).show();
                    if(TextUtil.passwordExists(getContext())){
                    if (TextUtil.loadPassword(getContext()).equals(enterPW.getText().toString())) {
                        correctPassword = true;
                        Toast.makeText(getContext(), "Correct password", Toast.LENGTH_SHORT).show();
                        init();
                        refreshArrayAdapter();
                        alertDialog.cancel();
                    } else {
                        Toast.makeText(getContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                        Toast.makeText(getContext(), "Set a password first", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(getContext(), "Password: "+ TextUtil.loadPassword(getContext()), Toast.LENGTH_SHORT).show();
                    }

            });

        //Set a new password when setPWButton is clicked
            setPWButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtil.passwordExists(getContext())){
                        TextUtil.savePassword(setPW.getText().toString(), getContext());
                    }
                    Toast.makeText(getContext(), "Password: "+ TextUtil.loadPassword(getContext()), Toast.LENGTH_SHORT).show();
                }
            });

        //Create and show the alert dialog that asks for your password
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Authentification");
            builder.setCancelable(true);
            builder.setMessage("Password to confirm you're not some kind of weirdo who just wants to have a look at my private stuff");
            builder.setView(passwordView);
           alertDialog = builder.create();
            alertDialog.show();


    }

    public static NotesEncryptedFragment newInstance(){
        return new NotesEncryptedFragment();
    }

    public void refreshArrayAdapter(){
        listViewAdapter.clear();
        listViewAdapter.addAll(TextUtil.getAllNotes(getContext(), encrypted));
        listViewAdapter.notifyDataSetChanged();
    }

    //invoked in SwipeActivity.class when this Fragment is currently visible
    //shows the password dialog if boolean correctPassword is false
    @Override
    public void fragmentIsVisible() {
        if(!correctPassword){
            createPasswordDialog();
        }
    }

    //refresh the page
    @Override
    public void onResume() {
        super.onResume();
        if(correctPassword){
            refreshArrayAdapter();
        }
    }

    //initiate ArrayList, ArrayAdapter, ListView, addNoteButton(+OnClick)
    public void init(){
        listViewItems = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.array_adapter_design, R.id.list_text_color, listViewItems);

        addNote = (Button) view.findViewById(R.id.add_note_button_e);
        listview = (ListView) view.findViewById(R.id.listViewE);

        addNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), NewNoteActivity.class);
                intent.putExtra("encrypted", encrypted);
                startActivity(intent);
            }
        });

        List<TextUtil.Note> notes = TextUtil.getAllNotes(this.getContext(), encrypted);
        for(TextUtil.Note n : notes) {
            listViewAdapter.add(n.title);
        }

        listview.setAdapter(listViewAdapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}