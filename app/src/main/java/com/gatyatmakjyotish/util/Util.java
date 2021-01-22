package com.gatyatmakjyotish.util;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gatyatmakjyotish.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class Util {

    public static AlertDialog dialog;

    public static void setupToolbar(AppCompatActivity appCompatActivity, Toolbar toolbar, String title) {
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
// appCompatActivity. getSupportActionBar().setLogo(R.drawable.tatasky);
        appCompatActivity.getSupportActionBar().setDisplayUseLogoEnabled(true);
        Drawable drawable = ContextCompat.getDrawable(appCompatActivity, R.drawable.ic_more_vert_white_24dp);
        toolbar.setOverflowIcon(drawable);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(title);
// toolbar.setNavigationIcon();
    }

    public static void setupToolbarWithoutBack(AppCompatActivity appCompatActivity, Toolbar toolbar, String title) {
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
// appCompatActivity. getSupportActionBar().setLogo(R.drawable.tatasky);
        appCompatActivity.getSupportActionBar().setDisplayUseLogoEnabled(true);
        Drawable drawable = ContextCompat.getDrawable(appCompatActivity, R.drawable.ic_more_vert_white_24dp);
        toolbar.setOverflowIcon(drawable);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(title);
// toolbar.setNavigationIcon();
    }

    public static TimePickerDialog setUpTimePicker(final EditText editText, Context context) {


        final Calendar newCalendar = Calendar.getInstance();
        int mHour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = newCalendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String AM_PM = " AM";
                        String mm_precede = "";
                        if (hourOfDay >= 12) {
                            AM_PM = " PM";
                            if (hourOfDay >=13 && hourOfDay < 24) {
                                hourOfDay -= 12;
                            }
                            else {
                                hourOfDay = 12;
                            }
                        } else if (hourOfDay == 0) {
                            hourOfDay = 12;
                        }
                        if (minute < 10) {
                            mm_precede = "0";
                        }
                        editText.setText("" + hourOfDay + ":" + mm_precede + minute + AM_PM);
//                        String status = "AM";
//                        if(hourOfDay > 11){
//                            status="PM";
//                        }
//                        int hour_of_12_hour_format;
//
//                        if(hourOfDay > 11){
//
//                            // If the hour is greater than or equal to 12
//                            // Then we subtract 12 from the hour to make it 12 hour format time
//                            hour_of_12_hour_format = hourOfDay - 12;
//                        }
//                        else {
//                            hour_of_12_hour_format = hourOfDay;
//                        }
//                        editText.setText(String.format("%02d:%02d", hour_of_12_hour_format, minute) +" "+status );
                    }
                }, mHour, mMinute, false);
        return timePickerDialog;
    }



    public static TimePickerDialog setUpTimePickerFormat(final EditText editText, Context context) {
        final Calendar newCalendar = Calendar.getInstance();
        int mHour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = newCalendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {


                        editText.setText("" + hourOfDay + ":" + minute + ":" +"00");


//                        String status = "AM";
//                        if(hourOfDay > 11){
//                            status="PM";
//                        }
//                        int hour_of_12_hour_format;
//
//                        if(hourOfDay > 11){
//
//                            // If the hour is greater than or equal to 12
//                            // Then we subtract 12 from the hour to make it 12 hour format time
//                            hour_of_12_hour_format = hourOfDay - 12;
//                        }
//                        else {
//                            hour_of_12_hour_format = hourOfDay;
//                        }
//                        editText.setText(String.format("%02d:%02d", hour_of_12_hour_format, minute) +" "+status );
                    }
                }, mHour, mMinute, false);
        return timePickerDialog;
    }



    public static DatePickerDialog setUpDatePicker(final EditText editText, Context context) {
        final Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                newCalendar.set(year, month, dayOfMonth);
                editText.setText(dateFormatter.format(newCalendar.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog;

    }



    public static void setLinearLayoutManagerNestedScroll(Context context, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
// addAnimation(context, recyclerView);

    }

    public static void showProgress(ProgressDialog progressDialog, String message) {
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public static void dismissProgress(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public static Boolean checkDateSelection(int minAgeSelection, String firstStrDate, String secondStrDate, String dateFormat){

        Date firstDate = getStrToDateFormat(firstStrDate,dateFormat);
        Date secondDate = getStrToDateFormat(secondStrDate,dateFormat);
        System.out.println("GetDateDifference " +getDiffYears(firstDate,secondDate));
        if(getDiffYears(firstDate,secondDate) < minAgeSelection){
            return true;
        }

        return false;
    }

    public static Date getStrToDateFormat(String dateStr, String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static void showDialog(Context context, String title, String message, boolean isTrue, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        assert title != null;
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if (isTrue) {
            alertDialog.setIcon(R.drawable.ic_check_circle_black_24dp);
        } else {
            alertDialog.setIcon(R.drawable.ic_cross_24dp);
        }
        alertDialog.setPositiveButton("OK", onClickListener);
        dialog = alertDialog.create();
        dialog.show();
    }

    public static void showDialogButton(Context context, String title, String message, boolean isTrue, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        assert title != null;
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if (isTrue) {
            alertDialog.setIcon(R.drawable.ic_check_circle_black_24dp);
        } else {
            alertDialog.setIcon(R.drawable.ic_cross_24dp);
        }
        alertDialog.setPositiveButton("OK", onClickListener);
        alertDialog.setNegativeButton("Cancel", onClickListener);
        dialog = alertDialog.create();
        dialog.show();
    }

}
