package setmatch.setmatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import setmatch.setmatch.fragments.MatchFragment;
import setmatch.setmatch.fragments.ProfileFragment;
import setmatch.setmatch.fragments.StatsFragment;

public class MainActivity extends AppCompatActivity implements MatchFragment.OnFragmentInteractionListener,
                                                                StatsFragment.OnFragmentInteractionListener{


    private FragmentPagerAdapter adapterViewPager;


    public static class PagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList;

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            fragmentList = new ArrayList<Fragment>();
            fragmentList.add(MatchFragment.newInstance("LSDKJFLSDKF", "L"));
            fragmentList.add(StatsFragment.newInstance("dddd", "q"));
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return fragmentList.size();
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            if(position < fragmentList.size()) {
                return fragmentList.get(position);
            } else {
                return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    public void onFragmentInteraction(Uri uri){

    }

    protected void launchNewWorkout(View v){
        Intent intent = new Intent(this, NewWorkoutActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new PagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);



    }

}
