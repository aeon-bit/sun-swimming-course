package com.nurul.swimmingcourse.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.dialog.DialogDetailJadwalLatihan;
import com.nurul.swimmingcourse.model.JadwalLatihans;
import com.nurul.swimmingcourse.utils.ApiClient;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListJadwalLatihan extends
        RecyclerView.Adapter<AdapterListJadwalLatihan.HolderItem> {

    List<JadwalLatihans> listJadwalLatihans;
    Context context;

    public AdapterListJadwalLatihan(List<JadwalLatihans> listJadwalLatihans, Context context) {
        this.listJadwalLatihans = listJadwalLatihans;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //initiate item layout
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_daftar_jadwal_latihan, viewGroup, false);
        HolderItem holderItem = new HolderItem(view);
        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holderItem, @SuppressLint("RecyclerView") int position) {
        //memasukan data ke objek yg sudah dikenalkan di HolderItem
        final JadwalLatihans pelatih = listJadwalLatihans.get(position);

        holderItem.tv_lokasiJadwalList.setText(pelatih.getLokasi());
        holderItem.tv_tglJadwalList.setText(pelatih.getHari());

        holderItem.cv_itemListJadwalLatihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogDetailJadwalLatihan dialogDetailJadwalLatihan = new DialogDetailJadwalLatihan(v.getContext());
                dialogDetailJadwalLatihan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView tv_tglLatihanDialog = dialogDetailJadwalLatihan.findViewById(R.id.tv_tglLatihanDialog);
                TextView tv_jamLatihanDialog = dialogDetailJadwalLatihan.findViewById(R.id.tv_jamLatihanDialog);
                TextView tv_pelatihLatihanDialog = dialogDetailJadwalLatihan.findViewById(R.id.tv_pelatihLatihanDialog);
                TextView tv_lokasiLatihanDialog = dialogDetailJadwalLatihan.findViewById(R.id.tv_lokasiLatihanDialog);
                LinearLayout ly_btnCloseDialog = dialogDetailJadwalLatihan.findViewById(R.id.ly_btnCloseDialog);

                tv_tglLatihanDialog.setText(": " + listJadwalLatihans.get(position).getHari());
                tv_jamLatihanDialog.setText(": " + listJadwalLatihans.get(position).getJam());
                tv_pelatihLatihanDialog.setText(": " + listJadwalLatihans.get(position).getPelatih());
                tv_lokasiLatihanDialog.setText(": " + listJadwalLatihans.get(position).getLokasi());

                ly_btnCloseDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogDetailJadwalLatihan.dismiss();
                    }
                });
                dialogDetailJadwalLatihan.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listJadwalLatihans.size();
    }


    //initiate item layout
    public class HolderItem extends RecyclerView.ViewHolder{
        TextView tv_lokasiJadwalList, tv_tglJadwalList;

        CardView cv_itemListJadwalLatihan;

        public  HolderItem(View v){
            super(v);

            cv_itemListJadwalLatihan = v.findViewById(R.id.cv_itemListJadwalLatihan);
            tv_lokasiJadwalList = v.findViewById(R.id.tv_lokasiJadwalList);
            tv_tglJadwalList = v.findViewById(R.id.tv_tglJadwalList);

        }
    }
}
