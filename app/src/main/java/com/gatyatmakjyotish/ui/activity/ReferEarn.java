package com.gatyatmakjyotish.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.util.Util;

public class ReferEarn extends AppCompatActivity {
  TextView share;
  Toolbar toolbar;
    RatingBar ratingBar;
    private RecyclerView recyclerView;
  private final  static String APP_TITLE="Gatyatmak jyothish";
    private final  static String APP_PNAME="com.gatyatmakjyotish.ridhi.gatyatmakjyotish";
    private final static int DAYS_UNTIL_PROMPT=3;
    private final static int LAUNCHES_UNTIL_PROMPT=3;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer);
        toolbar = findViewById(R.id.toolbar);
       /* recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new RemedyRecyclerAdpter(Arrays.asList(getResources().getStringArray(R.array.points))));*/
        ratingBar = findViewById(R.id.rating_bar);
        Util.setupToolbar(this, toolbar,getString(R.string.refer));
        share = findViewById(R.id.tv_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "I recommend you this application, download this for free & accurate time-bound day-to-day and yearly forecast for different aspects of your life by Gatyatmak Jyotish, a new branch in Astrology. Other Astrological Services is also available here: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.plus")));

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}


