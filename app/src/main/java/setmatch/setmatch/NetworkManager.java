package setmatch.setmatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;

import java.net.URL;
import java.net.HttpURLConnection;

import java.lang.String;
import java.lang.StringBuffer;


import org.json.JSONObject;


/*
* Network manager was created to send and recieve JSON data.
* Be nice to it
 */
public class NetworkManager{



    //Sends login params to server and returns its result as JSON
    //UNTESTED
    private JSONObject loginResponse(String... params) throws Exception{
        URL url = new URL("http://gablescode.net:8000"); //If needed change url herep
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.connect();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("email", params[0]);
        jsonObject.put("password", params[1]);
        jsonObject.put("name", params[2]);

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(jsonObject.toString());
        wr.flush();
        wr.close();

        return new JSONObject(wr.toString());

    }


}

