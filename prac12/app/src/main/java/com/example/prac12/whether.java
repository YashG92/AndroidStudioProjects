package com.example.prac12;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class whether extends AppCompatActivity {
    private TextView resultTextView, region, country, humidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whether);

        resultTextView = findViewById(R.id.voll);
        region = findViewById(R.id.region);
        country = findViewById(R.id.country);
        humidity = findViewById(R.id.humidity);

        Intent i1 = getIntent();
        String reg = i1.getStringExtra("country");
        region.setText(reg);

        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl = "http://api.weatherapi.com/v1/current.json?key=20c24a18ec494752b4262119233110&q=rajkot&aqi=no";  // Replace "api url" with the actual API endpoint URL
        Log.d("api", apiUrl);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject main = response.getJSONObject("location");
                            JSONObject current = response.getJSONObject("current");
                            country.setText("Name: " + main.getString("name") + ", Region: " + main.getString("region"));
                            humidity.setText("Humidity: " + current.getString("humidity") + ", Temperature: " + current.getString("temp_c"));
                            Log.d("location", main.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resultTextView.setText("Error: " + error.toString());
            }
        });

        queue.add(jsonObjectRequest);  // Add the request to the queue to execute it
    }
}
