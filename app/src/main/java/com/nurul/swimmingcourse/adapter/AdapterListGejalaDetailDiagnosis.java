package com.nurul.swimmingcourse.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.model.Gejalas;

import java.util.List;

public class AdapterListGejalaDetailDiagnosis extends
        RecyclerView.Adapter<AdapterListGejalaDetailDiagnosis.HolderItem> {

    List<Gejalas> listGejalas;
    Context context;

    public AdapterListGejalaDetailDiagnosis(List<Gejalas> listGejalas, Context context) {
        this.listGejalas = listGejalas;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //initiate item layout
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_gejala_detail, viewGroup, false);
        HolderItem holderItem = new HolderItem(view);
        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holderItem, @SuppressLint("RecyclerView") int position) {
        //memasukan data ke objek yg sudah dikenalkan di HolderItem
        final Gejalas gejalaDetail = listGejalas.get(position);

        holderItem.tv_gejalaDetailItem.setText(gejalaDetail.getNama_gejala() != null ?gejalaDetail.getNama_gejala():gejalaDetail.getGejala());

//        Log.d("adapter", "onBindViewHolder: " + gejalaDetail.getNama_gejala());

    }

    @Override
    public int getItemCount() {
        return listGejalas.size();
    }


    //initiate item layout
    public class HolderItem extends RecyclerView.ViewHolder{
        TextView tv_gejalaDetailItem;

        CardView cv_itemListGejalaDetail;

        public  HolderItem(View v){
            super(v);

            cv_itemListGejalaDetail = v.findViewById(R.id.cv_itemListGejalaDetail);
            tv_gejalaDetailItem = v.findViewById(R.id.tv_gejalaDetailItem);

        }
    }
}
