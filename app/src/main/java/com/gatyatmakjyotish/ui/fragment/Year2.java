package com.gatyatmakjyotish.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.YearAdapter;
import com.gatyatmakjyotish.constants.Api;
import com.gatyatmakjyotish.constants.Constants;
import com.gatyatmakjyotish.pojo.ResultCategory;
import com.gatyatmakjyotish.pojo.YearCategory;
import com.gatyatmakjyotish.pojo.YearResult;
import com.gatyatmakjyotish.ui.activity.SubscriptionActivity;
import com.gatyatmakjyotish.util.SaveTextSize;
import com.gatyatmakjyotish.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.gatyatmakjyotish.constants.Constants.ID;
import static com.gatyatmakjyotish.constants.Constants.LOGIN_PREF;
import static com.gatyatmakjyotish.ui.fragment.Publish.mypreference;

public class Year2 extends Fragment implements AdapterView.OnItemSelectedListener {
    TextView textView, textViewNoData;
    List<YearCategory> yearCategoryList = new ArrayList<>();
    Spinner spinner;
    SharedPreferences sharedPreferences, languagePreference;
    SharedPreferences.Editor editor;
    List<ResultCategory> resultCategoryList = new ArrayList<>();
    List<YearResult> yearResultList = new ArrayList<>();

    YearAdapter yearAdapter;
    ProgressDialog progressDialog;
    LinearLayout prevNextLayout, noDataLayout;
    private String user_id = "";
    private String currentDate = "";
    private String currentDate2 = "";
    private int temp_count = 0;
    private Boolean tempFlag = false;
    RecyclerView recyclerViewYear;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.year_fragment, container, false);
        spinner = v.findViewById(R.id.spinner);
        recyclerViewYear = v.findViewById(R.id.recycler_view);
        yearAdapter = new YearAdapter(yearResultList, getActivity());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewYear.setLayoutManager(layoutManager);

        recyclerViewYear.setAdapter(yearAdapter);
        //recyclerViewYear.setAdapter(yearAdapter = new YearAdapter());
        textViewNoData = v.findViewById(R.id.tv_no_data);
        date = v.findViewById(R.id.tv_date);
        feeling = v.findViewById(R.id.tv_feeling);
        description = v.findViewById(R.id.tv_description);
        prevNextLayout = v.findViewById(R.id.layout_prev_next1);
        noDataLayout = v.findViewById(R.id.layout_no_data);
        previous = v.findViewById(R.id.previous);
        next = v.findViewById(R.id.next);
        user_id = getUserID();
        getCurrentDate();
        postnewcomment(getActivity());
        return v;

    }

    private String getUserID() {
        sharedPreferences = getActivity().getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
        return sharedPreferences.getString(ID, "");
    }

    private void postnewcomment(final Context context) {
        //sharedPreferences = context.getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
        languagePreference = context.getSharedPreferences("language", MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, Api.YEAR_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        JSONArray object = jsonObject.getJSONArray("object");
                        yearCategoryList.clear();
                        for (int i = 0; i < object.length(); i++) {
                            JSONObject c = object.getJSONObject(i);
                            int id = c.optInt("id");
                            String title = c.getString("title");
                            String name = c.getString("name");
                            yearCategoryList.add(new YearCategory(id, name, title));
                        }
                        ArrayAdapter<YearCategory> adapter = new ArrayAdapter<YearCategory>(context, android.R.layout.simple_spinner_item,
                                yearCategoryList) {

                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView textView = view.findViewById(android.R.id.text1);
                                textView.setText(yearCategoryList.get(position).getName());
                                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                                textView.setTypeface(boldTypeface);
                                textView.setTextSize(15);

                                return view;
                            }

                            @Override
                            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView textView = view.findViewById(android.R.id.text1);
                                textView.setText(yearCategoryList.get(position).getName());
                                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                                textView.setTypeface(boldTypeface);
                                textView.setTextSize(15);


                                return view;
                            }
                        };
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);


//                        Api.YEAR_RESULT_API = Api.BASE_URL + Api.RESULT + "/" + user_id + "/" + yearCategoryList.get(0).getId();
//                        System.out.println(Api.YEAR_RESULT_API);
//                        add=0;
//                        tempFlag=false;
//                        count=0;
//                        //postnewcom(Api.YEAR_RESULT_API, getActivity());
//                        postnewcom(Api.BASE_URL+"result1" + "/" + user_id + "/" + yearCategoryList.get(0).getId(), getActivity());
//
//

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                Api.YEAR_RESULT_API = Api.BASE_URL + Api.RESULT + "/" + user_id + "/" + yearCategoryList.get(position).getId();
                                System.out.println(Api.YEAR_RESULT_API);
                                add = 0;
                                tempFlag = false;
                                count = 0;
                                //postnewcom(Api.YEAR_RESULT_API, getActivity());
//                                postnewcom(Api.BASE_URL + "result" + "/" + user_id + "/" + yearCategoryList.get(position).getId(), getActivity());
                                postnewcom(Api.BASE_URL + Api.RESULT + "/" + user_id + "/" + yearCategoryList.get(position).getId(), getActivity());


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }

                        });


                    } else {
                        textView.setError(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("response: ", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("language", languagePreference.getString("language", Constants.Language.ENGLISH.getLanguage()).
                        equals(Constants.Language.ENGLISH.getLanguage()) ? Constants.Language.ENGLISH.getLanguage() :
                        Constants.Language.HINDI.getLanguage());
                return params;
            }
        };
        queue.add(sr);
    }

    private void setPreference() {
        sharedPreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void setIsPaid(int is_paid) {
        setPreference();
        editor.putInt("is_paid", is_paid);
        editor.commit();
    }

    private void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd ");
        currentDate = mdformat.format(calendar.getTime());
        System.out.println("current date " + currentDate);
    }


    private void postnewcom(String url, final Context context) {
        Util.showProgress(progressDialog = new ProgressDialog(getActivity()), "Loading...");
        RequestQueue queue = Volley.newRequestQueue(context);
//        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Util.dismissProgress(progressDialog);
                try {

                    resultCategoryList.clear();
                    yearResultList.clear();
                    yearAdapter.notifyDataSetChanged();
                    date.setText("");


                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("object");
                    setIsPaid(jsonObject.getInt("is_paid"));
                    if (languagePreference.getString("language", Constants.Language.ENGLISH.getLanguage()).equals(Constants.Language.ENGLISH.getLanguage())) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String description = "";
                            /*JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject original = jsonObject.getJSONObject("original");
                            JSONObject object = original.getJSONObject("object");*/

//                            JSONObject object = jsonArray.getJSONArray(i).getJSONObject(0);
                            JSONObject object = jsonArray.getJSONObject(i);

                            //JSONObject object = jsonObject.getJSONObject("object");

                            description = object.getString("description");

                            String feeling = "";
                            String fromdate = "";
                            String todate = "";
                            String date = "";

                            if(!object.isNull("date"))
                            {
                                date = object.getString("date");
                            }

                            if(!object.isNull("feeling"))
                            {
                                feeling =object.getString("feeling");
                            }

                            if(!object.isNull("from_date"))
                            {
                                fromdate = object.getString("from_date");
                            }

                            if(!object.isNull("to_date"))
                            {
                                todate = object.getString("to_date");
                            }




                            ResultCategory resultCategory = new ResultCategory();

                            resultCategory.setDate(object.getString("date"));
                            resultCategory.setFromdate(object.getString("from_date"));
                            resultCategory.setTodate(object.getString("to_date"));
                            List<YearResult> yearResults1 = new ArrayList<>();

                           /* if (object.getBoolean("status")) {
                                JSONArray jsonArrayData = object.getJSONArray("data");
                                for (int j = 0; j < jsonArrayData.length(); j++) {
                                    JSONObject jsonObject1 = jsonArrayData.getJSONObject(j);
                                    YearResult yearResult = new YearResult(jsonObject1.getString("feeling"), jsonObject1.getString("description"));
                                    yearResults1.add(yearResult);
                                }
                            } else {

                            }*/


                            YearResult yearResult = new YearResult(feeling, description);
                            yearResults1.add(yearResult);



                            resultCategory.setYearResults(yearResults1);


                            resultCategoryList.add(resultCategory);

//                            for (int j = 0; j < jsonArrayData.length(); i++) {
//
//                            }

//                            if(!object.isNull("description"))
//                                description = object.getString("description");
//                                resultCategoryList.add(new ResultCategory(object.getString("date"),object.getString("feeling"),
//                                    description,object.getString("from_date"),object.getString("to_date")));

                        }
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String description = "";
                            /*JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject original = jsonObject.getJSONObject("original");
                            JSONObject object = original.getJSONObject("object");*/
//                            JSONObject object = jsonArray.getJSONArray(i).getJSONObject(0);
                            JSONObject object = jsonArray.getJSONObject(i);


                            description = object.getString("description_hindi");

                            String feeling = "";
                            String fromdate = "";
                            String todate = "";
                            String date = "";

                            if(!object.isNull("date"))
                            {
                                date = object.getString("date");
                            }

                            if(!object.isNull("feeling_hindi"))
                            {
                                feeling =object.getString("feeling_hindi");
                            }

                            if(!object.isNull("from_date"))
                            {
                                fromdate = object.getString("from_date");
                            }

                            if(!object.isNull("to_date"))
                            {
                                todate = object.getString("to_date");
                            }




                            ResultCategory resultCategory = new ResultCategory();

                            resultCategory.setDate(object.getString("hindi_date"));
                            resultCategory.setFromdate(object.getString("from_date"));
                            resultCategory.setTodate(object.getString("to_date"));
                            List<YearResult> yearResults1 = new ArrayList<>();

                          /*  if (object.getBoolean("status")) {
                                JSONArray jsonArrayData = object.getJSONArray("data");
                                for (int j = 0; j < jsonArrayData.length(); j++) {
                                    JSONObject jsonObject1 = jsonArrayData.getJSONObject(j);
                                    YearResult yearResult = new YearResult(jsonObject1.getString("feeling_hindi"), jsonObject1.getString("description_hindi"));
                                    yearResults1.add(yearResult);
                                }
                            } else {

                            }*/


                            YearResult yearResult = new YearResult(feeling, description);
                            yearResults1.add(yearResult);

                            resultCategory.setYearResults(yearResults1);


                            resultCategoryList.add(resultCategory);

                        }
                    }
                    getCount();
                    System.out.println("TempFlag " + tempFlag);
                    if (tempFlag) {
                        data(jsonArray.length());
                        tempFlag = false;
                        //yearAdapter.setAdapter(resultCategoryList);
//                        date.setVisibility(View.VISIBLE);
//                        feeling.setVisibility(View.VISIBLE);
//                        description.setVisibility(View.VISIBLE);
                        prevNextLayout.setVisibility(View.VISIBLE);
                        noDataLayout.setVisibility(View.GONE);
                    } else {
                        data(jsonArray.length());
//                        date.setVisibility(View.GONE);
//                        feeling.setVisibility(View.GONE);
//                        description.setVisibility(View.GONE);
                        prevNextLayout.setVisibility(View.VISIBLE);
                        noDataLayout.setVisibility(View.VISIBLE);
                        // count = i;

                    }

                    //count=0;

                    // yearAdapter.notifyDataSetChanged();

                    //yearAdapter.setAdapter(resultCategoryList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.dismissProgress(progressDialog);
                Toast.makeText(context, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

               /* params.put("language", sharedPreferences.getString("language", Constants.Language.ENGLISH.getLanguage()).
                        equals(Constants.Language.ENGLISH.getLanguage()) ? Constants.Language.ENGLISH.getLanguage() :
                        Constants.Language.HINDI.getLanguage());
*/
                return params;
            }
        };
        queue.add(sr);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    int count;

    public void data(final int resultCategoryList) {

        getData(count);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharedPreferences.getInt("is_paid", 0) == 0) {
                    if (count < resultCategoryList - 1 && count + 1 <= temp_count + 1) {
                        count = count + 1;
                        getData(count);
                    } else
                        startActivity(new Intent(getContext(), SubscriptionActivity.class));
                } else {
                    if (count + 1 < resultCategoryList) {
                        count = count + 1;
                        getData(count);
                    }
                }

//                    if (count+1<resultCategoryList){
//                    count=count+1;
//                    getData(count);
//                }else{
//                    if(sharedPreferences.getInt("is_paid",0) != 1){
//                        startActivity(new Intent(getContext(),SubscriptionActivity.class));
//                    }
//                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharedPreferences.getInt("is_paid", 0) == 0) {
                    if (count != 0 && count - 1 >= temp_count - 1) {
                        count = count - 1;
                        getData(count);
                    } else
                        startActivity(new Intent(getContext(), SubscriptionActivity.class));
                } else {
                    if (count > 0) {
                        count = count - 1;
                        getData(count);
                    } else {
                        return;
                    }
                }
            }
        });

    }

    private TextView date, feeling, description;
    private Button previous, next;

    private void getCount() {
        try {
            getCurrentDate();
            for (int i = 0; i < resultCategoryList.size(); i++) {

         /*   if(tempFlag)
                break;*/

                System.out.println("current date " + currentDate);
                if (new SimpleDateFormat("yyyy-MM-dd").parse(currentDate).
                        equals(new SimpleDateFormat("yyyy-MM-dd").
                                parse(resultCategoryList.get(i).getFromdate()))
                        || new SimpleDateFormat("yyyy-MM-dd").
                        parse(currentDate).equals(new SimpleDateFormat("yyyy-MM-dd").
                        parse(resultCategoryList.get(i).getTodate()))) {
                    System.out.println("date check done");
                    count = i;
                    tempFlag = true;
                    break;
                } else if (new SimpleDateFormat("yyyy-MM-dd").parse(currentDate).
                        after(new SimpleDateFormat("yyyy-MM-dd").
                                parse(resultCategoryList.get(i).getFromdate()))
                        && new SimpleDateFormat("yyyy-MM-dd").
                        parse(currentDate).before(new SimpleDateFormat("yyyy-MM-dd").
                        parse(resultCategoryList.get(i).getTodate()))) {

                    count = i;
                    tempFlag = true;

                    System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse(currentDate)
                            + " from date " + new SimpleDateFormat("yyyy-MM-dd").
                            parse(resultCategoryList.get(i).getFromdate()));
                    System.out.println("to date " + new SimpleDateFormat("yyyy-MM-dd").
                            parse(resultCategoryList.get(i).getTodate()));
                    System.out.println("date count " + count);

                    break;
                } else {
                    if (add < resultCategoryList.size() - 1 && !tempFlag) {
                        getCount2();
                    }
                }/*else {
                 *//* tempFlag = true;
                            count = i - 3;*//*
                        }*/
                //if(tempFlag)
                //  break;


            }
        } catch (Exception e) {
            System.out.println("exception " + e.getMessage());
        }
        temp_count = count;
        count = temp_count;
    }

    public void getCount2() {
        try {
            if (tempFlag)
                return;

            add = add + 1;
            System.out.println("date add " + add);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, add);
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd ");
            currentDate2 = mdformat.format(calendar.getTime());
            System.out.println("current date 1 " + currentDate2);


            for (int q = 0; q < resultCategoryList.size(); q++) {
/*

                    if (tempFlag)
                        break;
*/

                if (new SimpleDateFormat("yyyy-MM-dd").parse(currentDate2).
                        equals(new SimpleDateFormat("yyyy-MM-dd").
                                parse(resultCategoryList.get(q).getFromdate()))
                        || new SimpleDateFormat("yyyy-MM-dd").
                        parse(currentDate2).equals(new SimpleDateFormat("yyyy-MM-dd").
                        parse(resultCategoryList.get(q).getTodate()))) {
                    count = q;
                    tempFlag = true;
                    System.out.println("from date 1" + resultCategoryList.get(q).getTodate());
                    break;

                } else if (new SimpleDateFormat("yyyy-MM-dd").parse(currentDate2).
                        after(new SimpleDateFormat("yyyy-MM-dd").
                                parse(resultCategoryList.get(q).getFromdate()))
                        && new SimpleDateFormat("yyyy-MM-dd").
                        parse(currentDate2).before(new SimpleDateFormat("yyyy-MM-dd").
                        parse(resultCategoryList.get(q).getTodate()))) {

                    count = q;
                    tempFlag = true;

                    System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse(currentDate2)
                            + " from date " + new SimpleDateFormat("yyyy-MM-dd").
                            parse(resultCategoryList.get(q).getFromdate()));
                    System.out.println("to date " + new SimpleDateFormat("yyyy-MM-dd").
                            parse(resultCategoryList.get(q).getTodate()));
                    System.out.println("date count " + count);

                    break;
                } else {
                    System.out.println("date else ");
                }
            }
        } catch (Exception e) {
            System.out.println("date exception " + e.getMessage());
        }
        temp_count = count;
        count = temp_count;
    }

    public void getData(int count) {


        try {
            date.setText(resultCategoryList.get(count).getDate() + "");

            yearResultList.clear();


            if (resultCategoryList.get(count).getYearResults().size() <= 0) {
                textViewNoData.setVisibility(View.VISIBLE);
                recyclerViewYear.setVisibility(View.GONE);

                if (languagePreference.getString("language", Constants.Language.ENGLISH.getLanguage()).equals(Constants.Language.ENGLISH.getLanguage())) {
                    //textViewNoData.setText("In these cases, your time is going on as usual");
                    textViewNoData.setText("Now there is no such planetary position regarding these matters which can present quite nice or quite bad feelings for you. If good or bad results unexpectedly occur, your birth time planets are responsible means it is your life journey due to planetary situation of your horoscope.");
                } else {
                    //textViewNoData.setText("इन मामलों में अभी आपका समय सामान्य ढंग का चल रहा है");
                    textViewNoData.setText("अभी आसमान में ग्रहों की स्थिति ऐसी नहीं कि इन मामलों में आपके समक्ष खास अच्छी या खास बुरी मनःस्थिति उपस्थित करे। बावजूद इसके 1-2 प्रतिशत जगहों पर यदि इस समय में कोई अच्छी या बुरी बात होती है तो उसके लिए आपके जन्मकालीन ग्रह जिम्मेदार होंगे यानि जन्मकुंडली में उपस्थित ग्रहों के कारण बन रही आपकी जीवनयात्रा में एक मोड़ खास वातावरण बनाएगा।");
                }
                textViewNoData.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(getActivity()).getTextSize());

            } else {
                textViewNoData.setVisibility(View.GONE);
                recyclerViewYear.setVisibility(View.VISIBLE);

                for (int i = 0; i < resultCategoryList.get(count).getYearResults().size(); i++) {
                    System.out.println("resultData " + resultCategoryList.get(count).getYearResults().get(i).getDescription());
                    YearResult movie = new YearResult(resultCategoryList.get(count).getYearResults().get(i).getFeeling(), resultCategoryList.get(count).getYearResults().get(i).getDescription());
                    yearResultList.add(movie);
                }

            }

            //yearResultList.add(yearResultList);
            yearAdapter.notifyDataSetChanged();
            //recyclerViewYear.setAdapter(new YearAdapter(yearResultList));
//        date.setText(resultCategoryList.get(count).getName());
//        date.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(getContext()).getTextSize());
//        if(resultCategoryList.get(count).getFeeling().equalsIgnoreCase("Normal") || resultCategoryList.get(count).getFeeling().equalsIgnoreCase("सामान्य"))
//            feeling.setTextColor(Color.parseColor("#728FCE")); // Green
//        if(resultCategoryList.get(count).getFeeling().equalsIgnoreCase("Positive") || resultCategoryList.get(count).getFeeling().equalsIgnoreCase("सकारात्मक"))
//            feeling.setTextColor(Color.parseColor("#228B22")); // Green
//        else if(resultCategoryList.get(count).getFeeling().equalsIgnoreCase("Negative") || resultCategoryList.get(count).getFeeling().equalsIgnoreCase("ऋणात्मक"))
//            feeling.setTextColor(Color.parseColor("#802606")); // Red
//
//        feeling.setText(resultCategoryList.get(count).getFeeling());
//        feeling.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(getContext()).getTextSize());
//        description.setText(resultCategoryList.get(count).getDescription());
//        description.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(getContext()).getTextSize());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //    public void getData(int count){
//        date.setText(resultCategoryList.get(count).getName());
//        date.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(getContext()).getTextSize());
//        if(resultCategoryList.get(count).getFeeling().equalsIgnoreCase("Normal") || resultCategoryList.get(count).getFeeling().equalsIgnoreCase("सामान्य"))
//            feeling.setTextColor(Color.parseColor("#728FCE")); // Green
//        if(resultCategoryList.get(count).getFeeling().equalsIgnoreCase("Positive") || resultCategoryList.get(count).getFeeling().equalsIgnoreCase("सकारात्मक"))
//            feeling.setTextColor(Color.parseColor("#228B22")); // Green
//        else if(resultCategoryList.get(count).getFeeling().equalsIgnoreCase("Negative") || resultCategoryList.get(count).getFeeling().equalsIgnoreCase("ऋणात्मक"))
//            feeling.setTextColor(Color.parseColor("#802606")); // Red
//
//        feeling.setText(resultCategoryList.get(count).getFeeling());
//        feeling.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(getContext()).getTextSize());
//        description.setText(resultCategoryList.get(count).getDescription());
//        description.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(getContext()).getTextSize());
//    }
    int add = 0;


}

