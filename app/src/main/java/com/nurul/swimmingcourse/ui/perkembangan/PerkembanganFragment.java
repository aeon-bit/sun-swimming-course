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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.model.Pelatihs;
import com.nurul.swimmingcourse.model.Perkembangans;
import com.nurul.swimmingcourse.model.ResponseInfoPerkembangan;
import com.nurul.swimmingcourse.model.ResponseSPPelatih;
import com.nurul.swimmingcourse.model.Siswas;
import com.nurul.swimmingcourse.model.ResponseBookingJadwals;
import com.nurul.swimmingcourse.model.ResponseSPSiswa;
import com.nurul.swimmingcourse.model.ResponseSPSiswa;
import com.nurul.swimmingcourse.model.Siswas;
import com.nurul.swimmingcourse.utils.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerkembanganFragment extends Fragment {
    DatePickerDialog datePickerDialog;
    TextView tv_dateSelected;
    String selectedIDSiswa;
    //date
    int bulan;
    String selectedDate, selectedDateShow, dateToUp;
    Spinner sp_namaPerkembangan, sp_latihankePerkembangan;
    TextView tv_infoPerkembangan;

    ArrayList<Siswas> listSpAllSiswa;
    ArrayList<Perkembangans> listAllPerkembangan;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perkembangan, container, false);

        sp_namaPerkembangan = root.findViewById(R.id.sp_namaPerkembangan);
        sp_latihankePerkembangan = root.findViewById(R.id.sp_latihankePerkembangan);
        tv_infoPerkembangan = root.findViewById(R.id.tv_infoPerkembangan);
        CardView cv_btnTampilPerkembangan = root.findViewById(R.id.cv_btnTampilPerkembangan);
        getSpinnerAllSiswa();
        spinnerHariKe();

        cv_btnTampilPerkembangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilDataPerkembangan();
            }
        });

        return root;
    }

    private void tampilDataPerkembangan() {
        Call<ResponseInfoPerkembangan> call = MainActivity.apiInterface.getAllPerkembangan(
                "Bearer " + SessionManager.getToken()
        );
        call.enqueue(new Callback<ResponseInfoPerkembangan>() {
            @Override
            public void onResponse(Call<ResponseInfoPerkembangan> call, final retrofit2.Response<ResponseInfoPerkembangan> response) {
                listAllPerkembangan = new ArrayList<>();
                Log.d("spinner", "onResponse: " + response.body());
                listAllPerkembangan = response.body().getData();

//                Log.d("spinner", "onResponse: " + response.body());
                List<String> listTglSp = new ArrayList<>();
                for (int i = 0; i < listAllPerkembangan.size(); i++) {
                    listTglSp.add(listAllPerkembangan.get(i).getId());
                }
            }

            @Override
            public void onFailure(Call<ResponseInfoPerkembangan> call, Throwable t) {

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
                sp_namaPerkembangan.setAdapter(adapter);
                sp_namaPerkembangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        sp_latihankePerkembangan.setAdapter(adapterJam);
        sp_latihankePerkembangan.setOnItemSelectedListener(sp_latihankePerkembangan.getOnItemSelectedListener());
    }


    private void openDatePicker() {
        datePickerDialog.show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                tv_dateSelected.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        selectedDate = year + "-" + month + "-" + day;
//        dateToUp = day + "-" + month + "-" + year;

//        Log.d("selecteddate", "makeDateString: " + dateToUp);

//        requestJamByIdLapToday();
        selectedDateShow = day + " " + getMonthFormat(month) + " " + year;
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "Januari";
        if (month == 2)
            return "Februari";
        if (month == 3)
            return "Maret";
        if (month == 4)
            return "April";
        if (month == 5)
            return "Mei";
        if (month == 6)
            return "Juni";
        if (month == 7)
            return "Juli";
        if (month == 8)
            return "Agustus";
        if (month == 9)
            return "September";
        if (month == 10)
            return "Oktober";
        if (month == 11)
            return "November";
        if (month == 12)
            return "Desember";

        //save mont in int
        bulan = month;
        //default should never happen
        return "Januari";
    }

}