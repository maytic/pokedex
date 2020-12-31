package com.maytic.pokedex;

import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * async task gets from webservice then after getting data it will use a call back to display pokemon
 *
 */
public class MainAsync extends AsyncTask<String, String, String> {

    public MainInterface mainInterface = null;

    private JSONObject object;

    // acquire data
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(MainActivity.BASE_URL + "pokemon/" + strings[0]);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            object = new JSONObject(bufferedReader.readLine());



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    // send to call back
    @Override
    protected void onPostExecute(String s) {
        try {
            mainInterface.onFinishedMainAsync(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

}
