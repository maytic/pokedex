package com.maytic.pokedex.Results;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.maytic.pokedex.R;

public class ResultsActivity extends AppCompatActivity {

    private String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        query = getIntent().getStringExtra("query");
    }
}