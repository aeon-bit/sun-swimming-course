package com.nurul.swimmingcourse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yasinta.kesehatankucing.R;


public class Splashscreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2500;
    ImageView iv_splashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //GOTO home & open next actv
        iv_splashLogo = findViewById(R.id.iv_splashLogo);

        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.transition);
        iv_splashLogo.startAnimation(myAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Splashscreen.this, MainActivity.class);
                homeIntent.putExtra("backValue", "");
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}