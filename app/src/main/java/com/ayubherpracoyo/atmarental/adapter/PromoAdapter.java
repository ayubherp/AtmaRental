package com.ayubherpracoyo.atmarental.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayubherpracoyo.atmarental.databinding.PromoViewBinding;
import com.ayubherpracoyo.atmarental.model.Promo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.viewHolderPromo>
        implements Filterable {

    private List<Promo> promoList, filteredPromoList;
    private Context context;

    public PromoAdapter(List<Promo> promoList, Context context) {
        this.promoList = promoList;
        filteredPromoList = new ArrayList<>(promoList);
        this.context = context;
    }

    public class viewHolderPromo extends RecyclerView.ViewHolder {
        PromoViewBinding promoBinding;
        public viewHolderPromo(@NonNull PromoViewBinding promoBinding){
            super(promoBinding.getRoot());
            this.promoBinding = promoBinding;

            this.promoBinding.layoutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TEST", "onClick: " +getAdapterPosition());
                }
            });
        }
        public void bindView(Promo promo)
        {
            promoBinding.setData(promo);
        }
    }
    @NonNull
    @Override
    public viewHolderPromo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PromoViewBinding binding = PromoViewBinding.inflate(inflater, parent, false);
        return new PromoAdapter.viewHolderPromo(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderPromo holder, int position) {
        Promo promo = filteredPromoList.get(position);
        holder.bindView(filteredPromoList.get(position));

        holder.promoBinding.layoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder =
                        new MaterialAlertDialogBuilder(context);
                materialAlertDialogBuilder.setTitle("Keterangan")
                        .setMessage(promo.getKeterangan())
                        .setNegativeButton("Close", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredPromoList.size();
    }

    public void setPromoList(List<Promo> promoList) {
        this.promoList = promoList;
        filteredPromoList = new ArrayList<>(promoList);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Promo> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(promoList);
                } else {
                    for (Promo promo : promoList) {
                        if (promo.getJenis_promo().toLowerCase().contains(charSequenceString.toLowerCase()))
                            filtered.add(promo);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredPromoList.clear();
                filteredPromoList.addAll((List<Promo>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
