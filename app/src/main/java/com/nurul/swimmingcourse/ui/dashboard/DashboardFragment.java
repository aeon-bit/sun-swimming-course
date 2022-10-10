package com.nurul.swimmingcourse.ui.dashboard;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.nurul.swimmingcourse.dialog.DialogMenuPenjadwalan;
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

//        Log.d("artikel", "START TOKEN: " + SessionManager.getToken());
//        Log.d("artikel", "START RULE: " + SessionManager.getRole());

        CardView cv_btnJadwalLatihan = root.findViewById(R.id.cv_btnJadwalLatihan);
        CardView cv_btnDaftarPelatih = root.findViewById(R.id.cv_btnDaftarPelatih);
        CardView cv_btnDaftarPelatih_p = root.findViewById(R.id.cv_btnDaftarPelatih_P);
        CardView cv_btnPendaftaran = root.findViewById(R.id.cv_btnPendaftaran);
        CardView cv_btnPembayaran = root.findViewById(R.id.cv_btnPembayaran);
        CardView cv_btnInputPerkembangan = root.findViewById(R.id.cv_btnInputPerkembangan);
        CardView cv_btnTentang = root.findViewById(R.id.cv_btnTentang);
        CardView cv_btnTentang_p = root.findViewById(R.id.cv_btnTentang_p);
        CardView cv_perkembangan = root.findViewById(R.id.cv_btnPerkembangan);
        ImageView iv_avatar = root.findViewById(R.id.iv_avatar);

        LinearLayoutCompat ly_lineSiswa = root.findViewById(R.id.line1_s);
        LinearLayoutCompat ly_linePelatih = root.findViewById(R.id.line1_p);
        LinearLayoutCompat ly_line2 = root.findViewById(R.id.line2);
        LinearLayoutCompat ly_line2_p = root.findViewById(R.id.line2_p);
        LinearLayoutCompat ly_line3 = root.findViewById(R.id.line3);

        if (SessionManager.getRole().equals("pelatih")) {
            ly_lineSiswa.setVisibility(View.INVISIBLE);
            ly_linePelatih.setVisibility(View.VISIBLE);
            ly_line2.setVisibility(View.INVISIBLE);
            ly_line2_p.setVisibility(View.VISIBLE);
            ly_line3.setVisibility(View.INVISIBLE);
        } else {
            ly_lineSiswa.setVisibility(View.VISIBLE);
            ly_linePelatih.setVisibility(View.INVISIBLE);
            ly_line2.setVisibility(View.VISIBLE);
            ly_line2_p.setVisibility(View.INVISIBLE);
            ly_line3.setVisibility(View.VISIBLE);
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
        cv_btnDaftarPelatih_p.setOnClickListener(this);
        cv_btnPendaftaran.setOnClickListener(this);
        cv_btnPembayaran.setOnClickListener(this);
        cv_btnTentang.setOnClickListener(this);
        cv_btnTentang_p.setOnClickListener(this);

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
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject artikel = jsonArray.getJSONObject(i);

                                //get key 1 by 1
                                Artikels model = new Artikels();

                                model.setId(artikel.getString("id"));
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
//                Log.d("artikel", "No RES: " + error.getMessage());
//                Log.d("artikel", "No RES TOKEN: " + SessionManager.getToken());
//                Log.d("artikel", "No RES ROLE: " + SessionManager.getRole());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap header = new HashMap();
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer " + SessionManager.getToken());
//                params.put("Authorization", "Bearer 46|eatlS5ftHgLWvZOqNf8l5LrqEZASYS6ab5dyB2Rx");
                return params;
            }
        };

        requestQueue.add(request);
    }

    @Override
    public void onClick(View view) {
        int frag = 0;
        switch (view.getId()) {

            case R.id.cv_btnJadwalLatihan:
//                openDialogPenjadwalan();
                frag = 1;
                break;
            case R.id.cv_btnDaftarPelatih:
            case R.id.cv_btnDaftarPelatih_P:
                frag = 2;
                break;
            case R.id.cv_btnPendaftaran:
                frag = 8;
                break;
            case R.id.cv_btnTentang:
            case R.id.cv_btnTentang_p:
                frag = 6;
                break;
            case R.id.cv_btnPembayaran:
                frag = 13;
                break;
            case R.id.cv_btnPerkembangan:
                frag = 11;
                break;
            case R.id.cv_btnInputPerkembangan:
                frag = 12;
                break;
        }

        if (frag == 1) {
            if (SessionManager.isLogin())
                openDialogPenjadwalan();
            else
                callToast("Silakan login dahulu", 0);
        } else if (frag == 6) { //tentang
            ((MainActivity) getActivity()).SwitchFrag(frag);
        } else {
            if (SessionManager.isLogin()) {
                ((MainActivity) getActivity()).SwitchFrag(frag);
            } else {
                callToast("Silakan login dahulu", 0);
            }
        }
    }

    private void openDialogPenjadwalan() {

        DialogMenuPenjadwalan dialogMenuPenjadwalan = new DialogMenuPenjadwalan(getContext());
        dialogMenuPenjadwalan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout ly_btnPesanJadwalDialog = dialogMenuPenjadwalan.findViewById(R.id.ly_btnPesanJadwalDialog);
        LinearLayout ly_btnLihatJadwalDialog = dialogMenuPenjadwalan.findViewById(R.id.ly_btnLihatJadwalDialog);
        LinearLayout ly_btnCloseDialog = dialogMenuPenjadwalan.findViewById(R.id.ly_btnCloseDialog);

        ly_btnPesanJadwalDialog.setOnClickListener(v -> {
            ((MainActivity) getActivity()).SwitchFrag(1);

            dialogMenuPenjadwalan.dismiss();
        });

        ly_btnLihatJadwalDialog.setOnClickListener(v -> {
            ((MainActivity) getActivity()).SwitchFrag(14);
            dialogMenuPenjadwalan.dismiss();
        });

        ly_btnCloseDialog.setOnClickListener(v -> {
            dialogMenuPenjadwalan.dismiss();
        });

        dialogMenuPenjadwalan.show();
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

    private void callToast(String msg, int i) {
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            View view = toast.getView();
            view.setPadding(42, 12, 42, 12);
            if (i == 1) {
                view.setBackgroundResource(R.drawable.xmlbg_toast_success);
            } else {
                view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
            }
            TextView textView = view.findViewById(android.R.id.message);
            textView.setTextColor(Color.WHITE);
        }
        toast.show();
    }
}