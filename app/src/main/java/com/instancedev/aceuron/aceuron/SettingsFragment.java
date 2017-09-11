package com.instancedev.aceuron.aceuron;

import android.net.Uri;
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

    View view;

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

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    public void init(){
        setStart = (Button) view.findViewById(R.id.SetStartButton);
        setDesign = (Button) view.findViewById(R.id.SetDesignButton);
        setPush = (Button) view.findViewById(R.id.SetPushButton);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}




