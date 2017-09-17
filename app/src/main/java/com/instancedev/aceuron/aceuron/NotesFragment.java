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


    //refresh the ArrayAdapter in the LiestView after editing its values
    public void refreshArrayAdapter(){
        listViewAdapter.clear();
        listViewAdapter.addAll(TextUtil.getAllNotes(getContext(), false));
        listViewAdapter.notifyDataSetChanged();
    }

    //
    public void init(){
        //initializing ArrayList and ArrayAdapter
        listViewItems = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.array_adapter_design, R.id.list_text_color, listViewItems){

        };

        //retrieving views from the xml file
        addNote = (Button) view.findViewById(R.id.AddNoteButton);
        listview = (ListView) view.findViewById(R.id.listView);


        //Add all public notes to the ArrayAdapter to display their title
        List<TextUtil.Note> notes = TextUtil.getAllNotes(this.getContext(), false);
        for(TextUtil.Note n : notes) {
            listViewAdapter.add(n);
        }

        //Add ArrayAdapter to ListView
        listview.setAdapter(listViewAdapter);

        //pass this fragment to EditNoteActivity and NewNoteActivity
        //so it can refresh the array adapter after updating its values
        EditNoteActivity.passFragment(this);
        NewNoteActivity.passFragment(this);


        //addNote Button -> startActivity(NewNoteActivity)
        addNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getActivity(), NewNoteActivity.class));
            }
        });



        //If you click on a item of the ArrayAdapter it directs you to EditNoteActivity where you can
        //see what you have previously written and saved
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextUtil.Note n = (TextUtil.Note) parent.getItemAtPosition(position);
                EditNoteActivity.setText(n.title, n.content);
                EditNoteActivity.setNoteID(n.id);
                startActivity(new Intent(getActivity(), EditNoteActivity.class));
            }
        });

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
