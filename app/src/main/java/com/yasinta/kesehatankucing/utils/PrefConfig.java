package com.yasinta.kesehatankucing.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.yasinta.kesehatankucing.R;

public class PrefConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig(Context context){
            this.context = context;
            sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);

    }

    //write n read login sttus
    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.commit();
    }

    public boolean readLoginStatus(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    /*public void writeEmail(String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_email), email);
        editor.commit();
    }

    public String readEmail(){

        return sharedPreferences.getString(context.getString(R.string.pref_email), "User");
    }*/
    public void writeName(String Name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_nama_user), Name);
        editor.commit();
    }

    public String readName(){

        return sharedPreferences.getString(context.getString(R.string.pref_nama_user), "User");
    }

    public void displayToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
