package com.gatyatmakjyotish.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.constants.Constants;
import com.gatyatmakjyotish.ui.fragment.FragmentNavigationDrawer;
import com.gatyatmakjyotish.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hotchemi.android.rate.AppRate;

import static com.gatyatmakjyotish.constants.Constants.DATE_OF_BIRTH;
import static com.gatyatmakjyotish.constants.Constants.EMAIL;
import static com.gatyatmakjyotish.constants.Constants.LANGUAGE;
import static com.gatyatmakjyotish.constants.Constants.LOGIN_PREF;
import static com.gatyatmakjyotish.constants.Constants.NAME;
import static com.gatyatmakjyotish.constants.Constants.PASSWORD;
import static com.gatyatmakjyotish.constants.Constants.TOKEN;

public class FrontScreen extends AppCompatActivity {
   LinearLayout click, signup;
   // private android.support.v7.widget.Toolbar toolbar;
   // private Toolbar toolbar;
    //private DrawerLayout mDrawerLayout;
    private LinearLayout li_bdday,horoscope,dailyforecast,yearlyforecast,consulation,publications,compare;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    String email, password;
    TextView toolbarTitle, textViewMessage,tv_badday;
    private DrawerLayout mDrawerLayout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontscreen);
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
        //tabLayout.setupWithViewPager(viewpager);
        //setupViewPager(viewpager);

       // FirebaseMessaging firebaseMessaging=FirebaseMessaging.getInstance();

        li_bdday=findViewById(R.id.li_bdday);
        tv_badday=findViewById(R.id.tv_badday);
        textViewMessage=findViewById(R.id.tv_displayname);
        consulation=findViewById(R.id.consulation);
        horoscope=findViewById(R.id.horoscope);
        dailyforecast=findViewById(R.id.dailyForecast);
        yearlyforecast=findViewById(R.id.yearlyForecast);
        publications=findViewById(R.id.publications);
        compare = findViewById(R.id.compare);

        FragmentNavigationDrawer fragmentNavigationDrawer = (FragmentNavigationDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);
        fragmentNavigationDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        //todo language selection at app startup code here


//        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View dialogView = inflater.inflate(R.layout.layout_rate, (ViewGroup)findViewById(R.id.layout_root));

        AppRate.with(this)
                .setTitle(getString(R.string.rate_dialog_title1))
                .setMessage(getString(R.string.rate_dialog_message1))
                .setTextRateNow(getString(R.string.rate_dialog_ok1))
                .setTextLater(getString(R.string.rate_dialog_cancel1))
                .setTextNever(getString(R.string.rate_dialog_no1))
                .setInstallDays(0)
                .setLaunchTimes(1)
                .setRemindInterval(1)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);

        horoscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FrontScreen.this,Books.class).putExtra("Service","Horoscope");
                startActivity(intent);
            }
        });

        consulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FrontScreen.this,Books.class).putExtra("Service","Consulation");
                startActivity(intent);
            }
        });

        dailyforecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FrontScreen.this,DashBoard.class).putExtra("Forecast","DAILYFORECAST"));
            }
        });

        yearlyforecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FrontScreen.this,DashBoard.class).putExtra("Forecast","YEARLYFORECAST"));
            }
        });

        publications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FrontScreen.this,Books.class).putExtra("Service","Publications");
                startActivity(intent);
            }
        });
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FrontScreen.this,CompareKundali.class).putExtra("Service","CompareKundali");
                startActivity(intent);
            }
        });

        //click = findViewById(R.id.click);
       // signup = findViewById(R.id.Signup);

        sharedPreferences = getApplicationContext().getSharedPreferences(LOGIN_PREF, 0); // 0 - for private mode
        if (sharedPreferences.getString(TOKEN, null) != null) {
            //Intent intent = new Intent(getApplicationContext(), DashBoard.class);
            //startActivity(intent);
            //finish();
        }

         String dob=sharedPreferences.getString(DATE_OF_BIRTH,"");
         getDate(dob);

        SharedPreferences sharedPreferences2 = getSharedPreferences("language", MODE_PRIVATE);

        String lang=sharedPreferences2.getString(LANGUAGE, "");
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
        /*FragmentNavigationDrawer fragmentNavigationDrawer = (FragmentNavigationDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);
        fragmentNavigationDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);*/


        /*click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });*/

        email=sharedPreferences2.getString(EMAIL, "");
        password=sharedPreferences2.getString(PASSWORD, "");
        displayName();

    }

    public void displayName(){
        String test = sharedPreferences.getString(NAME, "");
        Calendar rightNow = Calendar.getInstance();
        String hours = String.valueOf(rightNow.get(Calendar.HOUR_OF_DAY));
        System.out.println("hours " + hours);
        if (Integer.parseInt(hours) >= 3 && Integer.parseInt(hours) < 11){
            textViewMessage.setText("Good morining, "+test);
        }
        if (Integer.parseInt(hours.trim()) >= 11 && Integer.parseInt(hours) < 16){
            textViewMessage.setText("Good afternoon, "+test);
        }
        if (Integer.parseInt(hours.trim()) >= 16 && Integer.parseInt(hours) < 19){
            System.out.println("hoursr "+hours);
            textViewMessage.setText("Good evening, "+test);
        }
        if (Integer.parseInt(hours.trim()) >= 19 ){
            textViewMessage.setText("Good evening, "+test);
        }

    }

    public void youtube(View view){
        watchYoutubeVideo();
    }

    public void watchYoutubeVideo() {
//        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        //Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCAi6aRIvEIswUm2ego5grow"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC90p4N7eeqQ8a5-OHeWV2Mg"));
        startActivity(webIntent);
        try {
//            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    public void Facebook(View view){
        watchFacebookVideo();
    }
    public void watchFacebookVideo(){
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/gatyatmakjyotish/"));
        startActivity(webIntent);
        try {

        }
        catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }

    public  void  Instagram(View view){
        watchInstagramvideo();
    }
    public void watchInstagramvideo(){
        //Intent webIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/gatyatmak_jyotish/"));
        Intent webIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/gatyatmakjyotish/"));

        startActivity(webIntent);
        try {

        }
        catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }


    public  void  Twitter(View view){
        watchTwitterVideo();
    }
    public void watchTwitterVideo(){
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://twitter.com/gatyatmakJyotis"));
        startActivity(webIntent);
        try {

        }
        catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }

    public void facebookGroup(View view){
        watchFacebookGroupPage();
    }
    public void watchFacebookGroupPage(){
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/groups/gatyatmakjyotish/"));
        startActivity(webIntent);
        try {

        }
        catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }


    public void blog(View view){
        watchBlog();
    }
    public void watchBlog(){
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://sangeetapuri.blogspot.com/"));
        startActivity(webIntent);
        try {

        }
        catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }

    public void myspace(View view){
        watchMyspace();
    }
    public void watchMyspace(){
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://myspace.com/gatyatmakjyotish"));
        startActivity(webIntent);
        try {

        }
        catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }

    public void tumbler(View view){
        watchTumbler();
    }
    public void watchTumbler(){
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.tumblr.com/blog/gatyatmak-jyotish"));
        startActivity(webIntent);
        try {

        }
        catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }


    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }*/
/*
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(), Books.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent1 = new Intent(getApplicationContext(), Remedy.class);
                startActivity(intent1);
                return true;
            case R.id.item3:
                Intent intent2 = new Intent(getApplicationContext(), PointstoKnow.class);
                startActivity(intent2);
                return true;
            case R.id.item4:
                Intent intent3 = new Intent(getApplicationContext(), UseofApp.class);
                startActivity(intent3);
                return true;
            case R.id.item5:
                Intent intent4 = new Intent(getApplicationContext(), Team.class);
                startActivity(intent4);
                return true;
            case R.id.item6:
                Intent intent5 = new Intent(getApplicationContext(), Language.class);
                startActivity(intent5);
                return true;
            case R.id.item7:
                Intent intent6 = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(intent6);
                return true;
            case R.id.item8:
                Intent intent7 = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(intent7);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }*/
   // }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu_withoutcart, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.notification:
                Intent i=new Intent(this,Notification.class);
                startActivity(i);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public  void getDate(String dt)
    {

        try {
            String dateParts[] = dt.split("-");
            String month = dateParts[1];
            String day = dateParts[2];
            String year = dateParts[0];


            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateToStr = format.format(today);

            String datePart[] = dateToStr.split("-");

            if (datePart[1].equalsIgnoreCase(month) && datePart[2].equalsIgnoreCase(day)) {
                li_bdday.setVisibility(View.VISIBLE);
                tv_badday.setText("Happy Birthday " + sharedPreferences.getString(NAME, ""));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
