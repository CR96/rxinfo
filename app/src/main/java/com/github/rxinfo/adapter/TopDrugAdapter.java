package com.github.rxinfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rxinfo.R;
import com.github.rxinfo.model.TopDrug;

import java.util.List;

public class TopDrugAdapter extends RecyclerView.Adapter<TopDrugAdapter.ViewHolder> {

    final private Context mContext;
    final private List<TopDrug> mTopDrugList;

    public TopDrugAdapter(
            Context context,
            List<TopDrug> topDrugList) {
        mContext = context;
        mTopDrugList = topDrugList;
    }

        @Override
        public TopDrugAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_top_drug, parent, false);
            return new ViewHolder((LinearLayout) v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            LinearLayout layout = holder.mLinearLayout;

            TextView txt_ranking = layout.findViewById(R.id.txt_ranking);
            TextView txt_brand_name = layout.findViewById(R.id.txt_brand_name);
            TextView txt_generic_name = layout.findViewById(R.id.txt_generic_name);

            final String ranking = String.valueOf(position + 1);

            final String brandName =
                    mTopDrugList.get(holder.getAdapterPosition()).brandName;

            final String genericName =
                    mTopDrugList.get(holder.getAdapterPosition()).genericName;

            txt_ranking.setText(ranking);
            txt_brand_name.setText(brandName);
            txt_generic_name.setText(genericName);
        }

        @Override
        public int getItemCount() {
            return mTopDrugList.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final LinearLayout mLinearLayout;

            ViewHolder(LinearLayout v) {
                super(v);
                mLinearLayout = v;
            }
    }
}