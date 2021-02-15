package com.gatyatmakjyotish.util;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.gatyatmakjyotish.R;

public class ImageViewer extends AppCompatActivity {


    ImageView imageView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);
        imageView = findViewById(R.id.image_view);
        Glide.with(this).load(getResources().getDrawable(R.drawable.graph)).into(imageView);
        imageView.setOnTouchListener(new ImageMatrixTouchHandler(this));
    }
}
