package com.github.rxinfo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LinearLayout layout = holder.mLinearLayout;
        TextView txt_name = layout.findViewById(R.id.txt_name);

        try {
            final String brandName = mDrugs.get(position).results.get(0).openfda.brandName.toString()
                    .replaceAll("\\[", "")
                    .replaceAll("]", "");

            final String genericName = mDrugs.get(holder.getAdapterPosition()).results.get(0).openfda.genericName.toString()
                    .replaceAll("\\[", "")
                    .replaceAll("]", "");

            final String warnings = mDrugs.get(holder.getAdapterPosition()).results.get(0).warnings.toString()
                    .replaceAll("\\[", "")
                    .replaceAll("]", "");

            txt_name.setText(brandName);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(brandName);
                    builder.setMessage(genericName + "\n\n" + warnings);
                    builder.setCancelable(true);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }catch (NullPointerException e) {
            // Prevent a crash due to incomplete data
        }
    }

    @Override
    public int getItemCount() {
        return mDrugs.size();
    }
}
