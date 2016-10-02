package setmatch.setmatch;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {


    public class ProfileLoadTask extends AsyncTask<Void, Void, JSONObject> {
        private Context mContext;

        ProfileLoadTask(Context context) {
            mContext = context;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject msg = new JSONObject();
            SharedPreferences sharedPreferences =  getApplicationContext().getSharedPreferences(getString(R.string.shared_prefs_file_key), Context.MODE_PRIVATE);
            try {
                msg.put("email", sharedPreferences.getString(getString(R.string.saved_email_field), ""));
                msg.put("token", sharedPreferences.getString(getString(R.string.saved_token_field), ""));

                Log.i("LKSDJFLSKDJFLSKF", msg.toString());

                return NetworkManager.serverResponsePost(msg, UrlBuilder.getProfileEndpoint());
            }catch(Exception e){
                Log.i("LSKDJFLSKJF", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            if (result != null) {
                try{
                    CharSequence text;
                    if(result.get("result").toString().equals("Success")){
                        text = "Profile Access success";
                        updateProfileFields(result);
                        Log.i("LSKDJFLSKJDFLS", result.toString());
                        //((Activity)mContext).finish();


                    }else{
                        Log.i("LKSJDFLKSJDF", result.toString());
                        text = "Profile Access failure";
                    }
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);


                }catch (Exception e){
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
    public class ProfileEditSubmitTask extends AsyncTask<Void, Void, JSONObject> {
        private Context mContext;
        private JSONObject mMsg;

        ProfileEditSubmitTask(Context context, JSONObject msg) {
            mContext = context;
            mMsg = msg;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            SharedPreferences sharedPreferences =  getApplicationContext().getSharedPreferences(getString(R.string.shared_prefs_file_key), Context.MODE_PRIVATE);
            try {
                mMsg.put("email", sharedPreferences.getString(getString(R.string.saved_email_field), ""));
                mMsg.put("token", sharedPreferences.getString(getString(R.string.saved_token_field), ""));
                Log.i("LKSDJFLSKDJFLSKF", mMsg.toString());

                return NetworkManager.serverResponsePost(mMsg, UrlBuilder.getEditProfileEndpoint());
            }catch(Exception e){
                Log.i("LSKDJFLSKJF", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            if (result != null) {
                try{
                    CharSequence text;
                    if(result.get("result").toString().equals("Success")){
                        text = "Profile Edit success";
                        updateProfileFields(result);
                        Log.i("LSKDJFLSKJDFLS", result.toString());
                        ((Activity)mContext).finish();


                    }else{
                        Log.i("LKSJDFLKSJDF", result.toString());
                        text = "Profile Edit failure";
                    }
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);


                }catch (Exception e){
                    Log.i("APPLICATION THING", "Edit Failure");
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


    protected void updateProfileFields(JSONObject profile){
        try {
            ((EditText) findViewById(R.id.name_input)).setText(profile.getString("name"));
            ((EditText) findViewById(R.id.about_input)).setText(profile.getString("about"));
            ((EditText) findViewById(R.id.email_input)).setText(profile.getJSONObject("email").getString("value"));
            ((EditText) findViewById(R.id.phone_input)).setText(profile.getJSONObject("phone").getString("value"));
            if(profile.getInt("skill") > 0)
                ((Spinner) findViewById(R.id.skill_spinner)).setSelection(profile.getInt("skill") - 1);
            else
                ((Spinner) findViewById(R.id.skill_spinner)).setSelection(0);
            for(int i = 0; i < profile.getJSONArray("prefs").length(); i++){
                if(profile.getJSONArray("prefs").get(i).equals( "Swimming")) ((CheckBox) findViewById(R.id.swimming_checkbox)).setChecked(true);
                if(profile.getJSONArray("prefs").get(i).equals( "Running")) ((CheckBox) findViewById(R.id.running_checkbox)).setChecked(true);
                if(profile.getJSONArray("prefs").get(i).equals( "Yoga") )((CheckBox) findViewById(R.id.yoga_checkbox)).setChecked(true);
                if(profile.getJSONArray("prefs").get(i).equals(  "Lifting")) ((CheckBox) findViewById(R.id.lifting_checkbox)).setChecked(true);

            }
        }catch(Exception e){

        }
    }

    public void saveButtonClick(View v){
        JSONObject msg = new JSONObject();
        try {
            msg.put("name", ((EditText) findViewById(R.id.name_input)).getText());
            msg.put("about", ((EditText) findViewById(R.id.about_input)).getText());
            msg.put("phone", ((EditText) findViewById(R.id.phone_input)).getText());
            msg.put("shareEmail", false);
            msg.put("sharePhone", false);
            msg.put("skill",  ((Spinner) findViewById(R.id.skill_spinner)).getSelectedItemPosition() + 1);
            JSONArray j = new JSONArray();
            if(((CheckBox) findViewById(R.id.swimming_checkbox)).isChecked()) j.put("Swimming");
            if(((CheckBox) findViewById(R.id.running_checkbox)).isChecked()) j.put("Running");
            if(((CheckBox) findViewById(R.id.yoga_checkbox)).isChecked()) j.put("Yoga");
            if(((CheckBox) findViewById(R.id.lifting_checkbox)).isChecked()) j.put("Lifting");
            msg.put("prefs", j);
            (new ProfileEditSubmitTask(this, msg)).execute();

        }catch(Exception e){

        }
    }
    public void cancelButtonClick(View v){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner skillSpinner = ((Spinner) findViewById(R.id.skill_spinner));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.skill_level, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        skillSpinner.setAdapter(adapter);

        (new ProfileLoadTask(this)).execute();
    }

}
