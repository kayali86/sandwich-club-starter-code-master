package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = null;
        // I used try/catch statement because parseSandwichJson method throws a JSONException
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e){
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.no_image)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAsView = (TextView)findViewById(R.id.also_known_tv);
        // I used replaceAll method to hide brackets before displaying the array on textView
        alsoKnownAsView.setText(sandwich.getAlsoKnownAs().toString().replaceAll("\\[|\\]", ""));
        if (sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownAsView.setText(getString(R.string.no_data_found_message));
        }

        TextView placeOfOriginView = (TextView)findViewById(R.id.origin_tv);
        placeOfOriginView.setText(sandwich.getPlaceOfOrigin());
        if (sandwich.getPlaceOfOrigin().isEmpty()){
            placeOfOriginView.setText(getString(R.string.no_data_found_message));
        }

        TextView descriptionView = (TextView)findViewById(R.id.description_tv);
        descriptionView.setText(sandwich.getDescription());
        if (sandwich.getDescription().isEmpty()){
            descriptionView.setText(getString(R.string.no_data_found_message));
        }

        TextView ingredientsView = (TextView)findViewById(R.id.ingredients_tv);
        // I used replaceAll method to hide brackets before displaying the array on textView
        ingredientsView.setText(sandwich.getIngredients().toString().replaceAll("\\[|\\]", ""));
        if (sandwich.getIngredients().isEmpty()){
            ingredientsView.setText(getString(R.string.no_data_found_message));
        }
    }
}
