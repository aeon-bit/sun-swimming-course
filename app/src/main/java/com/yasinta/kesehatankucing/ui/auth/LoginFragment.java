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

    public  interface OnLoginFormActivityListener{
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
                if (et_emailLogin.getText().toString().isEmpty()){
                    et_emailLogin.setError("Masukkan email");
                } else if (et_passwordLogin.getText().toString().isEmpty()){
                    et_passwordLogin.setError("Masukkan password");
                } else if (!isValidEmail(et_emailLogin.getText().toString())){
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
                ((MainActivity)getActivity()).SwitchFrag(8);
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

    private void performLogin(){
        String sEmail = et_emailLogin.getText().toString().trim();
        String sPassword = et_passwordLogin.getText().toString().trim();

        Call<Logins> call = MainActivity.apiInterface.performLogin(sEmail, sPassword);
        call.enqueue(new Callback<Logins>() {
            @Override
            public void onResponse(Call<Logins> call, Response<Logins> response) {
                Log.d("respon", "onResponse: " + response.body().toString());
                if (response.body().getResponse().equals("ok")){
                    SessionManager.login(response.body().getUsers(),
                            response.body().getToken());

                    //SessionManager.;
//                    Toast.makeText(getActivity(), "Login BERHASIL", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getActivity(), "Login Berhasil", Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setPadding(42, 16, 42, 16);
                    view.setBackgroundResource(R.drawable.xmlbg_toast_success);
                    TextView textView = view.findViewById(android.R.id.message);
                    textView.setTextColor(Color.WHITE);
                    toast.show();

//                    LoginRegisterActivity.prefConfig.writeLoginStatus(true);
                    loginFormActivityListener.performLogin(response.body().getUsers().getEmail()); //restart actv
                }
                else if (response.body().getResponse().equals("failed")){
//                    LoginRegisterActivity.prefConfig.displayToast("LoginFailed.. Try again");
//                    Toast.makeText(getActivity(), "Login GAGAL", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getActivity(), "Email atau Password Salah!", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setPadding(42, 16, 42, 16);
                    view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
                    TextView textView = view.findViewById(android.R.id.message);
                    textView.setTextColor(Color.WHITE);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Logins> call, Throwable t) {
                Log.d("nores", t.getMessage());
//                Toast.makeText(getActivity(), "ERROR : " +t.getMessage(), Toast.LENGTH_LONG);
                Toast toast = Toast.makeText(getActivity(), "Terjadi Kesalahan !", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setPadding(42, 16, 42, 16);
                view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
                TextView textView = view.findViewById(android.R.id.message);
                textView.setTextColor(Color.WHITE);
                toast.show();

                pb_loading.setVisibility(View.GONE);

            }
        });

        et_emailLogin.setText("");
        et_passwordLogin.setText("");
        pb_loading.setVisibility(View.GONE);
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
