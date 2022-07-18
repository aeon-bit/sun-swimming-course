package com.yasinta.kesehatankucing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yasinta.kesehatankucing.R;
import com.yasinta.kesehatankucing.model.Histories;

import java.util.List;

public class AdapterListHistoryPeriksa extends
        RecyclerView.Adapter<AdapterListHistoryPeriksa.HolderItem> {

    List<Histories> listHistories;
    Context context;

    public AdapterListHistoryPeriksa(List<Histories> listHistories, Context context) {
        this.listHistories = listHistories;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //initiate item layout
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_histori_periksa, viewGroup, false);
        HolderItem holderItem = new HolderItem(view);
        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holderItem, int position) {
        //memasukan data ke objek yg sudah dikenalkan di HolderItem
        final Histories historiPeriksa = listHistories.get(position);

        holderItem.tv_namaKucingItemHistory.setText(historiPeriksa.getUsers().getNama_kucing());
        holderItem.tv_tanggalKucingItemHistory.setText(historiPeriksa.getTanggal());

        holderItem.cv_itemListHistoriPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listHistories.size();
    }


    //initiate item layout
    public class HolderItem extends RecyclerView.ViewHolder{
        TextView tv_namaKucingItemHistory, tv_tanggalKucingItemHistory;

        CardView cv_itemListHistoriPeriksa;

        public  HolderItem(View v){
            super(v);

            cv_itemListHistoriPeriksa = v.findViewById(R.id.cv_itemListHistoriPeriksa);
            tv_namaKucingItemHistory = v.findViewById(R.id.tv_namaKucingItemHistory);
            tv_tanggalKucingItemHistory = v.findViewById(R.id.tv_tanggalKucingItemHistory);

        }
    }
}
