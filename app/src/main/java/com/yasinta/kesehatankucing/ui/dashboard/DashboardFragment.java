package com.yasinta.kesehatankucing.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.yasinta.kesehatankucing.R;
import com.yasinta.kesehatankucing.activity.MainActivity;
import com.yasinta.kesehatankucing.databinding.ActivityMainBinding;

public class DashboardFragment extends Fragment implements View.OnClickListener{

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        CardView cv_btnTes = root.findViewById(R.id.cv_btnTes);
        CardView cv_btnJenisPenyakit = root.findViewById(R.id.cv_btnJenisPenyakit);
        CardView cv_btnBooking = root.findViewById(R.id.cv_btnBooking);
        CardView cv_btnChat = root.findViewById(R.id.cv_btnChat);
        CardView cv_btnHistori = root.findViewById(R.id.cv_btnHistori);
        CardView cv_btnTentang = root.findViewById(R.id.cv_btnTentang);

        cv_btnTes.setOnClickListener(this);
        cv_btnJenisPenyakit.setOnClickListener(this);
        cv_btnBooking.setOnClickListener(this);
        cv_btnChat.setOnClickListener(this);
        cv_btnHistori.setOnClickListener(this);
        cv_btnTentang.setOnClickListener(this);

        return root;
    }


    @Override
    public void onClick(View view) {
//        assert getFragmentManager() != null;
//        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.cv_btnTes:
//                ft.replace(R.id.nav_host_fragment_content_main, new AnakFragment(), "frDataAnak");
//                ft.commit();
                ((MainActivity)getActivity()).SwitchFrag(1);

                break;
            case R.id.cv_btnJenisPenyakit:
//                ft.replace(R.id.nav_host_fragment_content_main, new TumbangFragment(), "frTumbang");
//                ft.commit();
                ((MainActivity)getActivity()).SwitchFrag(2);
                break;
            case R.id.cv_btnBooking:
//                ft.replace(R.id.nav_host_fragment_content_main, new VaksinFragment(), "frVaksin");
//                ft.commit();
                ((MainActivity)getActivity()).SwitchFrag(3);
                break;
            case R.id.cv_btnChat:
//                ft.replace(R.id.nav_host_fragment_content_main, new MpasiFragment(), "frMpasi");
//                ft.commit();
                ((MainActivity)getActivity()).SwitchFrag(4);
                break;
            case R.id.cv_btnHistori:
//                ft.replace(R.id.nav_host_fragment_content_main, new JadwalFragment(), "frJadwal");
//                ft.commit();
                ((MainActivity)getActivity()).SwitchFrag(5);
                break;
            case R.id.cv_btnTentang:
//                ft.replace(R.id.nav_host_fragment_content_main, new TentangFragment(), "frTentang");
//                ft.commit();
                ((MainActivity)getActivity()).SwitchFrag(6);
                break;
        }
    }
}