package com.yasinta.kesehatankucing.utils;


import android.app.Application;

/**
 * Created by limakali on 3/18/2018.
 */

public class AppQu extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        com.yasinta.kesehatankucing.utils.SessionManager.init(this);
    }
}
