package com.gatyatmakjyotish.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gatyatmakjyotish.ModelClass.PublishModel;
import com.gatyatmakjyotish.OnClickListener;
import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.ui.fragment.Publish;
import com.gatyatmakjyotish.ui.fragment.Service;
import com.gatyatmakjyotish.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.gatyatmakjyotish.adapters.AboutAdapter.dailyStatus;
import static com.gatyatmakjyotish.adapters.AboutAdapter.yearlyStatus;

public class Books extends AppCompatActivity implements OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar toolbar;
    Button button;
    List<PublishModel> publishModelList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    private String forecast = "";
    public String setService = "";
    int p = -1;
    public static Boolean publishStatus = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_service_base);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        toolbar = findViewById(R.id.toolbar);
        //Util.setupToolbar(this, toolbar, getString(R.string.publish));
        tabLayout.setupWithViewPager(viewPager);
        if (getIntent().getExtras().getString("Forecast") != null)
            forecast = getIntent().getExtras().getString("Forecast");

        if (sharedPreferences.getBoolean("dailyStatus", false) || sharedPreferences.getBoolean("yearlyStatus", false)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cart", "");
            editor.commit();
            dailyStatus = false;
            yearlyStatus = false;
            editor.putBoolean("dailyStatus", false);
            editor.putBoolean("yearlyStatus", false);
            editor.commit();
        }


        if (getIntent().getExtras().getString("Service") != null)
            setService = getIntent().getExtras().getString("Service");

        if (!setService.equalsIgnoreCase("Publications")) {
            tabLayout.setVisibility(View.GONE);
            if (setService.equalsIgnoreCase("Horoscope")) {
                Util.setupToolbar(this, toolbar, getString(R.string.horoscope) + "/" + getString(R.string.services));
                p = 1;
            } else {
                Util.setupToolbar(this, toolbar, getString(R.string.consulation) + "/" + getString(R.string.services));
                p = 1;
            }
        } else {
            Util.setupToolbar(this, toolbar, getString(R.string.publish));
            p = 0;
        }
        setUpViewPager(viewPager);
        button = findViewById(R.id.button);
        getFreshCartItems();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();


                if (publishModelList.size() == 0) {
                    Util.showDialog(Books.this, "Service Not Selected", "Please select at least one service.", false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    editor.putString("cart", new Gson().toJson(publishModelList));
                    editor.commit();

                    if(publishModelList.get(0).getTitle()!=R.string.title_service2)
                    {
                        p= 0;
                    }


                    if(p==0)
                    {
                        Intent intent = new Intent(Books.this, Cart.class);
                        intent.putExtra("proceedWith", p);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(Books.this, CompareKundali.class);
                        intent.putExtra("Service", "CompareKundali");
                        intent.putExtra("proceedWith", p);
                        startActivity(intent);
                    }



//

                }
            }
        });
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Intent i=new Intent(this,Notification.class);
                startActivity(i);

                break;
            case R.id.cart:
                if (forecast.equalsIgnoreCase("DAILYFORECAST")) {
                    int title[] = {R.string.dailyforecast};
                    int description[] = {R.string.daily_cart};

                    List<PublishModel> publishList = new ArrayList<>();

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String json = sharedPreferences.getString("cart", "");

                    Type type = new TypeToken<List<PublishModel>>() {
                    }.getType();

                    if (sharedPreferences.getBoolean("yearlyStatus", false)) {
                        if (!sharedPreferences.getBoolean("dailyStatus", false)) {
                            publishList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);
                            publishList.add(new PublishModel(description[0], 22332, true, title[0], 400));
                        }
                    } else {
                        editor.putString("cart", "");
                        publishList.add(new PublishModel(description[0], 22332, true, title[0], 400));
                    }

                    dailyStatus = true;
                    editor.putBoolean("dailyStatus", true);
                    editor.commit();
                    if (publishList.size() != 0) {
                        editor.putString("cart", new Gson().toJson(publishList));
                        editor.commit();
                    }

                } else if (forecast.equalsIgnoreCase("YEARLYFORECAST")) {
                    int title[] = {R.string.yearlyforecast};
                    int description[] = {R.string.yearly_cart};


                    List<PublishModel> publishList = new ArrayList<>();

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String json = sharedPreferences.getString("cart", "");
                    System.out.println("StatusForDate " + dailyStatus + " json " + json);
                    if (sharedPreferences.getBoolean("dailyStatus", false)) {
                        if (!sharedPreferences.getBoolean("yearlyStatus", false)) {
                            Type type = new TypeToken<List<PublishModel>>() {
                            }.getType();
                            publishList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);
                            publishList.add(new PublishModel(description[0], 22332, true, title[0], 400));
                        }
                    } else {
                        editor.putString("cart", "");
                        publishList.add(new PublishModel(description[0], 22332, true, title[0], 400));
                    }

                    yearlyStatus = true;
                    editor.putBoolean("yearlyStatus", true);
                    editor.commit();
                    if (publishList.size() != 0) {
                        editor.putString("cart", new Gson().toJson(publishList));
                        editor.commit();
                    }
                }

                Intent intent = new Intent(getApplicationContext(), Cart.class);
                intent.putExtra("reference", "DashBoardActivity");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (setService.equalsIgnoreCase("Publications"))
            adapter.addFragment(new Publish(), getString(R.string.publish));
        // if(setService.equalsIgnoreCase("Publications") || !setService.equalsIgnoreCase("Publications"))
        adapter.addFragment(new Service(), getString(R.string.services));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(PublishModel publishModel, boolean isChecked) {
        if (isChecked) {
            publishModelList.add(publishModel);

        } else {
            Iterator<PublishModel> iterator = publishModelList.iterator();
            while (iterator.hasNext()) {
                PublishModel publishModel1 = iterator.next();
                if (publishModel1.getPublish().equals(publishModel.getPublish())) {
                    iterator.remove();
                }
            }
        }
    }

    private void getFreshCartItems() {
        String json = sharedPreferences.getString("cart", "");
        if (json != null && json.isEmpty()) {
/*
            Toast.makeText(this, "json is empty", Toast.LENGTH_SHORT).show();
*/
        } else {
            Type type = new TypeToken<List<PublishModel>>() {
            }.getType();
            publishModelList = new Gson().fromJson(sharedPreferences.getString("cart", ""), type);
        }
    }


    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
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
}
