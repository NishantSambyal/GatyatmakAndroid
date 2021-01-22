package com.gatyatmakjyotish.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.AboutAdapter;
import com.gatyatmakjyotish.util.Util;

import java.util.Arrays;

public class AboutUs extends AppCompatActivity {
    private TextView aboutUs;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, getString(R.string.aboutus));
        aboutUs=findViewById(R.id.textview);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new AboutAdapter(Arrays.asList(getResources().getStringArray(R.array.about))));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
