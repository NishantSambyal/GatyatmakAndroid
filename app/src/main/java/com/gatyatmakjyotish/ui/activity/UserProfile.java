package com.gatyatmakjyotish.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.constants.Api;
import com.gatyatmakjyotish.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.gatyatmakjyotish.constants.Constants.CURRENT_PLACE;
import static com.gatyatmakjyotish.constants.Constants.DATE_OF_BIRTH;
import static com.gatyatmakjyotish.constants.Constants.EMAIL;
import static com.gatyatmakjyotish.constants.Constants.GENDER;
import static com.gatyatmakjyotish.constants.Constants.ID;
import static com.gatyatmakjyotish.constants.Constants.LOGIN_PREF;
import static com.gatyatmakjyotish.constants.Constants.MOBILE;
import static com.gatyatmakjyotish.constants.Constants.NAME;
import static com.gatyatmakjyotish.constants.Constants.PLACE_OF_BIRTH;
import static com.gatyatmakjyotish.constants.Constants.TIME_OF_BIRTH;

public class UserProfile extends AppCompatActivity {
    EditText name, dob, tob, pob, gender, location, mob, emailaddress;
    SharedPreferences sharedPreferences;
    android.support.v7.widget.Toolbar toolbar;
    TextView edit;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);
        name = findViewById(R.id.et_Name);
        dob = findViewById(R.id.et_Dob);
        tob = findViewById(R.id.et_Tob);
        pob = findViewById(R.id.et_Pob);
        edit = findViewById(R.id.tv_edit);
        gender = findViewById(R.id.gender);
        location = findViewById(R.id.et_Loc);
        mob = findViewById(R.id.et_Mob);
        button = findViewById(R.id.button);
        emailaddress = findViewById(R.id.et_Email);
        sharedPreferences = getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);

        String nameStr = sharedPreferences.getString(NAME, "");
        String pobStr = sharedPreferences.getString(PLACE_OF_BIRTH, "");
        String currentplaceStr = sharedPreferences.getString(CURRENT_PLACE, "");
        String tobStr = sharedPreferences.getString(TIME_OF_BIRTH, "");
        String mobStr = sharedPreferences.getString(MOBILE, "");
        String emailStr = sharedPreferences.getString(EMAIL, "");
        String genderStr = sharedPreferences.getString(GENDER, "");
        String dobStr = sharedPreferences.getString(DATE_OF_BIRTH, "");
        name.setText(nameStr);
        pob.setText(pobStr);
        location.setText(currentplaceStr);
        tob.setText(tobStr);
        mob.setText(mobStr);
        emailaddress.setText(emailStr);
        gender.setText(genderStr);
        dob.setText(dobStr);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, getString(R.string.UsersProfile));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setEnabled(true);
                dob.setEnabled(true);
                tob.setEnabled(true);
                pob.setEnabled(true);
                gender.setEnabled(true);
                location.setEnabled(true);
                mob.setEnabled(true);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api.USER_ID = sharedPreferences.getString(ID, "");
                postNewComment();
            }
        });
    }


    private void postNewComment() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Api.UPDATE_API = Api.BASE_URL + Api.USER + sharedPreferences.getString(ID, "") + Api.UPDATE;
        StringRequest sr = new StringRequest(Request.Method.POST, Api.UPDATE_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    if (!status) {
                        JSONObject object = jsonObject.getJSONObject("user_info");

                        String name = object.getString("name");
                        String date_of_birth = object.getString("date_of_birth");
                        String time_of_birth = object.getString("time_of_birth");
                        String place_of_birth = object.getString("place_of_birth");
                        String gender = object.getString("gender");
                        String mobile = object.getString("mobile");
                        String current_place = object.getString("current_place");
                        String email = object.getString("email");

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(NAME, name);
                        editor.putString(DATE_OF_BIRTH, date_of_birth);
                        editor.putString(TIME_OF_BIRTH, time_of_birth);
                        editor.putString(PLACE_OF_BIRTH, place_of_birth);
                        editor.putString(GENDER, gender);
                        editor.putString(MOBILE, mobile);
                        editor.putString(CURRENT_PLACE, current_place);
                        editor.putString(EMAIL, email);
                        editor.apply();
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                        startActivity(intent);
                    } else {
                        name.setError(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("response: ", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name.getText().toString().trim());
//                params.put("email", emailaddress.getText().toString().trim());
                params.put("date_of_birth", dob.getText().toString().trim());
                params.put("place_of_birth", pob.getText().toString().trim());
                params.put("time_of_birth", tob.getText().toString().trim());
                params.put("gender", gender.getText().toString().trim());
//                params.put("mobile", mob.getText().toString().trim());
                params.put("current_place", location.getText().toString().trim());
                return params;
            }

        };
        queue.add(sr);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}

