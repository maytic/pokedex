package com.maytic.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.maytic.pokedex.Pokemon.PokemonServices;
import com.maytic.pokedex.Results.ResultsActivity;
import com.maytic.pokedex.Results.ResultsAsync;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * class is the main activity and handles everything in the main_activity.xml view
 */
public class MainActivity extends AppCompatActivity implements MainInterface {
    public final static String BASE_URL = "https://pokemon-db-123.herokuapp.com/";
    int count = 1;
    int callBackCount = 0;
    int totalPokemonLoaded = 0;


    ArrayList<LinearLayout> linearLayouts = new ArrayList<LinearLayout>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add event listener
        final ScrollView scrollView = findViewById(R.id.main_scroll);

        // load first 20 pokemon
        getPokemon(20);

        // set event listner to load more pokemon when scroll view is at the bottom of the page
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scrollView.getChildAt(0).getBottom() <= (scrollView.getHeight() + scrollView.getScrollY())) {
                    if(totalPokemonLoaded <= 880){
                        getPokemon(20);
                    }

                }
            }
        }); // end of event listener

        SearchView searchView = findViewById(R.id.main_search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ResultsAsync resultsAsync = new ResultsAsync();
                resultsAsync.mainInterface = MainActivity.this;
                resultsAsync.execute(query);



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        }); // end search view listener


    } // end of main

    /**
     * gets pokemon by amount requested
     *  goes in ascending order from 1-898
     * @param amount - amount of pokemon being requested
     */
    private void getPokemon(int amount){

        // handle the first 880 pokemon
        if(totalPokemonLoaded < 880){
            for (int i = totalPokemonLoaded; i < amount + this.totalPokemonLoaded; i++){
                LinearLayout main = findViewById(R.id.main_layout);

                CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.pokemon_card_view,main,false);

                // handle when populating the dynamic views
                if(i % 2 == 0){
                    LinearLayout linearLayout = new LinearLayout(this);
                    linearLayout.setId(this.count);
                    linearLayout.addView(cardView);
                    linearLayouts.add(linearLayout);

                    main.addView(linearLayout);

                }
                else{
                    LinearLayout layout  = findViewById(count);
                    linearLayouts.get(this.count - 1).addView(cardView);

                    this.count = this.count + 1;
                }
                // call async to get data from webservice
                MainAsync async = new MainAsync();
                async.mainInterface = this;
                async.execute(String.valueOf(i + 1));

            }
        }
        else{ // handle the population of the remaining 18 pokemon
            for (int i = totalPokemonLoaded; i < 18 + this.totalPokemonLoaded; i++){
                LinearLayout main = findViewById(R.id.main_layout);

                CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.pokemon_card_view,main,false);

                // handle when populating the dynamic views
                if(i % 2 == 0){
                    LinearLayout linearLayout = new LinearLayout(this);
                    linearLayout.setId(this.count);
                    linearLayout.addView(cardView);
                    linearLayouts.add(linearLayout);

                    main.addView(linearLayout);

                }
                else{
                    LinearLayout layout  = findViewById(count);
                    linearLayouts.get(this.count - 1).addView(cardView);

                    this.count = this.count + 1;
                }

                // call async task
                MainAsync async = new MainAsync();
                async.mainInterface = this;
                async.execute(String.valueOf(i + 1));

            }
        }
        this.totalPokemonLoaded += amount;
    }

    // call back from async task to populate generated views
    @Override
    public void onFinishedMainAsync(final JSONObject object) throws JSONException {

        // set data on cards
        if(object.getInt("id") % 2 !=  0){

            LinearLayout layout = linearLayouts.get(this.callBackCount);

            CardView cardView = (CardView) layout.getChildAt(0);
            TextView name = (TextView) cardView.getChildAt(2);
            TextView id = (TextView) cardView.getChildAt(3);
            Button button = (Button) cardView.getChildAt(5);

            id.setText(Integer.toString(object.getInt("id")));
            name.setText(object.getString("name"));

            ProgressBar progressBar = (ProgressBar) cardView.getChildAt(4);
            cardView.removeView(progressBar);

            String type = object.getString("type1");

            // set event listener to the button to open new activity
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,PokemonStatsView.class);

                    intent.putExtra("json" , object.toString());
                    startActivity(intent);
                }
            });

            // color card background by pokemon type
            cardView.setCardBackgroundColor(Color.parseColor(PokemonServices.getTypeColor(type)));

            // get objects from card view
            ImageView pokemon = (ImageView) cardView.getChildAt(1);

            // insert pokemon png from database to image view
            Picasso.get().load("https://storage.googleapis.com/staging.pokemondatabase-86124.appspot.com/icons/" + object.get("id") +".png").into(pokemon);
            callBackCount++;
        }
        else{

            LinearLayout layout = linearLayouts.get(this.callBackCount - 1);

            CardView cardView = (CardView) layout.getChildAt(1);
            TextView name = (TextView) cardView.getChildAt(2);
            TextView id = (TextView) cardView.getChildAt(3);
            Button button = (Button) cardView.getChildAt(5);

            id.setText(Integer.toString(object.getInt("id")));
            name.setText(object.getString("name"));

            ProgressBar progressBar = (ProgressBar) cardView.getChildAt(4);
            cardView.removeView(progressBar);
            String type = object.getString("type1");

            // set event listener to the button to open new activity
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,PokemonStatsView.class);

                    intent.putExtra("json" , object.toString());
                    startActivity(intent);
                }
            });


            // color card background by pokemon type
            cardView.setCardBackgroundColor(Color.parseColor(PokemonServices.getTypeColor(type)));

            // get objects from card view
            ImageView pokemon = (ImageView) cardView.getChildAt(1);

            // insert pokemon png from database to image view
            Picasso.get().load("https://storage.googleapis.com/staging.pokemondatabase-86124.appspot.com/icons/" + object.get("id") +".png").into(pokemon);

        } // end else if blocks


    }

    @Override
    public void onNoResultsFound() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String error = "Error 404 Pokemon Not Found";
                Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResultsFound(JSONArray pokemons) {
        //int size = pokemons.length();

        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        intent.putExtra("query", pokemons.toString());
        startActivity(intent);
    }
}