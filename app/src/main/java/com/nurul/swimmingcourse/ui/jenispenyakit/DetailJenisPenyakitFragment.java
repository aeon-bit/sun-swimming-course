package com.nurul.swimmingcourse.ui.jenispenyakit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nurul.swimmingcourse.R;

public class DetailJenisPenyakitFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_jenis_penyakit, container, false);

        catchValues(root);

//        requestAllJenisPenyakit();


        return root;
    }

    private void catchValues(View root) {
        if (getArguments() != null){
            TextView tv_namaJenisPenyakitDetail = root.findViewById(R.id.tv_namaJenisPenyakitDetail);
            TextView tv_deskripsiPenyakitDetail = root.findViewById(R.id.tv_deskripsiPenyakitDetail);
            TextView tv_pengobatanPenyakitDetail = root.findViewById(R.id.tv_pengobatanPenyakitDetail);

            tv_namaJenisPenyakitDetail.setText(getArguments().getString("nama_penyakit", "-tidak ada data-"));
            tv_deskripsiPenyakitDetail.setText(getArguments().getString("deskripsi", "-tidak ada data-"));
            tv_pengobatanPenyakitDetail.setText(getArguments().getString("pengobatan", "-tidak ada data-"));

            Log.d("detail", "catchValues: " + getArguments().getString("nama_penyakit"));
        } else {
            Log.d("detail", "catchValues: NULL");
        }
    }

//    private void requestAllJenisPenyakit() {
//        String apiUrl = ApiClient.API + "data-penyakit/";
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("respon", "Data jenis penyakit: " + response.toString());
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("data");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject artikel = jsonArray.getJSONObject(i);
//
//                                //get key 1 by 1
//                                JenisPenyakits model = new JenisPenyakits();
//
//                                model.setId(artikel.getString("id"));
//                                model.setKode_penyakit(artikel.getString("kode_penyakit"));
//                                model.setNama_penyakit(artikel.getString("nama_penyakit"));
//                                model.setDeskripsi(artikel.getString("deskripsi"));
//                                model.setPengobatan(artikel.getString("pengobatan"));
//
//                                listJenis.add(model);
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        rvAdapter.notifyDataSetChanged();
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