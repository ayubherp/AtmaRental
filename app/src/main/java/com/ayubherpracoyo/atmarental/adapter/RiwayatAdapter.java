package com.ayubherpracoyo.atmarental.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ayubherpracoyo.atmarental.MainActivity;
import com.ayubherpracoyo.atmarental.R;
import com.ayubherpracoyo.atmarental.databinding.MobilDetailDialogBinding;
import com.ayubherpracoyo.atmarental.databinding.RatingDriverDialogBinding;
import com.ayubherpracoyo.atmarental.databinding.RiwayatViewBinding;
import com.ayubherpracoyo.atmarental.model.Transaksi;
import com.ayubherpracoyo.atmarental.model.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.viewHolderRiwayat>
        implements Filterable {

    private List<Transaksi> riwayatList, filteredRiwayatList;
    private Context context;
    private User user;

    public RiwayatAdapter(List<Transaksi> riwayatList, Context context) {
        this.riwayatList = riwayatList;
        filteredRiwayatList = new ArrayList<>(riwayatList);
        this.context = context;
    }

    public class viewHolderRiwayat extends RecyclerView.ViewHolder {
        RiwayatViewBinding riwayatBinding;
        public viewHolderRiwayat(@NonNull RiwayatViewBinding riwayatBinding){
            super(riwayatBinding.getRoot());
            this.riwayatBinding = riwayatBinding;

            this.riwayatBinding.layoutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TEST", "onClick: " +getAdapterPosition());
                }
            });
        }
        public void bindView(Transaksi riwayat)
        {
            riwayatBinding.setData(riwayat);
        }
    }
    @NonNull
    @Override
    public viewHolderRiwayat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RiwayatViewBinding binding = RiwayatViewBinding.inflate(inflater, parent, false);
        return new RiwayatAdapter.viewHolderRiwayat(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderRiwayat holder, int position) {
        Transaksi riwayat = filteredRiwayatList.get(position);
        holder.bindView(filteredRiwayatList.get(position));
        if(riwayat.getStatus_rating()==0 && riwayat.getId_driver()!=null && !riwayat.getId_driver().equals("DRV000000-000")){
            holder.riwayatBinding.layoutCard.setBackgroundTintList(context.getResources().getColorStateList(R.color.yellow));
            holder.riwayatBinding.layoutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getRootView().getContext());
                    RatingDriverDialogBinding ratingDriverDialogBinding =
                            DataBindingUtil.inflate(
                                    LayoutInflater.from(v.getRootView().getContext()), R.layout.rating_driver_dialog, null, false
                            );
                    alertDialog.setView(ratingDriverDialogBinding.getRoot());

                    ratingDriverDialogBinding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            ratingDriverDialogBinding.ratingNumber.setText(String.valueOf(rating));
                            Toast.makeText(context,"Rating Terpilih : " +rating, Toast.LENGTH_SHORT).show();
                        }
                    });

                    ratingDriverDialogBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity) context).submitRating(riwayat.getId(),ratingDriverDialogBinding.ratingBar.getRating());
                        }
                    });

                    alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            });
        }
        else{
            holder.riwayatBinding.layoutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Transaksi selesai..", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return filteredRiwayatList.size();
    }

    public void setRiwayatList(List<Transaksi> riwayatList, User user) {
        this.user = user;
        for(Transaksi riwayat : riwayatList)
        {
            if(riwayat.getId_customer().equals(user.getId_role()) && riwayat.getStatus_sewa()==2)
            {
                this.riwayatList.add(riwayat);
            }
        }
        filteredRiwayatList = new ArrayList<>(this.riwayatList);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Transaksi> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(riwayatList);
                } else {
                    for (Transaksi riwayat : riwayatList) {
                        if (riwayat.getNama_mobil().toLowerCase().contains(charSequenceString.toLowerCase()))
                            filtered.add(riwayat);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredRiwayatList.clear();
                filteredRiwayatList.addAll((List<Transaksi>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
