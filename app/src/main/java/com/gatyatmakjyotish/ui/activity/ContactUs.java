package com.gatyatmakjyotish.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.gatyatmakjyotish.constants.Constants.EMAIL;
import static com.gatyatmakjyotish.constants.Constants.LOGIN_PREF;
import static com.gatyatmakjyotish.constants.Constants.NAME;

public class ContactUs extends AppCompatActivity {

    private TextView text;
    EditText name,email,message,subject;
    SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;
    Button button;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar,"Contact us");
        text=findViewById(R.id.text);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        message=findViewById(R.id.message);
        subject=findViewById(R.id.subject);
        button=findViewById(R.id.contact);
        sharedPreferences = getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        String nameStr = sharedPreferences.getString(NAME, "");
        String emailStr = sharedPreferences.getString(EMAIL, "");
        name.setText(nameStr);
        email.setText(emailStr);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equalsIgnoreCase("")) {
                    name.setError("Name cant be empty!");
                }else if (email.getText().toString().equalsIgnoreCase("") || !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Invalid email address");
                }else if (subject.getText().toString().equalsIgnoreCase("")) {
                    subject.setError("Subject cant be empty!");
                }else if (message.getText().toString().equalsIgnoreCase("")) {
                    message.setError("Message cant be empty!");
                }else{
                    contactRequest();
                }
            }
        });

        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbarWithoutBack(this, toolbar, getString(R.string.Contactus));
    }


    private void contactRequest() {
        progressDialog = new ProgressDialog(ContactUs.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(ContactUs.this);
        StringRequest sr = new StringRequest(Request.Method.POST, Api.CONTACT_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optBoolean("status")){
                        //Toast.makeText(ContactUs.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        Util.showDialog(ContactUs.this, "Successfully", jsonObject.optString("message"), true, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name.getText().toString());
                params.put("email", email.getText().toString());
                params.put("subject", subject.getText().toString());
                params.put("message", message.getText().toString());
                return params;

            }

        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
