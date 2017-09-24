package com.instancedev.aceuron.aceuron;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ClassScheduleFragment extends Fragment implements FragmentInterface {

    View view;

    GridView gridView;

    public ClassScheduleFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        view = inflater.inflate(R.layout.fragment_class_schedule, container, false);
        init();
        return view;
    }

    public static ClassScheduleFragment newInstance(){
        return new ClassScheduleFragment();
    }

    public void init(){
        gridView = (GridView) view.findViewById(R.id.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO open dialog that prompts for details or edit
                // first ask on the bottom whether to show details or edit
                switch (position) {
                    case 0:
                    case 6:
                    case 12:
                    case 18:
                    case 24:
                        // TODO don't prompt, those are weekday items
                        break;
                    default:
                        // TODO prompt
                }

                // TODO hmm which one is better, switch or modulo

                if (position % 6 == 0) {
                    // TODO don't prompt
                } else {
                    // TODO prompt
                }
            }
        });
    }

    @Override
    public void fragmentIsVisible() {

    }
}
