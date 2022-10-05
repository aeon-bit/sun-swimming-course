package com.nurul.swimmingcourse.ui.auth;


import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.model.Registers;
import com.nurul.swimmingcourse.utils.APIError;
import com.nurul.swimmingcourse.utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText et_nameRegisterForm, et_noTelpRegisterForm, et_tglLahirRegisterForm,
            et_tempatLahirRegisterForm, et_namaOrtuRegisterForm, et_namaKucingRegisterForm,
            et_jenisKucingRegisterForm, et_alamatRegisterForm, et_usernameRegisterForm,
            et_emailRegisterForm, et_passwordRegisterForm, et_cPasswordRegisterForm;
    private RadioButton rb_laki, rb_perempuan;
    private Button btn_registerForm;
    private TextView tvBtn_loginNow;
    private EditText rb_errorMsg;

    private ProgressBar pb_loading;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        et_nameRegisterForm = view.findViewById(R.id.et_nameRegisterForm);
        et_tglLahirRegisterForm = view.findViewById(R.id.et_tglLahirRegisterForm);
        et_tempatLahirRegisterForm = view.findViewById(R.id.et_tempatLahirRegisterForm);
        rb_laki = view.findViewById(R.id.rb_laki);
        rb_perempuan = view.findViewById(R.id.rb_perempuan);
        et_namaOrtuRegisterForm = view.findViewById(R.id.et_namaOrtuRegisterForm);
        et_alamatRegisterForm = view.findViewById(R.id.et_alamatRegisterForm);
        et_noTelpRegisterForm = view.findViewById(R.id.et_noHpRegisterForm);
        et_usernameRegisterForm = view.findViewById(R.id.et_usernameRegisterForm);
        et_passwordRegisterForm = view.findViewById(R.id.et_passwordRegisterForm);
        et_cPasswordRegisterForm = view.findViewById(R.id.et_cPasswordRegisterForm);

        pb_loading = view.findViewById(R.id.pb_loading);
        pb_loading.setVisibility(View.INVISIBLE);

        TextView tv_btnLoginNow = view.findViewById(R.id.tv_btnLoginNow);

//        rb_laki = view.findViewById(R.id.rb_laki);
//        rb_perempuan = view.findViewById(R.id.rb_perempuan);
//        rb_errorMsg = view.findViewById(R.id.rb_errorMsg);

        tv_btnLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).SwitchFrag(7);
            }
        });

        CardView cv_btnPerformRegister = view.findViewById(R.id.cv_btnPerformRegister);
        cv_btnPerformRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sNama = et_nameRegisterForm.getText().toString();
                String sTgl = et_tglLahirRegisterForm.getText().toString();
                String sTempatLahir = et_tempatLahirRegisterForm.getText().toString();
                String sOrtu = et_namaOrtuRegisterForm.getText().toString();
                String sAlamat = et_alamatRegisterForm.getText().toString();
                String sNoHp = et_noTelpRegisterForm.getText().toString().trim();
                String sUsername = et_usernameRegisterForm.getText().toString().trim();
                String sPassword = et_passwordRegisterForm.getText().toString();
                String sCPassword = et_cPasswordRegisterForm.getText().toString();
                String sJk = "";
                if (rb_laki.isChecked()) {
                    sJk = "Laki-laki";
                } else if (rb_perempuan.isChecked()){
                    sJk = "Perempuan";
                } else {
                    sJk = "Laki-laki";
                }
//                if (sNoHp.isEmpty() || sNoHp == null) {
//                    sNoHp = "";
//                }

                if (sNama.equals("")) {
                    et_nameRegisterForm.setError("Masukkan nama");
                } else if (sAlamat.equals("")) {
                    et_alamatRegisterForm.setError("Masukkan alamat");
//                } else if (!rb_laki.isChecked() && !rb_perempuan.isChecked()) {
//                    rb_errorMsg.setError("isi jenis kelamin");
                } else if (sTgl.equals("")){
                    et_tglLahirRegisterForm.setError("Masukkan tanggal lahir");
                } else if (sTempatLahir.equals("")){
                    et_tempatLahirRegisterForm.setError("Masukkan tempat lahir");
                } else if (sOrtu.equals("")){
                    et_namaOrtuRegisterForm.setError("Masukkan nama orang tua");
                } else if (sUsername.equals("")) {
                    et_usernameRegisterForm.setError("Masukkan username");
                } else if (sNoHp.equals("")) {
                    et_noTelpRegisterForm.setError("Masukkan No Hp");
                } else if (sNoHp.length() < 11) {
                    et_noTelpRegisterForm.setError("Minimal 11 karakter");
                } else if (sPassword.equals("")) {
                    et_passwordRegisterForm.setError("isi password");
                } else if (sPassword.length() < 8) {
                    et_passwordRegisterForm.setError("Minimal 6 karakter");
                } else if (!sCPassword.equals(sPassword)) {
                    et_cPasswordRegisterForm.setError("password tidak sesuai");
//                } else if (!isValidEmail(sEmail)) {
//                    et_emailRegisterForm.setError("Format Email salah");
                } else {
                    performRegistration(sNama, sTempatLahir, sTgl, sJk, sOrtu, sAlamat, sNoHp, sUsername, sPassword);
                    hideKeyboard();
//                    Log.d("daftar", sNama + "\n");
//                    Log.d("daftar", sTempatLahir + "\n");
//                    Log.d("daftar", sTgl + "\n");
//                    Log.d("daftar", sJk + "\n");
//                    Log.d("daftar", sOrtu + "\n");
//                    Log.d("daftar", sAlamat + "\n");
//                    Log.d("daftar", sNoHp + "\n");
//                    Log.d("daftar", sUsername + "\n");
//                    Log.d("daftar", sPassword + "\n");
//                    Log.d("daftar", sCPassword + "\n");
                    pb_loading.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void performRegistration(String sNama, String sTempatLahir, String sTgl, String sJk, String sOrtu, String sAlamat, String sNoHp, String sUsername, String sPassword) {


        Call<Registers> call = MainActivity.apiInterface.performRegistration(
//                "tNama",
//                "tAlamat",
//                "99999",
//                "tNamaKucing",
//                "tJenis",
//                "email@email.com",
//                "123",
//                "123"
                sNama, sTempatLahir, sTgl, sJk, sOrtu, sAlamat, sNoHp, sUsername, sPassword
        );
        call.enqueue(new Callback<Registers>() {
            @Override
            public void onResponse(Call<Registers> call, Response<Registers> response) {

//                if (response.body().getMessage().equals("register sukses")) {
//
//                    Toast toast = Toast.makeText(getActivity(), "Pendaftaran Berhasil", Toast.LENGTH_LONG);
//                    View view = toast.getView();
//                    view.setBackgroundResource(R.drawable.xmlbg_toast_success);
//                    TextView textView = view.findViewById(android.R.id.message);
//                    textView.setTextColor(Color.WHITE);
//                    toast.show();
//
//                    ((MainActivity) getContext()).logoutPerform(); //restart actv
//                }
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("register sukses")) {
                        callToast("Pendaftaran berhasil", 1);

                        ((MainActivity) getContext()).logoutPerform(); //restart actv
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);

                    callToast("Server Error", 0);

                    Log.d("error message", error.message());
                }
            }

            @Override
            public void onFailure(Call<Registers> call, Throwable t) {

                Log.d("daftar", "onFaillure: " + t.toString());
                Toast toast = Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
                TextView textView = view.findViewById(android.R.id.message);
                textView.setTextColor(Color.WHITE);
                toast.show();
            }
        });

        pb_loading.setVisibility(View.INVISIBLE);
    }

    private void callToast(String msg, int i) {
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
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

//        et_nameRegisterForm.setText("");
//        et_nikRegisterForm.setText("");
//        et_noTelpRegisterForm.setText("");
//        et_alamatRegisterForm.setText("");
//        et_usernameRegisterForm.setText("");
//        et_passwordRegisterForm.setText("");

}
