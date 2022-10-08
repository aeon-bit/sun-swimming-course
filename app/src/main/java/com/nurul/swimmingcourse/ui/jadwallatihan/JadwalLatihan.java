package com.nurul.swimmingcourse.ui.jadwallatihan;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.model.Gejalas;
import com.nurul.swimmingcourse.model.Pelatihs;
import com.nurul.swimmingcourse.model.ResponsePesanJadwal;
import com.nurul.swimmingcourse.model.ResponseSPPelatih;
import com.nurul.swimmingcourse.model.ResponseTesKesehatan;
import com.nurul.swimmingcourse.utils.ApiClient;
import com.nurul.swimmingcourse.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class JadwalLatihan extends Fragment {
    Spinner sp_hariInJadwal, sp_jamInJadwal, sp_pelatihInJadwal;
    String selectedIDPelatih, today;

    LinearLayout ly_cb;
    RequestQueue requestQueue;

    ArrayList<Pelatihs> listSpAllPelatih;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pesan_jadwal, container, false);

        requestQueue = Volley.newRequestQueue(getContext());
        getCurrentDate();


        EditText et_namaInJadwal = root.findViewById(R.id.et_namaInJadwal);
        EditText et_lokasiInJadwal = root.findViewById(R.id.et_lokasiInJadwal);
        EditText et_hariInJadwal = root.findViewById(R.id.et_hariInJadwal);
//        sp_hariInJadwal = root.findViewById(R.id.sp_hariInJadwal);
        sp_jamInJadwal = root.findViewById(R.id.sp_jamInJadwal);
        sp_pelatihInJadwal = root.findViewById(R.id.sp_pelatihInJadwal);
//        spinnerHari();
        spinnerJam();
        getSpinnerAllPelatih();

        et_namaInJadwal.setText(SessionManager.getUserData().getNama());

        CardView cv_btnKirimJadwal = root.findViewById(R.id.cv_btnKirimJadwal);
        cv_btnKirimJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_lokasiInJadwal.getText().toString().equals("")) {
                    et_lokasiInJadwal.setError("Masukkan lokasi latihan");
                } else if (et_hariInJadwal.getText().toString().equals("")) {
                    et_lokasiInJadwal.setError("Masukkan tanggal");
                } else {
                    performPesanJadwal(
                            et_hariInJadwal.getText().toString(),
                            (String) sp_jamInJadwal.getSelectedItem(),
                            et_lokasiInJadwal.getText().toString()
                    );
                }
            }
        });
        return root;
    }


    private void spinnerJam() {
        ArrayAdapter<CharSequence> adapterJam = ArrayAdapter.createFromResource(getContext(), R.array.jam, android.R.layout.simple_spinner_item);
        adapterJam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_jamInJadwal.setAdapter(adapterJam);
        sp_jamInJadwal.setOnItemSelectedListener(sp_jamInJadwal.getOnItemSelectedListener());
    }

    private void spinnerHari() {
        ArrayAdapter<CharSequence> adapterHari = ArrayAdapter.createFromResource(getContext(), R.array.hari, android.R.layout.simple_spinner_item);
        adapterHari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_hariInJadwal.setAdapter(adapterHari);
        sp_hariInJadwal.setOnItemSelectedListener(sp_hariInJadwal.getOnItemSelectedListener());
    }

    private void getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        today = dtf.format(now);

//        Log.d("tanggal", "getCurrentDate: " + dtf.format(now));
    }

    private void getSpinnerAllPelatih() {
        Call<ResponseSPPelatih> call = MainActivity.apiInterface.getSpAllPelatih(
                "Bearer " + SessionManager.getToken()
        );
        call.enqueue(new Callback<ResponseSPPelatih>() {
            @Override
            public void onResponse(Call<ResponseSPPelatih> call, final retrofit2.Response<ResponseSPPelatih> response) {
                listSpAllPelatih = new ArrayList<>();
                Log.d("spinner", "onResponse: " + response.body());
                listSpAllPelatih = response.body().getData();

//                Log.d("spinner", "onResponse: " + response.body());
                List<String> listTglSp = new ArrayList<>();
                for (int i = 0; i < listSpAllPelatih.size(); i++) {
                    listTglSp.add(listSpAllPelatih.get(i).getNama());
                }

                //set to spinner adapter
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listTglSp);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_pelatihInJadwal.setAdapter(adapter);
                sp_pelatihInJadwal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //get selected string
                        Pelatihs pelatihs = listSpAllPelatih.get(position);
                        selectedIDPelatih = pelatihs.getId();

                        Log.d("spinner", "SELECT " + selectedIDPelatih);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

//                        Log.d("select", "UNSELECT");
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseSPPelatih> call, Throwable t) {

            }
        });
    }

    private void performPesanJadwal(String sHari, String sJam, String sLokasi) {
        Call<ResponsePesanJadwal> call = MainActivity.apiInterface.performPesanJadwal(
                "Bearer " + SessionManager.getToken(),
                selectedIDPelatih,
                sHari,
                sJam,
                sLokasi
        );

        Log.d("perform", "ID PELATIH: " + selectedIDPelatih);
        Log.d("perform", "HARI: " + sHari);
        Log.d("perform", "JAM: " + sJam);
        Log.d("perform", "LOKASI: " + sLokasi);

//        Fragment self = this;
        call.enqueue(new Callback<ResponsePesanJadwal>() {
            @Override
            public void onResponse(Call<ResponsePesanJadwal> call, retrofit2.Response<ResponsePesanJadwal> response) {

//                Log.d("respon", "onResponse Diagnosis: " + response.body().getData().getTanggal());
//                Log.d("respon", "onResponse Diagnosis: " + response.errorBody().toString());
                if (response.body() != null) {
                    if (response.body().getMessage().equals("sukses")) {

                        callToast("Pemesanan jadwal berhasil", 1);

                        ((MainActivity) getContext()).SwitchFrag(0); //restart actv
//                        Fragment detailDiagnosa = new HasilDiagnosaFragment();
//
//                        Bundle bundle = new Bundle();
//                        bundle.putString("id", response.body().getData().getId());
//                        detailDiagnosa.setArguments(bundle);
//
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.nav_host_fragment_content_main, detailDiagnosa).commit();
                    }
                } else {
                    callToast("Format tanggal salah.\n(ex: 2022-12-31)", 0);
                }
            }

            @Override
            public void onFailure(Call<ResponsePesanJadwal> call, Throwable t) {

                Log.d("daftar", "onFaillure: " + t);
                callToast("Koneksi bermasalah", 0);
            }
        });

    }

    private void callToast(String msg, int i) {
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            View view = toast.getView();
            view.setPadding(42, 12, 42, 12);
            if (i == 1) {
                view.setBackgroundResource(R.drawable.xmlbg_toast_success);
            } else {
                view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
            }
            TextView textView = view.findViewById(android.R.id.message);
            textView.setTextColor(Color.WHITE);
        }
        toast.show();
    }

}