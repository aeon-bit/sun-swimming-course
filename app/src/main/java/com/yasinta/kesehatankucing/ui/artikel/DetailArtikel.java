package com.yasinta.kesehatankucing.ui.artikel;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.yasinta.kesehatankucing.R;
import com.yasinta.kesehatankucing.activity.MainActivity;
import com.yasinta.kesehatankucing.adapter.AdapterListGejalaDetailDiagnosis;
import com.yasinta.kesehatankucing.model.Gejalas;
import com.yasinta.kesehatankucing.model.Histories;
import com.yasinta.kesehatankucing.model.ResponseDetailArtikel;
import com.yasinta.kesehatankucing.model.ResponseTesKesehatan;
import com.yasinta.kesehatankucing.utils.ApiClient;
import com.yasinta.kesehatankucing.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailArtikel extends Fragment {
//    private String sId;

    TextView tv_JudulDetailArtikel, tv_deskripsiDetailArtikel;
    ImageView iv_gambarDetailArtikel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_artikel, container, false);

        iv_gambarDetailArtikel = root.findViewById(R.id.iv_gambarDetailArtikel);
        tv_JudulDetailArtikel = root.findViewById(R.id.tv_JudulDetailArtikel);
        tv_deskripsiDetailArtikel = root.findViewById(R.id.tv_deskripsiDetailArtikel);

        catchValues(root);

        return root;
    }

    private void catchValues(View root) {
        if (getArguments() != null) {
            String sId = getArguments().getString("id");
            String sJudul = getArguments().getString("judul_artikel");
            String sDeskripsi = getArguments().getString("deskripsi");
            String sGambar = getArguments().getString("gambar");

            tv_JudulDetailArtikel.setText(sJudul);
            tv_deskripsiDetailArtikel.setText(sDeskripsi);

            Glide.with(getContext()).load(ApiClient.IMAGE_URL + sGambar)
                    .error(R.drawable.ic_nopic).centerCrop().into(iv_gambarDetailArtikel);
//            requestDetailArtikeliById(sId);


//            Log.d("detail", "catchValues: " + getArguments().getString("nama_penyakit"));
        } else {
            Log.d("detail", "catchValues: NULL");
        }
    }

    private void requestDetailArtikeliById(String sId) {
        Call<ResponseDetailArtikel> call = MainActivity.apiInterface.getDetailArtikel(
                "Bearer " + SessionManager.getToken(), sId
        );

        Fragment self = this;
        call.enqueue(new Callback<ResponseDetailArtikel>() {
            @Override
            public void onResponse(Call<ResponseDetailArtikel> call, retrofit2.Response<ResponseDetailArtikel> response) {
                Log.d("respon", "onResponse Detail Artikel: " + response.toString());

                tv_JudulDetailArtikel.setText(response.body().getData().getJudul_artikel());
                tv_deskripsiDetailArtikel.setText(response.body().getData().getDeskripsi());

                Glide.with(getContext()).load(ApiClient.IMAGE_URL + response.body().getData().getGambar())
                        .error(R.drawable.ic_nopic).centerCrop().into(iv_gambarDetailArtikel);
            }

            @Override
            public void onFailure(Call<ResponseDetailArtikel> call, Throwable t) {

                Log.d("daftar", "onFaillure: " + t.toString());
                Toast toast = Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_LONG);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    View view = toast.getView();
                    view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
                    TextView textView = view.findViewById(android.R.id.message);
                    textView.setTextColor(Color.WHITE);
                }
                    toast.show();
            }
        });
    }
}