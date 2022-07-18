package com.yasinta.kesehatankucing.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yasinta.kesehatankucing.R;
import com.yasinta.kesehatankucing.model.Artikels;
import com.yasinta.kesehatankucing.utils.ApiClient;

import java.util.List;

public class AdapterListArtikel extends
        RecyclerView.Adapter<AdapterListArtikel.HolderItem> {

    List<Artikels> listArtikels;
    Context context;

    public AdapterListArtikel(List<Artikels> listArtikels, Context context) {
        this.listArtikels = listArtikels;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //initiate item layout
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_artikel_home, viewGroup, false);
        HolderItem holderItem = new HolderItem(view);
        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holderItem, int position) {
        //memasukan data ke objek yg sudah dikenalkan di HolderItem
        final Artikels artikels = listArtikels.get(position);

//        Log.d("images", "BASE_URL: " + ApiClient.IMAGE_URL + listArtikels.get(position).getGambar());

        holderItem.tv_judulItemArtikel.setText(artikels.getJudul_artikel());
        Glide.with(context).load(ApiClient.IMAGE_URL + listArtikels.get(position)
        .getGambar()).error(R.drawable.ic_nopic).centerCrop().into(holderItem.iv_gambarItemArtikel);
        holderItem.cv_itemListArtikelHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listArtikels.size();
    }


    //initiate item layout
    public class HolderItem extends RecyclerView.ViewHolder{
        TextView tv_judulItemArtikel;
        ImageView iv_gambarItemArtikel;

        CardView cv_itemListArtikelHome;

        public  HolderItem(View v){
            super(v);

            cv_itemListArtikelHome = v.findViewById(R.id.cv_itemListArtikelHome);
            tv_judulItemArtikel = v.findViewById(R.id.tv_judulItemArtikel);
            iv_gambarItemArtikel = v.findViewById(R.id.iv_gambarItemArtikel);

        }
    }
}
