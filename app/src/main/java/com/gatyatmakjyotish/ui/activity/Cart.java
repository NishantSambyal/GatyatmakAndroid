package com.gatyatmakjyotish.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.gatyatmakjyotish.BuildConfig;
import com.gatyatmakjyotish.ModelClass.PublishModel;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.CartAdapter;
import com.gatyatmakjyotish.constants.Api;
import com.gatyatmakjyotish.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.gatyatmakjyotish.constants.Constants.EMAIL;
import static com.gatyatmakjyotish.constants.Constants.ID;
import static com.gatyatmakjyotish.constants.Constants.LOGIN_PREF;
import static com.gatyatmakjyotish.constants.Constants.MOBILE;
import static com.gatyatmakjyotish.constants.Constants.NAME;
import static com.gatyatmakjyotish.constants.Constants.PROMOCODE;
import static com.gatyatmakjyotish.constants.Constants.WALLET_POINT;

public class Cart extends AppCompatActivity implements PaymentResultListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    Button button, cart, cart_ok, cart_cancel;
    ImageView imageView;
    List<PublishModel> publishModelList = new ArrayList<>();
    public static int total = 0;
    SharedPreferences sharedPreferences, loginSharedPreferences;
    SharedPreferences.Editor editor;
    public static final String mypreference = "mypref";
    LinearLayout empty_cart, total_layout;
    private ProgressDialog progressDialog;
    TextView tv_price;
    CartAdapter cartAdapter;

    Button btn_proceed;
    Context context;
    EditText et_Name, et_Dob, et_Tob, et_Pob, et_Loc, et_Mob;
    Button btnSubmit;
    EditText et_MaleName, et_MaleDob, et_MaleTob, et_MalePob, et_FemaleName, et_FemaleDob, et_FemaleTob, et_FemalePob;

    TextView tv_gatyatmakpoints_share, tv_name, tv_email, tv_gatyatmakpoints, tv_subtotal, tv_tax_amount, tv_final_total, tv_user_wallet, tv_total_amount;
    float final_total = 0;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    float subtotal = 0;
    int gst = 18;
    int p_width = 0;
    float amount_percentage;
    float wallet_points = 0,tv_finalwallet_amount = 0;
    private Boolean referenceStatus = false;
    private String cartType = "";
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private Boolean horoscopeMatching = false;
    private View viewMain, view2;
    private int pageClick = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMain = getLayoutInflater().inflate(R.layout.cart, null);
        setContentView(viewMain);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        loginSharedPreferences = getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        cart = findViewById(R.id.cart);
        toolbar = findViewById(R.id.toolbar);
        tv_price = findViewById(R.id.tv_price);
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recycler_cart);

        Util.setLinearLayoutManagerNestedScroll(this, recyclerView);
        imageView = findViewById(R.id.image_cart);
        empty_cart = findViewById(R.id.cart_empty);
        total_layout = findViewById(R.id.total_layout);
        Util.setupToolbar(this, toolbar, getString(R.string.cart));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(getIntent().hasExtra("reference"))
            referenceStatus = true;


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Books.class);
                intent.putExtra("Service","Publications");
                startActivity(intent);
                finish();
            }
        });
        getFreshCartItems();

        if (publishModelList != null) {
            cartAdapter = new CartAdapter(publishModelList, new CartAdapter.OnClick() {
                @Override
                public void onClick(int position) {
                    cartAdapter.deleteCartItem(position);
                    calculateTotal();
                    getFreshCartItems();
                }
            });
            recyclerView.setAdapter(cartAdapter);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  progressDialog = new ProgressDialog(Cart.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();*/
                // finish();

                if(empty_cart.getVisibility() == View.VISIBLE){
                    Util.showDialog(Cart.this,"Cart Empty", "Please select at least one service.", false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
                else{
                    if(sharedPreferences.getBoolean("dailyStatus",false) || sharedPreferences.getBoolean("yearlyStatus",false)){
                         paymentOrderDetails();
                    }
                    else {
                        if (getIntent().getExtras() != null) {
                            int p = getIntent().getExtras().getInt("proceedWith");
                            p_width = p;
                            if (p == 1) {
                                paymentOrderDetails();

                            } else if (p == 0) {
                                newActivity(p);
                            }
                        } else
                            paymentOrderDetails();
                            //startPayment();
                    }

                  }


                /*if(getIntent().getExtras() != null){
                    int p=getIntent().getExtras().getInt("proceedWith");
                    if (p==1){
                        newActivity(p);

                    }else if (p==0){
                        newActivity(p);
                    }
                }
                else
                    startPayment();*/

            }
        });
    }

    public void paymentOrderDetails(){
        setContentView(R.layout.paymentorder);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, "Order Details");

        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        loginSharedPreferences = getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);

        float tax = 0;

       // loginSharedPreferences = getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);

        tv_gatyatmakpoints_share = findViewById(R.id.tv_gatyatmakpoints_share);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_email.setVisibility(View.GONE);
        tv_gatyatmakpoints = findViewById(R.id.tv_gatyatmakpoints_amount);
        tv_subtotal = findViewById(R.id.tv_subtotal_amount);
        tv_user_wallet = findViewById(R.id.tv_used_wallet_amount);
        tv_final_total = findViewById(R.id.tv_final_amount);
        tv_tax_amount = findViewById(R.id.tv_tax_amount);
        tv_total_amount = findViewById(R.id.tv_total_amount);

        cart_ok = findViewById(R.id.btn_ok);
        cart_cancel = findViewById(R.id.btn_cancel);

        subtotal = calculateTotal();

        //tax = (subtotal * gst)/100;

        //final_total = subtotal + tax;

        //editor = loginSharedPreferences.edit();

        wallet_points = Float.parseFloat(loginSharedPreferences.getString(WALLET_POINT, "0"));
        amount_percentage = (wallet_points*50)/100;
        float half_of_subtotal = (subtotal*50)/100;
//        amount_percentage = (subtotal*50)/100;

        if (amount_percentage <= half_of_subtotal){
            final_total = subtotal - amount_percentage;
            tv_finalwallet_amount = wallet_points = amount_percentage;
            tv_user_wallet.setText(tv_finalwallet_amount+"");
        }else {
            final_total = subtotal - half_of_subtotal;
            tv_finalwallet_amount = wallet_points = half_of_subtotal;
            tv_user_wallet.setText(tv_finalwallet_amount+"");
        }

        /*if(wallet_points >= amount_percentage){
            wallet_points = wallet_points - amount_percentage;
            final_total = subtotal - amount_percentage;
            tv_finalwallet_amount = amount_percentage;
            tv_user_wallet.setText(tv_finalwallet_amount+"");
        }
        else{
            final_total = subtotal - wallet_points;
            tv_finalwallet_amount = wallet_points;
            if(tv_finalwallet_amount == 0)
                tv_user_wallet.setText(tv_finalwallet_amount+"");
            else
                tv_user_wallet.setText("-"+tv_finalwallet_amount+"");
            wallet_points = 0;
        }
        */
        float gst_amount = (final_total*gst)/100;

        if(sharedPreferences.getBoolean("dailyStatus",false) || sharedPreferences.getBoolean("yearlyStatus",false))
            tv_name.setText(loginSharedPreferences.getString(NAME, ""));

        tv_gatyatmakpoints.setText(loginSharedPreferences.getString(WALLET_POINT, "0"));
        tv_subtotal.setText(subtotal+"");
        tv_final_total.setText((final_total)+"");
        tv_tax_amount.setText(gst_amount+"");
        final_total = final_total + gst_amount;
        String s_final_total = String.format(java.util.Locale.US,"%.2f", final_total);
        tv_total_amount.setText(s_final_total+"");

        try{
            if (!TextUtils.isEmpty(et_Name.getText().toString().trim())){
                tv_name.setText(et_Name.getText().toString());
            }else {
                tv_name.setText(loginSharedPreferences.getString(NAME, ""));
            }
        }catch (Exception e){
            tv_name.setText(loginSharedPreferences.getString(NAME, ""));
        }

        cart_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

        cart_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_gatyatmakpoints_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });


    }

    public void newActivity(int p){
        setContentView(R.layout.activity_before_payment);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, "Proceed to Payment");


       /* progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();*/
        et_Name = findViewById(R.id.et_Name);
        et_Dob = findViewById(R.id.et_Dob);
        et_Tob = findViewById(R.id.et_Tob);
        et_Pob = findViewById(R.id.et_Pob);
        et_Loc = findViewById(R.id.et_Loc);
        et_Mob = findViewById(R.id.et_Mob);

        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        loginSharedPreferences = getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
        /*et_Name.setText( loginSharedPreferences.getString(NAME, ""));
        et_Pob.setText(loginSharedPreferences.getString(PLACE_OF_BIRTH, ""));
        et_Loc.setText(loginSharedPreferences.getString(CURRENT_PLACE, ""));
        et_Tob.setText(loginSharedPreferences.getString(TIME_OF_BIRTH, ""));
        et_Mob.setText(loginSharedPreferences.getString(MOBILE, ""));
        et_Dob.setText(loginSharedPreferences.getString(DATE_OF_BIRTH, ""));*/

        Bundle bundle=getIntent().getExtras();

        if (p==0){
            et_Name.setEnabled(true);
            et_Pob.setVisibility(View.GONE);
            et_Dob.setVisibility(View.GONE);
            et_Tob.setVisibility(View.GONE);
            et_Mob.setEnabled(true);
            et_Loc.setEnabled(true);

            /*et_Pob.setEnabled(false);
            et_Dob.setEnabled(false);
            et_Tob.setEnabled(false);*/
        }else if (p==1){
            et_Name.setEnabled(true);
            et_Pob.setEnabled(true);
            et_Dob.setEnabled(true);
            et_Tob.setEnabled(true);

            et_Mob.setVisibility(View.GONE);
            et_Loc.setVisibility(View.GONE);
            /*et_Mob.setEnabled(false);
            et_Loc.setEnabled(false);
            et_Dob.setEnabled(true);
            et_Tob.setEnabled(true);*/
        }

        et_Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpDatePicker(et_Dob, Cart.this).show();
            }
        });

        et_Tob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpTimePicker(et_Tob, Cart.this).show();
            }
        });

        btn_proceed=(Button) findViewById(R.id.btn_proceed);
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                if(p_width == 1){
                    if (et_Name.getText().toString().equalsIgnoreCase("")) {
                        et_Name.setError("name cant be empty!");
                    } else if (et_Dob.getText().toString().equalsIgnoreCase("")) {
                        et_Dob.setError("dob cant be empty!");
                    }  else if (et_Tob.getText().toString().equalsIgnoreCase("")) {
                        et_Tob.setError("tob cant be empty!");
                    } else if (et_Pob.getText().toString().equalsIgnoreCase("")) {
                        et_Pob.setError("district cant be empty!");
                    }
                    else{
                        if(horoscopeMatching)
                            startHoroscopeMatchingActivity();
                        else
                            paymentOrderDetails();
                    }

                }
                else {
                    if (et_Name.getText().toString().equalsIgnoreCase("")) {
                        et_Name.setError("name cant be empty!");
                    }else if (et_Loc.getText().toString().equalsIgnoreCase("")) {
                        et_Loc.setError("loc cant be empty!");
                    } else if (et_Mob.getText().toString().equalsIgnoreCase("")) {
                        et_Mob.setError("mob cant be empty!");
                    }
                    else
                        paymentOrderDetails();
                }
            }
        });
    }

    private void startHoroscopeMatchingActivity(){
        setContentView(R.layout.activity_horoscope_matching);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(Cart.this, toolbar, getString(R.string.horoscope_matching));

        et_MaleName = findViewById(R.id.et_MaleName);
        et_FemaleName = findViewById(R.id.et_FemaleName);
        et_MaleDob = findViewById(R.id.et_MaleDob);
        et_FemaleDob = findViewById(R.id.et_FemaleDob);
        et_MaleTob = findViewById(R.id.et_MaleTob);
        et_FemaleTob = findViewById(R.id.et_FemaleTob);
        et_MalePob = findViewById(R.id.et_MalePob);
        et_FemalePob = findViewById(R.id.et_FemalePob);
        btnSubmit = findViewById(R.id.btn_SignUp);

        et_MaleDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpDatePicker(et_MaleDob, Cart.this).show();
            }
        });

        et_FemaleDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpDatePicker(et_FemaleDob, Cart.this).show();
            }
        });

        et_MaleDob.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (Util.checkDateSelection(15,et_MaleDob.getText().toString(),new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()),"yyyy-MM-dd")) {
                    Toast.makeText(Cart.this, "You are too younger to use this app", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Cart.this, "You are too younger to use this app", Toast.LENGTH_LONG).show();
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
                Util.setUpTimePicker(et_MaleTob, Cart.this).show();
            }
        });

        et_FemaleTob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setUpTimePicker(et_FemaleTob, Cart.this).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                if (et_MaleName.getText().toString().equalsIgnoreCase("")) {
                    et_MaleName.setError("name cant be empty!");
                } else if (et_MaleDob.getText().toString().equalsIgnoreCase("")) {
                    et_MaleDob.setError("dob cant be empty!");
                } else if (Util.checkDateSelection(15,et_MaleDob.getText().toString(),new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()),"yyyy-MM-dd")) {
                    Toast.makeText(Cart.this, "You are too younger to use this app", Toast.LENGTH_LONG).show();
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
                    paymentOrderDetails();
                }

            }
        });



    }

    private void getFreshCartItems() {
        Type type = new TypeToken<List<PublishModel>>() {
        }.getType();
        System.out.println("cartitemdetails " +sharedPreferences.getString("cart", ""));
        publishModelList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);
        if (publishModelList != null && publishModelList.size() > 0) {
            calculateTotal();
            empty_cart.setVisibility(View.GONE);
        } else {
            total_layout.setVisibility(View.GONE);
            empty_cart.setVisibility(View.VISIBLE);
        }
    }

    public int calculateTotal() {
        int total = 0;
        for (PublishModel publishModel : publishModelList) {
            total += publishModel.getPrice();
        }
        tv_price.setText("Rs "+ String.valueOf(total));

        if(publishModelList.size() == 1){
            for (PublishModel publishModel : publishModelList) {
                if(publishModel.getPublish().toString().equalsIgnoreCase("2131820753")){
                    horoscopeMatching = true;
                }
            }
        }


        return total;
    }

    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        final Checkout co = new Checkout();
        co.setKeyID("rzp_live_hGygYqXHwnxCQg");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", getResources().getString(R.string.app_name));
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");

            //options.put("amount", calculateTotal() * 100);
          //  options.put("amount", final_total * 100);
//            options.put("amount", 1 * 100);
            options.put("amount", final_total * 100);


            JSONObject preFill = new JSONObject();
            preFill.put("email", loginSharedPreferences.getString(EMAIL, ""));
            preFill.put("contact", loginSharedPreferences.getString(MOBILE, ""));
            options.put("prefill", preFill);
            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

        if(sharedPreferences.getBoolean("dailyStatus",false) || sharedPreferences.getBoolean("yearlyStatus",false)){
            if(sharedPreferences.getBoolean("dailyStatus",false)) cartType = "1";
            if(sharedPreferences.getBoolean("yearlyStatus",false)) cartType = "2";
            if(sharedPreferences.getBoolean("dailyStatus",false) && sharedPreferences.getBoolean("yearlyStatus",false)) cartType = "3";
        }
        else {
            cartType = "publication";
        }





        Bundle bundle = new Bundle();
        bundle.putString("user_id",loginSharedPreferences.getString(ID,""));
        bundle.putString("transaction_id",razorpayPaymentID);
        bundle.putString("type",cartType);
        bundle.putString("amount",subtotal+"");
        bundle.putString("gst",gst+"");
        bundle.putString("wallet_point",tv_finalwallet_amount+"");
        bundle.putString("final_amount",final_total+"");

        if(!cartType.equalsIgnoreCase("publication"))
            bundle.putString("description",getCartDetails());
        else{
            bundle.putString("product",getCartDetails());
            bundle.putString("product_user",getUserDetails());
            if(horoscopeMatching)
                bundle.putString("kundli_user",getKundliDetails());
        }

        Intent intent = new Intent(getApplicationContext(), Payment.class);
        intent.putExtras(bundle);
        startActivity(intent);
      //  Toast.makeText(this, "Payment successfully done! " + razorpayPaymentID, Toast.LENGTH_SHORT).show();


        editor.putString("cart", "");
        editor.putBoolean("dailyStatus", false);
        editor.putBoolean("yearlyStatus", false);
        editor.commit();

        editor = loginSharedPreferences.edit();
        editor.putString(WALLET_POINT,wallet_points+"");
        editor.commit();


//        if(sharedPreferences.getBoolean("dailyStatus",false) || sharedPreferences.getBoolean("yearlyStatus",false)){
//            System.out.println("CartDetailsDescription -> " + getCartDetails());
//        }
//        else {
//            System.out.println("CartDetailsAmountAndDescriptionObject -> " + getCartDetails());
//        }



       // postPaymentDetails(Cart.this, razorpayPaymentID, cartType, getCartDetails());


    }

    public String getUserDetails(){
        JsonObject jsonObject = new JsonObject();
        if(p_width == 1){
            jsonObject.addProperty("name",et_Name.getText().toString());
            jsonObject.addProperty("dob",et_Dob.getText().toString());
            jsonObject.addProperty("tob",et_Tob.getText().toString());
            jsonObject.addProperty("pob",et_Pob.getText().toString());
            jsonObject.addProperty("address","");
            jsonObject.addProperty("mobile","");
        }
        else {
            jsonObject.addProperty("name",et_Name.getText().toString());
            jsonObject.addProperty("dob","");
            jsonObject.addProperty("tob","");
            jsonObject.addProperty("pob","");
            jsonObject.addProperty("address",et_Loc.getText().toString());
            jsonObject.addProperty("mobile",et_Mob.getText().toString());
        }

        return  new Gson().toJson(jsonObject);
    }

    public String getKundliDetails(){
        JsonObject jsonObject = new JsonObject();

        JsonObject jsonMailObject = new JsonObject();
        jsonMailObject.addProperty("name",et_MaleName.getText().toString());
        jsonMailObject.addProperty("gender","Mail");
        jsonMailObject.addProperty("dob",et_MaleDob.getText().toString());
        jsonMailObject.addProperty("timeofbirth",et_MaleTob.getText().toString());
        jsonMailObject.addProperty("placeofbirth",et_MalePob.getText().toString());

        JsonObject jsonFemaleObject = new JsonObject();
        jsonFemaleObject.addProperty("name",et_FemaleName.getText().toString());
        jsonFemaleObject.addProperty("gender","Female");
        jsonFemaleObject.addProperty("dob",et_FemaleDob.getText().toString());
        jsonFemaleObject.addProperty("timeofbirth",et_FemaleTob.getText().toString());
        jsonFemaleObject.addProperty("placeofbirth",et_FemalePob.getText().toString());

        jsonObject.addProperty("mail",jsonMailObject.toString());
        jsonObject.addProperty("female",jsonFemaleObject.toString());

        return new Gson().toJson(jsonObject);
    }

    @Override
    public void onPaymentError(int code, String response) {
//        try {
//            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Log.e("OnPaymentError", "Exception in onPaymentError", e);
//        }
        callApiForPaymentFailure(response);
    }

    private String getCartDetails(){
        Type type = new TypeToken<List<PublishModel>>() {}.getType();
        publishModelList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);
        String returnString;

        if(sharedPreferences.getBoolean("dailyStatus",false) || sharedPreferences.getBoolean("yearlyStatus",false)){
            String description = "";
            for(int i = 0; i < publishModelList.size() ; i++){
                PublishModel publishModel = publishModelList.get(i);
                description += String.valueOf(Html.fromHtml(getResources().getString(publishModel.getPublish()))) + "+";
            }
            returnString = description;
        }
        else {
            JsonObject jsonObject = new JsonObject();
            JsonArray objectArray = new JsonArray();
            for(int i = 0; i < publishModelList.size() ; i++){
                PublishModel publishModel = publishModelList.get(i);
                jsonObject.addProperty("amount",publishModel.getPrice());
                jsonObject.addProperty("description",String.valueOf(Html.fromHtml(getResources().getString(publishModel.getPublish()))));
                objectArray.add(jsonObject);
            }

            returnString = new Gson().toJson(objectArray);
        }
       return returnString;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        //finishActivty();
//        return super.onSupportNavigateUp();
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(referenceStatus){
            /*System.out.println("CartExistinthisActivity");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cart", "");
            editor.commit();*/
        }
        if(pageClick == 1)
            finish();
    }

    private void callApiForPaymentFailure(String resp){
        progressDialog = new ProgressDialog(Cart.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String desc = "payment failed";
        try{
            desc = new JSONObject(resp).getJSONObject("error").getString("description");
        }catch (Exception e){}

        Map<String, String> postParams = new HashMap<>();
        postParams.put("user_id",loginSharedPreferences.getString(ID,""));
        postParams.put("transaction_id","");
        postParams.put("type","service");
        postParams.put("amount",subtotal+"");
        postParams.put("gst",gst+"");
        postParams.put("status","0");

        postParams.put("description", desc);
//        postParams.put("wallet_point",tv_finalwallet_amount+"");
//        postParams.put("final_amount",final_total+"");

            postParams.put("product",getCartDetails());
            postParams.put("product_user",getUserDetails());

        AndroidNetworking.post(Api.PAYMENT_FAILED_API)
                .addBodyParameter(postParams)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            boolean status = response.getBoolean("status");
                            if (status == true){
                                Toast.makeText(Cart.this, "Payment detail submitted", Toast.LENGTH_SHORT).show();
                            }else {
                                showDialog("Please, Try again!!", resp);
                            }
//                            Toast.makeText(Cart.this, "Payment failed respon", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            showDialog("Please, Try again!!", resp);
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressDialog.dismiss();
                        showDialog("Please, Try again!!", resp);
                    }
                });
    }

    private void showDialog(String msg, String response){
        new AlertDialog.Builder(this)
                .setMessage(msg)
//                .setMessage("Payment failed!\nPlease, Try again!!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callApiForPaymentFailure(response);
                    }
                })
                .show();
    }


    public void shareApp(){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            /*String shareMessage= "\nI recommend you this application, download this for accurate\n" +
                    "time-bound  day-to-day and yearly forecast for free and get points for\n" +
                    "availing services of Gatyatmak Jyotish, a new branch in Astrology.\n\n";*/
            String shareMessage= "\n"+getString(R.string.share_app)+"\n\n"+"Use Promo Code " +loginSharedPreferences.getString(PROMOCODE,"")+"\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("CartDestroyActivity");
    }

    private void finishActivty() {
        finish();
    }

}


