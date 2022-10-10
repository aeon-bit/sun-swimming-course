package com.nurul.swimmingcourse.ui.pembayaran;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nurul.swimmingcourse.BuildConfig;
import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.model.Gejalas;
import com.nurul.swimmingcourse.model.Pelatihs;
import com.nurul.swimmingcourse.model.ResponsePembayarans;
import com.nurul.swimmingcourse.model.ResponseSPPelatih;
import com.nurul.swimmingcourse.model.ResponseTesKesehatan;
import com.nurul.swimmingcourse.ui.jadwallatihan.HasilDiagnosaFragment;
import com.nurul.swimmingcourse.utils.ApiClient;
import com.nurul.swimmingcourse.utils.RealPathUtil;
import com.nurul.swimmingcourse.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kosalgeek.android.photoutil.ImageBase64;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Pembayaran extends Fragment {
    Spinner sp_hariInJadwal, sp_jamInJadwal, sp_pelatihInJadwal;
    String selectedIDPelatih, today;
    String pathToFile, encodeImage;
    ImageView iv_buktiBayar;
    ProgressBar progressBar;
    MultipartBody.Part fileToSend;
    File photoFile, gambar;

    EditText et_tglBayarPembayaran, et_jmlBayarPembayaran;

    String path;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pembayaran, container, false);


        EditText et_namaPembayaran = root.findViewById(R.id.et_namaPembayaran);
        et_tglBayarPembayaran = root.findViewById(R.id.et_tglBayarPembayaran);
        et_jmlBayarPembayaran = root.findViewById(R.id.et_jmlBayarPembayaran);
        ImageView iv_btnMediaPembayaran = root.findViewById(R.id.iv_btnMediaPembayaran);
        iv_buktiBayar = root.findViewById(R.id.iv_buktiBayar);
        CardView cv_btnPerformBayar = root.findViewById(R.id.cv_btnPerformBayar);


        getCurrentDate();

        et_namaPembayaran.setText(SessionManager.getUserData().getNama());

        iv_btnMediaPembayaran.setOnClickListener(v -> {
            pilihFotoFromGallery();

        });

        cv_btnPerformBayar.setOnClickListener(v -> {
            if (et_jmlBayarPembayaran.getText().toString().trim().equals("")) {
                et_jmlBayarPembayaran.setError("Masukkan nominal pembayaran");
            } else {
                PerformBayar(et_jmlBayarPembayaran.getText().toString().trim());
            }
        });

        return root;
    }

    private void pilihFotoFromGallery() {
//        callToast("Gunakan Foto Orientasi Portrait", 3);

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 10);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Context context = getContext();
            path = RealPathUtil.getRealPath(context, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
//            iv_buktiBayar.setImageBitmap(bitmap);
            iv_buktiBayar.setImageURI(uri);
        }
    }

    private void getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        today = dtf.format(now);

        et_tglBayarPembayaran.setText(today);

//        Log.d("tanggal", "getCurrentDate: " + dtf.format(now));
    }


    private void PerformBayar(String sJmlBayar) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("bukti_pembayaran", file.getName(), requestFile);

        RequestBody idSiswa = RequestBody.create(MediaType.parse("multipart/form-data"), SessionManager.getUserData().getId());
        RequestBody tglBayar = RequestBody.create(MediaType.parse("multipart/form-data"), et_tglBayarPembayaran.getText().toString());
        RequestBody jmlBayar = RequestBody.create(MediaType.parse("multipart/form-data"), sJmlBayar);

        Call<ResponsePembayarans> call = MainActivity.apiInterface.performBayar(
                "Bearer " + SessionManager.getToken(),
                body, idSiswa, tglBayar, jmlBayar
        );
        call.enqueue(new Callback<ResponsePembayarans>() {
            @Override
            public void onResponse(Call<ResponsePembayarans> call, retrofit2.Response<ResponsePembayarans> response) {

//                Log.d("respon", "onResponse Diagnosis: " + response.body().getData().getTanggal());
//                Log.d("respon", "onResponse Diagnosis: " + response.errorBody().toString());
                if (response.body() != null) {
//                    Log.d("respon", "onResponse ID: " + response.body().getSiswa_id());
//                    Log.d("respon", "onResponse TGL: " + response.body().getTanggal_bayar());
//                    Log.d("respon", "onResponse JML: " + response.body().getJumlah_bayar());
                    if (response.body().getSiswa_id() == null) {
                        callToast("Isi Form Salah", 2);
                    } else {
                        callToast("Berhasil mengunggah data", 1);
                        ((MainActivity) getActivity()).SwitchFrag(0);
                    }
                } else {
                    callToast("Ulangi lagi", 2);
                }
            }

            @Override
            public void onFailure(Call<ResponsePembayarans> call, Throwable t) {

                Log.d("respon", "NORES bayar: " + t.getMessage());
                callToast("Kesalahan koneksi", 2);
            }
        });

    }

    private void callToast(String msg, int i) {
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            View view = toast.getView();
            view.setPadding(42, 12, 42, 12);
            if (i == 1) {
                view.setBackgroundResource(R.drawable.xmlbg_toast_success);
            } else if (i == 2) {
                view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
            } else {
                view.setBackgroundResource(R.drawable.xmlbg_toast_info);
            }
            TextView textView = view.findViewById(android.R.id.message);
            textView.setTextColor(Color.WHITE);
        }
        toast.show();
    }

}