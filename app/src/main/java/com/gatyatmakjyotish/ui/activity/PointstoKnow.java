package com.gatyatmakjyotish.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.util.SaveTextSize;
import com.gatyatmakjyotish.util.Util;

public class PointstoKnow  extends AppCompatActivity {
    private TextView tv_points,topPoint;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Button prev,next;
    private ImageView imageView;
    private Dialog dialog;
    private int points_counter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pointstoknow);
        toolbar = findViewById(R.id.toolbar);
        tv_points = findViewById(R.id.points);
        topPoint = findViewById(R.id.topPoint);
        prev = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        Util.setupToolbar(this, toolbar, getString(R.string.pointsforliving));
        //recyclerView = findViewById(R.id.recycler_view);
        //recyclerView.setAdapter(new AboutAdapter(Arrays.asList(getResources().getStringArray(R.array.living))));
        tv_points.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(getApplicationContext()).getTextSize());
        topPoint.setTextSize(TypedValue.COMPLEX_UNIT_PX, SaveTextSize.getInstance(getApplicationContext()).getTextSize());

        final String points[] = getResources().getStringArray(R.array.living);
        tv_points.setText(points[points_counter]);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(points_counter != 0){
                    tv_points.setText(points[points_counter-1]);
                    points_counter--;
                }

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(points_counter < points.length-1){
                   tv_points.setText(points[points_counter+1]);
                   points_counter++;
               }

            }
        });

        imageView=findViewById(R.id.imagePoints);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
                dialog.show();
            }
        });



    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    private void openImage(){
        dialog=new Dialog(PointstoKnow.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.full_image_view);
        dialog.setCancelable(true);
//        ImageView fullimage=(ImageView)dialog.findViewById(R.id.full_screen_image);
//        fullimage.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView closeImage=(ImageView)dialog.findViewById(R.id.closeDialog);

        //fullimage.animate().rotation(270).start();

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppRate.with(PointstoKnow.this)
//                        .setStoreType(StoreType.GOOGLEPLAY) //default is GOOGLEPLAY (Google Play), other options are
//                        //           AMAZON (Amazon Appstore) and
//                        //           SAMSUNG (Samsung Galaxy Apps)
//                        .setInstallDays((byte) 0) // default 10, 0 means install day
//                        .setLaunchTimes((byte) 3) // default 10
//                        .setRemindInterval((byte) 2) // default 1
//                        .setRemindLaunchTimes((byte) 2) // default 1 (each launch)
//                        .setShowLaterButton(true) // default true
//                        .setDebug(false) // default false
//                        //Java 8+: .setOnClickButtonListener(which -> Log.d(MainActivity.class.getName(), Byte.toString(which)))
//                        .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
//                            @Override
//                            public void onClickButton(byte which) {
//                                Log.d(MainActivity.class.getName(), Byte.toString(which));
//                            }
//                        })
//                        .monitor();
//
//                if (AppRate.with(getApplicationContext()).getStoreType() == StoreType.GOOGLEPLAY) {
//                    //Check that Google Play is available
//                    if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext()) != ConnectionResult.SERVICE_MISSING) {
//                        // Show a dialog if meets conditions
//                        AppRate.showRateDialogIfMeetsConditions(PointstoKnow.this);
//                    }
//                } else {
//                    // Show a dialog if meets conditions
//                    AppRate.showRateDialogIfMeetsConditions(PointstoKnow.this);
//                }

                dialog.dismiss();
            }
        });
    }
}
