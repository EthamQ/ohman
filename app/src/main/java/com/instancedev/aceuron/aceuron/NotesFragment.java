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


public class NotesFragment extends Fragment implements FragmentInterface {

    View view;

    Button addNote;
    ListView listview;

    ArrayList listViewItems;
    ArrayAdapter listViewAdapter;

    final boolean encrypted = false;

    public NotesFragment(){

    }

    @Override
    public void fragmentIsVisible() {

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
        listViewAdapter.addAll(TextUtil.getAllNotes(getContext(), encrypted));
        listViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();


        refreshArrayAdapter();
    }

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
        List<TextUtil.Note> notes = TextUtil.getAllNotes(this.getContext(), encrypted);
        for(TextUtil.Note n : notes) {
            listViewAdapter.add(n);
        }

        //Add ArrayAdapter to ListView
        listview.setAdapter(listViewAdapter);

        //addNote Button -> startActivity(NewNoteActivity)
        addNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    Intent intent = new Intent(getActivity(), NewNoteActivity.class);
                    intent.putExtra("encrypted", encrypted);
                    startActivity(intent);
            }
        });

        //If you click on a item of the ArrayAdapter it directs you to EditNoteActivity where you can
        //see what you have previously written and saved
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextUtil.Note n = (TextUtil.Note) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), EditNoteActivity.class);
                intent.putExtra("encrypted", encrypted);
                intent.putExtra("id", n.id);
                intent.putExtra("title", n.title);
                intent.putExtra("content", n.content);
                startActivity(intent);
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}