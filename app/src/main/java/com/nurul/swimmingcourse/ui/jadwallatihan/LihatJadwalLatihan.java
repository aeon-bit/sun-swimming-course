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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.adapter.AdapterListJadwalLatihan;
import com.nurul.swimmingcourse.model.Gejalas;
import com.nurul.swimmingcourse.model.JadwalLatihans;
import com.nurul.swimmingcourse.model.ResponsePesanJadwal;
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

public class LihatJadwalLatihan extends Fragment {
    RecyclerView rv_daftarJadwalLatihan;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;
    RequestQueue requestQueue;
    List<JadwalLatihans> listJadwalLatihan;

    TextView tv_noData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lihat_jadwal, container, false);

        rv_daftarJadwalLatihan = root.findViewById(R.id.rv_daftarJadwalLatihan);
        requestQueue = Volley.newRequestQueue(getContext());
        listJadwalLatihan = new ArrayList<>();

        requestAllJadwalLatihan();

        rvLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_daftarJadwalLatihan.setLayoutManager(rvLayoutManager);
        rvAdapter = new AdapterListJadwalLatihan(listJadwalLatihan, getContext());
        rv_daftarJadwalLatihan.setAdapter(rvAdapter);

        tv_noData = root.findViewById(R.id.tv_noData);
        tv_noData.setVisibility(View.VISIBLE);

        return root;
    }

    private void requestAllJadwalLatihan() {
        String apiUrl = ApiClient.API + "jadwal-latihan/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("respon", "Daftar JadwalLatihan: " + response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jadwal = jsonArray.getJSONObject(i);

                                //get key 1 by 1
                                JadwalLatihans model = new JadwalLatihans();

                                model.setId(jadwal.getString("id"));
                                model.setPelatih(jadwal.getString("pelatih"));
                                model.setSiswa_id(jadwal.getString("siswa_id"));
                                model.setHari(jadwal.getString("hari"));
                                model.setJam(jadwal.getString("jam"));
                                model.setLokasi(jadwal.getString("lokasi"));

                                if (jadwal.getString("siswa_id").equals(SessionManager.getUserData().getId())) {
                                    listJadwalLatihan.add(model);
                                }
                            }
                            if (!listJadwalLatihan.isEmpty()) {
                                tv_noData.setVisibility(View.INVISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        rvAdapter.notifyDataSetChanged();
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
}