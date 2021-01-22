package com.gatyatmakjyotish.ui.fragmentBase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.ui.fragment.FragmentBlog;
import com.gatyatmakjyotish.util.Util;

public class Blog extends AppCompatActivity {

    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, getString(R.string.Blog));
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, new FragmentBlog()).commitNow();

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}
