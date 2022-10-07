package com.nurul.swimmingcourse.ui.auth;


import android.app.Activity;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.model.Logins;
import com.nurul.swimmingcourse.utils.APIError;
import com.nurul.swimmingcourse.utils.ErrorUtils;
import com.nurul.swimmingcourse.utils.SessionManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText et_usernameLogin, et_passwordLogin;
    ProgressBar pb_loading;

    OnLoginFormActivityListener loginFormActivityListener;

    public interface OnLoginFormActivityListener {
        public void performRegister();

        public void performLogin(String username);
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        pb_loading = view.findViewById(R.id.pb_loading);
        et_usernameLogin = view.findViewById(R.id.et_usernameLogin);
        et_passwordLogin = view.findViewById(R.id.et_passwordLogin);
        LinearLayout ly_btnSkip = view.findViewById(R.id.ly_btnSkip);

        CardView cv_loginPerformLogin = view.findViewById(R.id.cv_loginPerformLogin);
        TextView tv_btnRegisterNow = view.findViewById(R.id.tv_btnRegisterNow);

        cv_loginPerformLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_usernameLogin.getText().toString().isEmpty()) {
                    et_usernameLogin.setError("Masukkan username");
                } else if (et_passwordLogin.getText().toString().isEmpty()) {
                    et_passwordLogin.setError("Masukkan password");
//                } else if (!isValidEmail(et_usernameLogin.getText().toString())) {
//                    et_usernameLogin.setError("Format Email salah");
                } else {
                    performLogin();
                    pb_loading.setVisibility(View.VISIBLE);
                }
            }
        });

        tv_btnRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).SwitchFrag(8);
            }
        });

        ly_btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).SwitchFrag(0);
            }
        });

        return view;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFormActivityListener = (OnLoginFormActivityListener) activity;
    }

    private void performLogin() {
        Log.d("login", "HIT LOGIN");
        hideKeyboard();

        String sUsername = et_usernameLogin.getText().toString().trim();
        String sPassword = et_passwordLogin.getText().toString().trim();

        Call<Logins> call = MainActivity.apiInterface.performLogin(sUsername, sPassword);
        call.enqueue(new Callback<Logins>() {
            @Override
            public void onResponse(Call<Logins> call, Response<Logins> response) {
//                if (response.body().getMessage().equals("sukses")){
//                    callToast("Login berhasil", 1);
//                    SessionManager.login(response.body().getUser(),
//                            response.body().getToken());
//
//                    loginFormActivityListener.performLogin(response.body().getUser().getEmail());
//                }

                if (response.body() != null) {
//                    Log.d("login", "MSG: " + response.body().getMessage());
//                    Log.d("login", "ROLE: " + response.body().getRole());
//                    Log.d("login", "TOKEN: " + response.body().getToken());
                    if (response.isSuccessful()) {
                        if (response.body().getMessage().equals("sukses")) {
                            callToast("Login berhasil", 1);
                            SessionManager.login(response.body().getUser(),
                                    response.body().getToken(),
                                    response.body().getRole());

                            loginFormActivityListener.performLogin(response.body().getUser().getNama());
                        }
                    } else {
                        callToast("Username / Password Salah", 0);
//                    APIError error = ErrorUtils.parseError(response);
//
//                    callToast("Server Error", 0);
//
//                    Log.d("error message", error.message());
                    }
//                    Log.d("login", "SES_TOKEN: " + response.body().getToken());
                } else {
                    callToast("Masalah koneksi, ulangi beberapa saat", 0);
                }
            }

            @Override
            public void onFailure(Call<Logins> call, Throwable t) {
                Log.d("login", t.getMessage());
                if (t instanceof IOException){
                    callToast("Terjadi Kesalahan Koneksi", 0);
                } else {
                    callToast("Username / Password Salah", 0);
                }

                pb_loading.setVisibility(View.GONE);

            }
        });

//        et_usernameLogin.setText("");
//        et_passwordLogin.setText("");
        pb_loading.setVisibility(View.GONE);
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

}
