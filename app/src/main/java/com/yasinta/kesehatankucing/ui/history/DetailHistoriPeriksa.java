package com.yasinta.kesehatankucing.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.yasinta.kesehatankucing.R;
import com.yasinta.kesehatankucing.adapter.AdapterListDetailHistori;
import com.yasinta.kesehatankucing.adapter.AdapterListGejalaDetailDiagnosis;
import com.yasinta.kesehatankucing.adapter.AdapterListHistoryPeriksa;
import com.yasinta.kesehatankucing.model.Gejalas;
import com.yasinta.kesehatankucing.model.Histories;
import com.yasinta.kesehatankucing.model.Users;
import com.yasinta.kesehatankucing.utils.ApiClient;
import com.yasinta.kesehatankucing.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailHistoriPeriksa extends Fragment {
    private String sId;

    RecyclerView rv_gejalaDetail;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;
    RequestQueue requestQueue;
    List<Histories> listHistori;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_history_periksa, container, false);

        rv_gejalaDetail = root.findViewById(R.id.rv_gejalaDetail);
        requestQueue = Volley.newRequestQueue(getContext());
        listHistori = new ArrayList<>();
        rvLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_gejalaDetail.setLayoutManager(rvLayoutManager);
        rvAdapter = new AdapterListDetailHistori(listHistori, getContext());
        rv_gejalaDetail.setAdapter(rvAdapter);

        catchValues(root);

        return root;
    }

    private void catchValues(View root) {
        if (getArguments() != null){
            sId = getArguments().getString("id");
            requestDetailHistoriById(sId);


//            Log.d("detail", "catchValues: " + getArguments().getString("nama_penyakit"));
        } else {
            Log.d("detail", "catchValues: NULL");
        }
    }

    private void requestDetailHistoriById(String sId) {
        String apiUrl = ApiClient.API + "data-riwayat-periksa/{" + sId + "}/show";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("respon", "Data Detail Histori: " + response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject histories = jsonArray.getJSONObject(i);

                                //get key 1 by 1
                                Histories model = new Histories();

                                model.setId(histories.getString("id"));
                                model.setData_jadwal_periksa_id(histories.getString("data_jadwal_periksa_id"));
                                model.setTanggal(histories.getString("tanggal"));

//                                user
                                Users u = new Users();
                                u.setNama(histories.getJSONObject("user").getString("nama_pemilik"));
                                u.setNama_kucing(histories.getJSONObject("user").getString("nama_kucing"));
                                u.setJenis_kucing(histories.getJSONObject("user").getString("jenis_kucing"));
                                model.setUsers(u);

//                                gejala
                                ArrayList<Gejalas> arrayListGejalas = new ArrayList<>();
                                JSONArray list_gejala = histories.getJSONArray("gejala");
                                for (int g=0; g<list_gejala.length(); g++){
                                    Gejalas gjl = new Gejalas();
                                    gjl.setNama_gejala(list_gejala.getJSONObject(g).getString("gejala"));
                                    arrayListGejalas.add(gjl);
                                }

                                model.setGejalas(arrayListGejalas);

                                model.setHasil_diagnosa(histories.getString("hasil_diagnosa"));
                                model.setSaran_pengobatan(histories.getString("saran_pengobatan"));

                                listHistori.add(model);
                                rvAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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