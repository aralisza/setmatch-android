package setmatch.setmatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import setmatch.setmatch.fragments.MyProfileFragment;
import setmatch.setmatch.fragments.ProfileFragment;
import setmatch.setmatch.fragments.StatsFragment;

public class MainActivity extends AppCompatActivity implements MyProfileFragment.OnFragmentInteractionListener,
        StatsFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener {

    ArrayList<JSONObject> matches = new ArrayList<>();
    JSONObject curmatch = new JSONObject();


    private FragmentPagerAdapter adapterViewPager;


    public static class PagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList;

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            fragmentList = new ArrayList<Fragment>();
            fragmentList.add(MyProfileFragment.newInstance("ksdjflk", "lskdfj"));
            //fragmentList.add(StatsFragment.newInstance("dddd", "q"));
            fragmentList.add(ProfileFragment.newInstance("dddd", "q"));

        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return fragmentList.size();
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            if (position < fragmentList.size()) {
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

    public void onFragmentInteraction(Uri uri) {

    }

    protected void launchNewWorkout(View v) {

        // create class object
        GpsPosition gps = new GpsPosition(this.getApplicationContext());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Log.i(String.valueOf(latitude), String.valueOf(longitude));
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        Intent intent = new Intent(this, NewWorkoutActivity.class);
        startActivity(intent);
    }

    public class ProfileLoadTask extends AsyncTask<Void, Void, JSONObject> {


        private Context mContext;

        ProfileLoadTask(Context context) {
            mContext = context;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject msg = new JSONObject();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.shared_prefs_file_key), Context.MODE_PRIVATE);
            try {
                msg.put("email", sharedPreferences.getString(getString(R.string.saved_email_field), ""));
                msg.put("token", sharedPreferences.getString(getString(R.string.saved_token_field), ""));
                Log.i("LKSDJFLSKDJFLSKF", msg.toString());

                return NetworkManager.serverResponsePost(msg, UrlBuilder.getProfileEndpoint());
            } catch (Exception e) {
                Log.i("LSKDJFLSKJF", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            if (result != null) {
                try {
                    CharSequence text;
                    if (result.get("result").toString().equals("Success")) {
                        text = "Profile Access success";
                        updateProfile(result);
                        Log.i("LSKDJFLSKJDFLS", result.toString());
                        //((Activity)mContext).finish();


                    } else {
                        Log.i("LKSJDFLKSJDF", result.toString());
                        text = "Profile Access failure";
                    }
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                } catch (Exception e) {
                    Log.i("APPLICATION THING", "Registration Failure");
                }

            } else {
                //TODO: properly identify this error
                Log.i("SLDKJFLSDKJFLSKDJF", "NETWORKMANAGER NOT WORKING");
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, "Check Network Connection", duration);
                toast.show();
            }
        }

    }

    public class CheckTask extends AsyncTask<Void, Void, JSONObject> {


        private Context mContext;

        CheckTask(Context context) {
            mContext = context;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject msg = new JSONObject();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.shared_prefs_file_key), Context.MODE_PRIVATE);
            try {
                msg.put("email", sharedPreferences.getString(getString(R.string.saved_email_field), ""));
                msg.put("token", sharedPreferences.getString(getString(R.string.saved_token_field), ""));
                msg.put("id", curmatch.get("_id"));



                return NetworkManager.serverResponsePost(msg, UrlBuilder.getCheckEndpoint());
            } catch (Exception e) {
                Log.i("Not good", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            if (result != null) {
                try {
                    CharSequence text = "";
                    if (result.get("result").toString().equals("Success")) {
                        if (result.get("match").equals("Yes"))
                            text = "You got a match!";
                        updateProfile(result);
                        Log.i("LSKDJFLSKJDFLS", result.toString());
                        //((Activity)mContext).finish();


                    } else {
                        Log.i("LKSJDFLKSJDF", result.toString());
                        text = "Profile Access failure";
                    }
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                } catch (Exception e) {
                    Log.i("APPLICATION THING", "Registration Failure");
                }

            } else {
                //TODO: properly identify this error
                Log.i("SLDKJFLSDKJFLSKDJF", "NETWORKMANAGER NOT WORKING");
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, "Check Network Connection", duration);
                toast.show();
            }
        }

    }

    public void launchEditProfileActivity(View v) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void updateProfile(JSONObject profile) {
        try {
            ((TextView) findViewById(R.id.name)).setText(profile.getString("name"));
            ((TextView) findViewById(R.id.num_matches)).setText(profile.getString("checkIns"));
            ((TextView) findViewById(R.id.level)).setText(profile.getString("skill"));
            ((TextView) findViewById(R.id.blurb_about)).setText(profile.getString("about"));
        } catch (Exception e) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Log.i("LSKDJFLSKJDFLS", e.toString());
            Toast toast = Toast.makeText(context, "Died during profile update", duration);
            toast.show();
        }

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

        (new ProfileLoadTask(this)).execute();
        (new MatchLoadTask(this)).execute();


    }

    public void unmatchClick(View v) {
        this.printInfo();
    }

    public void matchClick(View v) {

        this.printInfo();
        (new CheckTask(this)).execute();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.shared_prefs_file_key), Context.MODE_PRIVATE);
        JSONObject json = new JSONObject();

        /*try {
            json.put("email", sharedPreferences.getString(getString(R.string.saved_email_field), ""));
            json.put("token", sharedPreferences.getString(getString(R.string.saved_token_field), ""));
            json.put("id", curmatch.get("_id"));

            NetworkManager friend = new NetworkManager();
            CheckTask.execute(json);
            json = friend.serverResponsePost(json, UrlBuilder.getCheckEndpoint());
            if (json.get("result").equals("Success")) {
                if (json.get("match").equals("Yes")){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "You have a match!!", duration);
                    toast.show();
                }
            }
            this.printInfo();

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void printInfo() {
        if (this.matches.size() > 0) {
            JSONObject result = this.curmatch;
            try {
                ((TextView) findViewById(R.id.name_match)).setText(result.getString("name"));

                int tup = result.getInt("up");
                int tdown = result.getInt("down");

                ((TextView) findViewById(R.id.down_rating)).setText(String.valueOf((double) tdown / (tup + tdown)));
                ((TextView) findViewById(R.id.up_rating)).setText(String.valueOf((double) tup / (tup + tdown)));


                ((TextView) findViewById(R.id.match_num_matches)).setText(result.getString("checkIns"));
                ((TextView) findViewById(R.id.match_level)).setText(result.getString("skill"));
                ((TextView) findViewById(R.id.match_blurb_about)).setText(result.getString("about"));
            } catch (Exception e) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Log.i("LSKDJFLSKJDFLS", e.toString());
                Toast toast = Toast.makeText(context, "Died during profile update", duration);
                toast.show();
            }
        } else {
            try {
                ((TextView) findViewById(R.id.name_match)).setText("No more matches");
            } catch (Exception e) {
            }
        }

        this.curmatch = this.matches.remove(0);
    }

    void updateMatches(JSONArray result) throws JSONException {
        this.matches = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            this.matches.add((JSONObject) result.get(i));
        }
    }


    class MatchLoadTask extends AsyncTask<Void, Void, JSONObject> {
        private Context mContext;

        MatchLoadTask(Context context) {
            mContext = context;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject msg = new JSONObject();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.shared_prefs_file_key), Context.MODE_PRIVATE);
            try {
                msg.put("email", sharedPreferences.getString(getString(R.string.saved_email_field), ""));
                msg.put("token", sharedPreferences.getString(getString(R.string.saved_token_field), ""));
                Log.i("LKSDJFLSKDJFLSKF", msg.toString());

                return NetworkManager.serverResponsePost(msg, UrlBuilder.getMatchEndpoint());
            } catch (Exception e) {
                Log.i("LSKDJFLSKJF", e.toString());
                return null;
            }
        }


        @Override
        protected void onPostExecute(JSONObject result) {

            if (result != null) {
                try {
                    CharSequence text;
                    if (result.get("result").toString().equals("Success")) {
                        text = "Profile Access success";
                        updateMatches(result.getJSONArray("data"));
                        Log.i("LSKDJFLSKJDFLS", result.toString());
                        //((Activity)mContext).finish();


                    } else {
                        Log.i("LKSJDFLKSJDF", result.toString());
                        text = "Profile Access failure";
                    }
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } catch (Exception e) {
                    Log.i("APPLICATION THING", "Registration Failure");
                }

            } else {
                //TODO: properly identify this error
                Log.i("SLDKJFLSDKJFLSKDJF", "NETWORKMANAGER NOT WORKING");
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, "Check Network Connection", duration);
                toast.show();
            }
        }
    }
}


