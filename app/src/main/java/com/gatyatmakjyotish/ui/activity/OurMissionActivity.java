package com.gatyatmakjyotish.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.adapters.AboutAdapter;
import com.gatyatmakjyotish.util.Util;

import java.util.Arrays;

public class OurMissionActivity extends AppCompatActivity {
    private TextView points;
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ourmission);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, getString(R.string.our_mission));
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new AboutAdapter(Arrays.asList(getResources().getStringArray(R.array.ourmission))));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
