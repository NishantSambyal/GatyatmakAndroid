package com.gatyatmakjyotish.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.constants.Constants;
import com.gatyatmakjyotish.util.Util;

import java.util.Locale;

import static com.gatyatmakjyotish.constants.Constants.LANGUAGE;
import static com.gatyatmakjyotish.constants.Constants.TOKEN;

public class Language extends AppCompatActivity {

    private Button langbtn;
    private TextView langindicator, welcome;
    private String selectedLanguage;
    private RadioButton hindi, english;
    private RadioGroup radioLanguage;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language);
        langbtn = findViewById(R.id.langbtn);
        langindicator = findViewById(R.id.languageindicator);

        radioLanguage = findViewById(R.id.rd_language);
        english = findViewById(R.id.english);
        hindi = findViewById(R.id.hindi);

        langindicator.setText(R.string.select);
        langbtn.setText(R.string.ok);
        english.setText(R.string.english);
        hindi.setText(R.string.hindi);

        sharedPreferences = getSharedPreferences("language", MODE_PRIVATE);
        //String language = sharedPreferences.getString("languageToLoad", Locale.getDefault().getDisplayLanguage());
        String lang=sharedPreferences.getString(LANGUAGE, "");

        if (!lang.equals("")) {
            if (lang.equals(Constants.Language.ENGLISH.getLanguage())) {
                english.setSelected(true);
                english.setChecked(true);
                hindi.setSelected(false);
                hindi.setChecked(false);
            } else {
                hindi.setSelected(true);
                hindi.setChecked(true);
            }
        }
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbarWithoutBack(this, toolbar, getString(R.string.changelanguage));


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Resources res1 = getResources();
                DisplayMetrics dm1 = res1.getDisplayMetrics();
                android.content.res.Configuration conf1 = res1.getConfiguration();
                conf1.locale = Locale.getDefault();
                res1.updateConfiguration(conf1, dm1);
                recreate();*/
                langindicator.setText(getString(R.string.select));
                langbtn.setText(getString(R.string.OK));
            }
        });

        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.locale = new Locale("hi");
                res.updateConfiguration(conf, dm);
                recreate();*/
                langindicator.setText(getString(R.string.select));
                langbtn.setText(getString(R.string.OK));
            }
        });



        langbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                int radioButtonID = radioLanguage.getCheckedRadioButtonId();

                if(radioButtonID > 0){
                    if (radioButtonID == R.id.english) {
                        conf.locale = Locale.getDefault();
                        editor.putString("language", Constants.Language.ENGLISH.getLanguage());
                        editor.putString(LANGUAGE,Constants.Language.ENGLISH.getLanguage());
                        editor.commit();
                    } else {
                        conf.locale = new Locale("hi");
                        editor.putString("language", Constants.Language.HINDI.getLanguage());
                        editor.putString(LANGUAGE, Constants.Language.HINDI.getLanguage());
                        editor.commit();
                    }
                    editor.apply();
                    res.updateConfiguration(conf, dm);

                    if(sharedPreferences.getString(TOKEN, null) != null) {
                        //Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                        Intent intent = new Intent(getApplicationContext(), FrontScreen.class);
                        startActivity(intent);
                        finish();
                    }else {
                           Intent i = new Intent(getApplicationContext(), MainActivity.class);
                           i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           startActivity(i);
                       }
                }
                else
                    Toast.makeText(Language.this, "Please Select Language", Toast.LENGTH_SHORT).show();

            }

        });



    }
}

