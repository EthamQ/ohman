package com.instancedev.aceuron.aceuron;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    public NotesEncryptedFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        view = inflater.inflate(R.layout.fragment_notes_encrypted, container, false);
        init();
        return view;
    }

    public static NotesEncryptedFragment newInstance(){
        return new NotesEncryptedFragment();
    }

    public void init(){
        listViewItems = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, listViewItems);

        addNote = (Button) view.findViewById(R.id.AddNoteButtonE);
        listview = (ListView) view.findViewById(R.id.listViewE);

        addNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                NewNoteActivity.encrypt();
                startActivity(new Intent(getActivity(), NewNoteActivity.class));
            }
        });

        List<TextUtil.Note> notes = TextUtil.getAllNotes(this.getContext(), true);
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