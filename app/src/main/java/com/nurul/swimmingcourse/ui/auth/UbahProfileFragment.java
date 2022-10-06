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

public class UbahProfileFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ubah_profile, container, false);

        CardView cv_btnPerformLogout = root.findViewById(R.id.cv_btnPerformLogout);
        CardView cv_btnUbahProfile = root.findViewById(R.id.cv_btnUbahProfile);

//        siswa
        TextView tv_namaSiswaProfil = root.findViewById(R.id.tv_namaSiswaProfil);
        TextView tv_tglLahirSiswaProfil = root.findViewById(R.id.tv_tglLahirSiswaProfil);
        TextView tv_tempatLahirSiswaProfil = root.findViewById(R.id.tv_tempatLahirSiswaProfil);
        TextView tv_jkSiswaProfil = root.findViewById(R.id.tv_jkSiswaProfil);
        TextView tv_namaOrtuSiswaProfil = root.findViewById(R.id.tv_namaOrtuSiswaProfil);
        TextView tv_alamatSiswaProfil = root.findViewById(R.id.tv_alamatSiswaProfil);
        TextView tv_noTelpSiswaProfil = root.findViewById(R.id.tv_noTelpSiswaProfil);
        TextView tv_usernameSiswaProfil = root.findViewById(R.id.tv_usernameSiswaProfil);

//        pelatih
        TextView tv_namaPelatihProfil = root.findViewById(R.id.tv_namaPelatihProfil);
        TextView tv_alamatPelatihProfil = root.findViewById(R.id.tv_alamatPelatihProfil);
        TextView tv_noTelpPelatihProfil = root.findViewById(R.id.tv_noHpPelatihProfil);
        TextView tv_usernamePelatihProfil = root.findViewById(R.id.tv_usernamePelatihProfil);


        tv_namaSiswaProfil.setText(": " + SessionManager.getUserData().getNama());
        tv_tglLahirSiswaProfil.setText(": " + SessionManager.getUserData().getTanggal_lahir());
        tv_tempatLahirSiswaProfil.setText(": " + SessionManager.getUserData().getTempat_lahir());
        tv_jkSiswaProfil.setText(": " + SessionManager.getUserData().getJenis_kelamin());
        tv_namaOrtuSiswaProfil.setText(": " + SessionManager.getUserData().getNama_ortu());
        tv_alamatSiswaProfil.setText(": " + SessionManager.getUserData().getAlamat());
        tv_noTelpSiswaProfil.setText(": " + SessionManager.getUserData().getNo_telp());
        tv_usernameSiswaProfil.setText(": " + SessionManager.getUserData().getUsername());

        tv_namaPelatihProfil.setText(": " + SessionManager.getUserData().getNama());
        tv_alamatPelatihProfil.setText(": " + SessionManager.getUserData().getAlamat());
        tv_noTelpPelatihProfil.setText(": " + SessionManager.getUserData().getNo_telp());
        tv_usernamePelatihProfil.setText(": " + SessionManager.getUserData().getUsername());

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

        cv_btnUbahProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return root;
    }

}