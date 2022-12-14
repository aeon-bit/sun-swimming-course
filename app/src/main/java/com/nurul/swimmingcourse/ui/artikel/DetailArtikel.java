package com.nurul.swimmingcourse.ui.artikel;

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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.model.ResponseDetailArtikel;
import com.nurul.swimmingcourse.utils.ApiClient;
import com.nurul.swimmingcourse.utils.SessionManager;

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

        ((MainActivity) getActivity()).setTitle("Detail Info Sun Swimming");

        catchValues(root);

        return root;
    }

    private void catchValues(View root) {
        if (getArguments() != null) {
            String sId = getArguments().getString("id");
            String sJudul = getArguments().getString("judul_info");
            String sDeskripsi = getArguments().getString("detail_info");
            String sGambar = getArguments().getString("foto");

            tv_JudulDetailArtikel.setText(sJudul);
            tv_deskripsiDetailArtikel.setText(sDeskripsi);

            Glide.with(root).load(ApiClient.IMAGE_URL_ARTIKEL + sGambar)
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

//                tv_JudulDetailArtikel.setText(response.body().getData().getJudul_artikel());
//                tv_deskripsiDetailArtikel.setText(response.body().getData().getDeskripsi());
//
//                Glide.with(getContext()).load(ApiClient.IMAGE_URL + response.body().getData().getGambar())
//                        .error(R.drawable.ic_nopic).centerCrop().into(iv_gambarDetailArtikel);
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