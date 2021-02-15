package com.gatyatmakjyotish.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HoroscopeMatchingActivity extends AppCompatActivity {
    Button btn_SignUp;
    Context context;
    EditText et_MaleName, et_MaleDob, et_MaleTob, et_MalePob, et_FemaleName, et_FemaleDob, et_FemaleTob, et_FemalePob;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    private ProgressDialog progressDialog;
    Toolbar toolbar;
    String gender = "male";
    private BreakIterator error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope_matching);

        toolbar = findViewById(R.id.toolbar);

        Util.setupToolbar(this, toolbar, getString(R.string.horoscope_matching));

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        et_MaleName = findViewById(R.id.et_MaleName);
        et_FemaleName = findViewById(R.id.et_FemaleName);
        et_MaleDob = findViewById(R.id.et_MaleDob);
        et_FemaleDob = findViewById(R.id.et_FemaleDob);
        et_MaleTob = findViewById(R.id.et_MaleTob);
        et_FemaleTob = findViewById(R.id.et_FemaleTob);
        et_MalePob = findViewById(R.id.et_MalePob);
        et_FemalePob = findViewById(R.id.et_FemalePob);
        btn_SignUp = findViewById(R.id.btn_SignUp);


        et_MaleDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpDatePicker(et_MaleDob, HoroscopeMatchingActivity.this).show();
            }
        });

        et_FemaleDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpDatePicker(et_FemaleDob, HoroscopeMatchingActivity.this).show();
            }
        });



        et_MaleDob.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (Util.checkDateSelection(15,et_MaleDob.getText().toString(),new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()),"yyyy-MM-dd")) {
                    Toast.makeText(HoroscopeMatchingActivity.this, "You are too younger to use this app", Toast.LENGTH_LONG).show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        et_FemaleDob.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (Util.checkDateSelection(15,et_FemaleDob.getText().toString(),new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()),"yyyy-MM-dd")) {
                    Toast.makeText(HoroscopeMatchingActivity.this, "You are too younger to use this app", Toast.LENGTH_LONG).show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        et_MaleTob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Util.setUpTimePicker(et_MaleTob, HoroscopeMatchingActivity.this).show();
            }
        });

        et_FemaleTob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpTimePicker(et_FemaleTob, HoroscopeMatchingActivity.this).show();
            }
        });


        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                if (et_MaleName.getText().toString().equalsIgnoreCase("")) {
                    et_MaleName.setError("name cant be empty!");
                } else if (et_MaleDob.getText().toString().equalsIgnoreCase("")) {
                    et_MaleDob.setError("dob cant be empty!");
                } else if (Util.checkDateSelection(15,et_MaleDob.getText().toString(),new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()),"yyyy-MM-dd")) {
                    Toast.makeText(HoroscopeMatchingActivity.this, "You are too younger to use this app", Toast.LENGTH_LONG).show();
                } else if (et_MaleTob.getText().toString().equalsIgnoreCase("")) {
                    et_MaleTob.setError("tob cant be empty!");
                } else if (et_MalePob.getText().toString().equalsIgnoreCase("")) {
                    et_MalePob.setError("district cant be empty!");
                } else if (et_FemaleName.getText().toString().equalsIgnoreCase("")) {
                    et_FemaleName.setError("name cant be empty!");
                } else if (et_FemaleDob.getText().toString().equalsIgnoreCase("")) {
                    et_FemaleDob.setError("dob cant be empty!");
                } else if (et_FemaleTob.getText().toString().equalsIgnoreCase("")) {
                    et_FemaleTob.setError("tob cant be empty!");
                } else if (et_FemalePob.getText().toString().equalsIgnoreCase("")) {
                    et_FemalePob.setError("district cant be empty!");
                }
                else{
                    showConfirmationDialog();
                   // postNewComment(SignUp.this);
                }

            }
        });
    }


    private void showConfirmationDialog(){
        TextView textViewName, textViewDOB, textViewTOB, textViewPOB, textViewGender, textViewCurrentLocation, textViewMobileNumber, textViewEmailAddress, textViewPassword, textViewPromoCode;
        ImageView imgCloseDialog;
        Button btnSubmit;
        String title = "Confirmation Dialog";
        final Dialog dialog = new Dialog(HoroscopeMatchingActivity.this);
        assert title != null;
        dialog.setContentView(R.layout.dialog_sign_up_confirmation);
        dialog.setTitle(title);

        imgCloseDialog = dialog.findViewById(R.id.imgClose);
        btnSubmit = dialog.findViewById(R.id.btnSubmit);

        textViewName = dialog.findViewById(R.id.tvName);
        textViewDOB = dialog.findViewById(R.id.tvDOB);
        textViewTOB = dialog.findViewById(R.id.tvTOB);
        textViewPOB = dialog.findViewById(R.id.tvPOB);
        textViewGender = dialog.findViewById(R.id.tvGender);
        textViewCurrentLocation = dialog.findViewById(R.id.tvCurrentLocation);
        textViewMobileNumber = dialog.findViewById(R.id.tvMobileNumber);
        textViewEmailAddress = dialog.findViewById(R.id.tvEmailAddress);
        textViewPassword = dialog.findViewById(R.id.tvPassword);
        textViewPromoCode = dialog.findViewById(R.id.tvPromocode);

        textViewName.setText(et_MaleName.getText().toString()+ "");
        textViewDOB.setText(et_MaleDob.getText().toString()+ "");
        textViewTOB.setText(et_MaleTob.getText().toString()+ "");
        textViewPOB.setText(et_MalePob.getText().toString()+ "");
        textViewGender.setText(gender+ "");

        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressDialog.show();
                postNewComment(HoroscopeMatchingActivity.this);
            }
        });

        dialog.show();
    }

    public void postNewComment(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, Api.SIGNUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response: ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.optBoolean("status")) {
                        //Intent intent = new Intent(getApplicationContext(), Email_base.class);
                        Util.showDialog(context, jsonObject.optString("message"), "Now, Please Verify Email First", true, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });

                    } else
                        Toast.makeText(context, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", et_MaleName.getText().toString().trim());
                params.put("time_of_birth", et_MaleTob.getText().toString().trim()/*.split(" ")[0]+":00"*/);
                params.put("place_of_birth", et_MalePob.getText().toString().trim());
                params.put("date_of_birth", et_MaleDob.getText().toString().trim());
                params.put("gender", gender);


                System.out.println("SignupContentName " +et_MaleName.getText().toString().trim());
                System.out.println("SignupContentTime with arrar " +et_MaleTob.getText().toString().split(" ")[0]+":00");
                System.out.println("SignupContentTime " +et_MaleTob.getText().toString());
                System.out.println("SignupContentPlace " +et_MalePob.getText().toString());
                System.out.println("SignupContentDOB " +et_MaleDob.getText().toString().trim());
                System.out.println("SignupContentGender " +gender);

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
                20*1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(sr);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("", "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
