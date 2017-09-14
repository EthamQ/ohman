package com.instancedev.aceuron.aceuron;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class OverviewFragment extends Fragment {

   float y1, y2;
    float x1, x2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_overview, container, false);



        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent touchevent) {
                switch (touchevent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    {
                        x1 = touchevent.getX();
                        y1 = touchevent.getY();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        x2 = touchevent.getX();
                        y2 = touchevent.getY();
                    }

                        // if UP to Down sweep event on screen
                        if (y1 < y2)
                        {
                            //Toast.makeText(getActivity(), "Weekly schedule is supposed to appear", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(getActivity(), WeeklyScheduleActivity.class);
                            startActivity(i);
                        }

                        //if Down to UP sweep event on screen
                        if (y1 > y2) {
                            Intent i = new Intent(getActivity(), SettingsActivity.class);
                            startActivity(i);
                        }
                        break;
                    }
                return true;
            }
        });


        return rootView;
    }




    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }
}
