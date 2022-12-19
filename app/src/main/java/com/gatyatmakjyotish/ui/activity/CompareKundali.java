package com.gatyatmakjyotish.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.constants.Api;
import com.gatyatmakjyotish.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.gatyatmakjyotish.constants.Constants.ID;
import static com.gatyatmakjyotish.constants.Constants.LOGIN_PREF;

public class CompareKundali extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    TextView toolbarTitle, textViewMessage;
    private DrawerLayout mDrawerLayout;
    private SharedPreferences sharedPreferences;
    public String setService = "";
    private ViewPager viewPager;
    Context context;
    int p;
    Button btn_SignUp;
    String user_id;
    private ProgressDialog progressDialog;


    EditText et_Name, et_Dob, et_Tob, et_Pob, et_Loc, et_Mob, et_Email, et_Password, et_Cpass, et_promocode;
    EditText et_fDob, et_fTob,et_fName,et_fPob;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_kundali);
        context = this;
        progressDialog = new ProgressDialog(context);

        user_id=getUserID();

        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbarWithoutBack(this, toolbar, getString(R.string.app_name_header));
        //viewpager = findViewById(R.id.viewpager);
        //tabLayout = findViewById(R.id.tabs);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.app_name_header));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Util.setupToolbar(CompareKundali.this, toolbar, getResources().getString(R.string.enter_detail));
        try {
            p = getIntent().getExtras().getInt("proceedWith");
        } catch (Exception e) {
            e.printStackTrace();
        }

        et_Name = findViewById(R.id.et_Name);
        et_fName = findViewById(R.id.et_fName);
        et_fPob = findViewById(R.id.et_fPob);
        et_Pob = findViewById(R.id.et_Pob);


        et_Dob = findViewById(R.id.et_Dob);
        et_Tob = findViewById(R.id.et_Tob);
        et_fDob = findViewById(R.id.et_fDob);
        et_fTob = findViewById(R.id.et_fTob);
        btn_SignUp = findViewById(R.id.btn_SignUp);
        et_Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpDatePickerDOB(et_Dob, CompareKundali.this).show();
            }
        });
        et_Dob.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (Util.checkDateSelection(15, et_Dob.getText().toString(), new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()), "yyyy-MM-dd")) {
                    Toast.makeText(CompareKundali.this, "You are too younger to use this app", Toast.LENGTH_LONG).show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        et_Tob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpTimePickerFormat(et_Tob, CompareKundali.this).show();
            }
        });
        et_fDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpDatePickerDOB(et_fDob, CompareKundali.this).show();
            }
        });
        et_fDob.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (Util.checkDateSelection(15, et_fDob.getText().toString(), new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()), "yyyy-MM-dd")) {
                    Toast.makeText(CompareKundali.this, "You are too younger to use this app", Toast.LENGTH_LONG).show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
        et_fTob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpTimePickerFormat(et_fTob, CompareKundali.this).show();
            }
        });


        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (isValid()) {
                    submitDetail();
//                }
            }
        });

    }

    private boolean isValid(){

        if (TextUtils.isEmpty(et_Name.getText())||TextUtils.isEmpty(et_fName.getText())
                ||TextUtils.isEmpty(et_fPob.getText())||TextUtils.isEmpty(et_Pob.getText())
                ||TextUtils.isEmpty(et_Dob.getText())||TextUtils.isEmpty(et_Tob.getText())
                ||TextUtils.isEmpty(et_fDob.getText())||TextUtils.isEmpty(et_fTob.getText())){
            Toast.makeText(context, "All field are mandatory", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.notification:

                Intent i=new Intent(this,Notification.class);
                startActivity(i);

                break;
            case R.id.cart:

                Intent intent=new Intent(getApplicationContext(),Cart.class);
                intent.putExtra("reference","CompareKundali");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void submitDetail() {
        progressDialog.show();
        final JsonArray jsonArray=new JsonArray();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("user_id",getUserID());
        jsonObject.addProperty("name",et_Name.getText().toString().trim());
        jsonObject.addProperty("gender","male");
        jsonObject.addProperty("place_of_birth",et_Pob.getText().toString().trim());
        jsonObject.addProperty("date_of_birth",et_Dob.getText().toString().trim());
        jsonObject.addProperty("time_of_birth",et_Tob.getText().toString());
        jsonArray.add(jsonObject);
        JsonObject jsonObject1=new JsonObject();
        jsonObject1.addProperty("user_id",getUserID());
        jsonObject1.addProperty("name",et_fName.getText().toString().trim());
        jsonObject1.addProperty("gender","female");
        jsonObject1.addProperty("place_of_birth",et_fPob.getText().toString().trim());
        jsonObject1.addProperty("date_of_birth",et_fDob.getText().toString().trim());
        jsonObject1.addProperty("time_of_birth",et_fTob.getText().toString());
        jsonArray.add(jsonObject1);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, Api.SAVE_KUNDALI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response: ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.optBoolean("status")) {
                        Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CompareKundali.this, Cart.class);
                        intent.putExtra("proceedWith", p);
                        startActivity(intent);
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_details", String.valueOf(jsonArray));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                20 * 1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(sr);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String getUserID(){
        sharedPreferences = getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
        return sharedPreferences.getString(ID,"27");
    }


}
