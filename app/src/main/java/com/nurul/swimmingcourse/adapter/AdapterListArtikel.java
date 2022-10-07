package com.nurul.swimmingcourse.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.model.Artikels;
import com.nurul.swimmingcourse.ui.artikel.DetailArtikel;
import com.nurul.swimmingcourse.utils.ApiClient;

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
    public void onBindViewHolder(@NonNull HolderItem holderItem, @SuppressLint("RecyclerView") int position) {
        //memasukan data ke objek yg sudah dikenalkan di HolderItem
        final Artikels artikels = listArtikels.get(position);

//        Log.d("images", "BASE_URL: " + ApiClient.IMAGE_URL + listArtikels.get(position).getGambar());

        holderItem.tv_judulItemArtikel.setText(artikels.getJudul_info());
        Glide.with(context).load(ApiClient.IMAGE_URL_ARTIKEL + listArtikels.get(position)
        .getFoto()).error(R.drawable.ic_nopic).centerCrop().into(holderItem.iv_gambarItemArtikel);

        holderItem.cv_itemListArtikelHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment detailFragment = new DetailArtikel();
//                Bundle bundle = new Bundle();
//                bundle.putString("id", listArtikels.get(position).getId());
//                bundle.putString("judul_artikel", listArtikels.get(position).getJudul_artikel());
//                bundle.putString("deskripsi", listArtikels.get(position).getDeskripsi());
//                bundle.putString("gambar", listArtikels.get(position).getGambar());

//                detailFragment.setArguments(bundle);
//                ((FragmentActivity)context).getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.nav_host_fragment_content_main, detailFragment).addToBackStack("fromDetailArtikel")
//                        .commit();
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
