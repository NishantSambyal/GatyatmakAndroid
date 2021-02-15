package com.gatyatmakjyotish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.util.Util;

public class Set extends AppCompatActivity {
    LinearLayout profile, changepassword, font;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        profile = findViewById(R.id.profile);
        changepassword = findViewById(R.id.Cpassword);
        font = findViewById(R.id.font);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Language.class);
                startActivity(intent);
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
        font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ChangeFontSize.class);
                startActivity(intent);
            }
        });
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbar(this, toolbar, getString(R.string.settings));
    }

        @Override
        public boolean onSupportNavigateUp () {
            onBackPressed();
            return super.onSupportNavigateUp();
        }


}