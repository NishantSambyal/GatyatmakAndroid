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

public class Year extends Fragment implements AdapterView.OnItemSelectedListener {
    TextView textView, textViewNoData;
    List<YearCategory> yearCategoryList = new ArrayList<>();
    Spinner spinner;
    SharedPreferences sharedPreferences, languagePreference;
    SharedPreferences.Editor editor;
    List<ResultCategory> resultCategoryList = new ArrayList<>();
    List<YearResult> yearResultList = new ArrayList<>();

    YearAdapter yearAdapter;
    ProgressDialog progressDialog;
    LinearLayout prevNextLayout;
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
        prevNextLayout = v.findViewById(R.id.layout_prev_next1);
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
                System.out.println("### Dropdown Param: "+params.toString());
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
        System.out.println("### PostURL: "+url);
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
                    if (jsonArray.length() > 1){
                        count = 2;
                    }
                    if (languagePreference.getString("language", Constants.Language.ENGLISH.getLanguage()).equals(Constants.Language.ENGLISH.getLanguage())) {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {
                                JSONObject jObject = jsonArray.getJSONObject(i);

                                boolean status = jObject.getBoolean("status");
                                if (status) {
                                //SETTING DATE
                                ResultCategory resultCategory = new ResultCategory();
                                String s = jObject.getString("date");
                                resultCategory.setDate(jObject.getString("date"));
                                resultCategory.setFromdate(jObject.getString("from_at"));
                                resultCategory.setTodate(jObject.getString("to_at"));

                                //SETTING LIST DATA
                                JSONArray jDataArray = jObject.getJSONArray("data");
                                List<YearResult> yearResults1 = new ArrayList<>();
                                for (int j = 0; j < jDataArray.length(); j++) {
                                    try {
                                        JSONObject jObject2 = jDataArray.getJSONObject(j);
                                        String feeling = jObject2.getString("feeling");
                                        String description = jObject2.getString("description");

                                        yearResults1.add(new YearResult(feeling, description));
                                    } catch (Exception e) {
                                    }
                                }
                                resultCategory.setYearResults(yearResults1);

                                resultCategoryList.add(resultCategory);
                            }
                            }catch (Exception e){}
                        }
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {
                                JSONObject jObject = jsonArray.getJSONObject(i);

                                boolean status = jObject.getBoolean("status");
                                if (status) {
                                //  SETTING DATE
                                ResultCategory resultCategory = new ResultCategory();
                                resultCategory.setDate(jObject.getString("hindi_date"));
                                resultCategory.setFromdate(jObject.getString("from_at"));
                                resultCategory.setTodate(jObject.getString("to_at"));

                                // SETTING LIST
                                JSONArray jDataArray = jObject.getJSONArray("data");
                                List<YearResult> yearResults1 = new ArrayList<>();
                                for (int j = 0; j < jDataArray.length(); j++) {
                                    try {
                                        JSONObject jObject2 = jDataArray.getJSONObject(j);
                                        String feeling = jObject2.getString("feeling_hindi");
                                        String description = jObject2.getString("description_hindi");

                                        yearResults1.add(new YearResult(feeling, description));
                                    } catch (Exception e) {
                                    }
                                }
                                resultCategory.setYearResults(yearResults1);

                                resultCategoryList.add(resultCategory);

                            }
                            }catch (Exception e){}

                        }
                    }
                    getCount();
                    System.out.println("TempFlag " + tempFlag);
                    if (tempFlag) {
                        data(jsonArray.length());
                        tempFlag = false;

                        prevNextLayout.setVisibility(View.VISIBLE);
                    } else {
                        data(jsonArray.length());
                        prevNextLayout.setVisibility(View.VISIBLE);

                    }

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

    int count = 1;

    public void data(final int resultCategoryListSize) {

        getData(count);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharedPreferences.getInt("is_paid", 0) == 0) {
                    if (count < resultCategoryListSize - 1 && count + 1 <= temp_count + 1) {
                        count = count + 1;
                        getData(count);
                    } else
                        startActivity(new Intent(getContext(), SubscriptionActivity.class));
                } else {
                    if (count + 2 < resultCategoryListSize) {
                        try {
                            count = count + 1;
                            resultCategoryList.get(count - 1).getDate();
                            getData(count);
                        }catch (Exception e){
                            count = count -1;
                        }
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
                    if (count-1 > 0) {
                            count = count - 1;
                            getData(count);
                    } else {
                        return;
                    }
                }
            }
        });

    }

    private TextView date;
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
                }
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
            if ((count-1) < 0){
                return;
            }
            date.setText(resultCategoryList.get(count-1).getDate() + "");

            yearResultList.clear();


            if (resultCategoryList.get(count-1).getYearResults().size() <= 0) {
                textViewNoData.setVisibility(View.GONE);
                recyclerViewYear.setVisibility(View.GONE);

                if (languagePreference.getString("language", Constants.Language.ENGLISH.getLanguage()).equals(Constants.Language.ENGLISH.getLanguage())) {
                    //textViewNoData.setText("In these cases, your time is going on as usual");
                    textViewNoData.setText("Now there is no such planetary position regarding these matters which can present quite nice or quite bad feelings for you. If good or bad results unexpectedly occur this time in 1-2 % cases , your birth time planets are responsible means it is your life journey due to planetary situation of your horoscope.");
                } else {
                    //textViewNoData.setText("इन मामलों में अभी आपका समय सामान्य ढंग का चल रहा है");
                    textViewNoData.setText("अभी आसमान में ग्रहों की स्थिति ऐसी नहीं कि इन मामलों में आपके समक्ष खास अच्छी या खास बुरी मनःस्थिति उपस्थित करे। बावजूद इसके 1-2 प्रतिशत जगहों पर यदि इस समय में कोई अच्छी या बुरी बात होती है तो उसके लिए आपके जन्मकालीन ग्रह जिम्मेदार होंगे यानि जन्मकुंडली में उपस्थित ग्रहों के कारण बन रही आपकी जीवनयात्रा में खास वातावरण बनाएगा।");
                }
                textViewNoData.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(getActivity()).getTextSize());

            } else {
                textViewNoData.setVisibility(View.GONE);
                recyclerViewYear.setVisibility(View.VISIBLE);

                for (int i = 0; i < resultCategoryList.get(count-1).getYearResults().size(); i++) {
                    System.out.println("resultData " + resultCategoryList.get(count-1).getYearResults().get(i).getDescription());
                    YearResult movie = new YearResult(resultCategoryList.get(count-1).getYearResults().get(i).getFeeling(), resultCategoryList.get(count-1).getYearResults().get(i).getDescription());
                    yearResultList.add(movie);
                }

                yearAdapter.notifyDataSetChanged();
            }
            //yearResultList.add(yearResultList);

        } catch (Exception e) {
            textViewNoData.setVisibility(View.VISIBLE);
            recyclerViewYear.setVisibility(View.GONE);
            if (languagePreference.getString("language", Constants.Language.ENGLISH.getLanguage()).equals(Constants.Language.ENGLISH.getLanguage())) {
                textViewNoData.setText("Now there is no such planetary position regarding these matters which can present quite nice or quite bad feelings for you. If good or bad results unexpectedly occur this time in 1-2 % cases , your birth time planets are responsible means it is your life journey due to planetary situation of your horoscope.");
            } else {
                textViewNoData.setText("अभी आसमान में ग्रहों की स्थिति ऐसी नहीं कि इन मामलों में आपके समक्ष खास अच्छी या खास बुरी मनःस्थिति उपस्थित करे। बावजूद इसके 1-2 प्रतिशत जगहों पर यदि इस समय में कोई अच्छी या बुरी बात होती है तो उसके लिए आपके जन्मकालीन ग्रह जिम्मेदार होंगे यानि जन्मकुंडली में उपस्थित ग्रहों के कारण बन रही आपकी जीवनयात्रा में खास वातावरण बनाएगा।");
            }

//            e.printStackTrace();
        }
    }

    int add = 0;

}

