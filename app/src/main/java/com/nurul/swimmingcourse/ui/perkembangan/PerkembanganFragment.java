package com.nurul.swimmingcourse.ui.perkembangan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.nurul.swimmingcourse.model.JadwalLatihans;
import com.nurul.swimmingcourse.model.Perkembangans;
import com.nurul.swimmingcourse.model.ResponseInfoPerkembangan;
import com.nurul.swimmingcourse.model.Siswas;
import com.nurul.swimmingcourse.model.ResponseSPSiswa;
import com.nurul.swimmingcourse.utils.ApiClient;
import com.nurul.swimmingcourse.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class PerkembanganFragment extends Fragment {
    DatePickerDialog datePickerDialog;
    TextView tv_dateSelected;
    String selectedIDSiswa, selectedTglPerkembangan;
    //date
    int bulan;
    String selectedDate, selectedDateShow, dateToUp;
    Spinner sp_namaPerkembangan, sp_latihankePerkembangan;
    TextView tv_infoPerkembangan;
    RequestQueue requestQueue;

    ArrayList<Siswas> listSpAllSiswa;
    ArrayList<Perkembangans> listSpAllTglPerkembangan;

    List<Perkembangans> listSelectedPerkembangan;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perkembangan, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        sp_namaPerkembangan = root.findViewById(R.id.sp_namaSiswaPerkembangan);
        sp_latihankePerkembangan = root.findViewById(R.id.sp_latihankePerkembangan);
        tv_infoPerkembangan = root.findViewById(R.id.tv_infoPerkembangan);
        CardView cv_btnTampilPerkembangan = root.findViewById(R.id.cv_btnTampilPerkembangan);
        getSpinnerAllSiswa();
//        getSpinnerAllTanggal();
//        spinnerHariKe();

        cv_btnTampilPerkembangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilDataPerkembangan(selectedIDSiswa, selectedTglPerkembangan);
            }
        });

        return root;
    }

    private void tampilDataPerkembangan(String sIdSiswa, String sTgl) {
        Log.d("perkem", "ID SIS: " + sIdSiswa);
        Log.d("perkem", "ID TGL: " + sTgl);

        String apiUrl = ApiClient.API + "perkembangan-siswa/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("respon", "ALl Perkembangan: " + response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject perkembangan = jsonArray.getJSONObject(i);

                                //get key 1 by 1
                                Perkembangans model = new Perkembangans();

                                model.setKeterangan(perkembangan.getString("keterangan"));

                                if (perkembangan.getString("siswa").equals(sIdSiswa)
                                        && perkembangan.getString("tanggal_latihan").equals(sTgl)) {
//                                    listSelectedPerkembangan.add(model);
                                    tv_infoPerkembangan.setText(perkembangan.getString("keterangan"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        rvAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("respon", "No RES: " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap header = new HashMap();
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer " + SessionManager.getToken());
                return params;
            }
        };

        requestQueue.add(request);
    }

    private void getSpinnerAllTanggal(String sIdSiswa){
        Call<ResponseInfoPerkembangan> call = MainActivity.apiInterface.getAllPerkembanganByIdSiswa(
                "Bearer " + SessionManager.getToken(),
                sIdSiswa
        );
        call.enqueue(new Callback<ResponseInfoPerkembangan>() {
            @Override
            public void onResponse(Call<ResponseInfoPerkembangan> call, final retrofit2.Response<ResponseInfoPerkembangan> response) {
                listSpAllTglPerkembangan = new ArrayList<>();
                Log.d("spinner", "onResponse: " + response.body());
                listSpAllTglPerkembangan = response.body().getData();

//                Log.d("spinner", "onResponse: " + response.body());
                List<String> listTglSp = new ArrayList<>();
                for (int i = 0; i < listSpAllTglPerkembangan.size(); i++) {
                    listTglSp.add(listSpAllTglPerkembangan.get(i).getTanggal_latihan());
                }

                //set to spinner adapter
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listTglSp);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_latihankePerkembangan.setAdapter(adapter);
                sp_latihankePerkembangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //get selected string
                        Perkembangans perkembangans = listSpAllTglPerkembangan.get(position);
                        selectedTglPerkembangan = perkembangans.getTanggal_latihan();

                        Log.d("spinner", "SELECT DATE " + selectedTglPerkembangan);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

//                        Log.d("select", "UNSELECT");
                    }
                });
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
                Log.d("spinner", "onResponse: " + response.body().getData());
                listSpAllSiswa = response.body().getData();

//                Log.d("spinner", "onResponse: " + response.body());
                List<String> listSiswaSp = new ArrayList<>();
                for (int i = 0; i < listSpAllSiswa.size(); i++) {
                    listSiswaSp.add(listSpAllSiswa.get(i).getNama());
                }

                //set to spinner adapter
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listSiswaSp);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_namaPerkembangan.setAdapter(adapter);
                sp_namaPerkembangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //get selected string
                        Siswas siswas = listSpAllSiswa.get(position);
                        selectedIDSiswa = siswas.getId();
//                        getPerkembanganByIdSiswa(selectedIDSiswa);
                        getSpinnerAllTanggal(selectedIDSiswa);

                        Log.d("spinner", "SELECT SISWA " + selectedIDSiswa);
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

    private void getPerkembanganByIdSiswa(String sSelectedIdSiswa) {

    }

//    private void spinnerHariKe() {
//        //spinner hari ke
//        ArrayAdapter<CharSequence> adapterJam = ArrayAdapter.createFromResource(getContext(), R.array.hari_ke, android.R.layout.simple_spinner_item);
//        adapterJam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_latihankePerkembangan.setAdapter(adapterJam);
//        sp_latihankePerkembangan.setOnItemSelectedListener(sp_latihankePerkembangan.getOnItemSelectedListener());
//    }


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