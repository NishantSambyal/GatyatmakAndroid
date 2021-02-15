package com.gatyatmakjyotish.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Payment extends AppCompatActivity {
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    Button btn;
    String serviceType = "";
    String user_id = "", transaction_id = "", type = "", amount = "",gst = "", wallet_point = "", final_amount = "", description = "", product = "", product_user = "";
    String API = "";
    String kundli_user = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        btn=findViewById(R.id.click);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, getString(R.string.PaymentDetails));
        Intent intent = getIntent();
        if(intent != null){
            Bundle extras = getIntent().getExtras();
            if (extras != null){
                user_id = extras.getString("user_id");
                transaction_id = extras.getString("transaction_id");
                type = extras.getString("type");
                amount = extras.getString("amount");
                gst = extras.getString("gst");
                wallet_point = extras.getString("wallet_point");
                final_amount = extras.getString("final_amount");
                if(type.equalsIgnoreCase("1") || type.equalsIgnoreCase("2") || type.equalsIgnoreCase("3")){
                    serviceType = "day-yearly";
                    description = extras.getString("description");
                    API = Api.PAYMENT_API;
                }
                else {
                    serviceType = "publication";
                    product = extras.getString("product");
                    product_user = extras.getString("product_user");
                    API = Api.ORDER_API;
                    try{
                        if(extras.containsKey("kundli_user")){
                            if(!extras.getString("kundli_user").equals(null)){
                                kundli_user = extras.getString("kundli_user");
                            }
                            else
                             kundli_user = "";
                        }
                        else
                            kundli_user = "";
                    }catch(Exception e) {
                        kundli_user = "";
                    }
                }
                System.out.println("CartDetails_UserID " +user_id);
                System.out.println("CartDetails_TransactionID " +transaction_id);
                System.out.println("CartDetails_Type " +type);
                System.out.println("CartDetails_Amount " +amount);
                System.out.println("CartDetails_GST " +gst);
                System.out.println("CartDetails_Wallet_Point " +wallet_point);
                System.out.println("CartDetails_FinalAmount " +final_amount);
                if(type.equalsIgnoreCase("1") || type.equalsIgnoreCase("2") || type.equalsIgnoreCase("3"))
                    System.out.println("CartDetails_Description " +description);
                else{
                    System.out.println("CartDetails_Product " +product);
                    System.out.println("CartDetails_Product_User " +product_user);
                }

            }
            else
                System.out.println("PaymentDetailsNull");
           // Bundle extras = intent.getExtras();
           // System.out.println("PaymentDetails1 " + extras.getString("user_id"));
        }
        postPaymentDetails(Payment.this,"232323","eeweweew","2323232334");
        //editor = sharedPreferences.edit();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),FrontScreen.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void postPaymentDetails(final Context context, final String razorpayPaymentID, final String cartType, final String cartDeatils) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(serviceType.equalsIgnoreCase("day-yearly")){
                        boolean status = jsonObject.getBoolean("status");
                        if (status) {
                            JSONObject objectDetails = jsonObject.getJSONObject("object");
                            System.out.println("Details " +objectDetails.getString("transaction_id"));
                            Toast.makeText(context,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(context,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
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
                params.put("status", "1");
                //params.put("user_id","1");
                params.put("user_id",user_id);
                params.put("transaction_id",transaction_id);
                params.put("type", type);
                if(serviceType.equalsIgnoreCase("day-yearly"))
                    params.put("amount", amount);
                else
                    params.put("transaction_amount", amount);
                params.put("gst", gst);
                params.put("wallet_point", wallet_point);
                params.put("final_amount", final_amount);
                if(serviceType.equalsIgnoreCase("day-yearly"))
                    params.put("description", description);
                else{
                    params.put("product", product);
                    params.put("product_user", product_user);
                    if(!kundli_user.equalsIgnoreCase(""))
                        params.put("kundli_user", kundli_user);
                }

                System.out.println("getorderdetailsentry " +params);
                return params;
            }

        };
        queue.add(sr);

    }

    }
