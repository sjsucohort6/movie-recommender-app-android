package com.sjsu.swethamuchukota.cmpe277finalproject_movierecommender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDisplayActivity extends Activity {
    JSONArray array = null;
    JSONObject json_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_display);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("recommendations");


        // TODO : please use CommonUtils.convertJsonArrayToList() to get List<UserRecommendations>
        // !!! ASHUTOSH to change it !!!
        try {
            array = new JSONArray(jsonArray);
            System.out.println(array.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<String> items = new ArrayList<String>();
        for(int i=0; i < array.length() ; i++) {
            try {
                json_data = array.getJSONObject(i); //getJSONObject(i);
                int rating = json_data.getInt("rating");
                String name = json_data.getString("title");
                String genre = json_data.getString("genre");
                int imdbid = json_data.getInt("imdbid");

                items.add(name + " -- " + rating + " -- " + genre + " :" + imdbid);
                //TODO: ASHUTOSH please set genre in description text
                Log.d(name, "Output");
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }


        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.single_listitem_layout, R.id.firstLine, items);

        ListView listView = (ListView) findViewById(R.id.movieListview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                TextView listText = (TextView) view.findViewById(R.id.firstLine);
                String text = listText.getText().toString();

                // create intent to start another activity
                Intent intent = new Intent(MovieDisplayActivity.this, MovieDetailsActivity.class);
                // add the selected text item to our intent.
                intent.putExtra("movieDetails", text);
                startActivity(intent);

            }
        });


    }

}
