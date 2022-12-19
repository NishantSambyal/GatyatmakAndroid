package com.gatyatmakjyotish.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.util.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationActivity extends AppCompatActivity {
    private TextView tv_notification_title,tv_notification_message;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private Pattern urlPattern;
    int matchStart =0, matchEnd=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, getString(R.string.notification));
        tv_notification_title=findViewById(R.id.tv_notification_title);
        tv_notification_message=findViewById(R.id.tv_notification_message);
        //recyclerView = findViewById(R.id.recycler_view);
        //recyclerView.setAdapter(new SubscriptionAdapter(Arrays.asList(getResources().getStringArray(R.array.subscription))));


        if(getIntent() != null){
            if(getIntent().getExtras().get("title") != null || getIntent().getExtras().get("message") != null){
                tv_notification_title.setText(""+getIntent().getStringExtra("title"));
                tv_notification_message.setText(""+getIntent().getStringExtra("message"));
            }
        }

        urlPattern = Pattern.compile(
                "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                        + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                        + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);


        Matcher matcher = urlPattern.matcher(getIntent().getStringExtra("message"));
        while (matcher.find()) {
            matchStart = matcher.start(1);
            matchEnd = matcher.end();
            System.out.println("eyueyuyueyweyweyweeywwewewee " +matchStart + " - " +matchEnd);
        }

        try{
            SpannableString ss = new SpannableString(getIntent().getStringExtra("message"));

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    try{

                        System.out.println("eywyyuyuwewe " +getIntent().getStringExtra("message").substring(matchStart, matchEnd));
                        //Intent webIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/gatyatmakjyotish/"));
                        Intent webIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("message").substring(matchStart, matchEnd)));
                        startActivity(webIntent);
                        System.out.println("clickyuweyueeuwweweweweweewe");

                    }catch(Exception e){
                        System.out.println("ryurryuryuryuryueryueryueryueryu  " +e);
                    }
                }
            };

            ss.setSpan(clickableSpan, matchStart, matchEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            tv_notification_message.setText(ss);
            tv_notification_message.setMovementMethod(LinkMovementMethod.getInstance());

        }catch (Exception e){
            System.out.println("ryurryuryuryuryueryueryueryueryu  " +e);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();  // optional depending on your needs
        finish();
    }
}
