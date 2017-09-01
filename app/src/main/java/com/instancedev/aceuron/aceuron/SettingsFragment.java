package com.instancedev.aceuron.aceuron;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rapha on 01.09.2017.
 */

public class SettingsFragment extends Fragment {

    //you can see the automatically created annotations in ViewFragment.java

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        //if true, adds the swipe activity to container
        //if false it sets the size etc and you can add it later
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.swipe_activity, container, false);

        return rootView;
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
}
