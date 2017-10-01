package com.github.rxinfo.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rxinfo.R;
import com.github.rxinfo.activity.ResultActivity;
import com.github.rxinfo.model.Drug;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by corey on 10/1/17.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Drug> mDrugs;

    static class ViewHolder extends RecyclerView.ViewHolder {
        final LinearLayout mLinearLayout;
        ViewHolder(LinearLayout v) {
            super(v);
            mLinearLayout = v;
        }
    }

    public DrugAdapter(
            Context context,
            ArrayList<Drug> drugs) {
        mContext = context;
        mDrugs = drugs;
    }

    @Override
    public DrugAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new ViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LinearLayout layout = holder.mLinearLayout;
        TextView txt_name = layout.findViewById(R.id.txt_name);
        TextView txt_desc = layout.findViewById(R.id.txt_desc);
        Button btn_save = layout.findViewById(R.id.btn_save);

        txt_name.setText(mDrugs.get(position).results.get(0).openfda.brandName.toString());
        txt_desc.setText(mDrugs.get(position).results.get(0).openfda.genericName.toString());

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMedToStorage(mDrugs.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDrugs.size();
    }

    private ArrayList<Drug> getMedsFromStorage() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();

        String json = sharedPrefs.getString("savedMeds", null);
        Type type = new TypeToken<ArrayList<Drug>>(){}.getType();
        return gson.fromJson(json, type);
    }

    private void saveMedToStorage(Drug drug) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json;
        if (getMedsFromStorage() == null) {
            ArrayList<Drug> firstStore = new ArrayList<>();
            firstStore.add(drug);
            json = gson.toJson(firstStore);
        }else {
            json = gson.toJson(getMedsFromStorage().add(drug));
        }

        editor.putString("savedMeds", json);
        editor.apply();
    }
}
