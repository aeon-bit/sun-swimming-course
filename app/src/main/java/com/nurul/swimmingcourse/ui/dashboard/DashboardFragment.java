package com.nurul.swimmingcourse.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.nurul.swimmingcourse.adapter.AdapterListArtikel;
import com.nurul.swimmingcourse.model.Artikels;
import com.nurul.swimmingcourse.utils.ApiClient;
import com.nurul.swimmingcourse.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    RecyclerView rv_arrtikelHome;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;
    RequestQueue requestQueue;
    List<Artikels> listArtikels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        CardView cv_btnJadwalLatihan = root.findViewById(R.id.cv_btnJadwalLatihan);
        CardView cv_btnDaftarPelatih = root.findViewById(R.id.cv_btnDaftarPelatih);
        CardView cv_btnPendaftaran = root.findViewById(R.id.cv_btnPendaftaran);
        CardView cv_btnTentang = root.findViewById(R.id.cv_btnTentang);

        cv_btnJadwalLatihan.setOnClickListener(this);
        cv_btnDaftarPelatih.setOnClickListener(this);
        cv_btnPendaftaran.setOnClickListener(this);
        cv_btnTentang.setOnClickListener(this);

        rv_arrtikelHome = root.findViewById(R.id.rv_arrtikelHome);
        requestQueue = Volley.newRequestQueue(getContext());
        listArtikels = new ArrayList<>();

//        requestAllArtikels();

        rvLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_arrtikelHome.setLayoutManager(rvLayoutManager);
        rvAdapter = new AdapterListArtikel(listArtikels, getContext());
        rv_arrtikelHome.setAdapter(rvAdapter);

        return root;
    }

    private void requestAllArtikels() {
        String apiUrl = ApiClient.API + "data-artikel/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d("respon", "Data artikel: " + response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject artikel = jsonArray.getJSONObject(i);

                                //get key 1 by 1
                                Artikels model = new Artikels();

                                model.setJudul_artikel(artikel.getString("judul_artikel"));
                                model.setDeskripsi(artikel.getString("deskripsi"));
                                model.setGambar(artikel.getString("gambar"));

                                listArtikels.add(model);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_btnJadwalLatihan:
                ((MainActivity) getActivity()).SwitchFrag(1);

                break;
            case R.id.cv_btnDaftarPelatih:
                ((MainActivity) getActivity()).SwitchFrag(2);
                break;
            case R.id.cv_btnPendaftaran:
                ((MainActivity) getActivity()).SwitchFrag(3);
                break;
            case R.id.cv_btnTentang:
                ((MainActivity) getActivity()).SwitchFrag(4);
                break;
        }
    }
}