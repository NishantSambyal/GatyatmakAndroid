package com.gatyatmakjyotish.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gatyatmakjyotish.ModelClass.PublishModel;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.ui.fragment.Date;
import com.gatyatmakjyotish.ui.fragment.Year;
import com.gatyatmakjyotish.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.gatyatmakjyotish.adapters.AboutAdapter.dailyStatus;
import static com.gatyatmakjyotish.adapters.AboutAdapter.yearlyStatus;
import static com.gatyatmakjyotish.ui.activity.Books.mypreference;

/*import com.gatyatmakjyotish.Year;*/

public class DashBoard extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    TextView toolbarTitle;
    private DrawerLayout mDrawerLayout;
    private String forecast = "";
    SharedPreferences sharedPreferences;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_base_activity);
        toolbar = findViewById(R.id.toolbar);

        if(getIntent().getExtras().getString("Forecast") != null) {
            forecast = getIntent().getExtras().getString("Forecast");
        }
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        viewpager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.app_name));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tabLayout.setupWithViewPager(viewpager);
        setupViewPager(viewpager);
        
        //Util.setupToolbar(DashBoard.this,toolbar,getString(R.string.app_name));
        if(forecast.equalsIgnoreCase("DAILYFORECAST"))
            Util.setupToolbar(DashBoard.this,toolbar,getString(R.string.dailyforecast));
        else if(forecast.equalsIgnoreCase("YEARLYFORECAST"))
            Util.setupToolbar(DashBoard.this,toolbar,getString(R.string.yearlyforecast));
/*

        FragmentNavigationDrawer fragmentNavigationDrawer = (FragmentNavigationDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);
        fragmentNavigationDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
*/



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(forecast.equals("DAILYFORECAST"))
            adapter.addFragment(new Date(), "");
        else if(forecast.equals("YEARLYFORECAST"))
            adapter.addFragment(new Year(), "");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
            System.out.println("PositionStatement " +position);
            return mFragmentList.get(position);
        }

        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        public CharSequence getPageTitle(int Position) {
            return mFragmentTitleList.get(Position);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


   /* public void youtube(View view){
        watchYoutubeVideo();
    }





    public void watchYoutubeVideo() {
//        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/channel/UCAi6aRIvEIswUm2ego5grow"));
        startActivity(webIntent);
        try {
//            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

public  void  Facebook(View view){
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
        Intent webIntent=new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.instagram.com/gatyatmak_jyotish/"));
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
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return true;
     }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.notification:

                Intent i=new Intent(this,Notification.class);
                startActivity(i);

                break;
            case R.id.cart:
                if(forecast.equalsIgnoreCase("DAILYFORECAST")){

                    int title[] = {R.string.dailyforecast};
                    int description[] = {R.string.daily_cart};

                    List<PublishModel> publishList = new ArrayList<>();

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String json = sharedPreferences.getString("cart", "");

                    Type type = new TypeToken<List<PublishModel>>() {}.getType();

                    if(sharedPreferences.getBoolean("yearlyStatus",false)){
                        if(!sharedPreferences.getBoolean("dailyStatus",false)){
                            publishList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);
                            publishList.add(new PublishModel(description[0],22332,true,title[0],1, false));
                        }
                    }
                    else{
                        editor.putString("cart", "");
                        publishList.add(new PublishModel(description[0],22332,true,title[0],1, false));
                    }

                    dailyStatus = true;
                    editor.putBoolean("dailyStatus",true);
                    editor.commit();
                    if(publishList.size() != 0){
                        editor.putString("cart", new Gson().toJson(publishList));
                        editor.commit();
                    }

                }
                else if(forecast.equalsIgnoreCase("YEARLYFORECAST")){
                    int title[] = {R.string.yearlyforecast};
                    int description[] = {R.string.yearly_cart};


                    List<PublishModel> publishList = new ArrayList<>();

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String json = sharedPreferences.getString("cart", "");
                    System.out.println("StatusForDate " +dailyStatus + " json " + json);
                    if(sharedPreferences.getBoolean("dailyStatus",false)){
                        if(!sharedPreferences.getBoolean("yearlyStatus",false)){
                            Type type = new TypeToken<List<PublishModel>>() {}.getType();
                            publishList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);
                            publishList.add(new PublishModel(description[0],22332,true,title[0],1, false));
                        }
                    }
                    else{
                        editor.putString("cart", "");
                        publishList.add(new PublishModel(description[0],22332,true,title[0],1, false));
                    }

                    yearlyStatus = true;
                    editor.putBoolean("yearlyStatus",true);
                    editor.commit();
                    if(publishList.size() != 0) {
                        editor.putString("cart", new Gson().toJson(publishList));
                        editor.commit();
                    }
                }

                Intent intent=new Intent(getApplicationContext(),Cart.class);
                intent.putExtra("reference","DashBoardActivity");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}


