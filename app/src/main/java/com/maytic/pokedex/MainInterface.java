package com.maytic.pokedex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// used for callbacks in main activity
public interface MainInterface {
    public void onFinishedMainAsync(JSONObject object) throws JSONException;
    void onNoResultsFound();
    void onResultsFound(JSONArray pokemons);
}
