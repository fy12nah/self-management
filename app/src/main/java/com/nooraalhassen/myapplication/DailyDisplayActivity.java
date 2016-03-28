package com.nooraalhassen.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DailyDisplayActivity extends AppCompatActivity {

    ArrayList<String> dateList;
    ArrayList<String> contentList;

    CheckBox chkPhys;
    CheckBox chkMeals;
    CheckBox chkExer;
    CheckBox chkSleep;
    CheckBox chkMood;
    CheckBox chkIllness;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();

        dateList = intent.getStringArrayListExtra("Dates");
        contentList = intent.getStringArrayListExtra("Contents");

        chkPhys = (CheckBox) findViewById(R.id.inspPhys);
        chkMeals = (CheckBox) findViewById(R.id.inspMeals);
        chkExer = (CheckBox) findViewById(R.id.inspExer);
        chkSleep = (CheckBox) findViewById(R.id.inspSleep);
        chkMood = (CheckBox) findViewById(R.id.inspMood);
        chkIllness = (CheckBox) findViewById(R.id.inspIllness);

        if (chkPhys.isChecked())


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daily_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_daily_display, container, false);


            RelativeLayout physLay = (RelativeLayout) rootView.findViewById(R.id.phyLayout);
            //if (chkPhys.isChecked()) physLay.setVisibility(View.VISIBLE);
            //else physLay.setVisibility(View.INVISIBLE);

            RelativeLayout illnessLay = (RelativeLayout) rootView.findViewById(R.id.illnessLayout);
            //if (chkillness.isChecked()) illnessLay.setVisibility(View.VISIBLE);
            //else illnessLay.setVisibility(View.INVISIBLE);

            RelativeLayout mealsLay = (RelativeLayout) rootView.findViewById(R.id.mealsLayout);
            //if (chkMeals.isChecked()) mealsLay.setVisibility(View.VISIBLE);
            //else mealsLay.setVisibility(View.INVISIBLE);

            RelativeLayout SlpLay = (RelativeLayout) rootView.findViewById(R.id.SleepLayout);
            //if (chkSleep.isChecked()) physLay.setVisibility(View.VISIBLE);
            //else physLay.setVisibility(View.INVISIBLE);

            RelativeLayout exerLay = (RelativeLayout) rootView.findViewById(R.id.exercisesLayout);
            //if (chkExer.isChecked()) exerLay.setVisibility(View.VISIBLE);
            //else exerLay.setVisibility(View.INVISIBLE);

            RelativeLayout moodLay = (RelativeLayout) rootView.findViewById(R.id.moodLayout);
            //if (chkMood.isChecked()) moodLay.setVisibility(View.VISIBLE);
            //else moodLay.setVisibility(View.INVISIBLE);


            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {

            return dateList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            //Log.d("Noora", dateList.get(position));
            return dateList.get(position);
        }
    }

    public static Intent createIntent(Context context, ArrayList<String> dateList, ArrayList<String> contentList){
        Intent intent = new Intent(context, DailyDisplayActivity.class);
        intent.putExtra("Dates", dateList);
        intent.putExtra("Contents", contentList);

        return intent;
    }
}
