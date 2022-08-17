package com.yasinta.kesehatankucing.ui.teskesehatan;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import com.yasinta.kesehatankucing.adapter.AdapterListKesimpulanDetailDiagnosis;
import com.yasinta.kesehatankucing.adapter.AdapterListSaranDetailDiagnosis;
import com.yasinta.kesehatankucing.model.Diagnosas;
import com.yasinta.kesehatankucing.model.Gejalas;
import com.yasinta.kesehatankucing.model.HasilSarans;
import com.yasinta.kesehatankucing.model.Histories;
import com.yasinta.kesehatankucing.model.ResponseTesKesehatan;
import com.yasinta.kesehatankucing.ui.bookingjadwal.BookingJadwalFragment;
import com.yasinta.kesehatankucing.utils.SessionManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.ErrorListener;

import retrofit2.Call;
import retrofit2.Callback;

public class HasilDiagnosaFragment extends Fragment {
    TextView tv_namaKucingHasilD, tv_namaPemilikHasilD, tv_jenisKucingHasilD,
            tv_hasilDiagnosaDetail, tv_saranDetail;

    //gejala
    RecyclerView rv_gejalaDetail;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;
    List<Gejalas> listGejalas;

    //hasil
    RecyclerView rv_hasilDetail;
    RecyclerView.Adapter rvAdapterHasilDetail;
    RecyclerView.LayoutManager rvLayoutManagerHasilDetail;
    List<HasilSarans> listHasils;

    //saran
    RecyclerView rv_saranDetail;
    RecyclerView.Adapter rvAdapterSaranDetail;
    RecyclerView.LayoutManager rvLayoutManagerSaranDetail;
    List<HasilSarans> listSarans;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hasil_diagnosa, container, false);

        tv_namaKucingHasilD = root.findViewById(R.id.tv_namaKucingHasilD);
        tv_jenisKucingHasilD = root.findViewById(R.id.tv_jenisKucingHasilD);
        tv_namaPemilikHasilD = root.findViewById(R.id.tv_namaPemilikHasilD);

//        rv_gejala
        rv_gejalaDetail = root.findViewById(R.id.rv_gejalaDetail);
        listGejalas = new ArrayList<>();
        rvLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_gejalaDetail.setLayoutManager(rvLayoutManager);
        rvAdapter = new AdapterListGejalaDetailDiagnosis(listGejalas, getContext());
        rv_gejalaDetail.setAdapter(rvAdapter);


//        ===================================
        //rv_hasil
        rv_hasilDetail = root.findViewById(R.id.rv_hasilDiagnosaDetail);
        listHasils = new ArrayList<>();
        rvLayoutManagerHasilDetail = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_hasilDetail.setLayoutManager(rvLayoutManagerHasilDetail);
        rvAdapterHasilDetail = new AdapterListKesimpulanDetailDiagnosis(listHasils, getContext());
        rv_hasilDetail.setAdapter(rvAdapterHasilDetail);

        //saran
        rv_saranDetail = root.findViewById(R.id.rv_saranDiagnosaDetail);
        listSarans = new ArrayList<>();
        rvLayoutManagerSaranDetail = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_saranDetail.setLayoutManager(rvLayoutManagerSaranDetail);
        rvAdapterSaranDetail = new AdapterListSaranDetailDiagnosis(listSarans, getContext());
        rv_saranDetail.setAdapter(rvAdapterSaranDetail);

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

        CardView cv_btnPerformBooking = root.findViewById(R.id.cv_btnPerformBooking);
        cv_btnPerformBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment booking = new BookingJadwalFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, booking).commit();
                ((MainActivity)getActivity()).setActionBarTitle(R.string.menu_booking_jadwal);
            }
        });
        return root;
    }

    private void catchCalues() {
        String sId = getArguments().getString("id");

        Log.d("catch", "catchCalues ID: " + sId);

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

                if (response.body() != null) {
                    Log.d("respon", "onResponse Hasil Diagnosis: " + response.body().toString());
                    tv_namaKucingHasilD.setText(response.body().getData().getNama_kucing());
                    tv_jenisKucingHasilD.setText(response.body().getData().getJenis_kucing());
                    tv_namaPemilikHasilD.setText(response.body().getData().getNama_pemilik());

                    //                tv_hasilDiagnosaDetail.setText(response.body().getData().get);
//                tv_saranDetail.setText(response.body().getData().getSaran_pengobatan());

//                listGejalas = response.body().getData().getGejala();
//                Log.d("respons", "LISTGEJALAS: " + Arrays.toString(listGejalas.toArray()));
//                rvAdapter.notifyDataSetChanged();

                    //gejala
                    ArrayList<Gejalas> arrayListGejalas = response.body().getData().getGejala();
                    for (int i = 0; i < arrayListGejalas.size(); i++) {
                        listGejalas.add(arrayListGejalas.get(i));
                    }
                    rvAdapter.notifyDataSetChanged();

                    //hasil
                    ArrayList<HasilSarans> arrayListHasils = response.body().getData().getHasil_diagnosa();
                    for (int i = 0; i < arrayListHasils.size(); i++) {
                        listHasils.add(arrayListHasils.get(i));
                    }
                    rvAdapterHasilDetail.notifyDataSetChanged();

                    //saran
                    ArrayList<HasilSarans> arrayListSarans = response.body().getData().getSaran_pengobatan();
                    for (int i = 0; i < arrayListSarans.size(); i++) {
                        listSarans.add(arrayListSarans.get(i));
                    }
                    rvAdapterSaranDetail.notifyDataSetChanged();
                } else {
                    Log.d("respon", "onResponse Diagnosis: NULL");
                }
            }

            @Override
            public void onFailure(Call<ResponseTesKesehatan> call, Throwable t) {

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