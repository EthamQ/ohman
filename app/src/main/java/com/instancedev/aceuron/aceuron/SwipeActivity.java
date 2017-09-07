package com.instancedev.aceuron.aceuron;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


public class SwipeActivity extends FragmentActivity implements
        MainButtonsFragment.OnFragmentInteractionListener {

    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    public void onFragmentInteraction(Uri uri){
        // TODO
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_activity);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    public void setCurrentItem (int item, boolean smoothScroll) {
        mPager.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        /**
         *
         * @param position
         * @return
         * position 0: return MainButtonsFragment
         * position 1: return ViewFragment
         * position 2: return SettingsFragment
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MainButtonsFragment.newInstance();
                case 1:
                    return SettingsFragment.newInstance();
                case 2:
                    return NotesFragment.newInstance();
                case 3:
                case 4:
                    break; // TODO
            }
            return new Fragment();
        }
    }

}




