package com.maytic.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maytic.pokedex.Pokemon.PokemonServices;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * this class populates the pokemon stats view with dynamic information such as the pokemon's type
 */
public class PokemonStatsView extends AppCompatActivity {
    JSONObject pokemon;
    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_stats_view);

        // get the intent of the class calling this class
        Intent intent = getIntent();
        try {

            // get the pokemon's json object from intent
            pokemon = new JSONObject(intent.getStringExtra("json"));
            this.color = PokemonServices.getTypeColor(this.pokemon.getString("type1"));

            // set stats
            setPokemonTitle();
            setMetricsAndType();
            setLocationsAndSpecies();
            setDetails();
            setBaseStats();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * set the
     * @throws JSONException
     */
    private void setPokemonTitle() throws JSONException {
        TextView name = findViewById(R.id.stats_name);
        ImageView pokemonImage = findViewById(R.id.stats_image);
        ImageView background = findViewById(R.id.stats_background);
        CardView  outline = findViewById(R.id.stats_outline);
        CardView textOutline = findViewById(R.id.stats_text_background);

        textOutline.setCardBackgroundColor(Color.parseColor(this.color));
        outline.setCardBackgroundColor(Color.parseColor(this.color));


        Picasso.get()
                .load("https://storage.googleapis.com/staging.pokemondatabase-86124.appspot.com/backgrounds/grass.jpg")
                .transform(new BlurTransformation(this,1,1))
                .into(background);

        Picasso.get().load("https://storage.googleapis.com/staging.pokemondatabase-86124.appspot.com/pokemon/" +
                this.pokemon.get("id") + ".jpg")
                .into(pokemonImage);
        name.setText(  this.pokemon.getInt("id") + ": "+ (String) this.pokemon.get("name"));
    }


    /**
     * set the height and weight for the pokemon and the pokemons type
     * @throws JSONException
     */
    private void setMetricsAndType() throws JSONException {

        // set stats for type
        CardView type1Background = findViewById(R.id.stats_type1_background);
        CardView type2Background = findViewById(R.id.stats_type2_background);

        type1Background.setCardBackgroundColor(Color.parseColor(color));
        type2Background.setCardBackgroundColor(Color.parseColor(PokemonServices.getTypeColor(this.pokemon.getString("type2"))));

        TextView type1Text = findViewById(R.id.stats_type1_text);
        TextView type2Text = findViewById(R.id.stats_type2_text);

        type1Text.setText(this.pokemon.getString("type1"));
        String type2 = this.pokemon.getString("type2");
        if(type2.equals("null")){
            LinearLayout linearLayout = findViewById(R.id.stats_linear_type);
            LinearLayout linearLayout1 = findViewById(R.id.stats_linear2);
            linearLayout.removeView(linearLayout1);

        }else{
            type2Text.setText(this.pokemon.getString("type2"));
        }

        // sets stats for height and weight
        CardView statsOutline1 = findViewById(R.id.stats_outline1);

        statsOutline1.setCardBackgroundColor(Color.parseColor(this.color));

        TextView height = findViewById(R.id.stats_height);
        TextView weight = findViewById(R.id.stats_weight);

        height.setText(this.pokemon.getString("height"));
        weight.setText(this.pokemon.getString("weight"));

    }

    /**
     * function sets the species and locations of the pokemon
     * @throws JSONException
     */
    private void setLocationsAndSpecies() throws JSONException {
        // set species
        TextView species = findViewById(R.id.stats_species);
        species.setText(this.pokemon.getString("species"));

        // set locations
        TextView locations = findViewById(R.id.stats_locations);

        String ls[] = pokemon.getString("locations").split("\\) ");

        for(int i = 0; i < ls.length; i++){
            locations.setText(locations.getText() + ls[i] +")\n");
        }
    }


    /**
     * calls methods to set the stats of the pokemon
     * and the outline color of the stats background
     * @throws JSONException
     */
    private void setBaseStats() throws JSONException {
        CardView statsOutline2 = findViewById(R.id.stats_outline2);
        statsOutline2.setCardBackgroundColor(Color.parseColor(this.color));

        setHpStat();
        setAttackStat();
        setSpAttackStat();
        setDefenseStat();
        setSpDefenseStat();
        setSpeedStat();
        setTotalStat();
    }

    /**
     * set the health point stat for the pokemon
     * @throws JSONException
     */
    private void setHpStat() throws JSONException {
        ProgressBar hpBar = findViewById(R.id.stats_bar_hp);

        TextView baseHpText = findViewById(R.id.stats_base_hp);
        TextView minHpText = findViewById(R.id.stats_min_hp);
        TextView maxHpText = findViewById(R.id.stats_max_hp);

        baseHpText.setText(this.pokemon.getString("baseHp"));
        minHpText.setText(this.pokemon.getString("minHp"));
        maxHpText.setText(this.pokemon.getString("maxHp"));

        hpBar.setMax(180);
        hpBar.setProgress(this.pokemon.getInt("baseHp"));
        hpBar.getProgressDrawable().setColorFilter(Color.parseColor(PokemonServices.getStatColor("hp")), PorterDuff.Mode.SRC_IN);

    }

    /**
     * set the attack stat for the pokemon
     * @throws JSONException
     */
    private void setAttackStat() throws JSONException {
        ProgressBar atkBar = findViewById(R.id.stats_bar_atk);

        TextView baseAtkText = findViewById(R.id.stats_base_atk);
        TextView minAtkText = findViewById(R.id.stats_min_atk);
        TextView maxAtkText = findViewById(R.id.stats_max_atk);

        baseAtkText.setText(this.pokemon.getString("baseAttack"));
        minAtkText.setText(this.pokemon.getString("minAttack"));
        maxAtkText.setText(this.pokemon.getString("maxAttack"));

        atkBar.setMax(180);
        atkBar.setProgress(this.pokemon.getInt("baseAttack"));
        atkBar.getProgressDrawable().setColorFilter(Color.parseColor(PokemonServices.getStatColor("attack")), PorterDuff.Mode.SRC_IN);

    }

    /**
     * set the special attack stat for the pokemon
     * @throws JSONException
     */
    private void setSpAttackStat() throws JSONException {
        ProgressBar spAtkBar = findViewById(R.id.stats_bar_sp_atk);

        TextView baseSpAtkText = findViewById(R.id.stats_base_sp_atk);
        TextView minSpAtkText = findViewById(R.id.stats_min_sp_atk);
        TextView maxSpAtkText = findViewById(R.id.stats_max_sp_atk);

        baseSpAtkText.setText(this.pokemon.getString("baseSpAtk"));
        minSpAtkText.setText(this.pokemon.getString("minSpAtk"));
        maxSpAtkText.setText(this.pokemon.getString("maxSpAtk"));

        spAtkBar.setMax(180);
        spAtkBar.setProgress(this.pokemon.getInt("baseSpAtk"));
        spAtkBar.getProgressDrawable().setColorFilter(Color.parseColor(PokemonServices.getStatColor("sp-atk")), PorterDuff.Mode.SRC_IN);

    }

    /**
     * set defense stat for pokemon
     * @throws JSONException
     */
    private void setDefenseStat() throws JSONException {
        ProgressBar defBar = findViewById(R.id.stats_bar_def);

        TextView baseDefText = findViewById(R.id.stats_base_def);
        TextView minDefText = findViewById(R.id.stats_min_def);
        TextView maxDefText = findViewById(R.id.stats_max_def);

        baseDefText.setText(this.pokemon.getString("baseDefense"));
        minDefText.setText(this.pokemon.getString("minDefense"));
        maxDefText.setText(this.pokemon.getString("maxDefense"));

        defBar.setMax(180);
        defBar.setProgress(this.pokemon.getInt("baseDefense"));
        defBar.getProgressDrawable().setColorFilter(Color.parseColor(PokemonServices.getStatColor("defense")), PorterDuff.Mode.SRC_IN);

    }

    /**
     * set the special defense stat for the pokemon
     * @throws JSONException
     */
    private void setSpDefenseStat() throws JSONException {
        ProgressBar spDefBar = findViewById(R.id.stats_bar_sp_def);

        TextView baseSpDefText = findViewById(R.id.stats_base_sp_def);
        TextView minSpDefText = findViewById(R.id.stats_min_sp_def);
        TextView maxSpDefText = findViewById(R.id.stats_max_sp_def);

        baseSpDefText.setText(this.pokemon.getString("baseSpDef"));
        minSpDefText.setText(this.pokemon.getString("minSpDef"));
        maxSpDefText.setText(this.pokemon.getString("maxSpDef"));

        spDefBar.setMax(180);
        spDefBar.setProgress(this.pokemon.getInt("baseSpDef"));
        spDefBar.getProgressDrawable().setColorFilter(Color.parseColor(PokemonServices.getStatColor("sp-def")), PorterDuff.Mode.SRC_IN);

    }

    /**
     * set the speed stat fpr the pokemon
     */
    private void setSpeedStat() throws JSONException {
        ProgressBar speedBar = findViewById(R.id.stats_bar_speed);

        TextView baseSpeedText = findViewById(R.id.stats_base_speed);
        TextView minSpeedText = findViewById(R.id.stats_min_speed);
        TextView maxSpeedText = findViewById(R.id.stats_max_speed);

        baseSpeedText.setText(this.pokemon.getString("baseSpeed"));
        minSpeedText.setText(this.pokemon.getString("minSpeed"));
        maxSpeedText.setText(this.pokemon.getString("maxSpeed"));

        speedBar.setMax(180);
        speedBar.setProgress(this.pokemon.getInt("baseSpeed"));
        speedBar.getProgressDrawable().setColorFilter(Color.parseColor(PokemonServices.getStatColor("speed")), PorterDuff.Mode.SRC_IN);
    }

    /**
     * get total stats from pokemon
     * @throws JSONException
     */
    private void setTotalStat() throws JSONException {
        TextView total = findViewById(R.id.stats_total);

        total.setText(this.pokemon.getString("totalBase"));
    }

    /**
     * set the details such ass egg group for the pokemon
     * @throws JSONException
     */
    private void setDetails() throws JSONException {
        CardView cardView = findViewById(R.id.stats_outline3);

        cardView.setCardBackgroundColor(Color.parseColor(PokemonServices.getTypeColor(this.pokemon.getString("type1"))));

        TextView catchRate = findViewById(R.id.stats_catch_rate);
        TextView genderRate = findViewById(R.id.stats_gender_rate);
        TextView baseExp = findViewById(R.id.stats_base_exp);
        TextView baseFriendship = findViewById(R.id.stats_base_friendship);
        TextView growthRate = findViewById(R.id.stats_growth_rate);
        TextView eggCycles = findViewById(R.id.stats_egg_cycles);
        TextView evYield = findViewById(R.id.stats_ev_yield);
        TextView eggGroups = findViewById(R.id.stats_egg_groups);

        catchRate.setText(this.pokemon.getString("catchRate"));
        genderRate.setText(this.pokemon.getString("gender"));
        baseExp.setText(this.pokemon.getString("baseExp"));
        baseFriendship.setText(this.pokemon.getString("baseFriendship"));
        growthRate.setText(this.pokemon.getString("growthRate"));
        eggCycles.setText(this.pokemon.getString("eggCycles"));
        evYield.setText(this.pokemon.getString("evyield"));
        eggGroups.setText(this.pokemon.getString("eggGroups"));
    }



}