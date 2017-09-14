package com.instancedev.aceuron.aceuron;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NotesFragment extends Fragment {

    View view;

    Button addNote;
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


        addNote = (Button) view.findViewById(R.id.AddNoteButton);
        listview = (ListView) view.findViewById(R.id.listView);



        addNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getActivity(), NewNoteActivity.class));
            }
        });

        //added getAllNotes constructor arguments
        List<TextUtil.Note> notes = TextUtil.getAllNotes(this.getContext(), false);
        for(TextUtil.Note n : notes) {
            listViewAdapter.add(n.title);
        }


        //Added by Raphael
        listview.setAdapter(listViewAdapter);
        //
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                List<TextUtil.Note> notesd = TextUtil.getNoteByTitle(getContext(), parent.getItemAtPosition(position).toString());
                TextUtil.Note n = notesd.get(0);
                String text = n.content;
                String title = n.title;

                EditNoteActivity.setText(title, text);
              startActivity(new Intent(getActivity(), EditNoteActivity.class));
            }
        });


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
