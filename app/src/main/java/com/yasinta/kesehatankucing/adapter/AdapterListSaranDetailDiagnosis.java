package com.yasinta.kesehatankucing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yasinta.kesehatankucing.R;
import com.yasinta.kesehatankucing.model.HasilSarans;

import java.util.List;

public class AdapterListSaranDetailDiagnosis extends
        RecyclerView.Adapter<AdapterListSaranDetailDiagnosis.HolderItem> {

    List<HasilSarans> listHasilSarans;
    Context context;

    public AdapterListSaranDetailDiagnosis(List<HasilSarans> listHasilSarans, Context context) {
        this.listHasilSarans = listHasilSarans;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //initiate item layout
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_hasil_detail, viewGroup, false);
        HolderItem holderItem = new HolderItem(view);
        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holderItem, @SuppressLint("RecyclerView") int position) {
        //memasukan data ke objek yg sudah dikenalkan di HolderItem
        final HasilSarans hasilSarans = listHasilSarans.get(position);

        holderItem.tv_hasilDetailItem.setText(hasilSarans.getSaran_pengobatan() != null ?hasilSarans.getSaran_pengobatan():hasilSarans.getSaran_pengobatan());

        Log.d("adapter", "SARAN: " + hasilSarans.getSaran_pengobatan());

    }

    @Override
    public int getItemCount() {
        return listHasilSarans.size();
    }


    //initiate item layout
    public class HolderItem extends RecyclerView.ViewHolder{
        TextView tv_hasilDetailItem;

        CardView cv_itemListHasilDetail;

        public  HolderItem(View v){
            super(v);

            cv_itemListHasilDetail = v.findViewById(R.id.cv_itemListHasilDetail);
            tv_hasilDetailItem = v.findViewById(R.id.tv_hasilDetailItem);

        }
    }
}
