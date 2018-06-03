package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;


public class JsonUtils {

    private static final String Name="name";
    private static final String Description="description";
    private static final String Also_Known_As="alsoKnownAs";
    private static final String Main_Name="mainName";
    private static final String Place_Of_Origin="placeOfOrigin";
    private static final String Img="image";
    private static final String Ingredients="ingredients";




    public static Sandwich parseSandwichJson(String json)
    {

        try{
            JSONObject sandwich_data=new JSONObject(json);
            JSONObject name=sandwich_data.getJSONObject(Name);
            String mainName=name.getString(Main_Name);
            String description=sandwich_data.getString(Description);
            JSONArray alsoKnownAs=name.getJSONArray(Also_Known_As);
            List<String> synonym_list=new ArrayList<>();
            for(int i=0;i<alsoKnownAs.length();i++)
            {
                synonym_list.add(alsoKnownAs.getString(i));
            }



            String placeOfOrigin=sandwich_data.getString(Place_Of_Origin);
            String image=sandwich_data.getString(Img);
            JSONArray ingredients=sandwich_data.getJSONArray(Ingredients);
            List<String> ingredients_list=new ArrayList<>();
            for(int i=0;i<ingredients.length();i++)
            {
                ingredients_list.add(ingredients.getString(i));
            }

            Sandwich sandwich=new Sandwich(mainName,synonym_list,placeOfOrigin,description,image,ingredients_list);

            return sandwich;


        }catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }


    }
}
