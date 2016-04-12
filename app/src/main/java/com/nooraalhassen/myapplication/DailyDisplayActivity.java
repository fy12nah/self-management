package com.nooraalhassen.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nooraalhassen.myapplication.adapters.ExerciseAdapter;
import com.nooraalhassen.myapplication.adapters.IllnessAdapter;
import com.nooraalhassen.myapplication.adapters.MealsAdapter;
import com.nooraalhassen.myapplication.adapters.MoodAdapter;
import com.nooraalhassen.myapplication.adapters.SleepAdapter;
import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Exercise;
import com.nooraalhassen.myapplication.model.Illness;
import com.nooraalhassen.myapplication.model.Meal;
import com.nooraalhassen.myapplication.model.Mood;
import com.nooraalhassen.myapplication.model.Physical;
import com.nooraalhassen.myapplication.model.Sleeping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DailyDisplayActivity extends AppCompatActivity {

    static ArrayList<String> dateList;
    static ArrayList<String> contentList;



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


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


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

        CheckBox chkPhys;
        CheckBox chkMeals;
        CheckBox chkExer;
        CheckBox chkSleep;
        CheckBox chkMood;
        CheckBox chkIllness;



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

            try {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);
                Date d = simpleDateFormat.parse(dateList.get(getArguments().getInt(ARG_SECTION_NUMBER)));

                DBmanager db = new DBmanager(getActivity());
                SharedPreferences preferences = getActivity().getSharedPreferences(Constants.sharedpreferencesId, 0);
                long user_id = preferences.getLong(Constants.userId, -1);

                RelativeLayout physLay = (RelativeLayout) rootView.findViewById(R.id.phyLayout);

                if (contentList.contains(Constants.phys)) {

                    Physical p = db.getPhysicalAtDate(d, user_id);

                    TextView tv1 = (TextView) rootView.findViewById(R.id.tvHeight);
                    TextView tv2 = (TextView) rootView.findViewById(R.id.tvWeight);

                    TextView weightText = (TextView) rootView.findViewById(R.id.weight);
                    TextView heightText = (TextView) rootView.findViewById(R.id.height);
                    TextView msg = (TextView) rootView.findViewById(R.id.msgPhysT);
                    TextView title = (TextView) rootView.findViewById(R.id.physicalTitle);


                    if (p == null){
                        title.setText(title.getText().toString()+" - No Data Entered");
                        tv1.setVisibility(View.GONE);
                        tv2.setVisibility(View.GONE);
                        weightText.setVisibility(View.GONE);
                        heightText.setVisibility(View.GONE);
                        msg.setVisibility(View.GONE);
                    }

                    else{

                        if (p.getWeight() == -1){
                            weightText.setText("No Data");
                            msg.setVisibility(View.GONE);
                        }
                        else weightText.setText(String.valueOf(p.getWeight()));

                        if (p.getHeight() == -1){
                            heightText.setText("No Data");
                            msg.setVisibility(View.GONE);
                        }
                        else heightText.setText(String.valueOf(p.getHeight()));


                        if (p.getHeight() != -1 && p.getWeight() != -1){

                            float bmi = p.getWeight() / (p.getHeight() * p.getHeight());
                            if (bmi <= 18.5 )
                            {
                                msg.setText("You are underweight! Need to gain weight");
                            }
                            else if (bmi > 18.5 && bmi <= 24.99){
                                msg.setText("You have normal weight");
                            }
                            else if (bmi > 24.99 && bmi <= 29.99){
                                msg.setText("You are overweight! Be Careful");
                            }
                            else if (bmi > 29.99){
                                msg.setText("You have reached Obesity!!");
                            }
                        }

                    }

                    physLay.setVisibility(View.VISIBLE);
                }
                else physLay.setVisibility(View.GONE);



                LinearLayout illnessLay = (LinearLayout) rootView.findViewById(R.id.illnessLayout);
                if (contentList.contains(Constants.ill)) {

                    ArrayList<Illness> p = db.getIllnessAtDate(d, user_id);

                    TextView title = (TextView) rootView.findViewById(R.id.illnessTitle);
                    ListView listview = (ListView) rootView.findViewById(R.id.listIllness);

                    if (p == null){
                        title.setText(title.getText().toString() + " - No Data Entered");
                    }

                    else{
                        title.setVisibility(View.GONE);
                        IllnessAdapter adapter = new IllnessAdapter(getActivity(), p);
                        listview.setAdapter(adapter);
                    }
                    illnessLay.setVisibility(View.VISIBLE);
                }
                else illnessLay.setVisibility(View.GONE);


                LinearLayout mealsLay = (LinearLayout) rootView.findViewById(R.id.mealsLayout);
                if (contentList.contains(Constants.meal)) {
                    ArrayList<Meal> p = db.getMealAtDate(d, user_id);

                    TextView title = (TextView) rootView.findViewById(R.id.mealTitle1);
                    ListView listview = (ListView) rootView.findViewById(R.id.listMeals);

                    if (p.isEmpty()){
                        title.setText(title.getText().toString() + " - No Data Entered");
                    }

                    else{
                        title.setVisibility(View.GONE);
                        MealsAdapter adapter = new MealsAdapter(getActivity(), p);
                        listview.setAdapter(adapter);
                    }
                    mealsLay.setVisibility(View.VISIBLE);
                }
                else mealsLay.setVisibility(View.GONE);


                LinearLayout SlpLay = (LinearLayout) rootView.findViewById(R.id.slpLayout);
                if (contentList.contains(Constants.sleep)) {
                    ArrayList<Sleeping> p = db.getSleepingAtDate(d, user_id);

                    TextView title = (TextView) rootView.findViewById(R.id.sleepTitle1);
                    ListView listview = (ListView) rootView.findViewById(R.id.listSleep);

                    if (p == null){
                        title.setText(title.getText().toString() + " - No Data Entered");
                    }

                    else{
                        title.setVisibility(View.GONE);
                        SleepAdapter adapter = new SleepAdapter(getActivity(), p);
                        listview.setAdapter(adapter);
                    }
                    SlpLay.setVisibility(View.VISIBLE);
                }
                else SlpLay.setVisibility(View.GONE);


                LinearLayout exerLay = (LinearLayout) rootView.findViewById(R.id.exerciseLayout);
                if (contentList.contains(Constants.exer)) {

                    ArrayList<Exercise> p = db.getExerciseAtDate(d, user_id);

                    ListView listview = (ListView) rootView.findViewById(R.id.listExer);
                    TextView title = (TextView) rootView.findViewById(R.id.exerTitle1);


                    if (p == null){
                        title.setText(title.getText().toString() + " - No Data Entered");
                    }

                    else{
                        title.setVisibility(View.GONE);
                        ExerciseAdapter adapter = new ExerciseAdapter(getActivity(), p);
                        listview.setAdapter(adapter);
                    }
                    exerLay.setVisibility(View.VISIBLE);
                }
                else exerLay.setVisibility(View.GONE);


                LinearLayout moodLay = (LinearLayout) rootView.findViewById(R.id.moodLay);
                if (contentList.contains(Constants.mood_s)) {
                    ArrayList<Mood> p = db.getMoodAtDate(d, user_id);

                    TextView title = (TextView) rootView.findViewById(R.id.moodTitle1);
                    ListView listview = (ListView) rootView.findViewById(R.id.listMood);


                    if (p == null){
                        title.setText(title.getText().toString() + " - No Data Entered");
                    }

                    else{
                        title.setVisibility(View.GONE);
                        MoodAdapter adapter = new MoodAdapter(getActivity(), p);
                        listview.setAdapter(adapter);

                    }
                    moodLay.setVisibility(View.VISIBLE);
                }
                else moodLay.setVisibility(View.GONE);


                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            }
            catch(ParseException e){

            }
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
            return PlaceholderFragment.newInstance(position );
        }

        @Override
        public int getCount() {

            return dateList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            String s = dateList.get(position);
            return s.substring(0,s.length() - 5);
        }
    }

    public static Intent createIntent(Context context, ArrayList<String> dateList, ArrayList<String> contentList){
        Intent intent = new Intent(context, DailyDisplayActivity.class);
        intent.putExtra("Dates", dateList);
        intent.putExtra("Contents", contentList);

        return intent;
    }
}
