package com.gatyatmakjyotish.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gatyatmakjyotish.ModelClass.days_pkg.DaysPlanetModel;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.DateAdapter;
import com.gatyatmakjyotish.constants.Api;
import com.gatyatmakjyotish.constants.Constants;
import com.gatyatmakjyotish.pojo.DateCategory;
import com.gatyatmakjyotish.ui.activity.SubscriptionActivity;
import com.gatyatmakjyotish.util.SaveTextSize;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.gatyatmakjyotish.constants.Api.DATE_API;
import static com.gatyatmakjyotish.constants.Constants.ID;
import static com.gatyatmakjyotish.constants.Constants.LOGIN_PREF;
import static com.gatyatmakjyotish.ui.fragment.Publish.mypreference;

public class Date extends Fragment {
    SharedPreferences sharedPreferences, languagePreference, sharedPrefsLogin;
    LinearLayout layoutNoData;
    private TextView tv_feeling,horoscope,tv_date,tv_no_data;
    private ProgressDialog progressDialog;
    private CalendarView calendarView;
    private Context context;
    private Spinner day,month,year;
    private Button search,cancel,previous,next;
    private DatePicker picker;
    private String dateForResult = "";
    private int incDate = 0;
    private int counter = 0;
    private RecyclerView recyclerView;
    List<DateCategory> dateCategoryList = new ArrayList<>();
    int is_paid=0;
    SharedPreferences.Editor editor;
    private String dateToSearch="";

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.date, container, false);
        this.context = getActivity();
        calendarView = v.findViewById(R.id.calendarView);
        layoutNoData = v.findViewById(R.id.layout_no_data);
        tv_feeling = v.findViewById(R.id.tv_feeling);
        horoscope = v.findViewById(R.id.tv_horoscope);
        //picker = v.findViewById(R.id.datePicker);
        tv_date = v.findViewById(R.id.date_textView);
        tv_no_data = v.findViewById(R.id.tv_no_data);
        previous = v.findViewById(R.id.previous);
        next = v.findViewById(R.id.next);
        recyclerView = v.findViewById(R.id.recycler_view);
        //search = v.findViewById(R.id.search_button);
        //cancel = v.findViewById(R.id.cancel_button);
        sharedPrefsLogin = getActivity().getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);

        setPreference();
        currentDate();
        searchDate(dateForResult);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_paid !=  sharedPreferences.getInt("is_paid", 0)){
                    setUpDatePickerView(tv_date, getActivity()).show();
                }else
                    Toast.makeText(context, "Please Buy a subscription First", Toast.LENGTH_SHORT).show();

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter == -1 && is_paid ==  sharedPreferences.getInt("is_paid", 0)){
                    Intent intent=new Intent(getActivity(), SubscriptionActivity.class);
                    startActivity(intent);
                }
                else{

                    getDate(dateForResult, incDate - 1);
                    searchDate(dateForResult);
                    counter--;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter == 1 && is_paid ==  sharedPreferences.getInt("is_paid", 0)){
                    Intent intent=new Intent(getActivity(), SubscriptionActivity.class);
                    startActivity(intent);
                }
                else{

                    getDate(dateForResult, incDate + 1);
                    searchDate(dateForResult);
                    counter++;
                }

            }
        });


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    tv_date.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
                }
            });
        }*/


        /*cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tv_date.setText(sdf.format(myCalendar.getTime()));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar.set(Calendar.YEAR, picker.getYear());
                myCalendar.set(Calendar.MONTH, picker.getMonth());
                myCalendar.set(Calendar.DAY_OF_MONTH, picker.getDayOfMonth());
                searchDate();
            }
        });*/



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                //calendarView.setDate(Calendar.DATE);
                calendarView.setDate(myCalendar.get(Calendar.DATE));
                //getHoroscopeWithDate(i + String.format("-%02d-", (i1 + 1)) + String.format("%02d", i2));
                System.out.println("date format"+i + String.format("-%02d-", (i1 + 1)) + String.format("%02d", i2));
            }
        });
        horoscope.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(context).getTextSize());
        return v;

    }

    private void setPreference(){
        sharedPreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void setIsPaid(int is_paid){
        setPreference();
        editor.putInt("is_paid",is_paid);
        editor.commit();
    }

    public DatePickerDialog setUpDatePickerView(final TextView textView, Context context) {
        final Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                newCalendar.set(year, month, dayOfMonth);

                dateForResult=dateFormatter.format(newCalendar.getTime());

                textView.setText(dateFormatter.format(newCalendar.getTime()));
                searchDate(textView.getText().toString());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog;
    }

    private void currentDate(){
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat, Locale.US);
        dateForResult = sdf1.format(myCalendar.getTime());
        tv_date.setText(dateForResult);
    }

    private void getDate(String oldDate ,int incDate){
        //String dt = "2019-09-06";  // Start date
        String dateString = oldDate;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try{
            c.setTime(sdf.parse(dateString));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        c.add(Calendar.DATE, incDate);  // number of days to add
        dateForResult = sdf.format(c.getTime());  // dt is now the new date
        tv_date.setText(dateForResult);
    }

    private void getHoroscopeWithDate(final String date) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        sharedPreferences = context.getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
        languagePreference = context.getSharedPreferences("language", MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                DATE_API = Api.BASE_URL + "daily-result?user_id=" + sharedPrefsLogin.getString(ID, null)
                        + "&result_date=" + date, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Resultgetdate " +response);
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                try {
                    dateCategoryList.clear();


                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if(!error){
                        DaysPlanetModel model = new Gson().fromJson(response.toString(), DaysPlanetModel.class);
                        layoutNoData.setVisibility(View.GONE);
//                        setIsPaid(jsonObject.getInt("is_paid"));
                        setIsPaid(model.getIsPaid());

                        /*JSONArray  jsonArray = jsonObject.getJSONArray("object");
                        if(languagePreference.getString("language", Constants.Language.ENGLISH.getLanguage()).equals(Constants.Language.
                                ENGLISH.getLanguage())) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                if(!object.isNull("description")){
                                    dateCategoryList.add(new DateCategory(object.getString("feeling"), object.getString("description")));
                                }
                            }
                        }
                        else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                if(!object.isNull("description_hindi")){
                                    dateCategoryList.add(new DateCategory(object.getString("feeling_hindi"), object.getString("description_hindi")));
                                }
                            }
                        }*/

//                        recyclerView.setAdapter(new DateAdapter(dateCategoryList));
                        recyclerView.setAdapter(new DateAdapter(model.getObject()));

                    }
                    else{
                        setIsPaid(1);
                        JSONObject  dataObject = jsonObject.getJSONObject("object");
                        layoutNoData.setVisibility(View.VISIBLE);
                        String no_data_description = "";
                        if(languagePreference.getString("language", Constants.Language.ENGLISH.getLanguage()).equals(Constants.Language.
                                ENGLISH.getLanguage()))
                            no_data_description = dataObject.getString("description");
                        else
                            no_data_description = dataObject.getString("description_hindi");

                        tv_no_data.setText(no_data_description);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
        queue.add(stringRequest);
    }

    private void searchDate(String dateForResult) {
        //String myFormat = "yyyy-MM-dd"; //In which you need put here
        //SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        getHoroscopeWithDate(dateForResult);

        //String myFormat2 = "dd-MM-yyyy"; //In which you need put here
        //SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
        //tv_date.setText(sdf2.format(myCalendar.getTime()));
        //System.out.println("new Date="+sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
