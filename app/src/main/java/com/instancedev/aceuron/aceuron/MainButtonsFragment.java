package com.instancedev.aceuron.aceuron;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainButtonsFragment extends Fragment {

    View view;

    Button remindMe;
    Button schedule;
    Button notesButton;
    Button notesConf;

    private OnFragmentInteractionListener mListener;

    public MainButtonsFragment() {
        // Required empty public constructor
    }

    public static MainButtonsFragment newInstance() {
        return new MainButtonsFragment();
    }

    public void init(){
        if (view == null) {
            return;
        }

        remindMe = (Button) view.findViewById(R.id.RemindMeButton) ;
        schedule = (Button) view.findViewById(R.id.ScheduleButton);
        notesButton = (Button) view.findViewById(R.id.NotesButton);
        notesConf = (Button) view.findViewById(R.id.NotesConfidentialButton);

        notesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO open NotesFragment
                ((SwipeActivity) getActivity()).setCurrentItem(2, true);
            }
        });

        // TODO Add functionality to buttons
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_buttons, container, false);
        init();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
