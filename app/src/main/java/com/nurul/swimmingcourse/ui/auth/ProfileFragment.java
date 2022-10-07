package com.nurul.swimmingcourse.ui.auth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.utils.ApiClient;
import com.nurul.swimmingcourse.utils.SessionManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        CardView cv_btnPerformLogout = root.findViewById(R.id.cv_btnPerformLogout);
        CardView cv_btnUbahProfile = root.findViewById(R.id.cv_btnUbahProfile);
        CircleImageView icv_avatarProfile = root.findViewById(R.id.icv_avatarProfile);
        TextView tv_roleProfile = root.findViewById(R.id.tv_roleProfile);

        Log.d("profil", ApiClient.IMAGE_URL + SessionManager.getUserData().getFoto());

        tv_roleProfile.setText(SessionManager.getRole());
//        if (SessionManager.getRole().equals("pemilik")) {
//            Glide.with(getActivity()).load(ApiClient.IMAGE_URL + SessionManager.getUserData().getFoto())
//                    .error(R.drawable.ic_avatar).centerCrop().into(icv_avatarProfile);
//        }
        Glide.with(getActivity()).load(ApiClient.IMAGE_URL + SessionManager.getUserData().getFoto())
                .error(R.drawable.ic_avatar).centerCrop().into(icv_avatarProfile);

        Log.d("fotopp", "onCreateView: " + SessionManager.getUserData().getFoto());

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

        LinearLayout ly_profileSiswa = root.findViewById(R.id.c1_profile); //siswa
        LinearLayout ly_profilePelatih = root.findViewById(R.id.c2_profile); //pelatih

        if (SessionManager.getRole().equals("siswa")) {
            ly_profileSiswa.setVisibility(View.VISIBLE);
            ly_profilePelatih.setVisibility(View.INVISIBLE);

            tv_namaSiswaProfil.setText(": " + SessionManager.getUserData().getNama());
            tv_tglLahirSiswaProfil.setText(": " + SessionManager.getUserData().getTanggal_lahir());
            tv_tempatLahirSiswaProfil.setText(": " + SessionManager.getUserData().getTempat_lahir());
            tv_jkSiswaProfil.setText(": " + SessionManager.getUserData().getJenis_kelamin());
            tv_namaOrtuSiswaProfil.setText(": " + SessionManager.getUserData().getNama_ortu());
            tv_alamatSiswaProfil.setText(": " + SessionManager.getUserData().getAlamat());
            tv_noTelpSiswaProfil.setText(": " + SessionManager.getUserData().getNo_telp());
            tv_usernameSiswaProfil.setText(": " + SessionManager.getUserData().getUsername());
        } else {
            ly_profileSiswa.setVisibility(View.INVISIBLE);
            ly_profilePelatih.setVisibility(View.VISIBLE);
            cv_btnUbahProfile.setVisibility(View.INVISIBLE);

            tv_namaPelatihProfil.setText(": " + SessionManager.getUserData().getNama());
            tv_alamatPelatihProfil.setText(": " + SessionManager.getUserData().getAlamat());
            tv_noTelpPelatihProfil.setText(": " + SessionManager.getUserData().getNo_telp());
            tv_usernamePelatihProfil.setText(": " + SessionManager.getUserData().getUsername());
        }

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
                ((MainActivity) getActivity()).SwitchFrag(10);
            }
        });
        return root;
    }

}