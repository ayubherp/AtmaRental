package com.ayubherpracoyo.atmarental.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayubherpracoyo.atmarental.MainActivity;
import com.ayubherpracoyo.atmarental.TransaksiActivity;
import com.ayubherpracoyo.atmarental.databinding.TransaksiViewBinding;
import com.ayubherpracoyo.atmarental.model.Transaksi;
import com.ayubherpracoyo.atmarental.model.Transaksi;
import com.ayubherpracoyo.atmarental.model.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.viewHolderTransaksi>
        implements Filterable {

    private List<Transaksi> transaksiList, filteredTransaksiList;
    private Context context;
    private User user;

    public TransaksiAdapter(List<Transaksi> transaksiList, Context context) {
        this.transaksiList = transaksiList;
        filteredTransaksiList = new ArrayList<>(transaksiList);
        this.context = context;
    }

    public class viewHolderTransaksi extends RecyclerView.ViewHolder {
        TransaksiViewBinding transaksiBinding;
        public viewHolderTransaksi(@NonNull TransaksiViewBinding transaksiBinding){
            super(transaksiBinding.getRoot());
            this.transaksiBinding = transaksiBinding;

            this.transaksiBinding.layoutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TEST", "onClick: " +getAdapterPosition());
                }
            });
        }
        public void bindView(Transaksi transaksi)
        {
            transaksiBinding.setData(transaksi);
            DecimalFormat rupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance(new Locale("in", "ID"));
            transaksiBinding.txtTotalBayar.setText(rupiah.format(transaksi.getHarga_sewa()));
        }
    }
    @NonNull
    @Override
    public viewHolderTransaksi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TransaksiViewBinding binding = TransaksiViewBinding.inflate(inflater, parent, false);
        return new TransaksiAdapter.viewHolderTransaksi(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderTransaksi holder, int position) {
        Transaksi transaksi = filteredTransaksiList.get(position);
        holder.bindView(filteredTransaksiList.get(position));

        if(transaksi.getStatus_pembayaran()==0)
        {
            if(transaksi.getMetode_pembayaran().equals("Cash"))
                holder.transaksiBinding.btnUpload.setVisibility(View.GONE);
            else{
                holder.transaksiBinding.btnUpload.setVisibility(View.VISIBLE);
                holder.transaksiBinding.btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            holder.transaksiBinding.btnEdit.setVisibility(View.VISIBLE);
            holder.transaksiBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) context).bringTransaksiToUpdateTransaksi(transaksi.getId(), transaksi.getId_mobil());
                }
            });
        }
        else{
            holder.transaksiBinding.btnEdit.setVisibility(View.GONE);
            holder.transaksiBinding.btnUpload.setVisibility(View.GONE);
        }

        holder.transaksiBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder =
                        new MaterialAlertDialogBuilder(context);
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                        .setMessage("Apakah Anda yakin akan menghapus transaksi ini?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((MainActivity) context).deleteTransaksi(transaksi.getId());
                            }
                        })
                        .show();
            }
        });
        holder.transaksiBinding.layoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transaksi.getStatus_pembayaran()==0)
                {
                    if(holder.transaksiBinding.txtMetode.getText().toString().equals("Cash")) {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder =
                                new MaterialAlertDialogBuilder(context);
                        materialAlertDialogBuilder.setTitle("Keterangan")
                                .setMessage("Silakan melakukan pembayaran langsung ditempat sebelum melakukan penyewaan. Senilai : Rp." +transaksi.getTotal_pembayaran() + ". Terima Kasih :D")
                                .setNegativeButton("Close", null)
                                .show();
                    }
                    else if(holder.transaksiBinding.txtMetode.getText().toString().equals("Transfer")){
                        MaterialAlertDialogBuilder materialAlertDialogBuilder =
                                new MaterialAlertDialogBuilder(context);
                        materialAlertDialogBuilder.setTitle("Keterangan")
                                .setMessage("Silakan transfer ke nomor rekening ini : 777888777 (Bank Terbaik) atas nama Atma Rental. Senilai : Rp." +transaksi.getTotal_pembayaran() +
                                        ". Lalu kirimkan bukti transfer yang diakses melalui icon biru halaman Transaksi ini. Terima kasih :D")
                                .setNegativeButton("Close", null)
                                .show();
                    }
                }
                else
                {
                    Toast.makeText(context,"Akses pembayaran sudah dilakukan..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredTransaksiList.size();
    }

    public void setTransaksiList(List<Transaksi> transaksiList, User user) {
        this.user = user;
        for(Transaksi transaksi : transaksiList)
        {
            if(transaksi.getId_customer().equals(user.getId_role()) && transaksi.getStatus_sewa()!=2)
            {
                this.transaksiList.add(transaksi);
            }
        }
        filteredTransaksiList = new ArrayList<>(this.transaksiList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Transaksi> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(transaksiList);
                } else {
                    for (Transaksi transaksi : transaksiList) {
                        if (transaksi.getNama_mobil().toLowerCase().contains(charSequenceString.toLowerCase()))
                            filtered.add(transaksi);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredTransaksiList.clear();
                filteredTransaksiList.addAll((List<Transaksi>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
