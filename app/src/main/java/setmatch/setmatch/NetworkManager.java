package setmatch.setmatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.InputStream;

import java.net.URL;
import java.net.HttpURLConnection;

import java.lang.String;
import java.lang.StringBuffer;

import android.util.Log;

import org.json.JSONObject;


/*
* Network manager was created to send and recieve JSON data.
* Be nice to it
 */
public class NetworkManager{



    //JSON send and response
    //UNTESTED
    public static JSONObject serverResponse(JSONObject jsonObject, String urlString){

        try {

            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();


            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();

            int responseCode = httpURLConnection.getResponseCode();
            InputStream is = httpURLConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            is.close();
            return new JSONObject(is.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }


}

