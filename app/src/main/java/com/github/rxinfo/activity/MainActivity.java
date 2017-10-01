package com.github.rxinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.rxinfo.R;
import com.github.rxinfo.adapter.DrugAdapter;
import com.github.rxinfo.model.Drug;
import com.github.rxinfo.util.NdcUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_DRUG_ITEM = 1;

    ArrayList<Drug> drugs = new ArrayList<>();
    DrugAdapter drugAdapter;

    TextView txtEmpty;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmpty = findViewById(R.id.txt_empty);
        progress = findViewById(R.id.progress);

        if (savedInstanceState == null) {
            ArrayList<Drug> stored = getMedsFromStorage();
            if (stored != null) {
                if (stored.size() != 0) {
                    txtEmpty.setVisibility(View.INVISIBLE);
                    drugs.addAll(stored);
                }
            }
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        drugAdapter = new DrugAdapter(MainActivity.this, drugs);

        RecyclerView lst_meds = findViewById(R.id.lst_meds);
        lst_meds.setLayoutManager(layoutManager);
        lst_meds.setAdapter(drugAdapter);

        FloatingActionButton btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivityForResult(intent, REQUEST_DRUG_ITEM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_DRUG_ITEM && resultCode == Activity.RESULT_OK) {
            String[] possibleNdcs = NdcUtils.getPossibleNdcs(data.getStringExtra("ndc"));
            ArrayList<String> possibleNdcsList = new ArrayList<>(Arrays.asList(possibleNdcs));
            getDrugInfo(possibleNdcsList);
        }
    }

    private void getDrugInfo(final List<String> possibleNdcs) {
        txtEmpty.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.fda.gov/drug/label.json?search=\""
                + possibleNdcs.get(0) + "\"";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new GsonBuilder()
                            .create();
                    Drug drug = gson
                            .fromJson(response.toString(), Drug.class);
                    drugs.add(drug);
                    drugAdapter.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);
                }
            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (possibleNdcs.size() == 1) {
                    if (drugs.size() == 0) {
                        txtEmpty.setVisibility(View.VISIBLE);
                    }
                    progress.setVisibility(View.GONE);
                    Toast.makeText(
                            MainActivity.this,
                            getString(R.string.result_not_found),
                            Toast.LENGTH_LONG).show();
                }else{
                    possibleNdcs.remove(0);
                    getDrugInfo(possibleNdcs);
                }
            }
        }
        );
        queue.add(jsonObjectRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveMedsToStorage(drugs);
    }

    private void saveMedsToStorage(ArrayList<Drug> drugs) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(
                MainActivity.this
        );

        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(drugs);
        editor.putString("savedMeds", json);
        editor.apply();

    }

    private ArrayList<Drug> getMedsFromStorage() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(
                MainActivity.this
        );

        Gson gson = new Gson();
        String json = sharedPrefs.getString("savedMeds", null);
        Type type = new TypeToken<ArrayList<Drug>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
