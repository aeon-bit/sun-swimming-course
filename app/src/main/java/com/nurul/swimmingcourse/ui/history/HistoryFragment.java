package com.nurul.swimmingcourse.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.adapter.AdapterListHistoryPeriksa;
import com.nurul.swimmingcourse.model.Gejalas;
import com.nurul.swimmingcourse.model.Histories;
import com.nurul.swimmingcourse.utils.ApiClient;
import com.nurul.swimmingcourse.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryFragment extends Fragment {
    ProgressBar pb_loading;

    RecyclerView rv_historiPeriksa;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;
    RequestQueue requestQueue;
    List<Histories> listHistori;

    TextView tv_noData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        rv_historiPeriksa = root.findViewById(R.id.rv_historiPeriksa);
        requestQueue = Volley.newRequestQueue(getContext());
        listHistori = new ArrayList<>();
        rvLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_historiPeriksa.setLayoutManager(rvLayoutManager);
        rvAdapter = new AdapterListHistoryPeriksa(listHistori, getContext());
        rv_historiPeriksa.setAdapter(rvAdapter);

        tv_noData = root.findViewById(R.id.tv_noData);
        tv_noData.setVisibility(View.INVISIBLE);

        pb_loading = root.findViewById(R.id.pb_loading);
        pb_loading.setVisibility(View.VISIBLE);

        requestAllHistory();
//        requestAllHistoryById();
        return root;
    }

    private void requestAllHistory() {
        pb_loading.setVisibility(View.INVISIBLE);
        String apiUrl = ApiClient.API + "data-riwayat-diagnosa/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("respon", "Data History: " + response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            if (jsonArray.length() < 1){
                                tv_noData.setVisibility(View.VISIBLE);
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject histories = jsonArray.getJSONObject(i);

                                //get key 1 by 1
                                Histories model = new Histories();

                                model.setId(histories.getString("id"));
//                                model.setData_jadwal_periksa_id(histories.getString("data_jadwal_periksa_id"));
                                model.setTanggal(histories.getString("tanggal"));
                                model.setNama_kucing(histories.getString("nama_kucing"));
                                model.setJenis_kucing(histories.getString("jenis_kucing"));

//                                user
//                                Users u = new Users();
//                                u.setNama(histories.getJSONObject("user").getString("nama_pemilik"));
//                                u.setNama_kucing(histories.getJSONObject("user").getString("nama_kucing"));
//                                u.setJenis_kucing(histories.getJSONObject("user").getString("jenis_kucing"));
//                                model.setUsers(u);

//                                Log.d("detail", "NAMA_KUCING: " + histories.getJSONObject("user").getString("nama_kucing"));

//                                gejala
                                ArrayList<Gejalas> arrayListGejalas = new ArrayList<>();
                                JSONArray list_gejala = histories.getJSONArray("gejala");
                                for (int g=0; g<list_gejala.length(); g++){
                                    Gejalas gjl = new Gejalas();
                                    gjl.setNama_gejala(list_gejala.getJSONObject(g).getString("gejala"));
                                    arrayListGejalas.add(gjl);
                                }

                                model.setGejalas(arrayListGejalas);

//                                model.setHasil_diagnosa(histories.getString("hasil_diagnosa"));
//                                model.setSaran_pengobatan(histories.getString("saran_pengobatan"));

                                listHistori.add(model);
                                rvAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("detail", "CATCH LIST HISTORY");
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
//    private void requestAllHistoryById() {
//        String apiUrl = ApiClient.API + "data-riwayat-diagnosa/{"+ SessionManager.getUserData().getId() + "}/show";
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("respon", "Data History: " + response.toString());
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("data");
//
//                            if (jsonArray.length() < 1){
//                                tv_noData.setVisibility(View.VISIBLE);
//                            }
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject histories = jsonArray.getJSONObject(i);
//
//                                //get key 1 by 1
//                                Histories model = new Histories();
//
//                                model.setId(histories.getString("id"));
//                                model.setData_jadwal_periksa_id(histories.getString("data_jadwal_periksa_id"));
//                                model.setTanggal(histories.getString("tanggal"));
//
////                                user
//                                Users u = new Users();
//                                u.setNama(histories.getJSONObject("user").getString("nama_pemilik"));
//                                u.setNama_kucing(histories.getJSONObject("user").getString("nama_kucing"));
//                                u.setJenis_kucing(histories.getJSONObject("user").getString("jenis_kucing"));
//                                model.setUsers(u);
//
////                                Log.d("detail", "NAMA_KUCING: " + histories.getJSONObject("user").getString("nama_kucing"));
//
////                                gejala
//                                ArrayList<Gejalas> arrayListGejalas = new ArrayList<>();
//                                JSONArray list_gejala = histories.getJSONArray("gejala");
//                                for (int g=0; g<list_gejala.length(); g++){
//                                    Gejalas gjl = new Gejalas();
//                                    gjl.setNama_gejala(list_gejala.getJSONObject(g).getString("gejala"));
//                                    arrayListGejalas.add(gjl);
//                                }
//
//                                model.setGejalas(arrayListGejalas);
//
//                                model.setHasil_diagnosa(histories.getString("hasil_diagnosa"));
//                                model.setSaran_pengobatan(histories.getString("saran_pengobatan"));
//
//                                listHistori.add(model);
//                                rvAdapter.notifyDataSetChanged();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.d("detail", "CATCH LIST HISTORY");
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Log.d("respon", "No RES: " + error.toString());
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
////                HashMap header = new HashMap();
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json");
//                params.put("Authorization", "Bearer " + SessionManager.getToken());
//                return params;
//            }
//        };
//
//        requestQueue.add(request);
//    }

}