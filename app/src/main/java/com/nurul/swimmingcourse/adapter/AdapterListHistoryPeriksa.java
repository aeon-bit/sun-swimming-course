package com.nurul.swimmingcourse.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.model.Histories;
import com.nurul.swimmingcourse.ui.history.DetailHistoriPeriksa;

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
    public void onBindViewHolder(@NonNull HolderItem holderItem, @SuppressLint("RecyclerView") int position) {
        //memasukan data ke objek yg sudah dikenalkan di HolderItem
        final Histories historiPeriksa = listHistories.get(position);

        holderItem.tv_namaKucingItemHistory.setText(historiPeriksa.getNama_kucing());
        holderItem.tv_tanggalKucingItemHistory.setText(historiPeriksa.getTanggal());

        holderItem.cv_itemListHistoriPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment detailHistoriPeriksa = new DetailHistoriPeriksa();
                Bundle bundle = new Bundle();
                bundle.putString("id", listHistories.get(position).getId());
//                bundle.putString("nama_kucing", listHistories.get(position).getNama_kucing());
//                bundle.putString("jenis_kucing", listHistories.get(position).getJenis_kucing());
                detailHistoriPeriksa.setArguments(bundle);

                ((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, detailHistoriPeriksa).addToBackStack("fromDetailHistoriPeriksa")
                        .commit();
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
