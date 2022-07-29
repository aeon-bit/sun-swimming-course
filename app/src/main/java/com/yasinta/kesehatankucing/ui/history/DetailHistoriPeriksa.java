package com.yasinta.kesehatankucing.ui.history;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.yasinta.kesehatankucing.activity.MainActivity;
import com.yasinta.kesehatankucing.adapter.AdapterListDetailHistori;
import com.yasinta.kesehatankucing.adapter.AdapterListGejalaDetailDiagnosis;
import com.yasinta.kesehatankucing.adapter.AdapterListHistoryPeriksa;
import com.yasinta.kesehatankucing.adapter.AdapterListKesimpulanDetailDiagnosis;
import com.yasinta.kesehatankucing.adapter.AdapterListSaranDetailDiagnosis;
import com.yasinta.kesehatankucing.model.Gejalas;
import com.yasinta.kesehatankucing.model.HasilSarans;
import com.yasinta.kesehatankucing.model.Histories;
import com.yasinta.kesehatankucing.model.ResponseTesKesehatan;
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

import retrofit2.Call;
import retrofit2.Callback;

public class DetailHistoriPeriksa extends Fragment {
    private String sId;

    RecyclerView rv_gejalaDetail;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;
    RequestQueue requestQueue;
    List<Histories> listHistori;
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

    TextView tv_namaKucingDetailHistory, tv_jenisKucingDetailHistory,
    tv_namaPemilikDetailHistory, tv_tglPeriksaDetailHistory,
    tv_hasilDiagnosaDetailHistory, tv_saranDetailHistory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_history_periksa, container, false);

        tv_namaKucingDetailHistory = root.findViewById(R.id.tv_namaKucingDetailHistory);
        tv_jenisKucingDetailHistory = root.findViewById(R.id.tv_jenisKucingDetailHistory);
        tv_namaPemilikDetailHistory = root.findViewById(R.id.tv_namaPemilikDetailHistory);
        tv_tglPeriksaDetailHistory = root.findViewById(R.id.tv_tglPeriksaDetailHistory);
//        tv_hasilDiagnosaDetailHistory = root.findViewById(R.id.tv_hasilDiagnosaDetailHistory);
//        tv_saranDetailHistory = root.findViewById(R.id.tv_saranDetailHistory);

//        rv_gejalaDetail = root.findViewById(R.id.rv_gejalaDetail);
//        requestQueue = Volley.newRequestQueue(getContext());
//        listHistori = new ArrayList<>();
//        rvLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        rv_gejalaDetail.setLayoutManager(rvLayoutManager);
//        rvAdapter = new AdapterListDetailHistori(listHistori, getContext());
//        rv_gejalaDetail.setAdapter(rvAdapter);

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
        Call<ResponseTesKesehatan> call = MainActivity.apiInterface.getDetailDiagnosa(
                "Bearer " + SessionManager.getToken(), sId
        );

        Fragment self = this;
        call.enqueue(new Callback<ResponseTesKesehatan>() {
            @Override
            public void onResponse(Call<ResponseTesKesehatan> call, retrofit2.Response<ResponseTesKesehatan> response) {
                Log.d("respon", "onResponse Diagnosis: " + response.toString());

                tv_namaKucingDetailHistory.setText(response.body().getData().getUser().getNama_kucing());
                tv_jenisKucingDetailHistory.setText(response.body().getData().getUser().getJenis_kucing());
                tv_namaPemilikDetailHistory.setText(response.body().getData().getUser().getNama_pemilik());
                tv_tglPeriksaDetailHistory.setText(response.body().getData().getTanggal());
//                tv_hasilDiagnosaDetailHistory.setText(response.body().getData().getHasil_diagnosa() + "\n ");
//                tv_saranDetailHistory.setText(response.body().getData().getSaran_pengobatan() + "\n ");

//                tv_namaKucingHasilD.setText(response.body().getData().getUser().getNama_kucing());
//                tv_jenisKucingHasilD.setText(response.body().getData().getUser().getJenis_kucing());
//                tv_namaPemilikHasilD.setText(response.body().getData().getUser().getNama_pemilik());
//
//                tv_hasilDiagnosaDetail.setText(response.body().getData().getHasil_diagnosa());
//                tv_saranDetail.setText(response.body().getData().getSaran_pengobatan());

//                listGejalas = response.body().getData().getGejala();
//                Log.d("respons", "LISTGEJALAS: " + Arrays.toString(listGejalas.toArray()));
//                rvAdapter.notifyDataSetChanged();
                ArrayList<Gejalas> arrayListGejalas = response.body().getData().getGejala();
                for (int i = 0; i<arrayListGejalas.size(); i++){
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
            }

            @Override
            public void onFailure(Call<ResponseTesKesehatan> call, Throwable t) {

                Log.d("daftar", "onFaillure: " + t.toString());
                Toast toast = Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
                TextView textView = view.findViewById(android.R.id.message);
                textView.setTextColor(Color.WHITE);
                toast.show();
            }
        });
    }

//    private void requestDetailHistoriById(String sId) {
//        String apiUrl = ApiClient.API + "data-riwayat-diagnosa/" + sId + "/show";
//
////        Log.d("respon", "requestDetailHistoriById: " + apiUrl);
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("respon", "Data Detail Histori: " + response.toString());
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("data");
//
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject histories = jsonArray.getJSONObject(i);
//
//                                //get key 1 by 1
//                                Histories model = new Histories();
//                                Log.d("detail", "TANGGAL: " + histories.getString("tanggal"));
//
//
////                                model.setId(histories.getString("id"));
////                                model.setData_jadwal_periksa_id(histories.getString("data_jadwal_periksa_id"));
////                                model.setTanggal(histories.getString("tanggal"));
////
//////                                user
////                                Users u = new Users();
////                                u.setNama(histories.getJSONObject("user").getString("nama_pemilik"));
////                                u.setNama_kucing(histories.getJSONObject("user").getString("nama_kucing"));
////                                u.setJenis_kucing(histories.getJSONObject("user").getString("jenis_kucing"));
////                                model.setUsers(u);
////
//////                                gejala
////                                ArrayList<Gejalas> arrayListGejalas = new ArrayList<>();
////                                JSONArray list_gejala = histories.getJSONArray("gejala");
////                                for (int g=0; g<list_gejala.length(); g++){
////                                    Gejalas gjl = new Gejalas();
////                                    gjl.setNama_gejala(list_gejala.getJSONObject(g).getString("gejala"));
////                                    arrayListGejalas.add(gjl);
////                                }
////
////                                model.setGejalas(arrayListGejalas);
////
////                                model.setHasil_diagnosa(histories.getString("hasil_diagnosa"));
////                                model.setSaran_pengobatan(histories.getString("saran_pengobatan"));
//
//                                listHistori.add(model);
//                                rvAdapter.notifyDataSetChanged();
//
//
////                                tv_namaKucingDetailHistory.setText(u.getNama_kucing());
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.d("detail", "CATCH DETAIL");
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