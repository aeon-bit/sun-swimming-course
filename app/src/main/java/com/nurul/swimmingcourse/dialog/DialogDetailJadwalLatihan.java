package com.nurul.swimmingcourse.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.nurul.swimmingcourse.R;

public class DialogDetailJadwalLatihan extends Dialog{
    public DialogDetailJadwalLatihan(@NonNull Context context) {
        super(context);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_detail_jadwal_latihan, null);
        setContentView(view);
    }
}
