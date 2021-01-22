package com.gatyatmakjyotish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.ui.fragment.Consultation;
import com.gatyatmakjyotish.ui.fragment.HoroMaking;
import com.gatyatmakjyotish.ui.fragment.HoroMatching;
import com.gatyatmakjyotish.util.Util;

import java.util.ArrayList;
import java.util.List;

public class OtherServices extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView toolbarTitle;
    private ViewPagerAdapter viewPagerAdapter;
    private Button redeem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horoscope);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, getString(R.string.otherservices));

      /*  setSupportActionBar(toolbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.otherservices));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       */ tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        redeem = findViewById(R.id.redeem);
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Payment.class);
                startActivity(i);
                finish();

            }
        });

    }

    private void setUpViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new HoroMaking(), getString(R.string.horoscopemaking));
        viewPagerAdapter.addFragment(new HoroMatching(), getString(R.string.horoscopematching));
        viewPagerAdapter.addFragment(new Consultation(), getString(R.string.consultation));
        viewPager.setAdapter(viewPagerAdapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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


}
