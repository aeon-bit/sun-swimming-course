package com.yasinta.kesehatankucing.ui.auth;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.yasinta.kesehatankucing.R;
import com.yasinta.kesehatankucing.activity.MainActivity;
import com.yasinta.kesehatankucing.model.Logins;
import com.yasinta.kesehatankucing.model.Users;
import com.yasinta.kesehatankucing.utils.APIError;
import com.yasinta.kesehatankucing.utils.ErrorUtils;
import com.yasinta.kesehatankucing.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText et_emailLogin, et_passwordLogin;
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
        et_emailLogin = view.findViewById(R.id.et_emailLogin);
        et_passwordLogin = view.findViewById(R.id.et_passwordLogin);

        CardView cv_loginPerformLogin = view.findViewById(R.id.cv_loginPerformLogin);
        TextView tv_btnRegisterNow = view.findViewById(R.id.tv_btnRegisterNow);

        cv_loginPerformLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_emailLogin.getText().toString().isEmpty()) {
                    et_emailLogin.setError("Masukkan email");
                } else if (et_passwordLogin.getText().toString().isEmpty()) {
                    et_passwordLogin.setError("Masukkan password");
                } else if (!isValidEmail(et_emailLogin.getText().toString())) {
                    et_emailLogin.setError("Format Email salah");
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
        hideKeyboard();

        String sEmail = et_emailLogin.getText().toString().trim();
        String sPassword = et_passwordLogin.getText().toString().trim();

        Call<Logins> call = MainActivity.apiInterface.performLogin(sEmail, sPassword);
        call.enqueue(new Callback<Logins>() {
            @Override
            public void onResponse(Call<Logins> call, Response<Logins> response) {
//                Log.d("login", "onResponse: " + response.body().getUser().toString());
//                if (response.body().getMessage().equals("sukses")){
//                    callToast("Login berhasil", 1);
//                    SessionManager.login(response.body().getUser(),
//                            response.body().getToken());
//
//                    loginFormActivityListener.performLogin(response.body().getUser().getEmail());
//                }

                if (response.isSuccessful()){
                    if (response.body().getMessage().equals("sukses")){
                        callToast("Login berhasil", 1);
                        SessionManager.login(response.body().getUser(),
                                response.body().getToken());

                        loginFormActivityListener.performLogin(response.body().getUser().getEmail());
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);

                    callToast("Server Error", 0);

                    Log.d("error message", error.message());
                }
            }

            @Override
            public void onFailure(Call<Logins> call, Throwable t) {

                callToast("Terjadi Kesalahan Koneksi: " + t.getMessage(), 0);

                pb_loading.setVisibility(View.GONE);

            }
        });

        et_emailLogin.setText("");
        et_passwordLogin.setText("");
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
        View view = toast.getView();
        view.setPadding(42, 12, 42, 12);
        if (i == 1) {
            view.setBackgroundResource(R.drawable.xmlbg_toast_success);
        } else {
            view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
        }
        TextView textView = view.findViewById(android.R.id.message);
        textView.setTextColor(Color.WHITE);
        toast.show();
    }

}
