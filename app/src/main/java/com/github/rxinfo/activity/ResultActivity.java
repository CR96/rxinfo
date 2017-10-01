package com.github.rxinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.rxinfo.R;
import com.github.rxinfo.model.Drug;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    List<Drug> drugs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final TextView txtName = findViewById(R.id.txt_name);

        Intent result  = getIntent();
        String[] possibleNdcs = result.getStringArrayExtra("possibleNdcs");

        for (String possibleNdc : possibleNdcs) {
            getDrugInfo(possibleNdc);
        }
    }

    private void getDrugInfo(String possibleNdc) {
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
                        Drug drug = new Gson()
                                .fromJson(response.toString(), Drug.class);
                        drugs.add(drug);
                        Toast.makeText(ResultActivity.this,
                                drug.results.get(0).openfda.brandName.toString(),
                                Toast.LENGTH_LONG)
                        .show();
                    }
                }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
        );
        queue.add(jsonObjectRequest);
    }
}
