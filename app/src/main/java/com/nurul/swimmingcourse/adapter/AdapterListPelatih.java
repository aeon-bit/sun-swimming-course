package com.nurul.swimmingcourse.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.dialog.DialogConfirmLogout;
import com.nurul.swimmingcourse.dialog.DialogDetailPelatih;
import com.nurul.swimmingcourse.model.Pelatihs;
import com.nurul.swimmingcourse.ui.daftrapelatih.DetailJenisPenyakitFragment;
import com.nurul.swimmingcourse.utils.ApiClient;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListPelatih extends
        RecyclerView.Adapter<AdapterListPelatih.HolderItem> {

    List<Pelatihs> listPelatihs;
    Context context;

    public AdapterListPelatih(List<Pelatihs> listPelatihs, Context context) {
        this.listPelatihs = listPelatihs;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //initiate item layout
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_daftar_pelatih, viewGroup, false);
        HolderItem holderItem = new HolderItem(view);
        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holderItem, @SuppressLint("RecyclerView") int position) {
        //memasukan data ke objek yg sudah dikenalkan di HolderItem
        final Pelatihs pelatih = listPelatihs.get(position);

        holderItem.tv_namaPelatihList.setText(pelatih.getNama());
        holderItem.tv_alamatPelatihList.setText(pelatih.getAlamat());

        holderItem.cv_itemListPelatih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Fragment detailJenisPenyakit = new DetailJenisPenyakitFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("id", listPelatihs.get(position).getId());
//                bundle.putString("nama", listPelatihs.get(position).getNama());
//                bundle.putString("alamat", listPelatihs.get(position).getAlamat());
//                bundle.putString("no_telp", listPelatihs.get(position).getNo_telp());
//                bundle.putString("foto", listPelatihs.get(position).getFoto());
//                detailJenisPenyakit.setArguments(bundle);
//                ((FragmentActivity)context).getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.nav_host_fragment_content_main, detailJenisPenyakit).addToBackStack("fromDetailJenisPenyakit")
//                        .commit();

                DialogDetailPelatih dialogDetailPelatih = new DialogDetailPelatih(v.getContext());
                dialogDetailPelatih.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                CircleImageView icv_avatarDetailPelatih = dialogDetailPelatih.findViewById(R.id.icv_avatarDetailPelatih);
                TextView tv_namaPelatihProfil = dialogDetailPelatih.findViewById(R.id.tv_namaPelatihProfil);
                TextView tv_alamatPelatihProfil = dialogDetailPelatih.findViewById(R.id.tv_alamatPelatihProfil);
                TextView tv_noHpPelatihProfil = dialogDetailPelatih.findViewById(R.id.tv_noHpPelatihProfil);
                LinearLayout ly_btnCloseDialog = dialogDetailPelatih.findViewById(R.id.ly_btnCloseDialog);

                Glide.with(v).load(ApiClient.IMAGE_URL + listPelatihs.get(position).getFoto())
                                .error(R.drawable.ic_avatar).into(icv_avatarDetailPelatih);
                tv_namaPelatihProfil.setText(listPelatihs.get(position).getNama());
                tv_alamatPelatihProfil.setText(listPelatihs.get(position).getAlamat());
                tv_noHpPelatihProfil.setText(listPelatihs.get(position).getNo_telp());

                ly_btnCloseDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogDetailPelatih.dismiss();
                    }
                });
                dialogDetailPelatih.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPelatihs.size();
    }


    //initiate item layout
    public class HolderItem extends RecyclerView.ViewHolder{
        TextView tv_namaPelatihList, tv_alamatPelatihList;

        CardView cv_itemListPelatih;

        public  HolderItem(View v){
            super(v);

            cv_itemListPelatih = v.findViewById(R.id.cv_itemListPelatih);
            tv_namaPelatihList = v.findViewById(R.id.tv_namaPelatihList);
            tv_alamatPelatihList = v.findViewById(R.id.tv_alamatPelatihList);

        }
    }
}
