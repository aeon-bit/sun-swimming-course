package com.nurul.swimmingcourse.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.ui.teskesehatan.TesKesehatanFragment;
import com.nurul.swimmingcourse.ui.auth.LoginFragment;
import com.nurul.swimmingcourse.ui.auth.ProfileFragment;
import com.nurul.swimmingcourse.ui.auth.RegisterFragment;
import com.nurul.swimmingcourse.ui.dashboard.DashboardFragment;
import com.nurul.swimmingcourse.ui.history.HistoryFragment;
import com.nurul.swimmingcourse.ui.chat.ChatFragment;
import com.nurul.swimmingcourse.ui.tentang.TentangFragment;
import com.nurul.swimmingcourse.ui.jenispenyakit.JenisPenyakitFragment;
import com.nurul.swimmingcourse.ui.bookingjadwal.BookingJadwalFragment;
import com.nurul.swimmingcourse.utils.ApiClient;
import com.nurul.swimmingcourse.utils.ApiInterface;
import com.nurul.swimmingcourse.utils.PrefConfig;
import com.nurul.swimmingcourse.utils.SessionManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnLoginFormActivityListener {


    private AppBarConfiguration mAppBarConfiguration;
//    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    public static PrefConfig prefConfig;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

//        float radius = getResources().getDimension(R.dimen.toolbar_corner); //32dp
//        MaterialToolbar materialToolbar = findViewById(R.id.toolbar);
//
//        MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable)materialToolbar.getBackground();
//        toolbar.setShapeAppearanceModel(toolbar.getShapeAppearanceModel()
//                .toBuilder()
//                .setAllCorners(CornerFamily.ROUNDED,radius)
//                .build());

        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);
        TextView tv_namaPenggunaHeader = v.findViewById(R.id.tv_namaPenggunaHeader);
        CircleImageView iv_profilePicHeader = v.findViewById(R.id.iv_profilePicHeader);
        tv_namaPenggunaHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionManager.isLogin()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                            new ProfileFragment()).commit();
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    callWarningToast("Silakan login dahulu!");
                }
            }
        });
        iv_profilePicHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new ProfileFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        if (SessionManager.isLogin()) {
            tv_namaPenggunaHeader.setText(SessionManager.getUserData().getNama());
            navigationView.getMenu().findItem(R.id.nav_auth).setTitle("Logout");
        } else {
            navigationView.getMenu().findItem(R.id.nav_auth).setTitle("Login");
            tv_namaPenggunaHeader.setText("______");
        }

        if (savedInstanceState == null) {
            if (SessionManager.isLogin()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new DashboardFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_dashboard);
                getSupportActionBar().setTitle("Dashboard");
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new LoginFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_auth);
                getSupportActionBar().setTitle("Login");
            }
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_dashboard,
//                R.id.nav_dataanak,
//                R.id.nav_tumbang,
//                R.id.nav_vaksin,
//                R.id.nav_mpasi,
//                R.id.nav_jadwal,
//                R.id.nav_tentang
//        )
//                .setOpenableLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                new RegisterFragment()).addToBackStack(null).commit();
    }

    @Override
    public void performLogin(String username) {
        //prefConfig.writeName(Name);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragmentContainer, new WelcomeFragment()).commit();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void logoutPerform() {
//        prefConfig.writeLoginStatus(false);
//        prefConfig.writeName("User");
//        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragmentContainer, new LoginFragment()).commit();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (SessionManager.isLogin()) {

            switch (item.getItemId()) {
                case R.id.nav_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                            new DashboardFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_dashboard);
                    getSupportActionBar().setTitle(R.string.menu_dashboard);
                    break;
                case R.id.nav_tes_kesehatan:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                            new TesKesehatanFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_tes_kesehatan);
                    getSupportActionBar().setTitle(R.string.menu_tes_kesehatan);
                    break;
                case R.id.nav_jenis_penyakit:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                            new JenisPenyakitFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_jenis_penyakit);
                    getSupportActionBar().setTitle(R.string.menu_jenis_penyakit);
                    break;
                case R.id.nav_booking_jadwal:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                            new BookingJadwalFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_booking_jadwal);
                    getSupportActionBar().setTitle(R.string.menu_booking_jadwal);
                    break;
                case R.id.nav_chat:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                            new ChatFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_chat);
                    getSupportActionBar().setTitle(R.string.menu_chat);
                    break;
                case R.id.nav_history:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                            new HistoryFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_history);
                    getSupportActionBar().setTitle(R.string.menu_history);
                    break;
                case R.id.nav_tentang:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                            new TentangFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_tentang);
                    getSupportActionBar().setTitle(R.string.menu_tentang);
                    break;
                case R.id.nav_auth:
                    if (SessionManager.isLogin()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                                new ProfileFragment()).commit();
                        navigationView.setCheckedItem(R.id.nav_auth);
                        getSupportActionBar().setTitle("Profil Pengguna");
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                                new LoginFragment()).commit();
                        navigationView.setCheckedItem(R.id.nav_auth);
                        getSupportActionBar().setTitle("Login");
                    }
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (item.getItemId() == R.id.nav_auth) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new LoginFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_auth);
                getSupportActionBar().setTitle("Login");
            } else {
                callWarningToast("Silakan login dahulu!");
            }
        }
        return false;
    }

    private void callWarningToast(String s){
        Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            View view = toast.getView();
            view.setPadding(42, 16, 42, 16);
            view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
            TextView textView = view.findViewById(android.R.id.message);
            textView.setTextColor(Color.WHITE);
        }
        toast.show();
    }

    public void setActionBarTitle(int title){
        getSupportActionBar().setTitle(title);
    }

    public void SwitchFrag(int frag) {
        switch (frag) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new DashboardFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_dashboard);
                getSupportActionBar().setTitle(R.string.menu_dashboard);
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new TesKesehatanFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_tes_kesehatan);
                getSupportActionBar().setTitle(R.string.menu_tes_kesehatan);
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new JenisPenyakitFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_jenis_penyakit);
                getSupportActionBar().setTitle(R.string.menu_jenis_penyakit);
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new BookingJadwalFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_booking_jadwal);
                getSupportActionBar().setTitle(R.string.menu_booking_jadwal);
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new ChatFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_chat);
                getSupportActionBar().setTitle(R.string.menu_chat);
                break;
            case 5:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new HistoryFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_history);
                getSupportActionBar().setTitle(R.string.menu_history);
                break;
            case 6:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new TentangFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_tentang);
                getSupportActionBar().setTitle(R.string.menu_tentang);
                break;
            case 7:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new LoginFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_auth);
                getSupportActionBar().setTitle("Login");
                break;
            case 8:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,
                        new RegisterFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_auth);
                getSupportActionBar().setTitle("Register");
                break;
        }
    }
}