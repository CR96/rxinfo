package com.github.rxinfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rxinfo.R;
import com.github.rxinfo.model.Drug;
import java.util.ArrayList;

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
                .inflate(R.layout.item_med, parent, false);
        return new ViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        LinearLayout layout = holder.mLinearLayout;
        TextView txt_name = layout.findViewById(R.id.txt_name);
        TextView txt_desc = layout.findViewById(R.id.txt_desc);

        txt_name.setText(mDrugs.get(position).results.get(0).openfda.brandName.toString());
        txt_desc.setText(mDrugs.get(position).results.get(0).openfda.genericName.toString());
    }

    @Override
    public int getItemCount() {
        return mDrugs.size();
    }
}
