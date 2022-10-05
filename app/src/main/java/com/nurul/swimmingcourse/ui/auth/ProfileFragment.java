package com.nurul.swimmingcourse.ui.auth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.utils.SessionManager;

public class ProfileFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        CardView cv_btnPerformLogout = root.findViewById(R.id.cv_btnPerformLogout);

        TextView tv_namaPengguna = root.findViewById(R.id.tv_namaPengguna);
        TextView tv_alamatPengguna = root.findViewById(R.id.tv_alamatPengguna);
        TextView tv_noHpPengguna = root.findViewById(R.id.tv_noHpPengguna);
        TextView tv_emailPengguna = root.findViewById(R.id.tv_emailPengguna);
        TextView tv_userPengguna = root.findViewById(R.id.tv_userPengguna);
        TextView tv_passPengguna = root.findViewById(R.id.tv_passPengguna);

        tv_namaPengguna.setText(": " + SessionManager.getUserData().getNama());
        tv_alamatPengguna.setText(": " + SessionManager.getUserData().getAlamat());
//        tv_noHpPengguna.setText(": " + SessionManager.getUserData().getNo_hp());
//        tv_emailPengguna.setText(": " + SessionManager.getUserData().getEmail());
        tv_userPengguna.setText(": " + SessionManager.getUserData().getUsername());
//        tv_passPengguna.setText(": " + SessionManager.getUserData().getpas);

        cv_btnPerformLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.logout();

                Toast toast = Toast.makeText(getActivity(), "Logout Berhasil", Toast.LENGTH_SHORT);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    View view = toast.getView();
                    view.setPadding(42, 12, 42, 12);
                    view.setBackgroundResource(R.drawable.xmlbg_toast_success);
                    TextView textView = view.findViewById(android.R.id.message);
                    textView.setTextColor(Color.WHITE);
                }
                toast.show();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

}