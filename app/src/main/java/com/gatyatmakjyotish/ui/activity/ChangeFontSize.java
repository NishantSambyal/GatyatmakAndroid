package com.gatyatmakjyotish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.util.SaveTextSize;
import com.gatyatmakjyotish.util.Util;

public class ChangeFontSize extends AppCompatActivity {
    private RadioGroup radioTextSize;
  RadioButton device,tiny,medium,large,extralarge,huge;
  Button ok,cancel;
  TextView textView;
    private Toolbar toolbar;
  public static final int DEVICE=50;
  public static final int TINY = 30;
  public static final int MEDIUM = 50;
  public static final int LARGE = 65;
  public static final int EXTRA_LARGE = 80;
  public static final int HUGE = 100;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changefontsize);
        radioTextSize = findViewById(R.id.device);
        textView=findViewById(R.id.text);
        device=findViewById(R.id.rd_device);
        tiny=findViewById(R.id.rd_tiny);
        medium=findViewById(R.id.rd_medium);
        large=findViewById(R.id.rd_large);
        extralarge=findViewById(R.id.rd_extralarge);
        huge=findViewById(R.id.rd_huge);
        ok=findViewById(R.id.button);
        cancel=findViewById(R.id.cancel);
        toolbar = findViewById(R.id.toolbar);
        Util.setupToolbarWithoutBack(this, toolbar, getString(R.string.changefontsize));
        device.setTextSize(TypedValue.COMPLEX_UNIT_PX, DEVICE);
        tiny.setTextSize(TypedValue.COMPLEX_UNIT_PX, TINY);
        medium.setTextSize(TypedValue.COMPLEX_UNIT_PX, MEDIUM);
        large.setTextSize(TypedValue.COMPLEX_UNIT_PX, LARGE);
        extralarge.setTextSize(TypedValue.COMPLEX_UNIT_PX, EXTRA_LARGE);
        huge.setTextSize(TypedValue.COMPLEX_UNIT_PX, HUGE);

        radioTextSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rd_device:
                        SaveTextSize.getInstance(getApplicationContext()).saveTextSize(DEVICE);
                        break;
                    case R.id.rd_tiny:
                        SaveTextSize.getInstance(getApplicationContext()).saveTextSize(TINY);
                        break;
                    case R.id.rd_medium:
                        SaveTextSize.getInstance(getApplicationContext()).saveTextSize(MEDIUM);
                        break;
                    case R.id.rd_large:
                        SaveTextSize.getInstance(getApplicationContext()).saveTextSize(LARGE);
                        break;
                    case R.id.rd_extralarge:
                        SaveTextSize.getInstance(getApplicationContext()).saveTextSize(EXTRA_LARGE);
                        break;
                    case R.id.rd_huge:
                        SaveTextSize.getInstance(getApplicationContext()).saveTextSize(HUGE);
                        break;
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),FrontScreen.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),FrontScreen.class);
                startActivity(intent);
            }
        });

    }
}
