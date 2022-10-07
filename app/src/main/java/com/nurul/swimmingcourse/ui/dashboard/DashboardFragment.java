package com.nurul.swimmingcourse.ui.dashboard;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
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
import com.nurul.swimmingcourse.dialog.DialogConfirmLogout;
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
        CardView cv_btnInputPerkembangan = root.findViewById(R.id.cv_btnInputPerkembangan);
        CardView cv_btnTentang = root.findViewById(R.id.cv_btnTentang);
        CardView cv_perkembangan = root.findViewById(R.id.cv_btnPerkembangan);
        ImageView iv_avatar = root.findViewById(R.id.iv_avatar);

        LinearLayoutCompat ly_lineSiswa = root.findViewById(R.id.line1_s);
        LinearLayoutCompat ly_linePelatih = root.findViewById(R.id.line1_p);
        LinearLayoutCompat ly_line2 = root.findViewById(R.id.line2);

        if (SessionManager.getRole().equals("pelatih")) {
            ly_lineSiswa.setVisibility(View.INVISIBLE);
            ly_linePelatih.setVisibility(View.VISIBLE);
            ly_line2.setVisibility(View.INVISIBLE);
        } else {
            ly_lineSiswa.setVisibility(View.VISIBLE);
            ly_linePelatih.setVisibility(View.INVISIBLE);
        }


        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionManager.isLogin()) {
                    ((MainActivity) getActivity()).SwitchFrag(9);
                } else {
                    ((MainActivity) getActivity()).SwitchFrag(7);
                }
            }
        });

        cv_btnJadwalLatihan.setOnClickListener(this);
        cv_btnDaftarPelatih.setOnClickListener(this);
        cv_btnPendaftaran.setOnClickListener(this);
        cv_btnTentang.setOnClickListener(this);

        cv_perkembangan.setOnClickListener(this);
        cv_btnInputPerkembangan.setOnClickListener(this);


        rv_arrtikelHome = root.findViewById(R.id.rv_arrtikelHome);
        requestQueue = Volley.newRequestQueue(getContext());
        listArtikels = new ArrayList<>();

        requestAllArtikels();

        rvLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_arrtikelHome.setLayoutManager(rvLayoutManager);
        rvAdapter = new AdapterListArtikel(listArtikels, getContext());
        rv_arrtikelHome.setAdapter(rvAdapter);

        return root;
    }

    private void requestAllArtikels() {
        String apiUrl = ApiClient.API + "informasi/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("artikel", "Data artikel: " + response.toString());
                        Log.d("artikel", "RES TOKEN: " + SessionManager.getToken());
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject artikel = jsonArray.getJSONObject(i);

                                //get key 1 by 1
                                Artikels model = new Artikels();

                                model.setId(artikel.getString("judul_artikel"));
                                model.setJudul_info(artikel.getString("judul_info"));
                                model.setDetail_info(artikel.getString("detail_info"));
                                model.setFoto(artikel.getString("foto"));

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
                Log.d("artikel", "No RES: " + error.getMessage());
                Log.d("artikel", "No RES TOKEN: " + SessionManager.getToken());
                Log.d("artikel", "No RES ROLE: " + SessionManager.getRole());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap header = new HashMap();
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer " + SessionManager.getToken());
//                params.put("Authorization", "Bearer 27|Sz7s6wAWaNGwkcef8v1HQ8JqFxEG9fmnckNJlCOc");
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
//                if (SessionManager.isLogin()){
//                    LogoutConfirm();
//                }
                ((MainActivity) getActivity()).SwitchFrag(8);
                break;
            case R.id.cv_btnTentang:
                ((MainActivity) getActivity()).SwitchFrag(6);
                break;

            case R.id.cv_btnPerkembangan:
                ((MainActivity) getActivity()).SwitchFrag(11);
                break;
            case R.id.cv_btnInputPerkembangan:
                ((MainActivity) getActivity()).SwitchFrag(12);
                break;
        }
    }

    private void LogoutConfirm() {
        DialogConfirmLogout dialogConfirmLogout = new DialogConfirmLogout(getContext());
        dialogConfirmLogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout ly_btnConfirmLogout = dialogConfirmLogout.findViewById(R.id.ly_btnConfirmLogout);
        LinearLayout ly_btnBatalLogout = dialogConfirmLogout.findViewById(R.id.ly_btnBatalLogout);

        ly_btnConfirmLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.logout();
                dialogConfirmLogout.dismiss();
                ((MainActivity) getActivity()).SwitchFrag(8);
            }
        });

        ly_btnBatalLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmLogout.dismiss();
            }
        });

        dialogConfirmLogout.show();
    }
}