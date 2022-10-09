package com.nurul.swimmingcourse.ui.perkembangan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.model.Perkembangans;
import com.nurul.swimmingcourse.model.ResponseBookingJadwals;
import com.nurul.swimmingcourse.model.ResponseInfoPerkembangan;
import com.nurul.swimmingcourse.model.ResponseInputPerkembangan;
import com.nurul.swimmingcourse.model.ResponsePesanJadwal;
import com.nurul.swimmingcourse.model.ResponseSPSiswa;
import com.nurul.swimmingcourse.model.Siswas;
import com.nurul.swimmingcourse.utils.SessionManager;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputPerkembanganFragment extends Fragment {

    Spinner sp_namaInputPerkembangan, sp_hariKeInputPerkembangan;
    EditText et_todayInputPerkembangan, et_inputPerkembangan, et_lokasiInputPerkembangan;
    String selectedIDSiswa, today;

    ArrayList<Siswas> listSpAllSiswa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_input_perkembangan, container, false);

        sp_namaInputPerkembangan = root.findViewById(R.id.sp_namaInputPerkembangan);
//        sp_hariKeInputPerkembangan = root.findViewById(R.id.sp_hariKeInputPerkembangan);
        et_todayInputPerkembangan = root.findViewById(R.id.et_todayInputPerkembangan);
        et_lokasiInputPerkembangan = root.findViewById(R.id.et_lokasiInputPerkembangan);
        et_inputPerkembangan = root.findViewById(R.id.et_inputPerkembangan);

        CardView cv_btnSimpanPerkembangan = root.findViewById(R.id.cv_btnSimpanPerkembangan);
        getSpinnerAllSiswa();
//        spinnerHariKe();
        getCurrentDate();

        cv_btnSimpanPerkembangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_lokasiInputPerkembangan.getText().toString().equals("")) {
                    et_lokasiInputPerkembangan.setError("Masukkan lokasi latihan");
                } else if (et_inputPerkembangan.getText().toString().equals("")) {
                    et_inputPerkembangan.setError("Masukkan keterangan perkembangan");
                } else {
                    kirimDataPerkembangan(
                            et_todayInputPerkembangan.getText().toString(),
                            et_lokasiInputPerkembangan.getText().toString(),
                            et_inputPerkembangan.getText().toString()
                    );
                }
            }
        });

        return root;
    }

    private void getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        today = dtf.format(now);

        et_todayInputPerkembangan.setText(today);

//        Log.d("tanggal", "getCurrentDate: " + dtf.format(now));
    }

    private void kirimDataPerkembangan(String sTglLatihan, String sLokasi, String sKetPerkembangan) {
//        Log.d("perkem", "ID SISWA: " + selectedIDSiswa);
//        Log.d("perkem", "TGL: " + sTglLatihan);
//        Log.d("perkem", "LOKASI: " + sLokasi);
//        Log.d("perkem", "KET: " + sKetPerkembangan);
        Call<ResponseInputPerkembangan> call = MainActivity.apiInterface.performInputPerkembangan(
                "Bearer " + SessionManager.getToken(),
                SessionManager.getUserData().getId(),
                selectedIDSiswa,
                sTglLatihan,
                sLokasi,
                sKetPerkembangan
        );

//        Fragment self = this;
        call.enqueue(new Callback<ResponseInputPerkembangan>() {
            @Override
            public void onResponse(Call<ResponseInputPerkembangan> call, retrofit2.Response<ResponseInputPerkembangan> response) {

//                Log.d("respon", "onResponse Diagnosis: " + response.body().getData().getTanggal());
//                Log.d("respon", "onResponse Diagnosis: " + response.errorBody().toString());
                if (response.body() != null) {
                    if (response.body().getMessage().equals("sukses")) {

                        callToast("Berhasil input perkembangan", 1);

                        ((MainActivity) getContext()).SwitchFrag(0); //restart actv

                    }
                } else {
                    callToast("Gagal. Ulangi beberapa saat", 0);
                }
            }

            @Override
            public void onFailure(Call<ResponseInputPerkembangan> call, Throwable t) {

                Log.d("daftar", "onFaillure: " + t);
                callToast("Koneksi bermasalah", 0);
            }
        });

    }

    private void getSpinnerAllSiswa() {
        Call<ResponseSPSiswa> call = MainActivity.apiInterface.getSpAllSiswa(
                "Bearer " + SessionManager.getToken()
        );
        call.enqueue(new Callback<ResponseSPSiswa>() {
            @Override
            public void onResponse(Call<ResponseSPSiswa> call, final retrofit2.Response<ResponseSPSiswa> response) {
                listSpAllSiswa = new ArrayList<>();
                Log.d("spinner", "onResponse: " + response.body());
                listSpAllSiswa = response.body().getData();

//                Log.d("spinner", "onResponse: " + response.body());
                List<String> listTglSp = new ArrayList<>();
                for (int i = 0; i < listSpAllSiswa.size(); i++) {
                    listTglSp.add(listSpAllSiswa.get(i).getNama());
                }

                //set to spinner adapter
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listTglSp);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_namaInputPerkembangan.setAdapter(adapter);
                sp_namaInputPerkembangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //get selected string
                        Siswas siswas = listSpAllSiswa.get(position);
                        selectedIDSiswa = siswas.getId();

                        Log.d("spinner", "SELECT " + selectedIDSiswa);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

//                        Log.d("select", "UNSELECT");
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseSPSiswa> call, Throwable t) {

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

//    private void spinnerHariKe() {
//        //spinner hari ke
//        ArrayAdapter<CharSequence> adapterJam = ArrayAdapter.createFromResource(getContext(), R.array.hari_ke, android.R.layout.simple_spinner_item);
//        adapterJam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_hariKeInputPerkembangan.setAdapter(adapterJam);
//        sp_hariKeInputPerkembangan.setOnItemSelectedListener(sp_hariKeInputPerkembangan.getOnItemSelectedListener());
//    }


}