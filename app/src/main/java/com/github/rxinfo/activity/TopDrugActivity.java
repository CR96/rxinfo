package com.github.rxinfo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.rxinfo.R;
import com.github.rxinfo.adapter.TopDrugAdapter;
import com.github.rxinfo.model.TopDrug;
import com.github.rxinfo.model.TopDrugs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopDrugActivity extends AppCompatActivity {

    private List<TopDrug> topDrugList = new ArrayList<>();
    private TopDrugAdapter topDrugAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_drugs);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        final RecyclerView lst_top_drugs = findViewById(R.id.lst_top_drugs);
        lst_top_drugs.setLayoutManager(layoutManager);

        // Deserialize top drug feed
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://cr96.github.io/topdrugs/api/top_drugs.json";

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new GsonBuilder()
                        .create();
                TopDrugs topDrugsJson = gson
                        .fromJson(response.toString(), TopDrugs.class);

                topDrugAdapter = new TopDrugAdapter(TopDrugActivity.this, topDrugsJson.topDrugs);
                lst_top_drugs.setAdapter(topDrugAdapter);
                topDrugList = topDrugsJson.topDrugs;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Handle error
            }
        }
        );
        queue.add(jsonObjectRequest);
    }
}
