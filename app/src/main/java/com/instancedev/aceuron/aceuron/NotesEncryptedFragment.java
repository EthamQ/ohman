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

    //Access activity_password.xml
    View passwordView;

    Button addNote;
    ListView listview;

    ArrayList listViewItems;
    ArrayAdapter listViewAdapter;

    final boolean encrypted = true;



    public NotesEncryptedFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        view = inflater.inflate(R.layout.fragment_notes_encrypted, container, false);
        passwordView = inflater.inflate(R.layout.activity_password, null);
        init();
        return view;
    }

    public void createPasswordDialog(){

        if(this.isVisible()) {
            LayoutInflater li2 = LayoutInflater.from(getContext());
            View promptsView2 = li2.inflate(R.layout.activity_password, null);

            final EditText enterPW = (EditText) passwordView.findViewById((R.id.enter_password));
            EditText setPW = (EditText) passwordView.findViewById((R.id.set_password));
            Button enterPWButton = (Button) passwordView.findViewById(R.id.enter_password_button);
            Button setPWButton = (Button) passwordView.findViewById(R.id.set_password_button);

            enterPWButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtil.passwordExists(getContext())){
                    if (TextUtil.loadPassword(getContext()).equals(enterPW.getText().toString())) {
                        //TODO: show notes
                    } else {
                        Toast.makeText(getContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                        Toast.makeText(getContext(), "Set a password first", Toast.LENGTH_SHORT).show();
                    }
                    }
                    //else{
                    //    Toast.makeText(getContext(), "Set a password first", Toast.LENGTH_SHORT).show();
                    //}

               // }
            });




            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Authentification");
            builder.setCancelable(true);
            builder.setMessage("Password to confirm you're not some kind of weirdo who just wants to have a look at my private stuff");
            builder.setView(passwordView);
            AlertDialog a = builder.create();
            a.show();
        }
    }



    public static NotesEncryptedFragment newInstance(){
        return new NotesEncryptedFragment();
    }

    public void refreshArrayAdapter(){
        listViewAdapter.clear();
        listViewAdapter.addAll(TextUtil.getAllNotes(getContext(), encrypted));
        listViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void fragmentIsVisible() {
        createPasswordDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshArrayAdapter();
    }

    public void init(){
        listViewItems = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.array_adapter_design, R.id.list_text_color, listViewItems);

        addNote = (Button) view.findViewById(R.id.AddNoteButtonE);
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