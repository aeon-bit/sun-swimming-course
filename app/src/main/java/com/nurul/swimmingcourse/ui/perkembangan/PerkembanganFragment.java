package com.nurul.swimmingcourse.ui.perkembangan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nurul.swimmingcourse.R;
import com.nurul.swimmingcourse.activity.MainActivity;
import com.nurul.swimmingcourse.model.ResponseBookingJadwals;
import com.nurul.swimmingcourse.utils.SessionManager;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerkembanganFragment extends Fragment {
    DatePickerDialog datePickerDialog;
    TextView tv_dateSelected;
    //date
    int bulan;
    String selectedDate, selectedDateShow, dateToUp;
    Spinner sp_jenisKucing;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perkembangan, container, false);

//        initDatePicker();
//
//        tv_dateSelected = root.findViewById(R.id.tv_dateSelected);
//        ImageView iv_openDatePicker = root.findViewById(R.id.iv_openDatePicker);
//        iv_openDatePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDatePicker();
//            }
//        });
//
//        EditText et_namaKucingBook = root.findViewById(R.id.et_namaKucingBook);
//        sp_jenisKucing = root.findViewById(R.id.sp_jenisKucing);
//        EditText et_usiaKucingBook = root.findViewById(R.id.et_usiaKucingBook);
//        EditText et_namaPemilikBook = root.findViewById(R.id.et_namaPemilikBook);
//        EditText et_noHpBook = root.findViewById(R.id.et_noHpBook);
//        EditText et_alamatBook = root.findViewById(R.id.et_alamatBook);
////        EditText et_hasilDiagnosisBook = root.findViewById(R.id.et_hasilDiagnosisBook);
//        EditText et_tglBook = root.findViewById(R.id.et_tglBook);
//        CardView cv_btnPerformBuatJadwal = root.findViewById(R.id.cv_btnPerformBuatJadwal);
//
//        spinnerJenisKucing();
//
//        cv_btnPerformBuatJadwal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (et_namaKucingBook.getText().toString().isEmpty()) {
//                    et_namaKucingBook.setError("Masukkan nama kucing");
////                } else if (et_jenisKucingKucingBook.getText().toString().isEmpty()) {
////                    et_jenisKucingKucingBook.setError("Masukkan jenis kucing");
////                } else if (et_usiaKucingBook.getText().toString().isEmpty()) {
////                    et_usiaKucingBook.setError("Masukkan usia kucing");
////                } else if (et_namaPemilikBook.getText().toString().isEmpty()) {
////                    et_namaPemilikBook.setError("Masukkan nama pemilik");
////                } else if (et_noHpBook.getText().toString().isEmpty()) {
////                    et_noHpBook.setError("Masukkan no hp");
////                } else if (et_noHpBook.getText().length() < 11) {
////                    et_noHpBook.setError("Format no hp salah");
////                } else if (et_alamatBook.getText().toString().isEmpty()) {
////                    et_alamatBook.setError("Masukkan alamat");
////                } else if (et_hasilDiagnosisBook.getText().toString().isEmpty()) {
////                    et_hasilDiagnosisBook.setError("Masukkan hasil diagnosis");
////                } else if (tv_dateSelected.getText().toString().isEmpty()) {
////                    et_tglBook.setError("Masukkan tanggal booking");
////                } else {
//                }
//
//                if (tv_dateSelected.getText().toString().isEmpty()) {
//                    Toast toast = Toast.makeText(getActivity(), "Masukkan tanggal booking", Toast.LENGTH_LONG);
//                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//                        View view = toast.getView();
//                        view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
//                        TextView textView = view.findViewById(android.R.id.message);
//                        textView.setTextColor(Color.WHITE);
//                    }
//                    toast.show();
//                } else {
////                    performBuatJadwal();
//                    performBuatJadwal(
//                            et_namaKucingBook.getText().toString(), sp_jenisKucing.getSelectedItem().toString()
////                            et_jenisKucingKucingBook.getText().toString()
////                            et_usiaKucingBook.getText().toString(),
////                            et_namaPemilikBook.getText().toString(),
////                            et_noHpBook.getText().toString().trim(),
////                            et_alamatBook.getText().toString(),
////                            et_hasilDiagnosisBook.getText().toString(),
////                            dateToUp
//                    );
//                }
//            }
//        });
        return root;
    }

    private void spinnerJenisKucing() {
        //spinner jam
        ArrayAdapter<CharSequence> adapterJam = ArrayAdapter.createFromResource(getContext(), R.array.hari, android.R.layout.simple_spinner_item);
        adapterJam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_jenisKucing.setAdapter(adapterJam);
        sp_jenisKucing.setOnItemSelectedListener(sp_jenisKucing.getOnItemSelectedListener());
    }

    //        private void performBuatJadwal(String sNamaKucing, String sJenis, String sUsia, String sNamaPemilik, String sNoHp, String sAlamat, String sHasilD, String dateToUp) {
    private void performBuatJadwal(String sNamaKucing, String sJenis) {
//                    Log.d("performup", "nama kucing: " + sNamaKucing);
//                    Log.d("performup", "jenis: " + sJenis);
//                    Log.d("performup", "usia: " + sUsia);
//                    Log.d("performup", "pemilik: " + sNamaPemilik);
//                    Log.d("performup", "nohp: " + sNoHp);
//                    Log.d("performup", "alamat: " + sAlamat);
//                    Log.d("performup", "hasilk: " + sHasilD);
//        Log.d("performup", "date: " + selectedDate);
        Call<ResponseBookingJadwals> call = MainActivity.apiInterface.performBookingJadwal(
                "Bearer " + SessionManager.getToken(),
                selectedDate,
                sNamaKucing,
                sJenis
        );

        Fragment self = this;
        call.enqueue(new Callback<ResponseBookingJadwals>() {
            @Override
            public void onResponse(Call<ResponseBookingJadwals> call, Response<ResponseBookingJadwals> response) {
                if (response.body() != null) {

                    Log.d("respon", "onResponse Booking: " + response.toString());

                    if (response.body().getStatus().equals("success")) {

                        Toast toast = Toast.makeText(getActivity(), "Booking Jadwal Berhasil", Toast.LENGTH_LONG);
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                            View view = toast.getView();
                            view.setBackgroundResource(R.drawable.xmlbg_toast_success);
                            TextView textView = view.findViewById(android.R.id.message);
                            textView.setTextColor(Color.WHITE);
                        }
                        toast.show();

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .remove(self).commit();
                    }
                } else {
                    Log.d("respon", "onResponse Booking: NULL");
                }
            }

            @Override
            public void onFailure(Call<ResponseBookingJadwals> call, Throwable t) {

                Log.d("daftar", "onFaillure: " + t.toString());
                Toast toast = Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_LONG);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    View view = toast.getView();
                    view.setBackgroundResource(R.drawable.xmlbg_toast_warning);
                    TextView textView = view.findViewById(android.R.id.message);
                    textView.setTextColor(Color.WHITE);
                }
                toast.show();
            }
        });
    }

    private void openDatePicker() {
        datePickerDialog.show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                tv_dateSelected.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        selectedDate = year + "-" + month + "-" + day;
//        dateToUp = day + "-" + month + "-" + year;

//        Log.d("selecteddate", "makeDateString: " + dateToUp);

//        requestJamByIdLapToday();
        selectedDateShow = day + " " + getMonthFormat(month) + " " + year;
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "Januari";
        if (month == 2)
            return "Februari";
        if (month == 3)
            return "Maret";
        if (month == 4)
            return "April";
        if (month == 5)
            return "Mei";
        if (month == 6)
            return "Juni";
        if (month == 7)
            return "Juli";
        if (month == 8)
            return "Agustus";
        if (month == 9)
            return "September";
        if (month == 10)
            return "Oktober";
        if (month == 11)
            return "November";
        if (month == 12)
            return "Desember";

        //save mont in int
        bulan = month;
        //default should never happen
        return "Januari";
    }

}