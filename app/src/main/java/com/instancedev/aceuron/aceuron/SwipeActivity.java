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
        MainButtonsFragment.OnFragmentInteractionListener, CalendarFragment.OnFragmentInteractionListener,
NotesFragment.OnFragmentInteractionListener{

    private static final int NUM_PAGES = 4;
    private static final int FIRST_PAGE = 1;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    public int getCurrentItem() {
        return mPager.getCurrentItem();
    }

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

        //First screen to be displayed on the screen
        setCurrentItem(FIRST_PAGE, true);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int position) {
                FragmentInterface fragment = (FragmentInterface) mPagerAdapter.instantiateItem(mPager, position);
                if (fragment != null) {
                    fragment.fragmentIsVisible();
                }
            }

            @Override
            public void onPageScrollStateChanged(final int position) {
            }
        });
    }

    public void setCurrentItem (int item, boolean smoothScroll) {
        mPager.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == FIRST_PAGE) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        }
        else if(mPager.getCurrentItem() == 0){
            mPager.setCurrentItem(1);
        }
        else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }



        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return CalendarFragment.newInstance();
                case 1:
                    return OverviewFragment.newInstance();
                case 2:
                    return NotesFragment.newInstance();
                case 3:
                    return NotesEncryptedFragment.newInstance();


            }
            return new Fragment();
        }
    }
}