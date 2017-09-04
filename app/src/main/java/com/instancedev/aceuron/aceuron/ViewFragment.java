package com.instancedev.aceuron.aceuron;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ViewFragment extends Fragment {

    // TODO Possibly not needed anymore, delete

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.swipe_activity, container, false);

        return rootView;
    }

    public static ViewFragment newInstance() {
        return new ViewFragment();
    }
}
