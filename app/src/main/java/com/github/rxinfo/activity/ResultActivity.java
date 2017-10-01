package com.github.rxinfo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.rxinfo.R;
import com.github.rxinfo.adapter.DrugAdapter;
import com.github.rxinfo.model.Drug;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ArrayList<Drug> drugs = new ArrayList<>();

    DrugAdapter drugAdapter;

    TextView txtEmpty;
    Button btnBack;

    boolean resultFound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        drugAdapter = new DrugAdapter(ResultActivity.this, drugs);

        RecyclerView lstResults = findViewById(R.id.lst_results);
        txtEmpty = findViewById(R.id.txt_empty);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lstResults.setLayoutManager(layoutManager);
        lstResults.setAdapter(drugAdapter);

        Intent result  = getIntent();
        String[] possibleNdcs = result.getStringArrayExtra("possibleNdcs");

        for (String possibleNdc : possibleNdcs) {
            getDrugInfo(possibleNdc);
        }
    }

    private void getDrugInfo(final String possibleNdc) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.fda.gov/drug/label.json?search=\""
                + possibleNdc + "\"";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new GsonBuilder()
                                .setPrettyPrinting()
                                .disableHtmlEscaping()
                                .create();
                        Drug drug = gson
                                .fromJson(response.toString(), Drug.class);
                        drugs.add(drug);
                        drugAdapter.notifyDataSetChanged();
                        resultFound = true;
                    }
                }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (!resultFound) {
                        txtEmpty.setVisibility(View.VISIBLE);
                        btnBack.setVisibility(View.VISIBLE);
                    }else{
                        txtEmpty.setVisibility(View.INVISIBLE);
                        btnBack.setVisibility(View.INVISIBLE);
                    }
                }
            }
        );
        queue.add(jsonObjectRequest);
    }
}
