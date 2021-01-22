package com.gatyatmakjyotish.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import static com.gatyatmakjyotish.constants.Constants.ID;
import static com.gatyatmakjyotish.constants.Constants.LOGIN_PREF;

public class ChangePassword extends AppCompatActivity {
    private Button save;
    private EditText currentpassword, newpassword, retypepassword;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        sharedPreferences = getApplicationContext().getSharedPreferences(LOGIN_PREF, 0); // 0 - for private mode
        currentpassword = findViewById(R.id.currentpassword);
        newpassword = findViewById(R.id.newpassword);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbarWithoutBack(this, toolbar, getString(R.string.changepassword));
        retypepassword = findViewById(R.id.retypepassword);
        save = findViewById(R.id.save);
    /*    progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
*/

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentpassword.getText().toString().trim().isEmpty()) {
                    currentpassword.setError("Feild must be minimum 8 Characters");
                    return;
                    //Toast.makeText(ChangePassword.this, "Field must not be empty", Toast.LENGTH_SHORT).show();
                } else if (newpassword.getText().toString().trim().length()<8) {
                //    Toast.makeText(ChangePassword.this, "Field must be of minimum 8 characters", Toast.LENGTH_SHORT).show();
                    newpassword.setError("Feild must be minimum 8 Characters");
                    return;
                } else if (!retypepassword.getText().toString().trim().equals(newpassword.getText().toString().trim())) {
              //      Toast.makeText(ChangePassword.this, "Field must be of minimum 8 characters", Toast.LENGTH_SHORT).show();
                    retypepassword.setError("Passwords do not match");
                    return;
                } else {
                    postNewComment();

                }
            }
        });

    }

    private void postNewComment() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Changing Password ...");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        Api.CHANGEPASSWORD_API = Api.BASE_URL + Api.USER + sharedPreferences.getString(ID, "") + Api.CHANGE_PASSWORD;
        StringRequest sr = new StringRequest(Request.Method.POST, Api.CHANGEPASSWORD_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        Toast.makeText(ChangePassword.this, "" + "Password Changed Successfully",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), FrontScreen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ChangePassword.this, "" + jsonObject.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("response: ", response);

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
                params.put("current_password", currentpassword.getText().toString().trim());
                params.put("password", newpassword.getText().toString().trim());
                params.put("confirm_password", retypepassword.getText().toString().trim());

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

