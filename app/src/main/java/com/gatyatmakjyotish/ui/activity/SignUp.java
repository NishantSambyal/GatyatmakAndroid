package com.gatyatmakjyotish.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import androidx.appcompat.widget.Toolbar;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    Button btn_SignUp;
    Context context;
    EditText et_Name, et_Dob, et_Tob, et_Pob, et_Loc, et_Mob, et_Email, et_Password, et_Cpass, et_promocode;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    private ProgressDialog progressDialog;
    RadioGroup radioGroup;
    RadioButton male, female;
    Toolbar toolbar;
    String gender = "male";
    private BreakIterator error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolbar);

        Util.setupToolbar(this, toolbar, getString(R.string.sign_up_activity_title));

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        et_Name = findViewById(R.id.et_Name);
        et_Dob = findViewById(R.id.et_Dob);
        et_Tob = findViewById(R.id.et_Tob);
        et_Pob = findViewById(R.id.et_Pob);
        et_Loc = findViewById(R.id.et_Loc);
        et_Mob = findViewById(R.id.et_Mob);
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        et_Cpass = findViewById(R.id.et_Cpass);
        et_promocode = findViewById(R.id.et_promocode);
        btn_SignUp = findViewById(R.id.btn_SignUp);
        radioGroup = findViewById(R.id.radio);
        male = findViewById(R.id.rd_Male);
        female = findViewById(R.id.rd_Female);
        /*et_Pob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(SignUp.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });*/

        et_Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpDatePickerDOB(et_Dob, SignUp.this).show();
            }
        });

        et_Dob.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (Util.checkDateSelection(15,et_Dob.getText().toString(),new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()),"yyyy-MM-dd")) {
                    Toast.makeText(SignUp.this, "You are too younger to use this app", Toast.LENGTH_LONG).show();
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
                  Util.setUpTimePicker(et_Tob, SignUp.this).show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                gender = rb.getText().toString();
            }
        });
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                if (et_Name.getText().toString().trim().equalsIgnoreCase("")) {
                    et_Name.setError("name cant be empty!");
                } else if (et_Dob.getText().toString().trim().equalsIgnoreCase("")) {
                    et_Dob.setError("dob cant be empty!");
                } else if (Util.checkDateSelection(15,et_Dob.getText().toString().trim(),new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()),"yyyy-MM-dd")) {
                    Toast.makeText(SignUp.this, "You are too younger to use this app", Toast.LENGTH_LONG).show();
                } else if (et_Tob.getText().toString().trim().equalsIgnoreCase("")) {
                    et_Tob.setError("tob cant be empty!");
                } else if (et_Pob.getText().toString().trim().equalsIgnoreCase("")) {
                    et_Pob.setError("district cant be empty!");
                } else if (et_Loc.getText().toString().trim().equalsIgnoreCase("")) {
                    et_Loc.setError("loc cant be empty!");
                } else if (et_Mob.getText().toString().trim().equalsIgnoreCase("")) {
                    et_Mob.setError("mob cant be empty!");
                } else if (et_Password.getText().toString().trim().equalsIgnoreCase("")) {
                    et_Password.setError("password cant be empty!");
                } else if (et_Cpass.getText().toString().trim().equalsIgnoreCase("")) {
                    et_Cpass.setError("confirm password cant be empty!");
                } else if (et_Email.getText().toString().trim().equalsIgnoreCase("") || !Patterns.EMAIL_ADDRESS.matcher(et_Email.getText().toString()).matches()) {
                    et_Email.setError("invalid email address");
                } else if (!et_Password.getText().toString().trim().equals(et_Cpass.getText().toString())) {
                    Toast.makeText(SignUp.this, "password not matching", Toast.LENGTH_LONG).show();
                } else if (et_Password.getText().toString().trim().length() < 8) {
                    Toast.makeText(SignUp.this, "password should be of minimum 8 characters", Toast.LENGTH_LONG).show();
                } else{
                    showConfirmationDialog();
                   // postNewComment(SignUp.this);
                }

//                else if(et_promocode.getText().toString().equalsIgnoreCase("")) {
//                    et_promocode.setError("Please enter promocode to Signup");
//                }
            }
        });
    }


    private void showConfirmationDialog(){
        TextView textViewName, textViewDOB, textViewTOB, textViewPOB, textViewGender, textViewCurrentLocation, textViewMobileNumber, textViewEmailAddress, textViewPassword, textViewPromoCode;
        ImageView imgCloseDialog;
        Button btnSubmit;
        String title = "Confirmation Dialog";
        final Dialog dialog = new Dialog(SignUp.this);
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

        textViewName.setText(et_Name.getText().toString()+ "");
        textViewDOB.setText(et_Dob.getText().toString()+ "");
        textViewTOB.setText(et_Tob.getText().toString()+ "");
        textViewPOB.setText(et_Pob.getText().toString()+ "");
        textViewGender.setText(gender+ "");
        textViewCurrentLocation.setText(et_Loc.getText().toString()+ "");
        textViewMobileNumber.setText(et_Mob.getText().toString()+ "");
        textViewEmailAddress.setText(et_Email.getText().toString()+ "");
        textViewPassword.setText(et_Password.getText().toString()+ "");
        textViewPromoCode.setText(et_promocode.getText().toString()+ "");

        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressDialog.show();
                postNewComment(SignUp.this);
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
                        Util.showDialog(context, jsonObject.optString("message"), "Now, Please Verify Email First\nIf you do not receive the confirmation message within a few minutes of signing up, please check your Spam folder", true, new DialogInterface.OnClickListener() {
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
                params.put("name", et_Name.getText().toString().trim());
                params.put("password", et_Password.getText().toString().trim());
                params.put("time_of_birth", et_Tob.getText().toString().trim()/*.split(" ")[0]+":00"*/);
                params.put("place_of_birth", et_Pob.getText().toString().trim());
                params.put("current_place", et_Loc.getText().toString().trim());
                params.put("email", et_Email.getText().toString().trim());
                params.put("date_of_birth", et_Dob.getText().toString().trim());
                params.put("gender", gender);
                params.put("mobile", et_Mob.getText().toString().trim());
                if(et_promocode.getText().toString().equalsIgnoreCase(""))
                    params.put("promo_code", "");
                else
                    params.put("promo_code", et_promocode.getText().toString().trim());


//                System.out.println("SignupContentName " +et_Name.getText().toString().trim());
//                System.out.println("SignupContentPassword " +et_Password.getText().toString().trim());
//                System.out.println("SignupContentTime with arrar " +et_Tob.getText().toString().split(" ")[0]+":00");
//                System.out.println("SignupContentTime " +et_Tob.getText().toString());
//                System.out.println("SignupContentPlace " +et_Pob.getText().toString());
//                System.out.println("SignupContentLocation " +et_Loc.getText().toString().trim());
//                System.out.println("SignupContentEmail " +et_Email.getText().toString().trim());
//                System.out.println("SignupContentDOB " +et_Dob.getText().toString().trim());
//                System.out.println("SignupContentGender " +gender);
//                System.out.println("SignupContentMobile " +et_Mob.getText().toString().trim());

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
