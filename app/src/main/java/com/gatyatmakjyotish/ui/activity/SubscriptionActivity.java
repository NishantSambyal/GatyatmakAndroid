package com.gatyatmakjyotish.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.SubscriptionAdapter;
import com.gatyatmakjyotish.util.Util;

import java.util.Arrays;

public class SubscriptionActivity extends AppCompatActivity {
    private TextView aboutUs;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, getString(R.string.subscription));
        aboutUs=findViewById(R.id.textview);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new SubscriptionAdapter(Arrays.asList(getResources().getStringArray(R.array.subscription))));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
