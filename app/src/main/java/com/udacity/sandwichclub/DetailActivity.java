package com.udacity.sandwichclub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich sandwich;
    private TextView tv_synonym,tv_description,tv_origin,tv_ingredients;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }




        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tv_synonym=(TextView)findViewById(R.id.also_known_tv);
        tv_description=(TextView)findViewById(R.id.description_tv);
        tv_origin=(TextView)findViewById(R.id.origin_tv);
        tv_ingredients=(TextView)findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if(sandwich.getPlaceOfOrigin().isEmpty())
        {
            tv_origin.setText(R.string.no_data);
        }
        else{
            tv_origin.setText(sandwich.getPlaceOfOrigin());
        }
        if(sandwich.getAlsoKnownAs().isEmpty())
        {
            tv_synonym.setText(R.string.no_data);
        }
        else{
            tv_synonym.setText(listModel(sandwich.getAlsoKnownAs()));
        }
        tv_description.setText(sandwich.getDescription());
        tv_ingredients.setText(listModel(sandwich.getIngredients()));


    }

    public StringBuilder listModel(List<String> list){
        StringBuilder stringBuilder= new StringBuilder();
        for (int i=0;i<list.size();i++){
            stringBuilder.append(list.get(i)).append("\n");
        }
        return stringBuilder;
    }

}
