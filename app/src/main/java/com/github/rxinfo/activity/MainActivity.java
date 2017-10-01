package com.github.rxinfo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.rxinfo.R;
import com.github.rxinfo.model.Drug;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Drug> drugs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drugs = getMedsFromStorage();
        FloatingActionButton btnAdd = findViewById(R.id.btn_add);
        RecyclerView lst = findViewById(R.id.lst_meds);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, InputActivity.class);
                startActivity(i);
            }
        });
    }

    private ArrayList<Drug> getMedsFromStorage() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Gson gson = new Gson();

        String json = sharedPrefs.getString("savedMeds", null);
        Type type = new TypeToken<ArrayList<Drug>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}
