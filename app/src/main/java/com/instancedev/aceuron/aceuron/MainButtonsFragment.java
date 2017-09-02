package com.instancedev.aceuron.aceuron;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainButtonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainButtonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainButtonsFragment extends Fragment {

    // View in order to access the Buttons, see onCreateView
    View view;

    // Buttons added in fragment_main_buttons.xml
    // instantiated in public void onCreateView() with init()
    Button RemindMe;
    Button Schedule;
    Button NotesButton;
    Button NotesConf;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainButtonsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainButtonsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainButtonsFragment newInstance(String param1, String param2) {
        MainButtonsFragment fragment = new MainButtonsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    //TODO: Problem:
    //Why doesn't the constructor for the intent work? Does it simply not find the NotesActivity class
    //(maybe because i didn't declare it correctly in the manifest?, please check!)
    //Or an intent has to be set in an Activity (SwipeActivity) and not in a fragment. (i tried the same
    //code in SwipeActivity but still didn't work)
    public void init(){
        Button remindMe = (Button) view.findViewById(R.id.RemindMeButton) ;
        Button schedule = (Button) view.findViewById(R.id.ScheduleButton);
        Button notesButton = (Button) view.findViewById(R.id.NotesButton);
        Button notesConf = (Button) view.findViewById(R.id.NotesConfidentialButton);

        notesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(this, NotesActivity.class));

            }
        });

        // TODO Add functionality to buttons
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_buttons, container, false);
        init();
        return inflater.inflate(R.layout.fragment_main_buttons, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
