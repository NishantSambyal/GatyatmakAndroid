package com.gatyatmakjyotish.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.util.Util;

public class Marketing  extends AppCompatActivity {
    private TextView marketing;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketing);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, getString(R.string.marketing));
        marketing = findViewById(R.id.marketing);
        marketing.setText("With downloading this app, user will get 50 gatyatmak points. You will get 20-20 gatyatmak points sharing this app with your contacts. After sharing by you and downloading by a user, you will get 50 more gatyatmak points. You will get 100 gatyatmak points too by rating this app. One point will be equal to one Indian rupee. You will use these points in purchasing our books and availing our services upto 25% to 50%\n");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
