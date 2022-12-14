package com.nurul.swimmingcourse.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.nurul.swimmingcourse.model.Users;


public class SessionManager
{
    private static final String USER_DATA = "UserData",
                                USER_TOKEN = "USER_TOKEN",
                                USER_ROLE = "USER_ROLE",
                                IS_LOGIN = "islogin";

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public static void init(Context c)
    {
        if (sp == null || editor == null)
        {
            sp = c.getSharedPreferences("smartUser", 0);
            editor = sp.edit();
        }
    }

    public static void login(Users s)
    {
        editor.putString(USER_DATA, Gxon.toJsonObject(s)).commit();
        editor.putBoolean(IS_LOGIN, true).commit();
    }

    public static void login(Users s, String token, String role)
    {
        Log.d("sessi", "TOKEN: " + token);
        Log.d("sessi", "ROLE: " + role);
        editor.putString(USER_DATA, Gxon.toJsonObject(s)).commit();
        editor.putBoolean(IS_LOGIN, true).commit();
        editor.putString(USER_TOKEN, token).commit();
        editor.putString(USER_ROLE, role).commit();
    }

    public static boolean isLogin()
    {
        return sp.getBoolean(IS_LOGIN, false);
    }

    public static void logout()
    {
        editor.putString(USER_DATA, "").commit();
        editor.putBoolean(IS_LOGIN, false).commit();
        editor.putString(USER_TOKEN, "").commit();
        editor.putString(USER_ROLE, "").commit();
    }

    // ------------  -_-_-_-  --------------

//    public static String getIdUser()
//    {
//        return getUserData().getId();
//    }
//
//    public static String getEmail()
//    {
//        return getUserData().getEmail();
//    }
//
//    public static String getNamaUser()
//    {
//        return getUserData().getNama();
//    }
//
//    public static String getNoHp()
//    {
//        return getUserData().getNo_hp();
//    }
//
//    public static String getJk()
//    {
//        return getUserData().getJk();
//    }
//
//    public static String getPass()
//    {
//        return getUserData().getPass();
//    }
//
    public static String getToken()
    {
        return sp.getString(USER_TOKEN, "");
    }

    public static String getRole()
    {
        return sp.getString(USER_ROLE, "");
    }

    public static Users getUserData()
    {
        return Gxon.fromJsonObject(sp.getString(USER_DATA, ""), Users.class);
    }

    public static void setUserData(Users s)
    {
        editor.putString(USER_DATA, Gxon.toJsonObject(s)).commit();
    }

    public static void setUserData(Users s, String token, String role)
    {
        editor.putString(USER_DATA, Gxon.toJsonObject(s)).commit();
        editor.putString(USER_TOKEN, token).commit();
        editor.putString(USER_TOKEN, role).commit();
    }
}