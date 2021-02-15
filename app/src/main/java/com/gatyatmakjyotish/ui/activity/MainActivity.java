package com.gatyatmakjyotish.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.constants.Api;
import com.gatyatmakjyotish.constants.Constants;
import com.gatyatmakjyotish.ui.fragmentBase.Email_base;
import com.gatyatmakjyotish.util.Util;
import com.google.android.libraries.places.api.Places;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import static com.gatyatmakjyotish.constants.Constants.CURRENT_PLACE;
import static com.gatyatmakjyotish.constants.Constants.DATE_OF_BIRTH;
import static com.gatyatmakjyotish.constants.Constants.EMAIL;
import static com.gatyatmakjyotish.constants.Constants.EMAIL_VERIFIED_AT;
import static com.gatyatmakjyotish.constants.Constants.GENDER;
import static com.gatyatmakjyotish.constants.Constants.ID;
import static com.gatyatmakjyotish.constants.Constants.LANGUAGE;
import static com.gatyatmakjyotish.constants.Constants.LOGIN_PREF;
import static com.gatyatmakjyotish.constants.Constants.MOBILE;
import static com.gatyatmakjyotish.constants.Constants.NAME;
import static com.gatyatmakjyotish.constants.Constants.NOTIFICATION_PREF;
import static com.gatyatmakjyotish.constants.Constants.PASSWORD;
import static com.gatyatmakjyotish.constants.Constants.PLACE_OF_BIRTH;
import static com.gatyatmakjyotish.constants.Constants.PROMOCODE;
import static com.gatyatmakjyotish.constants.Constants.TIME_OF_BIRTH;
import static com.gatyatmakjyotish.constants.Constants.TOKEN;
import static com.gatyatmakjyotish.constants.Constants.WALLET_POINT;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText email, password;
    private Toolbar toolbar;
    private TextView register, forgot;
    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesNotification;
    SharedPreferences.Editor editor;
    TextInputLayout inputPassword;
    private LinearLayout signup;
    boolean isNotification = false;

    @Override
    public void onBackPressed() {
        return;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        forgot = findViewById(R.id.forgot);
        signup = findViewById(R.id.signup);

        sharedPreferencesNotification = getSharedPreferences(NOTIFICATION_PREF, MODE_PRIVATE);

        if(!sharedPreferencesNotification.getString("title","").equalsIgnoreCase("")){
            Intent intent = new Intent(this, NotificationActivity.class);
            intent.putExtra("title", sharedPreferencesNotification.getString("title",""));
            intent.putExtra("message", sharedPreferencesNotification.getString("message",""));
            startActivity(intent);

            editor = sharedPreferencesNotification.edit();
            editor.putString("title","");
            editor.putString("message","");
            editor.apply();
            isNotification = true;
        }





//
//        Intent intent1 = new Intent(getBaseContext(), MyFirebaseMessagingService.class);
//        startService(intent1);

        Util.setupToolbarWithoutBack(this, toolbar, getString(R.string.app_name));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            // Write you code here if permission already given.ey
        }

        sharedPreferences = getSharedPreferences("language", MODE_PRIVATE);
   /*     if(getSharedPreferences("language", MODE_PRIVATE).getString("language", null) == null) {
            Intent intentLanguage = new Intent(getApplicationContext(), Language.class);
            startActivity(intentLanguage);
        }
   */
        //todo language selection at app startup code here
        String lang=sharedPreferences.getString(LANGUAGE, "");
        if (lang.equals("")){
            Intent intentLanguage = new Intent(getApplicationContext(), Language.class);
            startActivity(intentLanguage);
        }else{
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            if (lang.equals(Constants.Language.ENGLISH.getLanguage())){
                conf.locale = Locale.getDefault();
            }else{
                conf.locale = new Locale("hi");
            }
            res.updateConfiguration(conf, dm);
        }


        /*SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", null);
        editor.apply();

        System.out.println("LanguageSelection " +getSharedPreferences("language", MODE_PRIVATE).getString("language", null));
        */


        //sharedPreferences = getApplicationContext().getSharedPreferences(LOGIN_PREF, 0); // 0 - for private mode
      /*  if(sharedPreferences.getString(TOKEN, null) != null) {
            //Intent intent = new Intent(getApplicationContext(), DashBoard.class);
            Intent intent = new Intent(getApplicationContext(), FrontScreen.class);
            startActivity(intent);
            finish();
        }*/
        sharedPreferences = getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);

        editor = sharedPreferences.edit();
        Places.initialize(getApplicationContext(), "AIzaSyAe2JrUxTmyOVrUfhlT-y7HAgxacQaZ4qE");
        button = findViewById(R.id.button);
        inputPassword = findViewById(R.id.input_password);



        /*register = findViewById(R.id.register);*/

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        String em=sharedPreferences.getString(EMAIL, "");
        String pas=sharedPreferences.getString(PASSWORD, "");
        password.setText(pas);
        email.setText(em);
        if(!em.equals("") && !pas.equals("") && isNotification == false){
            postNewComment(getApplicationContext(), em, pas,editor);
        }

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog builder = new Dialog(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.forgotpassword, null);
                /*builder.setView(dialogView);*/
                builder.setContentView(R.layout.forgotpassword);
                final EditText editText = (EditText) builder.findViewById(R.id.email);
                final Button button = builder.findViewById(R.id.fbutton);
             /*   builder.setPositiveButton(getString(R.string.find), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });*/

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetForgetRequest(editText.getText().toString(), builder);
                    }
                });
                builder.show();



            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equalsIgnoreCase("") || !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("invalid email address");
                } else if (password.getText().toString().equalsIgnoreCase("")) {
                    password.setError("password cant be empty!");
                } else {
                    postNewComment(MainActivity.this,email.getText().toString().trim(),password.getText().toString().trim()
                            ,editor);
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.gatyatmakjyotish.ui.activity.SignUp.class);
                startActivity(intent);
            }
        });

    }

    private void resetForgetRequest(final String email, final Dialog builder) {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        System.out.println("euyueryyuyuyuryeryeryureyu " +email);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, Api.FORGOTPASSWORD_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog != null && progressDialog.isShowing())
                    builder.dismiss();
                // Toast.makeText(MainActivity.this, "Password reset mail is sent to your mail", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optBoolean("status"))
                        Toast.makeText(MainActivity.this, ""+jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    builder.dismiss();
                    //Toast.makeText(MainActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("response: ", response.toString());
                builder.dismiss();

              //  Toast.makeText(MainActivity.this, "Password reset mail is sent to your email", Toast.LENGTH_SHORT).show();
                alertdialog();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null && progressDialog.isShowing())
                    builder.dismiss();
               // Toast.makeText(MainActivity.this, "Password reset mail is sent to your email", Toast.LENGTH_SHORT).show();
                  alertdialog();
                // Toast.makeText(MainActivity.this, ""+, Toast.LENGTH_SHORT).show();
                System.out.println("error "+error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email.trim());
                return params;

            }

        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(sr);
    }


    public void postNewComment(Context context, final String emailStr, final String passwordStr, final SharedPreferences.Editor editor) {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, Api.LOGIN_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        JSONObject userInfo = jsonObject.getJSONObject("user_info");
                        String token = jsonObject.getString("token");
                        String name = userInfo.getString("name");
                        String place_of_birth = userInfo.getString("place_of_birth");
                        String currenPlace = userInfo.getString("current_place");
                        String email = userInfo.getString("email");
                        String gender = userInfo.getString("gender");
                        String email_verified_at = userInfo.getString("email_verified_at");
                        String id = userInfo.getString("id");
                        String wallet_point = userInfo.getString("wallet_point");
                        String promo_code = userInfo.getString("promo_code");
                        String date_of_birth = userInfo.getString("date_of_birth");
                        String time_of_birth = userInfo.getString("time_of_birth");
                        String mobile = userInfo.getString("mobile");
                        String letter = userInfo.getString("letter");

                        /*if(email_verified_at != "null"){
                            Util.showDialog(MainActivity.this, "Login Message", "Please Verify Email First", false, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }*/

                        editor.putBoolean("KEY_NAME", true);
                        editor.putString(TOKEN, token);
                        editor.putString(NAME, name);
                        editor.putString(PLACE_OF_BIRTH, place_of_birth);
                        editor.putString(CURRENT_PLACE, currenPlace);
                        editor.putString(EMAIL, email);
                        editor.putString(PASSWORD, passwordStr);
                        editor.putString(EMAIL_VERIFIED_AT, email_verified_at);
                        editor.putString(ID, id);
                        editor.putString(WALLET_POINT,wallet_point);
                        //editor.putString(WALLET_POINT,"50.00");
                        editor.putString(PROMOCODE,promo_code);
                        editor.putString(DATE_OF_BIRTH, date_of_birth);
                        editor.putString(GENDER, gender);
                        editor.putString(TIME_OF_BIRTH, time_of_birth);
                        editor.putString(MOBILE, mobile);
                        editor.apply();

                        if(letter != "null") {
                            finish();
                            Intent intentDashboard = new Intent(getApplicationContext(), com.gatyatmakjyotish.ui.activity.FrontScreen.class);
                            startActivity(intentDashboard);
                            
                        }
                        else {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), Email_base.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    } else {
                        if (inputPassword != null)
                            inputPassword.setError(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("response: ", response.toString());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", emailStr);
                params.put("password", passwordStr);
                return params;

            }

        };
        queue.add(sr);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                /*if(sharedPreferences.getString(TOKEN, null) != null) {
                    Intent intent = new Intent(getApplicationContext(), SignUp.class);
                    startActivity(intent);
                }*/
                return true;
            case R.id.item2:
                Intent intent1 = new Intent(getApplicationContext(), com.gatyatmakjyotish.ui.activity.Remedy.class);
                startActivity(intent1);
                return true;
            case R.id.item3:
                Intent intent2 = new Intent(getApplicationContext(), com.gatyatmakjyotish.ui.activity.PointstoKnow.class);
                startActivity(intent2);
                return true;
            case R.id.item4:
                Intent intent3 = new Intent(getApplicationContext(), com.gatyatmakjyotish.ui.activity.OurMissionActivity.class);
                startActivity(intent3);
                return true;
            case R.id.item5:
                Intent intent4 = new Intent(getApplicationContext(), com.gatyatmakjyotish.ui.activity.UseofApp.class);
                startActivity(intent4);
                return true;
            case R.id.item6:
                Intent intent5 = new Intent(getApplicationContext(), com.gatyatmakjyotish.ui.activity.Team.class);
                startActivity(intent5);
                return true;
            case R.id.item7:
                Intent intent6 = new Intent(getApplicationContext(), Language.class);
                startActivity(intent6);
                return true;
            case R.id.item8:
                Intent intent7 = new Intent(getApplicationContext(), com.gatyatmakjyotish.ui.activity.AboutUs.class);
                startActivity(intent7);
                return true;
            case R.id.item9:
                Intent intent8 = new Intent(getApplicationContext(), com.gatyatmakjyotish.ui.activity.ContactUs.class);
                startActivity(intent8);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void alertdialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("PASSWORD RESET MAIL HAS BEEN SENT TO YOUR MAIL");
        alertDialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialogBuilder.show();
    }
}