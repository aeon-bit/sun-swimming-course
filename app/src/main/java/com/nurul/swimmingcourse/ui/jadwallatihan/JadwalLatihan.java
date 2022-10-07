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
    List<Gejalas> listGejalas;

    ArrayList<Pelatihs> listSpAllPelatih;

    ArrayList<String> checkedGejala = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pesan_jadwal, container, false);

//        rg_gejala = root.findViewById(R.id.rg_gejala);
//        ly_cb = root.findViewById(R.id.ly_cb);
        listGejalas = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
//        getAllDiagnosa();
        getCurrentDate();


        EditText et_namaInJadwal = root.findViewById(R.id.et_namaInJadwal);
        sp_hariInJadwal = root.findViewById(R.id.sp_hariInJadwal);
        sp_jamInJadwal = root.findViewById(R.id.sp_jamInJadwal);
        sp_pelatihInJadwal = root.findViewById(R.id.sp_pelatihInJadwal);
        spinnerHari();
        spinnerJam();
        getSpinnerAllPelatih();

        et_namaInJadwal.setText(SessionManager.getUserData().getNama());



//        String sNamaPemilik = et_namaPemilikTesK.getText().toString();

//        CardView cv_btnPerformDiagnosa = root.findViewById(R.id.cv_btnPerformDiagnosa);
//        cv_btnPerformDiagnosa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //call Dialog
//                DialogConfirmTes dialogConfirmTes = new DialogConfirmTes(getContext());
//                dialogConfirmTes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                LinearLayout ly_btnConfirmTes = dialogConfirmTes.findViewById(R.id.ly_btnConfirmTes);
//                LinearLayout ly_btnBatalTes = dialogConfirmTes.findViewById(R.id.ly_btnBatalTes);
//
//                ly_btnConfirmTes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String sNamaKucing = et_namaKucingTesK.getText().toString();
//                        String sJenis = sp_hariInJadwal.getSelectedItem().toString();
//
//                        Log.d("spinner", "JENIS: " + sJenis);
//
//                        if (sNamaKucing.equals("")) {
//                            et_namaKucingTesK.setError("Masukkan nama kucing");
////                        } else if (sJenis.equals("")) {
////                            et_jenisKucingTesK.setError("Masukkan jenis kucing");
////                } else if (sNamaPemilik.equals("")) {
////                    et_namaPemilikTesK.setError("Masukkan nama pemilik");
//                        } else {
//                            performDiagnosa(sNamaKucing, sJenis);
//                        }
//
//                        dialogConfirmTes.dismiss();
//                    }
//                });
//
//                ly_btnBatalTes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogConfirmTes.dismiss();
//                    }
//                });
//
//                dialogConfirmTes.show();
//            }
//        });


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

    private void getAllDiagnosa() {
        String apiUrl = ApiClient.API + "data-gejala/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("respon", "Data gejala: " + response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject gejala = jsonArray.getJSONObject(i);

                                //get key 1 by 1
                                Gejalas model = new Gejalas();

                                model.setId(gejala.getString("id"));
                                model.setKode_gejala(gejala.getString("kode_gejala"));
                                model.setNama_gejala(gejala.getString("nama_gejala"));

                                listGejalas.add(model);

                            }
                            createCheckBox();
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

    private void performDiagnosa(String sNamaKucing, String sJenis) {
        Call<ResponseTesKesehatan> call = MainActivity.apiInterface.performTesKesehatan(
                "Bearer " + SessionManager.getToken(),
                sNamaKucing,
                sJenis,
                today,
                checkedGejala
        );

        Log.d("perform", "NAMA: "+sNamaKucing);
        Log.d("perform", "JENIS: "+sJenis);
        Log.d("perform", "TANGGAL: "+today);

        Fragment self = this;
        call.enqueue(new Callback<ResponseTesKesehatan>() {
            @Override
            public void onResponse(Call<ResponseTesKesehatan> call, retrofit2.Response<ResponseTesKesehatan> response) {

//                Log.d("respon", "onResponse Diagnosis: " + response.body().getData().getTanggal());
//                Log.d("respon", "onResponse Diagnosis: " + response.errorBody().toString());
                if (response.body() != null){
                    if (response.body().getStatus().equals("success")) {

                        Toast toast = Toast.makeText(getActivity(), "Upload Diagnosa Berhasil", Toast.LENGTH_LONG);
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                            View view = toast.getView();
                            view.setBackgroundResource(R.drawable.xmlbg_toast_success);
                            TextView textView = view.findViewById(android.R.id.message);
                            textView.setTextColor(Color.WHITE);
                        }
                        toast.show();

//                    ((MainActivity) getContext()).logoutPerform(); //restart actv
                        Fragment detailDiagnosa = new HasilDiagnosaFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("id", response.body().getData().getId());
                        detailDiagnosa.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, detailDiagnosa).commit();
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Server Bermasalah", Toast.LENGTH_LONG);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                        View view = toast.getView();
                        view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
                        TextView textView = view.findViewById(android.R.id.message);
                        textView.setTextColor(Color.WHITE);
                    }
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTesKesehatan> call, Throwable t) {

                Log.d("daftar", "onFaillure: " + t);
                Toast toast = Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_LONG);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    View view = toast.getView();
                    view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
                    TextView textView = view.findViewById(android.R.id.message);
                    textView.setTextColor(Color.WHITE);
                    toast.show();
                }
            }
        });

    }

    private void createCheckBox() {
//        final CheckBox[] cb = new CheckBox[10];
        Log.d("ischeck", "SIZE: " + listGejalas.size());

        for (int i = 0; i < listGejalas.size(); i++) {
            CheckBox cb = new CheckBox(getContext());
            cb.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondary)));
            cb.setText(listGejalas.get(i).getNama_gejala());
            cb.setTextColor(Color.parseColor("#034743"));
            cb.setId(i);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("ischeck", "isChecked: " + isChecked);
                    Log.d("ischeck", "buttonV: " + buttonView.getId());

                    Gejalas g = listGejalas.get(buttonView.getId());
                    if (isChecked) {
                        checkedGejala.add(g.getKode_gejala());
                    } else {
                        if (checkedGejala.contains(g.getKode_gejala())) {
                            checkedGejala.remove(g.getKode_gejala());
                        }
                    }

                    Log.d("ischeck", "CHECKED: " + Arrays.toString(checkedGejala.toArray()));
                }
            });
            ly_cb.addView(cb);
        }
//        ly_rg.addView(rg);//you add the whole RadioGroup to the layout

    }
}