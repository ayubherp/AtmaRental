package com.ayubherpracoyo.atmarental.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ayubherpracoyo.atmarental.MainActivity;
import com.ayubherpracoyo.atmarental.R;
import com.ayubherpracoyo.atmarental.databinding.MobilDetailDialogBinding;
import com.ayubherpracoyo.atmarental.databinding.MobilViewBinding;
import com.ayubherpracoyo.atmarental.model.Mobil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MobilAdapter extends RecyclerView.Adapter<MobilAdapter.viewHolderMobil>
        implements Filterable {
    private List<Mobil> mobilList, filteredMobilList;
    private Context context;
    public static final String GENERAL_URL = "http://atmarental.my.id";

    public MobilAdapter(List<Mobil> itemList, Context context){
        this.mobilList = itemList;
        filteredMobilList = new ArrayList<>(itemList);
        this.context = context;
    }

    public class viewHolderMobil extends RecyclerView.ViewHolder {
        MobilViewBinding binding;
        public viewHolderMobil(@NonNull MobilViewBinding binding){
            super(binding.getRoot());
            this.binding = binding;
            this.binding.cvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TEST", "onClick: " +getAdapterPosition());
                }
            });
        }
        public void bindView(Mobil mobil)
        {
            binding.setDataItem(mobil);
            binding.tvName.setText(mobil.getNama_mobil());
            binding.tvType.setText(mobil.getTipe_mobil());
            DecimalFormat rupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance(new Locale("in", "ID"));
            binding.tvPrice.setText(rupiah.format(mobil.getHarga_sewa_per_hari()));
        }
    }

    @NonNull
    @Override
    public viewHolderMobil onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MobilViewBinding bindingItem = MobilViewBinding.inflate(layoutInflater, parent, false);
        return new viewHolderMobil(bindingItem);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderMobil holder, int position) {
        Mobil mobil = filteredMobilList.get(position);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(context.getApplicationContext()).load(mobil.getLink_foto())
                .apply(options).into(holder.binding.ivImage);

        holder.binding.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getRootView().getContext());
                MobilDetailDialogBinding mobilDetailDialogBinding =
                        DataBindingUtil.inflate(
                                LayoutInflater.from(v.getRootView().getContext()), R.layout.mobil_detail_dialog, null, false
                        );
                mobilDetailDialogBinding.setItemData(mobil);

                DecimalFormat rupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance(new Locale("in", "ID"));
                mobilDetailDialogBinding.hargaSewa.setText(rupiah.format(mobil.getHarga_sewa_per_hari()));

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round);
                String link = GENERAL_URL + mobil.getFoto_mobil();
                Glide.with(context.getApplicationContext()).asBitmap().load(link)
                        .apply(options).dontAnimate().into(mobilDetailDialogBinding.imageDialog);

                alertDialog.setView(mobilDetailDialogBinding.getRoot());

                if(mobil.getStatus_mobil()==1)
                {
                    mobilDetailDialogBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (context instanceof MainActivity)
                                ((MainActivity) context).bringMobilToTransaksi(mobil.getId());
                        }
                    });
                }
                else
                {
                    mobilDetailDialogBinding.btnAdd.setEnabled(false);
                }

                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
        holder.bindView(filteredMobilList.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredMobilList.size();
    }

    public void setItemList(List<Mobil> mobilList) {
        this.mobilList = mobilList;
        filteredMobilList = new ArrayList<>(mobilList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Mobil> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(mobilList);
                } else {
                    for (Mobil mobil : mobilList) {
                        if (mobil.getTipe_mobil().toLowerCase().contains(charSequenceString.toLowerCase()))
                            filtered.add(mobil);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredMobilList.clear();
                filteredMobilList.addAll((List<Mobil>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

}
