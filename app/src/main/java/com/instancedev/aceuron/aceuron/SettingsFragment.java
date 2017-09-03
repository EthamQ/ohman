package com.instancedev.aceuron.aceuron;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by rapha on 01.09.2017.
 */

public class SettingsFragment extends Fragment {

    //you can see the automatically created annotations in ViewFragment.java

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //Define Buttons. Created in init()
    Button setStart;
    Button setDesign;
    Button setPush;

    public SettingsFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        init();
        return view;

    }


    public static SettingsFragment newInstance(String param1, String param2){
        SettingsFragment fragment = new SettingsFragment();
        //Bundles are used to pass arguments and information between different activities
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    View view;
    public void init(){
        setStart = (Button) view.findViewById(R.id.SetStartButton);
        setDesign = (Button) view.findViewById(R.id.SetDesignButton);
        setPush = (Button) view.findViewById(R.id.SetPushButton);

    }
}
