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
import com.nurul.swimmingcourse.model.JenisPenyakits;
import com.nurul.swimmingcourse.ui.jenispenyakit.DetailJenisPenyakitFragment;

import java.util.List;

public class AdapterListJenisPenyakit extends
        RecyclerView.Adapter<AdapterListJenisPenyakit.HolderItem> {

    List<JenisPenyakits> listJenisPenyakits;
    Context context;

    public AdapterListJenisPenyakit(List<JenisPenyakits> listJenisPenyakits, Context context) {
        this.listJenisPenyakits = listJenisPenyakits;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //initiate item layout
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_jenis_penyakit, viewGroup, false);
        HolderItem holderItem = new HolderItem(view);
        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holderItem, @SuppressLint("RecyclerView") int position) {
        //memasukan data ke objek yg sudah dikenalkan di HolderItem
        final JenisPenyakits jenisPenyakit = listJenisPenyakits.get(position);

        holderItem.tv_namaJenisPenyakit.setText(jenisPenyakit.getNama_penyakit());

        holderItem.cv_itemListJenisPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment detailJenisPenyakit = new DetailJenisPenyakitFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", listJenisPenyakits.get(position).getId());
                bundle.putString("kode_penyakit", listJenisPenyakits.get(position).getKode_penyakit());
                bundle.putString("nama_penyakit", listJenisPenyakits.get(position).getNama_penyakit());
                bundle.putString("deskripsi", listJenisPenyakits.get(position).getDeskripsi());
                bundle.putString("pengobatan", listJenisPenyakits.get(position).getPengobatan());
                detailJenisPenyakit.setArguments(bundle);
                ((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, detailJenisPenyakit).addToBackStack("fromDetailJenisPenyakit")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listJenisPenyakits.size();
    }


    //initiate item layout
    public class HolderItem extends RecyclerView.ViewHolder{
        TextView tv_namaJenisPenyakit;

        CardView cv_itemListJenisPenyakit;

        public  HolderItem(View v){
            super(v);

            cv_itemListJenisPenyakit = v.findViewById(R.id.cv_itemListJenisPenyakit);
            tv_namaJenisPenyakit = v.findViewById(R.id.tv_namaJenisPenyakit);

        }
    }
}
