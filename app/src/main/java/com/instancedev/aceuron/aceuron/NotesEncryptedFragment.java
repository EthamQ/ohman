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
public class NotesEncryptedFragment extends Fragment {

    View view;

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
        init();
        return view;
    }

    public void createPasswordDialog(){

        if(this.isVisible()) {
            LayoutInflater li = LayoutInflater.from(getContext());
            View promptsView = li.inflate(R.layout.activity_password, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            //builder.setTitle("title");
            builder.setCancelable(true);
            //builder.setMessage("message");
            builder.setView(promptsView);
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
    public void onResume() {
        super.onResume();
        createPasswordDialog();
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