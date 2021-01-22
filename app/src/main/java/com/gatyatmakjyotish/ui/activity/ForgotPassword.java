package com.gatyatmakjyotish.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.util.Util;

public class ForgotPassword extends AppCompatActivity {
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String  myforgot="forgot";
    Button reset,otp;
    EditText ed_email,edotp,newPassword,reTyped;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        sharedPreferences = getSharedPreferences(myforgot, Context.MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
        otp=findViewById(R.id.fbutton);
        reset=findViewById(R.id.save);
        ed_email=findViewById(R.id.email);
        edotp=findViewById(R.id.currentpassword);
        newPassword=findViewById(R.id.newpassword);
        reTyped=findViewById(R.id.retypepassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");

        Util.setupToolbar(this, toolbar, getString(R.string.resetPassword));

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_email.getText().toString().trim().equals("")||ed_email.getText().toString().trim()==null
                || !Patterns.EMAIL_ADDRESS.matcher(ed_email.getText().toString()).matches()){
                    ed_email.setError("Please enter a valid email");
                    return;
                }else{
                    //todo add otp api method
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edotp.getText().toString().trim().equals("")|| edotp.getText().toString().trim()==null){
                    edotp.setError("Enter a valid OTP");
                    return;
                }
                if (newPassword.getText().toString().trim().equals("")||newPassword.getText().toString().trim()==null
                ||newPassword.getText().toString().trim().length()<8){
                    newPassword.setError("Feild must be minimum 8 Characters");
                    return;
                }
                if (!reTyped.getText().toString().trim().equals(newPassword.getText().toString().trim())){
                    reTyped.setError("Password does not match");
                    return;
                }

                //todo check otp and then reset password
            }
        });

    }
}
