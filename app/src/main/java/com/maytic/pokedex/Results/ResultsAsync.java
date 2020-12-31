package com.maytic.pokedex.Results;

import android.os.AsyncTask;
import android.widget.Toast;

import com.maytic.pokedex.MainActivity;
import com.maytic.pokedex.MainInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * class gets the results of a pokemon search by name and returns a list of pokemon
 */
public class ResultsAsync extends AsyncTask<String, String, String> {
    public MainInterface mainInterface = null;

    private JSONArray pokemons;
    private String baseUrl = "https://pokemon-db-123.herokuapp.com/pokemons?name=";

    // get results or return 404 not found error
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(baseUrl + strings[0]);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            if(connection.getResponseCode() == 404) {
                mainInterface.onNoResultsFound();
                this.cancel(true);

            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            pokemons = new JSONArray(reader.readLine());
            System.out.println("tag00: " + pokemons.getString(0));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    // once list is retrieved use a callback to get results
    @Override
    protected void onPostExecute(String s) {


        mainInterface.onResultsFound(pokemons);
    }
}
