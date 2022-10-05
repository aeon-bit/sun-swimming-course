package com.nurul.swimmingcourse.utils;


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
        com.nurul.swimmingcourse.utils.SessionManager.init(this);
    }
}
