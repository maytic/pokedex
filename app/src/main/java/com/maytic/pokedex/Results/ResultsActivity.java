package com.maytic.pokedex.Results;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maytic.pokedex.MainActivity;
import com.maytic.pokedex.Pokemon.PokemonServices;
import com.maytic.pokedex.PokemonStatsView;
import com.maytic.pokedex.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultsActivity extends AppCompatActivity {

    JSONArray pokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        try {
            this.pokemons = new JSONArray(getIntent().getStringExtra("query"));



        } catch (JSONException e) {
            e.printStackTrace();

        }
        try {
            createPokemonViews();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    } // end main onCreate()

    void createPokemonViews() throws JSONException {
        LinearLayout main = findViewById(R.id.results_main);


        for (int i = 0; i < this.pokemons.length(); i++){
            // get pokemon
            final JSONObject pokemon = this.pokemons.getJSONObject(i);

            // inflate view
            CardView pokemonView = (CardView) LayoutInflater
                    .from(this).inflate(R.layout.pokemon_results_view,main, false);

            // get temporary views
            CardView tempCardView = (CardView) pokemonView.getChildAt(0);
            LinearLayout tempLinearLayout = (LinearLayout) tempCardView.getChildAt(0);

            // get image view from tempView
            ImageView imageView = (ImageView) tempLinearLayout.getChildAt(0);

            // get main linear layout for stats
            LinearLayout statsLayout = (LinearLayout) tempLinearLayout.getChildAt(1);

            // get linear layout for stats
            LinearLayout idStatsLayout = (LinearLayout) statsLayout.getChildAt(0);
            LinearLayout nameStatsLayout = (LinearLayout) statsLayout.getChildAt(1);
            LinearLayout typeStatsLayout = (LinearLayout) statsLayout.getChildAt(2);

            // get stats
            TextView id = (TextView) idStatsLayout.getChildAt(1);
            TextView name = (TextView) nameStatsLayout.getChildAt(1);
            CardView type1Card = (CardView) typeStatsLayout.getChildAt(0);
            CardView type2Card = (CardView) typeStatsLayout.getChildAt(1);
            TextView type1Text = (TextView) type1Card.getChildAt(0);
            TextView type2Text = (TextView) type2Card.getChildAt(0);

            // set id and name
            id.setText(Integer.toString(pokemon.getInt("id")));
            name.setText(pokemon.getString("name"));

            // set types
            type1Card.setCardBackgroundColor(Color.parseColor(PokemonServices.getTypeColor(pokemon.getString("type1"))));
            type1Text.setText(pokemon.getString("type1"));

            if(!pokemon.getString("type2").equals("null")){
                type2Card.setCardBackgroundColor(Color.parseColor(PokemonServices.getTypeColor(pokemon.getString("type2"))));
                type2Text.setText(pokemon.getString("type2"));
            }
            else{
                typeStatsLayout.removeView(type2Card);
            }

            // set button listener
            Button button = (Button) pokemonView.getChildAt(1);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ResultsActivity.this, PokemonStatsView.class);

                    intent.putExtra("json" , pokemon.toString());
                    startActivity(intent);
                }
            });
            // set CardView background color
            pokemonView.setCardBackgroundColor(Color.parseColor(PokemonServices.getTypeColor(pokemon.getString("type1"))));

            // set pokemon icon image
            Picasso.get().load("https://storage.googleapis.com/staging.pokemondatabase-86124.appspot.com/icons/" + pokemon.get("id") +".png").into(imageView);

            //id.setText(pokemon.getInt("id"));
            main.addView(pokemonView);

        }
    }


}