package com.samapps.restituo.VollyAndFirebaseConfig;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.samapps.restituo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        textView = findViewById(R.id.textView3);
        mRequestQueue = Volley.newRequestQueue(this);

        // for json data

        String url = "https://protochordate-falls.000webhostapp.com/restituo/register.php?phone=9869745614&invitedBy=8652807821";
        JsonObjectRequest
                jsonObjectRequest
                = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            textView.setText(response.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(ApiActivity.this, "Some error occurred!!", Toast.LENGTH_LONG).show();

                    }
                });
        mRequestQueue.add(jsonObjectRequest);


    }
}