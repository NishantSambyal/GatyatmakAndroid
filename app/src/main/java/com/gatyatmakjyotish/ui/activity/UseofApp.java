package com.gatyatmakjyotish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.AboutAdapter;
import com.gatyatmakjyotish.util.ImageViewer;
import com.gatyatmakjyotish.util.Util;

import java.util.Arrays;

public class UseofApp extends AppCompatActivity {
    private TextView useofapp;
    private Toolbar toolbar;
    private ImageView imageView;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useofapp);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        imageView=findViewById(R.id.pandit);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ImageViewer.class));
            }
        });
        Util.setupToolbar(this, toolbar, getString(R.string.useofapp));
        Util.setLinearLayoutManagerNestedScroll(this, recyclerView);
        recyclerView.setAdapter(new AboutAdapter(Arrays.asList(getResources().getStringArray(R.array.use))));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
