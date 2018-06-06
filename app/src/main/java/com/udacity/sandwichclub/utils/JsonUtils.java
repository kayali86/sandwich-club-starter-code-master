package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    // This method throws a JSONException
    public static Sandwich parseSandwichJson(String json) throws JSONException{
        // Keys to retrieve strings and Objects from JSON Response
        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";
        // Variables to store values from JSON response and make a new Sandwich Object
        String mainName, placeOfOrigin, description, image;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        // Get the main Response and retrieve Sandwich name
        JSONObject JSONResponse = new JSONObject(json);
        JSONObject nameObject = JSONResponse.getJSONObject(NAME);
        mainName = nameObject.getString(MAIN_NAME);
        // Get an Array of alsoKnownAs names
        JSONArray alsoKnowsAsJSONArray = nameObject.getJSONArray(ALSO_KNOWN_AS);
        for (int i = 0; i < alsoKnowsAsJSONArray.length(); i++){
            if (alsoKnowsAsJSONArray.getString(i) != null){
                alsoKnownAs.add(alsoKnowsAsJSONArray.getString(i));
            }
        }
        // Get placeOfOrigin, description and image path
        placeOfOrigin = JSONResponse.getString(PLACE_OF_ORIGIN);
        description = JSONResponse.getString(DESCRIPTION);
        image = JSONResponse.getString(IMAGE);
        // Get an Array of ingredients names
        JSONArray ingredientsJSONArray = JSONResponse.getJSONArray(INGREDIENTS);
        for (int i = 0; i < ingredientsJSONArray.length(); i++){
            if (ingredientsJSONArray.getString(i) != null){
                ingredients.add(ingredientsJSONArray.getString(i));
            }
        }
        // Return an new Sandwich object using retrieved values
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
