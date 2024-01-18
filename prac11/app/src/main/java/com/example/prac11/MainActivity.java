package com.example.prac11;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static String apiUrl = "";
    TextView txtResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResponse = findViewById(R.id.txtResponse);
        callApi();
    }

    private void callApi() {
        if (isNetworkAvailable()) {
            Helper.showProgressDialog(MainActivity.this, "Please Wait");

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            apiUrl = "https://json.astrologyapi.com/v1/birth_details";
            Log.e("Main Activity: ", "Url: " + apiUrl);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Helper.dismissProgressDialog();
                            Log.e("Main Activity: ", "Response: " + response);
                            try {
                                // Parse JSON response here and update UI accordingly
                                txtResponse.setText(response);






                            } catch (Exception e) {
                                e.printStackTrace();
                                showToastMessage("Something Went Wrong");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Helper.dismissProgressDialog();
                            showToastMessage("Something went wrong");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("day", "9");
                    params.put("month", "12");
                    params.put("year", "2003");
                    params.put("hour", "12");
                    params.put("min", "50");
                    params.put("lat", "18.33");
                    params.put("lon", "77.43");
                    params.put("tzone","6.5");

                    Log.e("Main Activity: ", "Param: " + params);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = "626466:5e5ed883e3cd207e81daac3ff06d632e";
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    100000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        } else {
            showToastMessage("No Internet Connection");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showToastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
