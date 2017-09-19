package com.instancedev.aceuron.aceuron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    //dispatchTouchEvent gets called before onTouchEvent therefore you couldn't
    //sweep down while touching a button that's why i overrode dispatchTouchEvent which handles
    //button events
    float y1, y2;
    @Override
    public boolean dispatchTouchEvent(MotionEvent touchevent){
        super.dispatchTouchEvent(touchevent);

        switch(touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:{
                y1 = touchevent.getY();
                break;
            }

            case MotionEvent.ACTION_UP:{
                y2 = touchevent.getY();
                if(y2 > y1){
                    finish();
                }
                break;
            }
        }

        return true;
    }
}