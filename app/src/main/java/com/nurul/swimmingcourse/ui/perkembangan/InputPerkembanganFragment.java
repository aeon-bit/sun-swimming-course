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
import com.nurul.swimmingcourse.model.ResponseSPSiswa;
import com.nurul.swimmingcourse.model.Siswas;
import com.nurul.swimmingcourse.utils.SessionManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputPerkembanganFragment extends Fragment {

    Spinner sp_namaInputPerkembangan, sp_hariKeInputPerkembangan;
    EditText et_inputPerkembangan;
    String selectedIDSiswa;

    ArrayList<Siswas> listSpAllSiswa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_input_perkembangan, container, false);

        sp_namaInputPerkembangan = root.findViewById(R.id.sp_namaInputPerkembangan);
        sp_hariKeInputPerkembangan = root.findViewById(R.id.sp_hariKeInputPerkembangan);
        et_inputPerkembangan = root.findViewById(R.id.et_inputPerkembangan);

        CardView cv_btnSimpanPerkembangan = root.findViewById(R.id.cv_btnSimpanPerkembangan);
        getSpinnerAllSiswa();
        spinnerHariKe();

        cv_btnSimpanPerkembangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimDataPerkembangan();
            }
        });

        return root;
    }

    private void kirimDataPerkembangan() {

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

    private void spinnerHariKe() {
        //spinner hari ke
        ArrayAdapter<CharSequence> adapterJam = ArrayAdapter.createFromResource(getContext(), R.array.hari_ke, android.R.layout.simple_spinner_item);
        adapterJam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_hariKeInputPerkembangan.setAdapter(adapterJam);
        sp_hariKeInputPerkembangan.setOnItemSelectedListener(sp_hariKeInputPerkembangan.getOnItemSelectedListener());
    }


}