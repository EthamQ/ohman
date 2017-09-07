package com.instancedev.aceuron.aceuron;

import android.content.Intent;
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


public class NotesFragment extends Fragment {

    View view;

    Button addnote;
    ListView listview;

    ArrayList listViewItems;
    ArrayAdapter listViewAdapter;

    public NotesFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        view = inflater.inflate(R.layout.fragment_notes, container, false);
        init();
        return view;
    }

    public static NotesFragment newInstance(){
        return new NotesFragment();
    }

    public void init(){
        listViewItems = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, listViewItems);


        addnote = (Button) view.findViewById(R.id.button);
        listview = (ListView) view.findViewById(R.id.listView);

        addnote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getActivity(), NewNoteActivity.class));
            }
        });

        List<TextUtil.Note> notes = TextUtil.getAllNotes();
        for(TextUtil.Note n : notes) {
            listViewAdapter.add(n.title);
        }
    }
}
