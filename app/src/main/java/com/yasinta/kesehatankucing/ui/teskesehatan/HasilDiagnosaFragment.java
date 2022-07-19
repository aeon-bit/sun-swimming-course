package com.yasinta.kesehatankucing.ui.teskesehatan;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yasinta.kesehatankucing.R;
import com.yasinta.kesehatankucing.activity.MainActivity;
import com.yasinta.kesehatankucing.adapter.AdapterListGejalaDetailDiagnosis;
import com.yasinta.kesehatankucing.adapter.AdapterListHistoryPeriksa;
import com.yasinta.kesehatankucing.model.Gejalas;
import com.yasinta.kesehatankucing.model.Histories;
import com.yasinta.kesehatankucing.model.ResponseTesKesehatan;
import com.yasinta.kesehatankucing.utils.SessionManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HasilDiagnosaFragment extends Fragment {
    TextView tv_namaKucingHasilD, tv_namaPemilikHasilD,tv_jenisKucingHasilD,
            tv_hasilDiagnosaDetail, tv_saranDetail;

    RecyclerView rv_gejalaDetail;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;
    List<Gejalas> listGejalas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hasil_diagnosa, container, false);

        tv_namaKucingHasilD = root.findViewById(R.id.tv_namaKucingHasilD);
        tv_jenisKucingHasilD = root.findViewById(R.id.tv_jenisKucingHasilD);
        tv_namaPemilikHasilD = root.findViewById(R.id.tv_namaPemilikHasilD);
        tv_hasilDiagnosaDetail = root.findViewById(R.id.tv_hasilDiagnosaDetail);
        tv_saranDetail = root.findViewById(R.id.tv_saranDetail);

//        rv
        rv_gejalaDetail = root.findViewById(R.id.rv_gejalaDetail);
        listGejalas = new ArrayList<>();
        rvLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_gejalaDetail.setLayoutManager(rvLayoutManager);
        rvAdapter = new AdapterListGejalaDetailDiagnosis(listGejalas, getContext());
        rv_gejalaDetail.setAdapter(rvAdapter);

        catchCalues();

        CardView cv_btnPerformChatDokter = root.findViewById(R.id.cv_btnPerformChatDokter);
        cv_btnPerformChatDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone=" + "628156754523";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        return root;
    }

    private void catchCalues() {
        String sId = getArguments().getString("id");

        getDataById(sId);

    }

    private void getDataById(String sId) {
        Call<ResponseTesKesehatan> call = MainActivity.apiInterface.getDetailDiagnosa(
                "Bearer " + SessionManager.getToken(), sId
        );

        Fragment self = this;
        call.enqueue(new Callback<ResponseTesKesehatan>() {
            @Override
            public void onResponse(Call<ResponseTesKesehatan> call, retrofit2.Response<ResponseTesKesehatan> response) {
                Log.d("respon", "onResponse Diagnosis: " + response.body().toString());

                tv_namaKucingHasilD.setText(response.body().getData().getUser().getNama_kucing());
                tv_jenisKucingHasilD.setText(response.body().getData().getUser().getJenis_kucing());
                tv_namaPemilikHasilD.setText(response.body().getData().getUser().getNama_pemilik());

                tv_hasilDiagnosaDetail.setText(response.body().getData().getHasil_diagnosa());
                tv_saranDetail.setText(response.body().getData().getSaran_pengobatan());

//                listGejalas = response.body().getData().getGejala();
//                Log.d("respons", "LISTGEJALAS: " + Arrays.toString(listGejalas.toArray()));
//                rvAdapter.notifyDataSetChanged();
                ArrayList<Gejalas> arrayListGejalas = response.body().getData().getGejala();
                for (int i = 0; i<arrayListGejalas.size(); i++){
                    listGejalas.add(arrayListGejalas.get(i));
                }
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseTesKesehatan> call, Throwable t) {

                Log.d("daftar", "onFaillure: " + t.toString());
                Toast toast = Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
                TextView textView = view.findViewById(android.R.id.message);
                textView.setTextColor(Color.WHITE);
                toast.show();
            }
        });
    }

}